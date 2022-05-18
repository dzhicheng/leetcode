package com.dongzhic.other;

import java.util.Stack;

/**
 * @Author dongzhic
 * @Date 2021/11/9 16:59
 */
public class MinStack1 {

    Stack<Integer> stack;
    Stack<Integer> minStack;

    public MinStack1() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        if (stack.isEmpty()) {
            minStack.push(val);
        } else {
            int topVal = minStack.peek();
            minStack.push(val > topVal ? topVal : val);
        }
        stack.push(val);
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }

}
