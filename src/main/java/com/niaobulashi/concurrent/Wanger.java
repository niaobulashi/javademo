package com.niaobulashi.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Wanger {

    /**
     * 1. 创建一个线程
     * 声明一个实现了Runnable接口的匿名内部类；然后将他作为创建Thread对象的参数，
     * 再然后调用Thread对象的start()方法进行启动。
     * Java的并发是抢占式的
     */
    public static void createThread() {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("我是：" + Thread.currentThread().getName());
                }
            });
            t.start();
        }
    }

    /**
     * 2.创建一个线程池
     * Executors.newCachedThreadPool()用于创建一个可缓存的线程池
     * 调用该线程的方法execute()可以重用以前的线程，只要该线程可用
     * 60秒内还没有被使用的线程也会从缓存中移除
     * Executors.newFiexedThreadPool(int num)方法用于创建固定数目线程的线程池
     * Executors.newSingleThreadExecutor()方法用于创建单线程化的线程
     */
    public static void createThreadPool() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    System.out.println("我叫：" + Thread.currentThread().getName());
                }
            };
            executorService.execute(r);
        }
        executorService.shutdown();
    }

    public static void main(String[] args) {
        /**
         * 1.创建一个线程
         */
//        createThread();
        /**
         * 2.创建一个线程池
         */
        createThreadPool();

    }
}
