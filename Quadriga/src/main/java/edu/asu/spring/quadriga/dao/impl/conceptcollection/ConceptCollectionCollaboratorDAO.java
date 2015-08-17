package edu.asu.spring.quadriga.dao.impl.conceptcollection;

import java.util.List;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionCollaboratorDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionCollaboratorDTOMapper;

@Repository
public class ConceptCollectionCollaboratorDAO extends BaseDAO<ConceptCollectionCollaboratorDTO> implements IConceptCollectionCollaboratorDAO
{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ConceptCollectionCollaboratorDTOMapper collaboratorMapper;

	
	private static final Logger logger = LoggerFactory.getLogger(ConceptCollectionCollaboratorDAO.class);
	
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

    @Override
    public ConceptCollectionCollaboratorDTO getDTO(String id) {
        return getDTO(ConceptCollectionCollaboratorDTO.class, id);
    }

}
