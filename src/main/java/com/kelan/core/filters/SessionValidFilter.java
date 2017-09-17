package com.kelan.core.filters;

import org.apache.log4j.Logger;
import org.apache.regexp.RE;
import org.apache.regexp.RESyntaxException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
*
* Title: 处理每次请求当中用户登录是否超时，如果超时则跳转到指定的页面。
* 同时通过参数配置指定监听的路径， 以及使用正则表达式指定规避的路径
*
* Description: 处理每次请求当中用户登录是否超时，如果超时则跳转到指定的页面。
* 同时通过参数配置指定监听的路径， 以及使用正则表达式指定规避的路径
*
*
*/
public class SessionValidFilter implements Filter {
	
   public static final String AUTH_KEY = "dqsjitr4#$dp6aKHw#$(%845402fMNhk17fl";

   protected String encoding;

   /*
    * 在Web.xml配置文件中加入： <!--SessionValidFilter声明及配置信息--> <filter>
    * <filter-name>SessionValidFilter</filter-name>
    * <filter-class>com.hg.dzcl.core.filters</filter-class> 
    * <init-param>
    * <!--全局的开关，ignore为true时表明不做这个SessionValidFilter的过滤-->
    * <param-name>ignore</param-name>
    * <param-value>false</param-value>
    * </init-param> 
    * <init-param> 
    * <!--Filter返回到的登录页面的路径，缺省为WebApp的根"/"-->
    * <param-name>login_page</param-name> 
    * <param-value>/</param-value>
    * </init-param> 
    * <init-param>
    * <!--Filter需要避免的url字眼，使用正则表达式标识需要规避的URL-Pattern字眼-->
    * <param-name>exclusive_pattern</param-name>
    * <param-value>(login)|(logout)</param-value> 
    * </init-param> 
    * </filter>
    * <!--SessionValidFilter需要监控的URL-PATTERN--> 
    * <filter-mapping>
    * <filter-name>SessionValidFilter</filter-name>
    * <url-pattern>/jsp/*</url-pattern> 
    * </filter-mapping> 
    * <filter-mapping>
    * <filter-name>SessionValidFilter</filter-name>
    * <url-pattern>*.do</url-pattern> </filter-mapping> 
    * <filter-mapping>
    * <filter-name>SessionValidFilter</filter-name>
    * <url-pattern>/servlet/*</url-pattern> 
    * </filter-mapping>
    */
   /**
    * 当前类自己的logger
    */
   private static Logger logger = Logger.getLogger(SessionValidFilter.class);

   /**
    * Web.xml中对于本Filter的配置信息（用来得到参数的）
    */
   protected FilterConfig filterConfig = null;

   /**
    * Should a character encoding specified by the client be ignored?
    */
   protected boolean ignore = true;

   /**
    * 需要跳转到的登录页面
    *
    */
   protected String loginPage = null;

   /**
    * Filter需要避免的字词，用,分隔
    */
   protected String exclusivePattern = null;

   /**
    * 正则表达式检查器
    */
   private RE regularExam = null;

   /**
    * 检查客户端的session用户登录信息，如果为空表明超时登录，跳转倒指定页面
    *
    *
    * @param request The servlet request we are processing
    * @param response The servlet response we are creating
    * @param chain The filter chain we are processing
    * @exception IOException if an input/output error occurs
    * @exception ServletException if a servlet error occurs
    */
   public void doFilter(ServletRequest request, ServletResponse response,
           FilterChain chain) throws IOException, ServletException {
       ((HttpServletResponse) response).setHeader("Pragma", "no-cache"); //
       // 阻止浏览器缓存HTTP1.0
       ((HttpServletResponse) response).setHeader("Cache-Control", "no-store"); // 阻止浏览器缓存HTTP1.1
       request.setCharacterEncoding(encoding);
       ((HttpServletResponse) response).setDateHeader("Expires", 0); //
       // 阻止代理服务缓存生效
       // // 获得请求的路径

       String requestURI = ((HttpServletRequest) request).getRequestURI();

       // 如果非忽略本Filter，则检查用户的Session
       if (!ignore && !this.matchExclusivePattern(requestURI)
               && (!isUserLogin((HttpServletRequest) request))) {
			// Dispatch to the login page, and stop the filter chain.

           RequestDispatcher dispatcher = request
                   .getRequestDispatcher(loginPage);
           dispatcher.forward(request, response);

       } else {
           chain.doFilter(request, response);
       }
   }

   /**
    * Place this filter into service.
    *
    * @param pFilterConfig The filter configuration object
    * @throws ServletException 初始化失败
    *
    */
   public void init(FilterConfig pFilterConfig) throws ServletException {
       this.filterConfig = pFilterConfig;

       String value = pFilterConfig.getInitParameter("ignore");
       encoding = filterConfig.getInitParameter("encoding");
       if (encoding == null) {
           encoding = "utf-8";
       }
       if (value == null) {
           this.ignore = true;
       } else if (value.equalsIgnoreCase("true")) {
           this.ignore = true;
       } else if (value.equalsIgnoreCase("yes")) {
           this.ignore = true;
       } else {
           this.ignore = false;
       }

       this.loginPage = pFilterConfig.getInitParameter("login_page");
       if (this.loginPage == null) {
           this.loginPage = "/";
       }

       this.exclusivePattern = pFilterConfig
               .getInitParameter("exclusive_pattern");
       if (this.exclusivePattern == null) {
           this.exclusivePattern = "";
       }

       if (logger.isDebugEnabled()) {
           logger.debug("SessionFilter init success. Ignore=" + ignore
                   + ", LoginPage=" + loginPage + ", Exclusive="
                   + exclusivePattern);
       }

       regularExam = new RE(exclusivePattern);
   }

   /**
    * Take this filter out of service.
    */
   public void destroy() {
       this.filterConfig = null;
       this.loginPage = null;
       this.exclusivePattern = null;
   }

   /**
    * 检查用户是否登录
    *
    *
    * @param request HttpServletRequest
    * @return 用户已经登录为true，否则为false
    */
   public final static boolean isUserLogin(HttpServletRequest request) {
       HttpSession session = request.getSession();
       Object user = session.getAttribute(AUTH_KEY);
       if (user != null) {
           return true;
       }
       logger.debug("Application Session TimeOut");
       return false;
   }

   /**
    * 检查当前请求路径是否在规避的URL pattern中
    *
    *
    * @param url String
    * @return 存在规避的pattern，返回true；否则为false
    */
   private final boolean matchExclusivePattern(String url) {
       // 调用jakarta的RegExp包进行正则表达式匹配
       try {
           return regularExam.match(url);
       } catch (RESyntaxException e) {
           return false;
       }

   }
}
