package org.byte_stroke.inmemory_database;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Manages backup and restore operations for the in-memory database
 */
public class BackupManager {
    private final String backupDirectory;
    private final boolean compressionEnabled;

    public BackupManager() {
        this("./backups", true);
    }

    public BackupManager(String backupDirectory, boolean compressionEnabled) {
        this.backupDirectory = backupDirectory;
        this.compressionEnabled = compressionEnabled;
        createBackupDirectory();
    }

    private void createBackupDirectory() {
        try {
            Path path = Paths.get(backupDirectory);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create backup directory: " + backupDirectory, e);
        }
    }

    /**
     * Creates a backup of the database
     */
    public String createBackup(Map<String, DatabaseEntry> data) {
        String timestamp = Instant.now().toString().replace(":", "-");
        String filename = String.format("backup_%s.%s", timestamp, compressionEnabled ? "gz" : "dat");
        String filepath = Paths.get(backupDirectory, filename).toString();

        try (ObjectOutputStream oos = createOutputStream(filepath)) {
            oos.writeObject(data);
            return filepath;
        } catch (IOException e) {
            throw new RuntimeException("Failed to create backup: " + filepath, e);
        }
    }

    /**
     * Restores the database from a backup file
     */
    @SuppressWarnings("unchecked")
    public Map<String, DatabaseEntry> restoreFromBackup(String filepath) {
        try (ObjectInputStream ois = createInputStream(filepath)) {
            Object data = ois.readObject();
            if (data instanceof Map) {
                return (Map<String, DatabaseEntry>) data;
            } else {
                throw new RuntimeException("Invalid backup file format");
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to restore from backup: " + filepath, e);
        }
    }

    /**
     * Lists all available backup files
     */
    public String[] listBackups() {
        try {
            return Files.list(Paths.get(backupDirectory))
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .filter(name -> name.startsWith("backup_"))
                    .sorted()
                    .toArray(String[]::new);
        } catch (IOException e) {
            throw new RuntimeException("Failed to list backups", e);
        }
    }

    /**
     * Deletes old backup files, keeping only the most recent ones
     */
    public void cleanupOldBackups(int keepCount) {
        try {
            String[] backups = listBackups();
            if (backups.length > keepCount) {
                for (int i = 0; i < backups.length - keepCount; i++) {
                    Path filepath = Paths.get(backupDirectory, backups[i]);
                    Files.deleteIfExists(filepath);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to cleanup old backups", e);
        }
    }

    private ObjectOutputStream createOutputStream(String filepath) throws IOException {
        FileOutputStream fos = new FileOutputStream(filepath);
        if (compressionEnabled) {
            GZIPOutputStream gzos = new GZIPOutputStream(fos);
            return new ObjectOutputStream(gzos);
        } else {
            return new ObjectOutputStream(fos);
        }
    }

    private ObjectInputStream createInputStream(String filepath) throws IOException {
        FileInputStream fis = new FileInputStream(filepath);
        if (compressionEnabled && filepath.endsWith(".gz")) {
            GZIPInputStream gzis = new GZIPInputStream(fis);
            return new ObjectInputStream(gzis);
        } else {
            return new ObjectInputStream(fis);
        }
    }
}
