package com.dongzhic;

import com.dongzhic.common.ListNode;
import com.dongzhic.common.TreeNode;
import org.springframework.util.StringUtils;
import sun.misc.Unsafe;

import java.util.*;

/**
 * @Author dongzhic
 * @Date 2019-11-11 17:39
 */
public class App {

    public static void main(String[] args) {

        int[] nums = {1,2,3};
        permute(nums);
    }


    /**
     * 55. 跳跃游戏
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {

        // 最远跳跃位置
        int maxIndex = 0;
        int len = nums.length;

        for (int i = 0; i < len; i++) {

            if (i > maxIndex) {
                return false;
            }

            int currMaxIndex = i + nums[i];

            if (currMaxIndex > maxIndex) {
                maxIndex = currMaxIndex;
            }

        }

        return maxIndex > len - 1;
    }

    /**
     * 46. 全排列
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();

        dfs(nums, path, res);

        return res;
    }

    public static void dfs (int[] nums, List<Integer> path, List<List<Integer>> res) {

        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!path.contains(num)) {
                path.add(num);
                dfs(nums, path, res);
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
    public int removeElement(int[] nums, int val) {

        int left = 0;
        int right = nums.length-1;

        while (left <= right) {
            if (nums[left] == val) {
                int temp = nums[right];
                nums[right] = nums[left];
                nums[left] = temp;

                right--;
            } else {
                left++;
            }

        }

        return left;
    }

    /**
     * 110. 平衡二叉树
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {

        if (root == null) {
            return true;
        }

        // -1代表不是平衡二叉树
        return heigh(root) != -1;
    }

    public int heigh (TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftHigh = heigh(root.left);
        if (leftHigh == -1) {
            return -1;
        }

        int rightHigh = heigh(root.right);
        if (rightHigh == -1) {
            return -1;
        }

        if (Math.abs(leftHigh - rightHigh) > 1) {
            return -1;
        }

        return Math.max(leftHigh, rightHigh) + 1;
    }

    /**
     * 257. 二叉树的所有路径
     * @param root
     * @return
     */
    public static List<String> binaryTreePaths(TreeNode root) {

        List<String> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        binaryTreePaths(root, res, "");

        return res;
    }

    public static void binaryTreePaths(TreeNode root, List<String> list, String path) {

        StringBuffer str = new StringBuffer(path);
        str.append(root.val);

        if (root.left == null && root.right == null) {
            // 叶子结点
            list.add(str.toString());
        } else {
            // 非叶子结点
            str.append("->");
            if (root.left != null) {
                binaryTreePaths(root.left, list, str.toString());
            }
            if (root.right != null) {
                binaryTreePaths(root.right, list, str.toString());
            }
        }

    }

    /**
     * 448. 找到所有数组中消失的数字
     * @param nums
     * @return
     */
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> res = new ArrayList<>();

        int index = 0;
        while (index < nums.length) {
            int num  =  nums[index];
            int numIndex  = num-1;
            if (nums[numIndex] != num) {
                int tempNum = nums[numIndex];
                nums[numIndex] = nums[index];
                nums[index] = tempNum;
            } else {
                index++;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != (i+1)) {
                res.add(i+1);
            }
        }

