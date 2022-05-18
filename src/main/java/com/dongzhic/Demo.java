package com.dongzhic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author dongzhic
 * @Date 2021/11/27 18:39
 */
public class Demo {

    public static void main(String[] args) {

        List<String> list = new ArrayList<>();
        list.add("123abccd");
        list.add("abcdc123");
        list.add("1234abc");
        list.add("ab1234cd");

        deleteRepeat(list);
    }

    /**
     * 集合去重
     * @param list
     * @return
     */
    public static List<String> deleteRepeat (List<String> list) {

        List<String> resultList = new ArrayList<>();

        // 过滤重复元素，只存储数字
        Set<String> set = new HashSet<>();
        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if (set.add(getIntegerForString(s))) {
                resultList.add(s);
            }
        }
        
        return resultList;
    }

    /**
     * 获取字符串中的数字
     * @param s
     * @return
     */
    public static String getIntegerForString (String s) {
        if (s == null || "".equals(s)) {
            return s;
        }

        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(s);
        return m.replaceAll("").trim();
    }
}
