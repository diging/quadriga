package edu.asu.spring.quadriga.service.develop;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
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
@Service
public class MockupConceptCollectionManager implements
		IConceptCollectionManager {
	
	@Override
	public List<IConceptCollection> getCollectionsOfUser(String sUserId)
			throws SQLException {
		
		IConceptCollection cc1  = new ConceptCollection();
		IConceptCollection cc2  = new ConceptCollection();
		
		cc1.setId("id1");
		cc2.setId("id2");
		
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
	public int addConceptCollection(ConceptCollection newConcept) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IConceptCollection getConceptCollection(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
