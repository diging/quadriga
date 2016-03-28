package edu.asu.spring.quadriga.dao.workbench;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectWorkspaceDAO extends IBaseDAO<ProjectWorkspaceDTO> {

	String getCorrespondingProjectID(String workspaceId)
			throws QuadrigaStorageException;
}