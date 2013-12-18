package edu.asu.spring.quadriga.dao.sql.dictionary;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.dao.workbench.impl.ModifyProjectCollaboratorDAO;
import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class DictionaryCollaboratorManagerDAO extends DAOConnectionManager implements IDBConnectionDictionaryCollaboratorManager
{

	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	private static final Logger logger = LoggerFactory.getLogger(ModifyProjectCollaboratorDAO.class);
	
	@Override
	public void updateCollaboratorRoles(String dictionaryId, String collabUser,String collaboratorRole, String username) throws QuadrigaStorageException 
	{
		DictionaryDTO dictionaryDTO = null;
		DictionaryCollaboratorDTO dictCollaboratorDTO = null;
		DictionaryCollaboratorDTOPK collaboratorKey = null;
		List<DictionaryCollaboratorDTO> collaboratorList = null;
		List<String> roles = null;
		List<String> existingRoles = null;
		QuadrigaUserDTO user = null;
		String collaborator;
		String collabRole;
		Date date = null;
		try
		{
			dictionaryDTO = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class, dictionaryId);
			collaboratorList = dictionaryDTO.getDictionaryCollaboratorDTOList();
			roles = getList(collaboratorRole);
			existingRoles = new ArrayList<String>();
			
			if(!dictionaryDTO.equals(null))
			{
				//remove the user roles which are not associated with the input selection
				Iterator<DictionaryCollaboratorDTO> dictCollabItr = collaboratorList.iterator();
				while(dictCollabItr.hasNext())
				{
					dictCollaboratorDTO = dictCollabItr.next();
					collaboratorKey = dictCollaboratorDTO.getDictionaryCollaboratorDTOPK();
					collaborator = dictCollaboratorDTO.getQuadrigaUserDTO().getUsername();
					collabRole = collaboratorKey.getCollaboratorrole();
					if(collaborator.equals(collabUser))
					{
						if(!roles.contains(collabRole))
						{
							dictCollabItr.remove();
						}
						else
						{
							existingRoles.add(collabRole);
						}
					}
				}
				
				//add the new roles to the collaborator
				user = getUserDTO(collabUser);
				
				for(String role : roles)
				{
					if(!existingRoles.contains(role))
					{
						date = new Date();
						dictCollaboratorDTO = new DictionaryCollaboratorDTO();
						collaboratorKey = new DictionaryCollaboratorDTOPK(dictionaryId,collabUser,role);
						dictCollaboratorDTO.setDictionaryDTO(dictionaryDTO);
						dictCollaboratorDTO.setDictionaryCollaboratorDTOPK(collaboratorKey);
						dictCollaboratorDTO.setQuadrigaUserDTO(user);
						dictCollaboratorDTO.setCreatedby(username);
						dictCollaboratorDTO.setCreateddate(date);
						dictCollaboratorDTO.setUpdatedby(username);
						dictCollaboratorDTO.setUpdateddate(date);
						collaboratorList.add(dictCollaboratorDTO);
					}
				}
				
				dictionaryDTO.setDictionaryCollaboratorDTOList(collaboratorList);
				sessionFactory.getCurrentSession().update(dictionaryDTO);
			}
			else
			{
				 throw new QuadrigaStorageException(messages.getProperty("projectId_invalid"));
			}
		}
		catch(Exception ex)
		{
			logger.error("Error while updating dictionary collaborators",ex);
			throw new QuadrigaStorageException();
		}
	}
	
}
