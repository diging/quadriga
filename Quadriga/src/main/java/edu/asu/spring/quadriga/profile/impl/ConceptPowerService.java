package edu.asu.spring.quadriga.profile.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.conceptpower.POS;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply;
import edu.asu.spring.quadriga.conceptpower.model.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.IService;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

/**
 * this class contains methods which connects quadriga to conceptpower and searches
 * the required term in conceptpower
 * 
 * @author rohit
 *
 */
@Service
public class ConceptPowerService implements IService {

	@Autowired
	private IConceptCollectionManager collectionManager;
	
	private final static String SERVICE_ID = "edu.asu.conceptpower";
	private final static String SERVICE_NAME = "ConceptPower";
	
	@Override
	public String getServiceId() {

		return SERVICE_ID;
	}
	
	@Override
	public String getName() {

		return SERVICE_NAME;
	}

    /**
     * searches results in the conceptpower authority service and gives back
     * results to quadriga
     * 
     * @param term
     *            search term user wants to search in service
     * @return list of search results
     * @author rohit pendbhaje
     * 
     */
	@Override
	public List<ISearchResult> search(String term) {

		List<ISearchResult> searchResults = new ArrayList<ISearchResult>();
		
		ConceptpowerReply conceptReply = collectionManager.search(term, POS.NOUN);
		if(conceptReply!=null)
		{
			List<ConceptEntry> conceptEntries = conceptReply.getConceptEntry();
			for(ConceptEntry ce : conceptEntries)
			{
				ISearchResult searchResult = new SearchResult();
				searchResult.setName(ce.getLemma());
				searchResult.setId(ce.getId());
				searchResult.setDescription(ce.getDescription());
				searchResults.add(searchResult);
			}
		}
		return searchResults;
	}

}
