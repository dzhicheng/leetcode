package com.dongzhic;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author dongzhic
 * @Date 5/26/21 10:42 PM
 */
public class JstackCase {

    public static Executor executor = Executors.newFixedThreadPool(5);
    public static Object lock = new Object();

    public static void main(String[] args) {

        Task t1 = new Task();
        Task t2 = new Task();
        executor.execute(t1);
        executor.execute(t2);
    }

    static class Task implements Runnable {

        @Override
        public void run() {
            synchronized (lock) {
                calculate();
            }
        }

        public void calculate() {
            int i = 0;
            while (true) {
                i++;
            }
        }
    }
}
