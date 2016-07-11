package edu.asu.spring.quadriga.service.conceptcollection.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IModifyConceptCollectionManager;

@Service
public class ModifyConceptCollectionManager implements
        IModifyConceptCollectionManager {

    @Autowired
    private IConceptCollectionDAO ccDao;

    private static final Logger logger = LoggerFactory
            .getLogger(ModifyConceptCollectionManager.class);

    /**
     * This method updates the concept collection details for given concept
     * collection
     * 
     * @param collection
     *            concept collection object
     * @param userName
     *            - logged in user name
     */
    @Override
    @Transactional
    public void updateCollectionDetails(IConceptCollection collection,
            String userName) throws QuadrigaStorageException {
        ConceptCollectionDTO conceptCollection = ccDao.getDTO(collection.getConceptCollectionId());
        conceptCollection.setCollectionname(collection
                .getConceptCollectionName());
        conceptCollection.setDescription(collection.getDescription());
        conceptCollection.setAccessibility(Boolean.FALSE);
        conceptCollection.setUpdatedby(userName);
        conceptCollection.setUpdateddate(new Date());
        ccDao.updateDTO(conceptCollection);
    }

}
