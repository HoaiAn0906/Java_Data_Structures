package org.andev.queue;

public interface QueueADT<T> extends Iterable<T> {
    void enQueue(T element);
    T deQueue();
    T peek();
    int size();
    boolean isEmpty();
}
