package edu.asu.spring.quadriga.service.network.impl;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.INetworkDAO;
import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ETextAccessibility;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.network.impl.AppellationEventType;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.domain.network.impl.ElementEventsType;
import edu.asu.spring.quadriga.domain.network.impl.PredicateType;
import edu.asu.spring.quadriga.domain.network.impl.PrintedRepresentationType;
import edu.asu.spring.quadriga.domain.network.impl.RelationEventType;
import edu.asu.spring.quadriga.domain.network.impl.RelationType;
import edu.asu.spring.quadriga.domain.network.impl.SubjectObjectType;
import edu.asu.spring.quadriga.domain.network.impl.TermPartType;
import edu.asu.spring.quadriga.domain.network.impl.TermType;
import edu.asu.spring.quadriga.domain.network.json.AppellationEventObject;
import edu.asu.spring.quadriga.domain.workspace.ITextFile;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.mapper.networks.INetworkMapper;
import edu.asu.spring.quadriga.qstore.IMarshallingService;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.impl.TextOccurance;
import edu.asu.spring.quadriga.service.network.domain.impl.TextPhrase;
import edu.asu.spring.quadriga.service.textfile.ITextFileManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This class Implements the {@link INetworkManager}. It implemented all the
 * methods required to work on storing, displaying or manipulating
 * {@link INetwork}
 * 
 * @author : Lohith Dwaraka
 */

@Service
public class NetworkManager extends BaseDAO<NetworksDTO> implements INetworkManager {

    private static final Logger logger = LoggerFactory.getLogger(NetworkManager.class);

    @Autowired
    private IQStoreConnector qStoreConnector;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @Autowired
    private INetworkMapper networkmapper;

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private INetworkDAO dbConnect;

    @Autowired
    private IMarshallingService marshallingService;

    @Autowired
    private ITextFileManager txtManager;

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    public ElementEventsType getElementEventTypeFromCreationEventTypeID(String relationEventId)
            throws JAXBException, QStoreStorageException {
        String xml = qStoreConnector.getCreationEvent(relationEventId);
        ElementEventsType elementEventType = null;
        if (xml == null) {
            throw new QStoreStorageException(
                    "Some issue retriving data from Qstore. Please check the logs related to Qstore.");
        } else {
            // Initialize ElementEventsType object for relation event
            elementEventType = marshallingService.unMarshalXmlToElementEventsType(xml);
        }
        return elementEventType;
    }

    /**
     * Making a cache of relation predicate objects for checking references
     * 
     * @param relationEventId
     * @param predicateName
     * @param appellationEventObject
     * @return
     */
    public List<List<Object>> stackPredicateAppellationObject(String relationEventId, String predicateName,
            AppellationEventObject appellationEventObject, List<List<Object>> relationEventPredicateMapping) {
        Iterator<List<Object>> I = relationEventPredicateMapping.iterator();

        while (I.hasNext()) {
            List<Object> objectList = I.next();
            Iterator<Object> I1 = objectList.iterator();
            while (I1.hasNext()) {
                Object object = I1.next();
                if (object instanceof String[]) {
                    String pairs[] = (String[]) object;
                    if (pairs[0].equals(relationEventId)) {
                        String predicateNameLocal = pairs[1];
                        logger.debug(" relationEventId  :" + relationEventId + " id : " + pairs[0] + "predicate Name"
                                + predicateNameLocal);
                        return relationEventPredicateMapping;
                    }
                }
            }
        }
        String[] pairs = new String[2];
        pairs[0] = (relationEventId);
        pairs[1] = (predicateName);
        List<Object> objectList = new ArrayList<Object>();
        objectList.add(pairs);
        objectList.add(appellationEventObject);
        relationEventPredicateMapping.add(objectList);

        return relationEventPredicateMapping;

    }

