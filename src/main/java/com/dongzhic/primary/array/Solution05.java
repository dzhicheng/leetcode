package com.dongzhic.primary.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *  给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *  具有线性时间复杂度,不使用额外空间
 * @author dongzc
 * @date 2018/7/27 15:04
 */
public class Solution05 {

    public static void main(String[] args) {
        int[] arrays = {4,1,2,1,2};
        System.out.println(singleNumber2(arrays));
    }


    /**
     * 通过HashMap记录出现的次数
     * @param nums
     * @return
     */
    public static int singleNumber1(int[] nums) {

        Map<Integer, Integer> map = new HashMap<>(16);
        // 4,1,2,1,2
        for (int i = 0; i < nums.length; i ++ ) {
            Integer value = map.get(nums[i]);
            map.put(nums[i], (value == null ? 0 : value) +1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return  entry.getKey();
            }
        }
        return 0;
    }

    public static int singleNumber2(int[] nums) {

        // 4,1,2,1,2(1,1,2,2,4)
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i += 2) {
            if (i + 1 >= nums.length) {
                return nums[i];
            }
            if (nums[i] != nums[i+1] ) {
                return nums[i];
            }
        }
        return 0;
    }

}
