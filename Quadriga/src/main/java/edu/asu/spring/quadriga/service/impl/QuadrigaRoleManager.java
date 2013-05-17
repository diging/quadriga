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
	
	public IQuadrigaRoles getQuadrigaRole(String sQuadrigaRoleId) {
		for(IQuadrigaRoles role: quadrigaRoles)
		{
			if(role.getId().equals(sQuadrigaRoleId))
				return role;
		}
		return null;
	}

	public void setQuadrigaRoles(List<IQuadrigaRoles> quadrigaRoles) {
		this.quadrigaRoles = quadrigaRoles;
	}

}
