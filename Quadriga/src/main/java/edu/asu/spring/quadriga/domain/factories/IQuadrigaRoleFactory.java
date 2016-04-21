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
	/**
	 * Factory method to create a QuadrigaRole object
	 * @return IQuadrigaRole
	 * @author kiran batna
	 */
	public abstract IQuadrigaRole  createQuadrigaRoleObject();
	

	/**
	 * Method for cloning a {@link IQuadrigaRole} object. Note that this will produce a shallow clone, meaning that the QuadrigaRoles
	 * will simply be put into a new list for the clone, but the QuadrigaRole objects themselves will be the same.
	 * @param QuadrigaRole to be cloned
	 * @return a clone of the given QuadrigaRole object that contains the exact same information as the original object.
	 * @author kiran batna
	 */
	public abstract IQuadrigaRole cloneQuadrigaRoleObject(IQuadrigaRole quadrigaRole);
	

}
