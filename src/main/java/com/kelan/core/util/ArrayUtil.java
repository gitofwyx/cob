package com.kelan.core.util;


/**
 * 
 * @author Administrator
 */
public class ArrayUtil {
   /**
	 * 将字符串数组转为以,号逗号隔开的字符串
	 * @param strArr
	 * @return
	 */
	public static String getStringFormArr(String[] strArr){
		if(strArr==null){
			return "";
		}else{
			StringBuffer resultsb=new StringBuffer();
			for (String content : strArr) {
				if(!StringUtil.isNullorEmpty(content)){
					resultsb.append(content).append(",");
				}
			}
			if(resultsb.length()==0){
				return "";
			}else{
				return resultsb.toString().substring(0,resultsb.length());//去掉最一个逗号返回
			}
		}
	}
	
	/**
	 * 判断字符串是否存在办理者
	 * @param transactors 以逗号隔开的用户列表
	 * @return
	 */
	public static boolean existTransactor(String transactors){
		boolean result=false;
		String[] transactorArr=transactors.split(",");
		for (String transactor : transactorArr) {
			if(transactor!=null&&!"".equals(transactor)){
				result=true;
				break;
			}
		}
		return result;
	}
	
	
	/**
	 * 判断指定字符串是否在数组中存在
	 * @param target 指定字符串
	 * @param srcArr 源数组
	 * @return false默认不存在 true存在
	 */
	public static boolean existInArray(String target,String[] srcArr){
		boolean bool=false;
		for (String str : srcArr) {
			if(str!=null&&!"".equals(str)&&str.equals(target)){
				bool=true;
			}
		}
		return bool;
	} 
	
}
