package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;

/**
 * @description   : Factory interface for Quadriga Role factory
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IQuadrigaRoleFactory 
{
	
	public abstract IQuadrigaRole  createQuadrigaRoleObject();
	
	public abstract IQuadrigaRole cloneQuadrigaRoleObject(IQuadrigaRole quadrigaRole);
	

}
