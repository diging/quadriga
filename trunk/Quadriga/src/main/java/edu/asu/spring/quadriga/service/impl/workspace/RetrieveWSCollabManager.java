package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionRetrieveWSCollabManager;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDeepMapper;

//show collaborators for a workspace
//show non collaborators for a workspace
@Service
public class RetrieveWSCollabManager implements IRetrieveWSCollabManager {

	@Autowired
	IDBConnectionRetrieveWSCollabManager dbConnect;

	@Autowired
	private ICollaboratorRoleManager roleMapper;

	@Autowired
	private IWorkspaceDeepMapper workspaceDeepMapper;

	/**
	 * This method returns the collaborators list for a workspace
	 * @param workspaceId
	 * @return List<ICollaborator>
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public List<IWorkspaceCollaborator> getWorkspaceCollaborators(String workspaceId) throws QuadrigaStorageException
	{
		List<IWorkspaceCollaborator> workspaceCollaboratorList = null;
		ICollaboratorRole role;
		List<ICollaboratorRole> roleList;

		IWorkSpace workspace =workspaceDeepMapper.getWorkSpaceDetails(workspaceId);
		//retrieve the collaborators associated with project
		if(workspace != null){
			workspaceCollaboratorList = workspace.getWorkspaceCollaborators();
		}

		//map the collaborators to UI XML values
		if(workspaceCollaboratorList != null){
			for (IWorkspaceCollaborator workspaceCollaborator : workspaceCollaboratorList) 
			{
				roleList = new ArrayList<ICollaboratorRole>();
				if(workspaceCollaborator.getCollaborator()!=null && workspaceCollaborator.getCollaborator().getCollaboratorRoles() != null){
					for (ICollaboratorRole collaboratorRole : workspaceCollaborator.getCollaborator().getCollaboratorRoles()) {
						role = roleMapper.getWSCollaboratorRoleByDBId(collaboratorRole.getRoleDBid());
						roleList.add(role);
					}
				}
				workspaceCollaborator.getCollaborator().setCollaboratorRoles(roleList);
			}
		}
		return workspaceCollaboratorList;
	}

	/**
	 * This method retrieves the users who are not collaborators to the 
	 * workspace
	 * @param workspaceId
	 * @return  List<IUser>
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	@Transactional
	public List<IUser> getWorkspaceNonCollaborators(String workspaceId) throws QuadrigaStorageException
	{
		List<IUser> nonCollaboratorUser;

		//retrieve the non collaborators
		nonCollaboratorUser = dbConnect.getWorkspaceNonCollaborators(workspaceId);

		return nonCollaboratorUser;
	}
}
