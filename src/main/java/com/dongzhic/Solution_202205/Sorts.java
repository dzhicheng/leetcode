package com.dongzhic.Solution_202205;

import org.junit.Test;

import java.util.Random;

/**
 * @Author dongzhic
 * @Date 2022/5/21 15:22
 */
public class Sorts {

    @Test
    public void test () {
        int [] nums = {5,1,1,2,0,0};
        quickSort(nums);
    }

    public int [] quickSort (int [] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    public void quickSort (int [] nums, int left, int right) {
        if (left < right) {
            int pivotIndex = partition(nums, left, right);
            quickSort(nums, left, pivotIndex - 1);
            quickSort(nums, pivotIndex + 1, right);
        }
    }

    public int partition (int [] nums, int left, int right) {

        // 基准数位置(随机，提升稳定性)
        int pivotIndex = new Random().nextInt(right - left + 1) + left;
        swap(nums, pivotIndex, right);

        int pivot = nums[right];
        int index = left;
        for (int i = left; i < right; i++) {
            if (nums[i] < pivot) {
                swap(nums, left, index++);
            }
        }

        pivotIndex = index;
        swap(nums, index, right);

        return pivotIndex;
    }
    public void swap (int [] nums, int a, int b) {
        int num = nums[a];
        nums[a] = nums[b];
        nums[b] = num;
    }


}
