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
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItem;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.impl.CollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.impl.CollaboratorRoleManager;

@Service
public class DictionaryDTOMapper extends DAOConnectionManager
{
	@Autowired
	private DictionaryFactory dictionaryFactory;
	
	@Autowired
	private DictionaryItemsFactory dictionaryItemsFactory;
	
	@Autowired
	private UserDTOMapper userMapper;
	
	@Autowired
	private CollaboratorFactory collaboratorFactory;
	
	@Autowired
	private CollaboratorRoleManager collaboratorRoleManager;
	
	public IDictionary getDictionary(DictionaryDTO dictionary)
	{
		List<ICollaborator> collaboratorList = null;
		ICollaborator collaborator= null;
		IDictionary tempDictionary = null;
		List<DictionaryCollaboratorDTO> dictionaryCollaboratorList;
		
		collaboratorList = new ArrayList<ICollaborator>();
		
		tempDictionary = dictionaryFactory.createDictionaryObject();
		
		IUser user = userMapper.getUser(dictionary.getDictionaryowner());
		
		//fetch the collaborators
		dictionaryCollaboratorList = dictionary.getDictionaryCollaboratorDTOList();
		
		if(dictionaryCollaboratorList != null)
		{
			for(DictionaryCollaboratorDTO dictionaryCollaborator : dictionaryCollaboratorList)
			{
				collaborator = getDictionaryCollaborators(dictionaryCollaborator);
				
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
		
		tempDictionary.setId(dictionary.getDictionaryid());
		tempDictionary.setName(dictionary.getDictionaryname());
		tempDictionary.setDescription(dictionary.getDescription());
		tempDictionary.setOwner(user);
		tempDictionary.setCollaborators(collaboratorList);
		
		return tempDictionary;
	}
	
	
	public ICollaborator getDictionaryCollaborators(DictionaryCollaboratorDTO dictionaryCollaborator)
	{
		ICollaborator collaborator = null;
		List<ICollaboratorRole> collaboratorRoles = null;
		
		collaborator = collaboratorFactory.createCollaborator();
		collaboratorRoles = new ArrayList<ICollaboratorRole>();
		
		QuadrigaUserDTO userName = dictionaryCollaborator.getQuadrigaUserDTO();
		String role = dictionaryCollaborator.getDictionaryCollaboratorDTOPK().getCollaboratorrole();
		
		collaboratorRoles.add(collaboratorRoleManager.getDictCollaboratorRoleById(role));     
		
		collaborator.setUserObj(userMapper.getUser(userName));
		collaborator.setCollaboratorRoles(collaboratorRoles);
		
		return collaborator;
	}
	
	public List<IDictionary> getDictionaryList(List<DictionaryDTO> dictionaryDTOList)
	{
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
		if(dictionaryDTOList != null && dictionaryDTOList.size() > 0)
		{
			Iterator<DictionaryDTO> dictionaryDTOIterator = dictionaryDTOList.iterator();
			while(dictionaryDTOIterator.hasNext())
			{
				dictionaryList.add(getDictionary(dictionaryDTOIterator.next()));
			}
		}
		return dictionaryList;
	}

	public DictionaryDTO getDictionaryDTO(IDictionary dictionary) throws QuadrigaStorageException
	{
		DictionaryDTO dictionaryDTO = new DictionaryDTO();
		dictionaryDTO.setDictionaryid(dictionary.getId());
		dictionaryDTO.setDictionaryname(dictionary.getName());
		dictionaryDTO.setDescription(dictionary.getDescription());
		dictionaryDTO.setAccessibility(Boolean.FALSE);
		dictionaryDTO.setDictionaryowner((getUserDTO(dictionary.getOwner().getUserName())));
		dictionaryDTO.setCreatedby(dictionary.getOwner().getUserName());
		dictionaryDTO.setCreateddate(new Date());
		dictionaryDTO.setUpdatedby(dictionary.getOwner().getUserName());
		dictionaryDTO.setUpdateddate(new Date());
		return dictionaryDTO;
	}
	
	public IDictionaryItem getDictionaryItem(DictionaryItemsDTO dictionaryItemsDTO)
	{
		IDictionaryItem dictionaryItem = dictionaryItemsFactory.createDictionaryItemsObject();
		dictionaryItem.setId(dictionaryItemsDTO.getDictionaryItemsDTOPK().getTermid());
		dictionaryItem.setItems(dictionaryItemsDTO.getTerm());					
		dictionaryItem.setPos(dictionaryItemsDTO.getPos());
		return dictionaryItem;
	}
	
	public List<IDictionaryItem> getDictionaryItemList(List<DictionaryItemsDTO> dictItemsDTOList)
	{
		List<IDictionaryItem> dictItemList = new ArrayList<IDictionaryItem>();
		if(dictItemsDTOList != null && dictItemsDTOList.size() > 0)
		{
			Iterator<DictionaryItemsDTO> dictItemsIterator = dictItemsDTOList.iterator();
			while(dictItemsIterator.hasNext())
			{
				dictItemList.add(getDictionaryItem(dictItemsIterator.next()));
			}
		}
		return dictItemList;
	}
	
	/**
	 * This method associates the items to dictionary.
	 * @param dictionary
	 * @param item
	 * @param termid
	 * @param pos
	 * @param owner
	 * @return DictionaryItemsDTO object
	 */
	public DictionaryItemsDTO getDictionaryItemsDTO(DictionaryDTO dictionary,String item,String termid, String pos, String owner)
	{
		DictionaryItemsDTO dictionaryItems = null;
		DictionaryItemsDTOPK dictionaryItemsKey = null;
		Date date = new Date();
		
		dictionaryItemsKey = new DictionaryItemsDTOPK(dictionary.getDictionaryid(),termid);
		dictionaryItems = new DictionaryItemsDTO();
		dictionaryItems.setDictionaryItemsDTOPK(dictionaryItemsKey);
		dictionaryItems.setDictionaryDTO(dictionary);
		dictionaryItems.setPos(pos);
		dictionaryItems.setTerm(item);
		dictionaryItems.setCreatedby(owner);
		dictionaryItems.setCreateddate(date);
		dictionaryItems.setUpdatedby(owner);
		dictionaryItems.setUpdateddate(date);
		return dictionaryItems;
	}
}