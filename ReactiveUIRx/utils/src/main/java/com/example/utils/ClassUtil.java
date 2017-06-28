package com.example.utils;

/**
 * Created by ngoclinh.truong on 6/14/16.
 */
public class ClassUtil {

    public static String getName(Object obj) {
        String name = obj.getClass().getName();
        int dotPos = name.lastIndexOf(".");
        if (dotPos != -1 && dotPos < name.length() - 1) {
            return name.substring(dotPos + 1);
        } else {
            return name;
        }
    }

    private ClassUtil() {}
}
