package edu.asu.spring.quadriga.domain.factories.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.implementation.CollaboratorRole;

@Service
public class CollaboratorRoleFactory implements ICollaboratorRoleFactory {

	@Override
	public ICollaboratorRole createCollaboratorRoleObject() {
		return new CollaboratorRole();
	}

}
