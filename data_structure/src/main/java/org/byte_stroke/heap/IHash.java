package org.byte_stroke.heap;

public interface  IHash <T extends Comparable<T>>{
    IHash<T> insert(T data);
    T getRoot();
    void  sort();
}
