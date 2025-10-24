# In-Memory Database System - Class Diagram

## System Architecture

```mermaid
classDiagram  
    class InMemoryDatabase {  
        -Map<String, DatabaseEntry> data  
        -ReadWriteLock lock  
        -TTLManager ttlManager  
        -BackupManager backupManager  
        -String name  
        -boolean isStarted  
        +start(): void  
        +stop(): void  
        +create(key, value): boolean  
        +create(key, value, ttl): boolean  
        +create(key, value, ttl, metadata): boolean  
        +read(key): Object  
        +read(key, type): T  
        +readEntry(key): DatabaseEntry  
        +update(key, value): boolean  
        +update(key, value, ttl): boolean  
        +update(key, value, ttl, metadata): boolean  
        +upsert(key, value): boolean  
        +upsert(key, value, ttl): boolean  
        +upsert(key, value, ttl, metadata): boolean  
        +delete(key): boolean  
        +deleteAll(keys): int  
        +find(condition): List<DatabaseEntry>  
        +find(conditions): List<DatabaseEntry>  
        +find(conditions, limit, offset): List<DatabaseEntry>  
        +search(term): List<DatabaseEntry>  
        +searchByKey(pattern): List<DatabaseEntry>  
        +findByCreatedAtRange(start, end): List<DatabaseEntry>  
        +findByExpirationRange(start, end): List<DatabaseEntry>  
        +setTTL(key, ttl): boolean  
        +extendTTL(key, additional): boolean  
        +removeTTL(key): boolean  
        +getRemainingTTL(key): long  
        +isExpired(key): boolean  
        +createBackup(): String  
        +restoreFromBackup(path): boolean  
        +listBackups(): String[]  
        +cleanupOldBackups(keepCount): void  
        +size(): int  
        +isEmpty(): boolean  
        +exists(key): boolean  
        +keys(): Set<String>  
        +values(): List<Object>  
        +clear(): void  
        +getStats(): DatabaseStats  
    }  
  
    class DatabaseEntry {  
        -String key  
        -Object value  
        -Instant createdAt  
        -Instant expiresAt  
        -Map<String, Object> metadata  
        -Instant lastAccessedAt  
        +getKey(): String  
        +getValue(): Object  
        +getCreatedAt(): Instant  
        +getExpiresAt(): Instant  
        +getMetadata(): Map<String, Object>  
        +getLastAccessedAt(): Instant  
        +isExpired(): boolean  
        +getRemainingTTL(): long  
    }  
  
    class QueryCondition {  
        -String field  
        -Object value  
        -ComparisonOperator operator  
        -Predicate<DatabaseEntry> predicate  
        +getField(): String  
        +getValue(): Object  
        +getOperator(): ComparisonOperator  
        +getPredicate(): Predicate<DatabaseEntry>  
    }  
  
    class TTLManager {  
        -Map<String, DatabaseEntry> data  
        -ScheduledExecutorService scheduler  
        -AtomicBoolean isRunning  
        -long cleanupIntervalSeconds  
        +start(): void  
        +stop(): void  
        +cleanupExpiredEntries(): int  
        +isEntryExpired(key): boolean  
        +getRemainingTTL(key): long  
        +extendTTL(key, additional): boolean  
        +setTTL(key, ttl): boolean  
        +removeTTL(key): boolean  
        +getTTLStats(): TTLStats  
    }  
  
    class BackupManager {  
        -String backupDirectory  
        -boolean compressionEnabled  
        +createBackup(data): String  
        +restoreFromBackup(filepath): Map<String, DatabaseEntry>  
        +listBackups(): String[]  
        +cleanupOldBackups(keepCount): void  
    }  
  
    class ComparisonOperator {  
        <<enumeration>>  
        EQUALS  
        NOT_EQUALS  
        GREATER_THAN  
        GREATER_THAN_OR_EQUALS  
        LESS_THAN  
        LESS_THAN_OR_EQUALS  
        CONTAINS  
        STARTS_WITH  
        ENDS_WITH  
        REGEX  
    }  
  
    class DatabaseStats {  
        -String name  
        -int totalEntries  
        -int expiredEntries  
        -int ttlEntries  
        -boolean isStarted  
        +getName(): String  
        +getTotalEntries(): int  
        +getExpiredEntries(): int  
        +getTtlEntries(): int  
        +isStarted(): boolean  
    }  
  
    class TTLStats {  
        -int totalEntries  
        -int expiredEntries  
        -int ttlEntries  
        -long totalRemainingTTL  
        +getTotalEntries(): int  
        +getExpiredEntries(): int  
        +getTtlEntries(): int  
        +getTotalRemainingTTL(): long  
    }  
  
    %% Relationships  
    InMemoryDatabase --> DatabaseEntry: stores  
    InMemoryDatabase --> TTLManager: manages  
    InMemoryDatabase --> BackupManager: uses  
    InMemoryDatabase --> QueryCondition: uses for filtering  
    InMemoryDatabase --> DatabaseStats: returns  
    TTLManager --> DatabaseEntry: manages TTL for  
    TTLManager --> TTLStats: returns  
    QueryCondition --> ComparisonOperator: uses  
    BackupManager --> DatabaseEntry: serializes/deserializes  
  
    %% Notes  
    note for InMemoryDatabase "Main database class with\nthread-safe operations"  
    note for DatabaseEntry "Represents a database entry\nwith value, metadata, and TTL"  
    note for TTLManager "Manages automatic expiration\nof entries with TTL"  
    note for BackupManager "Handles backup and restore\noperations with compression"  
    note for QueryCondition "Provides flexible filtering\nand search capabilities"  
```

