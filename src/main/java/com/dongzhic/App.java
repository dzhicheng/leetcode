package com.dongzhic;

import com.dongzhic.common.ListNode;
import com.dongzhic.common.TreeNode;
import com.dongzhic.domain.Book;
import com.dongzhic.domain.User;
import org.springframework.util.StringUtils;
import sun.misc.Unsafe;
import sun.nio.cs.ext.IBM037;
import sun.security.krb5.internal.PAData;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author dongzhic
 * @Date 2019-11-11 17:39
 */
public class App {

    public static void main(String[] args) {
        String s = "abbaca";
        System.out.println(removeDuplicates(s));
    }

    /**
     * 1047. 删除字符串中的所有相邻重复项：使用栈
     * 输入："abbaca"
     * 输出："ca"
     * @param s
     * @return
     */
    public static String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i ++) {
            char c = s.charAt(i);
            if (stack.isEmpty()) {
                stack.add(c);
            } else {
                if (stack.peek().equals(c)) {
                    stack.pop();
                } else {
                    stack.push(c);
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.reverse().toString();
    }

    /**
     * 1190. 反转每对括号间的子串
     * 输入：s = "(u(love)i)"
     * 输出："iloveu"
     * 解释：先反转子字符串 "love" ，然后反转整个字符串。
     * @param s
     * @return
     */
    public static String reverseParentheses11(String s) {

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c != ')') {
                stack.push(c);
            } else {
                LinkedList<Character> list = new LinkedList<>();
                while (!stack.isEmpty() && stack.peek() != '(') {
                    list.add(stack.pop());
                }
                stack.pop();
                while (!list.isEmpty()) {
                    stack.push(list.remove());
                }
            }
        }

        StringBuffer result = new StringBuffer();
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.reverse().toString();
    }

    /**
     * 1190. 反转每对括号间的子串
     * 输入：s = "a(bcdefghijkl(mno)p)q"
     * 输出："iloveu"
     * 解释：先反转子字符串 "love" ，然后反转整个字符串。
     * @param s
     * @return
     */
    public static String reverseParentheses12(String s) {

        Stack<String> stack = new Stack<>();
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(sb.toString());
                sb.setLength(0);
            } else if (c == ')') {
                sb = sb.reverse();
                sb.insert(0, stack.pop());
            } else {
                sb.append(c);
            }

        }

        return sb.toString();
    }

    /**
     * 69. Sqrt(x) 算术平方根
     * @param x
     * @return
     */
    public static int mySqrt1(int x) {

        if (x == 0 || x == 1) {
            return x;
        }

        int left = 1;
        int right = x/2;

        while (left <= right) {
            int middle = (left + right)/2;
            if (x / middle == middle) {
                return middle;
            } else if (x / middle < middle) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }

        return right;
    }

    /**
     * 55. 跳跃游戏
     * @param nums
     * @return
     */
    public boolean canJump1(int[] nums) {

        // 记录能跳到最远的位置
        int maxIndex = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > maxIndex) {
                return false;
            }

            // 当前能跳到最远位置
            int currMaxIndex = i + nums[i];

            maxIndex = Math.max(maxIndex, currMaxIndex);
        }

        return maxIndex >= nums.length-1;
    }


    /**
     * 集合去重
     */
    public static void test () {

        // ==================================================================================================
        // 一、常规元素去重
        List<String> list1 = new ArrayList<>();
        list1.add("zhangsan");
        list1.add("lisi");
        list1.add("zhangsan");
        list1.add("wangwu");
        list1.add("zhangsan");

        // 存入连一个元素
        Set<String> set = new HashSet<>();
        List<String> result11 = new ArrayList<>();
        for (int i = 0; i < list1.size(); i++) {
            String s = list1.get(i);
            if (set.add(s)) {
                result11.add(s);
            }
        }

        List<String> result12 = list1.stream().distinct().collect(Collectors.toList());
         System.out.println("=================================================");

        // 二、对象去重
        List<User> userList = new ArrayList<>();
        userList.add(new User("zhangsan", 10));
        userList.add(new User("lisi", 12));
        userList.add(new User("zhangsan", 10));
        userList.add(new User("wangwu", 15));
        userList.add(new User("zhangsan", 13));

        List<User> result21 = userList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getUsername))), ArrayList::new)
        );
        List<User> result22 = userList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(User::getAge))), ArrayList::new)
        );
        List<User> result23 = userList.stream().collect(
                Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(user -> user.getUsername() + ";" + user.getAge()))), ArrayList::new)
        );

        // ==================================================================================================

        List<Book> list = new ArrayList<>();
        list.add(new Book("jvm虚拟机", 50.5));
        list.add(new Book("jvm虚拟机", 30.5));
        list.add(new Book("jvm虚拟机", 40.5));
        list.add(new Book("重构：改善既有代码的设计", 60.0));
        list.add(new Book("重构：改善既有代码的设计", 54.0));

        // 1.遍历
//        list.forEach(System.out::println);
        // 等同于
