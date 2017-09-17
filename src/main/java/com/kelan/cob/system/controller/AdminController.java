package com.kelan.cob.system.controller;

import com.kelan.cob.activity.controller.ActivityController;
import com.kelan.cob.develop.controller.DevelopController;
import com.kelan.cob.member.controller.MemberController;
import com.kelan.cob.notice.controller.NoticeController;
import com.kelan.cob.organize.controller.OrganizeController;
import com.kelan.cob.survey.controller.SurveyController;
import com.kelan.cob.system.service.Side_menuService;
import com.kelan.cob.work.controller.WorkController;
import com.kelan.core.file.BaseController;
import com.kelan.core.file.ImageInfo;
import com.kelan.core.ueditor.ActionEnter;
import com.kelan.core.util.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/system")
public class AdminController extends BaseController {

    private static Logger log = Logger.getLogger(AdminController.class);

    @Autowired
    private ActivityController activityController;

    @Autowired
    private DevelopController developController;

    @Autowired
    private NoticeController noticeController;

    @Autowired
    private SurveyController surveyController;

    @Autowired
    private WorkController workController;

    @Autowired
    private OrganizeController organizeController;

    @Autowired
    private MemberController memberController;

    @Autowired
    private Side_menuService side_menuService;

    @RequestMapping("")
    public ModelAndView  index() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("system/index", "result", result);
    }

    @RequestMapping("/account")
    public ModelAndView  account() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("user/registPage", "result", result);
    }

    public ModelAndView listPage(String page,String model) {
        Map<String,Object> result=new HashMap<>();
        return new ModelAndView("show/listPage", "result", result);
        //return "system/index";
    }

    @RequestMapping("/add")
    public ModelAndView add(String model) {
        Map<String, Object> result = new HashMap<>();
        if(!StringUtils.isEmpty(model)){
            if("Member".equals(model)){
                return new ModelAndView("system/control/add_member", "result", null);
            }
            result.put("add_url","/"+model+"/add"+model);
            result.put("CoverPic_url","/"+model+"/addCover_pic");
        }
        result.put("morePic_url","/CobPic/addMore_pic");
        return new ModelAndView("system/editor", "result", result);
    }

    @RequestMapping("/getObject")
    public Map<String, Object> getObject(String id,String model) {
        Map<String, Object> result = new HashMap<>();
        if(model!=null&&"Activity".equals(model)){
            result=activityController.getActivity(id);
            result.put("place","专题活动");
        }
        if(model!=null&&"Develop".equals(model)){
            result=developController.getDevelop(id);
            result.put("place","民意调研");
        }
        if(model!=null&&"Notice".equals(model)){
            result=noticeController.getNotice(id);
            result.put("place","通知公告");
        }
        if(model!=null&&"Survey".equals(model)){
            result=surveyController.getSurvey(id);
            result.put("place","党员发展");
        }

        if(model!=null&&"Work".equals(model)){
            result=workController.getWork(id);
            result.put("place","党建工作动态");
        }

        if(model!=null&&"Organize".equals(model)){
            result=organizeController.getOrganize(id);
            result.put("place","党组资讯");
        }

        if(model!=null&&"Member".equals(model)){
            result=memberController.getMember(id);
            result.put("place","党员信息");
        }
        return result;
    }

    @RequestMapping("/check")
    public ModelAndView check(String id,String model) {
        Map<String, Object> result = getObject(id,model);
        return new ModelAndView("show/content", "result", result);
    }

    @RequestMapping("/update")
    public ModelAndView update(String id,String model) {
        Map<String, Object> result = getObject(id,model);
        if(model!=null&&"Member".equals(model)){
            return new ModelAndView("system/control/update_member", "result", result);
        }
        return new ModelAndView("system/update", "result", result);
    }

     @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json");
        String rootPath = request.getSession()
                .getServletContext().getRealPath("/");

        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value="/images")
    public Map<String, Object> images (MultipartFile upfile, HttpServletRequest request, HttpServletResponse response){
        Map<String, Object> params = new HashMap<String, Object>();
        String realPath = request.getSession().getServletContext().getRealPath("");
        try{
            if (upfile != null && !(upfile.isEmpty()) && upfile.getSize() > 0) {
                ImageInfo imgInf = uploadTypeImage(upfile, realPath,"ueditor");
                if (imgInf.getMsg() == null) {
                    params.put("original", imgInf.getFileName());
                    params.put("url",  imgInf.getImgPath());
                } else {
                    params.put("state", "ERROR");
                    return params;
                }
            }
            params.put("state", "SUCCESS");
            params.put("size", upfile.getSize());
            params.put("type", upfile.getContentType());
        } catch (Exception e){
            params.put("state", "ERROR");
        }
        return params;
    }

    @RequestMapping(value="/menu_click")
    public ModelAndView menu_click (String node){
        Map<String, Object> result=null;
        if("1".equals(node)){
            return organizeController.tableOrganize("1/10",null);
        }
        if("2".equals(node)){
            return memberController.tableMember("1/10");
        }
        return new ModelAndView("system/control/menu", "result", result);
    }

    @RequestMapping(value="/form_member")
    public ModelAndView form_member (){

        return new ModelAndView("system/control/add_member", "result", null);
    }

}
