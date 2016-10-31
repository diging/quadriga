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

    /**
     * This method saves a the contents of the about text - title and
     * description of a public website to the database.
     * 
     * @param projectId
     *            Id of the project that the about text belongs to.
     * @param abtText
     *            Backing bean of the About text from the controller.
     * @throws QuadrigaStorageException
     */
    public void saveAbout(String projectId, IAboutText abtText) throws QuadrigaStorageException;

    /**
     * This method retrieves the baout text for a public project from the
     * database.
     * 
     * @param projectId
     *            Id of the public project whose about text is to be retrieved.
     * @return Returns a {@link IAboutText} corresponsding to the project id.
     * @throws QuadrigaStorageException
     */
    public IAboutText getAboutTextByProjectId(String projectId) throws QuadrigaStorageException;
}
