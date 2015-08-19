package edu.asu.spring.quadriga.service.dictionary.mapper.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.dictionary.IDBConnectionRetrieveDictionaryManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryItems;
import edu.asu.spring.quadriga.domain.dictionary.IItem;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryCollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryItemFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.dictionary.mapper.IDictionaryDeepMapper;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectDictionaryShallowMapper;
import edu.asu.spring.quadriga.service.workbench.mapper.IProjectShallowMapper;
import edu.asu.spring.quadriga.service.workspace.mapper.IWorkspaceDictionaryShallowMapper;

@Service
public class DictionaryDeepMapper implements IDictionaryDeepMapper {


	
	@Autowired
	private IQuadrigaRoleManager roleManager;
	
	@Autowired
	private IDictionaryCollaboratorFactory dictionaryCollaboratorFactory;
	
	@Autowired
	private IDBConnectionRetrieveDictionaryManager dbConnect;
	
	@Autowired
	private IDictionaryFactory dictionaryFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(DictionaryDeepMapper.class);
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private IQuadrigaRoleFactory roleFactory;
	
	@Autowired
	private IProjectDictionaryShallowMapper projectDictionaryShallowMapper;
	
	@Autowired
	private IWorkspaceDictionaryShallowMapper workspaceDictionaryShallowMapper;
	
	@Autowired
	private IProjectShallowMapper projectShallowMapper;
	
	@Autowired
	private IDictionaryItemsFactory dictionaryItemsFactory;
	
	@Autowired
	private IDictionaryItemFactory dictionaryItemFactory;
	
	@Autowired
	private IUserDeepMapper userDeepMapper;
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	public IDictionary getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException{
		IDictionary dictionary = null;
		
		DictionaryDTO dictionaryDTO = dbConnect.getDTO(dictionaryId);
		if(dictionaryDTO != null){
			dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setDictionaryId(dictionaryDTO.getDictionaryid());
			dictionary.setDictionaryName(dictionaryDTO.getDictionaryname());
			dictionary.setDescription(dictionaryDTO.getDescription());
			dictionary.setCreatedBy(dictionaryDTO.getCreatedby());
			dictionary.setCreatedDate(dictionaryDTO.getCreateddate());
			dictionary.setUpdatedBy(dictionaryDTO.getUpdatedby());
			dictionary.setUpdatedDate(dictionaryDTO.getUpdateddate());
			dictionary.setOwner(userDeepMapper.getUser(dictionaryDTO.getDictionaryowner().getUsername()));
			
			// Setting dictionary collaborator
			dictionary.setDictionaryCollaborators(getDictionaryCollaboratorList(dictionaryDTO, dictionary));
			// Setting dictionary Projects
			dictionary.setDictionaryProjects(projectDictionaryShallowMapper.getProjectDictionaryList(dictionaryDTO, dictionary));
			// Setting dictionary Workspaces
			dictionary.setDictionaryWorkspaces(workspaceDictionaryShallowMapper.getWorkspaceDictionaryList(dictionary, dictionaryDTO));
			// Setting dictionary Items
			dictionary.setDictionaryItems(getDictionaryItemList(dictionaryDTO, dictionary));
		}
		return dictionary;
	}
	
	
	/**
	 * {@inheritDoc}
	*/
	@Override
	public IDictionary getDictionaryDetails(String dictionaryId, String userName) throws QuadrigaStorageException{
		IDictionary dictionary = null;
		
		DictionaryDTO dictionaryDTO = dbConnect.getDictionaryDTO(dictionaryId, userName);
		if(dictionaryDTO != null){
			dictionary = dictionaryFactory.createDictionaryObject();
			dictionary.setDictionaryId(dictionaryDTO.getDictionaryid());
			dictionary.setDictionaryName(dictionaryDTO.getDictionaryname());
			dictionary.setDescription(dictionaryDTO.getDescription());
			dictionary.setCreatedBy(dictionaryDTO.getCreatedby());
			dictionary.setCreatedDate(dictionaryDTO.getCreateddate());
			dictionary.setUpdatedBy(dictionaryDTO.getUpdatedby());
			dictionary.setUpdatedDate(dictionaryDTO.getUpdateddate());
			dictionary.setOwner(userDeepMapper.getUser(dictionaryDTO.getDictionaryowner().getUsername()));
			
			// Setting dictionary collaborator
			dictionary.setDictionaryCollaborators(getDictionaryCollaboratorList(dictionaryDTO, dictionary));
			// Setting dictionary Projects
			dictionary.setDictionaryProjects(projectDictionaryShallowMapper.getProjectDictionaryList(dictionaryDTO, dictionary));
			// Setting dictionary Workspaces
			dictionary.setDictionaryWorkspaces(workspaceDictionaryShallowMapper.getWorkspaceDictionaryList(dictionary, dictionaryDTO));
			// Setting dictionary Items
			dictionary.setDictionaryItems(getDictionaryItemList(dictionaryDTO, dictionary));
		}
		return dictionary;
	}
	
