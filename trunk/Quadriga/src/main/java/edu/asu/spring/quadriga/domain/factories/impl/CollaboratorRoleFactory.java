package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.implementation.CollaboratorRole;

@Service
public class CollaboratorRoleFactory implements ICollaboratorRoleFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICollaboratorRole createCollaboratorRoleObject() {
		return new CollaboratorRole();
	}

}
