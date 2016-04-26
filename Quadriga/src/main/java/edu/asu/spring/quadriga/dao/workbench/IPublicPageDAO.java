package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;
import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;

public interface IPublicPageDAO extends IBaseDAO<PublicPageDTO> {

	public abstract PublicPageDTO getPublicPageDTO(String id);

	void insertPublicPageContent(String title, String description, int order, String id)
			throws QuadrigaStorageException;

	int updatePublicPageContent(String title, String description, String id, int order) throws QuadrigaStorageException;

	List<IPublicPage> getPublicPageContent(String id) throws QuadrigaStorageException;
}
