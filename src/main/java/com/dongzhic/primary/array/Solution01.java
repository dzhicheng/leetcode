package com.dongzhic.primary.array;

/**
 * 买卖股票的最佳时机 I
 * 假设有一个数组，它的第i个元素是一支给定的股票在第i天的价格。
 * 如果你最多只允许完成一次交易(例如,一次买卖股票),设计一个算法来找出最大利润。
 * @author dongzc
 * @date 2018/7/3 18:29
 */
public class Solution01 {

    public static void main(String[] args) {
        int[] prices = {3,2,3,1,2};
        System.out.println("最大利润为："+maxProfit1(prices));
    }

    /**
     * 类似冒泡排序循环遍历获取最大差，时间复杂度：O(N2)
     * @param prices
     * @return
     */
    public static int maxProfit1 (int[] prices) {
        int max = 0;
        if (prices == null || prices.length == 0) {
            return 0;
        }
        for (int i=0; i<prices.length; i++){
            for (int j=i; j<prices.length; j++) {
                max = Math.max(max, prices[j] - prices[i]);
            }
        }
        return max;
    }

    public static int maxProfit2 (int[] prices) {
        //最大收益，默认为0
        int result = 0;
        if (prices == null || prices.length == 0) {
            return 0;
        }
        //默认最小购买为数组第一个元素
        int minBuy = prices[0];
        for (int i=1; i<prices.length; i++) {
            result = Math.max(result, prices[i] - minBuy);
            minBuy = Math.min(minBuy, prices[i]);
        }
        return result;
    }
}
