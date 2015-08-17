package edu.asu.spring.quadriga.service.impl.conceptcollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionCollaboratorDAO;
import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.dao.impl.conceptcollection.ConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.ICCCollaboratorManager;
import edu.asu.spring.quadriga.service.impl.CollaboratorManager;

@Service
public class CCCollaboratorManager extends CollaboratorManager<ConceptCollectionCollaboratorDTO, ConceptCollectionCollaboratorDTOPK, ConceptCollectionDTO, ConceptCollectionDAO> implements ICCCollaboratorManager 
{
	
	private static final Logger logger = LoggerFactory.getLogger(CCCollaboratorManager.class);
	@Autowired
	private IConceptCollectionCollaboratorDAO ccCollaboratorDao;
	
	@Autowired
	private IConceptCollectionDAO ccDao;
	
	
	/**
	 * This method associated a collaborator to concept collection
	 * @param collaborator - collaborator object containing details of collaborator
	 * @param collectionid - Concept Collection id.
	 * @param userName - logged in user name
	 * @throws QuadrigaStorageException
	 */
	@Override
	@Transactional
	public void addCollaborators(ICollaborator collaborator, String collectionid, String userName)
			throws QuadrigaStorageException 
	{
		ccCollaboratorDao.addCollaboratorRequest(collaborator, collectionid, userName);
	}
	
	@Override
    public ConceptCollectionCollaboratorDTO createNewDTO() {
        return new ConceptCollectionCollaboratorDTO();
    }

    @Override
    public ConceptCollectionCollaboratorDTOPK createNewDTOPK(String id,
            String collabUser, String role) {
        return new ConceptCollectionCollaboratorDTOPK(id, collabUser, role);
    }

    @Override
    public IBaseDAO<ConceptCollectionDTO> getDao() {
        return ccDao;
    }

    @Override
    public IBaseDAO<ConceptCollectionCollaboratorDTO> getCollaboratorDao() {
        return ccCollaboratorDao;
    }
}
