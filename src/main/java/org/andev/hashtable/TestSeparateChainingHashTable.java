package org.andev.hashtable;

import org.andev.linkedlist.DoublyLinkedList;

import java.util.Iterator;
import java.util.Random;

public class TestSeparateChainingHashTable {
    static final int NUMBER_OF_KEYS = 1000;
    static final int MOD = 1000;
    static int[] keys = new int[NUMBER_OF_KEYS];
    static int[] values = new int[NUMBER_OF_KEYS];
    
    public static void main(String[] args) {
        Random random = new Random();
        for (int i = 0; i < NUMBER_OF_KEYS; i++) {
            keys[i] = random.nextInt() % MOD;
            values[i] = random.nextInt() % MOD;
        }
        
        testSeparateChainingHashTable();
    }

    private static void testSeparateChainingHashTable() {
        HashTableADT<Integer, Integer> hashTable = new SeparateChainingHashTable<>();
        long start = System.nanoTime();
        for (int i = 0; i < NUMBER_OF_KEYS; i++) {
            hashTable.insert(keys[i], values[i]);
        }
        long end = System.nanoTime();
        System.out.println("Time taken: " + (end - start) / 1e9 + "ms");
        System.out.println("Size: " + hashTable.size());
        System.out.println(hashTable);
    }
}
