package org.andev.stack;

public interface StackADT<T> extends Iterable<T> {
    void push(T element);
    T pop();
    T top();
    int size();
    boolean isEmpty();
}
