package edu.asu.spring.quadriga.service.conceptcollection.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerCache;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.dao.conceptcollection.IConceptCollectionDAO;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.conceptcollection.IConcept;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptFactory;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.dto.ConceptCollectionDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.conceptcollection.IConceptCollectionDeepMapper;
import edu.asu.spring.quadriga.mapper.conceptcollection.IConceptCollectionShallowMapper;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

/**
 * 
 * This class has all the Concept collection service layer functions. It
 * includes the handling DB and controller services.
 * 
 * @author satyaswaroop
 *
 */
@Service
public class ConceptCollectionManager implements IConceptCollectionManager {

    @Autowired
    private IConceptCollectionDAO ccDao;

    @Autowired
    private IConceptFactory conceptFactory;

    @Autowired
    private IConceptCollectionDeepMapper conceptCollectionDeepMapper;

    @Autowired
    private IConceptpowerConnector conceptpowerConnector;
    
    @Autowired
    private IConceptpowerCache cpCache;

    @Autowired
    private IQuadrigaRoleManager roleMapper;

    @Autowired
    private IConceptCollectionShallowMapper ccShallowMapper;

    /**
     * This method retrieves the concept collection owner by the submitted user
     * 
     * @param userId
     *            - logged in user
     * @throws QuadrigaStorageException
     * @return List<ConceptCollectionDTO> list of concept collection associated
     *         with the user as owner
     */
    @Override
    @Transactional
    public List<IConceptCollection> getCollectionsOwnedbyUser(String sUserId)
            throws QuadrigaStorageException {
        return ccShallowMapper.getConceptCollectionList(sUserId);
    }

    @Override
    @Transactional
    public List<IConceptCollection> getNonAssociatedProjectConcepts(
            String projectId) throws QuadrigaStorageException {
        return ccDao.getNonAssociatedProjectConcepts(projectId);
    }

    /**
     * This methods retrieves the concept collection associated with the user as
     * a collaborator
     * 
     * @param sUserID
     *            - logged in user id
     * @param List
     *            <IConceptCollection> - list of concept collection associated
     *            with user as a collaborator
     * @throws QuadrigatorageException
     */
    @Override
    @Transactional
    public List<IConceptCollection> getUserCollaborations(String sUserId)
            throws QuadrigaStorageException {
        return ccShallowMapper.getConceptCollectionListOfCollaborator(sUserId);
    }

    /**
     * This method searches the items and its part of speech in the concept
     * power database
     * 
     * @param item
     *            - concept collection item
     * @param pos
     *            - part of speech of item word
     */
    @Override
    public ConceptpowerReply search(String item, String pos) {
        if (item == null || item.isEmpty() || pos == null || pos.isEmpty())
            return null;

        return conceptpowerConnector.search(item, pos);
    }

    /**
     * This method updates the items associated to the concept collection
     * 
     * @param id
     *            [] - array of items associated with the collection
     * @param collection
     *            - concept collection object
     * @param username
     *            - logged in user
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void update(String[] ids, IConceptCollection collection,
            String username) throws QuadrigaStorageException {
        for (String id : ids) {
            if ((id != null && !id.isEmpty())) {
                edu.asu.spring.quadriga.conceptpower.IConcept cpConcept = cpCache.getConceptByUri(id);

                if (cpConcept != null) {
                    IConcept concept = conceptFactory.createConceptObject();
                    concept.setConceptId(id);
                    concept.setDescription(cpConcept.getDescription());
                    concept.setLemma(cpConcept.getWord());
                    concept.setPos(cpConcept.getPos());
                    ccDao.updateItem(concept, collection.getConceptCollectionId(),
                            username);
                }
            }
        }
    }

    /**
     * This method returns Lemma for the given concept
     * 
     * @param id
     *            - item id
     * @return String - lemma associated with concept
     */
    @Override
    public String getConceptLemmaFromConceptId(String id) {
        edu.asu.spring.quadriga.conceptpower.IConcept concept = cpCache.getConceptByUri(id);
        if (concept == null) {
            return id;
        }
        return concept.getWord();
    }

    /**
     * This method returns Description for the given concept
     * 
     * @param id
     *            - item id
     * @return String - Description associated with concept
     */
    @Override
    public String getConceptDescriptionFromConceptId(String id) {

        String desc = "";
        edu.asu.spring.quadriga.conceptpower.IConcept concept = cpCache.getConceptByUri(id);
        if (concept == null) {
            return desc;
        }
        return concept.getDescription();
    }

