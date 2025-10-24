# Concurrent ATM System - Improved Class Diagram

## Overview
This document describes the improved concurrent ATM system design that addresses thread safety, concurrency, and performance requirements for a single ATM supporting multiple users.

## Key Improvements

### 1. Thread Safety
- **ConcurrentATM**: Thread-safe singleton with proper synchronization
- **ATMSession**: Individual user session management with locks
- **ConcurrentATMState**: Thread-safe state management
- **TransactionResult**: Immutable transaction results

### 2. Concurrency Features
- **Multiple User Support**: Concurrent sessions with semaphore-based limiting
- **Hardware Component Locking**: Prevents conflicts between concurrent operations
- **Atomic Operations**: Thread-safe cash management using AtomicInteger
- **Session Isolation**: Each user has isolated session state

### 3. Performance
- **ExecutorService**: Asynchronous transaction processing
- **Resource Management**: Proper cleanup and resource management
- **ATM Statistics**: Real-time monitoring and statistics

## Class Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                        ConcurrentATM                            │
├─────────────────────────────────────────────────────────────────┤
│ - instance: volatile ConcurrentATM                              │
│ - cardReader: CardReader                                        │
│ - keyPad: KeyPad                                                │
│ - screen: Screen                                                │
│ - printer: Printer                                              │
│ - cashDispenser: CashDispenser                                 │
│ - atmBalance: AtomicInteger                                     │
│ - numberOfHundredDollarsBills: AtomicInteger                   │
│ - numberOfFiftyDollarsBills: AtomicInteger                     │
│ - numberOfTenDollarsBills: AtomicInteger                       │
│ - activeSessions: ConcurrentHashMap<String, ATMSession>        │
│ - sessionLock: ReadWriteLock                                    │
│ - cardReaderLock: Lock                                          │
│ - cashDispenserLock: Lock                                       │
│ - printerLock: Lock                                              │
│ - transactionExecutor: ExecutorService                          │
│ - maxConcurrentUsers: Semaphore                                 │
│ - atmStatus: volatile ATMStatus                                 │
│ - statusLock: Lock                                               │
├─────────────────────────────────────────────────────────────────┤
│ + getInstance(): ConcurrentATM                                   │
│ + initializeATM(int, int, int, int): void                       │
│ + createSession(User): String                                   │
│ + endSession(String): void                                      │
│ + getSession(String): ATMSession                                │
│ + processTransaction(String, TransactionType, Object...):        │
│   CompletableFuture<TransactionResult>                          │
│ + withdrawCash(double): boolean                                 │
│ + readCard(AtmCard): boolean                                    │
│ + printReceipt(String): void                                    │
│ + getAtmBalance(): int                                          │
│ + getActiveSessionCount(): int                                  │
│ + shutdown(): void                                              │
└─────────────────────────────────────────────────────────────────┘
                                    │
                                    │ 1..*
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                         ATMSession                              │
├─────────────────────────────────────────────────────────────────┤
│ - sessionId: String                                             │
│ - user: User                                                    │
│ - startTime: LocalDateTime                                      │
│ - sessionLock: Lock                                              │
│ - currentState: ATMState                                        │
│ - authenticated: volatile boolean                              │
│ - insertedCard: volatile AtmCard                               │
│ - sessionActive: volatile boolean                              │
│ - transactionCount: int                                        │
│ - maxTransactionsPerSession: int                               │
├─────────────────────────────────────────────────────────────────┤
│ + insertCard(AtmCard): boolean                                  │
│ + authenticatePin(int): boolean                                │
│ + processTransaction(TransactionType, Object...):               │
│   TransactionResult                                              │
│ + returnCard(): boolean                                         │
│ + endSession(): void                                            │
│ + cleanup(): void                                               │
│ + getSessionId(): String                                        │
│ + getUser(): User                                                │
│ + isAuthenticated(): boolean                                    │
│ + isSessionActive(): boolean                                    │
│ + getCurrentState(): ATMState                                   │
│ + getInsertedCard(): AtmCard                                    │
│ + getTransactionCount(): int                                    │
│ + getStartTime(): LocalDateTime                                 │
│ + getSessionDurationMinutes(): long                             │
└─────────────────────────────────────────────────────────────────┘
                                    │
                                    │ 1
                                    ▼
┌─────────────────────────────────────────────────────────────────┐
│                    ConcurrentATMState                           │
├─────────────────────────────────────────────────────────────────┤
│ - stateLock: Lock                                               │
│ - stateActive: volatile boolean                                 │
├─────────────────────────────────────────────────────────────────┤
│ + insertCard(ATMSession, AtmCard): boolean                     │
│ + authenticatePin(ATMSession, int): boolean                    │
│ + processTransaction(ATMSession, TransactionType, Object...):   │
│   TransactionResult                                              │
│ + returnCard(ATMSession): boolean                               │
│ + endSession(ATMSession): void                                  │
│ + isStateActive(): boolean                                      │
│ # performInsertCard(ATMSession, AtmCard): boolean              │
│ # performAuthenticatePin(ATMSession, int): boolean             │
│ # performTransaction(ATMSession, TransactionType, Object...):    │
│   TransactionResult                                              │
│ # performReturnCard(ATMSession): boolean                        │
│ # performEndSession(ATMSession): void                           │
└─────────────────────────────────────────────────────────────────┘
                                    ▲
                                    │
                    ┌───────────────┼───────────────┐
                    │               │               │
