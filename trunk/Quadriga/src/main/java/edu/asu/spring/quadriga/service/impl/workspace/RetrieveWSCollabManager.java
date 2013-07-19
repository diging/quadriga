package edu.asu.spring.quadriga.service.impl.workspace;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workspace.IDBConnectionRetrieveWSCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.workspace.IRetrieveWSCollabManager;

//show collaborators for a workspace
//show non collaborators for a workspace
@Service
public class RetrieveWSCollabManager implements IRetrieveWSCollabManager {
	
	@Autowired
	@Qualifier("DBConnectionRetrieveWSCollabManagerBean")
	IDBConnectionRetrieveWSCollabManager dbConnect;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper;
	
	/**
	 * This method returns the collaborators list for a workspace
	 * @param workspaceId
	 * @return List<ICollaborator>
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public List<ICollaborator> getWorkspaceCollaborators(String workspaceId) throws QuadrigaStorageException
	{
		List<ICollaborator> collaboratorList;
		
		//retrieve the collaborators associated with project
		collaboratorList = dbConnect.getWorkspaceCollaborators(workspaceId);
		
		//map the collaborators to UI XML values
		for (ICollaborator collaborator : collaboratorList) 
		{
			for (ICollaboratorRole collaboratorRole : collaborator.getCollaboratorRoles()) {
				roleMapper.fillProjectCollaboratorRole(collaboratorRole);
			}
		}
		
		return collaboratorList;
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
	public List<IUser> getWorkspaceNonCollaborators(String workspaceId) throws QuadrigaStorageException
	{
		List<IUser> nonCollaboratorUser;
		
		//retrieve the non collaborators
		nonCollaboratorUser = dbConnect.getWorkspaceNonCollaborators(workspaceId);

		return nonCollaboratorUser;
	}
}
