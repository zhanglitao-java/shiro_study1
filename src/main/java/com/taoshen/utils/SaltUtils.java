package com.taoshen.utils;

import java.util.Random;

public class SaltUtils {
    public static String getSalt(int n) {
        //首先把所有的字符都搞起来
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*()".toCharArray();
        //用于拼接字符串
        StringBuilder sb = new StringBuilder();
        //设置形参n 需要多少位的随机字符串就多少n
        for (int i = 0; i < n; i++) {
            //nextInt函数含头不含尾 随机下标int从0开始到chars.length-1
            char aChar = chars[new Random().nextInt(chars.length)];
            //将每一次随机到的字符给拼接成StringBuilder
            sb.append(aChar);
        }
        //将StringBuilder转换成String普通字符串
        return sb.toString();
    }
}
