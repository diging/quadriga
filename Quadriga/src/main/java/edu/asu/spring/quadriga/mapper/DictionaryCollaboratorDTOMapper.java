package edu.asu.spring.quadriga.mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Service
public class DictionaryCollaboratorDTOMapper extends BaseMapper
{
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	@Autowired
	private UserDTOMapper userMapper;
	
	@Autowired
	private IUserManager userManager;
	
	@Autowired
	private IQuadrigaRoleManager roleManager;
	
	@Autowired
	private IQuadrigaRoleFactory roleFactory;
	
	
	/**
	 * This method associates collaborator to a dictionary.
	 * @param dictionary
	 * @param userName
	 * @param collabRole
	 * @return DictionaryCollaboratorDTO object
	 * @throws QuadrigaStorageException
	 */
	public DictionaryCollaboratorDTO getDictionaryCollaboratorDTO(DictionaryDTO dictionary,String userName, String sessionUser,String collabRole) throws QuadrigaStorageException
	{
		DictionaryCollaboratorDTO dictionaryCollaborator = null;
		DictionaryCollaboratorDTOPK dictionaryCollaboratorKey = null;
		Date date = new Date();
		dictionaryCollaboratorKey = new DictionaryCollaboratorDTOPK(dictionary.getDictionaryid(),userName,collabRole);
		dictionaryCollaborator = new DictionaryCollaboratorDTO();
		
		dictionaryCollaborator.setDictionaryCollaboratorDTOPK(dictionaryCollaboratorKey);
		dictionaryCollaborator.setDictionaryDTO(dictionary);
		dictionaryCollaborator.setQuadrigaUserDTO(getUserDTO(userName));
		dictionaryCollaborator.setCreatedby(sessionUser);
		dictionaryCollaborator.setCreateddate(date);
		dictionaryCollaborator.setUpdatedby(sessionUser);
		dictionaryCollaborator.setUpdateddate(date);
		return dictionaryCollaborator;
	}
	
	/**
	 * This methods retrieves all the dictionary collaborators and returns them
	 * as list of collaborator domain objects 
	 * @param dictionary
	 * @return List<ICollaborator>
	 * @throws QuadrigaStorageException
	 */
	public List<ICollaborator> getDictionaryCollaboratorList(DictionaryDTO dictionary) throws QuadrigaStorageException
	{
		List<ICollaborator> dictionaryCollaborators = new ArrayList<ICollaborator>();
		List<DictionaryCollaboratorDTO> dictionaryCollaboratorsDTO = null;
		
		dictionaryCollaboratorsDTO = dictionary.getDictionaryCollaboratorDTOList();
		
		if((dictionaryCollaboratorsDTO !=null)&&(dictionaryCollaboratorsDTO.size() > 0))
		{
			HashMap<String,List<String>> collabMap = mapUserRoles(dictionary);
			for(String userID:collabMap.keySet())
			{
				List<IQuadrigaRole> collaboratorRoleList = new ArrayList<IQuadrigaRole>();
				ICollaborator collaborator = collaboratorFactory.createCollaborator();
				collaborator.setUserObj(userManager.getUser(userID));
				for(String roleName: collabMap.get(userID))
				{
				    IQuadrigaRole collaboratorRole = roleFactory.createQuadrigaRoleObject();
					collaboratorRole.setDBid(roleName);
					collaboratorRole.setDisplayName(roleManager.getQuadrigaRoleByDbId(IQuadrigaRoleManager.DICT_ROLES, roleName).getDisplayName());
					roleManager.fillQuadrigaRole(IQuadrigaRoleManager.DICT_ROLES, collaboratorRole);
					collaboratorRoleList.add(collaboratorRole);
				}
				collaborator.setCollaboratorRoles(collaboratorRoleList);
				dictionaryCollaborators.add(collaborator);
			}
		}
		
		return dictionaryCollaborators;
	}
	
	/**
	 * This retrieve the collaborator roles associated with the dictionary collaborators
	 * and stores them in the HashMap
	 * @param dictionary
	 * @return HashMap<String,List<String>>
	 */
	public HashMap<String,List<String>> mapUserRoles(DictionaryDTO dictionary)
	{
		HashMap<String,List<String>> collabMap = new HashMap<String, List<String>>();
		List<String> roleList = null;
		
		for(DictionaryCollaboratorDTO dictionaryCollaborator : dictionary.getDictionaryCollaboratorDTOList())
		{
			String userName = dictionaryCollaborator.getQuadrigaUserDTO().getUsername();
			if(collabMap.containsKey(userName))
			{
				collabMap.get(userName).add(dictionaryCollaborator.getDictionaryCollaboratorDTOPK().getCollaboratorrole());
			}
			else
			{
				roleList = new ArrayList<String>();
				roleList.add(dictionaryCollaborator.getDictionaryCollaboratorDTOPK().getCollaboratorrole());
				collabMap.put(userName,roleList);
			}
		}
		return collabMap;
	}
}
