package edu.asu.spring.quadriga.service.publicwebsite;

import edu.asu.spring.quadriga.dto.AboutTextDTO;

/**
 * Interface for AboutTextManager which is service to facilitate storage of
 * about text data.
 * 
 * @author Rajat Aggarwal
 *
 */
public interface IAboutTextManager {

	public void saveAbout(String projectId, String title, String description);
	public AboutTextDTO getDTOByProjectId(String projectId);
}
