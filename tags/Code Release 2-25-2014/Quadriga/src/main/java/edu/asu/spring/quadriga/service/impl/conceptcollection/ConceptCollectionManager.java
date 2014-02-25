/**
 * 
 */
package edu.asu.spring.quadriga.service.impl.conceptcollection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

/**
 * @author satyaswaroop
 *
 */
@Service
public class ConceptCollectionManager implements IConceptCollectionManager {

	@Autowired
	private IDBConnectionCCManager dbConnect;
	
	@Autowired
	private IConceptFactory conceptFactory;
	
	@Inject
	@Named("restTemplate")
	RestTemplate restTemplate;
	
	@Autowired
	@Qualifier("conceptPowerURL")
	private String conceptURL;
	
	@Autowired
	@Qualifier("searchConceptPowerURLPath")
	private String searchURL;
	
	@Autowired
	@Qualifier("updateConceptPowerURLPath")
	private String updateURL;
	
	@Autowired
	private ICollaboratorRoleManager roleMapper ;
	


	/**
	 * This method retrieves the concept collection owner by the submitted user
	 * @param userId - logged in user
	 * @throws QuadrigaStorageException
	 * @return List<IConceptCollection> list of concept collection associated with the user as owner
	 */
	@Override
	@Transactional
	public List<IConceptCollection> getCollectionsOwnedbyUser(String sUserId) throws QuadrigaStorageException
	{
		List<IConceptCollection> conceptList = new ArrayList<IConceptCollection>();  
		conceptList = dbConnect.getConceptsOwnedbyUser(sUserId);
		return conceptList;
	}


	/**
	 * This methods retrieves the concept collection associated with the user
	 * as a collaborator
	 * @param sUserID - logged in user id
	 * @param List<IConceptCollection> - list of concept collection associated 
	 * with user as a collaborator
	 * @throws QuadrigatorageException
	 */
	@Override
	@Transactional
	public List<IConceptCollection> getUserCollaborations(String sUserId) throws QuadrigaStorageException {
		
		List<IConceptCollection> conceptList = new ArrayList<IConceptCollection>();  
		conceptList = dbConnect.getCollaboratedConceptsofUser(sUserId);
		return conceptList;
	}

	/**
	 * This method retrieves the concept collection details
	 * @param concept - concept collection object containing the id
	 * @param username - logged in user name
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void getCollectionDetails(IConceptCollection concept, String username) throws QuadrigaStorageException, QuadrigaAccessException {
		dbConnect.getCollectionDetails(concept, username);
	}

	/**
	 * This method searches the items and its part of speech in the concept power database
	 * @param item - concept collection item
	 * @param pos - part of speech of item word
	 */
	@Override
	public ConceptpowerReply search(String item, String pos) {
		
		ConceptpowerReply rep;
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("name", item);
		vars.put("pos", pos);
		
		if((item!=null && !item.isEmpty())  && (pos!=null && !pos.isEmpty())){
			rep = restTemplate.getForObject(conceptURL+searchURL+"{name}/{pos}", ConceptpowerReply.class, vars);
			return rep;
		}
		else
			return null;
	}
	
