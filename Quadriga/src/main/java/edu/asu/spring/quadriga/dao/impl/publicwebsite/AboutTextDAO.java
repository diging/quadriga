package edu.asu.spring.quadriga.dao.impl.publicwebsite;

import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.publicwebsite.IAboutTextDAO;
import edu.asu.spring.quadriga.dto.AboutTextDTO;

/**
 * DAO class for public website's about project text.
 * 
 * @author Rajat Aggarwal
 *
 */

@Repository
public class AboutTextDAO extends BaseDAO<AboutTextDTO> implements IAboutTextDAO {

    public AboutTextDTO getDTO(String id) {
        return getDTO(AboutTextDTO.class, id);
    }

}
