package edu.asu.spring.quadriga.service.publicwebsite;

import edu.asu.spring.quadriga.domain.settings.IAboutText;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface for AboutTextManager which is service to facilitate storage of
 * about text data.
 * 
 * @author Rajat Aggarwal
 *
 */
public interface IAboutTextManager {

	public void saveAbout(String projectId, String title, String description) throws QuadrigaStorageException;
	public IAboutText getAboutTextByProjectId(String projectId) throws QuadrigaStorageException;
}
