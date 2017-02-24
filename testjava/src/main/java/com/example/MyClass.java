package com.example;

public class MyClass {

    private static int[] array = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};
    private static int[] array1 = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};
    private static int[] array3 = new int[]{-1,-1,-12312312, -12312312, -10,-3, -4, -10};
    private static int[] array4 = new int[]{-1,-1,-12312312, -12312312, -10,-3, -4, -10};

    public static void main(String[] args) {

//        System.out.println("1第二大：" + findSencond(array));
//        System.out.println("2第二大：" + findSencond1(array1));
        System.out.println("3第二大：" + findSencond(array3));
        System.out.println("3第二大：" + findSencond1(array4));
    }


    /**
     * 找出第二大的算法
     * <p>
     * 两种思路：
     * 1、排序然后再找第二大
     * 2、排除最大，然后找第二大
     *
     * @param v
     * @return
     */
    private static int findSencond(int[] v) {
        int big = v[0];
        int second = v[0];
        for (int i = 0; i < v.length; i++) {
            if (big <= v[i]) {
                big = v[i];//找出最大
            } else {
                second = v[i];//不是最大 但不一定是第二大
            }
        }
        for (int i = 0; i < v.length; i++) { //把所有最大的值都赋值为 非最大的值
            if (v[i] == big) {
                v[i] = second;
            }
        }
        for (int i = 0; i < v.length; i++) {
            if (second <= v[i]) {
                second = v[i];//找出第二大
            }
        }
        return second;
    }

    private static int findSencond1(int[] v) {
        int big = v[0];
        int second = v[0];
        for (int i = 0; i < v.length; i++) {
            if (big <= v[i]) {//如果比这个数小 则赋值为它
                big = v[i];//找出最大
            } else {
                second = v[i];//不是最大 但不一定是第二大
            }
        }
        for (int i = 0; i < v.length; i++) {
            if (v[i] != big) {//排除最大值
                if (second <= v[i]) {//如果比这个数小 则赋值为它
                    second = v[i];//找出最大
                }
            }
        }
        return second;
    }
}
