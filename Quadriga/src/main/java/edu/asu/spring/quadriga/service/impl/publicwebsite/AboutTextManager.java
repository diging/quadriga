package edu.asu.spring.quadriga.service.impl.publicwebsite;

import edu.asu.spring.quadriga.dao.impl.publicwebsite.AboutTextDAO;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;

public class AboutTextManager implements IAboutTextManager {

    private AboutTextDAO aboutTextDAO = new AboutTextDAO();

    void saveAbout() {
        AboutTextDTO aboutTextDTO = new AboutTextDTO();
        aboutTextDAO.saveNewDTO(aboutTextDTO);
    }
}