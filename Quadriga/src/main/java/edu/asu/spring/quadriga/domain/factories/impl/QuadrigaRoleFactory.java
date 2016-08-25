package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.impl.QuadrigaRole;

/**
 * @description   : Factory class for creating Quadriga Roles.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
@Service
public class QuadrigaRoleFactory implements IQuadrigaRoleFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IQuadrigaRole createQuadrigaRoleObject() {
		return new QuadrigaRole();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IQuadrigaRole cloneQuadrigaRoleObject(IQuadrigaRole quadrigaRole) {
		IQuadrigaRole clone = createQuadrigaRoleObject();
		
		clone.setDBid(quadrigaRole.getDBid());
		clone.setId(quadrigaRole.getId());
		clone.setName(quadrigaRole.getName());
		clone.setDescription(quadrigaRole.getDescription());
		
		return clone;
	}

}
