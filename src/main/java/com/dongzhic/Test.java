package com.dongzhic;

import com.dongzhic.common.ListNode;

/**
 * @Author dongzhic
 * @Date 5/26/21 10:49 PM
 */
public class Test {

    public static void main(String[] args) {


        System.out.println("11111111111");
    }

    /**
     * 21. 合并两个有序链表
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode newNode = new ListNode(-1);
        ListNode tempNode = newNode;
        while (l1 != null && l2 != null) {
            if (l1.val > l2.val) {
                tempNode.next = new ListNode(l2.val);
                l2 = l2.next;
            } else {
                tempNode.next = new ListNode(l1.val);
                l1 = l1.next;
            }
            tempNode = tempNode.next;
        }

        if (l1 != null) {
            tempNode.next = l1;
        }
        if (l2 != null) {
            tempNode.next = l2;
        }

        return newNode.next;
    }
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val > l2.val) {
            ListNode newNode = mergeTwoLists2(l1, l2.next);
            l2.next = newNode;
            return l2;
        } else {
            ListNode newNode = mergeTwoLists2(l1.next, l2);
            l1.next = newNode;
            return l1;
        }
    }


    /**
     * 206. 反转链表
     * @param head
     * @return
     */
    public ListNode reverseList1(ListNode head) {
        // 递归

        if (head == null || head.next == null) {
            return head;
        }

        ListNode nextNode = reverseList1(head.next);
        head.next.next = head;
        head.next = null;

        return nextNode;
    }
    public ListNode reverseList2(ListNode head) {
        // 迭代

        if (head == null || head.next == null) {
            return head;
        }

        ListNode newNode = null;
        while (head != null) {
            ListNode nextNode = head.next;

            head.next = newNode;
            newNode = head;

            head = nextNode;
        }

        return newNode;
    }

}
