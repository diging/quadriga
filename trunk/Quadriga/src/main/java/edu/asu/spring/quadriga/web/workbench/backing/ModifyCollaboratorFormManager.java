package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.impl.ModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;

/**
 * this class returns the collaborator of ModifyCollaborator domain by calling existing DBConnectonManager 
 * methods of conecptcollection,project and dictionary which by default return collaborator
 * of ICollaborator domain.
 * 
 * @author rohit pendbhaje
 *
 */
@Service
public class ModifyCollaboratorFormManager {

	@Autowired
	IRetrieveProjCollabManager projCollabManager;
	
	@Autowired
	IDictionaryManager dictManager;
	
	@Autowired
	IConceptCollectionManager collectionManager;
	
	@Autowired
	ModifyCollaboratorFormFactory collaboratorFormFactory;
	
	/**
	 * takes project id to return collaborators of ModifyCollaborator domain
	 * 
	 * @param projectId
	 * @return List<ModifyCollaborator> list of modifycollaborator domain
	 * @throws QuadrigaStorageException
	 */
	public List<ModifyCollaborator> modifyCollaboratorManager(String projectId) throws QuadrigaStorageException
	{
	
		List<ModifyCollaborator> modifyCollaborators = new ArrayList<ModifyCollaborator>();
		List<ICollaborator> collaborators =  projCollabManager.getProjectCollaborators(projectId);
		IUser user;
		
		for(ICollaborator collaborator:collaborators)
		{
			ModifyCollaborator modifyCollab = new ModifyCollaborator();
			user = collaborator.getUserObj();
			modifyCollab.setUserName(user.getUserName());
			modifyCollab.setName(user.getName());
			modifyCollab.setCollaboratorRoles(collaborator.getCollaboratorRoles());
			modifyCollaborators.add(modifyCollab);
		}
		
		return modifyCollaborators;
	}
	
	/**
	 * takes dictionary id to return collaborators of ModifyCollaborator domain
	 * 
	 * @param dictionaryId
	 * @return List<ModifyCollaborator> list of modifycollaborator domain
	 * @throws QuadrigaStorageException
	 */
	public List<ModifyCollaborator> modifyDictCollaboratorManager(String dictionaryId) throws QuadrigaStorageException
	{
	
		List<ModifyCollaborator> modifyCollaborators = new ArrayList<ModifyCollaborator>();
		IUser user;
		List<ICollaborator> collaborators =  dictManager.showCollaboratingUsers(dictionaryId);
		
		for(ICollaborator collaborator:collaborators)
		{
			ModifyCollaborator modifyCollab = new ModifyCollaborator();
			user = collaborator.getUserObj();
			modifyCollab.setUserName(user.getUserName());
			modifyCollab.setName(user.getName());
			modifyCollab.setCollaboratorRoles(collaborator.getCollaboratorRoles());
			modifyCollaborators.add(modifyCollab);
		}
		
		return modifyCollaborators;
	}
	
	
	/**
	 * takes collection id to return collaborators of ModifyCollaborator domain
	 * 
	 * @param collectionid
	 * @return List<ModifyCollaborator> list of modifycollaborator domain
	 * @throws QuadrigaStorageException
	 */
	public List<ModifyCollaborator> modifyCCCollaboratorManager(String collectionId) throws QuadrigaStorageException
	{
	
		List<ModifyCollaborator> modifyCollaborators = new ArrayList<ModifyCollaborator>();
		IUser user;
		List<ICollaborator> collaborators =  collectionManager.showCollaboratingUsers(collectionId);
		
		for(ICollaborator collaborator:collaborators)
		{
			ModifyCollaborator modifyCollab = new ModifyCollaborator();
			user = collaborator.getUserObj();
			modifyCollab.setUserName(user.getUserName());
			modifyCollab.setName(user.getName());
			modifyCollab.setCollaboratorRoles(collaborator.getCollaboratorRoles());
			modifyCollaborators.add(modifyCollab);
		}
		
		return modifyCollaborators;
	}
	
	
}
