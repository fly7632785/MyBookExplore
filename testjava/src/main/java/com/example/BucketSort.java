package com.example;

/**
 * Created by jafir on 17/2/22.
 */

public class BucketSort {
    private static int[] array = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};
    private static int[] array1 = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};
    private static int[] array2 = new int[]{12312312, 12312312, 3, 44444, 12312, 1, 5, 123, 87, 435, 12, 7, 1, 3, 44444, 4, 10};

    public static void main(String[] arg) {
        bucketSort(array);
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
        bucketSort1(array1);
        for (int i = 0; i < array1.length; i++) {
            System.out.print(array1[i] + " ");
        }
        System.out.println();
        bucket(array2);
        for (int i = 0; i < array2.length; i++) {
            System.out.print(array2[i] + " ");
        }
    }

    private static void bucketSort(int[] v) {
        //求出最大值
        int max = 0;
        for (int i = 0; i < v.length; i++) {
            if (max < v[i]) {
                max = v[i];
            }
        }

        //定义一个最大范围  0-100 有101个数
        //这里要+1因为 最大数是100 那么就需要 101个桶 因为还有0这个数字
        int[] tempArray = new int[max + 1];
        for (int i = 0; i < v.length; i++) {
            //数的值 就是 桶中的序号
            //比如 值是5  那么就放在第五个桶里   桶++
            //tempArray[4]=3 表示第5个桶里放了 3个数  三个数都是4
            tempArray[v[i]]++;
        }

        int count = 0;
        //一次打印桶的值  如果不为0 说明里面有数
        for (int i = 0; i < tempArray.length; i++) {
            if (tempArray[i] != 0) {
                for (int j = 0; j < tempArray[i]; j++) {
                    //桶的下标就是那个数  桶的下标对应的值 就是有几个数
                    //所以这里的 循环次数是 tempArray[i]次
                    v[count] = i;//就是那个数
                    count++;
                }
            }
        }
    }


    private static void bucketSort1(int []v){
        int max = v[0];
        for (int i = 1; i < v.length; i++) {
            if(max < v[i]){
                max = v[i];
            }
        }

        int[] buckets = new int[max+1];

        //注意这里是v的length 不是bucket的
        for (int i = 0; i < v.length; i++) {
            buckets[v[i]]++;
        }

        int index = 0;
        for (int i = 0; i < buckets.length; i++) {
            if(buckets[i] != 0) {
                for (int i1 = 0; i1 < buckets[i]; i1++) {
                    v[index] = i;
                    index++;
                }
            }
        }
    }


    /**
     * 优化
     * 开辟的内存 只有最高-最低+1的个内存单位
     * @param v
     */
    private static void bucket(int[] v){

        int big = 0;
        int min = 0;
        for (int i = 0; i < v.length; i++) {
            if(big<v[i]){
                big = v[i];
            }
            if(min>v[i]){
                min = v[i];
            }
        }

        int max = big-min+1;
        //浪费的空间  k 就是数值的范围
        int bucket[] = new int[max];

        for (int i = 0; i < v.length; i++) {
            bucket[v[i]-min]++;
        }

        int count = 0;
        for (int i = 0; i < bucket.length; i++) {
            if(bucket[i]!=0){
                for (int i1 = 0; i1 < bucket[i]; i1++) {
                    v[count] = i+min;
                    count++;
                }
            }
        }







    }
}
