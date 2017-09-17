package com.kelan.core.util.tree;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by WYX on 2017/3/20.
 */
public class PageDeal {

    public static Map<String,Integer> requestPage(String page){
        Map<String,Integer> result=new HashMap<>();
        String[] pageNum=page.split("/");
        if(isNumeric(pageNum[0])){
            result.put("pageStart",Integer.parseInt(pageNum[0]));
        }else {
            return null;
        }
        if(isNumeric(pageNum[1])){
            result.put("pageSize",Integer.parseInt(pageNum[1]));
        }else {
            return null;
        }
        return result;
    }

    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

}
