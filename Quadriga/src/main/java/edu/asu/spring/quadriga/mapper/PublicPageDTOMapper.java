package edu.asu.spring.quadriga.mapper;


import org.springframework.stereotype.Service;
import edu.asu.spring.quadriga.domain.impl.workbench.PublicPage;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


@Service
public class PublicPageDTOMapper extends BaseMapper {

	
	public IPublicPage getPublicPage(PublicPageDTO publicPageDTO)  throws QuadrigaStorageException {
		IPublicPage publicPage = new PublicPage();
		publicPage.setTitle(publicPageDTO.getTitle());
		publicPage.setDescription(publicPageDTO.getDescription());
		publicPage.setOrder(publicPageDTO.getOrder());
		return publicPage;
	}
	
	public PublicPageDTO getPublicPageDTO(IPublicPage publicPage) {
		
		PublicPageDTO publicPageDTO = new PublicPageDTO();
		publicPageDTO.setTitle(publicPage.getTitle());
		publicPageDTO.setDescription(publicPage.getDescription());
		publicPageDTO.setOrder(publicPage.getOrder());
		return publicPageDTO;
	}
}
