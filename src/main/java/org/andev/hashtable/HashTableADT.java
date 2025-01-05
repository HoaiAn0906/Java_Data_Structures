package org.andev.hashtable;

public interface HashTableADT<K, V> extends Iterable<K>{
    int size();
    boolean isEmpty();
    //hash code
    int hashCodeToIndex(int hashedKey);
    void clear();
    boolean has(K key);
    V get(K key);
    V insert(K key, V value);
    V remove(K key);
    
}
