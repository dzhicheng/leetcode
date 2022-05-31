package com.dongzhic.Solution_202205;

import com.dongzhic.common.ListNode;
import com.dongzhic.common.TreeNode;
import com.google.common.collect.Maps;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author dongzhic
 * @Date 2022/5/18 22:15
 */
public class Solution {

    public static void main(String[] args) {
        double n = 1e-2;
        System.out.println(n);
    }


    /**
     * 30.求根节点到叶节点数字之和（129）
     * @param root
     * @return
     */
    public int sumNumbers1(TreeNode root) {
        int sum = 0;

        List<Integer> treePathList = new ArrayList<>();
        getBinaryTreePathNum(root, treePathList, "");
        if (treePathList.size() > 0) {
            for (int i = 0; i < treePathList.size(); i++) {
                sum += treePathList.get(i);
            }
        }

        return sum;
    }
    public void getBinaryTreePathNum (TreeNode root, List<Integer> treePathList, String str) {
        StringBuilder path = new StringBuilder(str);
        path.append(root.val);

        // 叶子节点
        if (root.left == null && root.right == null) {
            treePathList.add(Integer.valueOf(path.toString()));
        }

        if (root.left != null) {
            getBinaryTreePathNum(root.left, treePathList, path.toString());
        }

        if (root.right != null) {
            getBinaryTreePathNum(root.right, treePathList, path.toString());
        }
    }
    /**
     * 30.求根节点到叶节点数字之和（129）：通过dfs深度优先搜索
     * @param root
     * @return
     */
    public int sumNumbers2(TreeNode root) {
        int sum = 0;
        return dfsSumNumbers(root, 0);
    }
    public int dfsSumNumbers (TreeNode root, int prevSum) {
        if (root == null) {
            return 0;
        }

        int sum = prevSum * 10 + root.val;
        // 叶子节点
        if (root.left == null || root.right == null) {
            return sum;
        } else {
            return dfsSumNumbers(root.left, sum) + dfsSumNumbers(root.right, sum);
        }
    }
    /**
     * 30.求根节点到叶节点数字之和（129）：通过bfs广度优先搜索
     * @param root
     * @return
     */
    public int sumNumbers3(TreeNode root) {

        int sum = 0;
        if (root == null) {
            return sum;
        }

        // 存储节点
        Queue<TreeNode> nodeQueue = new ArrayDeque<>();
        // 存储根节点到当前节点的sum
        Queue<Integer> sumQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        sumQueue.offer(root.val);

        while (!nodeQueue.isEmpty()) {
            TreeNode currNode = nodeQueue.poll();
            int currSum = sumQueue.poll();

            // 叶子节点
            if (currNode.left == null && currNode.right == null) {
                sum += currSum;
            }

            if (currNode.left != null) {
                nodeQueue.offer(currNode.left);
                sumQueue.offer(currSum * 10 + currNode.left.val);
            }

            if (currNode.right != null) {
                nodeQueue.offer(currNode.right);
                sumQueue.offer(currSum * 10 + currNode.right.val);
            }
        }

        return sum;
    }

