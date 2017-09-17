package com.kelan.cob.map.controller;

import com.kelan.cob.map.entity.CobMap;
import com.kelan.cob.map.service.CobMapService;
import com.kelan.core.file.BaseController;
import com.kelan.core.util.DateUtil;
import com.kelan.core.util.UUIdUtil;
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
@RequestMapping(value = "/CobMap")
public class CobMapController extends BaseController {

    @Autowired
    private CobMapService cobMapService;

    private static Logger log = Logger.getLogger(CobMapController.class);

    @RequestMapping("/system")
    public ModelAndView  cobMap() {

        return new ModelAndView("/system/cobMap", "key", "智慧党建");
        //return "system/index";
    }

    @ResponseBody
    @RequestMapping("/addCob_address")
    public Map<String, Object> addCob_address(CobMap cobMap) {

        Map<String, Object> result = new HashMap<>();
        String createDate = DateUtil.getFullTime();
        String id = UUIdUtil.getUUID();
        try{
            cobMap.setId(id);
            cobMap.setCreateDate(createDate);
            cobMap.setUpdateDate(createDate);
            cobMap.setDeleteFlag("0");
            if(cobMapService.addCcbMap(cobMap)==false){
                log.error("添加出错");
                result.put("error","添加失败");
                return result;
            }
            result.put("info","添加成功");
            result.put("id",cobMap.getId());
        }catch (Exception e){
            log.error(e);
            result.put("error","添加失败");
        }
        return result;
    }

    @ResponseBody
    @RequestMapping("/listCobMap")
    public Map<String, Object> listCobMap(String page) {
        Map<String, Object> result = new HashMap<>();
        try{
            Map<String, Integer> pageMap = PageDeal.requestPage(page);
            if(pageMap==null){
                log.error("获取分页错误");
                result.put("error","获取分页错误");
                return result;
            }
            List<CobMap> listCobMap=cobMapService.listCcbMap(pageMap.get("pageStart"),pageMap.get("pageSize"));
            if(listCobMap==null){
                log.error("获取分页出错");
                result.put("error","获取分页失败");
            }
            if(listCobMap.isEmpty()){
                log.warn("获取分页为空");
                result.put("warn","没有数据！！");
            }else{
                result.put("listObject",listCobMap);
                result.put("size",listCobMap.size());
                result.put("info","获取地图坐标分页成功");
            }
        }catch (Exception e){
            log.error(e);
            result.put("error","获取分页失败");
        }
        result.put("model","CobMap");
        return result;
    }
}
