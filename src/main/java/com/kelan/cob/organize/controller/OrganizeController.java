package com.kelan.cob.organize.controller;

import com.kelan.cob.organize.entity.Organize;
import com.kelan.cob.organize.service.OrganizeService;
import com.kelan.cob.pic.entity.CobPic;
import com.kelan.cob.pic.service.CobPicService;
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
@RequestMapping(value = "/Organize")
public class OrganizeController extends BaseController {

    private static Logger log = Logger.getLogger(OrganizeController.class);

    @Autowired
    private CobPicService cobPicService;

    @Autowired
    private OrganizeService organizeService;

    private String rootDIr = "http://" + InetAddressUtil.IP + ":8080";

    @RequestMapping("/system")
    public ModelAndView organize(String page,String level,String type) {
        Map<String,Object>result=listOrganize(page,null);
        if(level!=null&&"system".equals(level)){
            return new ModelAndView("/system/index", "result", result);
        }else {
            return new ModelAndView("show/listPage", "result", result);
        }
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/addOrganize")
    public Map<String, Object> addOrganize(Organize organize, MultipartHttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String realPath = request.getSession().getServletContext().getRealPath("");
        try{
            organize.setId(id);
            organize.setCreateDate(createDate);
            organize.setUpdateDate(createDate);
            organize.setDeleteFlag("0");
            //多图上传
            Map<String, Object> uploadResult = uploadFileKey(request);
            if ("0".equals(uploadResult.get("isError"))) {
                @SuppressWarnings("unchecked")
                List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                if(nameList!=null&&nameList.size()!=0){
                    result.put("picNum", nameList.size());
                    // 保存图片信息到数据库
                    for (int i = 0; i < nameList.size(); i++) {
                        organize.setCoverPic(nameList.get(i).getMiniImgPath());
                    }
                }
            }else{
                log.warn("未添加封面");
                result.put("warn","未添加封面");
            }
            if(organizeService.addOrganize(organize)==false){
                log.error("添加出错");
                result.put("error","添加失败");
                return result;
            }
            result.put("info","添加成功");
            result.put("id",organize.getId());
        }catch (Exception e){
            log.error(e);
            result.put("error","添加失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping(value ="/addCover_pic",method = RequestMethod.POST)
    public Map<String, Object> addCover_pic( MultipartHttpServletRequest request,String objId,String type) {
        Map<String, Object> result = new HashMap<>();
        if(objId==null){
            log.info("上传图片失败");
            result.put("error","ID获取为空");
            return result;
        }
        String updateDate = DateUtil.getFullTime();
        String realPath = request.getSession().getServletContext().getRealPath("");
        try{
            Organize organize=organizeService.getOrganize(objId);
            if(organize!=null){
                //多图上传
                Map<String, Object> uploadResult = uploadFileKey(request);
                if ("0".equals(uploadResult.get("isError"))) {
                    @SuppressWarnings("unchecked")
                    List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                    if(nameList.size()!=0&&nameList!=null){
                        result.put("picNum", nameList.size());
                        // 保存图片信息到数据库
                        for (int i = 0; i < nameList.size(); i++) {
                            organize.setUpdateDate(updateDate);
                            organize.setCoverPic(nameList.get(i).getMiniImgPath());
                            // pic.setObjId(url);
                            boolean picResult = organizeService.updateOrganize(organize);
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
    @RequestMapping("/getOrganize")
    public Map<String, Object> getOrganize(String id) {
        Map<String, Object> result = new HashMap<>();
        int picCount=cobPicService.getPicCount(id);
        try{
            Organize organize=organizeService.getOrganize(id);
            //activity.setCoverPic(rootDIr+activity.getCoverPic());
            List<CobPic> listCobPic=cobPicService.listCobPic(1,picCount,id);
            if(organize==null){
                log.error("获取出错");
                result.put("error","获取失败");
                return result;
            }
            result.put("Object",organize);
            result.put("listCobPic",listCobPic);
            result.put("rootDIr",rootDIr);
            result.put("model","Organize");
            if(organize.getContent()!=null){
                result.put("content",new String(organize.getContent()));
            }
            result.put("info","获取成功");
        }catch (Exception e){
            log.error(e);
            result.put("error","获取失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/getContent")
    public String getContent(String id,String type) {
        Map<String, Object> result = new HashMap<>();
        Organize organize=organizeService.getOrganize(id);
        if (organize!=null){
            return  new String(organize.getContent());
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/listOrganize")
    public Map<String, Object> listOrganize(String page,String type) {
        Map<String, Object> result = new HashMap<>();
        try{
            Map<String, Integer> pageMap = PageDeal.requestPage(page);
            if(pageMap==null){
                log.error("获取分页错误");
                result.put("error","获取分页错误");
                return result;
            }
           List<Organize> listOrganize=organizeService.listOrganize(pageMap.get("pageStart"),pageMap.get("pageSize"),type);
            if(listOrganize==null){
                log.error("获取分页出错");
                result.put("error","获取分页失败");
            }
            if(listOrganize.isEmpty()){
                log.warn("获取分页为空");
                result.put("warn","没有数据！！");
            }else{
                result.put("listObject",listOrganize);
                result.put("size",listOrganize.size());
                result.put("info","获取活动分页成功");
            }
        }catch (Exception e){
            log.error(e);
            result.put("error","获取分页失败");
        }
        result.put("model","Organize");
        return result;
    }

    @ResponseBody
    @RequestMapping("/tableOrganize")
    public ModelAndView tableOrganize(String page,String type) {
        Map<String,Object> result=listOrganize(page,type);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("system/table");
        mav.addObject("result",result);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/updateOrganize")
    public Map<String, String> updateOrganize(Organize organize) {
        Map<String, String> result = new HashMap<>();
        String updateDate = DateUtil.getFullTime();
        try{
            organize.setUpdateUserId("admin");
            organize.setUpdateDate(updateDate);
            if(organizeService.updateOrganize(organize)==false){
                log.error("更新出错");
                result.put("error","更新失败");
                return result;
            }
            result.put("info","更新成功");
            result.put("id",organize.getId());
        }catch (Exception e){
            log.error(e);
            result.put("error","更新失败");
        }finally {
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteOrganize")
    public Map<String, String> deleteActivity(List listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(organizeService.deleteOrganize(listStr)==false){
                log.error("删除出错");
                result.put("error","删除失败");
                return result;
            }
            result.put("info","删除成功");
        }catch (Exception e){
            log.error(e);
            result.put("error","删除失败");
        }finally {
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteListOrganize")
    public String deleteListActivity(@RequestParam("listStr[]") List<String> listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(organizeService.deleteListOrganize(listStr)==false){
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