    @Override
    @Transactional
    public String getNetworkXML(String networkId) throws Exception {
        VelocityEngine engine = restVelocityFactory.getVelocityRestEngine();
        Template template = null;
        StringWriter writer = null;
        String networkXML = null;
        try {
            engine.init();
            template = engine.getTemplate("velocitytemplates/getnetworksfromqstore.vm");
            VelocityContext context = new VelocityContext();
            List<INetworkNodeInfo> networkTopNodes = getNetworkTopNodes(networkId);
            context.put("statmentList", networkTopNodes);
            writer = new StringWriter();
            template.merge(context, writer);
            logger.debug("XML : " + writer.toString());
            networkXML = qStoreConnector.getStatements(writer.toString());
        } catch (ResourceNotFoundException e) {

            logger.error("Exception:", e);
            throw new RestException(404);
        } catch (ParseErrorException e) {

            logger.error("Exception:", e);
            throw new RestException(404);
        } catch (MethodInvocationException e) {

            logger.error("Exception:", e);
            throw new RestException(404);
        }
        networkXML = networkXML.substring(networkXML.indexOf("element_events") - 1, networkXML.length());
        return networkXML;
    }

    @Override
    public Set<TextOccurance> getTextsForConceptId(String conceptId, ETextAccessibility access) throws Exception {
        String results = qStoreConnector.searchNodesByConcept(conceptId);
        ElementEventsType events = marshallingService.unMarshalXmlToElementEventsType(results);

        List<CreationEvent> eventList = events.getRelationEventOrAppellationEvent();

        Set<TextOccurance> occurances = new HashSet<TextOccurance>();

        for (CreationEvent event : eventList) {
            if (!(event instanceof AppellationEventType)) {
                // we're only interested in appellation events here
                continue;
            }

            TextOccurance occur = new TextOccurance();
            occur.setTextUri(event.getSourceReference());
            ITextFile txtFile = txtManager.getTextFileByUri(occur.getTextUri());

            if (txtFile != null && txtFile.getAccessibility() == access) {
                occur.setContents(txtManager.retrieveTextFileContent(txtFile.getTextId()));
                occur.setTextId(txtFile.getTextId());
            } else {
                continue;
            }

            occur.setProject(projectManager.getProjectDetails(txtFile.getProjectId()));

            // there should only be one
            TermType term = ((AppellationEventType) event).getTermType();

            if (term != null) {
                PrintedRepresentationType printed = term.getPrintedRepresentation();
                if (printed == null) {
                    continue;
                }
                List<TermPartType> termparts = printed.getTermParts();
                occur.setTextPhrases(new ArrayList<TextPhrase>());
                for (TermPartType tp : termparts) {
                    TextPhrase phrase = new TextPhrase();
                    phrase.setExpression(tp.getExpression());
                    phrase.setFormat(tp.getFormat());
                    phrase.setFormattedPointer(tp.getFormattedPointer());
                    if (StringUtils.isNumeric(tp.getPosition())) {
                        phrase.setPosition(Integer.parseInt(tp.getPosition()));
                    }
                    if (!occur.getTextPhrases().contains(phrase)) {
                        occur.getTextPhrases().add(phrase);
                    }
                }
            }

            occurances.add(occur);

        }

        return occurances;
    }

