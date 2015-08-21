package edu.asu.spring.quadriga.service.workbench.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectDAO;
import edu.asu.spring.quadriga.domain.factory.workbench.IProjectWorkspaceFactory;
import edu.asu.spring.quadriga.domain.proxy.WorkSpaceProxy;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.ProjectDTO;
import edu.asu.spring.quadriga.dto.ProjectWorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectWorkspaceShallowMapper;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDeepMapper;

@Service
public class ProjectWorkspaceShallowMapper implements
IProjectWorkspaceShallowMapper {
	@Autowired
	private IRetrieveProjectDAO dbConnect;

	@Autowired
	private IWorkspaceDeepMapper workspaceDeepMapper;
	
	@Autowired
	private IListWSManager wsManager;

	@Autowired
	private IProjectWorkspaceFactory projectWorkspaceFactory;

	@Override
	public List<IProjectWorkspace> getProjectWorkspaceList(IProject project,ProjectDTO projectDTO)
			throws QuadrigaStorageException {
		List<IProjectWorkspace> projectWorkspaceList= null;
		if(project != null){
			List<ProjectWorkspaceDTO> projectWorkspaceDTOList =	projectDTO.getProjectWorkspaceDTOList();
			if(projectWorkspaceDTOList != null){
				projectWorkspaceList = new ArrayList<IProjectWorkspace>();
				for(ProjectWorkspaceDTO projectWorkspaceDTO :  projectWorkspaceDTOList){

					IWorkSpace workspaceProxy = new WorkSpaceProxy(wsManager);
					workspaceProxy.setProjectWorkspace(workspaceDeepMapper.getProjectWorkspaceOfWorkspace(workspaceProxy ,projectWorkspaceDTO.getWorkspaceDTO()));
					workspaceProxy.setWorkspaceId(projectWorkspaceDTO.getWorkspaceDTO().getWorkspaceid());
					workspaceProxy.setWorkspaceName(projectWorkspaceDTO.getWorkspaceDTO().getWorkspacename());
					workspaceProxy.setDescription(projectWorkspaceDTO.getWorkspaceDTO().getDescription());
					workspaceProxy.setCreatedBy(projectWorkspaceDTO.getWorkspaceDTO().getCreatedby());
					workspaceProxy.setCreatedDate(projectWorkspaceDTO.getWorkspaceDTO().getCreateddate());
					workspaceProxy.setUpdatedBy(projectWorkspaceDTO.getWorkspaceDTO().getUpdatedby());
					workspaceProxy.setUpdatedDate(projectWorkspaceDTO.getWorkspaceDTO().getUpdateddate());
					IProjectWorkspace projectWorkspace = projectWorkspaceFactory.createProjectWorkspaceObject();
					projectWorkspace.setProject(project);	
					projectWorkspace.setWorkspace(workspaceProxy);
					projectWorkspace.setCreatedBy(projectWorkspaceDTO.getCreatedby());
					projectWorkspace.setCreatedDate(projectWorkspaceDTO.getCreateddate());
					projectWorkspace.setUpdatedBy(projectWorkspaceDTO.getUpdatedby());
					projectWorkspace.setUpdatedDate(projectWorkspaceDTO.getUpdateddate());
					projectWorkspaceList.add(projectWorkspace);
				}

			}
		}

		return projectWorkspaceList;
	}

}
