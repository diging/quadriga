package edu.asu.spring.quadriga.service;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;

public interface IQuadrigaRoleManager {

	public abstract IQuadrigaRoles getQuadrigaRole(String sUserId);
	
}
