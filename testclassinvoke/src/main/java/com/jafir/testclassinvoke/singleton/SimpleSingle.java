package com.jafir.testclassinvoke.singleton;

/**
 * Created by jafir on 17/1/17.
 *
 * 最简单的单例之一
 * 如果对于多线程而言就可能出现问题
 * 因为可能出现几个线程都正在new 也或者几个线程都已经return 一个null的instance
 *
 */

public class SimpleSingle {

    private static SimpleSingle instance;

    public  int i;
    private SimpleSingle(){

    }

    public static SimpleSingle getInstance(){
        if(instance == null){
            instance = new SimpleSingle();
        }
        return instance;
    }

}
