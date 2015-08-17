package edu.asu.spring.quadriga.dao.impl.conceptcollection;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.conceptcollection.IDBConnectionModifyCCManager;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class ConceptCollectionModifyManagerDAO extends BaseDAO<ConceptCollectionDTO> implements IDBConnectionModifyCCManager
{
	@Autowired
	private SessionFactory sessionFactory;

	/**
	 * This method transfers the ownership of the concept collection
	 * to other user and assigns the current user as a collaborator.
	 * @param : collectionId - Concept Collection id
	 * @param : oldOwner - current owner of the Concept Collection
	 * @param : newOwner - new owner to be associated with the Concept Collection
	 * @param : collabRole - the collaborator role to be associated to the current owner
	 * @throws : QuadrigaStorageException
	 */
	@Override
	public void transferCollectionOwnerRequest(String collectionId,
			String oldOwner, String newOwner, String collabRole)
			throws QuadrigaStorageException 
	{
		ConceptCollectionDTO conceptCollection = null;
		List<ConceptCollectionCollaboratorDTO> conceptCollectionCollaborator = null;
		ConceptCollectionCollaboratorDTO collaborator = null;
		
		try
		{
			Date date = new Date();
			conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, collectionId);
			//update the owner of the concept collection
			conceptCollection.setCollectionowner(getUserDTO(newOwner));
			conceptCollection.setUpdatedby(oldOwner);
			conceptCollection.setUpdateddate(date);
			
			//remove the new owner from the collaborator role
			conceptCollectionCollaborator = conceptCollection.getConceptCollectionCollaboratorDTOList();
			Iterator<ConceptCollectionCollaboratorDTO> collaboratorIterator = conceptCollectionCollaborator.iterator();
			while(collaboratorIterator.hasNext())
			{
				collaborator = collaboratorIterator.next();
				if(collaborator.getQuadrigaUserDTO().getUsername().equals(newOwner))
				{
					collaboratorIterator.remove();
				}
			}
			
			//add the existing owner as collaborator admin role
			collaborator = new ConceptCollectionCollaboratorDTO();
			collaborator.setConceptCollectionDTO(conceptCollection);
			collaborator.setCollaboratorDTOPK(new ConceptCollectionCollaboratorDTOPK(collectionId,oldOwner,collabRole));
			collaborator.setQuadrigaUserDTO(getUserDTO(oldOwner));
			collaborator.setCreatedby(oldOwner);
			collaborator.setCreateddate(date);
			collaborator.setUpdatedby(oldOwner);
			collaborator.setUpdateddate(date);
			conceptCollectionCollaborator.add(collaborator);
			
			sessionFactory.getCurrentSession().update(conceptCollection);
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
	}

	/**
	 * This method updates the concept collection details
	 * @param : collection - Concept Collection object
	 * @param : userName - logged in user
	 * @throws : QuadrigaStorageException
	 */
	@Override
	public void updateCollectionDetails(IConceptCollection collection,
			String userName) throws QuadrigaStorageException 
	{
		ConceptCollectionDTO conceptCollection = null;
		try
		{
			Date date = new Date();
			conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, collection.getConceptCollectionId());
		    conceptCollection.setCollectionname(collection.getConceptCollectionName());
		    conceptCollection.setDescription(collection.getDescription());
		    conceptCollection.setAccessibility(Boolean.FALSE);
		    conceptCollection.setUpdatedby(userName);
		    conceptCollection.setUpdateddate(date);
		    sessionFactory.getCurrentSession().update(conceptCollection);
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		
	}

    @Override
    public ConceptCollectionDTO getDTO(String id) {
        return getDTO(ConceptCollectionDTO.class, id);
    }

}
