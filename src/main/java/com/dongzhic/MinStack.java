package com.dongzhic;

import java.util.Stack;

/**
 * @Author dongzhic
 * @Date 7/9/21 10:19 AM
 */
public class MinStack {

    public static void main(String[] args) {
        MinStack stack = new MinStack();

        stack.push(2);
        stack.push(0);
        stack.push(3);
        stack.push(0);
        stack.getMin();
        stack.pop();
        stack.getMin();
        stack.pop();
        stack.getMin();
        stack.pop();
        stack.getMin();
    }

    Stack<Integer> stack;
    Stack<Integer> minStack;

    /** initialize your data structure here. */
    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {

        stack.push(val);

        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            int peekVal = minStack.peek();
            if (val < peekVal) {
                minStack.push(val);
            } else {
                minStack.push(peekVal);
            }
        }

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
