# In-Memory Database System

A comprehensive, thread-safe in-memory database implementation with advanced features including CRUD operations, TTL support, advanced filtering/search, and backup functionality.

## Features

### Core Features
- **Thread-safe operations** with read-write locks
- **CRUD operations** (Create, Read, Update, Delete)
- **Upsert operations** (Create or Update)
- **Batch operations** for multiple keys
- **Type-safe reads** with automatic casting

### Advanced Features
- **TTL (Time To Live) support** with automatic expiration
- **Advanced filtering and search** with multiple operators
- **Metadata support** for custom attributes
- **Backup and restore** functionality with compression
- **Pagination support** for large result sets
- **Time-based queries** for temporal data
- **Performance monitoring** and statistics

## Quick Start

```java
// Create and start database
InMemoryDatabase db = new InMemoryDatabase("my-db");
db.start();

// Basic operations
db.create("key1", "value1");
String value = (String) db.read("key1");
db.update("key1", "new value");
db.delete("key1");

// With TTL
db.create("session:123", "user_data", 300L); // 5 minutes TTL

// Advanced search
List<DatabaseEntry> results = db.search("search_term");

// Backup
String backupPath = db.createBackup();
db.restoreFromBackup(backupPath);

// Cleanup
db.stop();
```

## API Reference

### Database Operations

#### Create Operations
```java
// Basic create
boolean create(String key, Object value)

// Create with TTL
boolean create(String key, Object value, Long ttlSeconds)

// Create with TTL and metadata
boolean create(String key, Object value, Long ttlSeconds, Map<String, Object> metadata)
```

#### Read Operations
```java
// Basic read
Object read(String key)

// Type-safe read
<T> T read(String key, Class<T> type)

// Read full entry
DatabaseEntry readEntry(String key)
```

#### Update Operations
```java
// Basic update
boolean update(String key, Object value)

// Update with TTL
boolean update(String key, Object value, Long ttlSeconds)

// Update with TTL and metadata
boolean update(String key, Object value, Long ttlSeconds, Map<String, Object> metadata)
```

#### Upsert Operations
```java
// Upsert (create or update)
boolean upsert(String key, Object value)
boolean upsert(String key, Object value, Long ttlSeconds)
boolean upsert(String key, Object value, Long ttlSeconds, Map<String, Object> metadata)
```

#### Delete Operations
```java
// Delete single key
boolean delete(String key)

// Delete multiple keys
int deleteAll(Collection<String> keys)
```

### Advanced Filtering and Search

#### Query Conditions
```java
// Create conditions
QueryCondition condition = new QueryCondition("field", value, ComparisonOperator.EQUALS);

// Available operators
enum ComparisonOperator {
    EQUALS, NOT_EQUALS,
    GREATER_THAN, GREATER_THAN_OR_EQUALS,
    LESS_THAN, LESS_THAN_OR_EQUALS,
    CONTAINS, STARTS_WITH, ENDS_WITH,
    REGEX
}
```

#### Search Operations
```java
// Find by single condition
List<DatabaseEntry> find(QueryCondition condition)

// Find by multiple conditions (AND)
List<DatabaseEntry> find(List<QueryCondition> conditions)

// Find with pagination
List<DatabaseEntry> find(List<QueryCondition> conditions, Integer limit, Integer offset)

// Text search
List<DatabaseEntry> search(String searchTerm)
List<DatabaseEntry> searchByKey(String keyPattern)

// Time-based queries
List<DatabaseEntry> findByCreatedAtRange(Instant startTime, Instant endTime)
List<DatabaseEntry> findByExpirationRange(Instant startTime, Instant endTime)
```

#### Custom Predicates
```java
// Custom filtering
List<DatabaseEntry> find(Predicate<DatabaseEntry> predicate)

// Example: Find entries with specific metadata
List<DatabaseEntry> results = db.find(entry -> 
    entry.getMetadata() != null && 
    "admin".equals(entry.getMetadata().get("role"))
);
```

### TTL (Time To Live) Operations

```java
// Set TTL for existing entry
boolean setTTL(String key, long ttlSeconds)

// Extend TTL
boolean extendTTL(String key, long additionalSeconds)

// Remove TTL (make permanent)
boolean removeTTL(String key)

// Check remaining TTL
long getRemainingTTL(String key)

// Check if expired
boolean isExpired(String key)

// Manual cleanup
int cleanupExpiredEntries()
```

### Backup and Restore

```java
// Create backup
String backupPath = db.createBackup()

// Restore from backup
boolean restored = db.restoreFromBackup(String backupPath)

// List available backups
String[] backups = db.listBackups()

// Cleanup old backups
db.cleanupOldBackups(int keepCount)
```

### Utility Operations

