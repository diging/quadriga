package edu.asu.spring.quadriga.dao.impl.publicwebsite;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.publicwebsite.IAboutTextDAO;
import edu.asu.spring.quadriga.dto.AboutTextDTO;

public class AboutTextDAO extends BaseDAO<AboutTextDTO> implements IAboutTextDAO<AboutTextDTO> {

    public AboutTextDTO getDTO(String id) {
        return getDTO(AboutTextDTO.class, id);
    }

}
