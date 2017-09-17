package com.kelan.core.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Session提供者
 */
public interface SessionProvider {
	public Object getAttribute(HttpServletRequest request, String name);

	public void setAttribute(HttpServletRequest request, HttpServletResponse response, String name, Object value);

	public String getSessionId(HttpServletRequest request, HttpServletResponse response);

	public void logout(HttpServletRequest request, HttpServletResponse response);
}
