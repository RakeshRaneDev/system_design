package org.byte_stroke.heap;

import java.util.Arrays;

public abstract  class Heap<T extends Comparable<T>>  implements IHash<T>{

    protected T[] heap;
    protected int position =-1;
    protected  abstract void fixUpward();
    protected  abstract void fixDownward(int endIndex);
    public Heap(){
        heap = (T[]) new Comparable[2];
    }

    @Override
    public IHash<T> insert(T data){
        if(isFull()){
            resize(2* heap.length);
        }
        heap[++position] = data;
        fixUpward();
        return this;
    }

    private boolean isFull(){
       return position == heap.length-1;
    }

    private void resize(int capacity){
        System.arraycopy(heap, 0 , heap= (T[]) new Comparable[capacity], 0, position+1);
    }

    protected void swap(int firstIndex, int secondIndex){
        T temp = heap[firstIndex];
        heap[firstIndex] = heap[secondIndex];
        heap[secondIndex] = temp;
    }

    @Override
    public T getRoot() {
        if(isEmpty()){
            return null;
        }
        T result  = heap[0];
        heap[0] = heap[position--];
        heap[position+1] = null;
        fixDownward(position);
        return result;
    }

    private boolean isEmpty(){
        return heap.length==0;
    }


    @Override
    public void sort() {
     for (int i = 0; i<=position; i++){
         swap(0, position-i);
         fixDownward(position - i-1);
     }
        Arrays.stream(heap).filter(s -> s!=null).forEach(System.out::println);
    }

}
