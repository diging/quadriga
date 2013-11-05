package edu.asu.spring.quadriga.profile.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

@Service
public class ConceptPower implements IService {

	@Autowired
	private IConceptCollectionManager collectionManager;
	
	@Autowired
	private ISearchResult searchResult;
	
	@Autowired
	private IDBConnectionProfileManager dbConnectionProfileManager;
	
	@Autowired
	private ISearchResultFactory searchResultFactory;
	
	private String serviceid;
	private String name;
	
	@Override
	public void setServiceId(String serviceid) {
		this.serviceid = serviceid;		
	}

	@Override
	public String getServiceId() {

		return "edu.asu.conceptpower";
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {

		return "ConceptPower";
	}

	@Override
	public List<ISearchResult> search(String term) {

		List<ISearchResult> searchResults = new ArrayList<ISearchResult>();
		String pos = "noun";
		
		ConceptpowerReply conceptReply = collectionManager.search(term, pos);
		if(conceptReply!=null)
		{
			List<ConceptEntry> conceptEntries = conceptReply.getConceptEntry();
			for(ConceptEntry ce : conceptEntries)
			{
				ISearchResult searchResult =searchResultFactory.getSearchResultObject();
				searchResult.setName(ce.getLemma());
				searchResult.setId(ce.getId());
				searchResult.setDescription(ce.getDescription());
				searchResults.add(searchResult);
			}
			
		}
		return searchResults;
	}

	

}
