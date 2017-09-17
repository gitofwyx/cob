package com.kelan.cob.roles.entity;

import com.kelan.core.entity.BaseInfoEntity;

public class Roles extends BaseInfoEntity {
	
	private String id;
	private String roleVal;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleVal() {
		return roleVal;
	}
	public void setRoleVal(String roleVal) {
		this.roleVal = roleVal;
	}
	
	
}
