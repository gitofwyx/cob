package com.kelan.cob.user.controller;

import com.kelan.cob.user.entity.CobUser;
import com.kelan.cob.user.service.CobUserService;
import com.kelan.core.file.BaseController;
import com.kelan.core.file.ImageInfo;
import com.kelan.cob.roles.entity.Roles;
import com.kelan.cob.roles.service.RolesService;
import com.kelan.core.util.DateUtil;
import com.kelan.core.util.UUIdUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("cob")
public class CobUserController extends BaseController {

    private static Logger log = Logger.getLogger(CobUserController.class);

    @Autowired
    private CobUserService cobUserService;

    @Autowired
    private RolesService rolesService;

    @ResponseBody
    @RequestMapping("")
    public ModelAndView  manager() {
        return new ModelAndView("user/login", "key", "智慧党建");
        //return "system/index";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)//用户登录路径
    public String login(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        Map<String, String> result = new HashMap<>();
        if (!currentUser.isAuthenticated()) {
            UsernamePasswordToken token = null;
            if (userName != null && !"".equals(userName)) {
                token = new UsernamePasswordToken(userName, passWord);// 获取登录令牌
                try {
                    //token.setRememberMe(true);
                    currentUser.login(token);
                    result.put("0", "已登录");
                    session.setAttribute("account", userName);
                    
                } catch (UnknownAccountException uae) {
                    log.info("There is no user with username of " + token.getPrincipal());
                    result.put("1", "没有帐号");
                } catch (IncorrectCredentialsException ice) {
                    log.info("Password for account " + token.getPrincipal() + " was incorrect!");
                    result.put("2", "密码不正确");
                } catch (ExcessiveAttemptsException eae) {
                    // 捕获错误登录过多的异常
                    log.info("times error");
                    result.put("3", "登录超次");
                } catch (AuthenticationException ae) {
                    log.info("some error unknown accure");
                    result.put("4", "系统错误");
                }
            } else {
                result.put("1", "没有帐号");
            }
        } else {
            result.put("4", "已登录账号"+session.getAttribute("account")+",重新登录请先注销");
        }
        return "redirect:/system";
    }

    @ResponseBody
    @Scope("prototype")
    @RequestMapping(value = "/regist", method = RequestMethod.POST)//用户注册路径
    public Map<String, String> regist(CobUser user, HttpServletRequest request, @RequestParam(value = "userPic", required = false) MultipartFile userPic) {
        String realPath = request.getSession().getServletContext().getRealPath("");
        Map<String, String> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        try {
            if (cobUserService.getUserAccount(user.getAccount())) {
                result.put("2", "注册信息已存在");
                return result;
            }
            if (userPic != null && !(userPic.isEmpty()) && userPic.getSize() > 0) {
                ImageInfo imgInf = uploadImage(userPic, realPath);
                if (imgInf.getMsg() == null) {
                    user.setPicUrl(imgInf.getImgPath());
                } else {
                    throw new RuntimeException(imgInf.getMsg());
                }
            }
        } catch (Exception e) {
            log.error(e);
            log.info(">>>>用户图片上传失败");
            result.put("msg", "系统错误");
            return result;
        }
        user.setId(id);
        user.setCreateDate(createDate);
        user.setUpdateDate(createDate);
        user.setDeleteFlag("0");
        boolean userResult = cobUserService.addUser(user);
        try {
            if (!(userResult)) {
                throw new RuntimeException("注册失败!");
            } else {
                log.info(">>>>注册成功");
                result.put("msg", "注册成功");
                return result;
            }
        } catch (Exception e) {
            log.info(">>>>注册失败");
            result.put("msg", "注册失败");
            return result;
        }
    }

    @RequestMapping("/page1")
    public String Page() {
        //转发方式1
        return "user/loginPage";
        //return "/system/registPage";
    }

    @ResponseBody
    @RequestMapping(value = "/addRoles", method = RequestMethod.GET) // 添加角色信息
    public Map<String, String> addRoles(HttpServletRequest request) {
        // Subject currentUser = SecurityUtils.getSubject();
        Map<String, String> result = new HashMap<>();
        Roles role = new Roles();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        InputStreamReader ir; // 定义一个字符输入流
        BufferedReader in; // 一个字符缓冲流
        ir = new InputStreamReader(System.in); // ir 接收从键盘得到的字符
        in = new BufferedReader(ir); // 对IR进行包装
        System.out.print("role x is :");
        try {
            String roleVal = in.readLine();
            System.out.println(roleVal);
            role.setId(id);
            role.setRoleVal(roleVal);
            role.setDeleteFlag("0");
            role.setCreateDate(createDate);
            role.setUpdateDate(createDate);
            if (rolesService.addUserRole(role)) {
                result.put("0", "添加角色成功");
                log.info("添加角色成功");
            } else {
                result.put("1", "添加角色失败");
                log.error("添加角色失败");
            }
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        // int x=Integer.parseInt(s); //把得到字符串转化为整型
        catch (Exception e) {
            System.out.println(e);
            log.info(">>>>添加角色失败");
            result.put("isError", "1");
            result.put("1", "添加角色失败");
            System.out.println(result);
            return result;
        }
        return result;
    }
}
