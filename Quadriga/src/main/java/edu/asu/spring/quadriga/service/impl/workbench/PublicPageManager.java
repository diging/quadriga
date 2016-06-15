package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.IPublicPageDAO;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.PublicPageDTOMapper;
import edu.asu.spring.quadriga.service.workbench.IPublicPageManager;

@Service
public class PublicPageManager implements IPublicPageManager {

	@Autowired
	private IPublicPageDAO publicPageDao;

	@Autowired
	private PublicPageDTOMapper publicPageDTOMapper;

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public void saveOrUpdatePublicPage(IPublicPage publicPage) {
		PublicPageDTO publicPageDTO = publicPageDTOMapper.getPublicPageDTO(publicPage);
		if (publicPage.getPublicPageId() == null || publicPage.getPublicPageId().trim().isEmpty()) {
			publicPageDTO.setPublicpageid(publicPageDao.generateUniqueID());
			publicPageDao.saveNewDTO(publicPageDTO);
			return;
		}
		publicPageDao.updateDTO(publicPageDTO);
	}

	@Override
	@Transactional
	public List<IPublicPage> retrievePublicPageContent(String projectId) throws QuadrigaStorageException {
		List<PublicPageDTO> publicPageDTOs = publicPageDao.getPublicPageDTOsByProjectId(projectId);
		List<IPublicPage> publicPageList = new ArrayList<IPublicPage>();
		for (PublicPageDTO publicPage : publicPageDTOs) {
			IPublicPage publicPageTemp = publicPageDTOMapper.getPublicPage(publicPage);
			publicPageList.add(publicPageTemp);
		}
		return publicPageList;
	}
}