┌───────────────────▼───┐ ┌─────────▼─────────┐ ┌──▼──────────────────┐
│ ConcurrentIdleState   │ │ConcurrentHasCardState│ │ConcurrentSelection │
│                      │ │                    │ │OptionState         │
├──────────────────────┤ ├────────────────────┤ ├────────────────────┤
│ + performInsertCard() │ │ + performInsertCard│ │ + performInsertCard│
│ + performAuthenticate │ │ + performAuthenticate│ │ + performAuthenticate│
│   Pin()               │ │   Pin()             │ │   Pin()            │
│ + performTransaction()│ │ + performTransaction│ │ + performTransaction│
│ + performReturnCard()  │ │ + performReturnCard │ │ + performReturnCard│
│ + performEndSession() │ │ + performEndSession │ │ + performEndSession│
└──────────────────────┘ └────────────────────┘ └────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    TransactionResult                            │
├─────────────────────────────────────────────────────────────────┤
│ - success: boolean                                              │
│ - message: String                                               │
│ - timestamp: long                                               │
│ - transactionId: String                                        │
├─────────────────────────────────────────────────────────────────┤
│ + TransactionResult(boolean, String)                            │
│ + TransactionResult(boolean, String, String)                    │
│ + isSuccess(): boolean                                          │
│ + getMessage(): String                                           │
│ + getTimestamp(): long                                           │
│ + getTransactionId(): String                                     │
│ + toString(): String                                             │
└─────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────┐
│                    ATMStatistics                                │
├─────────────────────────────────────────────────────────────────┤
│ - activeSessions: int                                           │
│ - totalCash: int                                                │
│ - hundredBills: int                                             │
│ - fiftyBills: int                                               │
│ - tenBills: int                                                 │
├─────────────────────────────────────────────────────────────────┤
│ + ATMStatistics(int, int, int, int, int)                       │
│ + getActiveSessions(): int                                      │
│ + getTotalCash(): int                                           │
│ + getHundredBills(): int                                         │
│ + getFiftyBills(): int                                           │
│ + getTenBills(): int                                             │
│ + toString(): String                                             │
└─────────────────────────────────────────────────────────────────┘
```

## Key Design Patterns Used

### 1. Singleton Pattern (Thread-Safe)
- **ConcurrentATM**: Double-checked locking for thread safety
- **ATMNetworkManager**: Thread-safe singleton for network management

### 2. State Pattern (Concurrent)
- **ConcurrentATMState**: Thread-safe state management
- **ConcurrentIdleState, ConcurrentHasCardState, ConcurrentSelectionOptionState**: Concrete states

### 3. Session Management Pattern
- **ATMSession**: Encapsulates user session state
- **Session Isolation**: Each user has independent session state

### 4. Load Balancing Pattern
- **ATMNetworkManager**: Manages multiple ATMs
- **Load Balancing Strategies**: Round-robin, least connections, random, capacity-based

### 5. Resource Management Pattern
- **Semaphore**: Limits concurrent users
- **ExecutorService**: Manages thread pools
- **Lock Management**: Prevents resource conflicts

## Concurrency Features

### 1. Thread Safety
- **Atomic Operations**: Cash management using AtomicInteger
- **Lock-based Synchronization**: Hardware component protection
- **Volatile Variables**: Session state management
- **ConcurrentHashMap**: Thread-safe session storage

### 2. Session Management
- **Session Isolation**: Each user has independent session
- **Session Timeout**: Automatic session cleanup
- **Transaction Limits**: Per-session transaction limits
- **Resource Cleanup**: Proper resource management

### 3. Load Balancing
- **Multiple ATMs**: Network of ATMs for scalability
- **Load Balancing Strategies**: Various algorithms for ATM selection
- **Failover Support**: Automatic failover between ATMs
- **Network Statistics**: Real-time network monitoring

### 4. Error Handling
- **Transaction Isolation**: Failed transactions don't affect others
- **Graceful Degradation**: System continues with reduced capacity
- **Resource Cleanup**: Proper cleanup on errors
- **Timeout Handling**: Session and transaction timeouts

## Benefits of Concurrent Design

1. **Scalability**: Supports multiple concurrent users
2. **Performance**: Asynchronous transaction processing
3. **Reliability**: Thread-safe operations prevent data corruption
4. **Maintainability**: Clear separation of concerns
5. **Extensibility**: Easy to add new features and ATMs
6. **Monitoring**: Comprehensive statistics and monitoring
7. **Load Balancing**: Efficient resource utilization
8. **Fault Tolerance**: Graceful handling of failures

## Usage Example

```java
// Create network manager
ATMNetworkManager networkManager = ATMNetworkManager.getInstance();

// Add ATMs to network
ConcurrentATM atm1 = ConcurrentATM.getInstance();
atm1.initializeATM(20000, 100, 150, 250);
networkManager.addATM("ATM_1", atm1);

// Create user session
String sessionId = networkManager.createSession(user).get();

// Process transaction
TransactionResult result = networkManager.processTransaction(
    sessionId, TransactionType.CASH_WITH_DRAW, 100.0).get();

// End session
networkManager.endSession(sessionId);
```

This concurrent design provides a robust, scalable, and thread-safe ATM system that can handle multiple users simultaneously while maintaining data integrity and system performance.
