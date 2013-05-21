package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;

/**
 * @description   : Factory interface for Quadriga Role factory
 * 
 * @author        : Kiran
 *
 */
public interface IQuadrigaRoleFactory 
{
	
	public abstract IQuadrigaRoles  createQuadrigaRoleObject();
	
	public abstract IQuadrigaRoles cloneQuadrigaRoleObject(IQuadrigaRoles quadrigaRole);
	

}
