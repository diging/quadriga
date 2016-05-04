package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPublicPageDAO extends IBaseDAO<PublicPageDTO> {
    List<PublicPageDTO> getPublicPageDTOsByProjectId(String projectId) throws QuadrigaStorageException;
}
