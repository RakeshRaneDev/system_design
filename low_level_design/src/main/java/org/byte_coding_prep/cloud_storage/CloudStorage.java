package org.byte_coding_prep.cloud_storage;

import java.util.*;

public class CloudStorage {
    private static final String FALSE = "false";
    private static final String TRUE = "true";

    // Storage: filename -> File
    private final Map<String, File> storage = new HashMap<>();

    // Users: userId -> User
    private final Map<String, User> users = new HashMap<>();

    public CloudStorage() {
        // admin having unlimited capacity (0 means not restricted)
        users.put("admin", new User("admin", 0));
    }

    // -------------------------
    // add_file
    // -------------------------
    public String addFile(String name, String sizeStr) {
        if (storage.containsKey(name))
            return FALSE;

        int size = Integer.parseInt(sizeStr);
        File f = new File(name, size);
        storage.put(name, f);

        users.get("admin").files.add(f);

        return TRUE;
    }

    // -------------------------
    // add_user
    // -------------------------
    public String addUser(String userId, String capacityStr) {
        if (users.containsKey(userId))
            return FALSE;

        int capacity = Integer.parseInt(capacityStr);
        users.put(userId, new User(userId, capacity));

        return TRUE;
    }

    // -------------------------
    // merge_user
    // -------------------------
    public String mergeUser(String userId1, String userId2) {
        if (!users.containsKey(userId1) || !users.containsKey(userId2))
            return "";
        if (userId1.equals(userId2))
            return "";

        User user1 = users.get(userId1);
        User user2 = users.get(userId2);

        user1.files.addAll(user2.files);
        user1.capacity += user2.capacity;

        users.remove(userId2);

        return String.valueOf(user1.capacity);
    }

    // -------------------------
    // add_file_by
    // -------------------------
    public String addFileBy(String userId, String name, String sizeStr) {
        if (storage.containsKey(name))
            return "";

        User user = users.get(userId);
        int size = Integer.parseInt(sizeStr);

        if (user.capacity < size)
            return "";

        File f = new File(name, size, userId);
        storage.put(name, f);

        user.capacity -= size;
        user.files.add(f);

        return String.valueOf(user.capacity);
    }

    // -------------------------
    // get_file_size
    // -------------------------
    public String getFileSize(String name) {
        if (storage.containsKey(name))
            return String.valueOf(storage.get(name).size);

        return "";
    }

    // -------------------------
    // delete_file
    // -------------------------
    public String deleteFile(String name) {
        if (!storage.containsKey(name))
            return "";

        File f = storage.get(name);
        int size = f.size;
        String userId = f.userId;

        User user = users.get(userId);
        user.capacity += size;
        user.files.remove(f);

        storage.remove(name);

        return String.valueOf(size);
    }

    // -------------------------
    // n_largest(prefix, n)
    // -------------------------
    public String nLargest(String prefix, String nStr) {
        int n = Integer.parseInt(nStr);

        List<File> result = new ArrayList<>();
        for (File f : storage.values()) {
            if (f.name.startsWith(prefix))
                result.add(f);
        }

        if (result.isEmpty())
            return "";

        // Sort: size desc, then name asc
        result.sort((a, b) -> {
            if (b.size != a.size)
                return b.size - a.size;
            return a.name.compareTo(b.name);
        });

        StringBuilder sb = new StringBuilder();
        int limit = Math.min(n, result.size());

        for (int i = 0; i < limit; i++) {
            File f = result.get(i);
            sb.append(f.name).append("(").append(f.size).append(")");
            if (i < limit - 1) sb.append(", ");
        }

        return sb.toString();
    }

    // -------------------------
    // backup(user)
    // -------------------------
    public String backup(String userId) {
        if (!users.containsKey(userId))
            return "";

        User user = users.get(userId);
        user.backup = new HashSet<>(user.files);

        return String.valueOf(user.files.size());
    }

    // -------------------------
    // restore(user)
    // -------------------------
    public String restore(String userId) {
        if (!users.containsKey(userId))
            return "";

        User user = users.get(userId);

        // delete all existing files of this user
        for (File f : new HashSet<>(user.files)) {
            deleteFile(f.name);
        }

        if (user.backup == null)
            return "0";

        int cnt = 0;

        for (File f : user.backup) {
            if (!storage.containsKey(f.name)) {
                addFileBy(userId, f.name, String.valueOf(f.size));
                cnt++;
            }
        }

        return String.valueOf(cnt);
    }
}
