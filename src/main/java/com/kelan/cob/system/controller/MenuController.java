package com.kelan.cob.system.controller;

import com.kelan.cob.activity.controller.ActivityController;
import com.kelan.cob.activity.entity.Activity;
import com.kelan.cob.develop.controller.DevelopController;
import com.kelan.cob.member.controller.MemberController;
import com.kelan.cob.notice.controller.NoticeController;
import com.kelan.cob.organize.controller.OrganizeController;
import com.kelan.cob.survey.controller.SurveyController;
import com.kelan.cob.system.entity.Side_menu;
import com.kelan.cob.system.service.Side_menuService;
import com.kelan.cob.work.controller.WorkController;
import com.kelan.core.file.BaseController;
import com.kelan.core.file.ImageInfo;
import com.kelan.core.ueditor.ActionEnter;
import com.kelan.core.util.DateUtil;
import com.kelan.core.util.StringUtils;
import com.kelan.core.util.UUIdUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/menu")
public class MenuController extends BaseController {

    private static Logger log = Logger.getLogger(MenuController.class);

    @Autowired
    private Side_menuService side_menuService;

    @RequestMapping("")
    public ModelAndView  index() {
        Map<String, Object> result = new HashMap<>();
        return new ModelAndView("/system/control/menu_add", "result", result);
    }

    @ResponseBody
    @RequestMapping("/addSide_menu")
    public Map<String, Object> addSide_menu(Side_menu side_menu) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        try{
            side_menu.setId(id);
            side_menu.setCreateDate(createDate);
            side_menu.setUpdateDate(createDate);
            side_menu.setDeleteFlag("0");
            if(side_menuService.addSide_menu(side_menu)==false){
                log.error("添加出错");
                result.put("error","添加失败");
                return result;
            }
            result.put("info","添加成功");
            result.put("id",side_menu.getId());
        }catch (Exception e){
            log.error(e);
            result.put("error","添加失败");
        }
        return result;
    }



    @RequestMapping(value="/list_menu")
    public ModelAndView menu (String model){
        Map<String, Object> result = new HashMap<>();
        try{
            List<Side_menu> listSide_menu=side_menuService.listSide_menu(model);
            if(listSide_menu==null){
                log.error("获取分页出错");
                result.put("error","获取分页失败");
            }
            if(listSide_menu.isEmpty()){
                log.warn("获取分页为空");
                result.put("warn","没有数据！！");
            }else{
                result.put("listObject",listSide_menu);
                result.put("size",listSide_menu.size());
                result.put("info","获取菜单分页成功");
            }
        }catch (Exception e){
            log.error(e);
            result.put("error","获取分页失败");
        }
        result.put("model",model);
        return new ModelAndView("system/control/menu", "result", result);
    }

}
