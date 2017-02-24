package com.example;

/**
 * Created by jafir on 17/2/23.
 */

public class CounSort {
    private static int[] array = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};

    public static void main(String[] args) {
        //排序的数组
        int b[] = countSort(array);
        for (int i : b) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static int[] countSort(int[] a) {
        int b[] = new int[a.length];
        int max = a[0], min = a[0];
        for (int i : a) {
            if (i > max) {
                max = i;
            }
            if (i < min) {
                min = i;
            }
        }
        //这里k的大小是要排序的数组中，元素大小的极值差+1
        int k = max - min + 1;


        //浪费k的范围
        int c[] = new int[k];
        for (int i = 0; i < a.length; ++i) {
            c[a[i] - min] += 1;//优化过的地方，减小了数组c的大小
        }
        for (int i = 1; i < c.length; ++i) {
            c[i] = c[i] + c[i - 1];
        }
        for (int i = a.length - 1; i >= 0; --i) {
            b[--c[a[i] - min]] = a[i];//按存取的方式取出c的元素
        }
        return b;
    }


}
