package org.byte_coding_prep.cloud_storage;

import java.util.Objects;

public class File {
    String name;
    int size;
    String userId;

    File(String name, int size) {
        this(name, size, "admin");
    }

    File(String name, int size, String userId) {
        this.name = name;
        this.size = size;
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, size, userId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof File)) return false;
        File o = (File) obj;
        return name.equals(o.name) && size == o.size && userId.equals(o.userId);
    }
}
