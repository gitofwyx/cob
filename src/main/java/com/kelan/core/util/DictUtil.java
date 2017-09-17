package com.kelan.core.util;


/**
 *Title: 数据字典共通
 * 
 */
public class DictUtil {
	
	/**
	 * 根据性别ID获取获取性别名称
	 * 
	 * @param genderId 性别ID
	 * @return 性别名称
	 */
    public static String getGender(String genderId) {
    	String genderNm = "";
        if ("1".equals(genderId)) {
        	genderNm = "男";
        } else if ("2".equals(genderId)) {
        	genderNm = "女";
        }
        return genderNm;
    }

    /**
	 * 根据车主性质ID获取获取车主性质名称
	 * 
	 * @param customerTypeId 车主性质ID
	 * @return 车主性质名称
	 */
    public static String getCustomerType(String customerTypeId) {
    	customerTypeId = (customerTypeId == null) ? "" : customerTypeId.trim();
    	String customerTypeNm = "";
        if ("1".equals(customerTypeId)) {
        	customerTypeNm = "私人";
        } else if ("2".equals(customerTypeId)) {
        	customerTypeNm = "企业";
        } else if ("3".equals(customerTypeId)) {
        	customerTypeNm = "政府";
        } else if ("4".equals(customerTypeId)) {
        	customerTypeNm = "其他";
        }
        return customerTypeNm;
    }
    
    /**
     * 根据工单ID状态获取工单状态名称
     * @param repairStatusId 工单状态
     * @return
     */
    public static String getRepairStatus(String repairStatusId) {
    	String repairStatusNm = "";
        if ("1".equals(repairStatusId)) {
        	repairStatusNm = "未派工";
        } else if ("2".equals(repairStatusId)) {
        	repairStatusNm = "部分派工";
        } else if ("3".equals(repairStatusId)) {
        	repairStatusNm = "已派工（在修）";
        } else if ("4".equals(repairStatusId)) {
        	repairStatusNm = "已派工（待料）";
        } else if ("5".equals(repairStatusId)) {
        	repairStatusNm = "已派工（等待客户答复）";
        } else if ("6".equals(repairStatusId)) {
        	repairStatusNm = "已完工";
        } else if ("7".equals(repairStatusId)) {
        	repairStatusNm = "已结算";
        } else if ("8".equals(repairStatusId)) {
        	repairStatusNm = "已付款";
        } else if ("9".equals(repairStatusId)) {
        	repairStatusNm = "已交车";
        }
        return repairStatusNm;
    }
    
}
