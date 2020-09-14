package com.dongzhic.primary.array;

/**
 * 买卖股票的最佳时机 II
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 * 设计一个算法来计算你所能获取的最大利润。你可以尽可能地完成更多的交易（多次买卖一支股票）。
 * @author dongzc
 * @date 2018/7/3 18:10
 */
public class Solution02 {

    public static void main(String[] args) {
        //7
        int[] a = {7,1,5,3,6,4};
        //4
        int[] b = {1,2,3,4,5};
        //0
        int[] c = {7,6,4,3,1};
        System.out.println("最大利润为："+maxProfit1(b));
    }

    public static int maxProfit1 (int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        //记录最大利润
        int sum = 0;
        for (int i=0; i<prices.length-1; i++) {
            if (prices[i] < prices[i+1]) {
                sum += prices[i+1] - prices[i];
            }
        }
        return sum;
    }

    public int maxProfit(int[] A) {
        // write your code here
        if(A == null || A.length == 0)
            return 0;
        if(A.length == 1)
            return 0;
        int sum=0;
        int i = 0;
        int j = 0;
        while(i < A.length && j < A.length){
            while(j<A.length-1 && A[j] <= A[j+1]) {
                j++;
            }
            // 跳出循环的j 是满足递增序列的最后一个位置
            // 最后一个元素，结束了
            if(j == A.length -1){
                sum += A[j] - A[i];
                break;
            }else{
                sum += A[j] - A[i];
            }
            // 下一个位置从新开始
            i = j + 1;
            j = j + 1;
        }
        return sum;
    }
}
