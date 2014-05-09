package edu.asu.spring.quadriga.service.develop;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionConcepts;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
/*	@Description : This class acts as a dummy Conceptcollection manager which adds list of concepts
**   				and their descriptions on the concept collection.
**   
**  @implements  : IConceptCollectionManager Interface
**   
**  @Called By   : ConceptCollectionController.java
**  
**  @implemented by : SatyaSwaroop   
**/
//@Service
public class MockupConceptCollectionManager implements
		IConceptCollectionManager {
	
	@Override
	public List<IConceptCollection> getCollectionsOwnedbyUser(String sUserId)
			 {
		
		IConceptCollection cc1  = new ConceptCollection();
		IConceptCollection cc2  = new ConceptCollection();
		
		
		
		cc1.setConceptCollectionName("quadriga 1");
		cc2.setConceptCollectionName("quadriga 2");
		
		cc1.setDescription("some list goes here");
		cc2.setDescription("some list goes here");
		
		List<IConceptCollection> list = new ArrayList<IConceptCollection>();
		
		list.add(cc1);
		list.add(cc2);
				
		return list;
		
	}
	
	

	@Override
	public List<IConceptCollection> getUserCollaborations(String sUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public ConceptpowerReply search(String item, String pos) {
		// TODO Auto-generated method stub
		return null;
	}

	

	

	@Override
	public void addConceptCollection(IConceptCollection collection) {
		// TODO Auto-generated method stub
	}

	

	

	

	@Override
	public void getCollaborators(IConceptCollection collection) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void getCollectionDetails(IConceptCollection concept, String username)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		
	}

	

	

	@Override
	public List<IUser> showNonCollaboratingUsers(String collectionid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IConceptCollectionCollaborator> showCollaboratingUsers(String collectionid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addItems(String lemmma, String id, String pos, String desc,
			String conceptcollectionId, String string)
			throws QuadrigaStorageException, QuadrigaAccessException {
		// TODO Auto-generated method stub
		
	}





	@Override
	public void deleteItem(String id, String collectionid, String username)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String[] values, IConceptCollection concept,
			String username) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getConceptLemmaFromConceptId(String id) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getConceptCollectionId(String ccName)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public String getProjectsTree(String userName,String ccId) throws JSONException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public boolean hasConceptID(
			List<IConceptCollectionConcepts> conceptCollectionConceptList,
			String id) {
		// TODO Auto-generated method stub
		return false;
	}



}
