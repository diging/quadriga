package edu.asu.spring.quadriga.service.impl.conceptcollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCCollaboratorManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.ICCCollaboratorManager;

@Service
public class CCCollaboratorManager implements ICCCollaboratorManager 
{
	@Autowired
	@Qualifier("DBConnectionCCCollaboratorManagerBean")
	private IDBConnectionCCCollaboratorManager dbConnect;
	
	@Override
	public void addCollaborators(ICollaborator collaborator, String collectionid, String userName)
			throws QuadrigaStorageException 
	{
		dbConnect.addCollaboratorRequest(collaborator, collectionid, userName);
	}
	
	@Override
	public void deleteCollaborators(String userName, String collectionid) throws QuadrigaStorageException 
	{
		dbConnect.deleteCollaboratorRequest(userName, collectionid);
	}
	
	@Override
	public void  updateCollaboratorRequest(String collectionId,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
	{
		dbConnect.updateCollaboratorRequest(collectionId, collabUser, collaboratorRole, username);
	}


}
