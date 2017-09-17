package com.kelan.core.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 
 * @author 张代浩
 *
 */
public class ApplicationContextUtil implements ApplicationContextAware {

	private static ApplicationContext context;

	@SuppressWarnings("static-access")
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	public static ApplicationContext getContext() {
		return context;
	}

	/** 功能：根据BEAN名称获取对象 **/
	public static Object getBean(String beanName) {
		return context.getBean(beanName);
	}
}
