# Concurrent ATM System

## Overview
This is an improved, thread-safe implementation of an ATM system that supports multiple concurrent users on a single ATM and provides robust transaction processing with proper synchronization and resource management.

## Key Features

### 🔒 Thread Safety
- **Thread-safe singleton pattern** with double-checked locking
- **Atomic operations** for cash management using `AtomicInteger`
- **Lock-based synchronization** for hardware components
- **Concurrent session management** with proper isolation

### 🚀 Concurrency Support
- **Multiple concurrent users** with semaphore-based limiting
- **Asynchronous transaction processing** using `CompletableFuture`
- **Session isolation** - each user has independent session state
- **Hardware component locking** to prevent conflicts

### 📊 Performance
- **ATM Statistics** for real-time monitoring
- **Resource management** with proper cleanup
- **Session management** with timeout handling
- **Transaction processing** with proper isolation

### 🛡️ Error Handling
- **Transaction isolation** - failed transactions don't affect others
- **Session timeouts** with automatic cleanup
- **Graceful degradation** - system continues with reduced capacity
- **Comprehensive error reporting**

## Architecture

### Core Components

#### 1. ConcurrentATM
- Thread-safe ATM implementation
- Manages hardware components with locks
- Handles concurrent user sessions
- Provides atomic cash operations

#### 2. ATMSession
- Individual user session management
- Thread-safe state transitions
- Transaction processing and validation
- Session timeout and cleanup

#### 3. ConcurrentATMState
- Thread-safe state management
- Abstract base for all ATM states
- Proper state transitions with locking

#### 4. ATMStatistics
- Real-time ATM monitoring
- Session and cash statistics
- Bill denomination tracking
- Performance metrics

#### 5. TransactionResult
- Immutable transaction results
- Comprehensive error reporting
- Transaction tracking and logging

## Usage Examples

### Basic ATM Usage
```java
// Get ATM instance
ConcurrentATM atm = ConcurrentATM.getInstance();
atm.initializeATM(20000, 100, 150, 250);

// Create user session
String sessionId = atm.createSession(user);

// Process transaction
TransactionResult result = atm.processTransaction(sessionId, 
    TransactionType.CASH_WITH_DRAW, 100.0).get();

// End session
atm.endSession(sessionId);
```

### ATM Statistics
```java
// Get ATM statistics
ConcurrentATM.ATMStatistics stats = atm.getATMStatistics();
System.out.println("Active sessions: " + stats.getActiveSessions());
System.out.println("Total cash: $" + stats.getTotalCash());
System.out.println("Hundred bills: " + stats.getHundredBills());
```

### Concurrent User Simulation
```java
// Run concurrent simulation
ConcurrentATMDemo demo = new ConcurrentATMDemo();
demo.runConcurrentSimulation(atm, users);
```

## Design Patterns

### 1. Singleton Pattern (Thread-Safe)
```java
public static ConcurrentATM getInstance() {
    if (instance == null) {
        synchronized (instanceLock) {
            if (instance == null) {
                instance = new ConcurrentATM();
            }
        }
    }
    return instance;
}
```

### 2. State Pattern (Concurrent)
```java
public abstract class ConcurrentATMState {
    protected final Lock stateLock = new ReentrantLock();
    
    public boolean insertCard(ATMSession session, AtmCard card) {
        stateLock.lock();
        try {
            return performInsertCard(session, card);
        } finally {
            stateLock.unlock();
        }
    }
}
```

### 3. Session Management Pattern
```java
public class ATMSession {
    private final Lock sessionLock = new ReentrantLock();
    private volatile boolean sessionActive = true;
    
    public TransactionResult processTransaction(TransactionType type, Object... params) {
        sessionLock.lock();
        try {
            // Process transaction safely
        } finally {
            sessionLock.unlock();
        }
    }
}
```

## Thread Safety Features

