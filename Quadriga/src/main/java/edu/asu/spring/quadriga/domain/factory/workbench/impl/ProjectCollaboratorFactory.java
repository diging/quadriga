package edu.asu.spring.quadriga.domain.factory.workbench.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IProjectCollaboratorFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.ProjectCollaborator;
import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;

/**
 * Factory class to create {@link IProjectCollaborator} object of domain class type {@link ProjectCollaborator}
 * @author Lohith Dwaraka
 *
 */
@Service
public class ProjectCollaboratorFactory implements IProjectCollaboratorFactory {

	/**
	 * {@inheritDoc}
	*/
	@Override
	public IProjectCollaborator createProjectCollaboratorObject() {
		return new ProjectCollaborator();
	}

}
