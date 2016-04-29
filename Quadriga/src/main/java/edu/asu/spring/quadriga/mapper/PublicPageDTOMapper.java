package edu.asu.spring.quadriga.mapper;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.workbench.PublicPage;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service
public class PublicPageDTOMapper extends BaseMapper {

	public IPublicPage getPublicPage(PublicPageDTO publicPageDTO) throws QuadrigaStorageException {
		IPublicPage publicPage = new PublicPage();
		publicPage.setPublicPageId(publicPageDTO.getPublicpageid());
		publicPage.setTitle(publicPageDTO.getTitle());
		publicPage.setDescription(publicPageDTO.getDescription());
		publicPage.setOrder(publicPageDTO.getEntryorder());
		publicPage.setProjectId(publicPageDTO.getProjectid());
		return publicPage;
	}

	public PublicPageDTO getPublicPageDTO(IPublicPage publicPage) {
		PublicPageDTO publicPageDTO = new PublicPageDTO();
		publicPageDTO.setPublicpageid(publicPage.getPublicPageId());
		publicPageDTO.setTitle(publicPage.getTitle());
		publicPageDTO.setDescription(publicPage.getDescription());
		publicPageDTO.setEntryorder(publicPage.getOrder());
		publicPageDTO.setProjectid(publicPage.getProjectId());
		return publicPageDTO;
	}
}
