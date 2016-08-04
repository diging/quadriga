package edu.asu.spring.quadriga.dao.workbench.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IPublicPageDAO;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Repository
public class PublicPageDAO extends BaseDAO<PublicPageDTO> implements IPublicPageDAO {

	@Override
	public PublicPageDTO getDTO(String id) {
		return getDTO(PublicPageDTO.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PublicPageDTO> getPublicPageDTOsByProjectId(String projectId) throws QuadrigaStorageException {
		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("from PublicPageDTO page where page.projectid = :projectId");
			query.setParameter("projectId", projectId);
			return query.list();
		} catch (HibernateException e) {
			throw new QuadrigaStorageException(e);
		}
	}
}
