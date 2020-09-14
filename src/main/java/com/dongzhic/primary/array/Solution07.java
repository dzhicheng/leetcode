package com.dongzhic.primary.array;


/**
 *  给定一个非负整数组成的非空数组，在该数的基础上加一，返回一个新的数组。
 *      输入: [1,2,3]
 *      输出: [1,2,4]
 *      解释: 输入数组表示数字 123。
 * @author dongzc
 * @date 2018/7/28 13:56
 */
public class Solution07 {



    public static void main(String[] args) {
//        int[] nums1 = {9,8,7,6,5,4,3,2,1,0};
        int[] nums1 = {7,2,8,5,0,9,1,2,9,5,3,6,6,7,3,2,8,4,3,7,9,5,7,7,4,7,4,9,4,7,0,1,1,1,7,4,0,0,6};
//        int[] nums1 = {7,2,8,0};
        int[] result = plusOne(nums1);
        for (int i = 0 ;i < result.length; i ++) {
            System.out.println(result[i]);
        }
    }

    /**
     * @param digits
     * @return
     */
    public static int[] plusOne(int[] digits) {

        // carry：0代表不进位，1代表进位
        int carry = 1;

        for (int i = digits.length-1; i >= 0; i -- ) {
            if (carry == 0) {
                return digits;
            }
            int temp = digits[i] + carry;
            carry = temp / 10;
            digits[i] = temp % 10;
        }

        if (carry != 0) {
            int[] result = new int[digits.length+1];
            result[0] = 1;
            return result;
        }
        return digits;
    }



}
