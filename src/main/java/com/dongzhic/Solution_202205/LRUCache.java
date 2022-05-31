package com.dongzhic.Solution_202205;

import java.util.HashMap;
import java.util.Map;

/**
 * 5.LRU缓存（146）：双向链表 + map
 *  淘汰很久没有访问的数据
 * @Author dongzhic
 * @Date 2022/5/20 15:55
 */
public class LRUCache {

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        System.out.println(lRUCache.get(2));    // 返回 -1 (未找到)
        lRUCache.put(2, 6); // 缓存是 {2=6}
        System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
        lRUCache.put(1, 5); // 缓存是 {1=5, 2=6}
        lRUCache.put(1, 2); // 缓存是 {1=2, 2=6}
        System.out.println(lRUCache.get(1));    // 返回 2 缓存是 {1=2, 2=6}
        System.out.println(lRUCache.get(2));    // 返回 6 缓存是 {2=6, 1=2}
    }

    private Map<Integer, Node> map;
    private Integer capacity;
    private Node head;
    private Node tail;

    public LRUCache(int capacity) {
        map = new HashMap<>(capacity);
        this.capacity = capacity;
        head = new Node(0, 0);
        tail = new Node(0, 0);
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = map.get(key);

        if (node == null) {
            return -1;
        }

        // 将元素移到头结点之后
        moveNode2FirstNode(node);

        return node.value;
    }

    public void put(int key, int value) {
        Node node;
        // 1.判断key是否存在
        if (map.containsKey(key)) {
            node = map.get(key);
            node.value = value;
            moveNode2FirstNode(node);
        } else {
            node = new Node(key, value);
            if (map.size() >= capacity) {
                map.remove(tail.prev.key);
                removeLastNode();
            }
            addNode2FirstNode(node);
        }
        map.put(key, node);
    }

    /**
     * 将节点移到第一个节点
     * @param node
     */
    public void moveNode2FirstNode (Node node) {

        // 1.删除当前节点原有位置
        Node prev = node.prev;
        Node next = node.next;

        prev.next = next;
        next.prev = prev;

        // 2.将节点添加到第一个节点
        addNode2FirstNode(node);
    }

    /**
     * 添加元素到第一个节点
     * @param node
     */
    public void addNode2FirstNode (Node node) {

        Node firstNode = head.next;

        head.next = node;
        node.prev = head;

        node.next = firstNode;
        firstNode.prev = node;
    }

    /**
     * 删除最后一个节点
     */
    public void removeLastNode () {
        Node lastNode = tail.prev;
        if (lastNode == tail) {
            return;
        }

        Node prev = lastNode.prev;
        prev.next = tail;
        tail.prev = prev;

        lastNode.next = null;
        lastNode.prev = null;
    }

    private class Node {
        Node prev;
        Node next;
        int key;
        int value;

        Node (int key, int value) {
            this.key = key;
            this.value = value;
        }
    }
}
