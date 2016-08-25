package edu.asu.spring.quadriga.mapper.workspace.impl;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.domain.impl.workbench.ProjectWorkspace;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.workbench.IProjectShallowMapper;
import edu.asu.spring.quadriga.mapper.workspace.IBaseWorkspaceMapper;

public class BaseWorkspaceMapper implements IBaseWorkspaceMapper {
    
    @Autowired IProjectShallowMapper projectShallowMapper;


    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.mapper.workspace.impl.IBaseWorkspaceMapper#getProjectWorkspaceOfWorkspace(edu.asu.spring.quadriga.domain.workspace.IWorkSpace, edu.asu.spring.quadriga.dto.WorkspaceDTO)
     */
    protected IProjectWorkspace getProjectWorkspaceOfWorkspace(IWorkSpace workspace, WorkspaceDTO workspaceDTO) throws QuadrigaStorageException {
    	IProjectWorkspace projectWorkspace = null;
    
    	ProjectWorkspaceDTO projectWorkspaceDTO = workspaceDTO.getProjectWorkspaceDTO();
    
    	projectWorkspace = new ProjectWorkspace();;
    	projectWorkspace.setWorkspace(workspace);
    
    	ProjectDTO projectDTO = projectWorkspaceDTO.getProjectDTO();
    	IProject project = projectShallowMapper.getProjectDetails(projectDTO);
    	projectWorkspace.setProject(project);
    
    	projectWorkspace.setCreatedBy(projectWorkspaceDTO.getCreatedby());
    	projectWorkspace.setCreatedDate(projectWorkspaceDTO.getCreateddate());
    	projectWorkspace.setUpdatedBy(projectWorkspaceDTO.getUpdatedby());
    	projectWorkspace.setUpdatedDate(projectWorkspaceDTO.getUpdateddate());
    
    	return projectWorkspace;
    
    }

}