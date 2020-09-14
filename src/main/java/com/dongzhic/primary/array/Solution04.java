package com.dongzhic.primary.array;

import java.util.HashSet;

/**
 * 给定一个整数数组，判断是否存在重复元素。
 *  如果任何值在数组中出现至少两次，函数返回 true。如果数组中每个元素都不相同，则返回 false。
 * @author dongzc
 * @date 2018/7/26 11:45
 */
public class Solution04 {

    public static void main(String[] args) {
        int[] nums = {1,1,1,3,3,4,3,2,4,2};
        System.out.println(containsDuplicate2(nums));
    }

    /**
     *  hashMap不允许有重复元素
     * @param nums
     * @return
     */
    public static boolean containsDuplicate2(int[] nums) {
        HashSet<Integer> hashSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++){
            hashSet.add(nums[i]);
        }
        if (hashSet.size() != nums.length) {
            return true;
        } else {
            return false;
        }
    }

        /**
         * 效率低
         * @param nums
         * @return
         */
    public static boolean containsDuplicate1(int[] nums) {
        // 1.通过冒泡排序对数组进行排序
        for (int i = 0; i< nums.length-1; i++) {
            for (int j = i+1 ; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
                if (nums[j-1] > nums[j]) {
                    int temp = nums[j];
                    nums[j] = nums[j-1];
                    nums[j-1] = temp;
                }
            }
        }
        return false;
    }

}