//        list.forEach(student -> {
//            System.out.println(student);
//        });

        // 2.过滤，分组，取价格最低的数据
        Map<String, Book> map = list.stream()
                .collect(
                        Collectors.groupingBy(
                                Book::getName,
                                Collectors.collectingAndThen(
                                        Collectors.reducing((b1, b2) -> b1.getPrice() < b2.getPrice() ? b1 : b2), Optional::get
                                )
                        )
                );
        List<Book> listSortAndGroup = new ArrayList<>(map.values());
        listSortAndGroup.stream().forEach(System.out::println);



    }

    /**
     * 46. 全排列
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute1(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();

        if (nums == null) {
            return result;
        }

        dfsPermute1(result, new ArrayList<>(), nums);

        return result;
    }
    public static void dfsPermute1 (List<List<Integer>> res, List<Integer> path, int[] nums) {

        // 叶子节点
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!path.contains(num)) {
                path.add(num);
                dfsPermute1(res, path, nums);
                path.remove(path.size()-1);
            }
        }

    }

    /**
     * 27. 移除元素
     * @param nums
     * @param val
     * @return
     */
    public static int removeElement(int[] nums, int val) {

            if (nums == null) {
                return 0;
            }

            int left = 0;
            int right = 0;
            while (right < nums.length) {
                if (nums[right] == val) {
                    right++;
                    continue;
                }
                swap1(nums, left, right);
                left++;
                right++;
            }

            return left;
    }

    public static void swap1 (int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    /**
     * 110. 平衡二叉树
     * @param root
     * @return
     */
    public boolean isBalanced1(TreeNode root) {

        if (root == null) {
            return true;
        }

        return height1(root) == -1 ? false : true;
    }

    public int height1 (TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftHeight = height1(root.left);
        if (leftHeight == -1) {
            return -1;
        }

        int rightHeight = height1(root.right);
        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }



    /**
     * 257. 二叉树的所有路径
     * @param root
     * @return
     */
    public static List<String> binaryTreePaths1(TreeNode root) {
        List<String> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        binaryTreePaths1(list, root, "");

        return list;
    }

    public static void binaryTreePaths1(List<String> list, TreeNode root, String str) {

        StringBuffer sb = new StringBuffer(str);
        sb.append(root.val);

        // 叶子节点
        if (root.left == null && root.right == null) {
            list.add(sb.toString());
        } else {
            sb.append("->");
        }

        if (root.left != null) {
            binaryTreePaths1(list, root.left, sb.toString());
        }
        if (root.right != null) {
            binaryTreePaths1(list, root.right, sb.toString());
        }
    }

    /**
     * 448. 找到所有数组中消失的数字
     * @param nums
     * @return
     */
    public List<Integer> findDisappearedNumbers1(int[] nums) {

        List<Integer> list = new ArrayList<>();
        if (nums == null) {
            return list;
        }

        int index = 0;
        while (index < nums.length) {
            int num = nums[index];
            if (num == nums[num-1]) {
                index++;
                continue;
            }

            swap(nums, index, nums[index]-1);
        }

        for (int i = 0; i < nums.length; i++) {
            if ((i + 1) != nums[i]) {
                list.add(i+1);
            }
        }

        return list;
    }

    public void swap (int[] nums, int i, int j) {
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }

    /**
     * 169. 多数元素
     * @param nums
     * @return
     */
    public int majorityElement1(int[] nums) {

        Arrays.sort(nums);

        int index = (0 + nums.length)/2;
        return nums[index];
    }

    /**
     * 543. 二叉树的直径
     * @param root
     * @return
     */
    private int maxLength = 0;
    public int diameterOfBinaryTree1(TreeNode root) {

        dfs1(root);

        return maxLength;
    }

    public int dfs1 (TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftLength = dfs1(root.left);
        int rightLength = dfs1(root.right);

        maxLength = Math.max(leftLength + rightLength, maxLength);

        return Math.max(leftLength, rightLength) + 1;
    }

    /**
     * 94. 二叉树的中序遍历：递归
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal11(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        List<Integer> leftList = inorderTraversal11(root.left);
        List<Integer> rightList = inorderTraversal11(root.right);
        result.addAll(leftList);
        result.add(root.val);
        result.addAll(rightList);

        return result;
    }

    /**
     * 94. 二叉树的中序遍历：遍历
     * @param root
     * @return
     */
    public static List<Integer> inorderTraversal12(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {

            TreeNode currNode = stack.pop();

            // 叶子节点or根节点
            if (currNode.right == null && currNode.left == null) {
                result.add(currNode.val);
                continue;
            }

            if (currNode.right != null) {
                stack.push(currNode.right);
            }

            if (currNode.left != null) {
                stack.push(new TreeNode(currNode.val));
                stack.push(currNode.left);
            } else {
                stack.push(new TreeNode(currNode.val));
            }

        }

        return result;
    }

    /**
     * 112. 路径总和
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum1(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        // 叶子节点
        if (root.left == null && root.right == null && root.val == targetSum) {
            return true;
        }

        boolean b1 = hasPathSum1(root.left, targetSum - root.val);
        boolean b2 = hasPathSum1(root.right, targetSum - root.val);

        if (b1 || b2) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 104. 二叉树的最大深度：递归
     * @param root
     * @return
     */
    public int maxDepth1(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftDepth = maxDepth1(root.left);
        int rightDepth = maxDepth1(root.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * 680. 验证回文字符串 Ⅱ
     * @param s
     * @return
     */
    private int deleteCount1 = 0;
    public boolean validPalindrome1(String s) {

        int left = 0;
        int right = s.length() - 1;
        while (left <= right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
                continue;
            }

            if (deleteCount1 < 1) {
                deleteCount1++;
                // 删除left位置元素
                boolean b1 = validPalindrome1(s.substring(left + 1, right + 1));
                // 删除right位置元素
                boolean b2 = validPalindrome1(s.substring(left, right));

                if (b1 || b2 ) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }

        }

        return true;
    }

    /**
     * 35. 搜索插入位置
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert1(int[] nums, int target) {

        if (nums == null) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] == target ? 0 : -1;
        }

        int left = 0;
        int right = nums.length-1;
        int middle = 0;
        while (left <= right) {
            middle = (left+right)/2;
            if (nums[middle] > target) {
                right = middle - 1;
            } else if (nums[middle] < target) {
                left = middle + 1;
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
    public boolean isSymmetric1(TreeNode root) {

        if (root == null) {
            return false;
        }

        return isSymmetric1(root.left, root.right);
    }

    public boolean isSymmetric1(TreeNode root1, TreeNode root2) {

        if (root1 == null && root2 == null) {
            return true;
        }

        if (root1 == null || root2 == null) {
            return false;
        }

        if (root1.val != root2.val) {
            return false;
        }

        boolean b1 = isSymmetric1(root1.left, root2.right);
        boolean b2 = isSymmetric1(root1.right, root2.left);
        if (b1 && b2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 617. 合并二叉树
     * @param root1
     * @param root2
     * @return
     */
    public TreeNode mergeTrees1(TreeNode root1, TreeNode root2) {

        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }

        root1.val = root1.val + root2.val;
        root1.left = mergeTrees1(root1.left, root2.left);
        root1.right = mergeTrees1(root1.right, root2.right);

        return root1;
    }

    /**
     * 234. 回文链表：使用list存储元素
     * @param head
     * @return
     */
    public static boolean isPalindrome11(ListNode head) {

        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }

        int left = 0;
        int right = list.size()-1;
        while (left < list.size()/2) {
            if (list.get(left) != list.get(right)) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * 344. 反转字符串
     * @param s
     */
    public void reverseString1(char[] s) {

        // 字符串长度
        int len = s.length;
        int left = 0;
        int right = len;
        while (left < len/2) {
            char c = s[left];
            s[left] = s[right];
            s[right] = c;
            left++;
            right--;
        }
    }

    /**
     * 283. 移动零
     * @param nums
     */
    public void moveZeroes1(int[] nums) {

        if (nums == null || nums.length == 1) {
            return;
        }

        // 第一个为0的位置
        int left = 0;
        // 第一个不为0的位置
        int right = 0;
        while (right < nums.length) {
            if (nums[right] != 0) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
            }
            right++;
        }

    }

    /**
     *  226. 翻转二叉树：深度优先遍历
     * @param root
     * @return
     */
    public TreeNode invertTreeDFS1(TreeNode root) {

        if (root == null) {
            return root;
        }

        TreeNode left = invertTreeDFS1(root.left);
        TreeNode right = invertTreeDFS1(root.right);

        root.left = right;
        root.right = left;

        return root;
    }

    /**
     * 226. 翻转二叉树：广度优先遍历
     * @param root
     * @return
     */
    public TreeNode invertTreeBFS1(TreeNode root) {
        if (root == null) {
            return root;
        }

        // 存储队列
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {

            TreeNode currNode = queue.poll();
            TreeNode leftTreeNode = currNode.left;
            currNode.left = currNode.right;
            currNode.right = leftTreeNode;

            if (currNode.left != null) {
                queue.add(currNode.left);
            }

            if (currNode.right != null) {
                queue.add(currNode.right);
            }


        }

        return root;
    }

    /**
     * 28. 实现 strStr()
     * "mississippi"
     * "issipi"
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr1(String haystack, String needle) {

        if ("".equals(needle)) {
            return 0;
        }

        if (needle.length() > haystack.length()) {
            return -1;
        }

        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            if (haystack.charAt(i) == needle.charAt(0)) {
                boolean flag = true;

                for (int j = 1; j < needle.length(); j++) {
                    if (haystack.charAt(i + j) != needle.charAt(j)) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    return i;
                }

            }
        }

        return -1;
    }

    /**
     * 26. 删除有序数组中的重复项
     * @param nums
     * @return
     */
    public int removeDuplicates1(int[] nums) {

        if(nums == null || nums.length == 1) {
            return 0;
        }
        // 记录数组
        int slow = 1;
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
     * 136. 只出现一次的数字
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {

        /**
         * 三种情况
         * 1 2 2
         * 1 1 2 3 3
         * 1 1 2 2 3
         */

        Arrays.sort(nums);

        int len = nums.length;
        // 前两种情况
        int index = 0;
        while (index < len - 2) {
            if (nums[index] != nums[index + 1] &&
                    nums[index + 1] == nums[index + 2]) {
                return nums[index];
            } else {
                index = index + 2;
            }
        }

        // 第三种情况
        return nums[len-1];
    }

    /**
     * 83. 删除排序链表中的重复元素
     * @param head
     * @return
     */
    public ListNode deleteDuplicates1(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode currNode = head;
        while (currNode != null && currNode.next != null) {

            ListNode nextNode = currNode.next;

            if (currNode.val == nextNode.val) {
                currNode.next = nextNode.next;
                continue;
            }

            currNode = currNode.next;
        }

        return head;
    }

    /**
     * 349. 两个数组的交集
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection1(int[] nums1, int[] nums2) {

        if (nums1 == null || nums2 == null) {
            return null;
        }

        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            set1.add(nums1[i]);
        }

        for (int j = 0; j < nums2.length; j++) {
            if (set1.contains(nums2[j])) {
                set2.add(nums2[j]);
            }
        }

        int[] result = new int[set2.size()];
        if (set2.size() > 0) {
            int index = 0;
            for (Integer val : set2) {
                result[index++] = val;
            }
        }

        return result;
    }

    /**
     * 118. 杨辉三角    
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate1(int numRows) {

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < numRows; i++) {
            int row = i + 1;
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < row; j++) {
                if (j == 0 || j == row - 1) {
                    list.add(1);
                } else {
                    List<Integer> prevList = result.get(i - 1);
                    list.add(prevList.get(j) + prevList.get(j-1));
                }
            }
            result.add(list);
        }


        return result;
    }

    /**
     * 704. 二分查找
     * @param nums
     * @param target
     * @return
     */
    public int search1(int[] nums, int target) {

        if (nums == null || nums.length == 0) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] == target ? 0 : -1;
        }

        int left = 0;
        int right = nums.length-1;
        while (left <= right) {
            int middle = (left + right)/2;
            if (nums[middle] > target) {
                right = middle - 1;
            } else if (nums[middle] < target) {
                left = middle + 1;
            } else {
                return middle;
            }
        }

        return -1;
    }

    /**
     * 415. 字符串相加
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings1(String num1, String num2) {

        StringBuffer result = new StringBuffer();

        if ("0".equals(num1)) {
            return num2;
        }
        if ("0".equals(num2)) {
            return num1;
        }

        int index1 = num1.length() - 1;
        int index2 = num2.length() - 1;

        int carry = 0;
        while (index1 >= 0 || index2 >= 0) {
            int val1 = index1 >= 0 ? num1.charAt(index1) - '0' : 0;
            int val2 = index2 >= 0 ? num2.charAt(index2) - '0' : 0;

            int sum = val1 + val2 + carry;
            int val = sum % 10;
            carry = sum / 10;

            result.append(val);

            index1--;
            index2--;
        }

        if (carry > 0) {
            result.append(carry);
        }

        return result.reverse().toString();
    }

    /**
     * 5. 最长回文子串
     * @param s
     * @return
     */
    public static String longestPalindrome1(String s) {

        if (s == null || "".equals(s) || s.length() == 1) {
            return s;
        }

        String maxLongestPalindrome = String.valueOf(s.charAt(0));

        int len = s.length();

        // 初始化
        boolean[][] dp = new boolean[len][len];
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        for (int right = 1; right < len; right++) {
            for (int left = 0; left < right; left++) {
                if (s.charAt(left) != s.charAt(right)) {
                    dp[left][right] = false;
                    continue;
                }

                if ((left + 1) >= (right - 1)) {
                    dp[left][right] = true;
                } else {
                    dp[left][right] = dp[left+1][right-1];
                }

                if (dp[left][right] && (right - left + 1) > maxLongestPalindrome.length()) {
                    maxLongestPalindrome = s.substring(left, right + 1);
                }
            }
        }

        return maxLongestPalindrome;
    }

    /**
     * 2. 两数相加
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers1(ListNode l1, ListNode l2) {

        ListNode result = new ListNode(-1);
        ListNode currNode = result;

        // 进位
        int carry = 0;
        while (l1 != null || l2 != null) {

            int value1 = l1 != null ? l1.val : 0;
            int value2 = l2 != null ? l2.val : 0;

            int sum = value1 + value2 + carry;
            carry = sum / 10;
            int value = sum % 10;
            currNode.next = new ListNode(value);

            currNode = currNode.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        if (carry > 0) {
            currNode = new ListNode(carry);
        }

        return result.next;
    }

    /**
     *  70. 爬楼梯
     * @param n
     * @return
     */
    public int climbStairs1(int n) {

        if (n < 3) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i < n + 1; i++) {
            dp[i] = dp[i-1] + dp[i-2];
        }

        return dp[n];
    }

    /**
     *  14. 最长公共前缀
     * @param strs
     * @return
     */
    public static String longestCommonPrefix1(String[] strs) {

        if (strs == null) {
            return "";
        }
        if (strs.length == 1) {
            return strs[0];
        }

        String commonPrefix = longestCommonPrefix1(strs[0], strs[1]);
        for (int i = 2; i < strs.length; i++) {
            commonPrefix = longestCommonPrefix1(commonPrefix, strs[i]);
        }

        return commonPrefix;
    }
    public static String longestCommonPrefix1(String str1, String str2) {
        int index = 0;
        while (index < str1.length() && index < str2.length()) {
            if (str1.charAt(index) != str2.charAt(index)) {
                break;
            }
            index++;
        }
        return str1.substring(0, index);
    }

    /**
     * 20. 有效的括号
     * @param s
     * @return
     */
    public static boolean isValid1(String s) {

        if (s.length()%2 != 0) {
            return false;
        }

        Stack<Character> stack = new Stack<>();

        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                if (stack.isEmpty() || stack.peek() != map.get(c)) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(c);
            }
        }

        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 143. 重排链表
     * @param head
     */
    public static void reorderList(ListNode head) {

        if (head == null || head.next == null) {
            return;
        }

        // 1.链表长度
        int length = 0;
        ListNode temp1 = head;
        while (temp1 != null) {
            temp1 = temp1.next;
            length++;
        }

        // 2.获取后半段链表
        ListNode afterNode = null;
        ListNode temp2 = head;
        for (int i = 0; i < length/2 - 1; i++) {
            temp2 = temp2.next;
        }
        if (length % 2 != 0) {
            temp2 = temp2.next;
        }
        afterNode = temp2.next;
        temp2.next = null;


        ListNode l2 = reverseNode(afterNode);

        // 合并链表
        ListNode l1 = head;
        while (l2 != null) {

            ListNode nextL1 = l1.next;
            ListNode nextl2 = l2.next;

            l1.next = l2;
            l2.next = null;
            l1 = l1.next;
            l1.next = nextL1;

            l1 = nextL1;
            l2 = nextl2;
        }

    }
    /**
     * 反转链表
     * @param head
     * @return
     */
    public static ListNode reverseNode (ListNode head) {

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

    /**
     *  88. 合并两个有序数组
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge1(int[] nums1, int m, int[] nums2, int n) {

        int index = m + n - 1;
        int index1 = m - 1;
        int index2 = n - 1;

        while (index >= 0 && index2 >= 0) {

            if (index1 < 0) {
                nums1[index--] = nums2[index2--];
                continue;
            }

            if (nums1[index1] > nums2[index2]) {
                nums1[index--] = nums1[index1--];
            } else {
                nums1[index--] = nums2[index2--];
            }

        }

    }

    /**
     * 1. 两数之和
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum1(int[] nums, int target) {

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }

        return null;
    }

    /**
     * 121. 买卖股票的最佳时机
     * @param prices
     * @return
     */
    public int maxProfit1(int[] prices) {
        int maxProfit = 0;

        int minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            int price = prices[i];
            maxProfit = Math.max(maxProfit, price - minPrice);

            minPrice = Math.min(minPrice, price);
        }

        return maxProfit;
    }

    /**
     * 257. 二叉树的所有路径
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        binaryTreePaths(root, new ArrayList<>(), "");
        return list;
    }

    public void binaryTreePaths(TreeNode root, List<String> list, String path) {

        StringBuffer sb = new StringBuffer(path);
        sb.append(root.val);

        if (root.left == null && root.right == null) {
            // 叶子节点
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
     * 448. 找到所有数组中消失的数字
     *  [4,3,2,7,8,2,3,1]
     *  [5,6]
     * @param nums
     * @return
     */
    public static List<Integer> findDisappearedNumbers(int[] nums) {

        List<Integer> result = new ArrayList<>();

        int index = 0;
        int n = nums.length;

        while (index < n) {

            // 元素应该在的位置
            int targetIndex = nums[index] - 1;
            if ((targetIndex+1) == nums[targetIndex]) {
                index++;
            } else {
                int temp = nums[targetIndex];
                nums[targetIndex] = nums[index];
                nums[index]= temp;
            }

        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != (i+1)) {
                result.add(i+1);
            }
        }

        return result;
    }

    /**
     * 169. 多数元素
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        Arrays.sort(nums);
        int index = (nums.length-1)/2;
        return nums[index];
    }

    /**
     * 543. 二叉树的直径
     * @param root
     * @return
     */
    private int maxBinaryTree = 0;
    public int diameterOfBinaryTree(TreeNode root) {

        dfsTree(root);

        return maxBinaryTree;
    }

    public int dfsTree (TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftMaxDepthTree = dfsTree(root.left);
        int rightMaxDepthTree = dfsTree(root.right);

        int currmaxBinaryTree = leftMaxDepthTree + rightMaxDepthTree + 1;
        if (currmaxBinaryTree > maxBinaryTree) {
            maxBinaryTree = currmaxBinaryTree;
        }

        return Math.max(leftMaxDepthTree, rightMaxDepthTree) + 1;
    }

    /**
     * 94. 二叉树的中序遍历：左根右
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        if (root == null) {
            return result;
        }

        if (root.left != null) {
            List<Integer> leftResult = inorderTraversal(root.left);
            result.addAll(leftResult);
        }
        result.add(root.val);
        if (root.right != null) {
            List<Integer> rightResult = inorderTraversal(root.right);
            result.addAll(rightResult);
        }

        return result;
    }

    /**
     * 112. 路径总和
     *  root = [1,2,3], targetSum = 5
     * @param root
     * @param targetSum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {

            if (root == null) {
                return false;
            }

            // 叶子节点
            if (root.left == null && root.right == null) {
                if ((root.val - targetSum) == 0) {
                    return true;
                } else {
                    return false;
                }
            }

            boolean b1 = false;
            if (root.left != null) {
                b1 = hasPathSum(root.left, targetSum - root.val);
            }
            boolean b2 = false;
            if (root.right != null) {
                b2 = hasPathSum(root.right, targetSum - root.val);
            }
            if (b1 || b2) {
                return true;
            } else  {
                return false;
            }

    }

    /**
     * 680. 验证回文字符串 Ⅱ
     * @param s
     * @return
     */
    private int deleteCount = 0;
    public boolean validPalindrome(String s) {

        int left = 0;
        int right = s.length()-1;

        while (left <= right) {
            if (s.charAt(left) != s.charAt(right)) {
                deleteCount++;
                if (deleteCount > 1) {
                    return false;
                } else {
                    boolean b1 = validPalindrome(s.substring(left+1, right+1));
                    boolean b2 = validPalindrome(s.substring(left, right));
                    if (b1 || b2) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                left++;
                right--;
            }
        }

        return true;
    }

    /**
     * 104. 二叉树的最大深度
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftMaxDepth = maxDepth(root.left);
        int rightMaxDepth = maxDepth(root.right);

        return Math.max(leftMaxDepth, rightMaxDepth) + 1;
    }

    /**
     * 35. 搜索插入位置:二分查找
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {

        int left = 0;
        int right = nums.length-1;

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

        return left;

    }

    /**
     * 101. 对称二叉树
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {

        if (root == null) {
            return true;
        }

        return isSymmetric(root.left, root.right);
    }
    public boolean isSymmetric(TreeNode root1, TreeNode root2) {

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
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {

        int left = 0;
        int right = nums.length-1;

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
     * 160. 相交链表
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
            } else {
                l1 = l1.next;
                l2 = l2.next;
            }
        }

        return null;
    }

    /**
     * 617. 合并二叉树
     * @param root1
     * @param root2
     * @return
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {

        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }

        root1.val = root1.val + root2.val;
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);

        return root1;
    }

    /**
     * 234. 回文链表:借助List
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {

        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }

        int left = 0;
        int right = list.size()-1;
        while (left <= right) {
            if (list.get(left) != list.get(right)) {
                return false;
            } else {
                left++;
                right--;
            }
        }

        return true;
    }

    /**
     * 344. 反转字符串
     * @param s
     */
    public void reverseString(char[] s) {

        int left = 0;
        int right = s.length-1;

        while (left <= right) {
            char c = s[left];
            s[left] = s[right];
            s[right] = c;
            left++;
            right--;
        }
    }

    /**
     * 283. 移动零
     * @param nums
     */
    public void moveZeroes(int[] nums) {

        int slow = 0;
        int fast = 0;

        while (fast < nums.length) {
            if (nums[fast] == 0) {
                fast++;
            } else {
                int temp = nums[slow];
                nums[slow] = nums[fast];
                nums[fast] = 0;
                slow++;
                fast++;
            }
        }
    }

    /**
     * 226. 翻转二叉树
     * @param root
     * @return
     */
    public TreeNode invertTree(TreeNode root) {

        if (root == null) {
            return root;
        }

        TreeNode leftNode = root.left;
        TreeNode rightNode = root.right;

        root.left = invertTree(rightNode);
        root.right = invertTree(leftNode);

        return root;
    }

    /**
     * 28. 实现 strStr()
     * 输入：haystack = "hello", needle = "ll"
     * 输出：2
     * @param haystack
     * @param needle
     * @return
     */
    public static int strStr(String haystack, String needle) {

        if (needle == null || "".equals(needle)) {
            return 0;
        }

        if (needle.length() > haystack.length()) {
            return -1;
        }

        for (int i = 0; i < (haystack.length() - needle.length() + 1); i++) {
            boolean flag = true;
            for (int j = 0; j < needle.length(); j++) {
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
     * 输入：nums = [0,0,1,1,1,2,2,3,3,4,4]
     * 输出：5, nums = [0,1,2,3,4]
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {

        if (nums == null) {
            return 0;
        }
        if (nums.length == 1) {
            return 1;
        }

        int left = 0;
        int right = 1;
        while (right < nums.length - 1) {
            if (nums[left] == nums[right]) {
                right++;
            } else {
                left++;
                nums[left] = nums[right];
                right++;
            }
        }


        return left+1;
    }

    /**
     * 136. 只出现一次的数字
     * @param nums
     * @return
     */
    public int singleNumber1(int[] nums) {

        if (nums.length == 1) {
            return nums[0];
        }

        int len = nums.length;
        int result = 0;
        for (int i = 0; i < len; i++) {
            int num = nums[i];
            if (i == 0) {
                if (num != nums[1]) {
                    result = num;
                    break;
                }
            } else if (i == len-1) {
                if (num != nums[len-2]) {
                    result = num;
                    break;
                }
            } else {
                if (nums[i] != nums[i-1] && nums[i] != nums[i+1]) {
                    result = num;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 136. 只出现一次的数字
     * @param nums
     * @return
     */
    public int singleNumber2(int[] nums) {

        /**
         * 一共三种情况
         * 1 2 2
         * 2 2 1 3 3
         * 2 2 3 3 1
         */

        // 前两种情况
        int index = 0;
        while (index < nums.length-2) {
            if (nums[index] != nums[index+1] &&
                    nums[index+1] == nums[index+2]) {
                return nums[index];
            } else {
                index += 2;
            }
        }

        // 最后一种情况
        return nums[nums.length-1];
    }

    /**
     * 83. 删除排序链表中的重复元素
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {

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
     * 349. 两个数组的交集
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {

        Set<Integer> setResult = new HashSet<>();
        Set<Integer> setFilter = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            setFilter.add(nums1[i]);
        }

        for (int j = 0; j < nums2.length; j++) {
            if (setFilter.contains(nums2[j])) {
                setResult.add(nums2[j]);
            }
        }

        int [] result = new int[setResult.size()];
        if (setResult.size() > 0) {
            int index = 0;
            for (Integer num : setResult) {
                result[index++] = num;
            }
        }

        return result;
    }

    /**
     * 118. 杨辉三角
     * @param numRows
     * @return
     */
    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();

        if (numRows < 1) {
            return result;
        }
        List<Integer> list1 = Arrays.asList(1);
        result.add(list1);
        if (numRows == 1) {
            return result;
        }
        List<Integer> list2 = Arrays.asList(1, 1);
        result.add(list2);
        if (numRows == 2) {
            return result;
        }

        for (int i = 3; i < numRows + 1; i++) {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            for (int j = 0; j < i - 2; j++) {
                List<Integer> upList = result.get(i-2);
                list.add(upList.get(j) + upList.get(j+1));
            }
            list.add(1);
            result.add(list);
        }

        return result;
    }

    /**
     * 141. 环形链表:使用set集合
     * @param head
     * @return
     */
    public boolean hasCycle1(ListNode head) {

        if (head == null || head.next == null) {
            return false;
        }

        Set<ListNode> set = new HashSet<>();

        while (head != null) {
            if (set.contains(head)) {
                return true;
            }
            set.add(head);
            head = head.next;
        }

        return false;
    }

    /**
     * 141. 环形链表 使用快慢指针
     * @param head
     * @return
     */
    public boolean hasCycle2(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }

        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {

            if (fast == slow) {
                return true;
            }

            slow = slow.next;
            fast = fast.next.next;
        }

        return false;
    }

    /**
     * 15. 三数之和：两数之和改造
     *  -1,0,1,2,-1,-4
     *  -4,-1,-1,0,1,2
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum1(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();
        Set<List<Integer>> setResult = new HashSet<>();

        if (nums == null || nums.length < 3) {
            return result;
        }

        // 避免set.add出现重复
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {

            Set<Integer> set = new HashSet<>();
            int target = 0 - nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                int num = nums[j];
                if (set.contains(target - num)) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(num);
                    list.add(target - num);
                    setResult.add(list);
                } else {
                    set.add(num);
                }
            }
        }

        result.addAll(setResult);

        return result;
    }

    /**
     * 15. 三数之和：双指针
     *  -1,0,1,2,-1,-4
     *  -4,-1,-1,0,1,2
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum2(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();
        Set<List<Integer>> setResult = new HashSet<>();

        if (nums == null || nums.length < 3) {
            return result;
        }

        Arrays.sort(nums);

        int len = nums.length;
        for (int i = 0; i < len - 2; i++) {
            int left = i + 1;
            int right = len - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    setResult.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }

        result.addAll(setResult);

        return result;
    }

    /**
     * 509. 斐波那契数:递归
     * @param n
     * @return
     */
    public int fib1(int n) {

        if (n < 2) {
            return n;
        }

        int[] array = new int[n+1];
        array[0] = 0;
        array[1] = 1;

        for (int i = 2; i < n + 1; i++) {
            array[i] = array[i-1] + array[i-2];
        }

        return array[n];
    }

    /**
     * 509. 斐波那契数:双指针
     * @param n
     * @return
     */
    public int fib2(int n) {

        if (n < 2) {
            return n;
        }

        int left = 0;
        int rigt = 1;
        for (int i = 2; i < n+1; i++) {
            int sum = left + rigt;
            left = rigt;
            rigt = sum;
        }

        return rigt;
    }

    /**
     * 3. 无重复字符的最长子串
     * s = "abcabcbb", 3
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {

        int maxSubstring = 0;

        // 存储字符
        Map<Character, Integer> map = new HashMap<>();

        int slow = 0;
        for (int fast = 0; fast < s.length(); fast++ ) {
            char c = s.charAt(fast);
            if (map.containsKey(c)) {
                slow = Math.max(slow, map.get(c) + 1);
            }

            map.put(c, fast);
            maxSubstring = Math.max(maxSubstring, fast - slow + 1);
        }

        return maxSubstring;
    }

    /**
     * 3. 无重复字符的最长子串：暴力法
     * s = "abcabcbb", 3
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring2(String s) {

        int maxSubstring = 0;

        for (int i = 0; i < s.length(); i++) {
            Set<Character> set = new HashSet<>();
            for (int j = i; j < s.length(); j++) {
                char c = s.charAt(j);
                if (set.contains(c)) {
                    break;
                }
                set.add(c);
            }
            if (set.size() > maxSubstring) {
                maxSubstring = set.size();
            }
        }

        return maxSubstring;
    }

    /**
     * [-1,0,0,3,3,3,0,0,0]
     * 6
     * [1,2,2]
     * 3
     * [-1,0,0,1,2,2,3,3,3]
     * 88. 合并两个有序数组
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        int index1 = m - 1;
        int index2 = n - 1;

        int index = m + n - 1;
        while (index2 >= 0) {
            if (index1 < 0) {
                nums1[index--] = nums2[index2--];
            } else if (index2 < 0) {
                break;
            } else if (nums1[index1] > nums2[index2]) {
                nums1[index--] = nums1[index1--];
            } else {
                nums1[index--] = nums2[index2--];
            }
        }
    }

    /**
     * 121. 买卖股票的最佳时机
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {

        int maxProfit = 0;

        int minPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            int currPrice = prices[i];
            if (currPrice < minPrice) {
                minPrice = currPrice;
                continue;
            }

            int currProfit = currPrice - minPrice;
            if (currProfit > maxProfit) {
                maxProfit = currProfit;
            }

        }

        return maxProfit;
    }


    /**
     * 415. 字符串相加
     * @param num1
     * @param num2
     * @return
     */
    public static String addStrings(String num1, String num2) {

        // 非空判断
        if (num1 == null || "".equals(num1)) {
            return num2;
        }
        if (num2 == null || "".equals(num2)) {
            return num1;
        }

        StringBuffer result = new StringBuffer();

        int len1 = num1.length();
        int len2 = num2.length();
        int index1 = len1 - 1;
        int index2 = len2 - 1;
        int carry = 0;
        while (index1 >= 0 || index2 >= 0) {

            int a = index1 >= 0 ? num1.charAt(index1) - '0' : 0;
            int b = index2 >= 0 ? num2.charAt(index2) - '0' : 0;

            int num = (a + b) + carry;
            result.append(num%10);
            carry = num/10;

            if (index1 < len1) {
                index1--;
            }
            if (index2 < len2) {
                index2--;
            }
        }

        if (carry > 0) {
            result.append(carry);
        }

        return result.reverse().toString();
    }

    /**
     * 5. 最长回文子串
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {

        if (s == null || "".equals(s) || s.length() == 1) {
            return s;
        }

        String maxPalindrome = String.valueOf(s.charAt(0));
        int len = s.length();

        boolean[][] dp = new boolean[len][len];
        // 初始化
        for (int k = 0; k < len; k++) {
            dp[k][k] = true;
        }


        for (int j = 1; j < len; j++) {
            for (int i = 0; i < j; i++) {

                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false;
                    continue;
                }

                if ((i + 1) >= (j - 1)) {
                    dp[i][j] = true;
                } else {
                    dp[i][j] = dp[i+1][j-1];
                }

                if (dp[i][j] && ((j-i+1) > maxPalindrome.length())) {
                    maxPalindrome = s.substring(i, j + 1);
                }

            }
        }

        return maxPalindrome;
    }

    /**
     * 2. 两数相加
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode result = new ListNode(-1);
        ListNode currNode = result;
        int carry = 0;
        while (l1 != null || l2 != null) {
            int num1 = l1 == null ? 0 : l1.val;
            int num2 = l2 == null ? 0 : l2.val;

            int sum = (num1 + num2) % 10 + carry;
            carry = (num1 + num2)/10;
            currNode.next = new ListNode(sum);
            currNode = currNode.next;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }

        if (carry > 0) {
            currNode.next = new ListNode(carry);
        }

        return result.next;
    }

    /**
     * 70. 爬楼梯
     * @param n
     * @return
     */
    public int climbStairs(int n) {

        if (n < 3) {
            return n;
        }

        int[] array = new int[n+1];
        array[0] = 0;
        array[1] = 1;
        array[2] = 2;

        for (int i = 3; i < n + 1; i++) {
            array[i] = array[i-1] + array[i-2];
        }

        return array[n-1]+array[n-2];
    }

    /**
     * 14. 最长公共前缀
     *  输入：strs = ["flower","flow","flight"]
     *  输出："fl"
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {

        if (strs == null) {
            return "";
        }
        
        if (strs.length == 1) {
            return strs[0];
        }

        String commonProfix = longestCommonPrefix(strs[0], strs[1]);
        for (int i = 2; i < strs.length - 1; i++) {
            commonProfix = longestCommonPrefix(commonProfix, strs[i]);
        }
        
        return commonProfix;
    }

    public String longestCommonPrefix(String str1, String str2) {
        StringBuffer commonProfix = new StringBuffer();

        if (str1 == null || "".equals(str1) ||
                str2 == null || "".equals(str2)) {
            return "";
        }

        int index1 = 0;
        int index2 = 0;
        while (index1 < str1.length() && index2 < str2.length()) {
            if (str1.charAt(index1) != str2.charAt(index2)) {
                break;
            }
            commonProfix.append(str1.charAt(index1));
            index1++;
            index2++;
        }

        return commonProfix.toString();
    }

    /**
     * 9. 回文数，转为数字，在判断
     *  121
     * @param x
     * @return
     */
    public boolean isPalindrome1(int x) {

        if (x < 0) {
            return false;
        }

        int y = x;
        int z = 0;
        while (y != 0) {
            z = z*10 + y%10;
            y = y/10;
        }

        return x == z;
    }

    /**
     * 9. 回文数，转为字符串，在判断
     *  121
     * @param x
     * @return
     */
    public boolean isPalindrome2(int x) {
        if (x < 0) {
            return false;
        }
        char[] chars = String.valueOf(x).toCharArray();

        int left = 0;
        int right = chars.length-1;

        while (left <= right) {
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }

        return true;
    }

    /**
     * 20. 有效的括号
     *  ()[]{}
     *  ([)]
     * @param s
     * @return
     */
    public boolean isValid(String s) {

        if (s == null || "".equals(s) || s.length()%2 == 1) {
            return false;
        }

        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>();
        map.put(')', '(');
        map.put('}', '{');
        map.put(']', '[');

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                if (stack.isEmpty() || stack.peek() != map.get(c)) {
                    return false;
                } else {
                    stack.pop();
                }
            } else {
                stack.push(c);
            }
        }

        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 53. 最大子序和
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {

        if (nums == null || nums.length == 0) {
            return 0;
        }

        int maxNum = nums[0];

        int currMaxNum = 0;
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (currMaxNum < 0) {
                currMaxNum = num;
            } else {
                currMaxNum += num;
            }

            if (currMaxNum > maxNum) {
                maxNum = currMaxNum;
            }
        }

        return maxNum;
    }

    /**
     * 7. 整数反转
     * @param x
     * @return
     */
    public static int reverse(int x) {
        long sum = 0;
        while (x != 0) {
             sum = sum*10 + x%10;
             if (sum > Integer.MAX_VALUE || sum < Integer.MIN_VALUE) {
                return 0;
             }
             x = x/10;
        }
        return (int) sum;
    }

    /**
     * 21. 合并两个有序链表：递归方式
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

        if (l1.val < l2.val) {
            ListNode nextNode = mergeTwoLists1(l1.next, l2);
            l1.next = nextNode;
            return l1;
        } else {
            ListNode nextNode = mergeTwoLists1(l1, l2.next);
            l2.next = nextNode;
            return l2;
        }

    }

    /**
     * 21. 合并两个有序链表
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {

        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode newNode = new ListNode(-1);
        ListNode currNode = newNode;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                currNode.next = l1;
                l1 = l1.next;
            } else {
                currNode.next = l2;
                l2 = l2.next;
            }
        }

        if (l1 != null) {
            currNode.next = l2;
        }
        if (l2 != null) {
            currNode.next = l1;
        }

       return newNode.next;
    }

    /**
     * 1. 两数之和
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {

        int[] result = new int[2];

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (map.containsKey(target - num)) {
                return new int[]{map.get(target-num), i};
            } else {
                map.put(num, i);
            }
        }

        return result;
    }

    /**
     * 206. 反转链表：迭代
     * @param head
     * @return
     */
    public ListNode reverseList1(ListNode head) {

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

    /**
     * 206. 反转链表：递归
     * @param head
     * @return
     */
    public ListNode reverseList2(ListNode head) {

        if (head == null || head.next == null) {
            return head;
        }

        ListNode nextNode = reverseList2(head.next);
        head.next.next = head;
        head.next = null;

        return nextNode;
    }
}
