package com.kelan.cob.roles.dao;

import java.util.List;
import com.kelan.cob.roles.entity.Roles;
import org.springframework.stereotype.Repository;


@Repository
public interface RolesDao {

	int addUserRole(Roles role);
	
	List<String> getUserRoles();
	
	String getUserRoleId(String roleVal);
}
