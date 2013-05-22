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
	 * Return the corresponding QuadrigaRole for a given QuadrigaRoleId.
	 * 
	 * @param sQuadrigaRoleDBId		The role id fetched from the Quadriga database.
	 * @return 						QuadrigaRole object corresponding to the role id.
	 */
	public IQuadrigaRole getQuadrigaRole(String sQuadrigaRoleDBId) {
		for(IQuadrigaRole role: quadrigaRoles)
		{
			if(role.getDBid().equals(sQuadrigaRoleDBId))
				return role;
		}
		return null;
	}
	
	/**
	 * Returns the list of QuadrigaRoles used in the application context.
	 * 
	 * @return 			List of QuadrigaRoles
	 */
	public List<IQuadrigaRole> getQuadrigaRoles() {
		return this.quadrigaRoles;
	}
	
	/**
	 *  
	 * @param quadrigaRoles List of QuadrigaRoles
	 */
	public void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles) {
		this.quadrigaRoles = quadrigaRoles;
	}

}
