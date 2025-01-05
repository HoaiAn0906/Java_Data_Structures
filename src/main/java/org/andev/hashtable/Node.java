package org.andev.hashtable;

public class Node<K,V> {
    private int hash;
    private K key;
    private V value;
    
    public Node(K key, V value) {
        this.hash = key.hashCode();
        this.key = key;
        this.value = value;
    }
    
    public boolean equals(Node<K,V> other) {
        if (other.hash != this.hash) {
            return false;
        }
        
        return key.equals(other.key);
    }

    public K getKey() {
        return key;
    }
    
    public V getValue() {
        return value;
    }
    
    public void setValue(V value) {
        this.value = value;
    }

    public int getHash() {
        return hash;
    }

    @Override
    public String toString() {
        return "Node{" +
                "hash=" + hash +
                ", key=" + key +
                ", value=" + value +
                '}';
    }
}
