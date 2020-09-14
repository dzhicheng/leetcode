package com.dongzhic.medium.orther;

/**
 * @Author dongzhic
 * @Date 2019-06-26 09:19
 */
public class Solution {


    public static void main(String[] args) {

        for (int i = 1; i < 11; i ++) {
            System.out.println(intToRoman(i));
        }
    }


    /**
     * 整数转罗马数字
     * @param num
     * @return
     */
    public static String intToRoman(int num) {

        StringBuilder result = new StringBuilder();

        if (num >= 1 && num < 5) {
            if (num == 4) {
                result.append("IV");
            } else {
                int amount = num / 1;
                for (int i = 0; i < amount; i ++) {
                    result.append("I");
                }
            }
        } else if (num >= 5 && num < 10) {
            if (num == 9) {
                result.append("IX");
            } else {
                int amount = num / 5;
                int remainder = num % 5;
                for (int i = 0; i < amount; i ++) {
                    result.append("V");
                }
                for (int i = 0; i < remainder; i ++) {
                    result.append("I");
                }
            }
        } else if (num >= 10 && num < 50) {
            if (num == 40) {
                result.append("XL");
            } else {

            }
        } else if (num >= 50 && num < 100) {
            if (num == 90) {
                result.append("XC");
            } else {

            }
        } else if (num >= 100 && num < 500) {
            if (num == 400) {
                result.append("CD");
            } else {

            }
        } else if (num >= 1000 && num < 3999) {
            if (num == 900) {
                result.append("CM");
            } else {

            }
        } else {
            result.append("请输入符合要求的数字");
        }

        return result.toString();
    }
}
