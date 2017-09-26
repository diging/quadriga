package edu.asu.spring.quadriga.mapper.workspace.impl;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectShallowMapper;
import edu.asu.spring.quadriga.mapper.workspace.IBaseWorkspaceMapper;

public class BaseWorkspaceMapper implements IBaseWorkspaceMapper {
    
    @Autowired IProjectShallowMapper projectShallowMapper;


    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.workspace.impl.IBaseWorkspaceMapper#getProjectWorkspaceOfWorkspace(edu.asu.spring.quadriga.domain.workspace.IWorkSpace, edu.asu.spring.quadriga.dto.WorkspaceDTO)
     */
    protected IProject getProjectWorkspaceOfWorkspace(IWorkspace workspace, WorkspaceDTO workspaceDTO) throws QuadrigaStorageException {
    	ProjectDTO projectDTO = workspaceDTO.getProjectWorkspaceDTO().getProjectDTO();
    	return projectShallowMapper.getProjectDetails(projectDTO);
    }

}