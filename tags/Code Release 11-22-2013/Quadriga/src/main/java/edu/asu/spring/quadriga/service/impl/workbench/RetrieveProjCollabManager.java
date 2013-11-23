package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;

//show collaborators for a project
//show non collaborators for a project
@Service
public class RetrieveProjCollabManager implements IRetrieveProjCollabManager 
{

	@Autowired
	@Qualifier("DBConnectionRetrieveProjCollabManagerBean")
	private IDBConnectionRetrieveProjCollabManager dbConnect;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper;
	
	
	
	@Override
	public List<IUser> getProjectNonCollaborators(String projectid) throws QuadrigaStorageException
	{
		List<IUser> nonCollaborators;
		
		nonCollaborators = dbConnect.getProjectNonCollaborators(projectid);
		
		return nonCollaborators;
	}
	
	
	@Override
	public List<ICollaborator> getProjectCollaborators(String projectId) throws QuadrigaStorageException
	{
		List<ICollaborator> collaboratorList;
		//retrieve the collaborators associated with project
		collaboratorList = dbConnect.getProjectCollaborators(projectId);

		//map the collaborators to UI XML values
		for (ICollaborator collaborator : collaboratorList) 
		{
			for (ICollaboratorRole collaboratorRole : collaborator.getCollaboratorRoles()) {
				roleMapper.fillProjectCollaboratorRole(collaboratorRole);
			}
		}
		return collaboratorList;
	}
}
