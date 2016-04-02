package edu.asu.spring.quadriga.service.publicwebsite;

/**
 * Interface for AboutTextManager which is service to facilitate storage of
 * about text data.
 * 
 * @author Rajat Aggarwal
 *
 */
public interface IAboutTextManager {

	public void saveAbout(String projectId, String title, String description);

	public String getAboutTitle(String projectId);

	public String getAboutDescription(String projectId);
}
