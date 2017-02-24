package com.example;

/**
 * Created by jafir on 17/2/22.
 */

public class QuickSort {

    private static int[] array = new int[]{12312312, 12312312,-123123, 3, 44444, -1,12312, 1, 5,-55555, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};

    public static void main(String[] args) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println("");


//        quicksort(array, 0, array.length - 1);
        myQuickSortR(array, 0, array.length - 1);


        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
    }

    /**
     * 思路：
     * 找一个标兵，一般是第一个值
     * 然后左右开工，左边往右边找 ，右边往左边找，交换放置
     *
     * @param v     数组
     * @param left  左边开始的下标
     * @param right 右边开始的下标
     */
    private static void quicksort(int[] v, int left, int right) {
        if (left < right) {
            int key = v[left];
            int low = left;
            int high = right;
            while (low < high) {
//high下标位置开始，向左边遍历，查找不大于基准数的元素
                while (low < high && v[high] >= key) {
                    high--;
                }
                if (low < high) {//找到小于准基数key的元素
                    v[low] = v[high];//赋值给low下标位置，low下标位置元素已经与基准数对比过了
                    for (int i = 0; i < array.length; i++) {
                        System.out.print(array[i] + " ");
                    }
                    System.out.print("low:" + low + " high:" + high);
                    System.out.println("");
                    low++;//low下标后移
                } else {//没有找到比准基数小的元素
//说明high位置右边元素都不小于准基数
                    break;
                }
// low下标位置开始，向右边遍历，查找不小于基准数的元素
                while (low < high && v[low] <= key) {
                    low++;
                }
                if (low < high) {//找到比基准数大的元素
                    v[high] = v[low];//赋值给high下标位置，high下标位置元素已经与基准数对比过了
                    high--;//high下标前移，
                    for (int i = 0; i < array.length; i++) {
                        System.out.print(array[i] + " ");
                    }
                    System.out.print("low:" + low + " high:" + high);
                    System.out.println("");
                } else {//没有找到比基准数小的元素
//说明low位置左边元素都不大于基准数
                    break;
                }
            }
            v[low] = key;//low下标赋值基准数
            for (int i = 0; i < array.length; i++) {
                System.out.print(array[i] + " ");
            }
            System.out.print("=low:" + low + " high:" + high);
            System.out.println("");
            quicksort(v, left, low - 1);
            quicksort(v, low + 1, right);
        }

    }


    /**
     * 从右边
     *
     * @param array
     * @param left
     * @param right
     */
    private static void myQuickSortR(int[] array, int left, int right) {
        if (left < right) {
            int l = left;
            int r = right;
            int key = array[r];//默认从最右边开始
            while (l < r) {
                while (l < r && array[r] > key) {
                    r--;
                }
                if (l < r) {
                    array[l] = array[r];
                    l++;
                }

                while (l < r && array[l] < key) {

                    l++;
                }
                if (l < r) {

                    array[r] = array[l];
                    r--;
                }
            }
            array[l] = key;
            myQuickSortR(array, left, l - 1);
            myQuickSortR(array, r + 1, right);


        }
    }


    /**
     * 从左边
     *
     * @param array
     * @param left
     * @param right
     */
    private static void myQuickSortL(int[] array, int left, int right) {
        if (left < right) {
            int l = left;
            int r = right;
            int key = array[l];//默认从最左边开始
            while (l < r) {
                while (l < r && array[r] >= key) {
                    r--;
                }
                if (l < r) {
                    array[l] = array[r];
                    l++;
                }

                while (l < r && array[l] <= key) {

                    l++;
                }
                if (l < r) {

                    array[r] = array[l];
                    r--;
                }
            }
            array[l] = key;
            myQuickSortL(array, left, l - 1);
            myQuickSortL(array, r + 1, right);


        }
    }
}
