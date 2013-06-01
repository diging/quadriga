package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.implementation.QuadrigaRole;

/**
 * @description   : Factory class for creating Quadriga Roles.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
@Service
public class QuadrigaRoleFactory implements IQuadrigaRoleFactory {

	@Override
	public IQuadrigaRole createQuadrigaRoleObject() {
		// TODO Auto-generated method stub
		return new QuadrigaRole();
	}

	@Override
	public IQuadrigaRole cloneQuadrigaRoleObject(IQuadrigaRole quadrigaRole) {
		// TODO Auto-generated method stub
		IQuadrigaRole clone = createQuadrigaRoleObject();
		
		clone.setDBid(quadrigaRole.getDBid());
		clone.setId(quadrigaRole.getId());
		clone.setName(quadrigaRole.getName());
		clone.setDescription(quadrigaRole.getDescription());
		
		return clone;
	}

}
