package edu.asu.spring.quadriga.domain.factory.impl.workbench;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IProjectWorkspaceFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.ProjectWorkspace;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;

@Service
public class ProjectWorkspaceFactory implements IProjectWorkspaceFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProjectWorkspace createProjectWorkspaceObject() {
		return new ProjectWorkspace();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProjectWorkspace cloneProjectWorkspaceObject(
			IProjectWorkspace projectWorkspace) 
	{
		IProjectWorkspace clone = new ProjectWorkspace();
		clone.setProject(projectWorkspace.getProject());
		clone.setWorkspace(projectWorkspace.getWorkspace());
		clone.setCreatedBy(projectWorkspace.getCreatedBy());
		clone.setCreatedDate(projectWorkspace.getCreatedDate());
		clone.setUpdatedBy(projectWorkspace.getUpdatedBy());
		clone.setUpdatedDate(projectWorkspace.getUpdatedDate());
		return clone;
	}

}
