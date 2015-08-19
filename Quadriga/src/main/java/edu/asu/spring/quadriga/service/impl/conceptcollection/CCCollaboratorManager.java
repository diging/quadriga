package edu.asu.spring.quadriga.service.impl.conceptcollection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionCollaboratorDAO;
import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.dao.impl.conceptcollection.ConceptCollectionDAO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTOPK;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
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
	
	@Override
    public ConceptCollectionCollaboratorDTO createNewCollaboratorDTO() {
        return new ConceptCollectionCollaboratorDTO();
    }

    @Override
    public ConceptCollectionCollaboratorDTOPK createNewCollaboratorDTOPK(String id,
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
