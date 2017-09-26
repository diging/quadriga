package edu.asu.spring.quadriga.web.workspace.backing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

@Service
public class ModifyWorkspaceFormManager 
{

	@Autowired
	IListWSManager wsManager;
    
	/**
	 * This method retrieves the active workspace list for the given
	 * project and user.
	 * @param projectId
	 * @param userName
	 * @return List<ModifyWorkspace> - list of workspace objects.
	 * @throws QuadrigaStorageException
	 */
	public List<ModifyWorkspace> getActiveWorkspaceList(String projectId,String userName) throws QuadrigaStorageException
	{
		List<ModifyWorkspace> modifyWorkspaceList = new ArrayList<ModifyWorkspace>();
		List<IWorkspace> workspaceList = wsManager.listActiveWorkspace(projectId,userName);
		
		for(IWorkspace workspace : workspaceList)
		{
		    ModifyWorkspace modifyWorkspace = new ModifyWorkspace();
			modifyWorkspace.setId(workspace.getWorkspaceId());
			modifyWorkspace.setName(workspace.getWorkspaceName());
			modifyWorkspace.setDescription(workspace.getDescription());
			modifyWorkspaceList.add(modifyWorkspace);
		}
		
		return modifyWorkspaceList;
	}
	
	/**
	 * This method retrieves all the deactivated workspaces associated with
	 * the given project.
	 * @param projectId
	 * @param userName
	 * @return List<ModifyWorkspace> - list of workspace objects.
	 * @throws QuadrigaStorageException
	 */
	public List<ModifyWorkspace> getDeactivatedWorkspaceList(String projectId,String userName) throws QuadrigaStorageException
	{
		List<IWorkspace> workspaceList;
		List<ModifyWorkspace> modifyWorkspaceList;
		ModifyWorkspace modifyWorkspace;
		
		modifyWorkspaceList = new ArrayList<ModifyWorkspace>();
		
		workspaceList =  wsManager.listDeactivatedWorkspace(projectId,userName);
		
		if(workspaceList != null)
		{
			for(IWorkspace workspace : workspaceList)
			{
				modifyWorkspace = new ModifyWorkspace();
				modifyWorkspace.setId(workspace.getWorkspaceId());
				modifyWorkspace.setName(workspace.getWorkspaceName());
				modifyWorkspace.setDescription(workspace.getDescription());
				modifyWorkspaceList.add(modifyWorkspace);
			}			
		}
		return modifyWorkspaceList;
	}
	
	/**
	 * This method retrieves the archived workspaces for given
	 * project and user.
	 * @param projectId
	 * @param userName
	 * @return List<ModifyWorkspace> - list of workspace objects.
	 * @throws QuadrigaStorageException
	 */
	public List<ModifyWorkspace> getArchivedWorkspaceList(String projectId,String userName) throws QuadrigaStorageException
	{
		List<IWorkspace> workspaceList;
		List<ModifyWorkspace> modifyWorkspaceList;
		ModifyWorkspace modifyWorkspace;
		
		modifyWorkspaceList = new ArrayList<ModifyWorkspace>();
		
		workspaceList =  wsManager.listArchivedWorkspace(projectId,userName);
		
		if(workspaceList == null)
		{
			return modifyWorkspaceList;
		}
		
		for(IWorkspace workspace : workspaceList)
		{
			modifyWorkspace = new ModifyWorkspace();
			modifyWorkspace.setId(workspace.getWorkspaceId());
			modifyWorkspace.setName(workspace.getWorkspaceName());
			modifyWorkspace.setDescription(workspace.getDescription());
			modifyWorkspaceList.add(modifyWorkspace);
		}
		
		return modifyWorkspaceList;
	}
}
