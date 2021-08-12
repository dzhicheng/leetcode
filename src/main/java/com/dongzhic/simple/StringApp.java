package com.dongzhic.simple;

import java.util.*;

/**
 * 字符串
 * @Author dongzhic
 * @Date 2020-09-14 15:54
 */
public class StringApp {


    /**
     * 20. 有效的括号
     *  给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     *  有效字符串需满足：
     *      左括号必须用相同类型的右括号闭合。
     *      左括号必须以正确的顺序闭合。
     *      注意空字符串可被认为是有效字符串。
     *      示例 1:
     *          输入: "()"
     *          输出: true
     *      示例 2:
     *          输入: "(]"
     *          输出: false
     *      示例 3:
     *          输入: "([)]"
     *          输出: false
     *      示例 4:
     *          输入: "{[]}"
     *          输出: true
     * @param s
     * @return
     */
    public boolean isValid(String s) {

        // 1.判空
        if (null == s || "".equals(s)) {
            return false;
        }

        // 2.判断位数，必须是偶数
        if (s.length() % 2 == 1) {
            return false;
        }

        Map<Character, Character> pairs = new HashMap<Character, Character>(){{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        Deque<Character> stack = new LinkedList<Character>();
        for (int i = 0; i < s.length(); i ++) {
            char c = s.charAt(i);
            if (pairs.containsKey(c)) {
                if (stack.isEmpty() || stack.peek() != pairs.get(c)) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();

    }



    /**
     * 14. 最长公共前缀
     *    编写一个函数来查找字符串数组中的最长公共前缀。
     *    如果不存在公共前缀，返回空字符串 ""。
     *   示例 1:
     *    输入: ["flower","flow","flight"]
     *    输出: "fl"
     *   示例 2:
     *    输入: ["dog","racecar","car"]
     *    输出: ""
     *    解释: 输入不存在公共前缀。
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {


        return "";
    }

}
