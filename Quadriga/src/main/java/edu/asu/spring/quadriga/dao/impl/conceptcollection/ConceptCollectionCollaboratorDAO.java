package edu.asu.spring.quadriga.dao.impl.conceptcollection;

import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionCollaboratorDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;

@Repository
public class ConceptCollectionCollaboratorDAO extends BaseDAO<ConceptCollectionCollaboratorDTO> implements IConceptCollectionCollaboratorDAO {

	@Override
    public ConceptCollectionCollaboratorDTO getDTO(String id) {
        return getDTO(ConceptCollectionCollaboratorDTO.class, id);
    }

}