	public List<IDictionaryItems> getDictionaryItemList(DictionaryDTO dictionaryDTO, IDictionary dictionary){
		List<IDictionaryItems> dictionaryItemList = null;
		List<DictionaryItemsDTO> dictionaryItemsDTOList = dictionaryDTO.getDictionaryItemsDTOList();
		
		if(dictionaryItemsDTOList != null ){
			for(DictionaryItemsDTO  dictionaryItemsDTO: dictionaryItemsDTOList){
				if(dictionaryItemList == null){
					dictionaryItemList = new ArrayList<IDictionaryItems>();
				}
				IItem item = dictionaryItemFactory.createDictionaryItemObject();
				//TODO : we need to fill this
				item.setTerm(dictionaryItemsDTO.getTerm());
				item.setDictionaryItemId(dictionaryItemsDTO.getDictionaryItemsDTOPK().getTermid());
				item.setPos(dictionaryItemsDTO.getPos());
				
				IDictionaryItems dictionaryItems= dictionaryItemsFactory.createDictionaryItemsObject(); 
				dictionaryItems.setCreatedBy(dictionaryItemsDTO.getCreatedby());
				dictionaryItems.setCreatedDate(dictionaryItemsDTO.getCreateddate());
				dictionaryItems.setUpdatedDate(dictionaryItemsDTO.getUpdateddate());
				dictionaryItems.setUpdatedBy(dictionaryItemsDTO.getUpdatedby());
				dictionaryItems.setDictionary(dictionary);
				dictionaryItems.setDictionaryItem(item);
				dictionaryItemList.add(dictionaryItems);
			}
		}
		
		return dictionaryItemList;
	}
	
	

	public List<IDictionaryCollaborator> getDictionaryCollaboratorList(DictionaryDTO dictionaryDTO,IDictionary dictionary) throws QuadrigaStorageException
	{
		List<IDictionaryCollaborator> dictionaryCollaboratorList = null;
		if(dictionaryDTO.getDictionaryCollaboratorDTOList() != null && dictionaryDTO.getDictionaryCollaboratorDTOList().size() > 0)
		{
			HashMap<String,IDictionaryCollaborator> userDictionaryCollaboratorMap = mapUserDictionaryCollaborator(dictionaryDTO,dictionary);
			for(String userID:userDictionaryCollaboratorMap.keySet())
			{
				if(dictionaryCollaboratorList == null){
					dictionaryCollaboratorList = new ArrayList<IDictionaryCollaborator>();
				}
				dictionaryCollaboratorList.add(userDictionaryCollaboratorMap.get(userID));
			}
		}
		return dictionaryCollaboratorList;
	}
	
	public HashMap<String,IDictionaryCollaborator> mapUserDictionaryCollaborator(DictionaryDTO dictionaryDTO,IDictionary dictionary) throws QuadrigaStorageException
	{		
		
		HashMap<String, IDictionaryCollaborator> userDictionaryCollaboratorMap = new HashMap<String, IDictionaryCollaborator>();
		
		for(DictionaryCollaboratorDTO dictionaryCollaboratorDTO : dictionaryDTO.getDictionaryCollaboratorDTOList())
		{
			String userName = dictionaryCollaboratorDTO.getQuadrigaUserDTO().getUsername();
			
			if(userDictionaryCollaboratorMap.containsKey(userName))
			{
				String roleName = dictionaryCollaboratorDTO.getDictionaryCollaboratorDTOPK().getCollaboratorrole();
				
				IQuadrigaRole collaboratorRole = roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.DICT_ROLES, roleName);
				
				IDictionaryCollaborator dictionaryCollaborator =userDictionaryCollaboratorMap.get(userName);
				
				ICollaborator collaborator = dictionaryCollaborator.getCollaborator();
				
				collaborator.getCollaboratorRoles().add(collaboratorRole);
				
				// Checking if there is a update latest then previous update date 
				if(dictionaryCollaboratorDTO.getUpdateddate().compareTo(dictionaryCollaborator.getUpdatedDate()) > 0 ){
					dictionaryCollaborator.setUpdatedBy(dictionaryCollaboratorDTO.getUpdatedby());
					dictionaryCollaborator.setUpdatedDate(dictionaryCollaboratorDTO.getUpdateddate());
				}
				
			}
			else
			{
				String roleName = dictionaryCollaboratorDTO.getDictionaryCollaboratorDTOPK().getCollaboratorrole();
				// Prepare collaborator roles
				IQuadrigaRole collaboratorRole = roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.DICT_ROLES, roleName);
				// Create a Collaborator Role list
				List<IQuadrigaRole> collaboratorRoleList = new ArrayList<IQuadrigaRole>();
				// Add collaborator role to the list
				collaboratorRoleList.add(collaboratorRole);
				// Create a Collaborator
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				// Set Collaborator Role List to the Collaborator
				collaborator.setCollaboratorRoles(collaboratorRoleList);
				collaborator.setUserObj(userDeepMapper.getUser(userName));
				// Create ProjectCollaborator object
				IDictionaryCollaborator dictionaryCollaborator = dictionaryCollaboratorFactory.createDictionaryCollaboratorObject();
				dictionaryCollaborator.setCollaborator(collaborator);
				dictionaryCollaborator.setCreatedBy(dictionaryCollaboratorDTO.getCreatedby());
				dictionaryCollaborator.setCreatedDate(dictionaryCollaboratorDTO.getCreateddate());
				dictionaryCollaborator.setUpdatedBy(dictionaryCollaboratorDTO.getUpdatedby());
				dictionaryCollaborator.setUpdatedDate(dictionaryCollaboratorDTO.getUpdateddate());
				dictionaryCollaborator.setDictionary(dictionary);
				
				userDictionaryCollaboratorMap.put(userName, dictionaryCollaborator);

			}
		}
		return userDictionaryCollaboratorMap;
	}

	
	
	
}
