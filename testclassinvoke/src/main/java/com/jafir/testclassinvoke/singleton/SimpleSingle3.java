package com.jafir.testclassinvoke.singleton;

/**
 * Created by jafir on 17/1/17.
 * <p>
 * 这个是在simpleSingle1的基础上优化提升了一下性能
 *
 * 再加上volatile确保多线程执行的安全性
 *
 */

public class SimpleSingle3 {

    private volatile static SimpleSingle3 instance;
    //锁
    private static Object object = new Object();

    public int i = 0;

    private SimpleSingle3() {

    }

    /**
     * 1、先检查是否初始化 是否null
     * 2、获得锁
     * 3、再次检查是否初始化（万一其他线程已经初始化完了）
     * 然后如果还是null就进行初始化（因为获得了锁，所以其他线程不会同步进行初始化）
     * 4、返回初始化后的变量
     *
     * @return
     */
    public static SimpleSingle3 getInstance() {
        if (instance == null) {
            synchronized (object) {
                if (instance == null) {
                    instance = new SimpleSingle3();
                }
            }
        }
        return instance;
    }
}
