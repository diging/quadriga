package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IItem;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryItemFactory;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTOPK;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;

@Service
public class DictionaryDTOMapper extends BaseMapper
{
	@Autowired
	private IDictionaryFactory dictionaryFactory;
	
	@Autowired
	private IDictionaryItemFactory dictionaryItemsFactory;
	
	@Autowired
	private UserDTOMapper userMapper;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private IQuadrigaRoleManager roleManager;
	
	
	public ICollaborator getDictionaryCollaborators(DictionaryCollaboratorDTO dictionaryCollaborator)
	{
		ICollaborator collaborator = null;
		List<IQuadrigaRole> collaboratorRoles = null;
		
		collaborator = collaboratorFactory.createCollaborator();
		collaboratorRoles = new ArrayList<IQuadrigaRole>();
		
		QuadrigaUserDTO userName = dictionaryCollaborator.getQuadrigaUserDTO();
		String role = dictionaryCollaborator.getDictionaryCollaboratorDTOPK().getCollaboratorrole();
		
		collaboratorRoles.add(roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.DICT_ROLES, role));     
		
		collaborator.setUserObj(userMapper.getUser(userName));
		collaborator.setCollaboratorRoles(collaboratorRoles);
		
		return collaborator;
	}
	


	public DictionaryDTO getDictionaryDTO(IDictionary dictionary) throws QuadrigaStorageException
	{
		DictionaryDTO dictionaryDTO = new DictionaryDTO();
		dictionaryDTO.setDictionaryid(dictionary.getDictionaryId());
		dictionaryDTO.setDictionaryname(dictionary.getDictionaryName());
		dictionaryDTO.setDescription(dictionary.getDescription());
		dictionaryDTO.setAccessibility(Boolean.FALSE);
		dictionaryDTO.setDictionaryowner((getUserDTO(dictionary.getOwner().getUserName())));
		dictionaryDTO.setCreatedby(dictionary.getOwner().getUserName());
		dictionaryDTO.setCreateddate(new Date());
		dictionaryDTO.setUpdatedby(dictionary.getOwner().getUserName());
		dictionaryDTO.setUpdateddate(new Date());
		return dictionaryDTO;
	}
	
	public IItem getDictionaryItem(DictionaryItemsDTO dictionaryItemsDTO)
	{
		IItem dictionaryItem = dictionaryItemsFactory.createDictionaryItemObject();
		dictionaryItem.setDictionaryItemId(dictionaryItemsDTO.getDictionaryItemsDTOPK().getTermid());
		dictionaryItem.setTerm(dictionaryItemsDTO.getTerm());					
		dictionaryItem.setPos(dictionaryItemsDTO.getPos());
		return dictionaryItem;
	}
	
	public List<IItem> getDictionaryItemList(List<DictionaryItemsDTO> dictItemsDTOList)
	{
		List<IItem> dictItemList = new ArrayList<IItem>();
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
