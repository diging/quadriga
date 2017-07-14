package edu.asu.spring.quadriga.domain.factory.workbench.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IProjectFactory;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.impl.Project;

/**
 * Factory class to create Project object
 * @author kiran batna
 * 
 */
@Service
public class ProjectFactory implements IProjectFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProject createProjectObject() {
		
		return new Project();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProject cloneProjectObject(IProject project) 
	{
		IProject clone = new Project();
		clone.setProjectId(project.getProjectId());
		clone.setProjectName(project.getProjectId());
		clone.setProjectAccess(project.getProjectAccess());
		clone.setDescription(project.getDescription());
		clone.setUnixName(project.getUnixName());
		clone.setProjectCollaborators(project.getProjectCollaborators());
		clone.setConceptCollections(project.getConceptCollections());
		clone.setDictionaries(project.getDictionaries());
		clone.setWorkspaces(project.getWorkspaces());
		clone.setCreatedBy(project.getCreatedBy());
		clone.setCreatedDate(project.getCreatedDate());
		clone.setUpdatedBy(project.getUpdatedBy());
		clone.setUpdatedDate(project.getUpdatedDate());
		return clone;
	}
	
}
