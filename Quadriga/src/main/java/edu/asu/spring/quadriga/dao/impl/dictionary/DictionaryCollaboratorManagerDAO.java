package edu.asu.spring.quadriga.dao.impl.dictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.dictionary.IDBConnectionDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.impl.workbench.ProjectCollaboratorDAO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryCollaboratorDTOMapper;

@Repository
public class DictionaryCollaboratorManagerDAO extends BaseDAO<DictionaryCollaboratorDTO> implements IDBConnectionDictionaryCollaboratorManager
{
	@Autowired
	private DictionaryCollaboratorDTOMapper collaboratorMapper;

	@Resource(name = "database_error_msgs")
	private Properties messages;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectCollaboratorDAO.class);
	
	/**
	 * This method update the roles associated with the collaborator for dictionary
	 * @param : dictionaryId - dictionary id
	 * @param : collabUser - collaborator user
	 * @param : collaboratorRole - roles associated with the collaborator
	 * @param : userName - logged in user
	 * @throws : QuadrigatorageException
	 */
	@Override
	public void updateCollaboratorRoles(String dictionaryId, String collabUser,String collaboratorRole, String username) throws QuadrigaStorageException 
	{
		DictionaryDTO dictionaryDTO = null;
		DictionaryCollaboratorDTO dictCollaboratorDTO = null;
		DictionaryCollaboratorDTOPK collaboratorKey = null;
		List<DictionaryCollaboratorDTO> collaboratorList = null;
		List<String> roles = null;
		List<String> existingRoles = null;
		String collaborator;
		String collabRole;
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
				for(String role : roles)
				{
					if(!existingRoles.contains(role))
					{
						dictCollaboratorDTO = collaboratorMapper.getDictionaryCollaboratorDTO(dictionaryDTO, collabUser,username, role);
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
		catch(HibernateException ex)
		{
			logger.error("Error while updating dictionary collaborators",ex);
			throw new QuadrigaStorageException();
		}
	}

    @Override
    public DictionaryCollaboratorDTO getDTO(String id) {
        return getDTO(DictionaryCollaboratorDTO.class, id);
    }
	
}
