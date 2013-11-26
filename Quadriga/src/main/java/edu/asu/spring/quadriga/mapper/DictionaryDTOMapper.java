package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.impl.CollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryFactory;
import edu.asu.spring.quadriga.dto.ConceptcollectionsCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.service.impl.CollaboratorRoleManager;

@Service
public class DictionaryDTOMapper extends DAOConnectionManager
{
	@Autowired
	private DictionaryFactory dictionaryFactory;
	
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
		
		
		tempDictionary.setId(dictionary.getId());
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
        //TODO : add collaborator description
		
		return collaborator;
	}

}
