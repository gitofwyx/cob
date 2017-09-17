package com.kelan.cob.pic.controller;

import com.kelan.cob.pic.entity.CobPic;
import com.kelan.cob.pic.service.CobPicService;
import com.kelan.core.file.BaseController;
import com.kelan.core.file.ImageInfo;
import com.kelan.core.util.DateUtil;
import com.kelan.core.util.UUIdUtil;
import com.kelan.core.util.InetAddressUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/15.
 */
@Controller
@RequestMapping("/CobPic")
public class CobPicController extends BaseController {

    @Autowired
    private CobPicService cobPicService;



    private static Logger log = Logger.getLogger(CobPicController.class);

    private String rootDIr = "http://" + InetAddressUtil.IP + ":8080";

    @ResponseBody
    @RequestMapping("/getCobPic")
    public String getCobPic(String id) {
        Map<String, Object> result = new HashMap<>();
        CobPic cobPic=null;
        try{
            cobPic=cobPicService.getCobPic(id);
            if(cobPic==null){
                log.error("获取出错");
                return null;
            }
        }catch (Exception e){
            log.error(e);
            return null;
        }
        return rootDIr+cobPic.getUrl();
    }

    @ResponseBody
    @RequestMapping(value ="/addMore_pic",method = RequestMethod.POST)
    public Map<String, Object> addMore_pic( MultipartHttpServletRequest request,String objId) {
        Map<String, Object> result = new HashMap<>();
        if(objId==null){
            log.info("上传图片失败");
            result.put("error","ID获取为空");
            return result;
        }
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        String realPath = request.getSession().getServletContext().getRealPath("");
        try{
            //多图上传
            Map<String, Object> uploadResult = uploadFileKey(request);
            if ("0".equals(uploadResult.get("isError"))) {
                @SuppressWarnings("unchecked")
                List<ImageInfo> nameList = (List<ImageInfo>) uploadResult.get("file");
                if(nameList.size()!=0&&nameList!=null){
                    result.put("picNum", nameList.size());
                    // 保存图片信息到数据库
                    for (int i = 0; i < nameList.size(); i++) {
                        CobPic pic = new CobPic();
                        String picId = UUIdUtil.getUUID();
                        pic.setId(picId);
                        pic.setUrl(nameList.get(i).getImgPath());
                        pic.setMixUrl(nameList.get(i).getMiniImgPath());
                        pic.setCreateDate(createDate);
                        pic.setDeleteFlag("0");
                        pic.setObjId(objId);
                        boolean picResult = cobPicService.addCobPic(pic);
                        if (picResult) {
                            log.info("上传图片成功");
                        } else {
                            log.info("上传图片失败");
                            result.put("info","添加失败");
                        }
                    }
                 }
            }
            result.put("info","添加成功");
        }catch (Exception e){
            log.error(e);
            result.put("error","添加失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/deletePicById")
    public Map<String, Object> deletePicById(String id) {
        Map<String, Object> result = new HashMap<>();
        try{
           boolean picResult=cobPicService.deletePicById(id);
            if (picResult) {
                log.info("删除图片成功");
                result.put("picResult","删除成功");
            } else {
                log.info("删除图片失败");
                result.put("error","删除失败");
                return result;
            }
        }catch (Exception e){
            log.error(e);
            result.put("error","删除失败");
        }
        return result;
    }
}
