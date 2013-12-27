package edu.asu.spring.quadriga.dao.conceptcollection.impl;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.sql.DAOConnectionManager;
import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionModifyCCManager;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class ConceptCollectionModifyManager extends DAOConnectionManager implements IDBConnectionModifyCCManager
{
	@Autowired
	private SessionFactory sessionFactory;

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
			collaborator.setConceptCollectionCollaboratorDTOPK(new ConceptCollectionCollaboratorDTOPK(collectionId,oldOwner,collabRole));
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

	@Override
	public void updateCollectionDetails(IConceptCollection collection,
			String userName) throws QuadrigaStorageException 
	{
		ConceptCollectionDTO conceptCollection = null;
		try
		{
			Date date = new Date();
			conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession().get(ConceptCollectionDTO.class, collection.getId());
		    conceptCollection.setCollectionname(collection.getName());
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

}
