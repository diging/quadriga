package edu.asu.spring.quadriga.service.publicwebsite.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.publicwebsite.IAboutTextDAO;
import edu.asu.spring.quadriga.domain.settings.IAboutText;
import edu.asu.spring.quadriga.dto.AboutTextDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.publicwebsite.IAboutTextManager;
import edu.asu.spring.quadriga.service.publicwebsite.mapper.IAboutTextMapper;
import edu.asu.spring.quadriga.service.publicwebsite.mapper.impl.AboutTextMapper;

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

    @Autowired
    private IAboutTextMapper abtTxtMapper;

    @Transactional
    @Override
    public void saveAbout(String projectId, IAboutText abtText) throws QuadrigaStorageException {
        if (abtText.getId() == null) {
            abtText.setId(aboutTextDAO.generateUniqueID());
        }
        abtText.setProjectId(projectId);

        AboutTextDTO aboutTextDTO = abtTxtMapper.aboutTextBeanToDTO(abtText);

        aboutTextDAO.saveOrUpdateDTO(aboutTextDTO);
    }

    @Transactional
    @Override
    public IAboutText getAboutTextByProjectId(String projectId) throws QuadrigaStorageException {
         AboutTextDTO abtDTO = aboutTextDAO.getDTOByProjectId(projectId);
        if (abtDTO != null) {
            return abtTxtMapper.aboutTextDTOtoBean(abtDTO);
        }
        return null;
    }
}