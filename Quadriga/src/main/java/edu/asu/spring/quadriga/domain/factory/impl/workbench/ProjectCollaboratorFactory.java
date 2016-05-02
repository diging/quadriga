package edu.asu.spring.quadriga.domain.factory.impl.workbench;

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProjectCollaborator cloneProjectCollaboratorObject(
			IProjectCollaborator projectCollaborator)
	{
		IProjectCollaborator clone = new ProjectCollaborator();
		clone.setProject(projectCollaborator.getProject());
		clone.setCollaborator(projectCollaborator.getCollaborator());
		clone.setCreatedBy(projectCollaborator.getCreatedBy());
		clone.setCreatedDate(projectCollaborator.getCreatedDate());
		clone.setUpdatedBy(projectCollaborator.getUpdatedBy());
		clone.setUpdateDate(projectCollaborator.getUpdatedDate());
		return clone;
	}

}
