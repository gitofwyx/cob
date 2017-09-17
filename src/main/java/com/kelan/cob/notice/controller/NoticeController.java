package com.kelan.cob.notice.controller;
import com.kelan.cob.notice.entity.Notice;
import com.kelan.cob.notice.service.NoticeService;
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
@RequestMapping("/Notice")
public class NoticeController extends BaseController {

    private static Logger log = Logger.getLogger(NoticeController.class);

    @Autowired
    private CobPicService cobPicService;

    @Autowired
    private NoticeService noticeService;

    private String rootDIr = "http://" + InetAddressUtil.IP + ":8080";

    @RequestMapping("/system")
    public ModelAndView notice(String page,String level) {
        Map<String,Object> result=listNotice(page);
        if(level!=null&&"system".equals(level)){
            return new ModelAndView("/system/index", "result", result);
        }else {
            return new ModelAndView("show/listPage", "result", result);
        }
    }

    @ResponseBody
    @RequestMapping("/addNotice")
    public Map<String, Object> addNotice(Notice notice, MultipartHttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String realPath = request.getSession().getServletContext().getRealPath("");
        try{
            notice.setId(id);
            notice.setCreateDate(createDate);
            notice.setUpdateDate(createDate);
            notice.setDeleteFlag("0");
            //多图上传
            Map<String, Object> uploadResult = uploadFileKey(request);
            if ("0".equals(uploadResult.get("isError"))) {
                @SuppressWarnings("unchecked")
                List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                if(nameList!=null&&nameList.size()!=0){
                    result.put("picNum", nameList.size());
                    // 保存图片信息到数据库
                    for (int i = 0; i < nameList.size(); i++) {
                        notice.setCoverPic(nameList.get(i).getMiniImgPath());
                    }
                }
            }else{
                log.warn("未添加封面");
                result.put("warn","未添加封面");
            }
            if(noticeService.addNotice(notice)==false){
                log.error("添加出错");
                result.put("error","添加失败");
                return result;
            }
            result.put("info","添加成功");
            result.put("id",notice.getId());
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
            Notice notice=noticeService.getNotice(objId);
            if(notice!=null){
                //多图上传
                Map<String, Object> uploadResult = uploadFileKey(request);
                if ("0".equals(uploadResult.get("isError"))) {
                    @SuppressWarnings("unchecked")
                    List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                    if(nameList.size()!=0&&nameList!=null){
                        result.put("picNum", nameList.size());
                        // 保存图片信息到数据库
                        for (int i = 0; i < nameList.size(); i++) {
                            notice.setUpdateDate(updateDate);
                            notice.setCoverPic(nameList.get(i).getMiniImgPath());
                            // pic.setObjId(url);
                            boolean picResult = noticeService.updateNotice(notice);
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
    @RequestMapping("/getNotice")
    public Map<String, Object> getNotice(String id) {
        Map<String, Object> result = new HashMap<>();
        int picCount=cobPicService.getPicCount(id);
        try{
            Notice notice=noticeService.getNotice(id);
            //activity.setCoverPic(rootDIr+activity.getCoverPic());
            List<CobPic> listCobPic=cobPicService.listCobPic(1,picCount,id);
            if(notice==null){
                log.error("获取出错");
                result.put("error","获取失败");
                return result;
            }
            result.put("Object",notice);
            result.put("listCobPic",listCobPic);
            result.put("rootDIr",rootDIr);
            result.put("model","Notice");
            result.put("content",new String(notice.getContent()));
            result.put("info","获取成功");
        }catch (Exception e){
            log.error(e);
            result.put("error","获取失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listNotice")
    public Map<String, Object> listNotice(String page) {
        Map<String, Object> result = new HashMap<>();
        try{
            Map<String, Integer> pageMap = PageDeal.requestPage(page);
            if(pageMap==null){
                log.error("获取分页错误");
                result.put("error","获取分页错误");
                return result;
            }
            List<Notice> listNotice=noticeService.listNotice(pageMap.get("pageStart"),pageMap.get("pageSize"));
            if(listNotice==null){
                log.error("获取分页出错");
                result.put("error","获取分页失败");
            }
            if(listNotice.isEmpty()){
                log.warn("获取分页为空");
                result.put("warn","没有数据！！");
            }else{
                result.put("listObject",listNotice);
                result.put("size",listNotice.size());
                result.put("info","获取公告分页成功");
            }
        }catch (Exception e){
            log.error(e);
            result.put("error","获取分页失败");
        }
        result.put("model","Notice");
        return result;
    }

    @ResponseBody
    @RequestMapping("/tableNotice")
    public ModelAndView tableNotice(String page) {
        Map<String,Object> result=listNotice(page);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("system/table");
        mav.addObject("result",result);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/updateNotice")
    public Map<String, String> updateNotice(Notice notice) {
        Map<String, String> result = new HashMap<>();
        String updateDate = DateUtil.getFullTime();
        try{
            notice.setUpdateUserId("admin");
            notice.setUpdateDate(updateDate);
            if(noticeService.updateNotice(notice)==false){
                log.error("更新出错");
                result.put("msg","更新失败");
                return result;
            }
            result.put("msg","更新成功");
            result.put("id",notice.getId());
        }catch (Exception e){
            log.error(e);
            result.put("msg","更新失败");
        }finally {
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteNotice")
    public Map<String, String> deleteNotice(List listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(noticeService.deleteNotice(listStr)==false){
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
    @RequestMapping("/deleteListNotice")
    public String deleteListNotice(@RequestParam("listStr[]") List<String> listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(noticeService.deleteListNotice(listStr)==false){
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
