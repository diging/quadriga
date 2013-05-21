package edu.asu.spring.quadriga.domain.factories.impl;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRoles;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.implementation.QuadrigaRole;

/**
 * @description   : Factory class for creating Quadriga Roles.
 * 
 * @author        : Kiran
 *
 */
@Service
public class QuadrigaRoleFactory implements IQuadrigaRoleFactory {

	@Override
	public IQuadrigaRoles createQuadrigaRoleObject() {
		// TODO Auto-generated method stub
		return new QuadrigaRole();
	}

	@Override
	public IQuadrigaRoles cloneQuadrigaRoleObject(IQuadrigaRoles quadrigaRole) {
		// TODO Auto-generated method stub
		throw new NotImplementedException("Clone Quadriga Role is not yet implemented.");
	}

}
