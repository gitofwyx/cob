package com.kelan.cob.roles.service.impl;

import java.util.List;

import com.kelan.cob.roles.dao.RolesDao;
import com.kelan.cob.roles.entity.Roles;
import com.kelan.cob.roles.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class RolesServiceImpl implements RolesService {
	
	@Autowired
	RolesDao dao;

	@Override
	public boolean addUserRole(Roles role) {
		return dao.addUserRole(role)==1?true:false;
	}

	@Override
	public List<String> getUserRoles() {
		return dao.getUserRoles();
		
	}
	@Override
	public String getUserRoleId(String roleVal) {
		return dao.getUserRoleId(roleVal);
	}
		
}