    @Override
    public String storeNetworks(String xml) throws QStoreStorageException {
        return qStoreConnector.store(xml);
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * This implementation uses the hibernate for dataaccess from the database
     */
    @Override
    @Transactional
    public INetwork getNetwork(String networkId) throws QuadrigaStorageException {
        NetworksDTO networkDto = dbConnect.getNetworksDTO(networkId);
        return networkmapper.getNetwork(networkDto);
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * This implementation uses the hibernate for dataaccess from the database
     */
    @Override
    @Transactional
    public List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException {
        return networkmapper.getListOfNetworksForUser(user);
    }

    
    /**
     * 
     * {@inheritDoc}
     * 
     * This implementation uses the hibernate for dataaccess from the database
     */
    @Override
    @Transactional
    public List<INetwork> getApprovedNetworkList() throws QuadrigaStorageException {
        return networkmapper.getListOfApprovedNetworks();
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * This implementation uses the hibernate for dataaccess from the database
     */
    @Override
    @Transactional
    public List<INetwork> getNetworksInProject(String projectid, String status) throws QuadrigaStorageException {

        List<NetworksDTO> networksDTO = dbConnect.getNetworkDTOList(projectid);

        List<INetwork> networksList = new ArrayList<>();
        if (status == null) {
            for (NetworksDTO nwDTO : networksDTO) {
                networksList.add(networkmapper.getNetwork(nwDTO));
            }

            return networksList;
        }

        for (NetworksDTO nwDTO : networksDTO) {
            if (nwDTO.getStatus().equals(status)) {
                networksList.add(networkmapper.getNetwork(nwDTO));
            }
        }

        return networksList;
    }
    
    @Override
    @Transactional
    public List<INetwork> getNetworksWithStatements(List<String> statementIds) throws QuadrigaStorageException{
        
        if(statementIds.size() == 0){
            return null;
        }
      /*  List<INetwork> networksList = new ArrayList<INetwork>();
        List<NetworksDTO> approvedNetworksList = dbConnect.getApprovedNetworkList();
        List<NetworksDTO> networksWithStatementsList = new ArrayList<NetworksDTO>();
        Set<String> networkIdsWithStatement = new HashSet<String>();
        NetworksDTO networkDTO = null;
        for(String statementId : statementIds){
            networkDTO = dbConnect.getNetworkWithStatement(statementId);
            System.out.println("StatementId: "+statementId+" , networkDTO: "+networkDTO);
            if(networkDTO != null && networkIdsWithStatement.add(networkDTO.getNetworkid())){
                networksWithStatementsList.add(networkDTO);
                System.out.println("NetworkId: "+networkDTO.getNetworkid());
            }
  
        }
        
        for(NetworksDTO canditateNetwork : networksWithStatementsList) {
            for(NetworksDTO approvedNetwork : approvedNetworksList){
                if(canditateNetwork.getNetworkid().equals(approvedNetwork.getNetworkid())){
                    networksList.add(networkmapper.getNetworkShallowDetails(canditateNetwork));
                    break;
                }
            }
        }*/
        
        List<NetworksDTO> networksDTOList = dbConnect.getNetworkWithStatement(statementIds);
        List<INetwork> networksList = new ArrayList<INetwork>();
        Set<String> networkIdsWithStatement = new HashSet<String>();
        System.out.println("NetworkDTOs");
        if(networksDTOList != null){
            for(NetworksDTO networksDTO : networksDTOList){
                if(networksDTO != null && networkIdsWithStatement.add(networksDTO.getNetworkid())){
                    networksList.add(networkmapper.getNetworkShallowDetails(networksDTO));
                }
            }
        }
        
        System.out.println("NetworkList Size: "+ networksList.size());
        return networksList;    
    }

    @Override
    @Transactional
    public List<INetwork> getAllNetworkVersions(String networkid) throws QuadrigaStorageException {

        List<INetwork> networksList = dbConnect.getAllNetworkVersions(networkid);

        if (networksList != null) {
            return networksList;
        }
        return null;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * This implementation uses the hibernate for dataaccess from the database
     */
    @Override
    @Transactional
    public List<INetworkNodeInfo> getNetworkTopNodes(String networkId) throws QuadrigaStorageException {
        int versionNo = getLatestVersionOfNetwork(networkId);
        List<INetworkNodeInfo> networkNodeList = networkmapper.getNetworkNodes(networkId, versionNo);
        if (networkNodeList != null) {
            Iterator<INetworkNodeInfo> iterator = networkNodeList.iterator();
            while (iterator.hasNext()) {
                INetworkNodeInfo networkNodeInfo = iterator.next();
                if (networkNodeInfo.getIsTop() != 1)
                    iterator.remove();
            }
        }

        return networkNodeList;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * This implementation uses the hibernate for dataaccess from the database
     */
    @Override
    @Transactional
    public List<INetworkNodeInfo> getNetworkTopNodesByVersion(String networkId, int versionNo)
            throws QuadrigaStorageException {
        List<INetworkNodeInfo> networkNodeList = networkmapper.getNetworkNodes(networkId, versionNo);
        if (networkNodeList != null) {
            Iterator<INetworkNodeInfo> iterator = networkNodeList.iterator();
            while (iterator.hasNext()) {
                INetworkNodeInfo networkNodeInfo = iterator.next();
                if (networkNodeInfo.getIsTop() != 1 || networkNodeInfo.getVersion() != versionNo)
                    iterator.remove();
            }
        }

        return networkNodeList;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * This implementation uses the hibernate for dataaccess from the database
     */
    @Override
    @Transactional
    public String updateNetworkName(String networkId, String networkName) throws QuadrigaStorageException {
        try {
            dbConnect.updateNetworkName(networkId, networkName);
        } catch (QuadrigaStorageException e) {
            throw new QuadrigaStorageException();
        }
        return "success";
    }

    /**
     * 
     * {@inheritDoc}
     * 
     * This implementation uses the hibernate for dataaccess from the database
     */
    @Override
    @Transactional
    public String storeNetworkDetails(String xml, IUser user, String networkName, String workspaceId,
            String uploadStatus, String networkId, int version, String networkStatus, String externalUserId)
            throws JAXBException {
        ElementEventsType elementEventType = marshallingService.unMarshalXmlToElementEventsType(xml);

        NewNetworkDetailsCache newNetworkDetailCache = new NewNetworkDetailsCache();

        // Below code reads the top level Appellation events

        newNetworkDetailCache = parseNewNetworkStatement(elementEventType, newNetworkDetailCache);

        // Add network into database
        if (uploadStatus == INetworkManager.NEWNETWORK) {
            try {
                networkId = dbConnect.addNetwork(networkName, user, workspaceId, networkStatus, externalUserId);
            } catch (QuadrigaStorageException e1) {
                logger.error("DB action error ", e1);
            }
        }

        List<NetworkEntry> networkDetailsCache = newNetworkDetailCache.getEntries();
        // Add network statements for networks
        for (NetworkEntry entry : networkDetailsCache) {
            try {
                String rowid = generateUniqueID();
                dbConnect.addNetworkStatement(rowid, networkId, entry.getId(), entry.getType(), entry.isTop() ? 1 : 0,
                        user, version);
            } catch (QuadrigaStorageException e1) {
                logger.error("DB error while adding network statment", e1);
            }
        }
        return networkId;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CreationEvent> getTopElementEvents(String xml, Stream<String> topNodeIDStream) throws JAXBException {
        ElementEventsType elementEventType = marshallingService.unMarshalXmlToElementEventsType(xml);

        Set<String> topIDs = topNodeIDStream.collect(Collectors.toSet());

        List<CreationEvent> eventList = elementEventType.getRelationEventOrAppellationEvent();

        return eventList.stream().filter(event -> topIDs.contains(event.getId())).collect(Collectors.toList());

    }

    /**
     * Parsing each network statement of the request network input. Network
     * would contain Appellation Event and Relation Event
     * 
     * @param elementEventType
     *            {@link ElementEventsType} object
     * @param bitStreamList
     *            {@link List} of {@link IBitStream}
     * @param newNetworkDetailCache
     *            {@link NewNetworkDetailsCache} object to hold the cache of
     *            network details
     * @return Returns updated {@link NewNetworkDetailsCache} object which holds
     *         the cache of network details
     */
    private NewNetworkDetailsCache parseNewNetworkStatement(ElementEventsType elementEventType,
            NewNetworkDetailsCache newNetworkDetailCache) {

        List<CreationEvent> creationEventList = elementEventType.getRelationEventOrAppellationEvent();
        Iterator<CreationEvent> creationEventIterator = creationEventList.iterator();

        while (creationEventIterator.hasNext()) {
            CreationEvent creationEvent = creationEventIterator.next();
            // Cache Appellation Events
            if (creationEvent instanceof AppellationEventType) {
                parseNewAppellationEvent(newNetworkDetailCache, creationEvent);
            }
            // Cache Relation Events
            if (creationEvent instanceof RelationEventType) {
                parseNewRelationEvent(newNetworkDetailCache, creationEvent);
            }
        }

        return newNetworkDetailCache;
    }

    /**
     * Parses the Appellation Event and stores the Appellation event in the
     * cache.
     * 
     * @param newNetworkDetailCache
     *            {@link NewNetworkDetailsCache} object to hold the cache of
     *            network details
     * @param creationEvent
     *            {@link CreationEvent} object of {@link AppellationEventType}
     *            type.
     * @param bitStreamList
     *            {@link List} of {@link IBitStream} object
     * @return Returns updated {@link NewNetworkDetailsCache} object which holds
     *         the cache of network details
     */
    private NetworkEntry parseNewAppellationEvent(NewNetworkDetailsCache newNetworkDetailCache,
            CreationEvent creationEvent) {

        NetworkEntry entry = new NetworkEntry();

        if (creationEvent.getId() != null && !creationEvent.getId().isEmpty()) {
            String id = creationEvent.getId();
            if (newNetworkDetailCache.getAddedIds().contains(id)) {
                entry = newNetworkDetailCache.getById(id);
            } else {
                entry.setId(id);
                entry.setType(INetworkManager.APPELLATIONEVENT);
                entry.setTop(true);
                newNetworkDetailCache.addEntry(entry);
            }
        }
        if (creationEvent.getRefId() != null && !creationEvent.getRefId().isEmpty()) {
            entry.setRefId(creationEvent.getRefId());
        }
        return entry;
    }

    /**
     * Parses the Relation Event and stores the Relation event in the cache.
     * 
     * @param newNetworkDetailCache
     *            {@link NewNetworkDetailsCache} object to hold the cache of
     *            network details
     * @param creationEvent
     *            {@link CreationEvent} object of {@link RelationEventType}
     *            type.
     * @param bitStreamList
     *            {@link List} of {@link IBitStream} object
     * @return Returns updated {@link NewNetworkDetailsCache} object which holds
     *         the cache of network details
     */
    private NetworkEntry parseNewRelationEvent(NewNetworkDetailsCache newNetworkDetailCache,
            CreationEvent creationEvent) {

        NetworkEntry entry = new NetworkEntry();

        // get relation event id
        if (creationEvent.getId() != null && !creationEvent.getId().isEmpty()) {
            String id = creationEvent.getId();
            if (newNetworkDetailCache.getAddedIds().contains(id)) {
                entry = newNetworkDetailCache.getById(id);
            } else {
                entry.setId(id);
                entry.setType(INetworkManager.RELATIONEVENT);
                entry.setTop(true);
                newNetworkDetailCache.addEntry(entry);
            }
        }

        RelationEventType relationEventType = (RelationEventType) (creationEvent);
        try {
            // Go Recursively and check for Relation event within a relation
            // events
            NetworkEntry relEntry = parseIntoRelationEventElement(relationEventType, newNetworkDetailCache);
            if (relEntry != null) {
                relEntry.setTop(false);
            }
        } catch (QuadrigaStorageException se) {
            logger.error("DB Storage issue", se);
        }

        return entry;
    }

    /**
     * Parse into the Relation Events by searches for subject, object and
     * predicate.
     * 
     * @param relationEventType
     *            {@link RelationEventType} object
     * @param newNetworkDetailCache
     *            {@link NewNetworkDetailsCache} object to hold the cache of
     *            network details
     * @param bitStreamList
     *            {@link List} of {@link IBitStream} object
     * @return Returns updated {@link NewNetworkDetailsCache} object which holds
     *         the cache of network details
     * @throws QuadrigaStorageException
     *             Throws Database storage exception
     */
    private NetworkEntry parseIntoRelationEventElement(RelationEventType relationEventType,
            NewNetworkDetailsCache newNetworkDetailCache) throws QuadrigaStorageException {

        List<?> creatorOrRelationList = relationEventType.getRelationCreatorOrRelation();
        Iterator<?> creatorOrRelationIterator = creatorOrRelationList.iterator();

        while (creatorOrRelationIterator.hasNext()) {
            Object o = creatorOrRelationIterator.next();
            if (o instanceof RelationType) {
                RelationType relationType = (RelationType) o;
                List<JAXBElement<?>> elementsList = relationType.getIdOrCreatorOrCreationDate();
                Iterator<JAXBElement<?>> elementsIterator = elementsList.iterator();
                while (elementsIterator.hasNext()) {
                    JAXBElement<?> element = (JAXBElement<?>) elementsIterator.next();

                    if (element.getValue().toString().contains("SubjectObjectType")) {
                        // Handles the subject part of the relation
                        if (element.getName().toString().contains("subject")) {
                            SubjectObjectType subject = (SubjectObjectType) element.getValue();
                            NetworkEntry entry = parseNewSubjectObjectType(newNetworkDetailCache, subject);
                            if (entry != null) {
                                entry.setTop(false);
                            }

                        } else {
                            // Handles the object part of the relation
                            if (element.getName().toString().contains("object")) {

                                SubjectObjectType object = (SubjectObjectType) element.getValue();
                                NetworkEntry entry = parseNewSubjectObjectType(newNetworkDetailCache, object);
                                if (entry != null) {
                                    entry.setTop(false);
                                }
                            }
                        }
                    } else {
                        // Handles the predicate part of the relation
                        if (element.getValue().toString().contains("PredicateType")) {

                            PredicateType predicateType = (PredicateType) element.getValue();
                            AppellationEventType appellationEventType = predicateType.getAppellationEvent();
                            NetworkEntry entry = parseNewAppellationEventFoundInRelationEvent(newNetworkDetailCache,
                                    appellationEventType);
                            if (entry != null) {
                                entry.setTop(false);
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Parse {@link SubjectObjectType} and store the appropriate ID in the cache
     * 
     * @param newNetworkDetailCache
     *            {@link NewNetworkDetailsCache} object to hold the cache of
     *            network details
     * @param subjectOrObject
     *            {@link SubjectObjectType} object
     * @param bitStreamList
     *            {@link List} of {@link IBitStream} object
     * @return Returns updated {@link NewNetworkDetailsCache} object which holds
     *         the cache of network details
     * @throws QuadrigaStorageException
     *             Throws Database storage exception
     */
    private NetworkEntry parseNewSubjectObjectType(NewNetworkDetailsCache newNetworkDetailCache,
            SubjectObjectType subjectOrObject) throws QuadrigaStorageException {

        // Check for relation event inside subject
        RelationEventType relationEventType = subjectOrObject.getRelationEvent();
        if (relationEventType == null) {
            // Check for Appellation event inside subject and add if any
            AppellationEventType appellationEventType = subjectOrObject.getAppellationEvent();
            return parseNewAppellationEventFoundInRelationEvent(newNetworkDetailCache, appellationEventType);
        } else {
            NetworkEntry entry = new NetworkEntry();

            if (relationEventType.getId() != null && !relationEventType.getId().isEmpty()) {
                String id = relationEventType.getId();
                if (newNetworkDetailCache.getAddedIds().contains(id)) {
                    entry = newNetworkDetailCache.getById(id);
                } else {
                    entry.setId(id);
                    entry.setType(INetworkManager.RELATIONEVENT);
                    entry.setTop(true);
                    newNetworkDetailCache.addEntry(entry);
                }
            }

            if (relationEventType.getRefId() != null && !relationEventType.getRefId().isEmpty()) {
                entry.setRefId(relationEventType.getRefId());
            }

            NetworkEntry nestedEntry = parseIntoRelationEventElement(relationEventType, newNetworkDetailCache);
            if (nestedEntry != null) {
                nestedEntry.setTop(false);
            }

            return entry;
        }
    }

    /**
     * Parse {@link AppellationEventType} usually which is found in Predicate
     * and store the appropriate ID in the cache
     * 
     * @param newNetworkDetailCache
     *            {@link NewNetworkDetailsCache} object to hold the cache of
     *            network details
     * @param appellationEventType
     *            {@link AppellationEventType}objects
     * @param bitStreamList
     *            {@link List} of {@link IBitStream} object
     * @return Returns updated {@link NewNetworkDetailsCache} object which holds
     *         the cache of network details
     */
    private NetworkEntry parseNewAppellationEventFoundInRelationEvent(NewNetworkDetailsCache newNetworkDetailCache,
            AppellationEventType appellationEventType) {

        // Check for Appellation event inside predicate
        if (appellationEventType == null) {
            logger.debug("AE1 is null");
            return null;
        } else {
            logger.debug("AE1 found object");
            NetworkEntry entry = new NetworkEntry();

            if (appellationEventType.getId() != null && !appellationEventType.getId().isEmpty()) {
                String id = appellationEventType.getId();
                if (newNetworkDetailCache.getAddedIds().contains(id)) {
                    entry = newNetworkDetailCache.getById(id);
                } else {
                    entry.setId(id);
                    entry.setType(INetworkManager.APPELLATIONEVENT);
                    entry.setTop(false);
                    newNetworkDetailCache.addEntry(entry);
                }
            }

            if (appellationEventType.getRefId() != null && !appellationEventType.getRefId().isEmpty()) {
                entry.setRefId(appellationEventType.getRefId());
            }

            return entry;
        }

    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public int getLatestVersionOfNetwork(String networkID) throws QuadrigaStorageException {
        List<Integer> latestVersion = dbConnect.getLatestVersionOfNetwork(networkID);
        if (latestVersion == null || latestVersion.size() == 0 || latestVersion.get(0) == null) {
            return -1;
        }

        int version = latestVersion.get(0);
        return version;
    }

    /**
     * 
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<INetwork> getNetworksOfOwner(IUser user) throws QuadrigaStorageException {
        List<INetwork> networkList = null;

        try {

            networkList = networkmapper.getListOfNetworksForUser(user);
        } catch (QuadrigaStorageException e) {
            logger.error("Error in fetching network of user: ", e);
        }

        return networkList;
    }

    /**
     * 
     * {@inheritDoc}
     * 
     */
    @Override
    public int getNetworkStatusCode(String status) {
        if (status == null)
            return INetworkStatus.UNKNOWN_CODE;
        if (status.equals(INetworkStatus.APPROVED))
            return INetworkStatus.APPROVED_CODE;
        if (status.equals(INetworkStatus.REJECTED))
            return INetworkStatus.REJECTED_CODE;
        if (status.equals(INetworkStatus.ASSIGNED))
            return INetworkStatus.ASSIGNED_CODE;
        if (status.equals(INetworkStatus.PENDING))
            return INetworkStatus.PENDING_CODE;

        return INetworkStatus.UNKNOWN_CODE;

    }

    @Override
    public List<INetwork> editNetworkStatusCode(List<INetwork> networks) {

        if (networks == null) {
            return networks;
        }

        for (INetwork network : networks) {
            network.setStatus(getNetworkStatusCode(network.getStatus()) + "");
        }

        return networks;
    }

    @Override
    public NetworksDTO getDTO(String id) {
        return getDTO(NetworksDTO.class, id);
    }

    /**
     * This inner class would be used to cache the network details of newly
     * uploaded network. We use hold the cache until all the data in the
     * uploaded network seems legitimate as per our general rules of network.
     * 
     * @author Lohith Dwaraka
     *
     */
    class NewNetworkDetailsCache {

        private boolean fileExists;
        private List<NetworkEntry> entries;
        private Map<String, NetworkEntry> addedIds;

        public NewNetworkDetailsCache() {
            this.fileExists = true;
            entries = new ArrayList<NetworkManager.NetworkEntry>();
            addedIds = new HashMap<String, NetworkManager.NetworkEntry>();
        }

        public boolean isFileExists() {
            return fileExists;
        }

        public void setFileExists(boolean fileExists) {
            this.fileExists = fileExists;
        }

        public void addEntry(NetworkEntry entry) {
            entries.add(entry);
            addedIds.put(entry.getId(), entry);
        }

        public List<NetworkEntry> getEntries() {
            return entries;
        }

        public Set<String> getAddedIds() {
            return addedIds.keySet();
        }

        public NetworkEntry getById(String id) {
            return addedIds.get(id);
        }

    }

    class NetworkEntry {
        private String refId;
        private String id;
        private String type;
        private boolean isTop;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isTop() {
            return isTop;
        }

        public void setTop(boolean isTop) {
            this.isTop = isTop;
        }

        public String getRefId() {
            return refId;
        }

        public void setRefId(String refId) {
            this.refId = refId;
        }

    }

}