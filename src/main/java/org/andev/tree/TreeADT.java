package org.andev.tree;

import java.util.Iterator;

public interface TreeADT<T> {
    boolean isEmpty();
    int size();
    int height();
    boolean contains(T element);
    boolean add(T element);
    boolean remove(T element);
    Iterator<T> traverse(TreeTraverseType traverseType);
}
