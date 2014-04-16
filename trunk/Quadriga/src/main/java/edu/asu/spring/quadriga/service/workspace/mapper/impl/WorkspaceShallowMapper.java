package edu.asu.spring.quadriga.service.workspace.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.proxy.WorkSpaceProxy;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.dto.WorkspaceDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceShallowMapper;

@Service
public class WorkspaceShallowMapper implements IWorkspaceShallowMapper {

	@Autowired
	private IDBConnectionListWSManager dbConnect;
	
	@Autowired
	private IListWSManager wsManager;
	
	@Autowired
	private IProjectShallowMapper projectShallowMapper;
	
	@Autowired
	private IUserManager userManager;
	
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public List<IWorkSpace> getWorkSpaceList(String projectId) throws QuadrigaStorageException{
		List<WorkspaceDTO> workspaceDTOList = dbConnect.listWorkspaceDTO(projectId);
		List<IWorkSpace> workspaceList = null;
		if(workspaceDTOList != null){
			for(WorkspaceDTO workspaceDTO : workspaceDTOList){
				if(workspaceList == null){
					workspaceList = new ArrayList<IWorkSpace>();
				}
				IWorkSpace workspaceProxy = new WorkSpaceProxy(wsManager);
				workspaceProxy.setWorkspaceId(workspaceDTO.getWorkspaceid());
				workspaceProxy.setWorkspaceName(workspaceDTO.getWorkspacename());
				workspaceProxy.setDescription(workspaceDTO.getDescription());
				workspaceProxy.setOwner(userManager.getUserDetails(workspaceDTO.getWorkspaceowner().getUsername()));
				workspaceProxy.setCreatedBy(workspaceDTO.getCreatedby());
				workspaceProxy.setCreatedDate(workspaceDTO.getCreateddate());
				workspaceProxy.setUpdatedBy(workspaceDTO.getUpdatedby());
				workspaceProxy.setUpdatedDate(workspaceDTO.getUpdateddate());
				workspaceProxy.setProject(projectShallowMapper.getProjectDetails(projectId));
				workspaceList.add(workspaceProxy);
			}
		}
		
		return workspaceList;
	}
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	@Transactional
	public IWorkSpace getWorkSpaceDetails(String workspaceId) throws QuadrigaStorageException{
		WorkspaceDTO workspaceDTO = dbConnect.getWorkspaceDTO(workspaceId);
		IWorkSpace workspaceProxy = null;
		
		if(workspaceDTO != null){
			workspaceProxy = new WorkSpaceProxy(wsManager);
			workspaceProxy.setWorkspaceId(workspaceDTO.getWorkspaceid());
			workspaceProxy.setWorkspaceName(workspaceDTO.getWorkspacename());
			workspaceProxy.setDescription(workspaceDTO.getDescription());
			workspaceProxy.setOwner(userManager.getUserDetails(workspaceDTO.getWorkspaceowner().getUsername()));
			workspaceProxy.setCreatedBy(workspaceDTO.getCreatedby());
			workspaceProxy.setCreatedDate(workspaceDTO.getCreateddate());
			workspaceProxy.setUpdatedBy(workspaceDTO.getUpdatedby());
			workspaceProxy.setUpdatedDate(workspaceDTO.getUpdateddate());
			// TODO : there is a bug in WorkspaceDTO, it returns a List of ProjectWorkspaceDTO. W
			// We need only one ProjectWorkspaceDTO object
			//workspaceProxy.setProject();
		}
		
		return workspaceProxy;
	}
}
