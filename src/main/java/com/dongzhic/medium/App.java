package com.dongzhic.medium;


import com.dongzhic.common.ListNode;
import com.dongzhic.common.TreeNode;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Author dongzhic
 * @Date 3/18/21 11:57 PM
 */
public class App {

    public static void main(String[] args) {
        ListNode l1 = new ListNode(1);
        ListNode l2 = new ListNode(2);
        ListNode l3 = new ListNode(3);
        ListNode l4 = new ListNode(4);
        ListNode l5 = new ListNode(5);

        l1.next = l2;
        l2.next = l3;
        l3.next = l4;
        l4.next = l5;

        getKthFromEnd11(l1, 2);
    }

    /**
     * 剑指 Offer 22. 链表中倒数第k个节点
     *  两次遍历
     * @param head
     * @param k
     * @return
     */
    public static ListNode getKthFromEnd11(ListNode head, int k) {
        // 1.获取链表的长度
        int len = 0;
        ListNode node1 = head;
        while (node1 != null) {
            len++;
            node1 = node1.next;
        }

        int index = 0;
        ListNode node2 = head;
        while (index != len - k) {
            index++;
            node2 = node2.next;
        }

        return node2;
    }

    /**
     * 剑指 Offer 22. 链表中倒数第k个节点
     *  快慢指针
     * @param head
     * @param k
     * @return
     */
    public static ListNode getKthFromEnd12(ListNode head, int k) {

        // 1.初始化，使两个指针相差k
        ListNode slowNode = head;
        ListNode fastNode = head;
        for (int i = 0; i < k; i++) {
            fastNode = fastNode.next;
        }

        // 2.两个指针同时移动，当快指针为空是，慢指针为倒数第k个节点
        while (fastNode != null) {
            slowNode = slowNode.next;
            fastNode = fastNode.next;
        }

        return slowNode;
    }

    /**
     * 46. 全排列
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute1(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();

        if (nums == null || nums.length == 0) {
            return result;
        }

        dfsPermute1(nums, new ArrayList<>(), result);

        return result;
    }
    public static void dfsPermute1(int[] nums, List<Integer> path, List<List<Integer>> res) {

        // 递归中止
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        // 去重
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if (!path.contains(num)) {
                path.add(num);
                dfsPermute1(nums, path, res);
                path.remove(path.size() - 1);
            }
        }
    }


    /**
     * 662. 二叉树最大宽度：遍历
     * BFS + 完全二叉树特性
     * @param root
     * @return
     */
    public static int widthOfBinaryTree(TreeNode root) {

        // 判空
        if (root == null) {
            return 0;
        }

        int maxWidth = 1;

        LinkedList<TreeNode> treeList = new LinkedList<>();
        LinkedList<Integer> indexTreeList = new LinkedList<>();

        // 初始化
        treeList.add(root);
        indexTreeList.add(1);

        while (!treeList.isEmpty()) {

            int currWith = treeList.size();
            int leftIndex = indexTreeList.peek();
            int rightIndex = leftIndex;

            while (currWith > 0) {
                TreeNode currNode = treeList.poll();
                rightIndex = indexTreeList.poll();

                if (currNode.left != null) {
                    treeList.add(currNode.left);
                    indexTreeList.add(rightIndex * 2);
                }
                if (currNode.right != null) {
                    treeList.add(currNode.right);
                    indexTreeList.add(rightIndex * 2 + 1);
                }

                currWith--;
            }

            int currMaxWith = rightIndex - leftIndex + 1;
            maxWidth = Math.max(maxWidth, currMaxWith);

        }

        return maxWidth;
    }

    /**
     * 142. 环形链表 II
     *  [-21,10,17,8,4,26,5,35,33,-7,-16,27,-12,6,29,-12,5,9,20,14,14,2,13,-24,21,23,-21,5]
     * 24
     * @param head
     * @return
     */
    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {

            slow = slow.next;
            fast = fast.next.next;

            // 找到相遇节点
            if (slow == fast) {

                ListNode cNode = head;
                ListNode bNode = slow;

                while (true) {
                    if (cNode == bNode) {
                        return cNode;
                    }

                    cNode = cNode.next;
                    bNode = bNode.next;
                }
            }

        }

