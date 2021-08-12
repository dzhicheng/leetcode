package com.dongzhic.simple;

import com.dongzhic.common.ListNode;
import com.dongzhic.common.TreeNode;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;

import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 按leetCode出现频率由低到高
 * @Author dongzhic
 * @Date 2/23/21 10:02 PM
 */
public class App {

    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();
        getImportance(employees, 1);

    }

    /**
     * 690. 员工的重要性
     * @param employees
     * @param id
     * @return
     */
    public static int getImportance(List<Employee> employees, int id) {

        int importance = 0;
        List<Integer> subordinates = new ArrayList<>();

        Employee employee = new Employee();
        for (Employee emp : employees) {
            if (emp.id == id) {
                employee = emp;
                break;
            }
        }

        if (employee.id != 0) {
            importance = employee.importance;
            subordinates = employee.subordinates;

            for (Integer empId : subordinates) {
                importance += getImportance(employees, empId);
            }
        }

        return importance;
    }

    /**
     * 1480. 一维数组的动态和
     * @param nums
     * @return
     */
    public int[] runningSum(int[] nums) {

        int len = nums.length;
        int[] res = new int[len];
        res[0] = nums[0];

        for (int i = 1; i < len; i++) {
            res[i] = res[i-1] + nums[i];
        }

        return res;
    }

    /**
     * 350. 两个数组的交集 II
     *  map+list, key存值，value存次数
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect1(int[] nums1, int[] nums2) {

        List<Integer> list = new ArrayList<>();

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) {
            int num1 = nums1[i];
            if (map.containsKey(num1)) {
                map.put(nums1[i], map.get(num1) + 1);
            } else {
                map.put(nums1[i], 1);
            }
        }

        for (int j = 0; j < nums2.length; j++) {
            int num2 = nums2[j];
            if (map.containsKey(num2)) {
                int count = map.get(num2);
                if (count > 0) {
                    list.add(num2);
                    map.put(num2, map.get(num2)-1);
                }
            }
        }

        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 350. 两个数组的交集 II
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect2(int[] nums1, int[] nums2) {

        List<Integer> res = new ArrayList<>();

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int index1 = 0;
        int index2 = 0;

        while (index1 < nums1.length && index2 < nums2.length) {

            int num1 = nums1[index1];
            int num2 = nums2[index2];
            if (num1 == num2) {
                res.add(num1);
                index1++;
                index2++;
            } else if (num1 > num2) {
                index2++;
            } else {
                index1++;
            }

        }

        return res.stream().mapToInt(Integer::valueOf).toArray();
    }

    /**
     * 145. 二叉树的后序遍历
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {

        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        if (root.left != null) {
            List<Integer> leftList = postorderTraversal(root.left);
            res.addAll(leftList);
        }

        if (root.right != null) {
            List<Integer> rightList = postorderTraversal(root.right);
            res.addAll(rightList);
        }

        res.add(root.val);

        return res;
    }

    /**
     * 122. 买卖股票的最佳时机 II
     * @param prices
     * @return
     */
    public static int maxProfitII(int[] prices) {

        // 最大利润
        int maxProfit = 0;

        if (prices == null || prices.length < 2) {
            return maxProfit;
        }

        // 交易天数
        int days = prices.length - 1;
        // 前一天的价格
        int minPrice = prices[0];
        boolean isSell = false;
        // 从第二天开始计算
        for (int i = 1; i < prices.length; i++) {

            // 当前价格
            int currPrice = prices[i];
            // 明天的价格
            int nextPrice;
            if (i == prices.length-1) {
                nextPrice = 0;
            } else {
                nextPrice = prices[i+1];
            }

            // 买股票
            if (isSell) {
                minPrice = currPrice;
                isSell = false;
                continue;
            }

            if (currPrice <=  minPrice) {
                minPrice = currPrice;
                continue;
            }

            // 第二天会跌，卖股票
            if (currPrice > nextPrice) {
                maxProfit += currPrice - minPrice;
                isSell = true;
            }
        }

        return maxProfit;
    }

    /**
     * 122. 买卖股票的最佳时机 II：贪心算法
     * @param prices
     * @return
     */
    public static int maxProfitII2(int[] prices) {
        // 最大利润
        int maxProfit = 0;

        if (prices == null || prices.length < 2) {
            return maxProfit;
        }

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > prices[i-1]) {
                maxProfit += prices[i] - prices[i-1];
            }
        }

        return maxProfit;
    }

    /**
     *
     * 867. 转置矩阵
     * @param matrix
     * @return
     */
    public static int[][] transpose(int[][] matrix) {

        int rowLength = matrix.length;
        int columnLength = matrix[0].length;

        int[][] res = new int[columnLength][rowLength];

        for (int i = 0; i < columnLength; i++) {
            for (int j = 0; j < rowLength; j ++) {
                res[i][j] = matrix[j][i];
            }
        }

        return res;
    }

    /**
     *  晒选+判断
     * @param s
     * @return
     */
    public static boolean isPalindrome1(String s) {

        if (s == null || "".equals(s)
                || s.length() == 1) {
            return true;
        }

        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isLetterOrDigit(c)) {
                sb.append(Character.toLowerCase(c));
            }
        }

        int left = 0;
        int right = sb.length()-1;
        while (left <= right) {
            if (sb.charAt(left) != sb.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * 双指针
     * @param s
     * @return
     */
    public static boolean isPalindrome2(String s) {

        if (s == null || "".equals(s)
                || s.length() == 1) {
            return true;
        }

        int left = 0;
        int right = s.length()-1;

        while (left <= right) {

            char cLeft = Character.toLowerCase(s.charAt(left));
            char cRight = Character.toLowerCase(s.charAt(right));

            if (!Character.isLetterOrDigit(cLeft)) {
                left++;
                continue;
            }

            if (!Character.isLetterOrDigit(cRight)) {
                right--;
                continue;
            }

            if (cLeft != cRight) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    /**
     * 1047. 删除字符串中的所有相邻重复项
     * @param s
     * @return
     */
    public static String removeDuplicates(String s) {
        if (s == null || "".equals(s) ||
                s.length() == 1) {
            return s;
        }

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (stack.isEmpty()) {
                stack.push(c);
            } else {
                if (c != stack.peek()) {
                    stack.push(c);
                } else {
                    stack.pop();
                }
            }
        }

        StringBuffer res = new StringBuffer();
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }

        return res.reverse().toString();
    }


    /**
     * 69. x 的平方根
     * @param x
     * @return
     */
    public int mySqrt(int x) {

        if (x == 0 || x == 1) {
            return x;
        }

        int left = 1;
        int right = x/2;

        while (left <= right) {
            int middle = (left + right)/2;
            int num = middle * middle;
            if (num > x) {
                right = middle - 1;
            } else if (num < x) {
                left = middle + 1;
            } else {
                return middle;
            }
        }

        // 由于while结束时，left > right，由取值小的
        return right;
    }

    /**
     * 27. 移除元素
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {

        int left = 0;
        for (int right = 0; right < nums.length; right ++) {
            if (nums[right] != val) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;

                left++;
            }
        }

        return left;
    }

    /**
     * 108. 将有序数组转换为二叉搜索树
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {



        return null;
    }

    /**
     * 110. 平衡二叉树：暴力法，有重复计算
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {

        if (root == null) {
            return true;
        }

//        boolean b = Math.abs(hight1(root.left) - hight1(root.right)) <= 1;
//
//
//        return b && isBalanced(root.left) && isBalanced(root.right);

        return hight2(root) != -1;
    }

    public int hight1 (TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftMaxDepth = hight1(root.left);
        int rightMaxDepth = hight1(root.right);

        return Math.max(leftMaxDepth, rightMaxDepth) + 1;
    }

    /**
     * 优化：减少重复计算
     * @param root
     * @return -1：不是平衡二叉树
     */
    public int hight2 (TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftDepth = hight2(root.left);
        if (leftDepth == -1) {
            return -1;
        }

        int rightDepth = hight2(root.right);
        if (rightDepth == -1) {
            return -1;
        }

        if (Math.abs(leftDepth -rightDepth) > 1) {
            return -1;
        }

        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 树
     * 剑指 Offer 27. 二叉树的镜像 == 226. 翻转二叉树
     * @param root
     * @return
     */
    public TreeNode mirrorTree(TreeNode root) {

        if (root == null) {
            return null;
        }

        TreeNode leftTreeNode = root.left;
        TreeNode rightTreeNode = root.right;

        root.left = rightTreeNode;
        root.right = leftTreeNode;

        mirrorTree(root.left);
        mirrorTree(root.right);

        return root;
    }

    /**
     * 树
     * 257. 二叉树的所有路径
     * @param root
     * @return
     */
    public static List<String> binaryTreePaths(TreeNode root) {

        List<String> list = new ArrayList<>();

        if (root == null) {
            return list;
        }

        binaryTreePaths(root, list, "");

        return list;
    }

    public static void binaryTreePaths(TreeNode root, List<String> list, String path) {

        StringBuffer sb = new StringBuffer(path);
        sb.append(root.val);

        // 叶子节点
        if (root.left == null && root.right == null) {
            list.add(sb.toString());
        } else {
            sb.append("->");
            if (root.left != null) {
                binaryTreePaths(root.left, list, sb.toString());
            }
            if (root.right != null) {
                binaryTreePaths(root.right, list, sb.toString());
            }
        }


    }


    /**
     * 树
     * 剑指 Offer 55 - I. 二叉树的深度
     * @param root
     * @return
     */
    public static int maxDepthNew1(TreeNode root) {

        if (root == null) {
            return 0;
        }

        return maxDepth(root) + 1;
    }


    /**
     * 461. 汉明距离
     * @param x
     * @param y
     * @return
     */
    public int hammingDistance(int x, int y) {



        return 0;
    }


    /**
     * 448. 找到所有数组中消失的数字
     *  输入:
     *  [4,3,2,7,8,2,3,1]
     *
     *  输出:
     *  [5,6]
     * @param nums
     * @return
     */
    public static List<Integer> findDisappearedNumbers(int[] nums) {

        List<Integer> list = new ArrayList<>();

        if (nums == null) {
            return list;
        }

        int index = 0;
        while (index < nums.length) {

            int targetIndex = nums[index]-1;
            if (nums[targetIndex] == (targetIndex + 1)) {
                index++;
            } else {
                int temp = nums[targetIndex];
                nums[targetIndex] = nums[index];
                nums[index] = temp;
            }
        }

        for (int i = 0; i < nums.length; i ++) {
            if (nums[i] != (i+1)) {
                list.add(i+1);
            }
        }

        return list;
    }


    /**
     * 338. 比特位计数
     * @param n
     * @return
     */
    public int[] countBits(int n) {


        return null;
    }




    /**
     * 169. 多数元素
     * @param nums
     * @return
     */
    public static int majorityElement(int[] nums) {

        if (nums == null) {
            return -1;
        }

        int n = nums.length;

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i ++) {
            int key = nums[i];
            if (map.containsKey(key)) {
                int value = map.get(key);
                map.put(key, value+1);
            } else {
                map.put(nums[i], 1);
            }
        }

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > n/2) {
                return entry.getKey();
            }
        }

        return -1;
    }

    /**
     * 543. 二叉树的直径
     *  左子树深度 + 右子树深度 + 1
     * @param root
     * @return
     */
    // 最长路径值
    private static int maxDepth = 0;
    public static int diameterOfBinaryTree(TreeNode root) {

        if (root == null) {
            return 0;
        }

        maxDepthNew(root);

        return maxDepth;
    }
    public static int maxDepthNew (TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftDepth = maxDepthNew(root.left);
        int rightDepth = maxDepthNew(root.right);

        maxDepth = Math.max(maxDepth, leftDepth + rightDepth);

        return Math.max(leftDepth, rightDepth) + 1;
    }




    /**
     * 94. 二叉树的中序遍历
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> list = new ArrayList<>();

        if (root == null ) {
            return list;
        }

        // 1.添加左子树元素
        List<Integer> leftList;
        if (root.left != null) {
            leftList = inorderTraversal(root.left);
            if (leftList != null && leftList.size() > 0) {

                list.addAll(leftList);
            }
        }

        // 2.添加根节点元素
        list.add(root.val);

        // 3.添加右子树元素
        List<Integer> rightList;
        if (root.right != null) {
            rightList = inorderTraversal(root.right);
            if (rightList != null && rightList.size() > 0) {

                list.addAll(rightList);
            }
        }


        return list;
    }

    /**
     * 112. 路径总和：DFS深度优先搜索
     * @param root
     * @param targetSum
     * @return
     */
    public static boolean hasPathSum(TreeNode root, int targetSum) {

        if (root == null) {
            return false;
        }

        // 根节点的值
        int rootValue = root.val;

        // 叶子节点
        if (root.left == null && root.right == null) {
            if (rootValue == targetSum) {
                return true;
            } else {
                return false;
            }
        }

        // 判断左子树是否存在，存在直接返回
        boolean b1 = false;
        if (root.left != null) {
            b1 = hasPathSum(root.left, targetSum - rootValue);
        }
        if (b1) {
            return true;
        }

        // 判断右子树是否存在，存在直接返回
        boolean b2 = false;
        if (root.right != null) {
            b2 = hasPathSum(root.right, targetSum - rootValue);
        }
        if (b2) {
            return true;
        }

        return false;
    }




    /**
     * 104. 二叉树的最大深度：递归
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftMaxDepth = maxDepth(root.left);
        int rightMaxDepth = maxDepth(root.right);

        return Math.max(leftMaxDepth, rightMaxDepth) + 1;
    }

    /**
     * 680. 验证回文字符串 Ⅱ
     * 输入: "abca"
     * 输出: True
     * 解释: 你可以删除c字符。
     * @param s
     * @return
     */
    private static int deleteCount = 0;
    public static boolean validPalindrome(String s) {


        char[] chars = s.toCharArray();

        int low = 0;
        int high = chars.length-1;

        while (low <= high) {
            if (chars[low] != chars[high]) {
               deleteCount++;
               if (deleteCount > 1) {
                   return false;
               }
               boolean b1 = validPalindrome(s.substring(low, high));
               boolean b2 = validPalindrome(s.substring(low+1, high+1));
               if (b1 || b2) {
                  return true;
               } else {
                   return false;
               }
            }
            low++;
            high--;
        }

        return true;

    }

    /**
     * 35. 搜索插入位置
     * @param nums
     * @param target
     * @return
     */
    public static int searchInsert(int[] nums, int target) {

        int low = 0;
        int high = nums.length-1;

        int middle = 0;
        while (low <= high) {
            middle = (low + high)/2;

            if (nums[middle] > target) {
                high = middle -1;
            } else if (nums[middle] < target) {
                low = middle + 1;
            } else {
                return middle;
            }
        }

        if (nums[middle] < target) {
            middle++;
        }

        return middle;
    }

    /**
     * 101. 对称二叉树
     * @param root
     * @return
     */
    public static boolean isSymmetric(TreeNode root) {

        if (root == null) {
            return false;
        }

        return isSymmetric(root.left, root.right);
    }
    public static boolean isSymmetric(TreeNode root1, TreeNode root2) {

        if (root1 == null && root2 == null) {
           return true;
        }

        if (root1 == null || root2 == null) {
            return false;
        }

        if (root1.val == root2.val) {

            boolean b1 = isSymmetric(root1.left, root2.right);
            boolean b2 = isSymmetric(root1.right, root2.left);

            if (b1 && b2) {
                return true;
            }

        }

        return false;
    }



    /**
     * 704. 二分查找
     * 输入: nums = [-1,0,3,5,9,12], target = 9
     * 输出: 4
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {

        int low = 0;
        int high = nums.length-1;

        while (low <= high) {
            int middle = (low + high)/2;
            if (nums[middle] > target) {
                high = middle - 1;
            } else if (nums[middle] < target) {
                low = middle + 1;
            } else {
                return middle;
            }
        }

        return -1;
    }

    /**
     * 160. 相交链表：暴力法
     * @param headA
     * @param headB
     * @return
     */
    public static ListNode getIntersectionNode1(ListNode headA, ListNode headB) {

        if (headA == null || headB == null) {
            return null;
        }

        while (headA != null) {

            ListNode newHeadB = headB;
            while (newHeadB != null) {

                if (headA == newHeadB) {
                    return headA;
                }

                newHeadB = newHeadB.next;
            }

            headA = headA.next;
        }

        return null;
    }

    /**
     * 160. 相交链表：哈希表法
     * @param headA
     * @param headB
     * @return
     */
    public static ListNode getIntersectionNode2(ListNode headA, ListNode headB) {

        if (headA == null || headB == null) {
            return null;
        }

        Set<ListNode> set = new HashSet<>();

        ListNode head1 = headA;
        while (head1 != null) {
            set.add(head1);
            head1 = head1.next;
        }

        ListNode head2 = headB;
        while (head2 != null) {
            if (set.contains(head2)) {
                return head2;
            }
            head2 = head2.next;
        }

        return null;
    }

    /**
     * 160. 相交链表：双指针法
     * @param headA
     * @param headB
     * @return
     */
    public static ListNode getIntersectionNode3(ListNode headA, ListNode headB) {
        // 特判
        if (headA == null || headB == null) {
            return null;
        }

        ListNode head1 = headA;
        ListNode head2 = headB;

        while (head1 != head2) {
            if (head1 != null) {
                head1 = head1.next;
            } else {
                head1 = headB;
            }

            if (head2 != null) {
                head2 = head2.next;
            } else {
                head2 = headA;
            }
        }

        return head1;

    }

    /**
     * 617. 合并二叉树
     * @param root1
     * @param root2
     * @return
     */
    public static TreeNode mergeTrees(TreeNode root1, TreeNode root2) {

        if (root1 == null) {
            return root2;
        }

        if (root2 == null) {
            return root1;
        }

        // 1.父节点相加
        if (root2 != null) {
           root1.val = root1.val + root2.val;
        }

        // 2.左子树
        if (root2.left != null) {

            if (root1.left != null) {
                mergeTrees(root1.left, root2.left);
            } else {
                root1.left = root2.left;
            }

        }

        // 3.右子树
        if (root2.right != null) {

            if (root1.right != null) {
                mergeTrees(root1.right, root2.right);
            } else {
                root1.right = root2.right;
            }

        }

        return root1;
    }

    /**
     * 234. 回文链表
     * 输入: 1->2
     * 输出: false
     *
     * 输入: 1->2->2->1
     * 输出: true
     *
     * 1->2->3->4
     *
     * @param head
     * @return
     */
    public static boolean isPalindrome(ListNode head) {

        if (head == null || head.next == null) {
            return true;
        }

        List<Integer> list = new ArrayList<>();
        ListNode newHead = head;
        while (newHead != null) {
            list.add(newHead.val);
            newHead = newHead.next;
        }

        int i = 0;
        int j = list.size()-1;
        while (i < list.size()/2) {
            if (list.get(i) != list.get(j)) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }

    /**
     * 344. 反转字符串
     * 不要给另外的数组分配额外的空间，
     * 使用 O(1) 的额外空间
     * 输入：["h","e","l","l","o"]
     * 输出：["o","l","l","e","h"]
     * @param s
     */
    public static void reverseString(char[] s) {

        int i = 0;
        int j = s.length-1;
        while (i < s.length/2) {

            char c = s[i];
            s[i] = s[j];
            s[j] = c;

            i++;
            j--;
        }

    }

    /**
     * 283. 移动零
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * @param nums
     */
    public static void moveZeroes1(int[] nums) {

        // 记录遍历时，为0的索引位置
        int i = 0;
        // 记录遍历时，不为0的索引位置
        int j = 0;

        while (j < nums.length) {

            if (nums[j] != 0) {
                int temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;

                i++;

            }
            j++;

        }

    }

    /**
     * 283. 移动零
     * 输入: [0,1,0,3,12]
     * 输出: [1,3,12,0,0]
     * @param nums
     */
    public static void moveZeroes2(int[] nums) {
        
        // 指向0的位置
        int slow = 0;
        int fast = 0;

        while (fast < nums.length) {

            if (nums[fast] == 0) {
                fast++;
            } else {
                nums[slow++] = nums[fast++];
            }

        }

        for (int i = slow; i < nums.length; i ++) {
            nums[i] = 0;
        }
    }


    /**
     * 226. 翻转二叉树
     * @param root
     * @return
     */
    public static TreeNode invertTree(TreeNode root) {

        if (root == null) {
            return root;
        }

        TreeNode left = root.left;
        TreeNode right = root.right;

        root.left = right;
        root.right = left;

        if (left != null) {
            invertTree(left);
        }

        if (right != null) {
            invertTree(right);
        }

        return root;
    }

    /**
     * 28. 实现 strStr()
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {

        if (haystack == null || needle == null) {
           return 0;
        }

        for (int i = 0; i < haystack.length() - needle.length() + 1; i ++) {
           boolean flag = true;
           for (int j = 0; j < needle.length(); j ++) {
               if (haystack.charAt(i+j) != needle.charAt(j)) {
                   flag = false;
                   break;
               }
           }

           if (flag) {
               return i;
           }
        }

        return -1;
    }

    /**
     * 26. 删除有序数组中的重复项
     * @param nums
     * @return
     */
    public static int removeDuplicates(int[] nums) {

        if (nums == null) {
            return 0;
        }

        // 记录不同元素位置
        int slow = 1;
        // 遍历数组时索引
        int fast = 1;

        while (fast < nums.length) {
            if (nums[fast] != nums[fast-1]) {
                nums[slow] = nums[fast];
                slow++;
            }

            fast++;
        }

        return slow;
    }


    /**
     * 136. 只出现一次的数字:不使用额外空间
     * @param nums
     * @return
     */
    public static int singleNumber1(int[] nums) {

        Arrays.sort(nums);
        int result = nums[0];

        int i = 0;
        while (i < nums.length - 2) {
            if (nums[i] != nums[i + 1] && nums[i + 1] == nums[i + 2]) {
                return nums[i];
            } else {
                i += 2;
            }
        }

        if (i == nums.length - 1) {
            result = nums[nums.length - 1];
        }

        return result;
    }

    /**
     * 136. 只出现一次的数字:使用额外空间-map
     * @param nums
     * @return
     */
    public static int singleNumber2(int[] nums) {

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i ++) {
            if (set.contains(nums[i])) {
                set.remove(nums[i]);
            } else {
                set.add(nums[i]);
            }
        }

        Integer result = 0;
        for (Integer value : set) {
            result = value;
        }

        return result;
    }

    /**
     * 83. 删除排序链表中的重复元素
     * @param head
     * @return
     */
    public static ListNode deleteDuplicates(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode currNode = head;

        while (currNode.next != null) {

            ListNode nextNode = currNode.next;
            if (currNode.val == nextNode.val) {
                currNode.next = nextNode.next;
            } else {
                currNode = currNode.next;
            }

        }

        return head;
    }

    /**
     * 349. 两个数组的交集 map+list
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection1(int[] nums1, int[] nums2) {

        if (nums1 == null || nums2 == null) {
            return null;
        }

        List<Integer> list = new ArrayList<>();

        // 将数组元素存放在map中
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums1.length; i ++) {
            map.put(nums1[i], 1);
        }

        for (int j = 0; j < nums2.length; j ++) {

            int number = nums2[j];
            Integer value = map.get(nums2[j]);

            if (value != null) {
                if (value == 1) {
                    list.add(nums2[j]);
                }
                map.put(number, value+1);
            }

        }

        if (list.size() < 0) {
            return null;
        }

        // 返回数组
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i ++) {
            result[i] = list.get(i);
        }

        return result;
    }

    /**
     * 349. 两个数组的交集 两个set
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersection2(int[] nums1, int[] nums2) {

        // 存放nums1元素，去重
        Set<Integer> set1 = new HashSet<>();
        // 存放交集元素
        Set<Integer> set2 = new HashSet<>();

        for (int i = 0; i < nums1.length; i ++) {
            set1.add(nums1[i]);
        }

        for (int j = 0; j < nums2.length; j ++) {
            if (set1.contains(nums2[j])) {
                set2.add(nums2[j]);
            }
        }

        int[] result = new int[set2.size()];
        int k = 0;
        for (Integer num : set2) {
            result[k] = num;
            k++;
        }

        return result;
    }


    /**
     * 118. 杨辉三角：动态规划
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate(int numRows) {

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < numRows; i ++) {

            List<Integer> list = new ArrayList<>();
            int row = i + 1;

            for (int j = 0; j < row; j ++) {
                if (j == 0 || j == i) {
                    list.add(1);
                } else {
                    List<Integer> prev = result.get(i-1);
                    int num = prev.get(j) + prev.get(j-1);
                    list.add(num);
                }
            }

            result.add(list);

        }

        return result;
    }

    /**
     * 141. 环形链表
     * @param head
     * @return
     */
    public static boolean hasCycle1(ListNode head) {


        Map<Integer, Integer> map = new HashMap<>();
        while (head != null) {

            int hashCode = head.hashCode();
            if (map.containsKey(hashCode)) {
               return true;
            }
            map.put(head.hashCode(), 1);

            head = head.next;

        }

        return false;
    }


    /**
     * 141. 环形链表
     * @param head
     * @return
     */
    public static boolean hasCycle2(ListNode head) {

        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {

            if (slow == fast) {
                return true;
            }

            slow = slow.next;
            fast = fast.next.next;
        }

        return false;
    }

    /**
     * 13. 罗马数字转整数
     * @param s
     * @return
     */
    public static int romanToInt(String s) {

        int result = 0;
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        for (int i =0; i < s.length(); i ++) {

            char c = s.charAt(i);
            if ( i < s.length() -1) {

                char nextC = s.charAt(i+1);

                if ('I' == c && 'V' == nextC) {
                    result += 4;
                    i++;
                } else if ('I' == c && 'X' == nextC) {
                    result += 9;
                    i++;
                } else if ('X' == c && 'L' == nextC) {
                    result += 40;
                    i++;
                } else if ('X' == c && 'C' == nextC) {
                    result += 90;
                    i++;
                } else if ('C' == c && 'D' == nextC) {
                    result += 400;
                    i++;
                }  else if ('C' == c && 'M' == nextC) {
                    result += 900;
                    i++;
                } else {
                    result += map.get(c);
                }

            } else {
                result += map.get(c);
            }

        }

        return result;
    }

    /**
     * 509. 斐波那契数:递归方式
     * @param n
     * @return
     */
    public static int fib1(int n) {

        if (n < 2) {
            return n;
        }

        return fib1(n - 1) + fib1(n-2);
    }

    /**
     * 509. 斐波那契数:双指针
     * @param n
     * @return
     */
    public static int fib2(int n) {

        if (n < 2) {
            return n;
        }

        int left = 0;
        int right = 1;
        for (int i = 2; i <= n; i ++) {
            int sum = left + right;
            left = right;
            right = sum;
        }

        return right;
    }

    /**
     * 88. 合并两个有序数组
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        int p1 = m - 1;
        int p2 = n - 1;
        int index = m + n - 1;

        while (p1 >= 0 || p2 >=0) {

            if (p1 < 0) {
                nums1[index--] = nums2[p2--];
            } else if (p2 < 0) {
                break;
            } else if (nums2[p2] > nums1[p1]) {
                nums1[index--] = nums2[p2--];
            } else {
                nums1[index--] = nums1[p1--];
            }

        }

    }

    /**
     * 121. 买卖股票的最佳时机
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {

        if (prices == null || prices.length < 2) {
            return 0;
        }

        // 最小买入金额
        int minPrice = prices[0];
        // 最大利润
        int maxPrice = 0;

        for (int i = 1; i < prices.length; i ++) {
            maxPrice = Math.max(maxPrice, prices[i] - minPrice);
            minPrice = Math.min(minPrice, prices[i]);

        }

        return maxPrice;
    }

    /**
     * 字符串相加
     * @param num1
     * @param num2
     * @return
     */
    public static String addStrings(String num1, String num2) {

        if (num1 == null || "".equals(num1)) {
            return num2;
        }

        if (num2 == null || "".equals(num2)) {
            return num1;
        }

        int len1 = num1.length()-1;
        int len2 = num2.length()-1;

        // 记录进位
        int carry = 0;
        StringBuffer sum = new StringBuffer();
        while (len1 >=0 || len2 >= 0) {

            int x = len1 < 0 ? 0 : num1.charAt(len1) - '0';
            int y = len2 < 0 ? 0 : num2.charAt(len2) - '0';

            int z = x + y + carry;
            if (z >= 10) {
                sum.append(z%10);
                carry = z/10;
            } else {
                sum.append(z);
                carry = 0;
            }

            len1--;
            len2--;

        }

        if (carry > 0) {
            sum.append(carry);
        }

        return sum.reverse().toString();
    }

    /**
     * 70. 爬楼梯
     * @param n
     * @return
     */
    public static int climbStairs(int n) {

        if (n < 3) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i < n + 1; i ++) {
            dp[i] = dp[i-1] + dp[i-2];
        }


        return dp[n];

    }

    /**
     * 14. 最长公共前缀，方法一
     * @param strs
     * @return
     */
    public static String longestCommonPrefix1(String[] strs) {

        if (strs == null || strs.length == 0) {
            return "";
        }

        if (strs.length == 1) {
            return strs[0];
        }

        // 获取字符串数组中，最短字符串长度
        int len = strs[0].length();
        for (int i = 1; i < strs.length; i ++) {
            len = Math.min(len, strs[i].length());
        }

        // 公共前缀
        String commonPrefix = "";
        String s = strs[0];
        for (int i = 1; i <= len; i ++) {
            String prefix = s.substring(0, i);
            int stat = 0;
            for (int k = 0; k < strs.length; k ++) {
                if (strs[k].startsWith(prefix)) {
                    stat++;
                }
            }
            if (stat == strs.length && prefix.length() > commonPrefix.length()) {
                commonPrefix = prefix;
            }
        }

        return commonPrefix;
    }

    /**
     * 14. 最长公共前缀，方法二
     * @param strs
     * @return
     */
    public static String longestCommonPrefix2(String[] strs) {

        if (strs == null || strs.length == 0) {
            return "";
        }

        String prefix = strs[0];
        for (int i = 1; i < strs.length; i ++) {
            prefix = longestCommonPrefix(prefix, strs[i]);
            if (prefix.length() == 0) {
                break;
            }
        }

        return prefix;
    }

    public static String longestCommonPrefix(String s1, String s2) {
        if (s1.isEmpty() || s2.isEmpty()) {
            return "";
        }

        int len = Math.min(s1.length(), s2.length());

        for (int i = 0; i < len; i ++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                continue;
            } else {
                return s1.substring(0, i);
            }
        }

        return s1.substring(0, len);
    }

    /**
     * 9.  数:翻转数
     * @param x
     * @return
     */
    public boolean isPalindrome1(int x) {

        if (x < 0) {
            return false;
        }

        int temp = x;
        int reverNum = 0;
        while (temp > 0) {
            reverNum = reverNum*10 + temp%10;
            temp = temp/10;
        }

        return reverNum == x;
    }

    /**
     * 9. 回文数:翻转数
     * @param x
     * @return
     */
    public boolean isPalindrome2(int x) {

        if (x < 0) {
            return false;
        }

        char[] chars = String.valueOf(x).toCharArray();

        int len = chars.length;
        if (len%2 == 0) {
            for (int i = 0; i < len/2; i ++) {
                if (chars[i] != chars[len-1-i]) {
                    return false;
                }
            }
        } else {
            for (int i = 0; i < len/2-1; i ++) {
                if (chars[i] != chars[len-1-i]) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 20. 有效的括号
     * @param s
     * @return
     */
    public boolean isValid(String s) {

        if (s == null || "".equals(s)) {
            return false;
        }

        char[] chars = s.toCharArray();
        if (chars.length%2 != 0) {
            return false;
        }

        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');

        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < chars.length; i ++) {
            if (map.containsKey(chars[i])) {
                if (stack.isEmpty() || stack.peek() != map.get(chars[i])) {
                    return false;
                }

                return false;
            } else {
                stack.push(chars[i]);
            }
        }

        return stack.isEmpty();

    }


    /**
     * 53. 最大子序和：暴力破解法
     * @param nums
     * @return
     */
    public static int maxSubArray(int[] nums) {

        if (nums == null || nums.length ==0) {
            return 0;
        }

        int maxNum = nums[0];

        for (int i = 0; i < nums.length; i ++) {
            int sum =0;
            for (int j = i; j < nums.length; j ++) {
                sum += nums[j];
                if (sum >= maxNum) {
                    maxNum = sum;
                }
            }

        }

        return maxNum;
    }

    /**
     * 53. 最大子序和：贪心算法
     * @param nums
     * @return
     */
    public static int  maxSubArray2(int[] nums) {

        int maxSum = nums[0];
        int num = nums[0];
        for (int i = 1; i < nums.length; i ++) {

            // 前面和为负数，舍弃，从当前位置开始
            if (num < 0) {
                num = nums[i];
            } else {
                num += nums[i];
            }

            if (num > maxSum) {
                maxSum = num;
            }
        }
        return maxSum;
    }


    /**
     * 21. 合并两个有序链表：迭代
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {

        ListNode preNode = new ListNode(-1);

        ListNode pre = preNode;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                pre.next = l1;
                l1 = l1.next;
            } else {
                pre.next = l2;
                l2 = l2.next;
            }
            pre = pre.next;
        }

        if (l1 == null) {
            pre.next = l2;
        }
        if (l2 == null) {
            pre.next = l1;
        }

        return preNode.next;
    }

    /**
     * 21. 合并两个有序链表：递归
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode mergeTwoLists2(ListNode l1, ListNode l2) {

       if (l1 == null) {
           return l2;
       } else if (l2 == null) {
           return l1;
       } else if (l1.val < l2.val) {
           l1.next = mergeTwoLists2(l1.next, l2);
           return l1;
       } else {
           l2.next = mergeTwoLists2(l1, l2.next);
           return l2;
       }
    }

    /**
     * 7. 整数反转
     *  123 321
     * @param x
     * @return
     */
    public static int reverse (int x) {
        long sum = 0;

        while (x != 0) {
            int a = x % 10;
            sum = sum*10 + a;
            if (sum < Integer.MIN_VALUE || sum > Integer.MAX_VALUE) {
                return 0;
            }
            x = x/10;
        }

        return (int) sum;
    }

    /**
     * 206. 反转链表：递归
     * @param head
     * @return
     */
    public static ListNode reverseList(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode newNode = reverseList(head.next);
        ListNode nextNode = head.next;
        head.next = null;
        nextNode.next = head;

        return newNode;
    }

    /**
     * 206. 反转链表：迭代
     * @param head
     * @return
     */
    public static ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode newHead = null;
        while (head != null) {

            ListNode tempNode = head.next;
            head.next = newHead;
            newHead = head;
            head = tempNode;

        }

        return newHead;
    }

    /**
     * 1. 两数之和：暴力枚举
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {

        int[] result = new int[2];

        for (int i = 0; i < nums.length; i ++) {
            for (int j = i + 1; j < nums.length; j ++) {
                if (nums[i] + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }

        return null;
    }

    /**
     * 1. 两数之和：哈希表
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum2(int[] nums, int target) {

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i ++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target-nums[i])};
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }


}
