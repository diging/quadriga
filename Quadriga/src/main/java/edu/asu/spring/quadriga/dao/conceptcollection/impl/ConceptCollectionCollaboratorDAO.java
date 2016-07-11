package edu.asu.spring.quadriga.dao.conceptcollection.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionCollaboratorDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;

@Repository
public class ConceptCollectionCollaboratorDAO extends
		BaseDAO<ConceptCollectionCollaboratorDTO> implements
		IConceptCollectionCollaboratorDAO {

    @Override
	public ConceptCollectionCollaboratorDTO getDTO(String id) {
		return getDTO(ConceptCollectionCollaboratorDTO.class, id);
	}

	@Override
	public List<QuadrigaUserDTO> getUsersNotCollaborating(String dtoId) {
		Query query = sessionFactory
				.getCurrentSession()
				.createQuery(
						"SELECT user FROM QuadrigaUserDTO user WHERE user.username NOT IN (Select quadrigaUserDTO.username from"
						+ " ConceptCollectionCollaboratorDTO ccCollab where ccCollab.conceptCollectionDTO.conceptCollectionid =:conceptCollectionid)"
						+ "  AND user.username NOT IN (Select ccCollab.conceptCollectionDTO.collectionowner.username from"
						+ " ConceptCollectionCollaboratorDTO ccCollab where ccCollab.conceptCollectionDTO.conceptCollectionid =:conceptCollectionid)");
		query.setParameter("conceptCollectionid", dtoId);

		return query.list();
	}
}
