package edu.asu.spring.quadriga.dao.impl.workbench;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IPublicPageDAO;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.PublicPageDTOMapper;

@Repository
public class PublicPageDAO extends BaseDAO<PublicPageDTO> implements IPublicPageDAO {

	@Autowired
	private PublicPageDTOMapper pageDTOMapper;

	private static final Logger logger = LoggerFactory.getLogger(PublicPageDAO.class);

	@Override
	public PublicPageDTO getPublicPageDTO(String id) {
		return getDTO(PublicPageDTO.class, id);
	}

	@Override
	public PublicPageDTO getDTO(String id) {
		return getPublicPageDTO(id);
	}

	@Override
	public String getIdPrefix() {
		return messages.getProperty("publicpage_id.prefix");
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<IPublicPage> getPublicPageContent(String id) throws QuadrigaStorageException {
		IPublicPage publicpage2 = null;
		List<IPublicPage> pageList = new ArrayList<IPublicPage>();
		List<PublicPageDTO> pageDTO = new ArrayList<PublicPageDTO>();
		try {

			Query query = sessionFactory.getCurrentSession().createQuery(
					"FROM PublicPageDTO cc WHERE cc.id NOT IN(" + "SELECT p.id FROM PublicPageDTO p WHERE p.id = :id)");
			query.setParameter("id", id);
			pageList = query.list();
		} catch (HibernateException e) {
			throw new QuadrigaStorageException(e);
		}
		for (PublicPageDTO page : pageDTO) {
			publicpage2 = pageDTOMapper.getPublicPage(page);
			pageList.add(publicpage2);
		}

		return pageList;
	}

	@Override
	public void insertPublicPageContent(String title, String description, int order, String id)
			throws QuadrigaStorageException {
		try {
			PublicPageDTO page = (PublicPageDTO) sessionFactory.getCurrentSession().get(PublicPageDTO.class, id);
			if (page == null) {
				// insert the record into the quadriga publicpage table

				PublicPageDTO publicPage = new PublicPageDTO();
				publicPage.setProjectid(id);
				publicPage.setEntryorder(order);
				publicPage.setDescription(description);
				publicPage.setTitle(title);

				sessionFactory.getCurrentSession().save(publicPage);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new QuadrigaStorageException();
		}
	}

	@Override
	public int updatePublicPageContent(String title, String description, String id, int order)
			throws QuadrigaStorageException {

		PublicPageDTO publicpageDTO;
		try {
			publicpageDTO = (PublicPageDTO) sessionFactory.getCurrentSession().get(PublicPageDTO.class, id);
		} catch (Exception e) {
			logger.error("Error in finding the publicpage content with that id: ", e);
			throw new QuadrigaStorageException(e);
		}

		if (publicpageDTO != null) {
			publicpageDTO.setDescription(description);
			publicpageDTO.setTitle(title);
			publicpageDTO.setEntryorder(order);
			sessionFactory.getCurrentSession().update(publicpageDTO);

			return SUCCESS;
		} else {
			return FAILURE;
		}
	}
}

