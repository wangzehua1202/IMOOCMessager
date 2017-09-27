package com.xingsu.italker.factory;

import com.xingsu.italker.common.common.app.Application;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class Factory {
    //单例模式
    private static final Factory instance;
    private final Executor executor;

    static {
        instance = new Factory();
    }

    public Factory() {
        //新建一个4个线程的线程池
        executor = Executors.newFixedThreadPool(4);
    }

    /**
     * 返回全局的Application
     * @return Application
     */
    public static Application app(){
        return Application.getInstance();
    }

    /**
     * 异步运行的方法
     * @param runable Runnable
     */
    public static void runOnAsync(Runnable runable){
        instance.executor.execute(runable);
    }
}
