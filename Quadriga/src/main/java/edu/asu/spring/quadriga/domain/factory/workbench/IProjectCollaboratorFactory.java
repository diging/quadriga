package edu.asu.spring.quadriga.domain.factory.workbench;

import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;
import edu.asu.spring.quadriga.domain.workbench.impl.ProjectCollaborator;

public interface IProjectCollaboratorFactory {

	/**
	 * Creates {@link ProjectCollaborator} object
	 * @return							Returns the {@link IProjectCollaborator} object
	 */
	public abstract IProjectCollaborator createProjectCollaboratorObject();
	
	public abstract IProjectCollaborator cloneProjectCollaboratorObject(
			IProjectCollaborator projectCollaborator);

}