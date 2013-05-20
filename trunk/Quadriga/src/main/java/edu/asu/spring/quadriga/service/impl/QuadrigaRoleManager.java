package edu.asu.spring.quadriga.service.impl;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

@Service
public class QuadrigaRoleManager implements IQuadrigaRoleManager{

	@Autowired
	private List<IQuadrigaRoles> quadrigaRoles;
	
	public IQuadrigaRoles getQuadrigaRole(String sQuadrigaRoleDBId) {
		for(IQuadrigaRoles role: quadrigaRoles)
		{
			if(role.getDBid().equals(sQuadrigaRoleDBId))
				return role;
		}
		return null;
	}

	public List<IQuadrigaRoles> getQuadrigaRoles() {
		return this.quadrigaRoles;
	}
	
	public void setQuadrigaRoles(List<IQuadrigaRoles> quadrigaRoles) {
		this.quadrigaRoles = quadrigaRoles;
	}

}