        return null;
    }

    /**
     * 200. 岛屿数量：DFS(深度优先遍历)
     * @param grid
     * @return
     */
    public static int numIslands(char[][] grid) {
        // 岛屿数量
        int numIsLands = 0;

        if (grid == null || grid.length == 0) {
            return numIsLands;
        }

        int rowLen = grid.length;
        int colLen = grid[0].length;

        for (int i = 0; i < rowLen; i++) {
            for (int j = 0; j < colLen; j++) {

                if (grid[i][j] == '1') {
                    numIsLands = numIsLands + 1;
                    dfsNumIsLands(grid, i, j, rowLen, colLen);
                }

            }
        }

        return numIsLands;
    }

    public static void dfsNumIsLands (char[][] grid, int i, int j, int rowLen, int colLen) {

        // 界限判断
        if (i < 0 || j < 0 || i >= rowLen || j >= colLen || grid[i][j] == '0') {
            return;
        }

        grid[i][j] = '0';

        dfsNumIsLands(grid,i - 1, j, rowLen, colLen);
        dfsNumIsLands(grid,i + 1, j, rowLen, colLen);
        dfsNumIsLands(grid,i, j - 1, rowLen, colLen);
        dfsNumIsLands(grid,i, j + 1, rowLen, colLen);

    }

    /**
     * 33. 搜索旋转排序数组
     * @param nums
     * @param target
     * @return
     */
    public static int search(int[] nums, int target) {

        if (nums == null) {
            return -1;
        }
        if (nums.length == 1) {
            return nums[0] == target ? nums[0] : -1;
        }

        int reverseIndex = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                reverseIndex = i;
                break;
            }
        }

        int leftTarget = binarySearch(nums, 0, reverseIndex, target);
        int rightTarget = binarySearch(nums, reverseIndex + 1, nums.length - 1, target);

        return leftTarget != -1 ? leftTarget : rightTarget;
    }

    public static int binarySearch (int[] nums, int left, int right, int target) {

        while (left <= right) {
            int middle = (left + right) / 2;
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
     * 236. 二叉树的最近公共祖先：递归
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode pqLeft = lowestCommonAncestor(root.left, p, q);
        TreeNode pqRight = lowestCommonAncestor(root.right, p, q);

        if (pqLeft == null) {
            return pqRight;
        }

        if (pqRight == null) {
            return pqLeft;
        }

        return root;
    }

    /**
     * 236. 二叉树的最近公共祖先：递归
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor1(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        boolean pInLeft = findNode1(root.left, p) != null;
        boolean pInRight = !pInLeft;

        boolean qInLeft = findNode1(root.left, q) != null;
        boolean qInRight = !qInLeft;

        if (pInLeft && qInLeft) {
            return lowestCommonAncestor1(root.left, p, q);
        }
        if (pInRight && qInRight) {
            return lowestCommonAncestor1(root.right, p, q);
        }

        return root;
    }
    /**
     * 查找某个节点
     * @param root 根节点
     * @param node 查找节点
     * @return
     */
    public TreeNode findNode1 (TreeNode root, TreeNode node) {
        if (root == null || root == node) {
            return root;
        }

        TreeNode leftNode = findNode1(root.left, node);
        TreeNode rightNode = findNode1(root.right, node);

        return leftNode == null ? rightNode : leftNode;
    }

    /**
     * 236. 二叉树的最近公共祖先：递归优化，一次找两个节点
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode pqInLeft = findNode2(root.left, p, q);
        TreeNode pqInRight = findNode2(root.right, p, q);

        if (pqInLeft == null) {
            return lowestCommonAncestor2(root.right, p, q);
        }
        if (pqInRight == null) {
            return lowestCommonAncestor2(root.left, p, q);
        }

        return root;
    }
    public TreeNode findNode2 (TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == p || root == q) {
            return root;
        }

        TreeNode leftNode = findNode2(root.left, p, q);
        TreeNode rightNode = findNode2(root.right, p, q);

        return leftNode == null ? rightNode : leftNode;
    }

    /**
     * 92. 反转链表 II：将链表切分为三个部分
     * @param head
     * @param left
     * @param right
     * @return
     */
    public static ListNode reverseBetween(ListNode head, int left, int right) {

            if (left == right) {
                return head;
            }

            ListNode newNode = new ListNode(-1);
            newNode.next = head;
            ListNode currNode = newNode;

            for (int i = 1; i < left; i++) {
                currNode = currNode.next;
            }

            ListNode leftEndNode = currNode;
            for (int j = left; j <= right; j ++) {
                currNode = currNode.next;
            }

            ListNode rightNode = currNode.next;
            ListNode middleNode = leftEndNode.next;

            currNode.next = null;
            leftEndNode.next = null;
            leftEndNode.next = reverseNode(middleNode);

            middleNode.next = rightNode;

            return newNode.next;
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

        ListNode newHead = null;
        while (head != null) {
            ListNode nextNode = head.next;

            head.next = newHead;
            newHead = head;

            head = nextNode;
        }

        return newHead;
    }

    /**
     * 102. 二叉树的层序遍历：使用栈存储节点
     * @param root
     * @return
     */
    public static List<List<Integer>> levelOrder(TreeNode root) {

        List<List<Integer>> result = new ArrayList<>();
        Stack<List<TreeNode>> stack = new Stack<>();

        if (root == null) {
            return result;
        }

        // 初始化
        List<TreeNode> rootList = new ArrayList<>();
        rootList.add(root);
        stack.push(rootList);

        while (!stack.isEmpty()) {
            List<TreeNode> currList = stack.pop();
            List<TreeNode> nextList = new ArrayList<>();
            List<Integer> list = new ArrayList<>();

            for (TreeNode treeNode : currList) {
                list.add(treeNode.val);

                if (treeNode.left != null) {
                    nextList.add(treeNode.left);
                }
                if (treeNode.right != null) {
                    nextList.add(treeNode.right);
                }
            }

            if (nextList.size() > 0) {
                stack.push(nextList);
            }

            result.add(list);
        }

        return result;
    }

    /**
     * 103. 二叉树的锯齿形层序遍历
     * @param root
     * @return
     */
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {

        List<List<Integer>> result = new ArrayList<>();
        Stack<List<TreeNode>> stack = new Stack<>();

        if (root == null) {
            return result;
        }

        // 初始化
        List<TreeNode> rootList = new ArrayList<>();
        rootList.add(root);
        stack.push(rootList);

        // 奇数代表正序，偶数代表倒叙
        int odd = 1;
        while (!stack.isEmpty()) {
            List<TreeNode> currList = stack.pop();
            List<TreeNode> nextList = new ArrayList<>();
            List<Integer> list = new ArrayList<>();

            for (TreeNode node : currList) {
                list.add(node.val);

                if (node.left != null) {
                    nextList.add(node.left);
                }
                if (node.right != null) {
                    nextList.add(node.right);
                }
            }

            if (nextList.size() > 0) {
                stack.add(nextList);
            }

            if (odd % 2 == 0) {
                Collections.reverse(list);
            }

            result.add(list);
            odd++;
        }

        return result;
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     * 前序遍历：根左右
     * 中序遍历：左根右
     * @param preorder
     * @param inorder
     * @return
     */
    public static TreeNode buildTree(int[] preorder, int[] inorder) {

        if (preorder == null || inorder == null
                || preorder.length == 0 || inorder.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode();
        if (preorder != null && preorder.length > 0) {
            root.val = preorder[0];
        }

        // 查找中序遍历中，根节点的索引
        int indexInorderRoot = 0;
        List<Integer> inorderLeftList = new ArrayList<>();
        List<Integer> inorderRightList = new ArrayList<>();
        // 分界标识
        boolean inorderFlag = true;
        for (int i = 0; i < inorder.length; i++) {
            int value = inorder[i];
            if (value == root.val) {
                inorderFlag = false;
                continue;
            }
            if (inorderFlag) {
                inorderLeftList.add(value);
            } else {
                inorderRightList.add(value);
            }
        }

        // 将前序遍历分为左右子树
        List<Integer> preorderLeftList = new ArrayList<>();
        List<Integer> preorderRightList = new ArrayList<>();
        // 分界标识
        boolean preorderFlag = true;
        // 左子树最后一位的索引
        int maxLeftIndex = inorderLeftList.size();

        for (int j = 1; j < preorder.length; j ++) {
            int value = preorder[j];
            if (j > maxLeftIndex) {
                preorderFlag = false;
            }

            if (preorderFlag) {
                preorderLeftList.add(value);
            } else {
                preorderRightList.add(value);
            }
        }

        TreeNode leftNode = buildTree(preorderLeftList.stream().mapToInt(i->i).toArray(), inorderLeftList.stream().mapToInt(i->i).toArray());
        TreeNode rightNode = buildTree(preorderRightList.stream().mapToInt(i->i).toArray(), inorderRightList.stream().mapToInt(i->i).toArray());

        if (leftNode != null) {
            root.left = leftNode;
        }
        if (rightNode != null) {
            root.right = rightNode;
        }

        return root;
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树：优化
     * 前序遍历：根左右
     * 中序遍历：左根右
     * @param preorder 前序遍历
     * @param inorder 中序遍历
     * @return
     */
    public static TreeNode buildTree2(int[] preorder, int[] inorder) {

        // 1.判空
        if (preorder == null || inorder == null
                || preorder.length == 0 || inorder.length == 0) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[0]);

        // 2.在中序遍历中，查找左右子树的个数
        int index = 0;
        for (int i = 0; i < inorder.length; i++) {
            if (inorder[i] == root.val) {
                index = i;
                break;
            }
        }
        int leftNumber = index;
        int rightNumber = preorder.length - (index+1);
        int[] inorderLeftArray = new int[leftNumber];
        int[] inorderRightArray = new int[rightNumber];
        int[] preorderLeftArray = new int[leftNumber];
        int[] preorderRightArray = new int[rightNumber];

        // 3.将左右遍历数组，分别拆分成左子树部分，右子树部分
        // 查找中序遍历中，根节点的索引
        int indexInorderLeft = 0;
        int indexInorderRight = 0;
        for (int i = 0; i < inorder.length; i++) {
            int value = inorder[i];
            if (i < leftNumber) {
                inorderLeftArray[indexInorderLeft++] = value;
            } else if (i > leftNumber) {
                inorderRightArray[indexInorderRight++] = value;
            }
        }

        // 将前序遍历分为左右子树
        int indexPreorderLeft = 0;
        int indexPreorderRight = 0;
        for (int j = 1; j < preorder.length; j ++) {
            int value = preorder[j];
            if (j <= leftNumber) {
                preorderLeftArray[indexPreorderLeft++] = value;
            } else {
                preorderRightArray[indexPreorderRight++] = value;
            }
        }

        // 4.递归左子树，右子树
        TreeNode leftNode = buildTree(preorderLeftArray, inorderLeftArray);
        TreeNode rightNode = buildTree(preorderRightArray, inorderRightArray);

        if (leftNode != null) {
            root.left = leftNode;
        }
        if (rightNode != null) {
            root.right = rightNode;
        }

        return root;
    }


    /**
     * 781. 森林中的兔子
     * @param answers
     * @return
     */
    public static int numRabbits(int[] answers) {

        if (answers == null || answers.length == 0) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();

        // key：该颜色兔子的个数，value：这个个数的兔子的个数
        for (int i = 0; i < answers.length; i++) {
            int key = answers[i];
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        int res = 0;
        for (Integer key : map.keySet()) {
            // 某颜色兔子的个数
            int count = key + 1;
            // 说该兔子个数的兔子数量
            int value = map.get(key);

            res += (int) Math.ceil((double) value/count) * count;
        }

        return res;
    }

    /**
     * 143. 重排链表
     * @param head
     */
    public static void reorderList(ListNode head) {

        if (head == null || head.next == null) {
            return;
        }

        int listLength = 0;

        ListNode currNode = head;
        while (currNode != null) {
            listLength++;
            currNode = currNode.next;
        }

        ListNode l1 = head;
        ListNode nextNode = l1;
        for (int i = 0; i < listLength/2 - 1; i ++) {
            nextNode = nextNode.next;
        }
        // 链表个数为奇数处理
        if (listLength%2 != 0) {
            nextNode = nextNode.next;
        }

        ListNode l2Head = nextNode.next;
        nextNode.next = null;


        ListNode l2 = reverseList(l2Head);

        ListNode newHead = l1;
        while (l2 != null) {
            ListNode l1NextNode = newHead.next;
            ListNode l2NextNode = l2.next;

            newHead.next = l2;
            l2.next = null;
            newHead = newHead.next;

            l2 = l2NextNode;
            newHead.next = l1NextNode;
            newHead = newHead.next;

        }

    }

    public static ListNode reverseList (ListNode head) {

        ListNode newNode = null;

        while (head != null) {
            ListNode currNode = head.next;

            head.next = newNode;
            newNode = head;
            head = currNode;

        }

        return newNode;
    }

    /**
     * 1190. 反转每对括号间的子串
     * @param s
     * @return
     */
    public static String reverseParentheses1(String s) {

        StringBuffer res = new StringBuffer();
        if (s == null || "".equals(s)) {
            return "";
        }

        Stack<Character> stack = new Stack<>();

        int index = 0;
        while (index < s.length()){
            char c = s.charAt(index);
            stack.push(c);
            if ( c == ')') {
                StringBuffer sb = new StringBuffer();
                while (stack.peek() != '(') {
                    if (stack.peek() != ')') {
                        sb.append(stack.pop());
                    } else {
                        stack.pop();
                    }
                }
                // 把'（'去掉
                stack.pop();
                for (int i = 0; i < sb.length(); i ++) {
                    stack.push(sb.charAt(i));
                }
            }
            index++;
        }

        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }

        return res.reverse().toString();
    }

    /**
     * 1190. 反转每对括号间的子串:优化
     * @param s
     * @return
     */
    public static String reverseParentheses2(String s) {

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
     * 45. 跳跃游戏 II
     * @param nums
     * @return
     */
    public int jump(int[] nums) {


        return 0;
    }

    /**
     * 55. 跳跃游戏：贪心算法
     *  每次跳最大位置，看最后所在的位置是否大于数组最后的位置
     * @param nums
     * @return
     */
    public static boolean canJump(int[] nums) {

        // 记录跳跃最大位置
        int maxIndex = 0;

        // 数组长度
        int len = nums.length;

        for (int i = 0; i < len; i++) {

            // 当前位置大于maxIndex，无法跳到数组最后位置
            if (i > maxIndex) {
                return false;
            }

            // 当前能跳到最大位置
            int currMaxIndex = i + nums[i];

            if (currMaxIndex > maxIndex) {
                maxIndex = currMaxIndex;
            }
        }

        return maxIndex >= len - 1;
    }

    /**
     * 46. 全排列:回溯算法
     * @param nums
     * @return
     */
    public static List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> res = new ArrayList<>();
        // 存储一次情况
        List<Integer> path = new ArrayList<>();

        dfs(nums, path, res);

        return res;
    }

    /**
     * 树的深度优先遍历dfs
     * @param nums
     * @param path
     * @param res
     */
    public static void dfs (int[] nums, List<Integer> path, List<List<Integer>> res) {

        // 递归终止
        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!path.contains(nums[i])) {
                path.add(nums[i]);
                dfs(nums, path, res);
                path.remove(path.size()-1);
            }
        }

    }


    /**
     * 穷举数组，转为n叉树
     * @param nums
     * @param index
     * @param path
     * @param res
     */
    public static void dfs1 (int[] nums, int index, List<Integer> path, List<List<Integer>> res) {

        // 停止递归
        if (path.size() == nums.length) {
            return;
        }

        if (index != -1) {
            path.add(nums[index]);
        }

        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
        }

        dfs1(nums, 0, path, res);
        dfs1(nums, 1, path, res);
        dfs1(nums, 2, path, res);

        if (index != -1) {
            path.remove(path.size()-1);
        } else {
            System.out.println();
        }

    }

    /**
     * 穷举数组，转为n叉树：优化1
     * @param nums
     * @param index
     * @param path
     * @param res
     */
    public static void dfs2 (int[] nums, int index, List<Integer> path, List<List<Integer>> res) {

        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        path.add(nums[0]);
        dfs2(nums, 0, path, res);
        path.remove(path.size()-1);

        path.add(nums[1]);
        dfs2(nums, 1, path, res);
        path.remove(path.size()-1);

        path.add(nums[2]);
        dfs2(nums, 2, path, res);
        path.remove(path.size()-1);

    }

    /**
     * 穷举数组，转为n叉树：优化2
     * @param nums
     * @param path
     * @param res
     */
    public static void dfs3 (int[] nums, List<Integer> path, List<List<Integer>> res) {

        if (path.size() == nums.length) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            path.add(nums[i]);
            dfs3(nums, path, res);
            path.remove(path.size()-1);
        }

    }


    /**
     * 11. 盛最多水的容器：暴力法（超时）
     * @param height
     * @return
     */
    public static int maxArea(int[] height) {

        if (height == null || height.length == 1) {
            return 0;
        }

        int maxNum = 0;
        for (int i = 0; i < height.length - 1; i ++) {
            for (int j = i + 1; j < height.length; j ++) {
                // 深度
                int depth = Math.min(height[i], height[j]);
                // 宽度
                int width = j-i;
                if (depth * width > maxNum) {
                    maxNum = depth * width;
                }
            }
        }

        return maxNum;
    }

    /**
     * 912. 排序数组：冒泡排序(超出时间限制)
     * @param nums
     * @return
     */
    public int[] sortArray1(int[] nums) {

        int len = nums.length;
        for (int i = 0; i < len - 1; i ++) {
            for (int j = 0; j < len - 1 - i; j ++) {
                if (nums[j] > nums[j+1]) {
                    int temp = nums[j];
                    nums[j] = nums[j+1];
                    nums[j+1] = temp;
                }
            }
        }

        return nums;
    }

    /**
     * 912. 排序数组：冒泡排序(超出时间限制)
     * @param nums
     * @return
     */
    public static int[] sortArray2(int[] nums) {

        if (nums == null || nums.length == 1) {
            return nums;
        }

        quickSort(nums, 0, nums.length - 1);

        return nums;
    }
    public static void quickSort (int[] array, int begin, int end) {

        if (begin < end) {
            int low = begin;
            int high = end;
            // 基准数
            int pivot = array[begin];

            while (low < high) {

                // 从右向做检索比基准数小的位置
                while (array[high] >= pivot && low < high) {
                    high--;
                }

                // 从左向右检索比基准数大的位置
                while (array[low] <= pivot && low < high) {
                    low++;
                }

                int temp = array[low];
                array[low] = array[high];
                array[high] = temp;

            }

            // 交换基准数的位置
            array[begin] = array[low];
            array[low] = pivot;

            quickSort(array, begin, low -1);
            quickSort(array, low + 1, end);
        }

    }


    /**
     * 15. 三数之和：集合两数求和算法
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum1(int[] nums) {

        Arrays.sort(nums);

        // 存放三元组
        List<List<Integer>> list = new ArrayList<>();
        Set<List<Integer>> set = new HashSet<>();

        for (int i = 0; i < nums.length - 1; i ++) {

            int target = 0 - nums[i];
            // 通过一个map下target相等，diff相等，就相当于重复了
            Map<Integer, Integer> map = new HashMap<>(16);
            for (int j = i + 1; j < nums.length; j ++) {

                // 两数之和
                int diff = target - nums[j];
                if (map.containsKey(diff)) {
                    set.add(Arrays.asList(nums[i], nums[j], diff));
                    map.remove(diff);
                } else {
                    map.put(nums[j], j);
                }

            }

        }

        list.addAll(set);

        return list;
    }

    /**
     * 15. 三数之和
     * @param nums
     * @return
     */
    public static List<List<Integer>> threeSum2(int[] nums) {

        Arrays.sort(nums);

        // 存放三元组
        List<List<Integer>> list = new ArrayList<>();
        Set<List<Integer>> set = new HashSet<>();

        int len = nums.length;
        for (int i = 0; i < len; i ++) {

            int left = i + 1;
            int right = len - 1;

            while (left < right) {

                int sum = nums[i] + nums[left] + nums[right];
                if (sum == 0) {
                    set.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    left++;
                    right--;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }

        }

        list.addAll(set);

        return list;
    }

    /**
     * 3. 无重复字符的最长子串
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring1(String s) {

        if (s == null || "".equals(s)) {
            return 0;
        }

        // 最长子串长度
        int maxLong = 1;

        // 子串起始位置
        int slow = 0;
        // 存储字符位置
        Map<Character, Integer> map = new HashMap<>();

        for (int fast = 0; fast < s.length(); fast ++) {

            char c = s.charAt(fast);
            if (map.containsKey(c)) {
                // 避免abba情况
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
    public static int lengthOfLongestSubstring2(String s) {

        int maxLen = 0;
        for (int i = 0; i < s.length(); i ++) {
            Set<Character> set = new HashSet<>();
            for (int j = i; j < s.length(); j ++) {
                if (set.contains(s.charAt(j))) {
                    break;
                }
                maxLen = Math.max(maxLen, j- i +1);
                set.add(s.charAt(j));
            }
        }

        return maxLen;
    }


    /**
     * 5. 最长回文子串:动态规划
     * @param s
     * @return
     */
    public static String longestPalindrome(String s) {

        if (s == null || "".equals(s) || s.length() == 1) {
            return s;
        }

        int len = s.length();

        String maxStr = String.valueOf(s.charAt(0));

        boolean[][] dp = new boolean[len][len];

        for (int i = 0; i < len; i ++) {
            dp[i][i] = true;
        }

        for (int right = 1; right < len; right ++) {
            for (int left = 0; left < right; left ++) {

                if (s.charAt(left) != s.charAt(right)) {
                    dp[left][right] = false;
                    continue;
                }

                // 判断字符串长度为2，3时, 直接返回true（aba，bb）
                if (left + 1 >= right -1) {
                    dp[left][right] = true;
                } else {
                    dp[left][right] = dp[left+1][right-1];
                }

                if (dp[left][right] && right-left+1 > maxStr.length()) {
                    maxStr = s.substring(left, right+ 1);
                }

            }
        }

        return maxStr;
    }


    /**
     * 2. 两数相加
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        if (l1 == null && l2 == null) {
            return null;
        }

        ListNode newHead = new ListNode(0);
        ListNode currHead = newHead;

        int carry = 0;
        while (l1 != null || l2 != null) {
            int v1 = l1 != null ? l1.val : 0;
            int v2 = l2 != null ? l2.val : 0;

            int num = v1 + v2 + carry;
            carry = num / 10;

            currHead.next = new ListNode(num%10);
            currHead = currHead.next;

            if (l1 != null) {
                l1 = l1.next;
            }

            if (l2 != null) {
                l2 = l2.next;
            }

        }

        if (carry > 0) {
            currHead.next = new ListNode(carry);
        }

        return newHead.next;
    }
}
