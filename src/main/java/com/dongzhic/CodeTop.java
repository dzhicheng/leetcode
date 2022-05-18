package com.dongzhic;

import com.dongzhic.common.ListNode;
import com.dongzhic.common.TreeNode;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @Author dongzhic
 * @Date 2021/9/23 17:10
 */
public class CodeTop {

    public static void main(String[] args) throws Exception {


        int[] nums = {3,2,1,5,6,4};
        System.out.println(findKthLargest(nums, 2));

    }

    /**
     * 快排：47 29 71 99 78 19 24 47
     *
     *  24 29 19 47 78 99 71 47
     * @param nums
     * @return
     */
    public static int[] sortArray(int[] nums) {

        if (nums == null || nums.length == 1) {
            return nums;
        }

        quickSort(nums, 0, nums.length-1);

        return nums;
    }
    public static void quickSort(int[] nums, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(nums, left, right);
            quickSort(nums, left, pivotIndex - 1);
            quickSort(nums, pivotIndex + 1, right);
        }
    }
    public static int partition (int[] nums, int left, int right) {

        // 随机选取一个数座位基准数
        int pivotIndex = new Random().nextInt(right - left + 1) + left;
        swap(nums, pivotIndex, right);

        int pivot = nums[right];
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (nums[j] < pivot) {
                i += 1;
                swap(nums, i, j);
            }
        }

        pivotIndex = i + 1;
        swap(nums, pivotIndex, right);

        return pivotIndex;
    }
    public static void quickSort2(int[] nums, int left, int right) {

        if (left >= right) {
            return;
        }

        // 基准值位置
        int i = left + 1;
        int j = right;
        int pivot = nums[left];
        int pivotIndex = left;

        while (i < j) {

            while (i< j && nums[j] >= pivot) {
                j--;
            }
            if (i >= j) {
                break;
            }
            swap(nums, pivotIndex, j);
            pivotIndex = j;


            while (i< j && nums[i] <= pivot) {
                i++;
            }
            if (i >= j) {
                break;
            }
            swap(nums, pivotIndex, i);
            pivotIndex = i;

        }

        quickSort2(nums, left, pivotIndex - 1);
        quickSort2(nums, pivotIndex + 1, right);

    }
    public static void swap (int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    /**
     * 215. 数组中的第K个最大元素：基于快排
     * 输入: [3,2,1,5,6,4] 和 k = 2
     * 输出: 5
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest(int[] nums, int k) {
        int left = 0;
        int right = nums.length - 1;
        return baseQuickSort(nums, left, right, right - k + 1);
    }

    public static int baseQuickSort (int[] nums, int left, int right, int k) {

        int pivotIndex = partition(nums, left, right);
        if (pivotIndex == k) {
            return nums[k];
        } else if (pivotIndex > k) {
            // 左边
            return baseQuickSort(nums, left, pivotIndex - 1, k);
        } else {
            // 右边
            return baseQuickSort(nums, pivotIndex + 1, right, k);
        }

    }

    /**
     * 215. 数组中的第K个最大元素
     * @param nums
     * @param k
     * @return
     */
    public static int findKthLargest2(int[] nums, int k) {

        Arrays.sort(nums);

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            list.add(nums[i]);
        }

        for (int j = list.size()-1; j >= 0; j--) {
            if (j == list.size()-k) {
                return list.get(j);
            }
        }

        return -1;
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

            int val1 = l1.val;
            int val2 = l2.val;

            if (val1 >= val2) {
                tempNode.next = new ListNode(val2);
                l2 = l2.next;
            } else {
                tempNode.next = new ListNode(val1);
                l1 = l1.next;
            }

            tempNode = tempNode.next;

        }

        if (l1 == null) {
            tempNode.next = l2;
        }
        if (l2 == null) {
            tempNode.next = l1;
        }

        return newNode.next;
    }

    /**
     * 21. 合并两个有序链表:递归
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {

        // 判空
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        if (l1.val >= l2.val) {
            ListNode nextNode = mergeTwoLists2(l1, l2.next);
            l2.next = nextNode;
            return l2;
        } else {
            ListNode nextNode = mergeTwoLists2(l1.next, l2);
            l1.next = nextNode;
            return l1;
        }

    }

    /**
     * 3. 无重复字符的最长子串
     *  输入: s = "abcabcbb"
     *  输出: 3
     *  解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {

        int maxLength = 0;

        Map<Character, Integer> map = new HashMap<>();

        int slow = 0;
        for (int fast = 0; fast < s.length(); fast++) {

            char c = s.charAt(fast);

            if (map.containsKey(c)) {
                // slow移动到下一个位置
                slow = Math.max(slow, map.get(c) + 1);
            }

            map.put(c, fast);
            maxLength = Math.max(maxLength, fast - slow + 1);
        }


        return maxLength;
    }

    /**
     * 206. 反转链表：递归
     * @param head
     * @return
     */
    public ListNode reverseList1(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode nextNode = reverseList1(head.next);
        head.next.next = head;
        head.next = null;

        return nextNode;
    }

    /**
     * 206. 反转链表：迭代
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {
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
