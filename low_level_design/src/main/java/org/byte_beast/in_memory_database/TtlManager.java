package org.byte_beast.in_memory_database;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class TtlManager {
    private final Map<String, DataBaseEntry> data;
    private final ScheduledExecutorService scheduler;
    private final AtomicBoolean isRunning;
    private final long cleanDurationInSeconds;

    public TtlManager(Map<String, DataBaseEntry> data) {
        this(data, 60l);
    }

    public TtlManager(Map<String, DataBaseEntry> data, long cleanDurationInSeconds) {
        this.data = data;
        this.cleanDurationInSeconds = cleanDurationInSeconds;
        this.scheduler = Executors.newSingleThreadScheduledExecutor(
                r -> {
                    Thread thread = new Thread(r, "clean-up");
                    thread.setDaemon(true);
                    return thread;
                }
        );
        this.isRunning = new AtomicBoolean(false);

    }

    public void start(){
        if(isRunning.compareAndSet(false, true)){
            scheduler.scheduleWithFixedDelay(
                    this::cleatExistingEntries,
                    cleanDurationInSeconds,
                    cleanDurationInSeconds,
                    TimeUnit.SECONDS
            );
        }

    }

    public void stop() {
        if(isRunning.compareAndSet(true, false)){
            scheduler.shutdown();
            try{
                if(!scheduler.awaitTermination(5, TimeUnit.SECONDS)){
                    scheduler.shutdown();
                }
            }catch (InterruptedException e){
                scheduler.shutdown();
                Thread.currentThread().interrupt();

            }
        }
    }

    public int cleatExistingEntries(){
        int count = 0;
        for(Map.Entry<String, DataBaseEntry> entry: data.entrySet()){
            DataBaseEntry dataBaseEntry = entry.getValue();
            if(dataBaseEntry.isExpired()){
                count++;
                data.remove(entry.getKey());
            }
        }
        return count;
    }

}
