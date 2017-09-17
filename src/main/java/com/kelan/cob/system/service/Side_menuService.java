package com.kelan.cob.system.service;

import com.kelan.cob.system.entity.Side_menu;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15.
 */
public interface Side_menuService {

    boolean addSide_menu(Side_menu side_menu);

    List<Side_menu> listSide_menu(String model);
}