    /**
     * 29.二叉树的所有路径（257）
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        getBinaryTreePath(root, result, "");

        return result;
    }
    public void getBinaryTreePath (TreeNode root, List<String> pathList, String str) {

        StringBuilder path = new StringBuilder(str);
        path.append(root.val);

        // 叶子结点
        if (root.left == null && root.right == null) {
            pathList.add(path.toString());
        } else {
            path.append("->");
        }

        if (root.left != null) {
            getBinaryTreePath(root.left, pathList, path.toString());
        }

        if (root.right != null) {
            getBinaryTreePath(root.right, pathList, path.toString());
        }
    }

    /**
     * 28.二叉树的最近公共祖先：递归（236）
     *  1）p、q分别在左右子树
     *  2）p、q都在左子树
     *  3）p、q都在右子树
     *  4）p、q其中之一在根节点
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {

        if (root == null) {
            return null;
        }

        // 1.p、q其中一个是root节点
        if (root.val == p.val || root.val == q.val) {
            return root;
        }

        // 从左右子树查找最近公共祖先
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // p、q分布在左右子树
        if (left != null && right != null) {
            return root;
        }

        // p、q分布在右子树
        if (left == null) {
            return right;
        }

        // p、q分布在左子树
        if (right == null) {
            return left;
        }

        return null;
    }
    @Test
    public void test30 () {
        TreeNode t1 = new TreeNode(6);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(8);
        TreeNode t4 = new TreeNode(0);
        TreeNode t5 = new TreeNode(4);
        TreeNode t6 = new TreeNode(7);
        TreeNode t7 = new TreeNode(9);
        TreeNode t8 = new TreeNode(3);
        TreeNode t9 = new TreeNode(5);

        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        t7.left = t8;
        t7.right = t9;

        lowestCommonAncestor(t1, t2, t5);
    }

    /**
     * 27.二叉搜索树的最近公共祖先（235）
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestorOfSearchTree1(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> pPath = new ArrayList<>();
        List<TreeNode> qPath = new ArrayList<>();
        getPathOfSearchTree(root, p, pPath);
        getPathOfSearchTree(root, q, qPath);

        int index = 0;
        // 公共祖先节点
        TreeNode commonAncestor = null;
        while (index < pPath.size() && index < qPath.size()) {
            if (pPath.get(index) == qPath.get(index)) {
                commonAncestor = pPath.get(index);
            }
            index++;
        }

        return commonAncestor;
    }
    /**
     * 获取节点的路径：递归
     * @param root
     * @param node
     * @return
     */
    public void getPathOfSearchTree (TreeNode root, TreeNode targetNode, List<TreeNode> path) {
        List<TreeNode> result = new ArrayList<>();
        if (root == null) {
            return;
        }

        path.add(root);
        if (root.val > targetNode.val) {
            getPathOfSearchTree(root.left, targetNode, path);
        } else if (root.val < targetNode.val) {
            getPathOfSearchTree(root.right, targetNode, path);
        }
    }
    /**
     * 获取节点的路径：遍历
     * @param root
     * @param node
     * @return
     */
    public List<TreeNode> getPathOfSearchTree (TreeNode root, TreeNode targetNode) {
        List<TreeNode> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        TreeNode node = root;
        while (node != targetNode) {
            result.add(node);
            if (node.val > targetNode.val) {
                node = node.left;
            } else  {
                node = node.right;
            }
        }
        result.add(node);

        return  result;
    }
    public TreeNode lowestCommonAncestorOfSearchTree2(TreeNode root, TreeNode p, TreeNode q) {
        // 公共祖先节点
        TreeNode commonAncestor = null;
        if (root == null) {
            return commonAncestor;
        }

        TreeNode node = root;
        while (true) {
            if (node.val > p.val && node.val > q.val) {
                node = node.left;
            } else if (node.val < p.val && node.val < q.val) {
                node = node.right;
            } else {
                commonAncestor = node;
                break;
            }
        }

        return commonAncestor;
    }
    @Test
    public void test27 () {
        TreeNode t1 = new TreeNode(6);
        TreeNode t2 = new TreeNode(2);
        TreeNode t3 = new TreeNode(8);
        TreeNode t4 = new TreeNode(0);
        TreeNode t5 = new TreeNode(4);
        TreeNode t6 = new TreeNode(7);
        TreeNode t7 = new TreeNode(9);
        TreeNode t8 = new TreeNode(3);
        TreeNode t9 = new TreeNode(5);

        t1.left = t2;
        t1.right = t3;
        t2.left = t4;
        t2.right = t5;
        t3.left = t6;
        t3.right = t7;
        t7.left = t8;
        t7.right = t9;

        lowestCommonAncestorOfSearchTree1(t1, t2, t5);
    }


