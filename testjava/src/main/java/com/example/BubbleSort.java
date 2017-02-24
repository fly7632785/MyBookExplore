package com.example;

/**
 * Created by jafir on 17/2/22.
 */

public class BubbleSort {

    private static int[] array = new int[]{12312312, 12312312, -123123, 3, 44444, -1, 12312, 1, 5, -55555, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};
    private static int[] array1 = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};
    private static int[] array2 = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};

    public static void main(String[] args) {


        int c1 = bubbleSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println(c1 + "交换 ");

        int c2 = bubbleSort1(array1);
        for (int i = 0; i < array1.length; i++) {
            System.out.print(array1[i] + " ");
        }
        System.out.println(c2 + "交换 ");

        int c3 = bubbleSort2(array2);
        for (int i = 0; i < array2.length; i++) {
            System.out.print(array2[i] + " ");
        }
        System.out.println(c3 + "交换 ");
    }

    private static int bubbleSort(int[] v) {

        int count = 0;
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v.length; j++) {
                if (v[i] < v[j]) {
                    int temp = v[i];
                    v[i] = v[j];
                    v[j] = temp;
                    count++;
                }
            }
        }
        return count;

    }

    private static int bubbleSort1(int[] v) {
        int count = 0;
        for (int i = 0; i < v.length; i++) {
            for (int j = i + 1; j < v.length; j++) {
                if (v[i] > v[j]) {
                    int temp = v[i];
                    v[i] = v[j];
                    v[j] = temp;
                    count++;
                }
            }
        }
        return count;

    }


    private static int bubbleSort2(int[] a) {
        int temp = 0;
        int count = 0;
        for (int i = a.length - 1; i > 0; --i) {
            for (int j = 0; j < i; ++j) {
                if (a[j + 1] < a[j]) {
                    temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                    count++;
                }
            }
        }
        return count;
    }


}
