package com.dongzhic.primary.array;


import java.util.*;

/**
 *  两个数组的交集 II
 *      例如：给定 nums1 = [1, 2, 2, 1], nums2 = [2, 2], 返回 [2, 2].
 * @author dongzc
 * @date 2018/7/28 13:56
 */
public class Solution06 {



    public static void main(String[] args) {
        int[] nums1 = {1, 2, 2, 1};
        int[] nums2 = {2, 2};
        int[] result = intersect2(nums1, nums2);
        for (int i = 0 ;i < result.length; i ++) {
            System.out.println(result[i]);
        }
    }

    /**
     *  解法一：
     * 1.将数组1放入map中，并记录元素出现的次数
     * 2.遍历第二个数组2，将数组1中包含的数组2元素，并且出现的次数大于0的元素放入一个List中
     * 3.将list中的元素放入int数组中
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect1(int[] nums1, int[] nums2) {

        // 存放交集元素
        List<Integer> list = new ArrayList<>();

        // 1.将数组1放入map中，并记录元素出现的次数
        Map<Integer, Integer> map = new HashMap<>(16);
        for (int i = 0; i < nums1.length; i ++) {
            map.put(nums1[i], (map.get(nums1[i]) != null ? map.get(nums1[i]) : 0)+1);
        }
        // 2.遍历第二个数组2，将数组1中包含的数组2元素，并且出现的次数大于0的元素放入一个List中
        for (int i = 0; i < nums2.length; i ++) {
            if (map.containsKey(nums2[i]) && map.get(nums2[i]) > 0) {
                list.add(nums2[i]);
                map.put(nums2[i], map.get(nums2[i])-1);
            }
        }

        // 返回数组
        int[] resultArray = new int[list.size()];
        int i = 0;
        for (Integer e : list) {
            resultArray[i++] = e;
        }

        return resultArray;
    }

    /**
     * 解法二：
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public static int[] intersect2(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        List<Integer> list = new ArrayList<>();

        // 分别标记两个数组的起始位置
        int i = 0;
        int j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] > nums2[j]) {
                j++;
            } else if (nums1[i] < nums2[j]) {
                i++;
            } else {
                list.add(nums1[i]);
                i++;
                j++;
            }
        }

        int k = 0;
        int[] resultArray = new int[list.size()];
        for (Integer e : list) {
            resultArray[k++] = e;
        }

        return resultArray;
    }


}