	/**
	 * This method updates the items associated to the concept collection
	 * @param id[] - array of items associated with the collection
	 * @param collection - concept collection object
	 * @param username - logged in user
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void update(String id[],IConceptCollection collection, String username) throws QuadrigaStorageException {
		
		IConcept concept;
		ConceptpowerReply rep;
		for(String i : id){
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("name", i);
		
		if((i!=null && !i.isEmpty())){
			rep = restTemplate.getForObject(conceptURL+updateURL+"{name}", ConceptpowerReply.class, vars);
			System.out.println("rep:: "+rep);
			System.out.println("getConceptEntry:: "+rep.getConceptEntry());
			System.out.println("conceptURL:: "+conceptURL);
			System.out.println("updateURL:: "+updateURL);
			System.out.println("name:: "+i);
			
			concept = conceptFactory.createConceptObject();
			concept.setId(i);
			concept = collection.getItems().get((collection.getItems().indexOf(concept)));
			concept.setDescription(rep.getConceptEntry().get(0).getDescription());
			concept.setLemma(rep.getConceptEntry().get(0).getLemma());
			concept.setPos(rep.getConceptEntry().get(0).getPos());
			dbConnect.updateItem(concept,collection.getId(),username);
		}
		}
	}
	
	/**
	 * This method returns Lemma for the given concept
	 * @param id - item id
	 * @return String - lemma associated with concept
	 */
	@Override
	public String getCocneptLemmaFromConceptId(String id){
		
		Map<String, String> vars = new HashMap<String, String>();
		vars.put("name", id);
		String Lemma=id;
		ConceptpowerReply rep = restTemplate.getForObject(conceptURL+updateURL+"{name}", ConceptpowerReply.class, vars);
		if(rep.getConceptEntry().size() == 0 ){
			return Lemma;
		}
		return rep.getConceptEntry().get(0).getLemma();
	}
	
	/**
	 * This method adds the items to the concept collection
	 * @param lemma
	 * @param id
	 * @param pos
	 * @param desc
	 * @param conceptcollectionId
	 * @param username - logged in user name
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@Override
	@Transactional
	public void addItems(String lemmma, String id, String pos, String desc, String conceptcollectionId, String username) throws QuadrigaStorageException, QuadrigaAccessException {
		dbConnect.saveItem(lemmma, id, pos, desc, conceptcollectionId,username);
	}

	/**
	 * This methods adds concept collection
	 * @param collection - Concept Collection object
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void addConceptCollection(IConceptCollection collection) throws QuadrigaStorageException {
		dbConnect.addCollection(collection);
	}

	/**
	 * This method deletes the item associated with the concept collection
	 * @param id - concept id
	 * @param collectionid - concept collection id
	 * @param username - logged in user name
	 * @throws QuadrigatorageException
	 */
	@Override
	@Transactional
	public void deleteItem(String id, String collectionId, String username) throws QuadrigaStorageException {
		dbConnect.deleteItems(id,collectionId,username);
		
	}
	
	/**
	 * @description	  retrieves non collaborating users from database
	 * @param  collectionid
	 * @throws QuadrigaStorageException
	 * @return list of non collaborating users
	 * @author rohit pendbhaje
	 */
	@Override
	@Transactional
	public List<IUser> showNonCollaboratingUsers(String collectionid) throws QuadrigaStorageException {
		List<IUser> nonCollaboratorList =  dbConnect.showNonCollaboratorRequest(collectionid);
		return nonCollaboratorList;
	}

	/**
	 * @description	  retrieves collaborating users from database
	 * @param  collectionid
	 * @throws QuadrigaStorageException
	 * @return list of collaborator objects
	 * @author rohit pendbhaje
	 */
	@Override
	@Transactional
	public List<ICollaborator> showCollaboratingUsers(String collectionid) throws QuadrigaStorageException {
		List<ICollaborator> collaboratorList = dbConnect.showCollaboratorRequest(collectionid);
		return collaboratorList;
	}

	/**
	 * @description	  retrieves collaborating users from database
	 * @param  collectioni
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 */
	@Override
	@Transactional
	public void getCollaborators(IConceptCollection collection) throws QuadrigaStorageException {
		
		dbConnect.getCollaborators(collection);
		List<ICollaborator> collaborators = collection.getCollaborators();
		if(collaborators != null && collaborators.size() > 0)
		{
			for(ICollaborator collaborator:collaborators)
			{
				for(ICollaboratorRole collaboratorRole: collaborator.getCollaboratorRoles())
				{
					roleMapper.fillCollectionCollaboratorRole(collaboratorRole);
				}
			}
		}	
	}
	
	/**
	 * This method retrieves the concept collection id for the given concept collection name
	 * @param ccName - concept collection name
	 * @return String - concept collection id
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public String getConceptCollectionId(String ccName) throws QuadrigaStorageException{
		String ccId = dbConnect.getConceptCollectionId(ccName);
		return ccId;
	}
	
}
