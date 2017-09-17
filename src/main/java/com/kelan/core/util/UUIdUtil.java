package com.kelan.core.util;

import com.kelan.core.constant.RegularString;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import java.util.List;
import java.util.UUID;

public class UUIdUtil {
	
	/**
	 * 获取一个UUId
	 * @return
	 */
	public static String getUUID() {
        return UUID.randomUUID().toString().toLowerCase();
    } 
	
	/**
	 * 获取一个去掉中划线的UUId
	 * @return
	 */
	public static String getNotMiddleLineUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }
	
	/**
	 * 校验带有中划线的UUID的合法性
	 * @param uuId
	 * @return
	 */
	public static boolean matchUUID(String uuId) {
		try {
			RE regularExam = new RE(RegularString.MIDDLELINEUUID);
            return regularExam.match(uuId);
        } catch (RESyntaxException e) {
            return false;
        }
	}
	
	/**
	 * 可否为空及校验带有中划线的UUID的合法性
	 * @param uuId
	 * @param isRequired 是否可以为空。true：不可以为空， false：可以为空
	 * @return
	 */
	public static boolean matchUUIDOrBlank(String uuId, Boolean isRequired) {
		try {
			if (uuId == null || uuId.length() == 0) {
	    		if (isRequired) {
	    			return false;
	    		} else {
	    			return true;
	    		}
	    	}
			
			RE regularExam = new RE(RegularString.MIDDLELINEUUID);
            return regularExam.match(uuId);
        } catch (RESyntaxException e) {
            return false;
        }
	}
	
	/**
	 * 批量校验可否为空及校验带有中划线的UUID的合法性
	 * @param uuIds UUID数组
	 * @param isRequired 是否可以为空。true：不可以为空， false：可以为空
	 * @return
	 */
	public static boolean matchUUIds(List<String> uuIds, Boolean isRequired) {
		try {
			RE regularExam = null;
			for (String uuId : uuIds) {
				if (uuId == null || uuId.length() == 0) {
		    		if (isRequired) {
		    			return false;
		    		} else {
		    			return true;
		    		}
		    	}
				
				regularExam = new RE(RegularString.MIDDLELINEUUID);
				if (!regularExam.match(uuId)) {
					return false;
				}
			}
			
			return true;
        } catch (RESyntaxException e) {
            return false;
        }
	}
	
	/**
	 * 校验去掉中划线的UUID的合法性
	 * @param uuId
	 * @return
	 */
	public static boolean matchNotMiddleLineUUID(String uuId) {
		try {
			RE regularExam = new RE(RegularString.UUID);
            return regularExam.match(uuId);
        } catch (RESyntaxException e) {
            return false;
        }
	}

}
