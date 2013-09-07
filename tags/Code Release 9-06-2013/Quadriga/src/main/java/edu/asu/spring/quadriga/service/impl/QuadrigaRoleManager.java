package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

/**
 * QuadrigaRoleManager is used to read the user roles from context file 
 * and create list of QuadrigaRole objects.
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Service
public class QuadrigaRoleManager implements IQuadrigaRoleManager{

	/**List of QuadrigaRole objects which will store various user roles.*/
	@Autowired
	private List<IQuadrigaRole> quadrigaRoles;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IQuadrigaRole getQuadrigaRole(String sQuadrigaRoleDBId) {
		for(IQuadrigaRole role: quadrigaRoles)
		{
			if(role.getDBid().equals(sQuadrigaRoleDBId))
				return role;
		}
		return null;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQuadrigaRoleDBId(String sQuadrigaRoleId)
	{
		for(IQuadrigaRole role:quadrigaRoles)
		{
			if(role.getId().equals(sQuadrigaRoleId))
				return role.getDBid();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IQuadrigaRole> getQuadrigaRoles() {
		return this.quadrigaRoles;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles) {
		this.quadrigaRoles = quadrigaRoles;
	}

}
