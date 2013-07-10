package edu.asu.spring.quadriga.service.develop;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
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
		
		
		
		cc1.setName("quadriga 1");
		cc2.setName("quadriga 2");
		
		cc1.setDescription("some list goes here");
		cc2.setDescription("some list goes here");
		
		List<IConceptCollection> list = new ArrayList<IConceptCollection>();
		
		list.add(cc1);
		list.add(cc2);
				
		return list;
		
	}

	@Override
	public String updateConceptCollection(ConceptCollection conceptCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int deleteConceptCollection(String id) {
		// TODO Auto-generated method stub
		return 0;
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
	public String addConceptCollection(IConceptCollection collection) {
		// TODO Auto-generated method stub
		return null;
	}

	

	@Override
	public void update(String[] values, IConceptCollection concept) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addItems(String lemmma, String id, String pos, String desc,
			int conceptId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteItem(String id, int i) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IUser> showNonCollaboratingUsers(int collectionid) {
		return null;
	}

	@Override
	public List<ICollaborator> showCollaboratingUsers(int collectionid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addCollaborators(ICollaborator collaborator, int collectionid, String userName) {
		// TODO Auto-generated method stub
		return null;
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


	
	

	

}
