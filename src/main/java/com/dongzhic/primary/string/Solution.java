package com.dongzhic.primary.string;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dongzhic
 */
public class Solution {

    public static void main(String[] args) {

        int a = romanToInt("MCMXCIV");
        System.out.println(a);

    }

    /**
     * day002
     * @param num
     * @return
     */
    public String intToRoman(int num) {

        return null;
    }

    /**
     * day001
     * 罗马数组转中文
     *  七种字符: I=1，V=5，X=10，L=50，C=100，D=500 和 M=1000
     *  例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     *  通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，
     *      所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。
     *  这个特殊的规则只适用于以下六种情况：
     *      I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。
     *      X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。 
     *      C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     *  例如：LVIII=58(50+5+3)，MCMXCIV=1994(1000+900+90+4)
     * @param s
     * @return
     */
    public static int romanToInt(String s) {
        int sum = 0;

        int len = s.length();
        char[] chars = s.toCharArray();

        int i = 0;
        while (i < len) {
            int num = 0;
            char c1 = chars[i];
            switch (c1) {
                case 'I':
                    num = 1;
                    break;
                case 'V':
                    num = 5;
                    break;
                case 'X':
                    num = 10;
                    break;
                case 'L':
                    num = 50;
                    break;
                case 'C':
                    num = 100;
                    break;
                case 'D':
                    num = 500;
                    break;
                case 'M':
                    num = 1000;
                    break;
            }
            if (i < len -1) {
                char c2 = chars[i+1];
                if (c1 == 'I' && c2 == 'V' ) {
                    num = 4;
                    i++;
                } else if (c1 == 'I' && c2 == 'X' ) {
                    num = 9;
                    i++;
                } else if (c1 == 'X' && c2 == 'L' ) {
                    num = 40;
                    i++;
                } else if (c1 == 'X' && c2 == 'C' ) {
                    num = 90;
                    i++;
                } else if (c1 == 'C' && c2 == 'D' ) {
                    num = 400;
                    i++;
                } else if (c1 == 'C' && c2 == 'M' ) {
                    num = 900;
                    i++;
                }
            }
            sum = sum + num;
            i++;
        }
        return sum;
    }

    /**
     * 二进制相加 11 1
     * @param a
     * @param b
     * @return
     */
    public static String addBinary (String a, String b) {

        StringBuilder result = new StringBuilder();

        // 进位
        int carry = 0;
        int alen = a.length()-1;
        int blen = b.length()-1;

        while(alen >= 0 || blen >=0) {
            // sum%2：当前位，sum/2：进位
            int sum = carry;

            if (alen >= 0) {
                sum += a.charAt(alen) - '0';
                alen--;
            }

            if (blen >= 0) {
                sum += b.charAt(blen) - '0';
                blen--;
            }

            result.append(sum%2);
            carry = sum/2;

        }
        if (carry != 0) {
            result.append(1);
        }
        return result.reverse().toString();
    }

    private static String[] map = {
            ".-",
            "-...",
            "-.-.",
            "-..",
            ".",
            "..-.",
            "--.",
            "....",
            "..",
            ".---",
            "-.-",
            ".-..",
            "--",
            "-.",
            "---",
            ".--.",
            "--.-",
            ".-.",
            "...",
            "-",
            "..-",
            "...-",
            ".--",
            "-..-",
            "-.--",
            "--.."
    };

    public static int uniqueMorseRepresentations(String[] words) {


        if (words == null) return 0;
        HashSet<String> set = new HashSet<String>();
        for (String s : words) {
            StringBuilder sb = new StringBuilder();
            for (char c : s.toCharArray()) {
                int i = c - 'a';
                sb.append(map[c - 'a']);
            }
            set.add(sb.toString());
        }
        return set.size();

//        HashSet set = new HashSet();
//        for (int i = 0; i < words.length; i ++) {
//            String s = enCode(words[i]);
//            set.add(s);
//        }
//
//        return set.size();
    }

    public static String enCode (String word) {

        StringBuilder result = new StringBuilder();

        final String[] a = new String[]{".-","-...","-.-.","-..",".",
                "..-.","--.","....","..",".---","-.-",
                ".-..","--","-.","---",".--.","--.-",".-.","...",
                "-","..-","...-",".--","-..-","-.--","--.."};
        final String[] b = new String[]{"a", "b", "c", "d", "e", "f", "g",
                "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
                "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        HashMap<String, String> map = new HashMap<>(16);
        for (int i = 0; i < 26; i ++) {
            map.put(b[i], a[i]);
        }

        char[] chars = word.toCharArray();
        for (int i = 0; i < chars.length; i ++) {
            char c = chars[i];
            result.append(map.get(String.valueOf(c)));
        }

        return result.toString();
    }





    public static int numUniqueEmails(String[] emails) {
        Set<String> hashSet = new HashSet<String>();
        for (int i = 0; i < emails.length; i++) {
            StringBuilder s = new StringBuilder();
            String[] str = emails[i].split("@");
            if (str.length == 2) {
                String localName = str[0];
                String domainName = str[1];
                String[] addStr = localName.split("\\+");
                s.append(addStr[0].replaceAll("\\.", ""));
                s.append("@");
                s.append(domainName);
                hashSet.add(s.toString());
            }
        }
        return hashSet.size();
    }

    public static String toLowerCase(String str) {
        return str.toLowerCase();
    }


}
