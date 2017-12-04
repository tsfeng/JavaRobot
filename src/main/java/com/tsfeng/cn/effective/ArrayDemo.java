package com.tsfeng.cn.effective;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * https://www.programcreek.com/2013/09/top-10-methods-for-java-arrays/
 * https://www.programcreek.com/
 * @author tsfeng
 * @version 创建时间 2017/11/24 13:57
 */
public class ArrayDemo {
    public static void main(String[] args) {
//        List<String> list = new ArrayList<String>();
//        add(list, 10);
//        String s = list.get(0);

//        ArrayList<String> list = new ArrayList<String>(Arrays.asList("a", "b", "c", "d"));
//        for (int i = 0; i < list.size(); i++) {
//            list.remove(i);
//        }
//        System.out.println(list);
        //0. Declare an array
//        String[] aArray = new String[5];
//        String[] bArray = {"a", "b", "c", "d", "e"};
//        String[] cArray = new String[]{"a", "b", "c", "d", "e"};

        //1. Print an array in Java
//        int[] intArray1 = {1, 2, 3, 4, 5};
//        String intArrayString = Arrays.toString(intArray1);
//        // print directly will print reference value
//        // [I@7150bd4d
//        System.out.println(intArray1);
//        // [1, 2, 3, 4, 5]
//        System.out.println(intArrayString);
//
//        //2. Create an ArrayList from an array
//        String[] stringArray = {"a", "b", "c", "d", "e"};
//        ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(stringArray));
//        // [a, b, c, d, e]
//        System.out.println(arrayList);
//
//        //3. Check if an array contains a certain value
//        boolean b = Arrays.asList(stringArray).contains("a");
//        //true
//        System.out.println(b);

        //4. Concatenate two arrays
//        int[] intArray4 = {1, 2, 3, 4, 5};
//        int[] intArray42 = {6, 7, 8, 9, 10};
//        // Apache Commons Lang library
//        int[] combinedIntArray = ArrayUtils.addAll(intArray4, intArray42);

        //5. Declare an array inline

        //6. Joins the elements of the provided array into a single String
        // containing the provided list of elements
        // Apache common lang
//        String j = StringUtils.join(new String[]{"a", "b", "c"}, ", ");
//        // a, b, c
//        System.out.println(j);

        //7. Covnert an ArrayList to an array
//        String[] stringArray7 = {"a", "b", "c", "d", "e", "f"};
//        ArrayList<String> arrayList7 = new ArrayList<>(Arrays.asList(stringArray7));
//        String[] stringArr7 = new String[arrayList7.size()];
//        arrayList7.toArray(stringArr7);
//        for (String s : stringArr7) {
//            System.out.print(s);
//        }
//        System.out.println();

        //8. Convert an array to a set
//        Set<String> set = new HashSet<>(Arrays.asList(stringArray));
//        //[a, b, c, d, e]
//        System.out.println(set);

        //9. Reverse an array
//        int[] intArray = { 1, 2, 3, 4, 5 };
//        ArrayUtils.reverse(intArray);
//        //[5, 4, 3, 2, 1]
//        System.out.println(Arrays.toString(intArray));

        //10. Remove element of an array
        //create a new array
//        int[] removed = ArrayUtils.removeElement(intArray, 3);
//        System.out.println(Arrays.toString(removed));

        byte[] bytes = ByteBuffer.allocate(4).putInt(8).array();
        for (byte t : bytes) {
            System.out.format("0x%x ", t);
        }
    }

    public static void add(List list, Object o){
        list.add(o);
    }
}
