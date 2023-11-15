package com.niaobulashi.thread.syncronized;

import javax.sound.midi.Soundbank;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @className: T0_MutilThreadAndUnsafe
 * @description: TODO 类描述
 * @author: HuLang
 * @date: 2023/11/13
 **/
public class T0_MutilThreadAndUnsafe {
    private static int total = 0;
    private static final Object object = new Object();
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    countDownLatch.await();
                    for (int j = 0; j < 1000; j++) {
                        /*synchronized (object) {
                            total++;
                        }*/
                        try {
                            lock.lock();
                            total++;
                            Thread.sleep(1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlock();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //System.out.println(Thread.currentThread().getName() + " 到达");
            }, "t" + i).start();
        }
        Thread.sleep(1000);

        countDownLatch.countDown();

        Thread.sleep(2000);

        System.out.println(total);
    }
}
