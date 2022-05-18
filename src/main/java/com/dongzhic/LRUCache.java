package com.dongzhic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 146. LRU 缓存机制
 *  双向链表+HashMap
 * @Author dongzhic
 * @Date 2021/9/24 11:04
 */
public class LRUCache {


    /**
     * 容量
     */
    int capacity = 0;
    Map<Integer, Node> map;
    /**
     * 头节点
     */
    private Node head;
    /**
     * 尾结点
     */
    private Node tail;

    /**
     * 以正整数作为容量 capacity 初始化 LRU 缓存
     * @param capacity
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        map = new HashMap<>(capacity);

        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    /**
     * 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
     * @param key
     * @return
     */
    public int get(int key) {

        if (!map.containsKey(key)) {
            return -1;
        }

        Node node = map.get(key);
        removeNode2First(node);
        return node.value;
    }

    /**
     * 如果关键字已经存在，则变更其数据值；
     * 如果关键字不存在，则插入该组「关键字-值」。
     * 当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
     * @param key
     * @param value
     */
    public void put(int key, int value) {

        // 1.判断key是否存在
        if (map.containsKey(key)) {
            Node node = map.get(key);
            node.value = value;
            removeNode2First(node);
        } else {
            // 2.判断容量是否已经满
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
     * 将节点添加到链表第一个元素
     * @param node
     */
    public void addNode2First (Node node) {

        Node firstNode = head.next;
        node.next = firstNode;
        firstNode.prev = node;

        head.next = node;
        node.prev = node;
    }

    /**
     * 删除最后一个元素
     */
    public void removeLast () {

        Node lastNode = tail.prev;
        Node prevLastNode = lastNode.prev;

        prevLastNode.next = tail;
        tail.prev = prevLastNode;

        lastNode.next = null;
        lastNode.prev = null;
    }

    /**
     * 删除节点，并将节点添加到链表头部(代表刚刚使用过)
     * @param node
     */
    public void removeNode2First (Node node) {

        Node prevNode = node.prev;
        Node nextNode = node.next;

        prevNode.next = nextNode;
        nextNode.prev = prevNode;

        addNode2First(node);
    }


    /**
     * 内部类，双向链表
     */
    private class Node {
        Node prev;
        Node next;
        int key;
        int value;

        public Node (int key, int value) {
            this.key = key;
            this.value = value;
        }

    }

}
