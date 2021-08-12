package com.dongzhic.simple;

import java.util.*;

/**
 * 155. 最小栈
 * @Author dongzhic
 * @Date 5/25/21 11:06 PM
 */
public class MinStack {

    public static void main(String[] args) {
        MinStack obj = new MinStack();
        obj.push(-2);
        obj.push(0);
        obj.push(-3);
        System.out.println(obj.getMin());
        obj.pop();
        System.out.println(obj.top());
        System.out.println(obj.getMin());

    }

    // 栈中元素为数组，分别是入栈值和栈中最小值
    Stack<Integer[]> stack;

    public MinStack() {
        stack = new Stack<>();
    }

    /**
     * 入栈
     * @param val
     */
    public void push(int val) {
        if (stack.isEmpty()) {
            stack.push(new Integer[]{val, val});
        } else {
            stack.push(new Integer[]{val, Math.min(val, stack.peek()[1])});
        }
    }

    /**
     * 出栈
     */
    public void pop() {
        stack.pop();
    }

    /**
     * 获取栈顶元素
     * @return
     */
    public int top() {
        return stack.peek()[0];
    }

    /**
     * 获取栈中最小值
     * @return
     */
    public int getMin() {
        return stack.peek()[1];
    }


}
