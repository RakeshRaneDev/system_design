package org.byte_beast.in_memory_database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BackManager {
    private final String backupDirectory;
    private final boolean isCompressed;
    public BackManager() {
        this("./backup", true);
    }

    public BackManager(String backupDirectory, boolean isCompressed) {
        this.backupDirectory = backupDirectory;
        this.isCompressed = isCompressed;
        createDirectory();
    }

    private void createDirectory(){
        Path path = Paths.get(backupDirectory);
        try {
            if(!Files.exists(path)){
                Files.createDirectory(path);
            }
        }catch (IOException e){
            throw  new RuntimeException("exception while cerate file path");
        }

    }

}
