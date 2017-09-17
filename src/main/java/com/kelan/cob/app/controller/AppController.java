package com.kelan.cob.app.controller;

import com.kelan.cob.activity.controller.ActivityController;
import com.kelan.cob.develop.controller.DevelopController;
import com.kelan.cob.map.controller.CobMapController;
import com.kelan.cob.notice.controller.NoticeController;
import com.kelan.cob.organize.controller.OrganizeController;
import com.kelan.cob.survey.controller.SurveyController;
import com.kelan.cob.work.controller.WorkController;
import com.kelan.core.file.BaseController;
import com.kelan.core.util.tree.PageDeal;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/app")
public class AppController extends BaseController {

    private static Logger log = Logger.getLogger(AppController.class);

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
    private CobMapController cobMapController;

    @Autowired
    private OrganizeController organizeController;

    @RequestMapping("")
    public ModelAndView  index() {
        Map<String, Object> result = new HashMap<>();
        //List<Activity> listActivity=activityController.listActivity(1,7);
        //result.put("listActivity",listActivity);
        return new ModelAndView("new", "result", result);
    }

    @ResponseBody
    @RequestMapping("/listActivity")
    public ModelAndView listActivity(String page) {
        Map<String, Object> result = new HashMap<>();
        result=activityController.listActivity(page);
        //result.put("listActivity",listActivity);
        return new ModelAndView("show/main/solicitude", "result", result);
    }

    @ResponseBody
    @RequestMapping("/listDevelop")
    public ModelAndView listDevelop(String page) {
        Map<String, Object> result = new HashMap<>();
        result=developController.listDevelop(page);
        //result.put("listActivity",listActivity);
        return new ModelAndView("show/main/develop", "result", result);
    }

    @ResponseBody
    @RequestMapping("/listNotice")
    public ModelAndView listNotice(String page) {
        Map<String, Object> result = new HashMap<>();
        result=noticeController.listNotice(page);
        return new ModelAndView("app/notice", "result", result);
    }
}
