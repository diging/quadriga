package edu.asu.spring.quadriga.dao.workbench;

import java.util.List;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPublicPageDAO extends IBaseDAO<PublicPageDTO> {

	public abstract PublicPageDTO getPublicPageDTO(String id);
	public List<PublicPageDTO> getPublicPageDTOListByProjectId(String projectId, Integer limit) throws QuadrigaStorageException;
 }