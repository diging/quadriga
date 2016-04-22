package edu.asu.spring.quadriga.dao.workbench;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.PublicPageDTO;

public interface IPublicPageDAO extends IBaseDAO<PublicPageDTO> {

	public abstract PublicPageDTO getPublicPageDTO(String id);
    }