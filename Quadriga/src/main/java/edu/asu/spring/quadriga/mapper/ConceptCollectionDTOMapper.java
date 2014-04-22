package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionItemsDTO;
import edu.asu.spring.quadriga.dto.ConceptsDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;

@Service
public class ConceptCollectionDTOMapper extends DAOConnectionManager
{
	
	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private UserDTOMapper userDTOMapper;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
//	public IConceptCollection getConceptCollection(ConceptCollectionDTO collection)
//	{
//		List<ICollaborator> collaboratorList = null;
//		List<IConcept> conceptList = null;
//		IConceptCollection concept = null;
//		ICollaborator collaborator= null;
//		IConcept tempConcept = null;
//		
//		concept = conceptCollectionFactory.createConceptCollectionObject();
//		collaboratorList = new ArrayList<ICollaborator>();
//		conceptList = new ArrayList<IConcept>();
//		List<ConceptCollectionCollaboratorDTO> conceptCollectionCollaborators = collection.getConceptCollectionCollaboratorDTOList();
//		
//		if(conceptCollectionCollaborators != null)
//		{
//			for(ConceptCollectionCollaboratorDTO conceptCollaborator : conceptCollectionCollaborators)
//			{
//				
//				collaborator = getConceptCollectionCollaborators(conceptCollaborator);
//				
//				if(collaboratorList.contains(collaborator))
//				{
//					int index = collaboratorList.indexOf(collaborator);
//					ICollaborator tempCollaborator = collaboratorList.get(index);
//					List<ICollaboratorRole> tempRoles = tempCollaborator.getCollaboratorRoles();
//					tempRoles.addAll(collaborator.getCollaboratorRoles());
//					tempCollaborator.setCollaboratorRoles(tempRoles);
//					
//					//set the collaborator with the roles
//					collaboratorList.set(index, tempCollaborator);
//				}
//				else
//				{
//					collaboratorList.add(collaborator);
//				}
//			}
//		}
//		
//		//loop through all the items and add it to concept collection
//		List<ConceptCollectionItemsDTO> collectionItems = collection.getConceptCollectionItemsDTOList();
//		
//		if(collectionItems != null)
//		{
//			for(ConceptCollectionItemsDTO collectionConcept : collectionItems)
//			{
//				tempConcept = getConceptCollectionItems(collectionConcept);
//				conceptList.add(tempConcept);
//			}
//			
//		}
//		concept.setId(collection.getConceptCollectionid());
//		concept.setName(collection.getCollectionname());
//		concept.setDescription(collection.getDescription());
//        concept.setOwner(userDTOMapper.getUser(collection.getCollectionowner()));
//        concept.setCollaborators(collaboratorList);   
//        concept.setItems(conceptList);
//		
//		return concept;
//	}
	public IConceptCollection getConceptCollection(ConceptCollectionDTO collection)
	{
		List<ICollaborator> collaboratorList = null;
		List<IConcept> conceptList = null;
		IConceptCollection concept = null;
		ICollaborator collaborator= null;
		IConcept tempConcept = null;
		
		concept = conceptCollectionFactory.createConceptCollectionObject();
		collaboratorList = new ArrayList<ICollaborator>();
		conceptList = new ArrayList<IConcept>();
		List<ConceptCollectionCollaboratorDTO> conceptCollectionCollaborators = collection.getConceptCollectionCollaboratorDTOList();
		
		if(conceptCollectionCollaborators != null)
		{
			for(ConceptCollectionCollaboratorDTO conceptCollaborator : conceptCollectionCollaborators)
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
		}
		
		//loop through all the items and add it to concept collection
		List<ConceptCollectionItemsDTO> collectionItems = collection.getConceptCollectionItemsDTOList();
		
		if(collectionItems != null)
		{
			for(ConceptCollectionItemsDTO tempCollectionConcept : collectionItems)
			{
				ConceptsDTO collectionConcept = tempCollectionConcept.getConceptDTO();
				tempConcept = getConceptCollectionItems(collectionConcept);
				conceptList.add(tempConcept);
			}
			
		}
		concept.setConceptCollectionId(collection.getConceptCollectionid());
		concept.setConceptCollectionName(collection.getCollectionname());
		concept.setDescription(collection.getDescription());
        concept.setOwner(userDTOMapper.getUser(collection.getCollectionowner()));
        concept.setCollaborators(collaboratorList);   
        concept.setConcepts(conceptList);
		
		return concept;
	}
	
//	public IConcept getConceptCollectionItems(ConceptCollectionItemsDTO conceptCollectionConcept)
//	{
//		IConcept concept = null;
//		concept = conceptCollectionFactory.createConcept();
//		concept.setId(conceptCollectionConcept.getConceptcollectionsItemsDTOPK().getItem());
//		concept.setDescription(conceptCollectionConcept.getDescription());
//		concept.setLemma(conceptCollectionConcept.getLemma());
//		concept.setPos(conceptCollectionConcept.getPos());
//		return concept;
//	}
	public IConcept getConceptCollectionItems(ConceptsDTO conceptDTO)
	{
		IConcept concept = null;
		concept = conceptCollectionFactory.createConcept();
		concept.setConceptId(conceptDTO.getItem());
		concept.setDescription(conceptDTO.getDescription());
		concept.setLemma(conceptDTO.getLemma());
		concept.setPos(conceptDTO.getPos());
		return concept;
	}
	public List<IConcept> getConceptCollectionItemList(List<ConceptsDTO> conceptsList)
	{
		List<IConcept> conceptList = new ArrayList<IConcept>();
		if(conceptsList != null && conceptsList.size() > 0)
		{
			Iterator<ConceptsDTO> ccItemsIterator = conceptsList.iterator();
			while(ccItemsIterator.hasNext())
			{
				conceptList.add(getConceptCollectionItems(ccItemsIterator.next()));
			}	
		}
		return conceptList;
	}
//	public List<IConcept> getConceptCollectionItemList(List<ConceptCollectionItemsDTO> conceptCollectionConceptList)
//	{
//		List<IConcept> conceptList = new ArrayList<IConcept>();
//		if(conceptCollectionConceptList != null && conceptCollectionConceptList.size() > 0)
//		{
//			Iterator<ConceptCollectionItemsDTO> ccItemsIterator = conceptCollectionConceptList.iterator();
//			while(ccItemsIterator.hasNext())
//			{
//				conceptList.add(getConceptCollectionItems(ccItemsIterator.next()));
//			}	
//		}
//		return conceptList;
//	}
	
