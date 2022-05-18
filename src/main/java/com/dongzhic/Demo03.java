package com.dongzhic;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author dongzhic
 * @Date 2021/11/27 19:02
 */
public class Demo03 {

    public static void main(String[] args) {
        printLetter();
    }

    /**
     * 交替打印ABC
     */
    public static void printLetter () {

        // 打印次数
        int printCount = 10;

        ReentrantLock lock = new ReentrantLock();
        Condition lockA = lock.newCondition();
        Condition lockB = lock.newCondition();
        Condition lockC = lock.newCondition();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < printCount; i++) {
                    System.out.print("A");
                    lockB.signal();
                    lockA.await();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "打印A");

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < printCount; i++) {
                    System.out.print("B");
                    lockC.signal();
                    lockB.await();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "打印B");

        Thread t3 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < printCount; i++) {
                    System.out.print("C");
                    lockA.signal();
                    lockC.await();

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }, "打印C");

        t1.start();
        t2.start();
        t3.start();

    }


}
