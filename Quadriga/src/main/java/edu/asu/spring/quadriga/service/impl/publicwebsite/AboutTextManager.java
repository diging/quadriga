package edu.asu.spring.quadriga.service.impl.publicwebsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.publicwebsite.IAboutTextDAO;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;

/**
 * Service to save title and description of public website about page form.
 * 
 * @author Rajat Aggarwal
 *
 */
@Service
public class AboutTextManager implements IAboutTextManager {

	@Autowired
	private IAboutTextDAO aboutTextDAO;

	@Transactional
	@Override
	public void saveAbout(String projectId, String title, String description) {
		AboutTextDTO aboutTextDTO = aboutTextDAO.getDTOByProjectId(projectId);
		aboutTextDTO.setProjectId(projectId);
		aboutTextDTO.setTitle(title);
		aboutTextDTO.setDescription(description);
		aboutTextDAO.saveNewDTO(aboutTextDTO);
	}
}