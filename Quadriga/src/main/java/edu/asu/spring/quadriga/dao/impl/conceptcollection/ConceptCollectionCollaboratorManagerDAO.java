package edu.asu.spring.quadriga.dao.impl.conceptcollection;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.conceptcollection.IDBConnectionCCCollaboratorManager;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionCollaboratorDTOMapper;

@Repository
public class ConceptCollectionCollaboratorManagerDAO extends BaseDAO<ConceptCollectionCollaboratorDTO> implements IDBConnectionCCCollaboratorManager
{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ConceptCollectionCollaboratorDTOMapper collaboratorMapper;

	
	private static final Logger logger = LoggerFactory.getLogger(ConceptCollectionCollaboratorManagerDAO.class);
	
	/**
	 * This method adds the collaborator for given concept collection
	 * @param : collaborator - ICollaborator object
	 * @param : collectionid - Concept Collection id
	 * @param : userName - logged in user
	 * @throws : QuadrigaStorageException
	 */
	@Override
	public void addCollaboratorRequest(ICollaborator collaborator,
			String collectionid, String userName)
			throws QuadrigaStorageException 
	{
		 List<ConceptCollectionCollaboratorDTO> conceptCollectionCollaborators = null;
		 ConceptCollectionDTO conceptCollection = null;
		 
		 try
		 {
			 conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,collectionid);
			 conceptCollectionCollaborators = conceptCollection.getConceptCollectionCollaboratorDTOList();
			 
			 if(conceptCollection!=null)
			 {
				 collaboratorMapper.getCollaboratorDAO(conceptCollectionCollaborators, collaborator, collectionid, userName);
				 conceptCollection.setConceptCollectionCollaboratorDTOList(conceptCollectionCollaborators);
				 sessionFactory.getCurrentSession().update(conceptCollection);
			 }
			 
		 }
		 catch(Exception ex)
		 {
			 throw new QuadrigaStorageException(ex);
		 }
		
	}

	/**
	 * This method deletes the collaborator associated with the 
	 * given concept collection
	 * @param : userName - logged in user
	 * @param : collectionid - concept collection id
	 * @throws : QuadrigaStorageException
	 */
	@Override
	public void deleteCollaboratorRequest(String userName, String collectionid)
			throws QuadrigaStorageException 
	{
		List<ConceptCollectionCollaboratorDTO> conceptCollectionCollaborator = null;
		List<String> collaborators = null;
		try
		{
		   ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, collectionid);
		   conceptCollectionCollaborator = conceptCollection.getConceptCollectionCollaboratorDTOList();   
		   collaborators = getList(userName);

		   if(conceptCollection !=null)
		   {
			   Iterator<ConceptCollectionCollaboratorDTO> iterator = conceptCollectionCollaborator.iterator();
			   
			   while(iterator.hasNext())
			   {
				   String collaborator = iterator.next().getQuadrigaUserDTO().getUsername();
				   if(collaborators.contains(collaborator))
				   {
					   iterator.remove();
				   }
			   }
			   conceptCollection.setConceptCollectionCollaboratorDTOList(conceptCollectionCollaborator);
			   
			   sessionFactory.getCurrentSession().update(conceptCollection);
		   }
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		
	}

	/**
	 * This method updates the collaborator roles for the given 
	 * collaborator associated with the specified concept collection
	 * @param : collectionId - concept collection id
	 * @param : collabUser - collaborator user name
	 * @param : collaboratorRole - selected roles for the collaborator
	 * @param : username - logged in  user
	 * @throws : QuadrigaStorageException
	 */
	@Override
	public void updateCollaboratorRequest(String collectionId,
			String collabUser, String collaboratorRole, String username)
			throws QuadrigaStorageException 
	{
		ConceptCollectionDTO conceptCollection = null;
		ConceptCollectionCollaboratorDTO conceptCollectionCollaborator = null;
		ConceptCollectionCollaboratorDTOPK collaboratorKey = null;
		List<ConceptCollectionCollaboratorDTO> collaboratorList = null;
		List<String> roles = null;
		List<String> existingRoles = null;
		QuadrigaUserDTO user = null;
		String collaborator;
		String collabRole;
		Date date = null;
		try
		{
			conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class,collectionId);
			collaboratorList = conceptCollection.getConceptCollectionCollaboratorDTOList();
			roles = getList(collaboratorRole);
			existingRoles = new ArrayList<String>();
			
			if(conceptCollection!=null)
			{
				//remove the user roles which are not associated with the input selection
				Iterator<ConceptCollectionCollaboratorDTO> iterator = collaboratorList.iterator();
				while(iterator.hasNext())
				{
					conceptCollectionCollaborator = iterator.next();
					collaboratorKey = conceptCollectionCollaborator.getConceptCollectionCollaboratorDTOPK();
					collaborator = conceptCollectionCollaborator.getQuadrigaUserDTO().getUsername();
					collabRole = collaboratorKey.getCollaboratorrole();
					if(collaborator.equals(collabUser))
					{
						if(!roles.contains(collabRole))
						{
							iterator.remove();
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
						conceptCollectionCollaborator = new ConceptCollectionCollaboratorDTO();
						collaboratorKey = new ConceptCollectionCollaboratorDTOPK(collectionId,collabUser,role);
						conceptCollectionCollaborator.setConceptCollectionDTO(conceptCollection);
						conceptCollectionCollaborator.setConceptCollectionCollaboratorDTOPK(collaboratorKey);
						conceptCollectionCollaborator.setQuadrigaUserDTO(user);
                        conceptCollectionCollaborator.setCreatedby(username);
                        conceptCollectionCollaborator.setCreateddate(date);
                        conceptCollectionCollaborator.setUpdatedby(username);
                        conceptCollectionCollaborator.setUpdateddate(date);
						collaboratorList.add(conceptCollectionCollaborator);
					}
				}
				
				conceptCollection.setConceptCollectionCollaboratorDTOList(collaboratorList);
				sessionFactory.getCurrentSession().update(conceptCollection);
			}
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		
	}

    @Override
    public ConceptCollectionCollaboratorDTO getDTO(String id) {
        return getDTO(ConceptCollectionCollaboratorDTO.class, id);
    }

}
