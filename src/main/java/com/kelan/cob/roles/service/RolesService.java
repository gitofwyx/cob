package com.kelan.cob.roles.service;

import com.kelan.cob.roles.entity.Roles;

import java.util.List;



public interface RolesService {

	boolean addUserRole(Roles role);
	
	List<String> getUserRoles();
	
	String getUserRoleId(String roleVal);
}
