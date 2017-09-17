package com.kelan.cob.system.service.impl;

import com.kelan.cob.system.dao.Side_menuDao;
import com.kelan.cob.system.entity.Side_menu;
import com.kelan.cob.system.service.Side_menuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
@Service
public class Side_menuServiceImpl implements Side_menuService {

    @Autowired
    private Side_menuDao dao;


    @Override
    public boolean addSide_menu(Side_menu side_menu) {
        return dao.addSide_menu(side_menu)==1?true:false;
    }

    @Override
    public List<Side_menu> listSide_menu(String model) {
        return dao.listSide_menu(model);
    }
}
