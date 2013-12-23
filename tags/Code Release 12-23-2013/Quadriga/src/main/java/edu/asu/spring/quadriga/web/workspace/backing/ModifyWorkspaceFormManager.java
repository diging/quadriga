package edu.asu.spring.quadriga.web.workspace.backing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

@Service
public class ModifyWorkspaceFormManager 
{

	@Autowired
	IListWSManager wsManager;
    
	public List<ModifyWorkspace> getActiveWorkspaceList(String projectId,String userName) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		List<ModifyWorkspace> modifyWorkspaceList;
		ModifyWorkspace modifyWorkspace;
		
		modifyWorkspaceList = new ArrayList<ModifyWorkspace>();
		workspaceList = wsManager.listActiveWorkspace(projectId,userName);
		
		for(IWorkSpace workspace : workspaceList)
		{
			modifyWorkspace = new ModifyWorkspace();
			modifyWorkspace.setId(workspace.getId());
			modifyWorkspace.setName(workspace.getName());
			modifyWorkspace.setDescription(workspace.getDescription());
			modifyWorkspaceList.add(modifyWorkspace);
		}
		
		return modifyWorkspaceList;
	}
	
	public List<ModifyWorkspace> getDeactivatedWorkspaceList(String projectId,String userName) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		List<ModifyWorkspace> modifyWorkspaceList;
		ModifyWorkspace modifyWorkspace;
		
		modifyWorkspaceList = new ArrayList<ModifyWorkspace>();
		
		workspaceList =  wsManager.listDeactivatedWorkspace(projectId,userName);
		
		if(workspaceList != null)
		{
			for(IWorkSpace workspace : workspaceList)
			{
				modifyWorkspace = new ModifyWorkspace();
				modifyWorkspace.setId(workspace.getId());
				modifyWorkspace.setName(workspace.getName());
				modifyWorkspace.setDescription(workspace.getDescription());
				modifyWorkspaceList.add(modifyWorkspace);
			}			
		}
		return modifyWorkspaceList;
	}
	
	public List<ModifyWorkspace> getArchivedWorkspaceList(String projectId,String userName) throws QuadrigaStorageException
	{
		List<IWorkSpace> workspaceList;
		List<ModifyWorkspace> modifyWorkspaceList;
		ModifyWorkspace modifyWorkspace;
		
		modifyWorkspaceList = new ArrayList<ModifyWorkspace>();
		
		workspaceList =  wsManager.listArchivedWorkspace(projectId,userName);;
		
		for(IWorkSpace workspace : workspaceList)
		{
			modifyWorkspace = new ModifyWorkspace();
			modifyWorkspace.setId(workspace.getId());
			modifyWorkspace.setName(workspace.getName());
			modifyWorkspace.setDescription(workspace.getDescription());
			modifyWorkspaceList.add(modifyWorkspace);
		}
		
		return modifyWorkspaceList;
	}
}