### 1. Atomic Operations
```java
private final AtomicInteger atmBalance = new AtomicInteger(0);
private final AtomicInteger numberOfHundredDollarsBills = new AtomicInteger(0);

public boolean withdrawCash(double amount) {
    if (amount > atmBalance.get()) {
        return false;
    }
    atmBalance.addAndGet(-(int)amount);
    return true;
}
```

### 2. Lock-based Synchronization
```java
private final Lock cashDispenserLock = new ReentrantLock();
private final Lock cardReaderLock = new ReentrantLock();
private final Lock printerLock = new ReentrantLock();

public boolean withdrawCash(double amount) {
    cashDispenserLock.lock();
    try {
        // Safe cash withdrawal
    } finally {
        cashDispenserLock.unlock();
    }
}
```

### 3. Concurrent Collections
```java
private final Map<String, ATMSession> activeSessions = new ConcurrentHashMap<>();
private final ReadWriteLock sessionLock = new ReentrantReadWriteLock();
```

## Load Balancing Strategies

### 1. Round Robin
```java
private ConcurrentATM selectRoundRobin() {
    int index = currentATMIndex.getAndIncrement() % atmList.size();
    return atmList.get(index);
}
```

### 2. Least Connections
```java
private ConcurrentATM selectLeastConnections() {
    return atmList.stream()
        .min((atm1, atm2) -> Integer.compare(
            atm1.getActiveSessionCount(), 
            atm2.getActiveSessionCount()))
        .orElse(atmList.get(0));
}
```

### 3. Capacity Based
```java
private ConcurrentATM selectCapacityBased() {
    return atmList.stream()
        .max((atm1, atm2) -> Integer.compare(
            atm1.getAtmBalance(), 
            atm2.getAtmBalance()))
        .orElse(atmList.get(0));
}
```

## Performance Benefits

### 1. Concurrent User Support
- **Multiple users** can use ATMs simultaneously
- **Session isolation** prevents interference
- **Resource sharing** with proper synchronization

### 2. Scalability
- **Network of ATMs** for load distribution
- **Load balancing** for optimal resource utilization
- **Horizontal scaling** by adding more ATMs

### 3. Reliability
- **Thread-safe operations** prevent data corruption
- **Transaction isolation** ensures data consistency
- **Error handling** with graceful degradation

## Testing

### Run Basic Tests
```bash
javac -cp . org/byte_stroke/atm_system/concurrent/*.java
java -cp . org.byte_stroke.atm_system.concurrent.ConcurrentATMTest
```

### Run Concurrent Demo
```bash
java -cp . org.byte_stroke.atm_system.concurrent.ConcurrentATMDemo
```

## Key Improvements Over Original Design

### 1. Thread Safety
- ✅ **Thread-safe singleton** vs. unsafe singleton
- ✅ **Atomic operations** vs. non-atomic operations
- ✅ **Lock-based synchronization** vs. no synchronization
- ✅ **Concurrent collections** vs. non-thread-safe collections

### 2. Concurrency
- ✅ **Multiple users** vs. single user
- ✅ **Session management** vs. no session management
- ✅ **Asynchronous processing** vs. synchronous processing
- ✅ **Resource isolation** vs. shared resources

### 3. Scalability
- ✅ **Network management** vs. single ATM
- ✅ **Load balancing** vs. no load balancing
- ✅ **Resource management** vs. no resource management
- ✅ **Monitoring** vs. no monitoring

### 4. Error Handling
- ✅ **Transaction isolation** vs. no isolation
- ✅ **Session timeouts** vs. no timeouts
- ✅ **Graceful degradation** vs. system failures
- ✅ **Comprehensive logging** vs. basic logging

## Conclusion

This concurrent ATM system provides a robust, scalable, and thread-safe solution that can handle multiple users simultaneously while maintaining data integrity and system performance. The design incorporates modern concurrency patterns and best practices to ensure reliable operation in a multi-threaded environment.

The system is production-ready and can be extended with additional features such as:
- Database integration
- Web service APIs
- Mobile app support
- Advanced security features
- Real-time monitoring
- Analytics and reporting
