
package edu.asu.spring.quadriga.service.workbench.mapper;

import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectWorkspaceShallowMapper {
	
	public List<IProjectWorkspace> getProjectWorkspaceList(IProject project,ProjectDTO projectDTO) throws QuadrigaStorageException;

}
