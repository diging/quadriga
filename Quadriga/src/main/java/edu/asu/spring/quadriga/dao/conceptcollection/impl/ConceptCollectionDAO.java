package edu.asu.spring.quadriga.dao.conceptcollection.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionItemsDTO;
import edu.asu.spring.quadriga.dto.ConceptCollectionItemsDTOPK;
import edu.asu.spring.quadriga.dto.ConceptsDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.ConceptCollectionDTOMapper;
import edu.asu.spring.quadriga.service.conceptcollection.mapper.IConceptCollectionShallowMapper;

/**
 * @author karthik
 *
 */
@Repository
public class ConceptCollectionDAO extends BaseDAO<ConceptCollectionDTO> implements IConceptCollectionDAO {

    
    @Autowired
    protected ConceptCollectionDTOMapper conceptCollectionDTOMapper;

    @Autowired
    private IConceptCollectionShallowMapper ccShallowMapper;

    /**
     * Queries the database and builds a list of concept collection objects
     * owned by particular user
     * 
     * @param Username
     * @return List containing user objects of all collections of the user
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConceptCollectionDTO> getConceptsOwnedbyUser(String userName) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession()
                    .createQuery("from ConceptCollectionDTO concept where concept.collectionowner.username =:userName");
            query.setParameter("userName", userName);
            return query.list();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }

    }

    /**
     * Queries the database and builds a list of concept collection objects with
     * the user as collaborator
     * 
     * @return List containing user objects of all collections of the user
     * @throws QuadrigaStorageException
     * @author Sowjanya Ambati
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<ConceptCollectionDTO> getCollaboratedConceptsofUser(String userName) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "Select conceptCollab.conceptCollectionDTO from ConceptCollectionCollaboratorDTO conceptCollab where conceptCollab.quadrigaUserDTO.username =:userName");
            query.setParameter("userName", userName);
            return query.list();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * Save the concept collection to the database
     * 
     * @return List containing user objects of all collections of the user
     * @throws QuadrigaStorageException
     * @author Karthik Jayaraman
     */
    @Override
    public void addCollection(IConceptCollection conceptCollection) throws QuadrigaStorageException {
        try {
            ConceptCollectionDTO conceptcollectionsDTO = conceptCollectionDTOMapper
                    .getConceptCollectionDTO(conceptCollection);
            conceptcollectionsDTO.setConceptCollectionid(generateUniqueID());
            sessionFactory.getCurrentSession().save(conceptcollectionsDTO);
            conceptCollection.setConceptCollectionId(conceptcollectionsDTO.getConceptCollectionid());
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * This method retrieves the collection details for the given concept
     * collection
     * 
     * @param :
     *            IConceptCollection - concept collection object
     * @param :
     *            username - logged in user
     */
    @Override
    public ConceptCollectionDTO getCollectionDetails(String collectionId, String username)
            throws QuadrigaStorageException, QuadrigaAccessException {
        IUser owner = null;
        ConceptCollectionDTO conceptcollectionsDTO = null;
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from ConceptCollectionDTO conceptColl where conceptColl.conceptCollectionid =:conceptCollectionid");
            query.setParameter("conceptCollectionid", collectionId);

            return (ConceptCollectionDTO) query.uniqueResult();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void saveItem(String lemma, String item, String pos, String desc, String collectionId, String username)
            throws QuadrigaStorageException, QuadrigaAccessException {
        List<QuadrigaUserDTO> quadrigaUserDTOList = new ArrayList<QuadrigaUserDTO>();
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from QuadrigaUserDTO user where user.username =:username and ( user.username IN (Select quadrigaUserDTO.username from ConceptCollectionCollaboratorDTO ccCollab where ccCollab.conceptCollectionDTO.conceptCollectionid =:conceptCollectionid) OR user.username IN (Select conceptColl.collectionowner.username from ConceptCollectionDTO conceptColl where conceptColl.conceptCollectionid =:conceptCollectionid))");
            query.setParameter("username", username);
            query.setParameter("conceptCollectionid", collectionId);

            ConceptCollectionDTO conceptCollection = (ConceptCollectionDTO) sessionFactory.getCurrentSession()
                    .get(ConceptCollectionDTO.class, collectionId);

            if (conceptCollection == null) {
                throw new QuadrigaStorageException();
            }
            quadrigaUserDTOList = query.list();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
        if (quadrigaUserDTOList != null && quadrigaUserDTOList.size() > 0) {
            ConceptsDTO conceptsDTO = (ConceptsDTO) sessionFactory.getCurrentSession().get(ConceptsDTO.class, item);
            if (conceptsDTO == null) {
                // create a new concept
                conceptsDTO = new ConceptsDTO();
                conceptsDTO.setItem(item);
                conceptsDTO.setCreateddate(new Date());
                conceptsDTO.setDescription(desc);
                conceptsDTO.setLemma(lemma);
                conceptsDTO.setPos(pos);
                conceptsDTO.setUpdateddate(new Date());
                sessionFactory.getCurrentSession().save(conceptsDTO);
            }

            // map the concept to the concept collection
            ConceptCollectionItemsDTO conceptCollectionMapping = new ConceptCollectionItemsDTO();
            ConceptCollectionItemsDTOPK conceptCollectionItemsDTOPK = new ConceptCollectionItemsDTOPK();
            conceptCollectionItemsDTOPK.setConcept(item);
            conceptCollectionItemsDTOPK.setConceptcollectionid(collectionId);
            conceptCollectionMapping.setConceptcollectionsItemsDTOPK(conceptCollectionItemsDTOPK);
            conceptCollectionMapping.setCreateddate(new Date());
            conceptCollectionMapping.setUpdateddate(new Date());
            sessionFactory.getCurrentSession().save(conceptCollectionMapping);

        } else {
            throw new QuadrigaAccessException("User does not have access to the collection");
        }
    }

    /**
     * This method checks if the given collection name exits in the system
     * 
     * @param :
     *            collectionname - concept collection name
     * @throws :
     *             QuadrigaStorageException
     * @return : String
     */
    @SuppressWarnings("unchecked")
    @Override
    public String validateId(String collectionname) throws QuadrigaStorageException {
        String errMsg = null;
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("ConceptCollectionDTO.findByCollectionname");
            query.setParameter("collectionname", collectionname);
            List<ConceptCollectionDTO> conceptcollectionsDTOList = query.list();

            if (!(conceptcollectionsDTOList != null && conceptcollectionsDTOList.size() > 0)) {
                errMsg = "Collection id is invalid.";
            }
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
        return errMsg;
    }

    /**
     * This method deletes the given items associated with the specified concept
     * collection
     * 
     * @param :
     *            id - concept collection associated item ids.
     * @param :
     *            collectionid - concept collection id
     * @param :
     *            username - logged in user
     */
    @SuppressWarnings("unchecked")
    @Override
    public void deleteItems(String id, String collectionId, String username) throws QuadrigaStorageException {
        try {
            Query query = sessionFactory.getCurrentSession().createQuery(
                    "from ConceptCollectionItemsDTO ccItems where ccItems.conceptCollectionItemsDTOPK.conceptcollectionid =:collectionId and ccItems.conceptCollectionItemsDTOPK.concept =:id");
            query.setParameter("id", id);
            query.setParameter("collectionId", collectionId);
            List<ConceptCollectionItemsDTO> ccItemsDTOList = query.list();

            if (ccItemsDTOList != null && ccItemsDTOList.size() > 0) {
                sessionFactory.getCurrentSession().delete(ccItemsDTOList.get(0));
            } else {
                throw new QuadrigaStorageException("Collection id is invalid.");
            }
            Query query1 = sessionFactory.getCurrentSession().createQuery(
                    "from ConceptCollectionItemsDTO ccItems where ccItems.conceptCollectionItemsDTOPK.concept =:id");
            query1.setParameter("id", id);
            List<ConceptCollectionItemsDTO> ccItemsDTOList1 = query1.list();
            if (ccItemsDTOList1 == null || ccItemsDTOList1.size() == 0) {
                Query query2 = sessionFactory.getCurrentSession()
                        .createQuery("from ConceptsDTO concepts where concepts.item =:id");
                query2.setParameter("id", id);
                List<ConceptsDTO> conceptsList = query2.list();
                if (conceptsList != null && conceptsList.size() > 0) {
                    sessionFactory.getCurrentSession().delete(conceptsList.get(0));
                }
            }

        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
    }

    /**
     * This method updates the concepts associated with the concept collection
     * 
     * @param :
     *            concept - concept associated with the concept collection
     * @param :
     *            collectionid - concept collection id
     * @param :
     *            username - logged in user
     * @return : String
     * @throws :
     *             QuadrigaStorageException
     */
    @SuppressWarnings("unchecked")
    @Override
    public void updateItem(IConcept concept, String collectionId, String username) throws QuadrigaStorageException {
        List<ConceptsDTO> conceptsDTOList = new ArrayList<ConceptsDTO>();
        try {
            Query query = sessionFactory.getCurrentSession().createQuery("from ConceptsDTO c where c.item =:id");
            query.setParameter("id", concept.getConceptId());
            conceptsDTOList = query.list();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
        if (conceptsDTOList != null && conceptsDTOList.size() > 0) {
            ConceptsDTO conceptsDTO = conceptsDTOList.get(0);
            conceptsDTO.setLemma(concept.getLemma());
            conceptsDTO.setPos(concept.getPos());
            conceptsDTO.setDescription(concept.getDescription());
            conceptsDTO.setUpdateddate(new Date());
            sessionFactory.getCurrentSession().update(conceptsDTO);
        } else {
            throw new QuadrigaStorageException("Concept id is invalid.");
        }

    }

    /**
     * This method retrieves the concept collection id associated with the given
     * concept collection name
     * 
     * @param :
     *            ccName - concept collection name
     * @return : String - concept collection id
     * @throws :
     *             QuadrigaStorageException
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getConceptCollectionId(String ccName) throws QuadrigaStorageException {
        String ccID = null;
        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery("ConceptCollectionDTO.findByCollectionname");
            query.setParameter("collectionname", ccName);
            List<ConceptCollectionDTO> ccIDList = query.list();

            if (ccIDList != null && ccIDList.size() > 0) {
                ccID = ccIDList.get(0).getConceptCollectionid();
            }
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
        return ccID;
    }

    @Override
    public ConceptCollectionDTO getDTO(String id) {
        return getDTO(ConceptCollectionDTO.class, id);
    }

    @Override
    public String getIdPrefix() {
        return messages.getProperty("concept_collection_id.prefix");
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public List<IConceptCollection> getNonAssociatedProjectConcepts(String projectId) throws QuadrigaStorageException {
        IConceptCollection conceptCollection = null;
        List<IConceptCollection> conceptCollectionList = new ArrayList<IConceptCollection>();
        List<ConceptCollectionDTO> conceptCollectionDTOList = new ArrayList<ConceptCollectionDTO>();
        try {

            Query query = sessionFactory.getCurrentSession()
                    .createQuery("FROM ConceptCollectionDTO cc WHERE cc.conceptCollectionid NOT IN("
                            + "SELECT p.projectConceptcollectionDTOPK.conceptcollectionid FROM ProjectConceptCollectionDTO p WHERE p.projectConceptcollectionDTOPK.projectid = :projectid)");
            query.setParameter("projectid", projectId);
            conceptCollectionDTOList = query.list();
        } catch (HibernateException e) {
            throw new QuadrigaStorageException(e);
        }
        for (ConceptCollectionDTO conceptCollectionDTO : conceptCollectionDTOList) {
            conceptCollection = ccShallowMapper.getConceptCollectionDetails(conceptCollectionDTO);
            conceptCollectionList.add(conceptCollection);
        }

        return conceptCollectionList;
    }

}
