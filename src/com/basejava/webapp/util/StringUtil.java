package com.basejava.webapp.util;

public class StringUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
