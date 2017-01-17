package com.jafir.testclassinvoke.singleton;

/**
 * Created by jafir on 17/1/17.
 *
 *
 * 双重检查锁
 * 因为内部类直到他们被引用时才会加载。
 * 所以利用静态内部类的加载机制出现的单例模式
 *
 *
 */

public class SimpleSingle4 {


    public int i ;
    private SimpleSingle4(){
    }

    public static SimpleSingle4 getInstance(){
        return SingleHolder.instance;
    }

    private static class SingleHolder{
        static SimpleSingle4 instance = new SimpleSingle4();
    }
}
