package edu.asu.spring.quadriga.service.impl.workbench.mapper.impl;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectDeepMapper {

	public abstract IProject getProjectDetails(String projectId)
			throws QuadrigaStorageException;

}