    /**
     * 26.二叉树的前序遍历（144）：递归
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        result.add(root.val);
        if (root.left != null) {
            result.addAll(preorderTraversal1(root.left));
        }
        if (root.right != null) {
            result.addAll(preorderTraversal1(root.right));
        }

        return result;
    }
    /**
     * 26.二叉树的前序遍历（144）：遍历
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode currNode = stack.pop();
            result.add(currNode.val);

            if (currNode.right != null) {
                stack.push(currNode.right);
            }

            if (currNode.left != null) {
                stack.push(currNode.left);
            }
        }

        return result;
    }

    /**
     * 25.链表中倒数第k个节点：两次遍历
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd1(ListNode head, int k) {

        ListNode node1 = head;

        int len = 0;
        while (node1 != null) {
            node1 = node1.next;
            len++;
        }

        int index = 0;
        ListNode node2 = head;
        while (index < (len - k)) {
            node2 = node2.next;
            index++;
        }

        return node2;
    }
    /**
     * 25.链表中倒数第k个节点：两个指针
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd2(ListNode head, int k) {
        ListNode slow = head;
        ListNode fast = head;

        // 1.先执行到正数第k个位置
        for (int i = 0; i < k; i++) {
            fast = fast.next;
        }

        // 2.快慢指针同时向后移动，快指针为空时，慢指针为倒数k位置
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        return slow;
    }

    /**
     * 24.x 的平方根，小数部分将被舍去  （69）：使用二分法
     * @param x
     * @return
     */
    public int mySqrt(int x) {

        if (x < 2) {
            return x;
        }

        int left = 1;
        int right = x / 2;
        while (left <= right) {
            int middle = (left + right) / 2;
            int num = x / middle;
            if (num == middle) {
                return middle;
            } else if (num > middle) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return right;
    }

    /**
     * 24.x 的平方根，扩展：保留/精确小数点后N位
     * @param x
     * @return
     */
    public double mySqrtExt(int x) {

        if (x < 2) {
            return x;
        }

        // 精确小数点后10位
        double n = 1e-10;

        double left = 1;
        double right = x / 2;
        while (left <= right) {
            double middle = (left + right) / 2;
            double target = x / middle;
            if (target == middle) {
                return middle;
            } else if (target > middle) {
                right = middle - n;
            } else {
                left = middle + n;
            }
        }

        return right;
    }

    /**
     * 23.寻找旋转排序数组中的最小值（153）
     *  数组中的值互不相同
     * @param nums
     * @return
     */
    public int findMinOfRevolveArray1(int[] nums) {

        int minValue = nums[0];
        int left = 0;
        int right = nums.length - 1;

        // 找无序部分，最左端元素
        while (left <= right) {
            int middle = (left + right) / 2;
            if (nums[left] <= nums[middle]) {
                // 无序在右侧
                if (middle < right && nums[middle] > nums[middle + 1]) {
                    minValue = Math.min(minValue, nums[middle + 1]);
                    break;
                }
                left = middle + 1;
            } else {
                // 无序在左侧
                if (middle > left && nums[middle] < nums[middle - 1]) {
                    minValue = Math.min(minValue, nums[middle]);
                    break;
                }
                right = middle - 1;
            }
        }

        return minValue;
    }
    @Test
    public void test24 () {
//        int[] nums = {4,5,6,7,0,1,2};
        int[] nums = {1,2};
        System.out.println( findMinOfRevolveArray1(nums));;
    }

    /**
     * 23.搜索旋转排序数组 II（81）
     *  数组中的值不必互不相同
     *  特殊情况，nums = {3,1,2,3,3,3,3}，target = 2时，左、中、右都相等，无法判断那边有序
     * @param nums
     * @param target
     * @return
     */
    public boolean searchRevolveArray2(int[] nums, int target) {

        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int middle = (left + right) / 2;
            if (nums[middle] == target) {
                return true;
            }

            if (nums[left] == nums[middle] && nums[middle] == nums[right]) {
                left++;
                right--;
            } else if (nums[left] <= nums[middle]) {
                // 左边有序
                if (nums[left] <= target && target <= nums[middle]) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else {
                // 右边有序
                if (nums[middle] <= target && target <= nums[right]) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }

        }

        return false;
    }
    public void test23 () {
        int[] nums = {3,1,2,3,3,3,3};
        int target = 2;
        searchRevolveArray2(nums, target);
    }

    /**
     * 22.搜索旋转排序数组（33）
     *  数组中的值互不相同
     * @param nums
     * @param target
     * @return
     */
    public int searchRevolveArray1(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

         while (left <= right) {
            int middle = (left + right) / 2;
            if (nums[middle] == target) {
                return middle;
            }

            // 两部分总会存在一部分有序
            if (nums[left] <= nums[middle]) {
                // 左部分有序
                if (nums[left] <= target && target < nums[middle]) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                }
            } else {
                // 右部分有序
                if (nums[middle] < target && target <= nums[right]) {
                    left = middle + 1;
                } else {
                    right = middle - 1;
                }
            }
        }

        return -1;
    }

