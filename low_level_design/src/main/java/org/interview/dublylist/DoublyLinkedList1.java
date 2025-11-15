package org.interview.dublylist;

public class DoublyLinkedList1<T> {
    Node<T> head;
    Node<T> tail;
    int size;
    public DoublyLinkedList1(){
        this.size=0;
        this.head = null;
        this.tail = null;
    }

    public void  addFirst(T value){
         final Node<T> first = head;
        Node<T> newNode = new Node<>(value, first, null);
        head = newNode;
        if(first == null){
            tail = newNode;
        }else{
           first.previous = newNode;
        }
        size++;
    }

    public  void addLast(T value){
        final  Node<T> t = tail;
        Node<T> newNode = new Node<>(value, null, t);
        tail = newNode;
        if(t==null){
            head  = newNode;
        }else{
            t.next = newNode;
        }
        size++;



    }
}
