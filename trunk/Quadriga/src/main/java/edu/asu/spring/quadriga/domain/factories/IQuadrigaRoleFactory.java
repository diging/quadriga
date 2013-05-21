package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;

public interface IQuadrigaRoleFactory 
{
	
	public abstract IQuadrigaRoles  createQuadrigaRoleObject();
	
	public abstract IQuadrigaRoles cloneQuadrigaRoleObject(IQuadrigaRoles quadrigaRole);
	

}
