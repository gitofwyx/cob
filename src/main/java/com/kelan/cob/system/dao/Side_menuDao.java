package com.kelan.cob.system.dao;

import com.kelan.cob.system.entity.Side_menu;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface Side_menuDao {

	int addSide_menu(Side_menu side_menu);
	
	List<Side_menu> listSide_menu(String model);

}
