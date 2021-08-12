package com.dongzhic.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * 146. LRU 缓存机制：最近最少使用
 *  双向链表+HashMap
 * @Author dongzhic
 * @Date 7/7/21 10:56 PM
 */
public class LRUCache {

    /**
     * 容量
     */
    private int capacity;
    private Map<Integer, Node> map;
    /**
     * 头结点
     */
    private Node head;
    /**
     * 尾结点
     */
    private Node tail;

    /**
     * 初始化LRU缓存
     * @param capacity 容量
     */
    public LRUCache (int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);

        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    /**
     * 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1
     * @param key
     * @return
     */
    public int get (int key) {

        if (!map.containsKey(key)) {
            return -1;
        }

        Node node = map.get(key);
        removeNode2First(node);
        return node.value;
    }

    /**
     *  如果关键字已经存在，则变更其数据值；
     *  如果关键字不存在，则插入该组「关键字-值」。
     *  当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
     * @param key
     * @param value
     */
    public void put (int key, int value) {

        // 1.key已存在
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;

            removeNode2First(node);
        } else {
            // 2.判断是否达到最大容量
            if (map.size() >= capacity) {
                // 3.删除最近最少使用
                map.remove(tail.prev.key);
                removeLast();
            }
            Node node = new Node(key, value);
            map.put(key, node);

            addNode2First(node);
        }

    }

    /**
     * 删除节点
     * @param node
     */
    public void removeNode2First (Node node) {

        // 1.删除节点
        Node nextNode = node.next;
        Node prevNode = node.prev;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        // 2.操作元素添加到链表首尾
        addNode2First(node);
    }

    /**
     * 删除最后一个元素
     */
    public void removeLast () {

        Node lastNode = tail.prev;

        Node preNode = lastNode.prev;
        preNode.next = tail;
        tail.prev = preNode;

        lastNode.next = null;
        lastNode.prev = null;
    }

    /**
     * 将节点添加到链表第一个元素
     * @param node
     */
    public void addNode2First (Node node) {
        Node firstNode = head.next;
        firstNode.prev = node;
        node.next = firstNode;

        head.next = node;
        node.prev = head;
    }



    /**
     * 内部类，双向链表
      */
    private class Node {
        Node next;
        Node prev;
        int key;
        int value;

        Node (int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
