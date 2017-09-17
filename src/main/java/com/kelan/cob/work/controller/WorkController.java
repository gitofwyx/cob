package com.kelan.cob.work.controller;

import com.kelan.cob.pic.entity.CobPic;
import com.kelan.cob.pic.service.CobPicService;
import com.kelan.cob.work.entity.Work;
import com.kelan.cob.work.service.WorkService;
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
@RequestMapping("/Work")
public class WorkController extends BaseController {

    private static Logger log = Logger.getLogger(WorkController.class);

    @Autowired
    private CobPicService cobPicService;

    @Autowired
    private WorkService workService;

    private String rootDIr = "http://" + InetAddressUtil.IP + ":8080";

    @RequestMapping("/system")
    public ModelAndView work(String page,String level) {
        Map<String,Object>result=listWork(page);
        if(level!=null&&"system".equals(level)){
            return new ModelAndView("/system/index", "result", result);
        }else {
            return new ModelAndView("show/listPage", "result", result);
        }
    }

    @ResponseBody
    @RequestMapping("/addWork")
    public Map<String, Object> addWork(Work work, MultipartHttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String realPath = request.getSession().getServletContext().getRealPath("");
        try{
            work.setId(id);
            work.setCreateDate(createDate);
            work.setUpdateDate(createDate);
            work.setDeleteFlag("0");
            //多图上传
            Map<String, Object> uploadResult = uploadFileKey(request);
            if ("0".equals(uploadResult.get("isError"))) {
                @SuppressWarnings("unchecked")
                List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                if(nameList!=null&&nameList.size()!=0){
                    result.put("picNum", nameList.size());
                    // 保存图片信息到数据库
                    for (int i = 0; i < nameList.size(); i++) {
                        work.setCoverPic(nameList.get(i).getMiniImgPath());
                    }
                }
            }else{
                log.warn("未添加封面");
                result.put("warn","未添加封面");
            }
            if(workService.addWork(work)==false){
                log.error("添加出错");
                result.put("error","添加失败");
                return result;
            }
            result.put("info","添加成功");
            result.put("id",work.getId());
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
            Work work=workService.getWork(objId);
            if(work!=null){
                //多图上传
                Map<String, Object> uploadResult = uploadFileKey(request);
                if ("0".equals(uploadResult.get("isError"))) {
                    @SuppressWarnings("unchecked")
                    List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                    if(nameList.size()!=0&&nameList!=null){
                        result.put("picNum", nameList.size());
                        // 保存图片信息到数据库
                        for (int i = 0; i < nameList.size(); i++) {
                            work.setUpdateDate(updateDate);
                            work.setCoverPic(nameList.get(i).getMiniImgPath());
                            // pic.setObjId(url);
                            boolean picResult = workService.updateWork(work);
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
    @RequestMapping("/getWork")
    public Map<String, Object> getWork(String id) {
        Map<String, Object> result = new HashMap<>();
        int picCount=cobPicService.getPicCount(id);
        try{
            Work work=workService.getWork(id);
            //activity.setCoverPic(rootDIr+activity.getCoverPic());
            List<CobPic> listCobPic=cobPicService.listCobPic(1,picCount,id);
            if(work==null){
                log.error("获取出错");
                result.put("error","获取失败");
                return result;
            }
            result.put("Object",work);
            result.put("listCobPic",listCobPic);
            result.put("rootDIr",rootDIr);
            result.put("model","Work");
            result.put("content",new String(work.getContent()));
            result.put("info","获取成功");
        }catch (Exception e){
            log.error(e);
            result.put("error","获取失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listWork")
    public  Map<String, Object> listWork(String page) {
        Map<String, Object> result = new HashMap<>();
        result.put("model","Work");
        try{
            Map<String, Integer> pageMap = PageDeal.requestPage(page);
            if(pageMap==null){
                log.error("获取分页错误");
                result.put("error","获取分页错误");
                return result;
            }
            List<Work> listWork=workService.listWork(pageMap.get("pageStart"),pageMap.get("pageSize"));
            if(listWork==null){
                log.error("获取分页出错");
                result.put("error","获取分页失败");
            }
            if(listWork.isEmpty()){
                log.warn("获取分页为空");
                result.put("warn","没有数据！！");
            }else{
                result.put("listObject",listWork);
                result.put("size",listWork.size());
                result.put("info","获取工作分页成功");
            }
            result.put("size",listWork.size());
        }catch (Exception e){
            log.error(e);
            result.put("error","获取分页失败");
        }
        result.put("model","Work");
        return result;
    }

    @ResponseBody
    @RequestMapping("/tableWork")
    public ModelAndView tableWork(String page) {
        Map<String,Object> result=listWork(page);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("system/table");
        mav.addObject("result",result);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/updateWork")
    public Map<String, String> updateWork(Work work) {
        Map<String, String> result = new HashMap<>();
        String updateDate = DateUtil.getFullTime();
        try{
            work.setUpdateUserId("admin");
            work.setUpdateDate(updateDate);
            if(workService.updateWork(work)==false){
                log.error("更新出错");
                result.put("error","更新失败");
                return result;
            }
            result.put("info","更新成功");
            result.put("id",work.getId());
        }catch (Exception e){
            log.error(e);
            result.put("error","更新失败");
        }finally {
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteWork")
    public Map<String, String> deleteWork(List listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(workService.deleteWork(listStr)==false){
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
    @RequestMapping("/deleteListWork")
    public String deleteListWork(@RequestParam("listStr[]") List<String> listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(workService.deleteListWork(listStr)==false){
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