        return res;
    }

    /**
     * 169. 多数元素
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {

        Arrays.sort(nums);

        return nums[nums.length/2];
    }

    /**
     * 543. 二叉树的直径
     * @param root
     * @return
     */
    int maxTreeLenth = 0;
    public int diameterOfBinaryTree(TreeNode root) {

        if (root == null) {
            return 0;
        }

        maxDepth2(root);

        return maxTreeLenth;
    }

    public int maxDepth2 (TreeNode root) {

        if (root == null) {
            return 0;
        }

        int leftMaxDepth = maxDepth2(root.left);
        int rightMaxDepth = maxDepth2(root.right);

        maxTreeLenth = Math.max(maxTreeLenth, leftMaxDepth + rightMaxDepth);

        return Math.max(leftMaxDepth, rightMaxDepth);
    }

    /**
     * 94. 二叉树的中序遍历
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {

        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        if (root.left != null) {
            List<Integer> leftList = inorderTraversal(root.left);
            res.addAll(leftList);
        }

        res.add(root.val);

        if (root.right != null) {
            List<Integer> rightList = inorderTraversal(root.right);
            res.addAll(rightList);
        }

        return res;
    }

    /**
     * 112. 路径总和
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
            if (root.val == targetSum) {
                return true;
            } else {
                return false;
            }
        }

        boolean b1 = hasPathSum(root.left, targetSum-root.val);
        if (b1) {
            return true;
        }

        boolean b2 = hasPathSum(root.right, targetSum-root.val);
        if (b2) {
            return true;
        }

        return false;
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
     * 680. 验证回文字符串 Ⅱ
     * @param s
     * @return
     */
    int deleteCount = 0;
    public boolean validPalindrome(String s) {

        int left = 0;
        int right = s.length()-1;

        while (left <= right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {
                deleteCount++;
                if (deleteCount > 1) {
                    return false;
                } else {
                    boolean b1 = validPalindrome(s.substring(left, right));
                    boolean b2 = validPalindrome(s.substring(left + 1, right + 1));
                    if (b1 || b2) {
                        return true;
                    } else {
                        return false;
                    }
                }
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
    public int searchInsert(int[] nums, int target) {

        int left = 0;
        int right = nums.length-1;

        while (left <= right) {
            int middle = (left+right)/2;
            if (target > nums[middle]) {
                left = middle + 1;
            } else if (target < nums[middle]) {
                right = middle - 1;
            } else {
                return middle;
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
            return false;
        }

        return isSymmetric(root.left, root.right);
    }

    public boolean isSymmetric (TreeNode root1, TreeNode root2) {

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

            if (target > nums[middle]) {
                left = middle+1;
            } else if (target < nums[middle]) {
                right = middle - 1;
            } else {
                return middle;
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

        ListNode head1 = headA;
        ListNode head2 = headB;

        while (head1 != null || head2 != null) {
            if (head1 == null) {
                head1 = headB;
            }

            if (head2 == null) {
                head2 = headA;
            }

            if (head1 == head2) {
                return head1;
            }

            head1 = head1.next;
            head2 = head2.next;
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
     * 234. 回文链表
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {

        List<Integer> list = new ArrayList<>();
        ListNode currNode = head;
        while (currNode != null) {
            list.add(currNode.val);
            currNode = currNode.next;
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
    public void reverseString(char[] s) {

        int left = 0;
        int right = s.length-1;

        for (int i = 0; i < s.length/2; i++) {
            char c = s[right];
            s[right] = s[left];
            s[left] = c;

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
            if (nums[fast] != 0) {
                int temp = nums[fast];
                nums[fast] = nums[slow];
                nums[slow] = temp;
                slow++;
            }
            fast++;
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

        TreeNode leftNode = invertTree(root.left);
        TreeNode rightNode = invertTree(root.right);

        root.left = rightNode;
        root.right = leftNode;

        return root;
    }


    /**
     * 26. 删除有序数组中的重复项
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {

        if (nums == null) {
            return 0;
        }

        int slow = 0;
        int fast = 0;

        while (fast < nums.length-1) {
            if (nums[fast] == nums[fast+1]) {
                fast++;
            } else {
                slow++;
                fast++;
                nums[slow] = nums[fast];
            }
        }

        return slow+1;
    }

    /**
     * 136. 只出现一次的数字
     * @param nums
     * @return
     */
    public int singleNumber1(int[] nums) {

        // 存储元素
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (set.contains(num)) {
                set.remove(num);
            } else {
                set.add(num);
            }
        }

        int res = nums[0];
        for (Integer val : set) {
            res = val;
        }

        return res;

    }

    /**
     * 136. 只出现一次的数字:不使用额外空间
     * @param nums
     * @return
     */
    public int singleNumber2(int[] nums) {

        int len = nums.length;
        Arrays.sort(nums);

        // 可能出现的情况
        // 1 2 2
        // 1 1 2 3 3
        int i = 0;
        while (i < len - 2) {
            if (nums[i] != nums[i+1] &&
                    nums[i+1] == nums[i+2]) {
                return nums[i];
            } else {
                i += 2;
            }
        }

        // 1 1 2 2 3情况
        return nums[len-1];
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

        ListNode currHead = head;

        while (currHead.next != null) {
            ListNode nextNode = currHead.next;
            if (currHead.val == nextNode.val) {
                currHead.next = nextNode.next;
            } else {
                currHead = currHead.next;
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

        Set<Integer> set1 = new HashSet<>();
        for (int i = 0; i < nums1.length; i++) {
            set1.add(nums1[i]);
        }

        Set<Integer> set2 = new HashSet<>();
        for (int j = 0; j < nums2.length; j ++) {
            int val = nums2[j];
            if (set1.contains(val)) {
                set2.add(val);
            }
        }

        int[] res = new int[set2.size()];
        int k = 0;
        for (Integer num : set2) {
            res[k++] = num;
        }

        return res;
    }

    /**
     * 118. 杨辉三角
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {

        int index = numRows - 1;

        List<List<Integer>> res = new ArrayList<>();
        if (index < 0) {
            return res;
        }
        res.add(Arrays.asList(1));
        if (index == 0) {
            return res;
        }
        res.add(Arrays.asList(1,1));
        if (index == 1) {
            return res;
        }

        for (int i = 2; i < numRows; i ++) {

            List<Integer> preList = res.get(i - 1);
            List<Integer> currList = new ArrayList<>();
            currList.add(1);
            for (int j = 1; j < i; j ++) {
                currList.add(preList.get(j-1) + preList.get(j));
            }
            currList.add(1);
            res.add(currList);
        }

        return res;
    }

    /**
     * 141. 环形链表
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {

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
     * 15. 三数之和
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum1(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();

        if (nums == null || nums.length < 2) {
            return res;
        }

        Arrays.sort(nums);

        Set<List<Integer>> set = new HashSet<>();
        for (int i = 0; i < nums.length; i ++) {

            int target = 0 - nums[i];
            Map<Integer, Integer> map = new HashMap<>();
            for (int j = i + 1; j < nums.length; j ++) {
                int diff = target - nums[j];
                if (map.containsKey(diff)) {
                    set.add(Arrays.asList(nums[i], diff, nums[j]));
                    map.remove(diff);
                } else {
                    map.put(nums[j], j);
                }
            }

        }

        if (set.size() > 0) {
            for (List<Integer> list : set) {
                res.add(list);
            }
        }

        return res;
    }

    /**
     * 15. 三数之和
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum2(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();
        Set<List<Integer>> set = new HashSet<>();

        if (nums == null || nums.length < 2) {
            return res;
        }

        Arrays.sort(nums);

        int len = nums.length;
        for (int i = 0; i < len; i++) {

            int left = i + 1;
            int right = len - 1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    set.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                } else if (sum < 0) {
                    left ++;
                } else {
                    right--;
                }

            }
        }

        res.addAll(set);

        return res;
    }

    /**
     * 509. 斐波那契数
     * @param n
     * @return
     */
    public int fibNew(int n) {

        if (n < 2) {
            return n;
        }

        int[] nums = new int[n+1];
        nums[0] = 0;
        nums[1] = 1;

        for (int i = 2; i <= n; i ++) {
           nums[i] = nums[i - 1] + nums[i - 2];
        }

        return nums[n];
    }



    /**
     * 88. 合并两个有序数组
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        int index1 = m - 1;
        int index2 = n -1;
        int index = nums1.length-1;

        while (index2 >= 0) {
            if (index1 < 0) {
                nums1[index--] = nums2[index2--];
            } else {
                if (nums2[index2] > nums1[index1]) {
                    nums1[index--] = nums2[index2--];
                } else {
                    nums1[index--] = nums1[index1--];
                }
            }

        }

        System.out.println();
    }

    /**
     * 3. 无重复字符的最长子串
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstringNew(String s) {

        if (s == null || "".equals(s)) {
            return 0;
        }

        // 存储元素位置
        Map<Character, Integer> map = new HashMap<>();
        // 子串开始位置
        int slow = 0;

        int maxLong = 1;

        for (int fast = 0; fast < s.length(); fast ++) {
            char c = s.charAt(fast);
            if (map.containsKey(c)) {
                slow = Math.max(slow, map.get(c) + 1);
            }

            map.put(c, fast);
            maxLong = Math.max(maxLong, fast - slow + 1);
        }

        return maxLong;
    }

    /**
     * 3. 无重复字符的最长子串
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {

        if (s == null || "".equals(s)) {
            return 0;
        }

        int maxLong = 1;

        Set<Character> set;

        for (int i = 0; i < s.length(); i ++) {
            set = new HashSet<>();
            for (int j = i; j < s.length(); j ++) {
                char c = s.charAt(j);
                if (set.contains(c)) {
                    break;
                }
                set.add(c);
            }

            if (set.size() > maxLong) {
                maxLong = set.size();
            }

        }

        return maxLong;
    }


    /**
     * 121. 买卖股票的最佳时机
     * @param prices
     * @return
     */
    public static int maxProfit(int[] prices) {

        int maxProfit = 0;

        // 最低买入金额
        int minPrice = prices[0];

        for (int i = 1; i < prices.length; i ++) {

            int currPrice = prices[i];

            maxProfit = Math.max(maxProfit, currPrice - minPrice);

            minPrice = Math.min(minPrice, currPrice);

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

        StringBuffer res = new StringBuffer();

        if (num1 == null || "".equals(num1)) {
            return num2;
        }

        if (num2 == null || "".equals(num2)) {
            return num1;
        }

        int index1 = num1.length()-1;
        int index2 = num2.length()-1;

        int carry = 0;

        while (index1 >= 0 || index2 >= 0) {

            int val1 = index1 >= 0 ? num1.charAt(index1) - '0' : 0;
            int val2 = index2 >= 0 ? num2.charAt(index2) - '0' : 0;

            int sum = val1 + val2 + carry;
            res.append(sum%10);
            carry = sum/10;

            index1--;
            index2--;
        }

        if (carry > 0) {
            res.append(carry);
        }

        return res.reverse().toString();
    }


    /**
     * 最长回文子串
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {

        if (s == null || "".equals(s) ||
                s.length() == 1) {
            return s;
        }

        String maxPalindrome = String.valueOf(s.charAt(0));

        int len = s.length();

        boolean[][] dp = new boolean[len][len];

        // 初始化
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        // right[1,4]  left[0,3]
        for (int right = 1; right < len; right ++) {
            for (int left = 0; left < right; left ++) {

                if (s.charAt(left) != s.charAt(right)) {
                    dp[left][right] = false;
                    continue;
                }

                // aba情况
                if ((left + 1) >= (right - 1)) {
                    dp[left][right] = true;
                } else {
                    dp[left][right] = dp[left+1][right-1];
                }

                if (dp[left][right] && (right-left+1) > maxPalindrome.length()) {
                    maxPalindrome = s.substring(left, right+1);
                }

            }

        }

        return maxPalindrome;
    }



    /**
     * 509. 斐波那契数
     * 斐波那契数，通常用 F(n) 表示，形成的序列称为斐波那契数列。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
     *  F(0) = 0,   F(1) = 1
     *  F(N) = F(N - 1) + F(N - 2), 其中 N > 1.
     *  给定 N，计算 F(N)。
     * @param N
     * @return
     */
    public static int fib(int n) {
        if (n <= 1) {
            return n;
        }

        int first = 0;
        int second = 1;
        for (int i = 1; i < n ; i ++) {
            int sum = first + second;
            first = second;
            second = sum;
        }
        return second;
    }




    /**
     * 魔术索引
     *  在数组A[0...n-1]中，有所谓的魔术索引，满足条件A[i] = i。给定一个有序整数数组，编写一种方法找出魔术索引，
     *  若有的话，在数组A中找出一个魔术索引，如果没有，则返回-1。若有多个魔术索引，返回索引值最小的一个。
     * @param nums
     * @return
     */
    public static int findMagicIndex(int[] nums) {

        int result = 0;

        for (int i = 0; i < nums.length; i ++) {
            if (nums[i] == i) {
                result = i;
                break;
            }
        }

        return result;
    }


    /**
     * 给一个 非空 字符串 s 和一个单词缩写 abbr ，判断这个缩写是否可以是给定单词的缩写。
     * 字符串 "word" 的所有有效缩写为：
     * ["word", "1ord", "w1rd", "wo1d", "wor1", "2rd", "w2d", "wo2", "1o1d", "1or1", "w1r1", "1o2", "2r1", "3d", "w3", "4"]
     * 注意单词 "word" 的所有有效缩写仅包含以上这些。任何其他的字符串都不是 "word" 的有效缩写。
     * 注意: 假设字符串 s 仅包含小写字母且 abbr 只包含小写字母和数字。
     *
     * @param word
     * @param abbr
     * @return
     */
    public static boolean validWordAbbreviation(String word, String abbr) {

        if (abbr.length() > word.length() || '0' == abbr.charAt(0)) {
            return false;
        }

        // 2.判断是否包含数字
        if (!hasDigit(abbr)) {
            // 不包含数字
           return abbr.equals(word);
        } else {
            if (abbr.length() == 1 && hasDigit(abbr)) {
                return word.length() == Integer.parseInt(abbr);
            }
            int index = 0;
            char [] chars = abbr.toCharArray();
            for (int i = 0; i < chars.length; i ++) {
                char c = chars[i];
                if (word.contains(String.valueOf(c))) {
                    index++;
                    continue;
                } else {
                    if (hasDigit(String.valueOf(c))) {

                        if (i > 0 && '0' == c && word.contains(String.valueOf(chars[i-1])) ) {
                            return false;
                        }

                        if (i < chars.length-1 && hasDigit(String.valueOf(chars[i+1]))) {
                            index += Integer.parseInt(String.valueOf(c)) * 10 + Integer.parseInt(String.valueOf(chars[i+1]));
                            i ++;
                        } else {
                            index += Integer.parseInt(String.valueOf(c));
                        }
                        continue;
                    } else {
                        return false;
                    }
                }
            }

            return index == word.length();
        }
    }

    public static boolean hasDigit (String s) {
        boolean flag = false;

//        Pattern p = Pattern.compile(".*\\d+.*");
//        Matcher m = p.matcher(s);
//        if (m.matches()) {
//            flag = true;
//        }

        for (int i = 0; i < s.length(); i ++){
            if (Character.isDigit(s.charAt(i))) {
                flag = true;
                break;
            }
        }

        return flag;
    }


    /**
     * 293. 翻转游戏
     *  你和朋友玩一个叫做「翻转游戏」的游戏，游戏规则：给定一个只有 + 和 - 的字符串。你和朋友轮流将 连续 的两个 "++" 反转成 "--"。
     *  当一方无法进行有效的翻转时便意味着游戏结束，则另一方获胜。
     *  请你写出一个函数，来计算出第一次翻转后，字符串所有的可能状态。
     *
     *  输入: s = "++++"
     * 输出:
     * [
     *   "--++",
     *   "+--+",
     *   "++--"
     * ]
     *
     * 如果不存在可能的有效操作，请返回一个空列表 []。
     *
     * @param s
     * @return
     */
    public static List<String> generatePossibleNextMoves(String s) {

        List<String> resultList = new ArrayList<>();

        int len = s.length();

        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length-1; i ++) {
            String str = "";
            char c = chars[i];
            if (chars[i] == '+' && chars[i+1] == '+') {
                str = s.substring(0, i) + "--" + s.substring(i+2, len);
                resultList.add(str);
            }
        }

        return resultList;
    }


    private String file = "abcde";
    /**
     * 157. 用 Read4 读取 N 个字符
     * @param buf
     * @param n
     * @return
     */
    public static int read(char[] buf, int n) {
        return 0;
    }


    /**
     * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     *
     * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
     * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
     *
     * @param matrix
     * @return
     */
    public int[] spiralOrder(int[][] matrix) {

        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return new int [0];
        }
        int rows = matrix.length;
        int columns = matrix[0].length;

        int left = 0, right = columns -1, top = 0, bottom = rows -1;

        int [] order = new int[rows * columns];
        int index = 0;

        while (left <= right && top <= bottom) {

            for (int column = left; column <= right; column ++) {
                order[index++] = matrix[top][column];
            }

            for (int row = top + 1; row <= bottom; row ++) {
                order[index++] = matrix[row][right];
            }

            if (left < right && top < bottom) {

                for (int column = right - 1; column > left; column --) {
                    order[index++] = matrix[bottom][column];
                }
                for (int row = bottom ; row > top; row --) {
                    order[index++] = matrix[row][left];
                }
            }

            left++;
            top++;
            right--;
            bottom--;
        }
        return order;
    }


    /**
     * 求1+2+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
     * 输入: n = 3
     * 输出: 6
     * @param n
     * @return
     */
    private static int res = 0;
    public static int sumNums(int n) {
        boolean x = (n > 1) && (sumNums(n-1) > 0);
        res += n;
        return res;
    }

    /**
     * 1431. 拥有最多糖果的孩子
     *  给你一个数组 candies 和一个整数 extraCandies ，其中 candies[i] 代表第 i 个孩子拥有的糖果数目。
     *  对每一个孩子，检查是否存在一种方案，将额外的 extraCandies 个糖果分配给孩子们之后，此孩子有 最多 的糖果。
     *  注意，允许有多个孩子同时拥有 最多 的糖果数目。
     *
     * 输入：candies = [2,3,5,1,3], extraCandies = 3
     * 输出：[true,true,true,false,true]
     * 解释：
     * 孩子 1 有 2 个糖果，如果他得到所有额外的糖果（3个），那么他总共有 5 个糖果，他将成为拥有最多糖果的孩子。
     * 孩子 2 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。
     * 孩子 3 有 5 个糖果，他已经是拥有最多糖果的孩子。
     * 孩子 4 有 1 个糖果，即使他得到所有额外的糖果，他也只有 4 个糖果，无法成为拥有糖果最多的孩子。
     * 孩子 5 有 3 个糖果，如果他得到至少 2 个额外糖果，那么他将成为拥有最多糖果的孩子。
     *
     * @param candies
     * @param extraCandies
     * @return
     */
    public static List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {

        List<Boolean> result = new ArrayList<>();

        int maxNum = 0;
        for (int i = 0 ; i < candies.length; i ++) {
            if (maxNum < candies[i]) {
                maxNum = candies[i];
            }
        }

        for (int i = 0 ; i < candies.length; i ++) {
            if (candies[i] + extraCandies >= maxNum) {
                result.add(true);
            } else {
                result.add(false);
            }
        }

        return result;
    }


    /**
     * 14. 你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
     *      如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。
     *      给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
     *
     *      输入: [1,2,3,1]
     *      输出: 4
     *      解释: 偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
     *          偷窃到的最高金额 = 1 + 3 = 4 。
     *
     * @param strs
     * @return
     */
    public static int rob(int[] nums) {

        int len = nums.length;

        int [] dp = new int[len+1];

        dp[0] = 0;
        dp[1] = nums[0];

        for (int i = 2; i < nums.length+1; i ++) {
            dp[i] = Math.max(dp[i-1], dp[i-2] + nums[i-1]);
        }
        return dp[len];
    }

    public static int max (int a, int b) {
        if (a > b) {
            return b;
        }
        return b;
    }

    /**
     * 9. 回文数
     * @param x
     * @return
     */
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        return reverse2(x) == x ? true : false;
    }

    /**
     * 7：整数反转：给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
     * 输入: -123 -110
     * 输出: -321 -11
     * @param x
     * @return
     */
    public static int reverse1(int x) {
        List<Character> list = new ArrayList<>();

        String num = String.valueOf(x);
        char[] chars = num.toCharArray();
        // 循环起始位置
        int start = 0;
        if ('-' == chars[0]) {
            start ++;
            list.add('-');
        }
        for (int i = chars.length - 1; i >= start ; i --) {
            char c = chars[i];
            if (i == chars.length - 1) {
                if ('0' != c) {
                    list.add(c);
                }
            } else {
                list.add(c);
            }
        }

        char[] result = new char[list.size()];
        for (int i = 0; i < list.size(); i ++) {
            result[i]= list.get(i);
        }

        long resultOfLong = Long.valueOf(String.valueOf(result));

        if (resultOfLong > Integer.MAX_VALUE || resultOfLong < Integer.MIN_VALUE) {
            return 0;
        } else {
            return Integer.valueOf(String.valueOf(result));
        }
    }

    /**
     * 7：整数反转：给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
     * 输入: 123 -123 -110
     * 输出: 321 -321 -11
     * @param x
     * @return
     */
    public static int reverse2(int x) {

        long result = 0;
        while (x != 0) {
            result = result*10 + x%10;
            x = x/10;
        }

        if ((int)result != result) {
            return  0;
        } else {
            return (int)result;
        }
    }





}
