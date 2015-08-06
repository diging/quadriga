package edu.asu.spring.quadriga.service.impl.conceptcollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.conceptcollection.IDBConnectionModifyCCManager;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IModifyConceptCollectionManager;

@Service
public class ModifyConceptCollectionManager implements
		IModifyConceptCollectionManager 
{
	@Autowired
	private IDBConnectionModifyCCManager dbConnect;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyConceptCollectionManager.class);
	
	/**
	 * This method transfers the ownership of the concept collection to the selected
	 * collaborator
	 * @param collectionid - concept collection id
	 * @param oldOwner - current owner of given concept collection
	 * @param newOwner - user for which the ownership is transfered
	 * @param collabRole - collaborator role which is assigned to the current owner
	 * @throws QuadrigatorageException
	 */
	@Override
	@Transactional
	public void transferCollectionOwnerRequest(String collectionId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		logger.info("In transferCollectionOwnerRequest service method");
		
		dbConnect.transferCollectionOwnerRequest(collectionId, oldOwner, newOwner, collabRole);
		
	}
	
	/**
	 * This method updates the concept collection details for given concept collection
	 * @param collection concept collection object
	 * @param userName - logged in user name
	 */
	@Override
	@Transactional
	public void updateCollectionDetails(IConceptCollection collection,String userName) throws QuadrigaStorageException
	{
		logger.info("Update concept collection details : ");
		dbConnect.updateCollectionDetails(collection, userName);
	}

}
