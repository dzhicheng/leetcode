package com.dongzhic.Solution_202205;

import java.util.Stack;

/**
 * 18.用栈实现队列（232）
 * @Author dongzhic
 * @Date 2022/5/25 10:51
 */
public class MyQueue {

    /**
     * 入队使用栈
     */
    Stack<Integer> inStack;
    /**
     * 出队使用栈
     */
    Stack<Integer> outStack;

    public MyQueue() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }

    public void push(int x) {
        inStack.push(x);
    }

    public int pop() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.pop();
    }

    public int peek() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.peek();
    }

    public boolean empty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }
}
