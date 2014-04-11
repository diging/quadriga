package edu.asu.spring.quadriga.service.impl.workbench.mapper.impl;

import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectShallowMapper {

	public abstract List<IProject> getProjectList(String userName)
			throws QuadrigaStorageException;

}