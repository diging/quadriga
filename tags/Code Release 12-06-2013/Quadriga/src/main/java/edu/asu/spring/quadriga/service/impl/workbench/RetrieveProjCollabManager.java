package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IRetrieveProjectCollaboratorManagerDAO;
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
	private ICollaboratorRoleManager roleMapper;
	
	@Autowired
	private IRetrieveProjectCollaboratorManagerDAO projectCollaboratorDAO;
	
	@Override
	@Transactional
	public List<IUser> getProjectNonCollaborators(String projectid) throws QuadrigaStorageException
	{
		List<IUser> nonCollaborators;
		
		nonCollaborators = projectCollaboratorDAO.getProjectNonCollaborators(projectid);
		
		return nonCollaborators;
	}
	
	
	@Override
	@Transactional
	public List<ICollaborator> getProjectCollaborators(String projectId) throws QuadrigaStorageException
	{
		List<ICollaborator> collaboratorList;
		//retrieve the collaborators associated with project
		collaboratorList = projectCollaboratorDAO.getProjectCollaborators(projectId);

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
