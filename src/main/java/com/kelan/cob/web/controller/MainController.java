package com.kelan.cob.web.controller;

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
@RequestMapping("/")
public class MainController extends BaseController {

    private static Logger log = Logger.getLogger(MainController.class);

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
        return new ModelAndView("show/main/notice", "result", result);
    }

    @ResponseBody
    @RequestMapping("/listSurvey")
    public ModelAndView listSurvey(String page) {
        Map<String, Object> result = new HashMap<>();
        result=surveyController.listSurvey(page);
        return new ModelAndView("show/main/office", "result", result);
    }

    @ResponseBody
    @RequestMapping("/listWork")
    public ModelAndView listWork(String page) {
        Map<String, Object> result = new HashMap<>();
        result=workController.listWork(page);
        return new ModelAndView("show/main/work", "result", result);
    }

    @ResponseBody
    @RequestMapping("/listOrganize")
    public ModelAndView listOrganize(String page) {
        Map<String, Object> result = new HashMap<>();
        result=organizeController.listOrganize(page,null);
        return new ModelAndView("show/main/organize", "result", result);
    }

    @ResponseBody
    @RequestMapping("/listCobMap")
    public ModelAndView listCobMap(String page) {
        Map<String, Object> result = new HashMap<>();
        result=cobMapController.listCobMap(page);
        return new ModelAndView("show/cobMap", "result", result);
    }

    @RequestMapping("/listPage")
    public ModelAndView  listPage(String model) {
        Map<String, Object> result = new HashMap<>();
        result.put("model",model);
        return new ModelAndView("show/listing/listPage", "result", result);
    }

    @RequestMapping("/listPagePoto")
    public ModelAndView  listPagePoto(String model) {
        Map<String, Object> result = new HashMap<>();
        result.put("model",model);
        return new ModelAndView("show/listing/listPagePoto", "result", result);
    }

    @RequestMapping("/indexList")
    public String  indexList() {
        return "show/indexList";
    }

    @RequestMapping("/pageObject")
    public ModelAndView  pageObject(String model,String page) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Integer> pageMap = PageDeal.requestPage(page);
        List<Object> pageNum=null;
        if(model!=null&&"activity".equals(model)){
            result=activityController.listActivity(page);
        }
        if(model!=null&&"develop".equals(model)){
            result=developController.listDevelop(page);
        }
        if(model!=null&&"notice".equals(model)){
            result=noticeController.listNotice(page);
        }
        if(model!=null&&"survey".equals(model)){
            result=surveyController.listSurvey(page);
        }
        if(model!=null&&"work".equals(model)){
            result=workController.listWork(page);
        }
        if(model!=null&&"cobMap".equals(model)){
            result=cobMapController.listCobMap(page);
            return new ModelAndView("show/cobMap", "result", result);
        }
        if(model!=null&&"organize".equals(model)){
            result=organizeController.listOrganize(page,null);
            result.put("pageNow",pageMap.get("pageStart"));
            return new ModelAndView("show/listing/tablePoto", "result", result);
        }
        result.put("pageNow",pageMap.get("pageStart"));
        return new ModelAndView("show/listing/tableObj", "result", result);
    }

    @ResponseBody
    @RequestMapping("/listingDevelop")
    public ModelAndView pageDevelop(String page) {
        Map<String, Object> result = new HashMap<>();
        result=developController.listDevelop(page);
        //result.put("listActivity",listActivity);
        return new ModelAndView("show/listing/develop", "result", result);
    }

    @ResponseBody
    @RequestMapping("/listingNotice")
    public ModelAndView listingNotice(String page) {
        Map<String, Object> result = new HashMap<>();
        result=noticeController.listNotice(page);
        //result.put("listActivity",listActivity);
        return new ModelAndView("show/listing/notice", "result", result);
    }

    @RequestMapping("/getObject")
    public ModelAndView getObject(String model,String id) {
        Map<String, Object> result = new HashMap<>();
        List<Object> pageNum=null;
        if(model!=null&&"Activity".equals(model)){
            result=activityController.getActivity(id);
        }
        if(model!=null&&"Develop".equals(model)){
            result=developController.getDevelop(id);
        }
        if(model!=null&&"Notice".equals(model)){
            result=noticeController.getNotice(id);
        }
        if(model!=null&&"Survey".equals(model)){
            result=surveyController.getSurvey(id);
        }
        if(model!=null&&"Work".equals(model)){
            result=workController.getWork(id);
        }
        if(model!=null&&"Organize".equals(model)){
            result=organizeController.getOrganize(id);
        }
        return new ModelAndView("show/content", "result", result);
    }

    @RequestMapping("/dzzSrc")
    public ModelAndView ptSrc_1() {
        return new ModelAndView("show/dzz", "result", null);
    }

    @RequestMapping("/pilot")
    public ModelAndView pilot(String level) {
        if("1".equals(level)){
            return new ModelAndView("show/hainan_cob", "result", null);
        }
        if("2".equals(level)){
            return new ModelAndView("show/haikou_cob", "result", null);
        }
        return new ModelAndView("show/hainan_cob", "result", null);
    }

    @RequestMapping("/main_header")
    public ModelAndView main_header() {
        return new ModelAndView("show/main/header", "result", null);
    }

    @RequestMapping("/main_footer")
    public ModelAndView main_footer() {
        return new ModelAndView("show/main/footer", "result", null);
    }

    @RequestMapping("/cob_video")
    public ModelAndView cob_video() {

        return new ModelAndView("show/cob_video", "result", null);
    }

    @RequestMapping("/organize")
    public ModelAndView organize() {
        return new ModelAndView("show/cob_organize/organize", "result", null);
    }

    @RequestMapping("/menu")
    public ModelAndView menu() {
        return new ModelAndView("show/control/menu", "result", null);
    }

    @RequestMapping("/cob_organize")
    public ModelAndView cob_organize() {
        return new ModelAndView("show/cob_organize/index", "result", null);
    }
}