## Key Components

### 1. InMemoryDatabase
- **Main database class** providing all CRUD operations
- **Thread-safe** with read-write locks
- **Integrates** TTL management and backup functionality
- **Supports** advanced filtering and search operations

### 2. DatabaseEntry
- **Represents** a single database entry
- **Contains** key, value, timestamps, and metadata
- **Supports** TTL with automatic expiration checking
- **Tracks** access times for analytics

### 3. TTLManager
- **Manages** Time To Live functionality
- **Runs** background cleanup of expired entries
- **Provides** TTL operations (set, extend, remove)
- **Thread-safe** with scheduled executor

### 4. BackupManager
- **Handles** backup creation and restoration
- **Supports** compression for space efficiency
- **Manages** backup file lifecycle
- **Provides** backup listing and cleanup

### 5. QueryCondition
- **Enables** flexible filtering and search
- **Supports** multiple comparison operators
- **Allows** custom predicates for complex queries
- **Provides** type-safe query building

## Data Flow

```mermaid
sequenceDiagram
    participant Client
    participant Database
    participant TTLManager
    participant BackupManager

    Client->>Database: create(key, value, ttl)
    Database->>Database: store entry with TTL
    Database->>TTLManager: register for cleanup

    Client->>Database: read(key)
    Database->>Database: check expiration
    alt Entry expired
        Database->>Database: remove entry
        Database-->>Client: null
    else Entry valid
        Database-->>Client: value
    end

    TTLManager->>TTLManager: scheduled cleanup
    TTLManager->>Database: remove expired entries

    Client->>Database: createBackup()
    Database->>BackupManager: serialize data
    BackupManager-->>Database: backup path
    Database-->>Client: backup path

    Client->>Database: restoreFromBackup(path)
    Database->>BackupManager: deserialize data
    BackupManager-->>Database: restored data
    Database->>Database: replace current data
```

## Thread Safety Model

```mermaid
graph TD
    A[Client Request] --> B{Operation Type}
    B -->|Read| C[Acquire Read Lock]
    B -->|Write| D[Acquire Write Lock]
    C --> E[Multiple Readers Allowed]
    D --> F[Exclusive Writer Access]
    E --> G[Execute Read Operation]
    F --> H[Execute Write Operation]
    G --> I[Release Read Lock]
    H --> J[Release Write Lock]
    I --> K[Return Result]
    J --> K
    K --> L[Client Response]

    M[TTL Cleanup Thread] --> N[Background Cleanup]
    N --> O[Remove Expired Entries]
    O --> P[Update Statistics]
```

## Memory Layout

```
InMemoryDatabase
├── ConcurrentHashMap<String, DatabaseEntry> data
├── ReentrantReadWriteLock lock
├── TTLManager ttlManager
└── BackupManager backupManager

DatabaseEntry
├── String key
├── Object value
├── Instant createdAt
├── Instant expiresAt
├── Map<String, Object> metadata
└── Instant lastAccessedAt

TTLManager
├── ScheduledExecutorService scheduler
├── AtomicBoolean isRunning
└── long cleanupIntervalSeconds

BackupManager
├── String backupDirectory
└── boolean compressionEnabled
```

## Performance Characteristics

- **Read Operations**: O(1) average case
- **Write Operations**: O(1) average case
- **Search Operations**: O(n) linear scan
- **TTL Cleanup**: O(n) periodic cleanup
- **Backup Operations**: O(n) serialization
- **Memory Usage**: O(n) where n is number of entries

## Scalability Considerations

- **Memory Bound**: Limited by available RAM
- **Concurrent Reads**: Multiple readers supported
- **Concurrent Writes**: Single writer at a time
- **TTL Overhead**: Background thread for cleanup
- **Backup Impact**: Blocks writes during backup
