package edu.asu.spring.quadriga.service.impl.workbench;

import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.impl.workbench.PublicPageDAO;
import edu.asu.spring.quadriga.dao.workbench.IPublicPageDAO;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.dto.PublicPageDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.PublicPageDTOMapper;
import edu.asu.spring.quadriga.service.workbench.IModifyPublicPageContentManager;

@Service
public class ModifyPublicPageContentManager implements IModifyPublicPageContentManager {
    
    @Autowired
    private IPublicPageDAO publicPageDao;

    @Autowired
    private PublicPageDTOMapper publicPageDTOMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void addNewPublicPageContent(IPublicPage publicPage) {
        publicPage.setPublicPageId(publicPageDao.generateUniqueID());
        PublicPageDTO publicPageDTO = publicPageDTOMapper.getPublicPageDTO(publicPage);
        publicPageDao.saveNewDTO(publicPageDTO);
    }
}
