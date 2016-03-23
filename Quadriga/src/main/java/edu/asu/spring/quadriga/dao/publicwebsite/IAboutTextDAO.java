package edu.asu.spring.quadriga.dao.publicwebsite;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.AboutTextDTO;

/**
 * Interface for editing About text of public website of project.
 * 
 * @author Rajat Aggarwal
 *
 */

public interface IAboutTextDAO extends IBaseDAO<AboutTextDTO> {

    /**
     * Saving "about project" information in AboutTextDTO object
     * 
     * @author Rajat Aggarwal
     */
    public abstract void saveNewDTO(AboutTextDTO dto);

}
