package com.jafir.testclassinvoke.singleton;

/**
 * Created by jafir on 17/1/17.
 * 在方法上加了一个锁
 * 这个的话效率要低一点，因为一个线程持有锁，其他线程要等待
 */

public class SimpleSingle1 {

    private static SimpleSingle1 instance;

    public int i;

    private SimpleSingle1(){

    }

    public static  synchronized SimpleSingle1 getInstance(){
        if(instance == null){
            instance = new SimpleSingle1();
        }
        return instance;
    }

}
