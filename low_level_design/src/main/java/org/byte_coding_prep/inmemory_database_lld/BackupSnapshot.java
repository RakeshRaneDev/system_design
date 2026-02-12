package org.byte_coding_prep.inmemory_database_lld;

import java.util.List;
import java.util.Map;

public class BackupSnapshot {
    private final Map<String, List<Item>> snapshot;

    public BackupSnapshot(Map<String, List<Item>> snapshot) {
        this.snapshot = snapshot;
    }

    public Map<String, List<Item>> getSnapshot() {
        return snapshot;
    }
}