```java
// Database info
int size()
boolean isEmpty()
boolean exists(String key)
Set<String> keys()
List<Object> values()

// Clear database
void clear()

// Statistics
DatabaseStats getStats()
```

## Configuration

### Database Configuration
```java
// Basic configuration
InMemoryDatabase db = new InMemoryDatabase("database-name");

// With custom backup directory
InMemoryDatabase db = new InMemoryDatabase("database-name", "./custom-backups");
```

### TTL Configuration
```java
// Default cleanup interval: 60 seconds
TTLManager ttlManager = new TTLManager(data);

// Custom cleanup interval
TTLManager ttlManager = new TTLManager(data, 30); // 30 seconds
```

### Backup Configuration
```java
// Default: compression enabled, "./backups" directory
BackupManager backupManager = new BackupManager();

// Custom configuration
BackupManager backupManager = new BackupManager("./custom-backups", false); // No compression
```

## Examples

### User Management System
```java
InMemoryDatabase userDb = new InMemoryDatabase("users");
userDb.start();

// Create user with metadata
Map<String, Object> userMeta = new HashMap<>();
userMeta.put("email", "john@example.com");
userMeta.put("role", "admin");
userMeta.put("lastLogin", Instant.now());

userDb.create("user:123", "John Doe", 3600L, userMeta); // 1 hour TTL

// Find admin users
List<DatabaseEntry> admins = userDb.find(
    new QueryCondition("role", "admin", QueryCondition.ComparisonOperator.EQUALS)
);

// Search users by email
List<DatabaseEntry> users = userDb.find(entry -> 
    entry.getMetadata() != null && 
    entry.getMetadata().get("email").toString().contains("@example.com")
);
```

### Session Management
```java
InMemoryDatabase sessionDb = new InMemoryDatabase("sessions");
sessionDb.start();

// Create session with TTL
sessionDb.create("session:abc123", "user_data", 1800L); // 30 minutes

// Extend session
sessionDb.extendTTL("session:abc123", 1800L); // Another 30 minutes

// Find expired sessions
List<DatabaseEntry> expired = sessionDb.find(entry -> entry.isExpired());
```

### Caching System
```java
InMemoryDatabase cache = new InMemoryDatabase("cache");
cache.start();

// Cache with TTL
cache.create("cache:user:123", userData, 300L); // 5 minutes

// Cache hit/miss
Object cached = cache.read("cache:user:123");
if (cached == null) {
    // Cache miss - fetch from source
    Object data = fetchFromSource();
    cache.create("cache:user:123", data, 300L);
}
```

### Analytics and Monitoring
```java
// Track user activity
Map<String, Object> activityMeta = new HashMap<>();
activityMeta.put("userId", "123");
activityMeta.put("action", "login");
activityMeta.put("timestamp", Instant.now());
activityMeta.put("ip", "192.168.1.1");

db.create("activity:" + UUID.randomUUID(), "login_event", 86400L, activityMeta);

// Find recent activities
Instant oneHourAgo = Instant.now().minusSeconds(3600);
List<DatabaseEntry> recentActivities = db.findByCreatedAtRange(oneHourAgo, Instant.now());

// Find activities by user
List<DatabaseEntry> userActivities = db.find(
    new QueryCondition("userId", "123", QueryCondition.ComparisonOperator.EQUALS)
);
```

## Performance Considerations

### Memory Usage
- Each entry stores key, value, timestamps, and metadata
- TTL entries have additional expiration tracking
- Consider memory limits for large datasets

### Concurrency
- Read operations use shared locks (multiple concurrent reads)
- Write operations use exclusive locks
- TTL cleanup runs in background thread

### Backup Performance
- Backups are compressed by default
- Large datasets may take time to backup/restore
- Consider incremental backups for production use

## Thread Safety

The database is fully thread-safe:
- All operations are synchronized using read-write locks
- Multiple readers can access simultaneously
- Writers have exclusive access
- TTL cleanup runs in separate thread

## Error Handling

```java
try {
    // Database operations
    db.create("key", "value");
} catch (Exception e) {
    // Handle errors appropriately
    System.err.println("Database operation failed: " + e.getMessage());
}
```

## Best Practices

1. **Always start the database** before use
2. **Stop the database** when done to cleanup resources
3. **Use appropriate TTL** for temporary data
4. **Regular backups** for important data
5. **Monitor memory usage** for large datasets
6. **Use metadata** for complex queries
7. **Batch operations** for better performance
8. **Handle exceptions** appropriately

## Limitations

- Data is stored in memory (not persistent across restarts)
- Limited by available RAM
- No ACID transactions
- No complex query language (SQL-like)
- No indexing (linear search for complex queries)

## Future Enhancements

- Persistent storage options
- Indexing for better search performance
- Transaction support
- Clustering and replication
- Query optimization
- Memory-mapped files for large datasets
