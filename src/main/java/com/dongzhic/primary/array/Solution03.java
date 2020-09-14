package com.dongzhic.primary.array;

/**
 * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
 *  输入: [1,2,3,4,5,6,7] 和 k = 3
 *  输出: [5,6,7,1,2,3,4]
 *  解释:
 *  向右旋转 1 步: [7,1,2,3,4,5,6]
 *  向右旋转 2 步: [6,7,1,2,3,4,5]
 *  向右旋转 3 步: [5,6,7,1,2,3,4]
 * @author dongzc
 * @date 2018/7/4 23:24
 */
public class Solution03 {

    public static void main(String[] args) {
        int[] a = {-1,-100,3,99};
        int k = 3;
        int[] b = rotate2(a, k);
        for (int i=0;i<b.length;i++) {
            System.out.println(b[i]);
        }
    }

    /**
     * 开辟一个新数组，把旧数组的元素放入新数组中
     * @param nums
     * @param k
     * @return
     */
    public static int[] rotate2(int[] nums, int k){
        int length = nums.length;
        int newk = k % length;
        int[] newArray = new int[length];
        for (int i = 0; i < length; i++) {
            int newPosition = (i + newk) % length;
            newArray[newPosition] = nums[i];
        }
        return newArray;
    }

    /**
     * 一次移动一个位置，循环k次
     * @param nums
     * @param k
     * @return
     */
    public static int[] rotate1(int[] nums, int k) {
        int newk = k % nums.length;
        for (int i=0; i<newk; i++) {
            int temp = nums[nums.length-1];
            for (int j=nums.length-2; j>=0; j--) {
                nums[j+1] = nums[j];
            }
            nums[0] = temp;
        }
        return nums;
    }


}
