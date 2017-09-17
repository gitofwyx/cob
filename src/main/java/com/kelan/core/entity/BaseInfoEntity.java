package com.kelan.core.entity;


public abstract class BaseInfoEntity extends UUIdEntity {

	protected String createDate; // 创建时间
	protected String createUserId; // 创建者ID
	protected String updateDate; // 更新时间
	protected String updateUserId; // 更新者ID
	protected String deleteFlag; // 删除标识，0：有效；1：无效
	
	public String getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getCreateUserId() {
		return createUserId;
	}
	
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	
	public String getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	public String getUpdateUserId() {
		return updateUserId;
	}
	
	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}
	
	public String getDeleteFlag() {
		return deleteFlag;
	}
	
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	
}
