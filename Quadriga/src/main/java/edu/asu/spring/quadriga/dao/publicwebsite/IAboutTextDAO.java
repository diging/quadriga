package edu.asu.spring.quadriga.dao.publicwebsite;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface for editing About text of public website of project.
 * 
 * @author Rajat Aggarwal
 *
 */

public interface IAboutTextDAO extends IBaseDAO<AboutTextDTO> {

    AboutTextDTO getDTOByProjectId(String projectId) throws QuadrigaStorageException;
    

}
