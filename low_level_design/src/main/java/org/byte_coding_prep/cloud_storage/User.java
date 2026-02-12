package org.byte_coding_prep.cloud_storage;

import java.util.HashSet;
import java.util.Set;

public class User {
    String userId;
    int capacity;
    Set<File> files = new HashSet<>();
    Set<File> backup = null;

    User(String userId, int capacity) {
        this.userId = userId;
        this.capacity = capacity;
    }
}
