package edu.asu.spring.quadriga.service.impl.conceptcollection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCCollaboratorManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.ICCCollaboratorManager;

@Service
public class CCCollaboratorManager implements ICCCollaboratorManager 
{
	@Autowired
	private IDBConnectionCCCollaboratorManager dbConnect;
	
	@Override
	@Transactional
	public void addCollaborators(ICollaborator collaborator, String collectionid, String userName)
			throws QuadrigaStorageException 
	{
		dbConnect.addCollaboratorRequest(collaborator, collectionid, userName);
	}
	
	@Override
	@Transactional
	public void deleteCollaborators(String userName, String collectionid) throws QuadrigaStorageException 
	{
		dbConnect.deleteCollaboratorRequest(userName, collectionid);
	}
	
	@Override
	@Transactional
	public void  updateCollaboratorRequest(String collectionId,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
	{
		dbConnect.updateCollaboratorRequest(collectionId, collabUser, collaboratorRole, username);
	}


}
