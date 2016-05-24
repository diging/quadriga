package edu.asu.spring.quadriga.dao.impl.dictionary;

import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.dictionary.IDictionaryDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.dto.DictionaryDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTO;
import edu.asu.spring.quadriga.dto.DictionaryItemsDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.DictionaryDTOMapper;

@Repository
public class DictionaryDAO extends BaseDAO<DictionaryDTO> implements IDictionaryDAO {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private DictionaryDTOMapper dictionaryDTOMapper;

    /**
     * Save a dictionary in the database
     * 
     * @param dictionary
     *            object from the screen
     * @return Error message
     * @throws QuadrigaStorageException
     * @author karthik jayaraman
     */
    @Override
    public void addDictionary(IDictionary dictionary) throws QuadrigaStorageException {
        String dictionaryId = generateUniqueID();
        dictionary.setDictionaryId(dictionaryId);
        DictionaryDTO dictionaryDTO = dictionaryDTOMapper.getDictionaryDTO(dictionary);
        saveNewDTO(dictionaryDTO);
    }

    /**
     * Adds a dictionary item to the database
     * 
     * @param dictionary
     *            id, item, itemid, pos and owner name
     * @return Error message
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addDictionaryItems(String dictionaryId, String item, String termid, String pos, String owner)
            throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid =:dictionaryid and dictItems.dictionaryItemsDTOPK.termid =:termid and dictItems.term =:term and dictItems.pos =:pos");
            query.setParameter("dictionaryid", dictionaryId);
            query.setParameter("termid", termid);
            query.setParameter("term", item);
            query.setParameter("pos", pos);
            List<DictionaryItemsDTO> dictItemsDTOList = query.list();

            if ((dictItemsDTOList.size() == 0) || (dictItemsDTOList == null)) {
                DictionaryDTO dictionary = (DictionaryDTO) sessionFactory.getCurrentSession().get(DictionaryDTO.class,
                        dictionaryId);
                DictionaryItemsDTO dictionaryItems = dictionaryDTOMapper.getDictionaryItemsDTO(dictionary, item, termid,
                        pos, owner);
                sessionFactory.getCurrentSession().save(dictionaryItems);
            }
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Delete a dictionary item to the database corresponding to dictionary
     * ID,Item ID and owner name
     * 
     * @param dictionary
     *            id
     * @return Error message
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public void deleteDictionaryItems(String dictionaryId, String itemid, String ownerName)
            throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid IN ( Select dict.dictionaryid from DictionaryDTO dict where dict.dictionaryid =:dictionaryid and dict.dictionaryowner.username =:username) and dictItems.dictionaryItemsDTOPK.termid =:termid");
            query.setParameter("dictionaryid", dictionaryId);
            query.setParameter("username", ownerName);
            query.setParameter("termid", itemid);

            DictionaryItemsDTO dictItemsDTO = (DictionaryItemsDTO) query.uniqueResult();
            if (dictItemsDTO != null) {
                sessionFactory.getCurrentSession().delete(dictItemsDTO);
            }
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Update a dictionary item to the database corresponding to dictionary ID
     * and Item ID
     * 
     * @param dictionary
     *            id
     * @return Error message
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public void updateDictionaryItems(String dictionaryId, String termid, String term, String pos)
            throws QuadrigaStorageException {
        DictionaryItemsDTO dictionaryItems = null;
        DictionaryItemsDTOPK dictionaryItemsKey = null;
        try {
            dictionaryItemsKey = new DictionaryItemsDTOPK(dictionaryId, termid);
            dictionaryItems = (DictionaryItemsDTO) sessionFactory.getCurrentSession().get(DictionaryItemsDTO.class,
                    dictionaryItemsKey);
            if (dictionaryItems != null) {
                dictionaryItems.setTerm(term);
                dictionaryItems.setPos(pos);
                sessionFactory.getCurrentSession().update(dictionaryItems);
            }
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Delete dictionary from the database corresponding to the dictionary ID
     * and owner name
     * 
     * @param username
     *            and dictionary id
     * @return Error message
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public void deleteDictionary(String dictionaryId) throws QuadrigaStorageException {
        DictionaryDTO dto = getDTO(dictionaryId);
        deleteDTO(dto);
    }

    /**
     * Get user dictionary permission from the database
     * 
     * @param user
     *            id and dictionary id
     * @return Boolean for the permission
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public boolean userDictionaryPerm(String userId, String dictionaryId) throws QuadrigaStorageException {
        Boolean isValidUser = Boolean.FALSE;
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from DictionaryDTO dict where dict.dictionaryid =:dictionaryid and dict.dictionaryowner.username =:username");
            query.setParameter("username", userId);
            query.setParameter("dictionaryid", dictionaryId);
            DictionaryDTO dictionaryDTO = (DictionaryDTO) query.uniqueResult();

            if (dictionaryDTO != null) {
                isValidUser = Boolean.TRUE;
            }
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
        return isValidUser;
    }

    /**
     * Get user dictionary of the user with the collaborator role
     * 
     * @param user
     *            id
     * @return List of Dictionary objects
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @SuppressWarnings({ "unchecked" })
    @Override
    public List<DictionaryDTO> getDictionaryCollabOfUser(String userId) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "Select distinct dictCollab.dictionaryDTO from DictionaryCollaboratorDTO dictCollab where dictCollab.quadrigaUserDTO.username =:username");
            query.setParameter("username", userId);
            return query.list();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Delete Dictionary Items corresponding to a dictionary ID and Term id
     * 
     * @param dictionary
     *            id and term id
     * @return error messages if any
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public void deleteDictionaryItemsCollab(String dictionaryId, String itemid) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "Delete from DictionaryItemsDTO dictItems where dictItems.dictionaryItemsDTOPK.dictionaryid =:dictionaryid and dictItems.dictionaryItemsDTOPK.termid =:termid");
            query.setParameter("dictionaryid", dictionaryId);
            query.setParameter("termid", itemid);
            int output = query.executeUpdate();
            if (output == 0) {
                throw new QuadrigaStorageException("Item does not exist in the dictionary");
            }
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Get Dictionary ID name corresponding to a dictionary ID
     * 
     * @param dictionary
     *            id
     * @return Owner username
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public String getDictionaryId(String dictName) throws QuadrigaStorageException {
        String dictID = null;
        DictionaryDTO dictDTO = null;
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("DictionaryDTO.findByDictionaryname");
            query.setParameter("dictionaryname", dictName);
            dictDTO = (DictionaryDTO) query.uniqueResult();

            if (dictDTO != null) {
                dictID = dictDTO.getDictionaryid();
            }
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
        return dictID;
    }

    /**
     * This method retrieves a list of DictionaryDTOs of which the provided user is the owner.
     * 
     * @param userName
     *            Username of owner
     * @throws QuadrigaStorageException
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<DictionaryDTO> getDictionaryDTOList(String userName) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("DictionaryDTO.findByUsername");
            query.setParameter("username", userName);
            return query.list();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DictionaryDTO> getNonAssociatedProjectDictionaries(String projectId) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("FROM DictionaryDTO dict WHERE dict.dictionaryid NOT IN ("
                            + "SELECT p.projectDictionaryDTOPK.dictionaryid FROM ProjectDictionaryDTO p WHERE p.projectDictionaryDTOPK.projectid = :projectid)");
            query.setParameter("projectid", projectId);
            return query.list();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    @Override
    public DictionaryDTO getDTO(String id) {
        return getDTO(DictionaryDTO.class, id);
    }

    /**
     * Update the dictionary details
     * 
     * @param dictionary
     *            object and userName
     * @return void
     * @throws QuadrigaStorageException
     */
    @Override
    public void updateDictionary(IDictionary dictionary, String userName) throws QuadrigaStorageException {
        DictionaryDTO dictionaryDTO = getDTO(dictionary.getDictionaryId());
        dictionaryDTO.setDictionaryname(dictionary.getDictionaryName());
        dictionaryDTO.setDescription(dictionary.getDescription());
        dictionaryDTO.setAccessibility(Boolean.FALSE);
        dictionaryDTO.setUpdatedby(userName);
        dictionaryDTO.setUpdateddate(new Date());
        updateDTO(dictionaryDTO);
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("dictionary_id.prefix");
    }

}
