package org.andev.hashtable;

import org.andev.linkedlist.DefaultDoublyLinkedList;
import org.andev.linkedlist.DoublyLinkedList;

import java.util.Arrays;
import java.util.Iterator;

public class SeparateChainingHashTable<K,V> implements HashTableADT<K,V>{
    private static final int DEFAULT_CAPACITY = 10;
    private static final double DEFAULT_LOAD_FACTOR = 0.5;
    
    private final double loadFactor;
    private int capacity, threshold, size = 0;
    private DoublyLinkedList<Node<K,V>>[] table;

    public SeparateChainingHashTable() {
        this(DEFAULT_LOAD_FACTOR, DEFAULT_CAPACITY);
    }

    public SeparateChainingHashTable(int capacity) {
        this(DEFAULT_LOAD_FACTOR, capacity);
    }

    public SeparateChainingHashTable(double loadFactor, int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Illegal capacity");
        if (loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
            throw new IllegalArgumentException("Illegal load factor");
        
        this.loadFactor = loadFactor;
        this.capacity = capacity;
        this.threshold = (int) (this.capacity * this.loadFactor);
        table = new DefaultDoublyLinkedList[this.capacity];
    }

    
    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int hashCodeToIndex(int hashedKey) {
        return (int) ((hashedKey & 0xFFFFFFFFFL) % capacity);
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
    }

    @Override
    public boolean has(K key) {
        int index = hashCodeToIndex(key.hashCode());
        DoublyLinkedList<Node<K,V>> doublyLinkedList = table[index];
        if (doublyLinkedList == null) return false;
        
        Iterator<Node<K,V>> iterator = doublyLinkedList.iterator();
        
        while (iterator.hasNext()) {
            Node<K,V> node = iterator.next();
            if (node.getKey().equals(key)) {
                return true;
            }
        }
        
        return false;
    }

    @Override
    public V get(K key) {
        int index = hashCodeToIndex(key.hashCode());
        DoublyLinkedList<Node<K,V>> doublyLinkedList = table[index];
        
        if (doublyLinkedList == null) return null;
        
        Iterator<Node<K,V>> iterator = doublyLinkedList.iterator();
        
        while (iterator.hasNext()) {
            Node<K,V> node = iterator.next();
            if (node.getKey().equals(key)) {
                return node.getValue();
            }
        }
        
        return null;
    }

    @Override
    public V insert(K key, V value) {
        int index = hashCodeToIndex(key.hashCode());
        DoublyLinkedList<Node<K,V>> doublyLinkedList = table[index];
        if (doublyLinkedList == null) {
            table[index] = doublyLinkedList = new DefaultDoublyLinkedList<>();
        }
        
        Node<K,V> existedNode = null;
        Iterator<Node<K,V>> iterator = doublyLinkedList.iterator();
        
        while (iterator.hasNext()) {
            Node<K,V> node = iterator.next();
            if (node.getKey().equals(key)) {
                existedNode = node;
            }
        }
        
        if (existedNode == null) {
            doublyLinkedList.add(new Node<>(key, value));
            if (++size > threshold) resizeTable();
            return null;
        } else {
            V oldValue = existedNode.getValue();
            existedNode.setValue(value);
            return oldValue;
        }
    }

    private void resizeTable() {
        capacity *= 2;
        threshold = (int) (capacity * loadFactor);
        
        DoublyLinkedList<Node<K,V>>[] newTable = new DefaultDoublyLinkedList[capacity];
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            
            Iterator<Node<K,V>> iterator = table[i].iterator();
            while (iterator.hasNext()) {
                Node<K,V> node = iterator.next();
                int index = hashCodeToIndex(node.getHash());
                DoublyLinkedList<Node<K,V>> newDoublyLinkedList = newTable[index];
                if (newDoublyLinkedList == null) {
                    newTable[index] = new DefaultDoublyLinkedList<>();
                }
                newTable[index].add(node);
            }
            
            table[i].clear();
            table[i] = null;
        }
        
        table = newTable;
    }

    @Override
    public V remove(K key) {
        int index = hashCodeToIndex(key.hashCode());
        DoublyLinkedList<Node<K,V>> doublyLinkedList = table[index];
        if (doublyLinkedList == null || doublyLinkedList.isEmpty()) return null;
        
        Iterator<Node<K,V>> iterator = doublyLinkedList.iterator();
        
        while (iterator.hasNext()) {
            Node<K,V> node = iterator.next();
            if (node.getKey().equals(key)) {
                doublyLinkedList.remove(node);
                --size;
                return node.getValue();
            }
        }
        
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        final int elementCount = size();
        
        return new Iterator<K>() {
            int index = 0;
            Iterator<Node<K,V>> bucketIterator = table[0] == null ? null : table[0].iterator();
            
            @Override
            public boolean hasNext() {
                if (elementCount != size()) throw new java.util.ConcurrentModificationException("size changed");
                
                if (bucketIterator == null || !bucketIterator.hasNext()) {
                    while (++index < capacity) {
                        if (table[index] != null || !table[index].isEmpty()) {
                            bucketIterator = table[index].iterator();
                            break;
                        }
                    }
                }
                
                return index < capacity;
            }

            @Override
            public K next() {
                return bucketIterator.next().getKey();
            }
        };
    }
    
    //to string
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (int i = 0; i < capacity; i++) {
            if (table[i] == null || table[i].isEmpty()) {
                sb.append("index: " + i);
                sb.append("\n");
                continue;
            };
            
            sb.append("index: " + i + " => ");
            Iterator<Node<K,V>> iterator = table[i].iterator();
            while (iterator.hasNext()) {
                sb.append(iterator.next());
                if (i < capacity - 1) {
                    sb.append(", ");
                }
            }
            
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }
}
