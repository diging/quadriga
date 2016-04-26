package edu.asu.spring.quadriga.dao.impl.workbench;

import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.workbench.IPublicPageDAO;
import edu.asu.spring.quadriga.dto.PublicPageDTO;

@Repository
public class PublicPageDAO extends BaseDAO<PublicPageDTO> implements IPublicPageDAO {

	@Override
	public PublicPageDTO getDTO(String id) {
		return getDTO(PublicPageDTO.class, id);
	}

	@Override
	public String getIdPrefix() {
		return messages.getProperty("publicpage_id.prefix");
	}

}
