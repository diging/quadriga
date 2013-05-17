package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;

public interface IQuadrigaRoleManager {

	public abstract void setQuadrigaRoles(List<IQuadrigaRoles> quadrigaRoles);
	
	public abstract IQuadrigaRoles getQuadrigaRole(String sUserId);
	
}
