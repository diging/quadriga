package edu.asu.spring.quadriga.service.impl.conceptcollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionModifyCCManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.sevice.conceptcollection.IModifyConceptCollectionManager;

@Service
public class ModifyConceptCollectionManager implements
		IModifyConceptCollectionManager 
{
	@Autowired
	@Qualifier("DBConnectionModifyCCManagerBean")
	private IDBConnectionModifyCCManager dbConnect;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyConceptCollectionManager.class);
	
	@Override
	public void transferCollectionOwnerRequest(String collectionId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		logger.info("In transferCollectionOwnerRequest service method");
		
		dbConnect.transferCollectionOwnerRequest(collectionId, oldOwner, newOwner, collabRole);
		
	}

}
