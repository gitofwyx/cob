package com.kelan.core.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

	private static Logger log = Logger.getLogger(CustomFormAuthenticationFilter.class);

	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httprequest = (HttpServletRequest) request;
		log.warn("用户没登录->请求路径：" + httprequest.getRequestURI());
		if (isLoginRequest(request, response)) {
			if (isLoginSubmission(request, response)) {
				if (log.isTraceEnabled())
					log.trace("Login submission detected.  Attempting to execute login.");
				return executeLogin(request, response);
			}
			if (log.isTraceEnabled())
				log.trace("Login page view.");
			return true;
		}
		if (log.isTraceEnabled())
			log.trace((new StringBuilder())
					.append("Attempting to access a path which requires authentication.  Forwarding to the Authentication url [")
					.append(getLoginUrl()).append("]").toString());
		saveRequestAndRedirectToLogin(request, response);
		return false;
	}
	
	protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
	        throws Exception
	    {
			log.info("test");
	        issueSuccessRedirect(request, response);
	        return false;
	    }
	
}
