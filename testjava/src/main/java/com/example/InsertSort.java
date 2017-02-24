package com.example;

/**
 * Created by jafir on 17/2/22.
 */

public class InsertSort {
    private static int[] array = new int[]{12312312, 12312312,-123123, 3, 44444, -1,12312, 1, 5,-55555, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};
    private static int[] array1 = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};
    private static int[] array2 = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};

    public static void main(String[] args) {

        int c1 = insertSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println(c1+"交换 ");

        int c2 = insertSort1(array1);
        for (int i = 0; i < array1.length; i++) {
            System.out.print(array1[i] + " ");
        }
        System.out.println(c2+"交换 ");

        int c3 = insertSort2(array2);
        for (int i = 0; i < array2.length; i++) {
            System.out.print(array2[i] + " ");
        }
        System.out.println(c3+"交换 ");
    }

    private static int insertSort(int[] v) {

        int count = 0;
        for (int i=1; i<v.length; i++){
            int key = v[i];//从第二项开始
            /**
             * http://www.cnblogs.com/luchen927/archive/2012/02/23/2343857.html
             * 现在有一张牌 与前面的牌比 如果小则跟前面的交换 一直往前换 直接比前面的数大停止
             * 注意这里是v[i-1]在和key比   v[i-1]一直在向前
             * 停止之后就找到了key的位置，就是i
             */
            while (i>0 && v[i-1] > key){
                v[i] = v[i-1];
                i--;
                count++;
            }
            v[i] = key;
        }
        return count;
    }

    private static int insertSort1(int[] v) {
        int count = 0;
        for (int i = 1; i < v.length; i++) {
            int key = v[i];
            while (i>0 && key < v[i-1]){
                v[i]=v[i-1];
                i--;
            }
            v[i] = key;//因为前面已经执行了一个i-- 所以这里是i 不是i-1
        }
        return count;
    }


    private static int insertSort2(int[] v) {
        int count = 0;
        for (int i = 1; i < v.length; i++) {
            int key = v[i];
            while (i>0 && v[i-1] > key){
                v[i] = v[i-1];
                i--;
            }
            v[i] = key;

        }

        return count;
    }
}
