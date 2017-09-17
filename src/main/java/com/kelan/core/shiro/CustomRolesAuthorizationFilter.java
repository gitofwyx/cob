package com.kelan.core.shiro;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

public class CustomRolesAuthorizationFilter extends RolesAuthorizationFilter {

	private static Logger log = Logger.getLogger(CustomRolesAuthorizationFilter.class);

	public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws IOException {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		log.info("授权->请求路径：" + httprequest.getRequestURI());
		Subject subject = getSubject(request, response);
		String rolesArray[] = (String[]) (String[]) mappedValue;
		Set roles = CollectionUtils.asSet(rolesArray);
		boolean hasAllRoles = false;
		try {
			if (rolesArray == null || rolesArray.length == 0) {
				return true;
			} else {
				String roleBys = null;
				for (Object role : roles) {
					if (roleBys != null && !"".equals(roleBys)) {
						hasAllRoles=roleBys.equals(role);
						continue;
					}
					hasAllRoles = subject.hasRole((String) role);
				}
				if (hasAllRoles == false) {
					httprequest.setAttribute("msg", "没有下限");
					log.info("没有请求权限");
				}
			}
		} catch (Exception e) {
			System.out.println(e);
			log.error("验证出现异常！");
			log.warn("验证未通过");
		}
		return hasAllRoles;
	}

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		log.warn("授权未通过>>>");
		Subject subject = getSubject(request, response);
		if (subject.getPrincipal() == null) {// 表示没有登录，重定向到登录页面
			String loginUrl = getLoginUrl();
			log.warn(">>>用户未登录");
			saveRequest(request);
			WebUtils.issueRedirect(request, response, loginUrl);
		} else {
			String unauthorizedUrl = getUnauthorizedUrl();
			if (StringUtils.hasText(unauthorizedUrl)) {// 如果有未授权页面跳转过去
				WebUtils.issueRedirect(request, response, unauthorizedUrl);
			} else {// 否则返回401未授权状态码
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		}
		return false;
	}
}
