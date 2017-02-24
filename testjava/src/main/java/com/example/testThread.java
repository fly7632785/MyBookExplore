package com.example;

/**
 * Created by jafir on 17/2/23.
 */

public class testThread {

    private static int i;

    public static void main(String[] a){
        test();
    }


    private static void test(){
        Runnable runnable1 = new Runnable() {
            @Override
            public void run() {
//                circle();//有并发问题
                circle1();//加入synchrized防止并发
            }
        };
        new Thread(runnable1).start();
        new Thread(runnable1).start();
        new Thread(runnable1).start();
        new Thread(runnable1).start();
        new Thread(runnable1).start();
        new Thread(runnable1).start();
    }

    private  static void circle() {
        for (int j = 0; j < 10; j++) {
            i++;
            try {
                Thread.sleep(50);// 这边让线程休眠下，增加出错概率
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i--;
            //按道理  这里应该 +1  -1 每次都应该是0才对
            System.out.println("i:"+i);
        }
    }
    private synchronized static void circle1() {
        for (int j = 0; j < 10; j++) {
            i++;
            try {
                Thread.sleep(50);// 这边让线程休眠下，增加出错概率
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            i--;
            //按道理  这里应该 +1  -1 每次都应该是0才对
            System.out.println("i:"+i);
        }
    }

}
