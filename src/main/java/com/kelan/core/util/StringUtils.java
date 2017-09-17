package com.kelan.core.util;

/**
 * Created by WYX on 2017/3/26.
 */
public class StringUtils {

    /**
     * 判断字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return (str == null || "".equals(str.trim()));
    }

    /**
     * 获取名称后缀
     * @param name
     * @return
     */
    public static String getExt(String name){
        if(name == null || "".equals(name) || !name.contains("."))
            return "";
        return name.substring(name.lastIndexOf(".")+1);
    }
}
