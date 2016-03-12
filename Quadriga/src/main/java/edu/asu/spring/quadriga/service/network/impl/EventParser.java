package edu.asu.spring.quadriga.service.network.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.impl.networks.AppellationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.impl.networks.PredicateType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationType;
import edu.asu.spring.quadriga.domain.impl.networks.TermType;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.transform.Link;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.transform.PredicateNode;

/**
 * Class for parsing Appellation/Relation Event networks into S-O-P networks.
 * @author jdamerow
 *
 */
@PropertySource(value = "classpath:/user.properties")
@Service
public class EventParser {
    
    private static final Logger logger = LoggerFactory.getLogger(EventParser.class);
    
    @Autowired
    @Qualifier("qStoreURL")
    private String qStoreURL;
    
    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("qStoreURL_Get_POST")
    private String qStoreURL_Get_POST;

    @Autowired
    @Qualifier("qStoreURL_Get")
    private String qStoreURL_Get;

    @Autowired
    @Qualifier("jaxbMarshaller")
    private Jaxb2Marshaller jaxbMarshaller;
    
    @Autowired
    private Environment env;
    
    @Autowired
    private IConceptCollectionManager conceptCollectionManager;

    
    public void parseStatement(String relationEventId, Map<String, Node> nodes, List<Link> links) throws JAXBException, QStoreStorageException {
        ElementEventsType elementEventType = getElementEventTypeFromCreationEventTypeID(relationEventId);
        List<CreationEvent> creationEventList = elementEventType.getRelationEventOrAppellationEvent();
        Iterator<CreationEvent> creationEventIterator = creationEventList.iterator();
         
        while (creationEventIterator.hasNext()) {
            CreationEvent event = creationEventIterator.next();
            parseSubjectOrObjectEvent(event, relationEventId, nodes, links);
        }
        
    }
    
    private Node parseSubjectOrObjectEvent(CreationEvent event, String statementId, Map<String, Node> leafNodes, List<Link> links) {
        if (event == null) {
            return null;
        }
        
        if (event instanceof AppellationEventType) {
           List<TermType> terms = ((AppellationEventType) event).getTerms();
           if (terms.size() > 0) {
               String conceptId = terms.get(0).getTermID();
               if (leafNodes.containsKey(conceptId)) {
                   leafNodes.get(conceptId).getStatementIds().add(statementId);
               }
               else {
                   Node node = new Node();
                   parseNode((AppellationEventType) event, node, statementId);
                   leafNodes.put(conceptId, node);
               }
               return leafNodes.get(conceptId);
           }
           return null;
        }
        else if (event instanceof RelationEventType) {
            RelationType relation = ((RelationEventType) event).getRelation();
            
            // create node for predicate
            PredicateType pred = relation.getPredicateType();
            PredicateNode predNode = parsePredicateEvent(pred.getAppellationEvent(), statementId);
            leafNodes.put(predNode.getId(), predNode);
            
            Node subjectNode = parseSubjectOrObjectEvent(relation.getSubjectType().getAppellationEvent(), statementId, leafNodes, links);
            if (subjectNode == null) {
                subjectNode = parseSubjectOrObjectEvent(relation.getSubjectType().getRelationEvent(), statementId, leafNodes, links);
            }
            
            Node objectNode = parseSubjectOrObjectEvent(relation.getObjectType(relation).getAppellationEvent(), statementId, leafNodes, links);
            if (objectNode == null) {
                objectNode = parseSubjectOrObjectEvent(relation.getObjectType(relation).getRelationEvent(), statementId, leafNodes, links);
            }
            
            if (subjectNode != null) {
                Link link = new Link();
                // add the statement id to the link
                link.setStatementId(statementId);
                link.setSubject(predNode);
                link.setObject(subjectNode);
                link.setLabel("has subject");
                links.add(link);
            }
            
            if (objectNode != null) {
                Link link = new Link();
                // add the statement id to the link
                link.setStatementId(statementId);
                link.setSubject(predNode);
                link.setObject(objectNode);
                link.setLabel("has object");
                links.add(link);
            }
            
            return predNode;
        }
        
        return null;
    }
    
    private PredicateNode parsePredicateEvent(AppellationEventType appellationEvent, String statementId) {
        PredicateNode predNode = new PredicateNode();
        
        parseNode(appellationEvent, predNode, statementId);
        
        predNode.setId(UUID.randomUUID().toString());
        
        return predNode;
    }
    
    private void parseNode(AppellationEventType event, Node node, String statementId) {
        StringBuffer label = new StringBuffer();
        for (TermType type : event.getTerms()) {
            label.append(type.getTermInterpertation());
            label.append(" ");
        }
        node.setId(event.getAppellationEventID());
       
        node.setConceptId(label.toString().trim());
        
        if (node.getConceptId() != null) {
            node.setLabel(conceptCollectionManager.getConceptLemmaFromConceptId(node.getConceptId()));
        }
        
        node.getStatementIds().add(statementId);
    }
    
    private ElementEventsType getElementEventTypeFromCreationEventTypeID(String relationEventId)
            throws JAXBException, QStoreStorageException {
        String xml = getCreationEventXmlStringFromQstore(relationEventId);
        ElementEventsType elementEventType = null;
        if (xml == null) {
            throw new QStoreStorageException(
                    "Some issue retriving data from Qstore, Please check the logs related to Qstore");
        } else {

            // Initialize ElementEventsType object for relation event
            elementEventType = unMarshalXmlToElementEventsType(xml);
        }
        return elementEventType;
    }
    
    private ElementEventsType unMarshalXmlToElementEventsType(String xml) throws JAXBException {
        ElementEventsType elementEventType = null;

        // Try to unmarshall the XML got from QStore to an ElementEventsType
        // object
        JAXBContext context = JAXBContext.newInstance(ElementEventsType.class);
        Unmarshaller unmarshaller1 = context.createUnmarshaller();
        unmarshaller1.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
        InputStream is = new ByteArrayInputStream(xml.getBytes());
        JAXBElement<ElementEventsType> response1 = unmarshaller1.unmarshal(new StreamSource(is),
                ElementEventsType.class);
        elementEventType = response1.getValue();

        return elementEventType;
    }
    
    private String getCreationEventXmlStringFromQstore(String id) throws JAXBException {
        // Message converters for JAXb to understand the xml
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_XML);
        messageConverters.add(new StringHttpMessageConverter());
        org.springframework.oxm.Marshaller marshaler = jaxbMarshaller;
        org.springframework.oxm.Unmarshaller unmarshaler = jaxbMarshaller;
        messageConverters.add(new MarshallingHttpMessageConverter(marshaler, unmarshaler));

        restTemplate.setMessageConverters(messageConverters);

        // Setting up the http header accept type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(mediaTypes);
        String authHeader = getAuthHeader();
        headers.set("Authorization", authHeader);
        ResponseEntity<String> response = null;
        try {
            logger.debug("URL : " + getQStoreGetURL() + id);
            // Get the XML from QStore
            response = restTemplate.exchange(getQStoreGetURL() + id, HttpMethod.GET, new HttpEntity<String[]>(headers),
                    String.class);
        } catch (Exception e) {
           logger.error(e.getMessage(), e);
           return null;
        }
        return response.getBody().toString();
    }
    
    private String getAuthHeader() {
        String auth = env.getProperty("qstore.admin.username") + ":" + env.getProperty("qstore.admin.password");
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(encodedAuth);
    }
    
    private String getQStoreGetURL() {
        return qStoreURL + qStoreURL_Get;
    }
}
