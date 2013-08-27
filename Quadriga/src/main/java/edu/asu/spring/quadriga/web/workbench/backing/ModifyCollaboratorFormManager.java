package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.factories.impl.ModifyCollaboratorFormFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjCollabManager;

@Service
public class ModifyCollaboratorFormManager {

	@Autowired
	IRetrieveProjCollabManager projCollabManager;
	
	@Autowired
	IDictionaryManager dictManager;
	
	@Autowired
	ModifyCollaboratorFormFactory collaboratorFormFactory;
	
	public List<ModifyCollaborator> modifyCollaboratorManager(String projectId) throws QuadrigaStorageException
	{
	
		List<ModifyCollaborator> modifyCollaborators = new ArrayList<ModifyCollaborator>();
		List<ICollaborator> collaborators =  projCollabManager.getProjectCollaborators(projectId);
		
		for(ICollaborator collaborator:collaborators)
		{
			ModifyCollaborator modifyCollab = new ModifyCollaborator();
			modifyCollab.setUserName(collaborator.getUserObj().getUserName());
			modifyCollab.setCollaboratorRoles(collaborator.getCollaboratorRoles());
			modifyCollaborators.add(modifyCollab);
		}
		
		return modifyCollaborators;
	}
	
	public List<ModifyCollaborator> modifyDictCollaboratorManager(String dictionaryId) throws QuadrigaStorageException
	{
	
		List<ModifyCollaborator> modifyCollaborators = new ArrayList<ModifyCollaborator>();
		List<ICollaborator> collaborators =  dictManager.showCollaboratingUsers(dictionaryId);
		
		for(ICollaborator collaborator:collaborators)
		{
			ModifyCollaborator modifyCollab = new ModifyCollaborator();
			modifyCollab.setUserName(collaborator.getUserObj().getUserName());
			modifyCollab.setCollaboratorRoles(collaborator.getCollaboratorRoles());
			modifyCollaborators.add(modifyCollab);
		}
		
		return modifyCollaborators;
	}
	
	
}
