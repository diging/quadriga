package edu.asu.spring.quadriga.service.workspace.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IWorkspaceShallowMapper {

	public abstract List<IWorkSpace> getWorkSpaceList(String projectId)
			throws QuadrigaStorageException;

}