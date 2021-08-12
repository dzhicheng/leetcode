package com.dongzhic.common;

/**
 * @Author dongzhic
 * @Date 2/23/21 10:40 PM
 */
public class ListNode {

     public int val;
     public ListNode next;

     public ListNode() {}

     public ListNode(int val) {
         this.val = val;
     }

     public ListNode(int val, ListNode next) {
         this.val = val; this.next = next;
     }
}
