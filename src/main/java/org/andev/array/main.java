package org.andev.array;

public class main {
    public static void main(String[] args) {
        DynamicArray<String> dynamicArray = new DynamicArray<>();
        dynamicArray.add("A");
        System.out.println(dynamicArray.size());
        dynamicArray.add("N");
        System.out.println(dynamicArray.size());
        dynamicArray.add("D");
        dynamicArray.add("E");
        dynamicArray.add("V");
        
        System.out.println(dynamicArray);
    }
}
