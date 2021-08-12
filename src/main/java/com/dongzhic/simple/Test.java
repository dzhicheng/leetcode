package com.dongzhic.simple;


import com.dongzhic.common.TreeNode;
import sun.nio.ch.ThreadPool;

import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author dongzhic合并二叉树
 * @Date 3/5/21 3:17 PM
 */
public class Test {

    public static void main(String[] args) {

        String s = "abc";
    }


    public boolean isSymmetric(TreeNode root) {

        if (root == null) {
            return false;
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
            } else {
                return false;
            }
        }

        return false;
    }



}
