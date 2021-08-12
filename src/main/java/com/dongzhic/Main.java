package com.dongzhic;

import com.dongzhic.util.TimeTool;

/**
 * @Author dongzhic
 * @Date 2020-10-16 13:50
 */
public class Main {
    public static void main(String[] args) {

        int n = 30;
        TimeTool.check("fib1", new TimeTool.Task() {
            @Override
            public void execute() {
                fib1(n);
            }
        });

        TimeTool.check("fib2", new TimeTool.Task() {
            @Override
            public void execute() {
                fib2(n);
            }
        });


    }

    /**
     * 斐波那契数： 0 1 1 2 3 5 8 13 ...
     * @return
     */
    public static int fib1 (int n) {
        if (n <= 1) {
            return n;
        }
        return fib1(n - 1) + fib1(n - 2);
    }

    /**
     * 斐波那契数： 0 1 1 2 3 5 8 13 ...
     * @param n
     * @return
     */
    public static int fib2 (int n) {
        if (n <= 1) {
            return n;
        }
        int first = 0;
        int second = 1;
        for (int i = 0; i < n - 1; i ++ ) {
            int sum = first + second;
            first = second;
            second = sum;
        }
        return second;
    }
}
