package org.byte_beast.in_memory_database;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RadisDatabase {

    private final Map<String, DataBaseEntry> data;
    private final ReadWriteLock lock;

    private final String name;

    private final TtlManager ttlManager;

    private volatile boolean isStarted;

    public RadisDatabase() {
        this("radis");
    }

    public RadisDatabase(String name) {
        this.data = new ConcurrentHashMap<>();
        this.lock = new ReentrantReadWriteLock();
        this.ttlManager = new TtlManager(data);
        this.name = name;
        this.isStarted = false;
    }

    /**
     * start the database operation like ttl clean
     */
    public void start(){
        if(!isStarted){
            ttlManager.stop();
            isStarted = true;
        }
    }

    /**
     * stop the databases
     */
    public void stop(){
        if(isStarted){
            ttlManager.stop();
            isStarted = false;
        }
    }

     // ======CRUD operation=======
     public boolean create(String key, Object value){
         return create(key ,value, null, null);
     }

    public boolean create(String key, Object value, Long ttl){
        return create(key ,value, ttl, null);
    }

    public boolean create(String key, Object value, Long ttl, Map<String , Object> metadata){
        if(key ==null || value == null){
            return false;
        }
        lock.writeLock().lock();
        try {
            if(data.containsKey(key)){
                 return false;
            }
            DataBaseEntry entry = new DataBaseEntry(key, value, ttl, metadata);
            data.put(key, entry);
            return true;
        }finally {
            lock.writeLock().unlock();
        }
    }

    public boolean upsert(String key, Object value){
        return upsert(key, value, null, null);
    }

    public boolean upsert(String key, Object value, Long ttl){
        return upsert(key, value, ttl, null);
    }


    public boolean upsert(String key, Object value, Long ttl, Map<String , Object> metadata){
        if(key==null || value==null){
            return false;
        }
        lock.writeLock().lock();
        try {

            DataBaseEntry entry = new DataBaseEntry(key, value, ttl, metadata);
            data.put(key, entry);
            return  true;
        }finally {
            lock.writeLock().unlock();
        }
    }

    public Object read(String key){
        if(key ==null){
            return null;
        }
        lock.readLock().lock();
        try{
            DataBaseEntry entry = data.get(key);
            if(entry==null){
                return null;
            }
            if(entry.isExpired()){
                data.remove(key);
                return null;
            }
            return entry.getValue();

        }finally {
            lock.readLock().unlock();
        }
    }

    public DataBaseEntry readEntry(String key){
        if(key ==null){
            return null;
        }
        lock.readLock().lock();
        try{
            DataBaseEntry entry = data.get(key);
            if(entry==null){
                return null;
            }
            if(entry.isExpired()){
                data.remove(key);
                return null;
            }
            return entry;

        }finally {
            lock.readLock().unlock();
        }
    }

    public <T> T read(String key, Class<T> clazz){
        Object value = read(key);
        if(value!=null && clazz.isAssignableFrom(value.getClass())){
            return (T) value;
        }
        return null;
    }
    public boolean update(String key, Object value){
        return update( key, value, null, null);
    }
    public boolean update(String key, Object value, Long ttl){
        return update( key, value, ttl, null);
    }

    public boolean update(String key, Object value, Long ttl, Map<String , Object> metadata){
        if(key ==null|| value ==null){
            return false;
        }
        lock.writeLock().lock();
        try {
            if(!data.containsKey(key)){
                return false;
            }
            DataBaseEntry entry = data.get(key);
            if(entry==null || entry.isExpired()){
                return false;
            }

            DataBaseEntry newEntry = new DataBaseEntry(key, value, ttl, metadata);
            data.put(key, newEntry);
            return true;

        }finally {
            lock.writeLock().unlock();
        }
    }

    // delete opration

    public boolean delete(String key){
        if(key==null){
            return false;
        }
        lock.writeLock().lock();
        try {
            return data.remove(key)!=null;

        }finally {
            lock.writeLock().unlock();
        }
    }

    public int delete(Collection<String> keys){
        if(keys.isEmpty()){
            return 0;
        }
        lock.writeLock().lock();

        try {
            int count = 0;
            for(String key:keys){
               if (data.remove(key)!=null){
                   count++;
               }
            }
            return count;
        }finally {
            lock.writeLock().unlock();
        }
    }

//================ Advanced Filtring and serach ========
    public List<DataBaseEntry> find(QueryCondition condition){
        return find (Collections.singletonList(condition), null, null);
    }

    public List<DataBaseEntry> find(List<QueryCondition> conditions){
        return find(conditions, null,null);
    }

    public List<DataBaseEntry> find(Predicate<DataBaseEntry> predicate){
        return find(Collections.singletonList(new QueryCondition(predicate)), null,null);
    }





    public List<DataBaseEntry> find(List<QueryCondition> conditions, Integer limit, Integer offset ){
        lock.readLock().lock();
        try {
            Predicate<DataBaseEntry> combinePredicate =
                    conditions.stream().map(QueryCondition::getPredicate)
                                    .reduce(Predicate::and).
                            orElse(entry-> true);
            List<DataBaseEntry> result = data.values().stream()
                    .filter(dataBaseEntry -> !dataBaseEntry.isExpired())
                    .filter(combinePredicate).toList();
            if(offset!= null && offset>0){
                result = result.stream().skip(offset).collect(Collectors.toList());
            }
            if(limit !=null && limit>0){
                result = result.stream().limit(limit).collect(Collectors.toList());
            }

            return result;

        }finally {
            lock.readLock().unlock();
        }

    }

    public Long getRemainingTtl(String key){
        if(key==null){
            return -1l;
        }
        DataBaseEntry entry = data.get(key);
        if(entry==null){
            return -1l;
        }
        return entry.getRemainingTtl();

    }



}
