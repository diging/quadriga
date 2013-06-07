/**
 * 
 */
package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;

/**
 * @author satyaswaroop
 *
 */
@Service
public class ConceptCollectionManager implements IConceptCollectionManager {

	@Autowired
	@Qualifier("DBConnectionCCManagerBean")
	private IDBConnectionCCManager dbConnect;
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.IConceptCollectionManager#getCollectionsOfUser(java.lang.String)
	 */
	@Override
	public List<IConceptCollection> getCollectionsOwnedbyUser(String sUserId)
	{
		
		List<IConceptCollection> conceptList = new ArrayList<IConceptCollection>();  
		conceptList = dbConnect.getConceptsOwnedbyUser(sUserId);
		return conceptList;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.IConceptCollectionManager#updateConceptCollection(edu.asu.spring.quadriga.domain.implementation.ConceptCollection)
	 */
	@Override
	public String updateConceptCollection(ConceptCollection conceptCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.IConceptCollectionManager#deleteConceptCollection(java.lang.String)
	 */
	@Override
	public int deleteConceptCollection(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.IConceptCollectionManager#addConceptCollection(edu.asu.spring.quadriga.domain.implementation.ConceptCollection)
	 */
	@Override
	public int addConceptCollection(ConceptCollection newConcept) {
		// TODO Auto-generated method stub
		return 0;
	}

	

	@Override
	public List<IConceptCollection> getUserCollaborations(String sUserId) {
		
		List<IConceptCollection> conceptList = new ArrayList<IConceptCollection>();  
		conceptList = dbConnect.getCollaboratedConceptsofUser(sUserId);
		return conceptList;
	}

	@Override
	public void getCollectionDetails(IConceptCollection concept) {
		dbConnect.getCollectionDetails(concept);
		}

	

}
