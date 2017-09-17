package com.kelan.cob.survey.controller;

import com.kelan.cob.pic.entity.CobPic;
import com.kelan.cob.pic.service.CobPicService;
import com.kelan.cob.survey.entity.Survey;
import com.kelan.cob.survey.service.SurveyService;
import com.kelan.core.file.BaseController;
import com.kelan.core.file.ImageInfo;
import com.kelan.core.util.DateUtil;
import com.kelan.core.util.InetAddressUtil;
import com.kelan.core.util.UUIdUtil;
import com.kelan.core.util.tree.PageDeal;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/Survey")
public class SurveyController extends BaseController {

    private static Logger log = Logger.getLogger(SurveyController.class);

    @Autowired
    private CobPicService cobPicService;

    @Autowired
    private SurveyService surveyService;

    private String rootDIr = "http://" + InetAddressUtil.IP + ":8080";

    @RequestMapping("/system")
    public ModelAndView survey(String page,String level) {
        Map<String,Object>result=listSurvey(page);
        if(level!=null&&"system".equals(level)){
            return new ModelAndView("/system/index", "result", result);
        }else {
            return new ModelAndView("show/listPage", "result", result);
        }
    }

    @ResponseBody
    @RequestMapping("/addSurvey")
    public Map<String, Object> addSurvey(Survey survey,MultipartHttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String realPath = request.getSession().getServletContext().getRealPath("");
        try{
            survey.setId(id);
            survey.setCreateDate(createDate);
            survey.setUpdateDate(createDate);
            survey.setDeleteFlag("0");
            //多图上传
            Map<String, Object> uploadResult = uploadFileKey(request);
            if ("0".equals(uploadResult.get("isError"))) {
                @SuppressWarnings("unchecked")
                List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                if(nameList!=null&&nameList.size()!=0){
                    result.put("picNum", nameList.size());
                    // 保存图片信息到数据库
                    for (int i = 0; i < nameList.size(); i++) {
                        survey.setCoverPic(nameList.get(i).getMiniImgPath());
                    }
                }
            }else{
                log.warn("未添加封面");
                result.put("warn","未添加封面");
            }
            if(surveyService.addSurvey(survey)==false){
                log.error("添加出错");
                result.put("error","添加失败");
                return result;
            }
            result.put("info","添加成功");
            result.put("id",survey.getId());
        }catch (Exception e){
            log.error(e);
            result.put("error","添加失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value ="/addCover_pic",method = RequestMethod.POST)
    public Map<String, Object> addCover_pic( MultipartHttpServletRequest request,String objId) {
        Map<String, Object> result = new HashMap<>();
        if(objId==null){
            log.info("上传图片失败");
            result.put("error","ID获取为空");
            return result;
        }
        String updateDate = DateUtil.getFullTime();
        String realPath = request.getSession().getServletContext().getRealPath("");
        try{
            Survey survey=surveyService.getSurvey(objId);
            if(survey!=null){
                //多图上传
                Map<String, Object> uploadResult = uploadFileKey(request);
                if ("0".equals(uploadResult.get("isError"))) {
                    @SuppressWarnings("unchecked")
                    List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                    if(nameList.size()!=0&&nameList!=null){
                        result.put("picNum", nameList.size());
                        // 保存图片信息到数据库
                        for (int i = 0; i < nameList.size(); i++) {
                            survey.setUpdateDate(updateDate);
                            survey.setCoverPic(nameList.get(i).getMiniImgPath());
                            // pic.setObjId(url);
                            boolean picResult = surveyService.updateSurvey(survey);
                            if (picResult) {
                                log.info("上传图片成功");
                            } else {
                                log.info("上传图片失败");
                                result.put("error","添加失败");
                                return result;
                            }
                        }
                    }
                }
                result.put("info","添加成功");
            }
        }catch (Exception e){
            log.error(e);
            result.put("error","添加失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getSurvey")
    public Map<String, Object> getSurvey(String id) {
        Map<String, Object> result = new HashMap<>();
        int picCount=cobPicService.getPicCount(id);
        try{
            Survey survey=surveyService.getSurvey(id);
            List<CobPic> listCobPic=cobPicService.listCobPic(1,picCount,id);
            if(survey==null){
                log.error("获取出错");
                result.put("error","获取失败");
                return result;
            }
            result.put("Object",survey);
            result.put("listCobPic",listCobPic);
            result.put("rootDIr",rootDIr);
            result.put("model","Survey");
            result.put("content",new String(survey.getContent()));
            result.put("info","获取成功");
        }catch (Exception e){
            log.error(e);
            result.put("error","获取失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listSurvey")
    public  Map<String, Object> listSurvey(String page) {
        Map<String, Object> result = new HashMap<>();
        try{
            Map<String, Integer> pageMap = PageDeal.requestPage(page);
            if(pageMap==null){
                log.error("获取分页错误");
                result.put("error","获取分页错误");
                return result;
            }
            List<Survey> listSurvey=surveyService.listSurvey(pageMap.get("pageStart"),pageMap.get("pageSize"));
            if(listSurvey==null){
                log.error("获取分页出错");
                result.put("error","获取分页失败");
            }
            if(listSurvey.isEmpty()){
                log.warn("获取分页为空");
                result.put("warn","没有数据！！");
            }else{
                result.put("listObject",listSurvey);
                result.put("size",listSurvey.size());
                result.put("info","获取调查分页成功");
            }
        }catch (Exception e){
            log.error(e);
            result.put("error","获取分页失败");
        }
        result.put("model","Survey");
       return result;
    }

    @ResponseBody
    @RequestMapping("/tableSurvey")
    public ModelAndView tableSurvey(String page) {
        Map<String,Object> result=listSurvey(page);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("system/table");
        mav.addObject("result",result);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/updateSurvey")
    public Map<String, String> updateSurvey(Survey survey) {
        Map<String, String> result = new HashMap<>();
        String updateDate = DateUtil.getFullTime();
        try{
            survey.setUpdateUserId("admin");
            survey.setUpdateDate(updateDate);
            if(surveyService.updateSurvey(survey)==false){
                log.error("更新出错");
                result.put("error","更新失败");
                return result;
            }
            result.put("msg","更新成功");
            result.put("id",survey.getId());
        }catch (Exception e){
            log.error(e);
            result.put("error","更新失败");
        }finally {
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteSurvey")
    public Map<String, String> deleteSurvey(List listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(surveyService.deleteSurvey(listStr)==false){
                log.error("删除出错");
                result.put("msg","删除失败");
                return result;
            }
            result.put("msg","删除成功");
        }catch (Exception e){
            log.error(e);
            result.put("msg","删除失败");
        }finally {
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteListSurvey")
    public String deleteListSurvey(@RequestParam("listStr[]") List<String> listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(surveyService.deleteListSurvey(listStr)==false){
                log.error("删除出错");
                result.put("error","删除失败");
                return result.get("info");
            }
            result.put("info","删除成功");
        }catch (Exception e){
            log.error(e);
            result.put("error","删除失败");
        }finally {
            return result.get("info");
        }
    }

}
