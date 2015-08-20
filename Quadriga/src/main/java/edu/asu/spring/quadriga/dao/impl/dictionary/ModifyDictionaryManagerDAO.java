package edu.asu.spring.quadriga.dao.impl.dictionary;

import java.util.Date;

import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.dictionary.IDBConnectionModifyDictionaryManager;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryCollaboratorDTOMapper;

@Repository
public class ModifyDictionaryManagerDAO extends BaseDAO<DictionaryDTO>
        implements IDBConnectionModifyDictionaryManager {

    @Autowired
    private DictionaryCollaboratorDTOMapper collaboratorMapper;

    private static final Logger logger = LoggerFactory
            .getLogger(ModifyDictionaryManagerDAO.class);

    /**
     * Update the dictionary details
     * 
     * @param dictionary
     *            object and userName
     * @return void
     * @throws QuadrigaStorageException
     */
    @Override
    public void updateDictionaryRequest(IDictionary dictionary, String userName)
            throws QuadrigaStorageException {
        try {
            DictionaryDTO dictionaryDTO = getDTO(dictionary.getDictionaryId());
            dictionaryDTO.setDictionaryname(dictionary.getDictionaryName());
            dictionaryDTO.setDescription(dictionary.getDescription());
            dictionaryDTO.setAccessibility(Boolean.FALSE);
            dictionaryDTO.setUpdatedby(userName);
            dictionaryDTO.setUpdateddate(new Date());
            updateDTO(dictionaryDTO);
        } catch (HibernateException e) {
            logger.error("Update dictionary request :", e);
            throw new QuadrigaStorageException(e);
        }
    }

    @Override
    public DictionaryDTO getDTO(String id) {
        return getDTO(DictionaryDTO.class, id);
    }

}
