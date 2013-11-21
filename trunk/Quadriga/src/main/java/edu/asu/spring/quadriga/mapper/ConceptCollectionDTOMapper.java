package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.dto.ConceptcollectionsCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptcollectionsDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;

@Service
public class ConceptCollectionDTOMapper 
{
	
	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private UserDTOMapper userDTOMapper;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	public IConceptCollection getConceptCollection(ConceptcollectionsDTO conceptCollection)
	{
		List<ICollaborator> collaboratorList = null;
		IConceptCollection concept = null;
		ICollaborator collaborator= null;
		
		concept = conceptCollectionFactory.createConceptCollectionObject();
		collaboratorList = new ArrayList<ICollaborator>();
		
		//fetch all the input values
		List<ConceptcollectionsCollaboratorDTO> conceptCollectionCollaborators = conceptCollection.getConceptcollectionsCollaboratorDTOList();
		//TODO : Fetch the corresponding items and convert them into IConcept
	//	List<ConceptcollectionsItemsDTO> collectionItems = conceptCollection.getConceptcollectionsItemsDTOList();
		
		//loop through the collaborators
		for(ConceptcollectionsCollaboratorDTO conceptCollaborator : conceptCollectionCollaborators)
		{
			
			collaborator = getConceptCollectionCollaborators(conceptCollaborator);
			
			if(collaboratorList.contains(collaborator))
			{
				int index = collaboratorList.indexOf(collaborator);
				ICollaborator tempCollaborator = collaboratorList.get(index);
				List<ICollaboratorRole> tempRoles = tempCollaborator.getCollaboratorRoles();
				tempRoles.addAll(collaborator.getCollaboratorRoles());
				tempCollaborator.setCollaboratorRoles(tempRoles);
				
				//set the collaborator with the roles
				collaboratorList.set(index, tempCollaborator);
			}
			else
			{
				collaboratorList.add(collaborator);
			}
		}
		
		concept.setId(conceptCollection.getId());
		concept.setName(conceptCollection.getCollectionname());
		concept.setDescription(conceptCollection.getDescription());
        concept.setOwner(userDTOMapper.getUser(conceptCollection.getCollectionowner()));
        concept.setCollaborators(collaboratorList);        
		
		
		return concept;
	}
	
	public ICollaborator getConceptCollectionCollaborators(ConceptcollectionsCollaboratorDTO conceptCollectionCollaborator)
	{
		ICollaborator collaborator = null;
		List<ICollaboratorRole> collaboratorRoles = null;
		
		collaborator = collaboratorFactory.createCollaborator();
		collaboratorRoles = new ArrayList<ICollaboratorRole>();
		
		QuadrigaUserDTO userName = conceptCollectionCollaborator.getQuadrigaUserDTO();
		String role = conceptCollectionCollaborator.getConceptcollectionsCollaboratorDTOPK().getCollaboratorrole();
		
		collaboratorRoles.add(collaboratorRoleManager.getCollectionCollabRoleByDBId(role));     
		
		collaborator.setUserObj(userDTOMapper.getUser(userName));
		collaborator.setCollaboratorRoles(collaboratorRoles);
        //TODO : add collaborator description
		
		return collaborator;
	}

}
