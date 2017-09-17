package com.kelan.core.util;

import org.apache.log4j.Logger;

/**
 *
 * Title: 处理记录日志用于静态方法使用
 *
 * Description: 处理记录日志用于静态方法使用
 *
 */
public class LoggerUtil {

    private static Logger logger = Logger.getLogger(LoggerUtil.class); // 作为日志的公用对象

    /**
     * 获取公用日志对象
     *
     * @return
     */
    public static Logger getLoggerInstance() {
        return logger;
    }
}
