/**
 * 
 */
package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
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
		throw new NotImplementedException();
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.IConceptCollectionManager#deleteConceptCollection(java.lang.String)
	 */
	@Override
	public int deleteConceptCollection(String id) {
		throw new NotImplementedException();
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

	@Override
	public ConceptpowerReply search(String item, String pos) {
		
		ConceptpowerReply rep;
		Jaxb2Marshaller jm = new Jaxb2Marshaller();
		jm.setClassesToBeBound(new Class[]{ConceptpowerReply.class});
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("name", item);
		vars.put("pos", pos);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		

		MarshallingHttpMessageConverter marsh=new MarshallingHttpMessageConverter(jm,jm);
		messageConverters.add(marsh);
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters(messageConverters);
		if((item!=null && !item.isEmpty())  && (pos!=null && !pos.isEmpty())){
		rep = restTemplate.getForObject(
		  "http://chps.asu.edu/conceptpower/rest/ConceptLookup/{name}/{pos}", 
		  ConceptpowerReply.class, vars);
		
		return rep;
		}
		else
			return null;
	}
	public static void main(String args[])
	{
		new ConceptCollectionManager().search("horse", "noun");
	}

	@Override
	public void addItems(String lemmma, String id, String pos, String desc, String conceptId) {
		// TODO Auto-generated method stub
		dbConnect.saveItem(lemmma, id, pos, desc, conceptId);
	}

	@Override
	public String addConceptCollection(IConceptCollection collection) {
		// TODO Auto-generated method stub
		return dbConnect.addCollection(collection);
	}

}