    /**
     * 21.二叉树的锯齿形层序遍历（103）
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<List<TreeNode>> stack = new Stack<>();
        List<TreeNode> treeNodeList = new ArrayList<>();
        treeNodeList.add(root);
        stack.push(treeNodeList);

        // 奇数从左向右，偶数从右向左
        int index = 1;
        while (!stack.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            List<TreeNode> currTreeList = stack.pop();
            List<TreeNode> nextTreeList = new ArrayList<>();

            for (TreeNode treeNode : currTreeList) {
                list.add(treeNode.val);
                if (treeNode.left != null) {
                    nextTreeList.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    nextTreeList.add(treeNode.right);
                }
            }

            if (index % 2 == 0) {
                Collections.reverse(list);
            }

            if (nextTreeList.size() > 0) {
                stack.add(nextTreeList);
            }

            index ++;
            result.add(list);
        }

        return result;
    }
    @Test
    public void test21 () {
        TreeNode r1 = new TreeNode(1);
        TreeNode r2 = new TreeNode(2);
        TreeNode r3 = new TreeNode(3);
        TreeNode r4 = new TreeNode(4);
        TreeNode r5 = new TreeNode(5);
        TreeNode r6 = new TreeNode(6);
        TreeNode r7 = new TreeNode(7);

        r1.left = r2;
        r1.right = r3;
        r2.left = r4;
        r2.right = r5;
        r3.left = r6;
        r3.right = r7;

        zigzagLevelOrder(r1);
    }

    /**
     * 20.二叉树的层序遍历（102）:遍历
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder1(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            // 存储层节点的栈
            Stack<TreeNode> levelStack = new Stack<>();
            List<Integer> list = new ArrayList<>();

            while (!stack.isEmpty()) {
                TreeNode currNode = stack.pop();
                list.add(currNode.val);

                if (currNode.left != null) {
                    levelStack.push(currNode.left);
                }

                if (currNode.right != null) {
                    levelStack.push(currNode.right);
                }
            }

            if (!levelStack.isEmpty()) {
                while (!levelStack.isEmpty()) {
                    stack.push(levelStack.pop());
                }
            }

            result.add(list);
        }

        return result;
    }
    public List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<List<TreeNode>> stack = new Stack<>();
        List<TreeNode> rootList = new ArrayList<>();
        rootList.add(root);
        stack.push(rootList);

        while (!stack.isEmpty()) {
            List<TreeNode> currTreeNode = stack.pop();
            List<TreeNode> nextTreeNode = new ArrayList<>();
            List<Integer> list = new ArrayList<>();

            for (TreeNode treeNode : currTreeNode) {
                list.add(treeNode.val);
                if (treeNode.left != null) {
                    nextTreeNode.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    nextTreeNode.add(treeNode.right);
                }
            }

            if (nextTreeNode.size() > 0) {
                stack.push(nextTreeNode);
            }

            result.add(list);
        }

        return result;
    }

    /**
     * 19.爬楼梯（70）
     * @param n
     * @return
     */
    public int climbStairs(int n) {

        if (n < 3) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= n; i ++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    /**
     * 17.二叉树的中序遍历（94）：递归
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        List<Integer> leftTree = inorderTraversal1(root.left);
        result.addAll(leftTree);
        result.add(root.val);
        List<Integer> rightTree = inorderTraversal1(root.right);
        result.addAll(rightTree);

        return result;
    }
    /**
     * 17.二叉树的中序遍历（94）：遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode currNode = stack.pop();

            // 叶子节点 or 跟节点
            if (currNode.left == null && currNode.right == null) {
                result.add(currNode.val);
                continue;
            }

            if (currNode.right != null) {
                // 右
                stack.push(currNode.right);
            }

            // 根
            stack.push(new TreeNode(currNode.val));

            if (currNode.left != null) {
                // 左
                stack.push(currNode.left);
            }
        }

        return result;
    }

    /**
     * 16.排序数组（912）：快排
     * @param nums
     * @return
     */
    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }
    /**
     * 快排算法
     * @param nums
     * @param left
     * @param right
     */
    public void quickSort (int [] nums, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(nums, left, right);
            quickSort(nums, left, pivotIndex - 1);
            quickSort(nums, pivotIndex + 1, right);
        }
    }

    /**
     * 15.二分查找（704）
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {

        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {
            int middle = (left + right)/2;
            if (nums[middle] == target) {
                return middle;
            } else if (nums[middle] > target) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return -1;
    }

    /**
     * 14.字符串相加（415）
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings(String num1, String num2) {
        if (num1 == null || "".equals(num1)) {
            return num2;
        }

        if (num2 == null || "".equals(num2)) {
            return num1;
        }

        StringBuilder result = new StringBuilder();
        int index1 = num1.length() - 1;
        int index2 = num2.length() - 1;
        int carry = 0;
        while (index1 >= 0 || index2 >= 0) {
            int a = index1 >= 0 ? num1.charAt(index1) - '0' : 0;
            int b = index2 >= 0 ? num2.charAt(index2) - '0' : 0;

            int sum = a + b + carry;
            carry = sum > 9 ? 1 : 0;
            result.append(sum%10);

            index1--;
            index2--;
        }

        if (carry > 0) {
            result.append(1);
        }

        return result.reverse().toString();
    }
    @Test
    public void test14 () {
        addStrings("99", "1");
    }

    /**
     * 13.三数之和（15）：两数之和改造
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum1(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        // 排序，防止结果重复
        Arrays.sort(nums);
        Set<List<Integer>> set = new HashSet<>();
        for (int i = 0; i < nums.length - 2; i++) {
            int target = 0 - nums[i];
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = i + 1; j < nums.length; j++) {
                int num = nums[j];
                if (map.containsKey(target - num)) {
                    set.add(Arrays.asList(nums[i], target - num, num));
                } else {
                    map.put(num, j);
                }
            }
        }

        if (set.size() > 0) {
            result.addAll(set);
        }

        return result;
    }
    /**
     * 13.三数之和（15）：基于二分法
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum2(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        // 递增排序
        Arrays.sort(nums);

        Set<List<Integer>> set = new HashSet<>();
        for (int i = 0; i < nums.length - 2; i++) {
            int a = nums[i];
            // 递增，第一个数大于0，必不存在和为0
            if (a > 0) {
                break;
            }

            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = a + nums[left] + nums[right];
                if (sum == 0) {
                    set.add(Arrays.asList(a, nums[left], nums[right]));
                    left++;
                    right--;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }

        if (set.size() > 0) {
            result.addAll(set);
        }

        return result;
    }
    @Test
    public void test13 () {
        int[] nums = {-1,0,1,2,-1,-4};
        threeSum1(nums);
    }

    /**
     * 12.相交链表（160）
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        ListNode l1 = headA;
        ListNode l2 = headB;
        while (l1 != null || l2 != null) {

            if (l1 == null) {
                l1 = headB;
            }
            if (l2 == null) {
                l2 = headA;
            }

            if (l1 == l2) {
                return l1;
            }

            l1 = l1.next;
            l2 = l2.next;
        }

        return null;
    }
    @Test
    public void test12 () {
        ListNode l8 = new ListNode(8);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);
        l8.next = l4;
        l4.next = l5;

        ListNode a1 = new ListNode(4);
        ListNode a2 = new ListNode(1);
        a1.next = a2;
        a2.next = l8;

        ListNode b1 = new ListNode(5);
        ListNode b2 = new ListNode(6);
        ListNode b3 = new ListNode(1);
        b1.next = b2;
        b2.next = b3;
        b3.next = l8;

        System.out.println(getIntersectionNode(a1, b1));
    }
    
    /**
     * 11.环形链表（141）：快慢指针
     *  起始位置相同；
     *  已快指针判断
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        ListNode slowNode = head;
        ListNode fastNode = head;
        while (fastNode != null && fastNode.next != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next.next;
            if (slowNode == fastNode) {
                return true;
            }
        }
        return false;
    }

    /**
     * 10.合并两个有序数组（88）
     *  输入：nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
     *  输出：[1,2,2,3,5,6]
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        // num1的数组长度
        int index = m + n - 1;
        int indexM = m - 1;
        int indexN = n -1;
        while (indexN >= 0) {
            int indexNum;
            if (indexM < 0 || nums2[indexN] > nums1[indexM]) {
                indexNum = nums2[indexN--];;
            } else {
                indexNum = nums1[indexM--];
            }
            nums1[index--] = indexNum;
        }
    }

    /**
     * 9.有效的括号（20）
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        int len = s.length();
        if (len % 2 != 0) {
            return false;
        }

        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                if (stack.isEmpty() || !stack.peek().equals(map.get(c))) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        return stack.isEmpty();
    }

    /**
     * 8.数组中的第K个最大元素（215）
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        if (nums.length == 1) {
            return nums[0];
        }

        return baseQuickSort(nums, 0, nums.length - 1, nums.length- k);
    }
    /**
     * 快排算法
     * @param nums
     * @param left
     * @param right
     */
    public int baseQuickSort (int [] nums, int left, int right, int k) {
        // 基准数的位置
        int pivotIndex = partition(nums, left, right);
        if (pivotIndex == k) {
            return nums[pivotIndex];
        } else if (pivotIndex > k) {
            return baseQuickSort(nums, left, pivotIndex - 1, k);
        } else {
            return baseQuickSort(nums, pivotIndex + 1, right, k);
        }
    }
    public int partition (int [] nums, int left, int right) {
        // 获取基准数(随机)，并将基准数移到最后
        int pivotIndex = new Random().nextInt(right - left + 1) + left;
        swapNum(nums, pivotIndex, right);

        int i = left;
        int pivot = nums[right];
        for (int j = left; j < right; j++) {
            if (nums[j] < pivot) {
                swapNum(nums, i++, j);
            }
        }

        pivotIndex = i;
        swapNum(nums, i, right);
        return pivotIndex;
    }
    public void swapNum (int [] nums, int a, int b) {
        int num = nums[a];
        nums[a] = nums[b];
        nums[b] = num;
    }

    /**
     * 7.买卖股票的最佳时机（121）
     *  输入：[7,1,5,3,6,4]
     *  输出：5
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {

        // 记录每天的最大利润
        int maxProfit = 0;
        int minPrice = prices[0];

        for (int i = 1; i < prices.length; i++) {
            int currPrice = prices[i];
            // 当天最大利率
            int currProfit =  currPrice - minPrice;
            minPrice = Math.min(minPrice , currPrice);

            if (currProfit > maxProfit) {
                maxProfit = currProfit;
            }
        }

        return maxProfit;
    }
    @Test
    public void test7 () {
        int[] prices = {7,1,5,3,6,4};
        maxProfit(prices);
    }

    /**
     * 6.两数之和（1）
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = Maps.newHashMap();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (map.containsKey(target - num)) {
                return new int[]{map.get(target - num), i};
            }
            map.put(num, i);
        }
        return null;
    }

    /**
     * 4.合并两个有序链表（21）：依次遍历两个链表
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists1(ListNode list1, ListNode list2) {
        ListNode mergeNode = new ListNode(-1);

        ListNode currNode = mergeNode;
        while (list1 != null && list2 != null) {
            if (list1.val > list2.val) {
                currNode.next = list2;
                list2 = list2.next;
            } else {
                currNode.next = list1;
                list1 = list1.next;
            }

            currNode = currNode.next;
        }

        if (list1 != null) {
            currNode.next = list1;
        }
        if (list2 != null) {
            currNode.next = list2;
        }

        return mergeNode.next;
    }
    /**
     * 4.合并两个有序链表（21）：递归
     * @param list1
     * @param list2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        if (list1.val > list2.val) {
            ListNode nextNode = mergeTwoLists2(list1, list2.next);
            list2.next = nextNode;
            return list2;
        } else {
            ListNode nextNode = mergeTwoLists2(list1.next, list2);
            list1.next = nextNode;
            return list1;
        }
    }

    /**
     * 3.最大子数组和（53）
     *  输入：nums = [-2,1,-3,4,-1,2,1,-5,4]
     *  输出：6
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        int maxSubSum = nums[0];
        int currSubSum = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int num = nums[i];
            if (currSubSum < 0) {
                currSubSum = num;
            } else {
                currSubSum += num;
            }

            maxSubSum = Math.max(maxSubSum, currSubSum);
        }

        return maxSubSum;
    }
    @Test
    public void test3 () {
        int [] nums = {-2,1,-3,4,-1,2,1,-5,4};
        maxSubArray(nums);
    }

    /**
     * 2.无重复字符的最长子串（3）：暴力法
     *  输入: s = "abcabcbb"
     *  输出: 3
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring1(String s) {
        int maxSubLength = 0;
        if (s == null) {
            return maxSubLength;
        }
        if (s.length() == 0 || s.length() == 1) {
            return s.length();
        }

        for (int i = 0; i < s.length() - 1; i ++) {
            List<Character> list = new ArrayList<>();
            list.add(s.charAt(i));
            for (int j = i + 1; j < s.length(); j++) {
                if (list.contains(s.charAt(j))) {
                    break;
                }
                list.add(s.charAt(j));
            }
            maxSubLength = Math.max(maxSubLength, list.size());
        }

        return maxSubLength;
    }
    /**
     * 2.无重复字符的最长子串（3）：滑动窗口
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring2(String s) {

        int maxSubLength = 0;
        if (s == null) {
            return maxSubLength;
        }

        // 存放元素位置
        Map<Character, Integer> map = new HashMap<>(16);

        int slow = 0;
        for (int fast = 0; fast < s.length(); fast++) {
            char c = s.charAt(fast);
            if (map.containsKey(c)) {
                slow = Math.max(map.get(c) + 1, slow);
            }
            map.put(c, fast);
            maxSubLength = Math.max(maxSubLength, fast - slow + 1);
        }

        return maxSubLength;
    }
    @Test
    public void test2 () {
        lengthOfLongestSubstring2("abbaabcbb");
    }

    /**
     * 1.反转链表（206）：迭代
     *
     * @param head
     * @return
     */
    public ListNode reverseList1(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode reverseNode = null;
        while (head.next != null) {
            ListNode nextNode = head.next;

            head.next = reverseNode;
            reverseNode = head;

            head = nextNode;
        }

        return reverseNode;
    }

    /**
     * 1.反转链表（206）：递归
     *
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode reverseNode = reverseList2(head.next);
        ListNode nextNode = head.next;
        nextNode.next = head;
        head.next = null;

        return reverseNode;
    }
}