	public ICollaborator getConceptCollectionCollaborators(ConceptCollectionCollaboratorDTO conceptCollectionCollaborator)
	{
		ICollaborator collaborator = null;
		List<ICollaboratorRole> collaboratorRoles = null;
		
		collaborator = collaboratorFactory.createCollaborator();
		collaboratorRoles = new ArrayList<ICollaboratorRole>();
		
		QuadrigaUserDTO userName = conceptCollectionCollaborator.getQuadrigaUserDTO();
		String role = conceptCollectionCollaborator.getConceptCollectionCollaboratorDTOPK().getCollaboratorrole();
		
		collaboratorRoles.add(collaboratorRoleManager.getCollectionCollabRoleByDBId(role));     
		
		collaborator.setUserObj(userDTOMapper.getUser(userName));
		collaborator.setCollaboratorRoles(collaboratorRoles);
        //TODO : add collaborator description
		
		return collaborator;
	}

	/**
	 * 
	 * Returns list of Conceptcollections mapped to ConceptcollectionsDTO
	 * @param conceptCollection
	 * @return ConceptCollection
	 * @author Karthik Jayaraman
	 * 
	 */
	public List<IConceptCollection> getConceptCollectionList(List<ConceptCollectionDTO> conceptCollectionDTOList) throws QuadrigaException
	{
		List<IConceptCollection> conceptCollectionList = new ArrayList<IConceptCollection>();
		Iterator<ConceptCollectionDTO> conceptIterator = conceptCollectionDTOList.iterator();
		while(conceptIterator.hasNext())
		{
			conceptCollectionList.add(getConceptCollection(conceptIterator.next()));
		}
		return conceptCollectionList;
	}
	
	/**
	 * 
	 * Returns ConceptcollectionsDTO mapped to Conceptcollection
	 * @param conceptCollection
	 * @return ConceptcollectionsDTO
	 * @author Karthik Jayaraman
	 * 
	 */
	public ConceptCollectionDTO getConceptCollectionDTO(IConceptCollection conceptCollection) throws QuadrigaStorageException
	{
		ConceptCollectionDTO conceptcollectionsDTO = new ConceptCollectionDTO();
		conceptcollectionsDTO.setUpdatedby(conceptCollection.getOwner().getUserName());
		conceptcollectionsDTO.setUpdateddate(new Date());
		conceptcollectionsDTO.setCreatedby(conceptCollection.getOwner().getUserName());
		conceptcollectionsDTO.setCreateddate(new Date());
		conceptcollectionsDTO.setCollectionname(conceptCollection.getConceptCollectionName());
		conceptcollectionsDTO.setDescription(conceptCollection.getDescription());
		conceptcollectionsDTO.setCollectionname(conceptCollection.getConceptCollectionName());
		conceptcollectionsDTO.setCollectionowner(getUserDTO(conceptCollection.getOwner().getUserName()));
		conceptcollectionsDTO.setAccessibility(Boolean.FALSE);
		return conceptcollectionsDTO;
	}
	
}
