/**
 * 
 */
package edu.asu.spring.quadriga.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;	
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaAcessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;

/**
 * @author satyaswaroop
 *
 */
@Service
public class ConceptCollectionManager implements IConceptCollectionManager {

	@Autowired
	@Qualifier("DBConnectionCCManagerBean")
	private IDBConnectionCCManager dbConnect;
	
	@Autowired
	private IConceptFactory conceptFactory;
	
	@Inject
	@Named("restTemplate")
	RestTemplate restTemplate;
	
	@Autowired
	@Qualifier("searchConceptPowerURL")
	private String searchURL;
	
	@Autowired
	@Qualifier("updateConceptPowerURL")
	private String updateURL;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper ;
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.IConceptCollectionManager#getCollectionsOfUser(java.lang.String)
	 */
	@Override
	public List<IConceptCollection> getCollectionsOwnedbyUser(String sUserId) throws QuadrigaStorageException
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
	public List<IConceptCollection> getUserCollaborations(String sUserId) throws QuadrigaStorageException {
		
		List<IConceptCollection> conceptList = new ArrayList<IConceptCollection>();  
		conceptList = dbConnect.getCollaboratedConceptsofUser(sUserId);
		return conceptList;
	}

	@Override
	public void getCollectionDetails(IConceptCollection concept, String username) throws QuadrigaStorageException, QuadrigaAcessException {
		dbConnect.getCollectionDetails(concept,username);
		//dbConnect.getCollaborators(concept);
		
	}

	@Override
	public ConceptpowerReply search(String item, String pos) {
		
		ConceptpowerReply rep;
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("name", item);
		vars.put("pos", pos);
		
		if((item!=null && !item.isEmpty())  && (pos!=null && !pos.isEmpty())){
			rep = restTemplate.getForObject(searchURL+"{name}/{pos}", ConceptpowerReply.class, vars);
			return rep;
		}
		else
			return null;
	}
	
	@Override
	public void update(String id[],IConceptCollection collection) throws QuadrigaStorageException {
		
		IConcept concept;
		ConceptpowerReply rep;
		for(String i : id){
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("name", i);
		
		if((i!=null && !i.isEmpty())){
			rep = restTemplate.getForObject(updateURL+"{name}", ConceptpowerReply.class, vars);
			concept = conceptFactory.createConceptObject();
			concept.setId(i);
			concept = collection.getItems().get((collection.getItems().indexOf(concept)));
			concept.setDescription(rep.getConceptEntry().get(0).getDescription());
			concept.setLemma(rep.getConceptEntry().get(0).getLemma());
			concept.setPos(rep.getConceptEntry().get(0).getPos());
			dbConnect.updateItem(concept,collection.getId());
		}
		}
	}
	

	@Override
	public void addItems(String lemmma, String id, String pos, String desc, int conceptId) throws QuadrigaStorageException {
		
		dbConnect.saveItem(lemmma, id, pos, desc, conceptId);
	}

	@Override
	public String addConceptCollection(IConceptCollection collection) throws QuadrigaStorageException {
		
		return dbConnect.addCollection(collection);
	}

	@Override
	public void deleteItem(String id, int collectionId) throws QuadrigaStorageException {
		
		dbConnect.deleteItems(id,collectionId);
		
	}
	
	@Override
	public List<IUser> showNonCollaboratingUsers(int collectionid) {
		List<IUser> nonCollaboratorList =  dbConnect.showNonCollaboratorRequest(collectionid);
		return nonCollaboratorList;
	}

	@Override
	public List<IUser> showCollaboratingUsers(int collectionid) {
		List<IUser> collaboratorList = dbConnect.showCollaboratorRequest(collectionid);
		return collaboratorList;
	}

	@Override
	public String addCollaborators(ICollaborator collaborator, int collectionid) {
		String errmsg = dbConnect.addCollaboratorRequest(collaborator, collectionid);
		return errmsg;
	}

	@Override
	public void getCollaborators(IConceptCollection collection) {
		
		dbConnect.getCollaborators(collection);
		List<ICollaborator> collaborators = collection.getCollaborators();
		for(ICollaborator collaborator:collaborators)
		{
			for(ICollaboratorRole collaboratorRole: collaborator.getCollaboratorRoles())
			{
				roleMapper.fillCollectionCollaboratorRole(collaboratorRole);
			}
		}
	}

}
