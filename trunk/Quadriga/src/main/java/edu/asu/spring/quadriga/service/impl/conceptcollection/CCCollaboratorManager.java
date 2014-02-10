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
	
	/**
	 * This method associated a collaborator to concept collection
	 * @param collaborator - collaborator object containing details of collaborator
	 * @param collectionid - Concept Collection id.
	 * @param userName - logged in user name
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void addCollaborators(ICollaborator collaborator, String collectionid, String userName)
			throws QuadrigaStorageException 
	{
		dbConnect.addCollaboratorRequest(collaborator, collectionid, userName);
	}
	
	/**
	 * This methods removes the association of collaborator to the concept collection.
	 * @param userName - logged in user
	 * @param collectionid - concept collection id
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void deleteCollaborators(String userName, String collectionid) throws QuadrigaStorageException 
	{
		dbConnect.deleteCollaboratorRequest(userName, collectionid);
	}
	
	/**
	 * This method updated the roles of the collaborator
	 * @param collectionId - concept collection id
	 * @param collabUser - collaborator user
	 * @param collaboratorRole - collaborator roles
	 * @param username - logged in user name
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void  updateCollaboratorRequest(String collectionId,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
	{
		dbConnect.updateCollaboratorRequest(collectionId, collabUser, collaboratorRole, username);
	}


}
