package edu.asu.spring.quadriga.dao.impl.publicwebsite;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.publicwebsite.IAboutTextDAO;
import edu.asu.spring.quadriga.dto.AboutTextDTO;

/**
 * DAO class for public website's about project text.
 * 
 * @author Rajat Aggarwal
 *
 */

@Repository
public class AboutTextDAO extends BaseDAO<AboutTextDTO> implements IAboutTextDAO {

	/**
	 * 
	 * @param projectId
	 *            Returns a DTO if already present for the given projectId
	 * @return
	 */

	public AboutTextDTO getDTOByProjectId(String projectId) {
		AboutTextDTO aboutTextDTO;
		try {
			Query query = sessionFactory.getCurrentSession().getNamedQuery("AboutTextDTO.findByProjectId");
			query.setParameter("projectId", projectId);
			List<AboutTextDTO> aboutTextDTOList = query.list();
			if (aboutTextDTOList.size() != 0)
				aboutTextDTO = aboutTextDTOList.get(0);
			else
				return null;
		} catch (HibernateException e) {
			System.out.println("Error while retrieving aboutTextDTO");
			return null;
		}

		return aboutTextDTO;
	}

	@Override
	public AboutTextDTO getDTO(String id) {
		return getDTO(AboutTextDTO.class, id);
	}

}
