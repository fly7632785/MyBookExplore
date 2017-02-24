package com.example;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jafir on 17/2/23.
 */

public class TestBingfa {


    private static int count = 0;
    public static class CountThread implements Runnable {// 1.这边有线程安全问题，共享变量乱套了
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                System.out.println(Thread.currentThread().getName() + ", count = " + count);
            }
        }
    }
    private static final Object lock = new Object();// 这边使用的lock对象
    public static class Count2Thread implements Runnable {// 这边使用的是互斥锁方式
        @Override
        public void run() {
            synchronized (lock) {// 使用互斥锁方式处理
                for (int i = 0; i < 10; i++) {
                    count++;
                    System.out.println(Thread.currentThread().getName() + ", count = " + count);
                }
            }
        }
    }
    private static AtomicInteger ai = new AtomicInteger();// 这边使用的是并发包的AtomicXXX类，使用的是CAS方式：compare and swap
    public static class Count3Thread implements Runnable {// AtomicInteger内部的CAS实现方式，采用的是：循环、判断、设置三部曲方式
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                int tmp = ai.incrementAndGet();// 采用CAS方式处理
                System.out.println(Thread.currentThread().getName() + ", count = " + tmp);
            }
        }
    }


    /**
     *
     *
     * 锁的特征：防并发性、可见性

     volatile ：可见性，不防并发。编译器在代码执行的时候会有一定的优化方式，比如从赋值语句从a =50 ,a =51,a =52 ...a = 100 会直接只执行a=100 。
     而violatile就是直接不进行代码优化，取最新的主存的值。
     还有我们的通常比如做一个赋值操作，原理是这样的，a =100 ,    取出a 工作内存里面 a_temp = a ,a_temp = 100, 最后a = a_temp 类似这样的操作。
     volatile没有并发性，所以不存在阻塞的说法，并且如果读操作大于写操作的话，volatile性能有优势
     相对而言，如果不存在不安全的线程操作，使用volatile比synchronized开销要低

     volatile只是在线程内存和“主”内存间同步某个变量的值，而synchronized通过锁定和解锁某个监视器同步所有变量的值。显然synchronized要比volatile消耗更多资源。

     1. 如果只是读操作，没有写操作，则可以不用加锁，此种情形下，建议变量加上final关键字；
     2. 如果有写操作，但是变量的写操作跟当前的值无关联，且与其他的变量也无关联，则可考虑变量加上volatile关键字，同时写操作方法通过synchronized加锁；
     3. 如果有写操作，且写操作依赖变量的当前值(如：i++)，则getXXX和写操作方法都要通过synchronized加锁。
     *
     *
     */
    private static volatile int countV = 0;// 定义成volatile，让多线程感知，因为值是放在主存中
    public static class Count4Thread implements Runnable {// volatile定义的变量只是说放到了主存，当时++操作并不是原子操作，这个要小心
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(50);// 这边让线程休眠下，增加出错概率
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getAnInt();// volatile要正确使用，不是说定义成volatile就是安全的，还是要注意++ --操作并不是原子操作
//                getAnInt1();//加了synchronized的写操作就不会有问题
                System.out.println(Thread.currentThread().getName() + ", count = " + countV);
            }
        }
    }

    private  static void getAnInt() {
        countV++;
    }
    private synchronized static void getAnInt1() {
        countV++;
    }


    /**
     * 使用泛型简单编写一个测试方法
     *
     * @param <T>
     * @param t
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InterruptedException
     */
    public static <T> void testTemplate(T t) throws InstantiationException, IllegalAccessException, InterruptedException {
        for (int i = 0; i < 5; i++) {
            if (t instanceof Runnable) {
                Class<?> c = t.getClass();
                Object object = c.newInstance();
                new Thread((Runnable) object).start();
            }
        }
    }



    /**
     * <pre>
     * 1.test1 线程不安全演示例子，count变量不能得到预期的效果
     * 2.test2 在test1基础上改进的，用互斥锁sync处理
     * 3.test3 在test1基础上改进的，用AtomicInteger类来实现
     * 4.test4 有问题的方法，因为i++并不是原子操作，将count定义为volatile类型的
     *
     * @param args
     * @throws InterruptedException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException {
        // 1.测试1
        // testTemplate(new CountThread());
//         2.测试2
//         testTemplate(new Count2Thread());
        // 3.测试3
//         testTemplate(new Count3Thread());
        // 4.测试4
        testTemplate(new Count4Thread());
        Thread.sleep(10000);
        System.out.println("count:"+count);
        System.out.println("atomicInteger:"+ai.get());
        System.out.println("volatile:"+countV);
    }

}