    /**
     * This method adds the items to the concept collection
     * 
     * @param lemma
     * @param id
     * @param pos
     * @param desc
     * @param conceptcollectionId
     * @param username
     *            - logged in user name
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @Override
    @Transactional
    public void addItems(String lemmma, String id, String pos, String desc,
            String conceptcollectionId, String username)
            throws QuadrigaStorageException, QuadrigaAccessException {
        ccDao.saveItem(lemmma, id, pos, desc, conceptcollectionId, username);
    }

    /**
     * This methods adds concept collection
     * 
     * @param collection
     *            - Concept Collection object
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void addConceptCollection(IConceptCollection collection)
            throws QuadrigaStorageException {
        ccDao.addCollection(collection);
    }

    /**
     * This method deletes the item associated with the concept collection
     * 
     * @param id
     *            - concept id
     * @param collectionid
     *            - concept collection id
     * @param username
     *            - logged in user name
     * @throws QuadrigatorageException
     */
    @Override
    @Transactional
    public void deleteItem(String id, String collectionId, String username)
            throws QuadrigaStorageException {
        ccDao.deleteItems(id, collectionId, username);

    }

    /**
     * @description retrieves collaborating users from database
     * @param collectionid
     * @throws QuadrigaStorageException
     * @return list of collaborator objects
     * @author rohit pendbhaje
     */
    @Override
    @Transactional
    public List<IConceptCollectionCollaborator> showCollaboratingUsers(
            String collectionid) throws QuadrigaStorageException {
        List<IConceptCollectionCollaborator> ccCollaboratorList = null;
        IConceptCollection conceptCollection = conceptCollectionDeepMapper
                .getConceptCollectionDetails(collectionid);
        if (conceptCollection != null) {
            ccCollaboratorList = conceptCollection
                    .getConceptCollectionCollaborators();
        }
        return ccCollaboratorList;
    }

    /**
     * @description retrieves collaborating users from database
     * @param collectioni
     * @throws QuadrigaStorageException
     * @author rohit pendbhaje
     */
    @Override
    @Transactional
    public void getCollaborators(IConceptCollection collection)
            throws QuadrigaStorageException {

        IConceptCollection conceptCollection = conceptCollectionDeepMapper
                .getConceptCollectionDetails(collection
                        .getConceptCollectionId());
        if (conceptCollection != null) {

            List<IConceptCollectionCollaborator> conceptCollectionCollaborators = conceptCollection
                    .getConceptCollectionCollaborators();
            if (conceptCollectionCollaborators != null
                    && conceptCollectionCollaborators.size() > 0) {
                for (IConceptCollectionCollaborator conceptCollectionCollaborator : conceptCollectionCollaborators) {
                    for (IQuadrigaRole collaboratorRole : conceptCollectionCollaborator
                            .getCollaborator().getCollaboratorRoles()) {
                        roleMapper.fillQuadrigaRole(
                                IQuadrigaRoleManager.CONCEPT_COLLECTION_ROLES,
                                collaboratorRole);
                    }
                }
            }
        }
    }

    /**
     * This method retrieves the concept collection id for the given concept
     * collection name
     * 
     * @param ccName
     *            - concept collection name
     * @return String - concept collection id
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public String getConceptCollectionId(String ccName)
            throws QuadrigaStorageException {
        return ccDao.getConceptCollectionId(ccName);
    }

    /**
     * This method retrieves a concept collection given its id.
     * 
     * @param id
     *            Id of the concept collection to retrieve.
     * @return
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public IConceptCollection getConceptCollection(String id)
            throws QuadrigaStorageException {
        return conceptCollectionDeepMapper.getConceptCollectionDetails(id);
    }

    /**
     * This method retrieves the dto corresponding to the id of the provided
     * concept collection and fills the provided concept collection with the
     * data from the dto. Note that if data is already present in the concept
     * collection, they will be overridden.
     * 
     * @param conceptCollection
     * @throws QuadrigaStorageException
     */
    @Override
    @Transactional
    public void fillConceptCollection(IConceptCollection conceptCollection)
            throws QuadrigaStorageException {
        ConceptCollectionDTO ccDto = ccDao.getDTO(conceptCollection
                .getConceptCollectionId());
        conceptCollectionDeepMapper.fillConceptCollection(conceptCollection,
                ccDto);
    }

}