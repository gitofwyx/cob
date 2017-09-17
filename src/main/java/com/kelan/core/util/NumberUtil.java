package com.kelan.core.util;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Stack;

/**
 *Title: 数值精确运算
 * 
 *Description: 提供精确的加减乘除运算
 * 
 */
public class NumberUtil {

    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;
    static private NumberUtil nuberUtil = null;

    private NumberUtil() {
    }

    synchronized static public NumberUtil getInstance() {
        if (nuberUtil == null) {
            nuberUtil = new NumberUtil();
        }
        return nuberUtil;
    }

    /**
     * 提供精确的加法运算。
     *
     *
     * @param v1 被加数
     *
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的加法运算。
     *
     *
     * @param v1 被加数
     *
     * @param v2 加数
     * @return 两个参数的和
     */
    public static BigDecimal add(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.add(b2);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * 提供精确的减法运算。
     *
     *
     * @param v1 被加数
     *
     * @param v2 加数
     * @return 两个参数的差
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     *
     *
     * @param v1 被加数
     *
     * @param v2 加数
     * @return 两个参数的差
     */
    public static BigDecimal sub(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.subtract(b2);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * 提供精确的乘法运算。
     *
     *
     * @param v1 被加数
     *
     * @param v2 加数
     * @return 两个参数的乘
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }
    
    /**
     * 提供精确的乘法运算并四舍五入保留小数点后N位
     * @param v1
     * @param v2
     * @param scale
     * @return
     */
    public static double mul(double v1, double v2, int scale) {
    	if (scale < 0) {
    		scale = 2;
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     *
     * @param v1 被加数
     *
     * @param v2 加数
     * @return 两个参数的乘
     */
    public static BigDecimal mul(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * add by tbb 提供精确的乘法运算。
     *
     *
     * @param v1 被乘数
     *
     * @param v2 乘数
     * @return 两个参数的乘
     */
    public static BigDecimal mul(String v1, int v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.multiply(b2);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     *
     * @param v1 被除数
     *
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     *
     * @param v1 被除数
     *
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * Add by tbb 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
     *
     *
     * @param v1 被除数
     *
     * @param v2 除数
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, int v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     *
     * @param v1 被除数
     *
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     *
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, String v2, int scale) {

        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * add by tbb 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     *
     * @param v1 被除数
     *
     * @param v2 除数
     * @param scale 表示需要精确到小数点以后几位。
     *
     * @return 两个参数的商
     */
    public static BigDecimal div(String v1, int v2, int scale) {

        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     *
     * @param v1 被除数
     *
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     *
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     *
     * @param v 需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static BigDecimal round(String v, int scale) {
    	if (v == null || v.isEmpty()) {
    		return new BigDecimal("0");
    	}
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(v);
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * null转变为空字串
     *
     * @param strValue 字串值
     *
     * @return 字串
     */
    public static String toString(String strValue) {
        if (strValue == null) {
            return "";
        } else {
            return strValue;
        }
    }

    /**
     * 字符转换成double
     *
     * @param str 字符串值
     *
     * @return double值
     *
     */
    public static double strToDouble(String str) {
//        String loghead = new String("TypeConver:str2float:");
        double ret = 0.00;
        try {
            boolean flag = (null == str);
            flag = flag || (str.trim().length() < 1);
            if (flag) {
                ret = 0.00;
            } else {
                ret = Double.parseDouble(str);
            }
        } catch (NumberFormatException e) {
            ret = 0.00;
        }
        return ret;
    }

    /**
     * 字符转换成Long
     *
     * @param str 字符串值
     *
     * @return long值
     *
     */
    public static long strToLong(String str) {
//        String loghead = new String("TypeConver:str2float:");
        long ret = 0l;
        try {
            boolean flag = (null == str);
            flag = flag || (str.trim().length() < 1);
            if (flag) {
                ret = 0l;
            } else {
                ret = Long.parseLong(str);
            }
        } catch (NumberFormatException e) {
            ret = 0l;
        }
        return ret;
    }

    /**
     * 字符转换成Int
     *
     * @param str 字符串值
     *
     * @return Int值
     *
     */
    public static int strToInt(String str) {
        int ret = 0;
        try {
            boolean flag = (null == str);
            flag = flag || (str.trim().length() < 1);
            if (flag) {
                ret = 0;
            } else {
                ret = Integer.parseInt(str);
            }
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    /**
     * 字符转换成Int
     *
     * @param str 字符串值
     *
     * @return Int值
     *
     */
    public static float strToFloat(String str) {
//        String loghead = new String("TypeConver:str2float:");
        float ret = 0;
        try {
            boolean flag = (null == str);
            flag = flag || (str.trim().length() < 1);
            if (flag) {
                ret = 0;
            } else {
                ret = Float.parseFloat(str);
            }
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }

    /**
     * 字串型转换成BigDecimal 型
     *
     *
     * @param str str的值
     *
     * @return BigDecimal 型
     *
     */
    public static BigDecimal strToBigDecimal(String str) {
        try {
            BigDecimal bd = new BigDecimal(str);
            return bd;
        } catch (NumberFormatException e) {
            return new BigDecimal("0");
        }
    }

    /**
     * BigDecimal转换成字符串型
     *
     *
     * @param bdNum BigDecimal
     * @return 字串类型
     */
    public static String BigDecimalToStr(BigDecimal bdNum) {
        return bdNum.toString();
    }

    /**
     * 格式化double值,
     *
     * @param dblValue double值
     *
     * @param strFormat 格式串("00000.000")
     * @return 返回格式串
     *
     */
    public static String format(double dblValue, String strFormat) {
        java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat);
        return df.format(dblValue);

    }

    /**
     * 格式化String值,
     *
     * @param dblValue double值
     *
     * @param strFormat 格式串("00000.000")
     * @return 返回格式串
     *
     */
    public static String format(String strValue, String strFormat) {
        java.text.DecimalFormat df = new java.text.DecimalFormat(strFormat);
        return df.format(strToDouble(strValue));
    }

    public static String toFinish(String strValue) {
        return toFinish(strValue, true);
    }

    /**
     * 判断是否为数字
     *
     *
     * @param strValue 字串
     * @return 真或假
     *
     */
    public static boolean isNumeric(String strValue) {
        boolean bRet = false;
        try {
            Double.parseDouble(strValue);
            bRet = true;
        } catch (NumberFormatException e) {
            bRet = false;
        }
        return bRet;
    }

    /**
     * 得到最精确的小数位数
     *
     *
     * @param strValue 字串
     * @param isMoney 是否金额型
     *
     * @return 精算后的字串
     */
    public static String toFinish(String strValue, boolean isMoney) {
        int iPos = 0;
        int iLens = 0;
        int iScale = 0;
        String strInt = new String("");
        String strScale = new String("");
        iPos = strValue.indexOf(".");
        // 容错处理
        if (isNumeric(strValue) == false) {
            return strValue;
        }

        if (iPos < 0) {
            if (isMoney == true) {
                strValue += ".00";
            }

            return strValue;
        }
        strInt = strValue.substring(0, iPos);
        strScale = strValue.substring(iPos + 1);
        iScale = strToInt(strScale);
        if (iScale == 0) { // 小数位数为0,其实这段代码只是容错处理
            if (isMoney == true) {
                strInt += ".00";
            } else {
                return strInt;
            }
            return strInt;
        }

        iLens = strScale.length() - 1;
        for (int idx = iLens; idx >= 0; idx--) {
            if (strScale.charAt(idx) != '0') {
                iLens = idx + 1;
                break;
            }
        }
        if (isMoney == true && iLens < 2) {
            strScale = strScale.substring(0, iLens);
            for (int idx = 0; idx < 2 - iLens; idx++) {
                strScale += "0";
            }
        } else {
            strScale = strScale.substring(0, iLens);
        }
        return strInt + "." + strScale;
    }

    /**
     * 判断是否为操作符
     *
     * @param operator 操作符
     *
     * @return true为真 ,false为假
     */
    private static boolean isOperator(char operator) {
        if (operator == '-' || operator == '+' || operator == '*'
                || operator == '/' || operator == '%') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 得到运算的优先级
     *
     * @param operator 操作符(+,-,*,/)
     * @return 优先级数
     */
    private static int priority(char operator) {
        int iPriority = 0;
        switch (operator) {
            case '-':
            case '+':
                iPriority = 1;
                break;
            case '/':
            case '*':
            case '%':
                iPriority = 2;
                break;
            default:
                iPriority = 0;
        }
        return iPriority;
    }

    /**
     * 检查表达式是否正确
     *
     * @param strExpression 表达式
     *
     * @return true为真 ,false为假
     */
    private static boolean checkExpression(String strExpression) {
        int rightCount = 0;
        int leftCount = 0;
        @SuppressWarnings("unused")
        int pos = -1;
        int iChar = 0;
        char c;
        @SuppressWarnings("unused")
        char preChar = '0';
        for (int idx = 0; idx < strExpression.length(); idx++) {
            c = strExpression.charAt(idx);
            iChar = (int) strExpression.charAt(idx);
            if (c == ')') {
                ++rightCount;
            } else if (c == '(') {
                ++leftCount;
            } else {
                if (!(isOperator(c) || (iChar >= 48 && iChar <= 57) || (c == '.'))) {
                    return false;
                }
                preChar = c;
            }
        }
        if (rightCount != leftCount) {
            return false;
        }

        return true;
    }

    /**
     * 处理计算
     *
     * @param operator 操作符
     *
     * @param sOpertand1 操作数1
     * @param sOpertand2 操作数2
     * @return
     */
    private static String toResult(char operator, String sOpertand1,
            String sOpertand2) {
        String strValue = new String("");
        switch (operator) {
            case '-':
                strValue = sub(sOpertand1, sOpertand2).toString();
                break;
            case '+':
                strValue = add(sOpertand1, sOpertand2).toString();
                break;
            case '*':
                strValue = mul(sOpertand1, sOpertand2).toString();
                break;
            case '/': // 除

                strValue = div(sOpertand1, sOpertand2).toString();
                break;
            case '%': // 求余操作
                double dblOpertand1,
                 dblOpertand2;
                dblOpertand1 = strToDouble(sOpertand1);
                dblOpertand2 = strToDouble(sOpertand2);
                if (dblOpertand2 == 0.0) {
                    return "0";
                }
                dblOpertand1 = dblOpertand1 % dblOpertand2;
                strValue = format(dblOpertand1, "0");
                break;
            default:
                strValue = "";

        }
        return strValue;
    }

    /**
     * 找到最匹配的括号的:因为括号可以无限制的嵌套
     *
     * @param strExpression 表达式
     *
     * @return 右边括号的光标位置
     *
     */
    private static int findRightPos(String strExpression) {
        int rightCount = 0;
        int leftCount = 1;
        int pos = -1;
        for (int idx = 0; idx < strExpression.length(); idx++) {
            if (strExpression.charAt(idx) == ')') {
                ++rightCount;
            } else if (strExpression.charAt(idx) == '(') {
                ++leftCount;
            }
            if (leftCount == rightCount) {
                pos = idx;
                break;
            }
        }
        return pos;
    }

    /**
     * 计算表达式(允许加减乘除,可以嵌套括号) 该方法主要采用堆栈及递归算法来实现 注:主要你在正规的语句输入的正确,就一定得到正确的结果
     *
     * @param strExpression 字符串表达式
     * @return 计算出来的结果
     *
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static String procSum(String strExpression) {
        Stack operator = new Stack();
        Stack operation = new Stack();
        int pos = 0;
        int rightPos = 0;
        char curOper;
        String strValue = "";
        String strOpertand1 = "";
        String strOpertand2 = "";
        boolean bNegative = false;
        try {
            while (true) {
                bNegative = false;
                // 处理负数
                if (strExpression.charAt(pos) == '-') {
                    if (pos > 0) {
                        if (isOperator(strExpression.charAt(pos - 1))) {
                            if (strExpression.charAt(pos - 1) == '-') {
                                throw new RuntimeException("");
                            }
                            bNegative = true;
                        }
                    } else {
                        bNegative = true;
                    }
                }
                // 运算符处理

                if (isOperator(strExpression.charAt(pos)) && bNegative == false) {
                    operation.push(strValue); // 将操作符压入栈

                    strValue = "";
                    if (!operator.isEmpty()) {
                        if (priority(strExpression.charAt(pos)) <= priority(((String) operator
                                .lastElement()).charAt(0))) {
                            strOpertand1 = (String) operation.pop();
                            strOpertand2 = (String) operation.pop();
                            curOper = ((String) operator.pop()).charAt(0);
                            operation.push(toResult(curOper, strOpertand2,
                                    strOpertand1));

                            if (!operator.isEmpty()
                                    && priority(strExpression.charAt(pos)) == 1) {
                                strOpertand1 = (String) operation.pop();
                                strOpertand2 = (String) operation.pop();
                                curOper = ((String) operator.pop()).charAt(0);
                                operation.push(toResult(curOper, strOpertand2,
                                        strOpertand1));
                            }
                        }
                        operator.push(strExpression.substring(pos, pos + 1)); // 将操作符压入栈

                    } else {
                        operator.push(strExpression.substring(pos, pos + 1)); // 将操作符压入栈

                    }
                } else if (strExpression.charAt(pos) == '(') {
                    rightPos = findRightPos(strExpression.substring(pos + 1)); // 找最匹配的右边托号

                    if ((rightPos + pos - 1) <= pos || rightPos == -1) { // 容错处理
                        ++pos;
                        continue;
                    }
                    // 递归调用本方法,计算括号内的表达式

                    strValue = procSum(strExpression.substring(pos + 1, pos
                            + rightPos + 1));
                    pos += rightPos + 2;
                    if (pos >= strExpression.length()) {
                        operation.push(strValue); // 将括号类计算的值压入堆栈

                        break;
                    }
                    continue;

                } else {
                    // 处理非法字符
                    if (strExpression.charAt(pos) != ')'
                            && (!strExpression.substring(pos, pos + 1).equals(
                                    " "))) {
                        strValue += strExpression.substring(pos, pos + 1);
                    }
                }
                ++pos;
                if (pos >= strExpression.length()) {
                    operation.push(strValue); // 将操作符压入栈

                    break;
                }
            }
            // 处理堆栈里最终的数据!!
            while (!operator.isEmpty()) {
                strOpertand1 = (String) operation.pop();
                strOpertand2 = (String) operation.pop();
                curOper = ((String) operator.pop()).charAt(0);
                operation.push(toResult(curOper, strOpertand2, strOpertand1));
            }
        } catch (Exception e) {
            throw new RuntimeException("表达式有误");
        }

        return (String) operation.pop();
    }

    /**
     * 先检查是否正确,如果不正确将抛异常
     *
     *
     * @param strExpression 表达式
     *
     * @return 处理的结果
     *
     */
    public static String sum(String strExpression) {
        if (checkExpression(strExpression)) {
            return procSum(strExpression);
        } else {
            throw new RuntimeException("表达式有误");
        }
    }

    /**
     * 用于生成特种业务编码 产生六位的随机数
     *
     * @return
     */
    public static String getspecialtypeNum() {
        int result = 0;
        Random r = new Random();
        while (true) {
            int x = r.nextInt(999999);
            if (x > 99999) {
                result = x;
                break;
            } else {
                continue;
            }
        }
        return String.valueOf(result);
    }
    
    /**
     * 校验浮点数的合法性
     * @param strValue 待校验字符串数字、浮点数
     * @param isRequired 是否可以为空。true：不可以为空， false：可以为空
     * @return
     */
    public static boolean isFloatNumber(String strValue, boolean isRequired) {
        if (strValue == null || strValue.isEmpty()) {
    		if (isRequired) {
    			return false;
    		} else {
    			return true;
    		}
    	}
        
        boolean bRet = false;
        try {
            Double.parseDouble(strValue);
            bRet = true;
        } catch (NumberFormatException e) {
            bRet = false;
        }
        return bRet;
    }
    
    /**
     * 校验整数的合法性
     * @param strValue 待校验字符串整数
     * @param isRequired 是否可以为空。true：不可以为空， false：可以为空
     * @return
     */
    public static boolean isInt(String strValue, boolean isRequired) {
        if (strValue == null || strValue.isEmpty()) {
    		if (isRequired) {
    			return false;
    		} else {
    			return true;
    		}
    	}
        
        boolean bRet = false;
        try {
            Integer.parseInt(strValue);
            bRet = true;
        } catch (NumberFormatException e) {
            bRet = false;
        }
        return bRet;
    }
    
    /**
     * 校验长整形数的合法性
     * @param strValue 待校验字符串整数
     * @param isRequired 是否可以为空。true：不可以为空， false：可以为空
     * @return
     */
    public static boolean isLong(String strValue, boolean isRequired) {
        if (strValue == null || strValue.isEmpty()) {
    		if (isRequired) {
    			return false;
    		} else {
    			return true;
    		}
    	}
        
        boolean bRet = false;
        try {
            Long.parseLong(strValue);
            bRet = true;
        } catch (NumberFormatException e) {
            bRet = false;
        }
        return bRet;
    }
    
}
