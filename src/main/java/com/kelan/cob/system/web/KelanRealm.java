package com.kelan.cob.system.web;

import com.kelan.cob.user.entity.CobUser;
import com.kelan.cob.user.service.CobUserService;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class KelanRealm extends AuthorizingRealm {

	@Autowired
	private CobUserService userService;

	private static Logger log = Logger.getLogger(KelanRealm.class);

	// 进行认证的!
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		// 1. 使用传入的 token 来获取数据表中实际的记录的信息
		// 具体是利用 username 来获取用户信息

		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
		Subject currentUser = SecurityUtils.getSubject();
		Session session = currentUser.getSession();
		String realmName = getName();
		String account = usernamePasswordToken.getUsername();
		CobUser user = userService.getUserByAccount(account);
		SimpleAuthenticationInfo info = null;
		if (user != null) {
			info = new SimpleAuthenticationInfo(account, user.getPassword(), realmName);
		} else {
			session.setAttribute("error", "用户没有登录");
			log.warn("用户没有登录");
			throw new UnknownAccountException("用户不存在");
		}
		session.setAttribute("userId", user.getId());
		session.setAttribute("userName", user.getName());
		log.info("验证完成...");
		return info;
	}

	// 进行授权的!
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 获取当前的登陆用的信息.
		String account = (String) principals.getPrimaryPrincipal();
		String role = userService.getRoleByAccount(account);
		info.addRole(role);
		log.info("授权完成...");
		return info;
	}
	protected AuthorizationInfo getAuthorizationInfo(PrincipalCollection principals){
		log.info("授权...");
		return super.getAuthorizationInfo(principals);
		
	}
}
