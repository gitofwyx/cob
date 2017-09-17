package com.kelan.cob.member.controller;

import com.kelan.cob.member.entity.Member;
import com.kelan.cob.member.service.MemberService;
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
@RequestMapping(value = "/Member")
public class MemberController extends BaseController {

    private static Logger log = Logger.getLogger(MemberController.class);

    @Autowired
    private CobPicService cobPicService;

    @Autowired
    private MemberService memberService;

    private String rootDIr = "http://" + InetAddressUtil.IP + ":8080";

    @RequestMapping("/system")
    public ModelAndView member(String page,String level) {
        Map<String,Object>result=listMember(page);
        if(level!=null&&"system".equals(level)){
            return new ModelAndView("/system/index", "result", result);
        }else {
            return new ModelAndView("show/listPage", "result", result);
        }
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/addMember")
    public Map<String, Object> addMember(Member member, MultipartHttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String realPath = request.getSession().getServletContext().getRealPath("");
        try{
            member.setId(id);
            member.setCreateDate(createDate);
            member.setUpdateDate(createDate);
            member.setDeleteFlag("0");
            //多图上传
            Map<String, Object> uploadResult = uploadFileKey(request);
            if ("0".equals(uploadResult.get("isError"))) {
                @SuppressWarnings("unchecked")
                List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                if(nameList!=null&&nameList.size()!=0){
                    result.put("picNum", nameList.size());
                    // 保存图片信息到数据库
                    for (int i = 0; i < nameList.size(); i++) {
                        member.setCoverPic(nameList.get(i).getMiniImgPath());
                    }
                }
            }else{
                log.warn("未添加封面");
                result.put("warn","未添加封面");
            }
            if(memberService.addMember(member)==false){
                log.error("添加出错");
                result.put("error","添加失败");
                return result;
            }
            result.put("info","添加成功");
            result.put("id",member.getId());
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
            Member member=memberService.getMember(objId);
            if(member!=null){
                //多图上传
                Map<String, Object> uploadResult = uploadFileKey(request);
                if ("0".equals(uploadResult.get("isError"))) {
                    List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                    if(nameList.size()!=0&&nameList!=null){
                        result.put("picNum", nameList.size());
                        // 保存图片信息到数据库
                        for (int i = 0; i < nameList.size(); i++) {
                            member.setUpdateDate(updateDate);
                            member.setCoverPic(nameList.get(i).getMiniImgPath());
                            // pic.setObjId(url);
                            boolean picResult = memberService.updateMember(member);
                            if (picResult) {
                                log.info("上传头像成功");
                            } else {
                                log.info("上传头像失败");
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
    @RequestMapping("/getMember")
    public Map<String, Object> getMember(String id) {
        Map<String, Object> result = new HashMap<>();
        int picCount=cobPicService.getPicCount(id);
        try{
            Member member=memberService.getMember(id);
            //activity.setCoverPic(rootDIr+activity.getCoverPic());
            List<CobPic> listCobPic=cobPicService.listCobPic(1,picCount,id);
            if(member==null){
                log.error("获取出错");
                result.put("error","获取失败");
                return result;
            }
            result.put("Object",member);
            result.put("listCobPic",listCobPic);
            result.put("rootDIr",rootDIr);
            result.put("model","Member");
            result.put("info","获取成功");
        }catch (Exception e){
            log.error(e);
            result.put("error","获取失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listMember")
    public Map<String, Object> listMember(String page) {
        Map<String, Object> result = new HashMap<>();
        try{
            Map<String, Integer> pageMap = PageDeal.requestPage(page);
            if(pageMap==null){
                log.error("获取分页错误");
                result.put("error","获取分页错误");
                return result;
            }
           List<Member> listMember=memberService.listMember(pageMap.get("pageStart"),pageMap.get("pageSize"));
            if(listMember==null){
                log.error("获取分页出错");
                result.put("error","获取分页失败");
            }
            if(listMember.isEmpty()){
                log.warn("获取分页为空");
                result.put("warn","没有数据！！");
            }else{
                result.put("listObject",listMember);
                result.put("size",listMember.size());
                result.put("info","获取党员分页成功");
            }
        }catch (Exception e){
            log.error(e);
            result.put("error","获取分页失败");
        }
        result.put("model","Member");
        return result;
    }

    @ResponseBody
    @RequestMapping("/tableMember")
    public ModelAndView tableMember(String page) {
        Map<String,Object> result=listMember(page);
        ModelAndView mav = new ModelAndView();
        mav.setViewName("system/member_table");
        mav.addObject("result",result);
        return mav;
    }

    @ResponseBody
    @RequestMapping("/updateMember")
    public Map<String, String> updateMember(Member member) {
        Map<String, String> result = new HashMap<>();
        String updateDate = DateUtil.getFullTime();
        try{
            member.setUpdateUserId("admin");
            member.setUpdateDate(updateDate);
            if(memberService.updateMember(member)==false){
                log.error("更新出错");
                result.put("error","更新失败");
                return result;
            }
            result.put("info","更新成功");
            result.put("id",member.getId());
        }catch (Exception e){
            log.error(e);
            result.put("error","更新失败");
        }finally {
            return result;
        }
    }

    @ResponseBody
    @RequestMapping("/deleteMember")
    public Map<String, String> deleteMember(List listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(memberService.deleteMember(listStr)==false){
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
    @RequestMapping("/deleteListMember")
    public String deleteListMember(@RequestParam("listStr[]") List<String> listStr) {
        Map<String, String> result = new HashMap<>();
        try{
            if(memberService.deleteListMember(listStr)==false){
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
