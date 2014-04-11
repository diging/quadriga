package edu.asu.spring.quadriga.service.network.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjectManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dspace.IBitStream;
import edu.asu.spring.quadriga.domain.factories.INetworkFactory;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.impl.networks.AppellationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.impl.networks.PredicateType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationType;
import edu.asu.spring.quadriga.domain.impl.networks.SubjectObjectType;
import edu.asu.spring.quadriga.domain.impl.networks.TermType;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.AppellationEventObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.JsonObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.ObjectTypeObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.PredicateObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.RelationEventObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.SubjectObject;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.network.ID3NetworkManager;
import edu.asu.spring.quadriga.service.network.IJITNetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;
import edu.asu.spring.quadriga.service.network.domain.impl.NetworkJSon;
import edu.asu.spring.quadriga.service.network.factory.INodeObjectWithStatementFactory;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This class Implements the {@link INetworkManager}. 
 * It implemented all the methods required to work on storing, displaying or manipulating {@link INetwork}
 * 
 * @author : Lohith Dwaraka
 */

@Service
public class NetworkManager extends DAOConnectionManager implements INetworkManager {

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkManager.class);

	@Autowired
	@Qualifier("qStoreURL")
	private String qStoreURL;

	@Autowired
	private INodeObjectWithStatementFactory nodeObjectWithStatementFactory;

	@Autowired
	IRestVelocityFactory restVelocityFactory;

	@Autowired
	private IJITNetworkManager jitNetworkManager;

	@Autowired
	private ID3NetworkManager d3NetworkManager;

	@Autowired
	IConceptCollectionManager conceptCollectionManager;

	@Autowired
	private	IListWSManager wsManager;

	@Autowired
	@Qualifier("qStoreURL_Add")
	private String qStoreURL_Add;


	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("qStoreURL_Get_POST")
	private String qStoreURL_Get_POST;

	@Autowired
	@Qualifier("qStoreURL_Get")
	private String qStoreURL_Get;

	@Autowired
	@Qualifier("jaxbMarshaller")
	Jaxb2Marshaller jaxbMarshaller;

	@Autowired
	private INetworkFactory networkFactory;

	@Autowired
	private IDBConnectionRetrieveProjectManager projectManager;

	@Autowired
	private IDBConnectionNetworkManager dbConnect;

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getQStoreAddURL() {
		return qStoreURL+qStoreURL_Add;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getQStoreGetURL() {
		return qStoreURL+qStoreURL_Get;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getQStoreGetPOSTURL() {
		return qStoreURL+qStoreURL_Get_POST;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * This implementation uses the hibernate for dataaccess from the database
	 */
	@Override
	@Transactional
	public INetworkJSon getJsonForNetworks(String networkId, String jqueryType) throws QuadrigaStorageException{

		INetworkJSon networkJSon=null;

		List<INetworkNodeInfo> networkTopNodesList = null;

		try{
			networkTopNodesList = getNetworkTopNodes(networkId);
		}catch(QuadrigaStorageException e){
			logger.error("DB Error while getting network top nodes",e);
		}

		if(jqueryType.equals(INetworkManager.D3JQUERY)){
			networkJSon = d3NetworkManager.parseNetworkForD3Jquery(networkTopNodesList);
		}else if(jqueryType.equals(INetworkManager.JITJQUERY)){
			String jitJSon = jitNetworkManager.parseNetworkForJITJquery(networkTopNodesList);
			if(networkJSon ==null){
				networkJSon = new NetworkJSon(jitJSon, null);
			}
		}		

		return networkJSon;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<INodeObjectWithStatement> parseEachStatement(String relationEventId,String statementType, String statementId, List<List<Object>> relationEventPredicateMapping, List<INodeObjectWithStatement> nodeObjectWithStatementList) throws JAXBException, QStoreStorageException{
		ElementEventsType elementEventType =getElementEventTypeFromCreationEventTypeID(relationEventId);
		List<CreationEvent> creationEventList =elementEventType.getRelationEventOrAppellationEvent();
		Iterator <CreationEvent> creationEventIterator= creationEventList.iterator();

		while(creationEventIterator.hasNext()){
			CreationEvent creationEvent = creationEventIterator.next();
			// Check if event is Appellation event
			if(creationEvent instanceof AppellationEventType)
			{
				// Do nothing, we don't need to display appellation events on UI.
			}
			// Check if event is Relation event
			if(creationEvent instanceof RelationEventType){
				// Trying to get a list of objects in the relations event type object
				// First get PredicateType
				// Then go recursively to subject and object
				JsonObject jsonObject = new JsonObject();
				RelationEventType relationEventType = (RelationEventType) creationEvent;
				jsonObject.setIsRelationEventObject(true);
				jsonObject.setRelationEventObject(parseThroughRelationEvent(relationEventType,new RelationEventObject(),relationEventPredicateMapping));



				// This would help us in forming the json string as per requirement.
				nodeObjectWithStatementList = prepareNodeObjectContent(jsonObject.getRelationEventObject(),nodeObjectWithStatementList,statementId);

			}
		}


		return nodeObjectWithStatementList;
	}


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public ElementEventsType getElementEventTypeFromCreationEventTypeID(String relationEventId) throws JAXBException, QStoreStorageException{
		String xml = getCreationEventXmlStringFromQstore(relationEventId);
		ElementEventsType elementEventType = null;
		if(xml ==null){
			throw new QStoreStorageException("Some issue retriving data from Qstore, Please check the logs related to Qstore");
		}else{

			// Initialize ElementEventsType object for relation event
			elementEventType = unMarshalXmlToElementEventsType(xml);
		}	
		return elementEventType;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public ElementEventsType unMarshalXmlToElementEventsType(String xml) throws JAXBException{
		ElementEventsType elementEventType = null;

		// Try to unmarshall the XML got from QStore to an ElementEventsType object
		JAXBContext context = JAXBContext.newInstance(ElementEventsType.class);
		Unmarshaller unmarshaller1 = context.createUnmarshaller();
		unmarshaller1.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
		InputStream is = new ByteArrayInputStream(xml.getBytes());
		JAXBElement<ElementEventsType> response1 =  unmarshaller1.unmarshal(new StreamSource(is), ElementEventsType.class);
		elementEventType = response1.getValue();

		return elementEventType;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public RelationEventObject parseThroughRelationEvent(RelationEventType relationEventType,RelationEventObject relationEventObject,List<List<Object>> relationEventPredicateMapping){

		// Get RelationType of the RelationEventType
		RelationType relationType = relationEventType.getRelation(relationEventType);

		// Handle Predicate of the RelationType
		PredicateType predicateType = relationType.getPredicateType(relationType);
		relationEventObject.setPredicateObject(parseThroughPredicate(relationEventType, predicateType, relationEventPredicateMapping));

		// Handle Subject of the RelationType
		SubjectObjectType subjectType = relationType.getSubjectType(relationType);
		SubjectObject subjectObject = parseThroughSubject(relationEventType, subjectType, relationEventPredicateMapping);
		relationEventObject.setSubjectObject(subjectObject);		

		// Handle Object of the RelationType
		SubjectObjectType objectType = relationType.getObjectType(relationType);
		ObjectTypeObject objectTypeObject = parseThroughObject(relationEventType, objectType, relationEventPredicateMapping);
		relationEventObject.setObjectTypeObject(objectTypeObject);


		return relationEventObject;
	}



	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public PredicateObject parseThroughPredicate(RelationEventType relationEventType, PredicateType predicateType,List<List<Object>> relationEventPredicateMapping){
		//	Predicate has only appellation event, so get appellation event inside the predicate
		AppellationEventType appellationEvent = predicateType.getAppellationEvent();
		String nodeId = appellationEvent.getAppellationEventID();
		PredicateObject predicateObject = null;
		List<TermType> termTypeList= appellationEvent.getTerms(appellationEvent);
		Iterator <TermType> termTypeIterator = termTypeList.iterator();
		while(termTypeIterator.hasNext()){
			TermType tt = termTypeIterator.next();
			AppellationEventObject appellationEventObject = new AppellationEventObject();
			appellationEventObject.setNode(conceptCollectionManager.getConceptLemmaFromConceptId(tt.getTermInterpertation(tt))+"_"+shortUUID());
			if(nodeId!=null){
				appellationEventObject.setTermId(nodeId+"_"+shortUUID());
				//appellationEventObject.setTermId(nodeId);
			}else{
				appellationEventObject.setTermId(tt.getTermID(tt)+"_"+shortUUID());
			}
			predicateObject = new PredicateObject();
			predicateObject.setAppellationEventObject(appellationEventObject);
			relationEventPredicateMapping = stackPredicateAppellationObject(relationEventType.getRelationEventId(relationEventType),predicateObject.getAppellationEventObject().getTermId(),appellationEventObject,relationEventPredicateMapping);
			predicateObject.setRelationEventID(relationEventType.getRelationEventId(relationEventType));
			return predicateObject;
		}
		return predicateObject;
	}

	/**
	 * Making a cache of relation predicate objects for checking references
	 * @param relationEventId
	 * @param predicateName
	 * @param appellationEventObject
	 * @return
	 */
	public List<List<Object>> stackPredicateAppellationObject(String relationEventId,String predicateName,AppellationEventObject appellationEventObject,List<List<Object>> relationEventPredicateMapping){
		Iterator<List<Object>> I = relationEventPredicateMapping.iterator();

		while(I.hasNext()){
			List<Object> objectList = I.next();
			Iterator<Object> I1 = objectList.iterator();
			while(I1.hasNext()){
				Object object = I1.next();
				if(object instanceof String[]){
					String pairs[] = (String [])object;
					if(pairs[0].equals(relationEventId)){
						String predicateNameLocal = pairs[1];
						logger.debug(" relationEventId  :" +relationEventId +" id : "+pairs[0]+"predicate Name"+predicateNameLocal );
						return relationEventPredicateMapping;
					}
				}
			}
		}
		String[] pairs = new String [2];
		pairs[0]=(relationEventId);
		pairs[1]=(predicateName);
		List<Object> objectList = new ArrayList<Object>();
		objectList.add(pairs);
		objectList.add(appellationEventObject);
		relationEventPredicateMapping.add(objectList);

		return relationEventPredicateMapping;

	}


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public AppellationEventObject isRelationEventPresentInStack(String relationEventId,List<List<Object>> relationEventPredicateMapping){

		Iterator<List<Object>> I = relationEventPredicateMapping.iterator();
		int flag=0;
		AppellationEventObject appellationEventObject=null;
		while(I.hasNext()){
			List<Object> objectList = I.next();
			Iterator<Object> I1 = objectList.iterator();

			while(I1.hasNext()){
				Object object = I1.next();

				if(object instanceof String[]){
					String pairs[] = (String [])object;
					if(pairs[0].equals(relationEventId)){
						String predicateNameLocal = pairs[1];
						logger.debug(" relationEventId  :" +relationEventId +" id : "+pairs[0]+"predicate Name"+predicateNameLocal );
						flag=1;
					}
				}
				if(object instanceof AppellationEventObject){
					appellationEventObject=(AppellationEventObject)object;
				}

			}
			if(flag==1){
				return appellationEventObject;
			}

		}

		return null;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public SubjectObject parseThroughSubject(RelationEventType relationEventType, SubjectObjectType subjectObjectType,List<List<Object>> relationEventPredicateMapping){

		//	Check for relation event inside subject and add if any
		RelationEventType subjectRelationEventType = subjectObjectType.getRelationEvent();

		SubjectObject subjectObject = new SubjectObject();

		if(subjectRelationEventType == null){
			subjectObject.setIsRelationEventObject(false);
		}else{
			String tempRelationEventId = subjectRelationEventType.getRelationEventId(subjectRelationEventType);
			AppellationEventObject temp = isRelationEventPresentInStack(tempRelationEventId, relationEventPredicateMapping);
			/*
			 * I am trying to fool subject as Appellation event
			 * when we find a existing relation event been referred here
			 * I will give appellation event with predicate of referred relation event
			 */
			if(temp != null){
				subjectObject.setIsRelationEventObject(false);
				subjectObject.setAppellationEventObject(temp);
				subjectObject.setRemoteStatementId(tempRelationEventId);
				subjectObject.setRemoteLink(true);
			}else{
				subjectObject.setIsRelationEventObject(true);
				RelationEventObject relationEventObject   = new RelationEventObject();
				//isRelationEventPresentInStack(subjectRelationEventType.getRelationEventId(subjectRelationEventType),relationEventPredicateMapping);
				relationEventObject=parseThroughRelationEvent(subjectRelationEventType,relationEventObject,relationEventPredicateMapping);
				subjectObject.setRelationEventObject(relationEventObject);
			}
		}
		//	Check for Appellation event inside subject and add if any
		AppellationEventType appellationEventType = subjectObjectType.getAppellationEvent();
		if(appellationEventType == null){

		}else{
			String nodeId = appellationEventType.getAppellationEventID();
			List<TermType> termTypeList= appellationEventType.getTerms(appellationEventType);
			Iterator <TermType> termTypeIterator = termTypeList.iterator();
			while(termTypeIterator.hasNext()){
				TermType tt = termTypeIterator.next();
				AppellationEventObject appellationEventObject = new AppellationEventObject();
				appellationEventObject.setNode(conceptCollectionManager.getConceptLemmaFromConceptId(tt.getTermInterpertation(tt)));
				if(nodeId!=null){
					appellationEventObject.setTermId(nodeId);
				}else{
					appellationEventObject.setTermId(tt.getTermID(tt)+"_"+shortUUID());
				}
				subjectObject.setAppellationEventObject(appellationEventObject);
			}
		}
		return subjectObject;

	}


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public ObjectTypeObject parseThroughObject(RelationEventType relationEventType, SubjectObjectType subjectObjectType,List<List<Object>> relationEventPredicateMapping){

		//	Check for relation event inside subject and add if any
		RelationEventType objectRelationEventType = subjectObjectType.getRelationEvent();

		ObjectTypeObject objectTypeObject = new ObjectTypeObject();

		if(objectRelationEventType == null){
			objectTypeObject.setIsRelationEventObject(false);
		}else{
			String tempRelationEventId = objectRelationEventType.getRelationEventId(objectRelationEventType);
			AppellationEventObject temp = isRelationEventPresentInStack(tempRelationEventId,relationEventPredicateMapping);
			/*
			 * I am trying to fool subject as Appellation event
			 * when we find a existing relation event been referred here
			 * I will give appellation event with predicate of referred relation event
			 */
			if(temp != null){
				objectTypeObject.setIsRelationEventObject(false);
				objectTypeObject.setAppellationEventObject(temp);
				objectTypeObject.setRemoteStatementId(tempRelationEventId);
				objectTypeObject.setRemoteLink(true);
			}else{
				objectTypeObject.setIsRelationEventObject(true);
				RelationEventObject relationEventObject   = new RelationEventObject();
				//isRelationEventPresentInStack(objectRelationEventType.getRelationEventId(objectRelationEventType),relationEventPredicateMapping);
				relationEventObject=parseThroughRelationEvent(objectRelationEventType,relationEventObject,relationEventPredicateMapping);
				objectTypeObject.setRelationEventObject(relationEventObject);
			}
		}
		//	Check for Appellation event inside subject and add if any
		AppellationEventType appellationEventType = subjectObjectType.getAppellationEvent();
		if(appellationEventType == null){

		}else{
			String nodeId = appellationEventType.getAppellationEventID();
			List<TermType> termTypeList= appellationEventType.getTerms(appellationEventType);
			Iterator <TermType> termTypeIterator = termTypeList.iterator();
			while(termTypeIterator.hasNext()){
				TermType tt = termTypeIterator.next();
				AppellationEventObject appellationEventObject = new AppellationEventObject();
				appellationEventObject.setNode(conceptCollectionManager.getConceptLemmaFromConceptId(tt.getTermInterpertation(tt)));
				if(nodeId!=null){
					appellationEventObject.setTermId(nodeId);
				}else{
					appellationEventObject.setTermId(tt.getTermID(tt)+"_"+shortUUID());
				}
				objectTypeObject.setAppellationEventObject(appellationEventObject);
				logger.debug("subjectType Term : "+tt.getTermInterpertation(tt));
			}
		}
		return objectTypeObject;

	}


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public List<INodeObjectWithStatement> prepareNodeObjectContent(RelationEventObject relationEventObject,List<INodeObjectWithStatement> nodeObjectWithStatementList, String statementId){

		// Get predicate Object structure
		PredicateObject predicateObject = relationEventObject.getPredicateObject();
		NodeObject nodeObject = getPredicateNodeObjectContent(predicateObject,new NodeObject());


		// Get Subject Object into temp structure 
		SubjectObject subjectObject = relationEventObject.getSubjectObject();
		ObjectTypeObject objectTypeObject = relationEventObject.getObjectTypeObject();
		if(subjectObject.getIsRelationEventObject()){
			nodeObject.setSubject(subjectObject.getSubjectRelationPredictionAppellation(subjectObject));
			nodeObject.setSubjectId(subjectObject.getSubjectRelationPredictionAppellationTermId(subjectObject));
			if(subjectObject.isRemoteLink()){
				nodeObject.setSubjectRemoteLink(true);
			}
			logger.debug("Subject Predicate node : "+subjectObject.getSubjectRelationPredictionAppellation(subjectObject));

			// Get Object into temp structure 
			if(objectTypeObject.getIsRelationEventObject()){
				nodeObject.setObject(objectTypeObject.getObjectRelationPredictionAppellation(objectTypeObject));
				nodeObject.setObjectId(objectTypeObject.getObjectRelationPredictionAppellationTermId(objectTypeObject));
				if(objectTypeObject.isRemoteLink()){
					nodeObject.setObjectRemoteLink(true);
				}
				nodeObjectWithStatementList.add(nodeObjectWithStatementFactory.getNodeObjectWithStatementFactory(nodeObject,statementId,false));
				logger.debug("Object Predicate node : "+objectTypeObject.getObjectRelationPredictionAppellation(objectTypeObject));
			}else{

				AppellationEventObject appellationEventObject1 = objectTypeObject.getAppellationEventObject();
				nodeObject.setObject(appellationEventObject1.getNode());
				nodeObject.setObjectId(appellationEventObject1.getTermId());
				if(objectTypeObject.isRemoteLink()){
					nodeObject.setObjectRemoteLink(true);
				}
				nodeObjectWithStatementList.add(nodeObjectWithStatementFactory.getNodeObjectWithStatementFactory(nodeObject,statementId,false));

				logger.debug("Object Predicate : "+appellationEventObject1.getNode() );
			}

			nodeObjectWithStatementList = prepareNodeObjectContent(subjectObject.getRelationEventObject(),nodeObjectWithStatementList,statementId);

		}else{

			AppellationEventObject appellationEventObject1 = subjectObject.getAppellationEventObject();
			nodeObject.setSubject(appellationEventObject1.getNode());
			nodeObject.setSubjectId(appellationEventObject1.getTermId());
			if(subjectObject.isRemoteLink()){
				nodeObject.setSubjectRemoteLink(true);
			}
			logger.debug("Subject Predicate : "+appellationEventObject1.getNode() );
		}

		// Get Object into temp structure 
		if(objectTypeObject.getIsRelationEventObject()){
			nodeObjectWithStatementList = prepareNodeObjectContent(objectTypeObject.getRelationEventObject(),nodeObjectWithStatementList,statementId);
		}else{
			AppellationEventObject appellationEventObject1 = objectTypeObject.getAppellationEventObject();
			nodeObject.setObject(appellationEventObject1.getNode());
			nodeObject.setObjectId(appellationEventObject1.getTermId());
			if(objectTypeObject.isRemoteLink()){
				nodeObject.setObjectRemoteLink(true);
			}
			nodeObjectWithStatementList.add(nodeObjectWithStatementFactory.getNodeObjectWithStatementFactory(nodeObject,statementId,false));
			logger.debug("Object Predicate : "+appellationEventObject1.getNode() );
		}

		return nodeObjectWithStatementList;
	}



	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public NodeObject getPredicateNodeObjectContent (PredicateObject predicateObject, NodeObject nodeObject){
		AppellationEventObject appellationEventObject = predicateObject.getAppellationEventObject();
		// Store predicate detail in our temporary structure
		nodeObject.setRelationEventId(predicateObject.getRelationEventID());

		nodeObject.setPredicate(appellationEventObject.getNode());
		nodeObject.setPredicateId(appellationEventObject.getTermId());

		return nodeObject;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getPredicateNameFromStackOfAE(String relationEventId,String predicateName,List<List<Object>> relationEventPredicateMapping){
		Iterator<List<Object>> relationEventPredicateMappingIterator = relationEventPredicateMapping.iterator();

		while(relationEventPredicateMappingIterator.hasNext()){
			List<Object> objectList = relationEventPredicateMappingIterator.next();
			Iterator<Object> I1 = objectList.iterator();
			while(I1.hasNext()){
				Object object = I1.next();
				if(object instanceof String[]){
					String pairs[] = (String [])object;
					if(pairs[0].equals(relationEventId)){
						String predicateNameLocal = pairs[1];
						return predicateNameLocal;
					}
				}
			}
		}
		return "";

	}


	/**
	 * Check if we have bit streams in the network XML
	 * @param uri							URI is for DSpace based URI of type {@link String}
	 * @param bitStreamList					{@link List} of {@link IBitStream} objects
	 * @return								Returns boolean values true or false
	 * @author Lohith Dwaraka
	 */
	public boolean hasBitStream(String uri,List<IBitStream> bitStreamList){
		if(uri.isEmpty()){
			logger.debug("true");
			return true;
		}
		String fileId =uri=uri.substring(uri.lastIndexOf("/")+1,uri.length());
		for(IBitStream bitStream : bitStreamList){
			if(fileId.equals(bitStream.getId())){
				logger.debug("true");
				return true;
			}
		}
		logger.debug("false");
		return false;
	}


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String getCreationEventXmlStringFromQstore(String id)throws JAXBException{
		// Message converters for JAXb to understand the xml
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_XML);
		messageConverters.add(new StringHttpMessageConverter());
		org.springframework.oxm.Marshaller marshaler = jaxbMarshaller;
		org.springframework.oxm.Unmarshaller unmarshaler = jaxbMarshaller;
		messageConverters.add(new MarshallingHttpMessageConverter(marshaler,unmarshaler));

		restTemplate.setMessageConverters(messageConverters);

		// Setting up the http header accept type
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setAccept(mediaTypes);
		ResponseEntity<String> response = null;
		try{
			logger.debug("URL : "+getQStoreGetURL()+id);
			// Get the XML from QStore
			response = restTemplate.exchange(getQStoreGetURL()+id,
					HttpMethod.GET,
					new HttpEntity<String[]>(headers),
					String.class);
		}catch(Exception e){
			e.getMessage();
		}
		return response.getBody().toString();
	}


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String shortUUID() {
		UUID uuid = UUID.randomUUID();
		long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
		return Long.toString(l, Character.MAX_RADIX);
	}



	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	public String storeXMLQStore(String XML) throws ParserConfigurationException, SAXException, IOException {
		String res="";
		// add message converters
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		RestTemplate restTemplate = new RestTemplate();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_XML);
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		// prepare http headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setAccept(mediaTypes);
		HttpEntity<String> request = new HttpEntity<String>(XML,headers);

		try{
			// add xml in QStore
			res = restTemplate.postForObject(getQStoreAddURL(), request,String.class);
		}catch(Exception e){
			logger.error("QStore not accepting the xml, please check with the server logs.",e);
			//res = e.getMessage();
			return res;
		}
		return res;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * QStore allows us to get network XML for specific {@link RelationEventType} and also List of {@link RelationEventType} embedded in XML.
	 * 
	 */
	@Override
	public String getNetworkXMLFromQStoreForAListOfCStatements(String xml) {
		String res="";
		// Add message converters for JAxb
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		RestTemplate restTemplate = new RestTemplate();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_XML);
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		// Add http header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setAccept(mediaTypes);
		HttpEntity<String> request = new HttpEntity<String>(xml,headers);

		try{
			// Get complete network xml from QStore
			res = restTemplate.postForObject(getQStoreGetPOSTURL(), request,String.class);
		}catch(Exception e){
			logger.error("QStore not accepting the xml, please check with the server logs.",e);
			//res = e.getMessage();
			return res;
		}
		return res;
	}

	@Override
	@Transactional
	public String getNetworkXML(String networkId) throws Exception{
		VelocityEngine engine = restVelocityFactory.getVelocityRestEngine();
		Template template = null;
		StringWriter writer = null;
		String networkXML=null;
		try {
			engine.init();
			template = engine
					.getTemplate("velocitytemplates/getnetworksfromqstore.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			List<INetworkNodeInfo> networkTopNodes = getNetworkTopNodes(networkId);
			context.put("statmentList", networkTopNodes);
			writer = new StringWriter();
			template.merge(context, writer);
			logger.debug("XML : "+ writer.toString());
			networkXML = getNetworkXMLFromQStoreForAListOfCStatements(writer.toString());
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
		networkXML = networkXML.substring(networkXML.indexOf("element_events")-1,networkXML.length());
		return networkXML;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * This implementation uses the hibernate for dataaccess from the database
	 */
	@Override
	@Transactional
	public INetwork getNetwork(String networkId) throws QuadrigaStorageException{
		INetwork network = null;
		try{
			network = dbConnect.getNetwork(networkId);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return network;
	}


	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * This implementation uses the hibernate for dataaccess from the database
	 */
	@Override
	@Transactional
	public List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try{
			networkList=dbConnect.getNetworkList(user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * This implementation uses the hibernate for dataaccess from the database
	 */
	@Override
	@Transactional
	public List<INetworkNodeInfo> getAllNetworkNodes(String networkId)
			throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkNodeList = dbConnect.getNetworkNodes(networkId,INetworkManager.VERSION_ZERO);

		if(networkNodeList != null)
		{
			Iterator<INetworkNodeInfo> iterator = networkNodeList.iterator();
			while(iterator.hasNext())
			{
				INetworkNodeInfo networkNodeInfo = iterator.next();
				if(networkNodeInfo.getVersion() != 0)
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
	public String getProjectIdForWorkspaceId(String workspaceid) throws QuadrigaStorageException{
		if(workspaceid == null || workspaceid.equals(""))
			return null;
		//Get the project object associated with the workspace
		IProject project = projectManager.getProject(workspaceid);
		if(project != null)
			return project.getProjectId();
		else 
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
	public List<INetwork> getNetworksInProject(String projectid) throws QuadrigaStorageException{

		//Fetch the list of networks in the project
		List<INetwork> networksList = dbConnect.getNetworks(projectid);

		if(networksList != null){
			return networksList;
		}
		return null;
	}

	@Override
	@Transactional
	public List<INetwork> getAllNetworkVersions(String networkid) throws QuadrigaStorageException{


		List<INetwork> networksList = dbConnect.getAllNetworkVersions(networkid);

		if(networksList != null){
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
	public List<INetworkNodeInfo> getNetworkTopNodes(String networkId)throws QuadrigaStorageException
	{
		int versionNo = getLatestVersionOfNetwork(networkId);
		List<INetworkNodeInfo> networkNodeList = dbConnect.getNetworkNodes(networkId,versionNo);
		if(networkNodeList != null)
		{
			Iterator<INetworkNodeInfo> iterator = networkNodeList.iterator();
			while(iterator.hasNext())
			{
				INetworkNodeInfo networkNodeInfo = iterator.next();
				if(networkNodeInfo.getIsTop() != 1 )
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
	public List<INetworkNodeInfo> getNetworkTopNodesByVersion(String networkId, int versionNo)throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkNodeList = dbConnect.getNetworkNodes(networkId,versionNo);
		if(networkNodeList != null)
		{
			Iterator<INetworkNodeInfo> iterator = networkNodeList.iterator();
			while(iterator.hasNext())
			{
				INetworkNodeInfo networkNodeInfo = iterator.next();
				if(networkNodeInfo.getIsTop() != 1 || networkNodeInfo.getVersion() != versionNo)
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
	public void archiveNetwork(String networkId) throws QuadrigaStorageException{

		try{
			dbConnect.archiveNetwork(networkId);
		}catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException();
		}

	}

	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * This implementation uses the hibernate for dataaccess from the database
	 */
	@Override
	@Transactional
	public String updateNetworkName(String networkId,String networkName) throws QuadrigaStorageException{
		try{
			dbConnect.updateNetworkName(networkId, networkName);
		}catch(QuadrigaStorageException e){
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
	public String getNetworkJSTreeJson(String userName) throws JSONException{
		List<IProject> projectList = null;
		JSONObject core = new JSONObject();
		try{
			projectList = projectManager.getProjectList(userName);
			JSONArray dataArray = new JSONArray();

			for(IProject project : projectList){
				// Each data
				JSONObject data = new JSONObject();
				data.put("id",project.getProjectId());
				data.put("parent","#");
				data.put("text",project.getProjectName());
				dataArray.put(data);
				String wsParent = project.getProjectId();

				List<IWorkSpace> wsList = wsManager.listActiveWorkspace(project.getProjectId(), userName);
				for(IWorkSpace ws : wsList){
					//workspace json
					JSONObject data1 = new JSONObject();
					data1.put("id",ws.getWorkspaceId());
					data1.put("parent",wsParent);
					data1.put("text",ws.getWorkspaceName());
					dataArray.put(data1);
					String networkParent = ws.getWorkspaceId();

					List<INetwork> networkList1 = wsManager.getWorkspaceNetworkList(ws.getWorkspaceId());
					for(INetwork network : networkList1){
						JSONObject data2 = new JSONObject();
						data2.put("id",network.getId());
						data2.put("parent",networkParent);
						String networkLink = "<a href='#' id='"
								+ network.getId()
								+ "' name='"
								+ network.getName()
								+ "' onclick='javascript:clicknetwork(this.id,this.name);' > "
								+ network.getName() + "</a>";
						data2.put("text", networkLink);
						data2.put("href", "networks/visualize/"+network.getId());
						JSONObject data2href = new JSONObject();
						data2href.put("href", "networks/visualize/"+network.getId());
						dataArray.put(data2);
					}
				}
			}
			JSONObject dataList = new JSONObject();
			dataList.put("data", dataArray);

			core.put("core", dataList);
		}catch(QuadrigaStorageException e) {
			logger.error("DB Error while fetching project, Workspace and network details",e);
		}
		return core.toString(SUCCESS);
	}


	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * This implementation uses the hibernate for dataaccess from the database
	 */
	@Override
	@Transactional
	public INetworkJSon getJsonForOldNetworks(String networkId, String jqueryType,String versionID) throws QuadrigaStorageException{

		INetworkJSon networkJSon=null;

		List<INetworkNodeInfo> oldNetworkTopNodesList = null;

		try{
			oldNetworkTopNodesList = getNetworkTopNodesByVersion(networkId, Integer.parseInt(versionID));
		}catch(QuadrigaStorageException e){
			logger.error("DB Error while getting network top nodes",e);
		}

		if(jqueryType.equals(INetworkManager.D3JQUERY)){
			networkJSon = d3NetworkManager.parseNetworkForD3Jquery(oldNetworkTopNodesList);

		}else if(jqueryType.equals(INetworkManager.JITJQUERY)){
			String jitJSon = jitNetworkManager.parseNetworkForJITJquery(oldNetworkTopNodesList);
			if(networkJSon ==null){
				networkJSon = new NetworkJSon(jitJSon, null);
			}
		}		

		return networkJSon;
	}


	/**
	 * 
	 * {@inheritDoc}
	 * 
	 * This implementation uses the hibernate for dataaccess from the database
	 */
	@Override
	@Transactional
	public String storeNetworkDetails(String xml, IUser user, String networkName,String workspaceId, String uploadStatus, String networkId, int version) throws JAXBException{
		ElementEventsType elementEventType = unMarshalXmlToElementEventsType(xml);

		// Get Workspace details.
		IWorkSpace workspace = null;
		try {
			workspace = wsManager.getWorkspaceDetails(workspaceId, user.getUserName());
		} catch (QuadrigaStorageException e3) {
			logger.error("Error while getting workspace details",e3);
		} catch (QuadrigaAccessException e3) {
			logger.error("User doesn't have access to workspace",e3);
		}

		// Get DSpace of the workspace
		List<IBitStream> bitStreamList = workspace.getBitstreams();

		NewNetworkDetailsCache newNetworkDetailCache = new NewNetworkDetailsCache();

		// Below code reads the top level Appelation events 

		newNetworkDetailCache = parseNewNetworkStatement(elementEventType,bitStreamList,newNetworkDetailCache);

		// Check if it DSpace is present in the XML
		if(!newNetworkDetailCache.isFileExists()){
			logger.info("Network not uploaded");
			logger.info("Some of the text files in the uploaded network were not present in the workspace");
			return INetworkManager.DSPACEERROR;
		}

		// Add network into database 
		if(uploadStatus == INetworkManager.NEWNETWORK){
			try{
				networkId=dbConnect.addNetworkRequest(networkName, user,workspaceId);
			}catch(QuadrigaStorageException e1){
				logger.error("DB action error ",e1);
			}
		}

		List<String []> networkDetailsCache = newNetworkDetailCache.getNetworkDetailsCache();
		// Add network statements for networks
		for(String node[] : networkDetailsCache){
			try{
				String rowid = generateUniqueID();
				dbConnect.addNetworkStatement(rowid,networkId,node[0],node[1], node[2], user,version);
			}catch(QuadrigaStorageException e1){
				logger.error("DB error while adding network statment",e1);
			}
		}
		return networkId;
	}

	/**
	 * Parsing each network statement of the request network input. Network would contain Appellation Event and Relation Event
	 * @param elementEventType						{@link ElementEventsType} object
	 * @param bitStreamList							{@link List} of {@link IBitStream}
	 * @param newNetworkDetailCache					{@link NewNetworkDetailsCache} object to hold the cache of network details
	 * @return										Returns updated {@link NewNetworkDetailsCache} object which holds the cache of network details
	 */
	private NewNetworkDetailsCache parseNewNetworkStatement(
			ElementEventsType elementEventType, List<IBitStream> bitStreamList,
			NewNetworkDetailsCache newNetworkDetailCache) {	

		List<CreationEvent> creationEventList =elementEventType.getRelationEventOrAppellationEvent();
		Iterator <CreationEvent> creationEventIterator= creationEventList.iterator();
		while(creationEventIterator.hasNext()){
			CreationEvent creationEvent = creationEventIterator.next();
			// Cache Appellation Events
			if(creationEvent instanceof AppellationEventType){
				newNetworkDetailCache =  parseNewAppellationEvent(newNetworkDetailCache, creationEvent, bitStreamList);
			}
			// Cache Relation Events
			if(creationEvent instanceof RelationEventType){
				newNetworkDetailCache =  parseNewRelationEvent(newNetworkDetailCache, creationEvent, bitStreamList);

			}
		}
		return newNetworkDetailCache;
	}

	/**
	 * Parses the Appellation Event and stores the Appellation event in the cache.
	 * @param newNetworkDetailCache					{@link NewNetworkDetailsCache} object to hold the cache of network details
	 * @param creationEvent							{@link CreationEvent} object of {@link AppellationEventType} type.
	 * @param bitStreamList							{@link List} of {@link IBitStream} object
	 * @return										Returns updated {@link NewNetworkDetailsCache} object which holds the cache of network details
	 */
	public NewNetworkDetailsCache parseNewAppellationEvent(NewNetworkDetailsCache newNetworkDetailCache,CreationEvent creationEvent,List<IBitStream> bitStreamList){


		List<JAXBElement<?>> elementsList = creationEvent.getIdOrCreatorOrCreationDate();
		Iterator <JAXBElement<?>> elementsIterator = elementsList.iterator();
		while(elementsIterator.hasNext()){
			JAXBElement<?> element = (JAXBElement<?>) elementsIterator.next();
			if(element.getName().toString().contains("id")){
				String networkNodeInfo[] = { element.getValue().toString(),INetworkManager.APPELLATIONEVENT, INetworkManager.TOPNODE};
				newNetworkDetailCache.getNetworkDetailsCache().add(networkNodeInfo);
			}
			// Check if dspace file exists. 
			if(element.getName().toString().contains("source_reference")){
				logger.debug("Dspace file : "+element.getValue().toString());
				boolean dspaceFileExists = hasBitStream(element.getValue().toString(), bitStreamList);
				if(dspaceFileExists == false){
					newNetworkDetailCache.setFileExists(false);
				}
			}
		}

		return newNetworkDetailCache;
	}


	/**
	 * Parses the Relation Event and stores the Relation event in the cache.
	 * @param newNetworkDetailCache					{@link NewNetworkDetailsCache} object to hold the cache of network details
	 * @param creationEvent							{@link CreationEvent} object of {@link RelationEventType} type.
	 * @param bitStreamList							{@link List} of {@link IBitStream} object
	 * @return										Returns updated {@link NewNetworkDetailsCache} object which holds the cache of network details
	 */
	public NewNetworkDetailsCache parseNewRelationEvent(NewNetworkDetailsCache newNetworkDetailCache,CreationEvent creationEvent,List<IBitStream> bitStreamList){


		List<JAXBElement<?>> elementsList = creationEvent.getIdOrCreatorOrCreationDate();
		Iterator <JAXBElement<?>> elementsIterator = elementsList.iterator();
		while(elementsIterator.hasNext()){
			JAXBElement<?> element = (JAXBElement<?>) elementsIterator.next();

			// get relation event id
			if(element.getName().toString().contains("id")){
				String networkNodeInfo[] = { element.getValue().toString(),INetworkManager.RELATIONEVENT, INetworkManager.TOPNODE};
				newNetworkDetailCache.getNetworkDetailsCache().add(networkNodeInfo);
			}

			// get dspace quadriga URL
			if(element.getName().toString().contains("source_reference")){
				boolean dspaceFileExists = hasBitStream(element.getValue().toString(), bitStreamList);
				if(dspaceFileExists == false){
					newNetworkDetailCache.setFileExists(false);
				}
			}

		}
		RelationEventType relationEventType = (RelationEventType) (creationEvent);
		try{
			// Go Recursively and check for Relation event within a relation events
			newNetworkDetailCache = parseIntoRelationEventElement(relationEventType,newNetworkDetailCache,bitStreamList);
		}catch(QuadrigaStorageException se){
			logger.error("DB Storage issue",se);
		}


		return newNetworkDetailCache;
	}


	/**
	 * Parse into the Relation Events by searches for subject, object and predicate.
	 * @param relationEventType								{@link RelationEventType} object
	 * @param newNetworkDetailCache							{@link NewNetworkDetailsCache} object to hold the cache of network details
	 * @param bitStreamList									{@link List} of {@link IBitStream} object
	 * @return												Returns updated {@link NewNetworkDetailsCache} object which holds the cache of network details
	 * @throws QuadrigaStorageException						Throws Database storage exception
	 */
	public NewNetworkDetailsCache parseIntoRelationEventElement(RelationEventType relationEventType, NewNetworkDetailsCache newNetworkDetailCache,List<IBitStream> bitStreamList) throws QuadrigaStorageException{

		List <?> creatorOrRelationList = relationEventType.getRelationCreatorOrRelation();
		Iterator <?> creatorOrRelationIterator=creatorOrRelationList.iterator();

		while(creatorOrRelationIterator.hasNext()){
			Object o = creatorOrRelationIterator.next();
			if(o instanceof RelationType){
				RelationType relationType = (RelationType) o;
				List<JAXBElement<?>> elementsList =relationType.getIdOrCreatorOrCreationDate();
				Iterator <JAXBElement<?>> elementsIterator = elementsList.iterator();
				while(elementsIterator.hasNext()){
					JAXBElement<?> element = (JAXBElement<?>) elementsIterator.next();

					if(element.getValue().toString().contains("SubjectObjectType")){
						//	Handles the subject part of the relation
						if(element.getName().toString().contains("subject")){
							SubjectObjectType  subject= (SubjectObjectType) element.getValue();
							newNetworkDetailCache = parseNewSubjectObjectType(newNetworkDetailCache, subject, bitStreamList);

						}else{
							//	Handles the object part of the relation
							if(element.getName().toString().contains("object")){

								SubjectObjectType  object= (SubjectObjectType) element.getValue();
								newNetworkDetailCache = parseNewSubjectObjectType(newNetworkDetailCache, object, bitStreamList);

							}
						}
					}else{ 
						//	Handles the predicate part of the relation
						if(element.getValue().toString().contains("PredicateType")){

							PredicateType  predicateType= (PredicateType) element.getValue();
							AppellationEventType appellationEventType = predicateType.getAppellationEvent();
							newNetworkDetailCache = parseNewAppellationEventFoundInRelationEvent(newNetworkDetailCache, appellationEventType, bitStreamList);
						}
					}
				}
			}
		}

		return newNetworkDetailCache;
	}

	/**
	 * Parse {@link SubjectObjectType} and store the appropriate ID in the cache
	 * @param newNetworkDetailCache						{@link NewNetworkDetailsCache} object to hold the cache of network details
	 * @param subjectOrObject							{@link SubjectObjectType} object 
	 * @param bitStreamList								{@link List} of {@link IBitStream} object	
	 * @return											Returns updated {@link NewNetworkDetailsCache} object which holds the cache of network details
	 * @throws QuadrigaStorageException					Throws Database storage exception
	 */
	public NewNetworkDetailsCache parseNewSubjectObjectType(NewNetworkDetailsCache newNetworkDetailCache,SubjectObjectType subjectOrObject,List<IBitStream> bitStreamList) throws QuadrigaStorageException{


		//	Check for relation event inside subject
		RelationEventType relationEventType = subjectOrObject.getRelationEvent();
		if(relationEventType == null){
			// Check for Appellation event inside subject and add if any
			AppellationEventType appellationEventType = subjectOrObject.getAppellationEvent();
			newNetworkDetailCache = parseNewAppellationEventFoundInRelationEvent(newNetworkDetailCache, appellationEventType, bitStreamList);	
		}else{
			List<JAXBElement<?>> elementsList = relationEventType.getIdOrCreatorOrCreationDate();
			Iterator <JAXBElement<?>> elementsIterator = elementsList.iterator();
			while(elementsIterator.hasNext()){
				JAXBElement<?> elements = (JAXBElement<?>) elementsIterator.next();

				if(elements.getName().toString().contains("id")){
					String networkNodeInfo[] = { elements.getValue().toString(),INetworkManager.RELATIONEVENT, INetworkManager.NONTOPNODE};
					newNetworkDetailCache.getNetworkDetailsCache().add(networkNodeInfo);
				}

				if(elements.getName().toString().contains("source_reference")){
					boolean dspaceFileExists = hasBitStream(elements.getValue().toString(), bitStreamList);
					if(dspaceFileExists == false){
						newNetworkDetailCache.setFileExists(false);
					}
				}
			}
			newNetworkDetailCache=parseIntoRelationEventElement(relationEventType,newNetworkDetailCache,bitStreamList);
		}

		return newNetworkDetailCache;
	}



	/**
	 * Parse {@link AppellationEventType} usually which is found in Predicate and store the appropriate ID in the cache
	 * @param newNetworkDetailCache					{@link NewNetworkDetailsCache} object to hold the cache of network details
	 * @param appellationEventType					{@link AppellationEventType}objects
	 * @param bitStreamList							{@link List} of {@link IBitStream} object
	 * @return										Returns updated {@link NewNetworkDetailsCache} object which holds the cache of network details
	 */
	public NewNetworkDetailsCache parseNewAppellationEventFoundInRelationEvent(NewNetworkDetailsCache newNetworkDetailCache,AppellationEventType appellationEventType,List<IBitStream> bitStreamList){


		//	Check for Appellation event inside predicate
		if(appellationEventType == null){
			logger.debug("AE1 is null");
		}else{
			logger.debug("AE1 found object");
			List<JAXBElement<?>> elementsList = appellationEventType.getIdOrCreatorOrCreationDate();
			Iterator <JAXBElement<?>> elementsIterator = elementsList.iterator();
			while(elementsIterator.hasNext()){
				JAXBElement<?> element = (JAXBElement<?>) elementsIterator.next();

				if(element.getName().toString().contains("id")){
					String networkNodeInfo[] = { element.getValue().toString(),INetworkManager.APPELLATIONEVENT, INetworkManager.NONTOPNODE};
					newNetworkDetailCache.getNetworkDetailsCache().add(networkNodeInfo);
				}

				if(element.getName().toString().contains("source_reference")){
					boolean dspaceFileExists = hasBitStream(element.getValue().toString(), bitStreamList);
					if(dspaceFileExists == false){
						newNetworkDetailCache.setFileExists(false);
					}
				}
			}
		}

		return newNetworkDetailCache;
	}

	/**
	 * This inner class would be used to cache the network details of newly uploaded network.
	 * We use hold the cache until all the data in the uploaded network seems legitimate as per our general rules of network. 
	 * @author Lohith Dwaraka
	 *
	 */
	class NewNetworkDetailsCache{

		List <String[]> networkDetailsCache;
		boolean fileExists;

		NewNetworkDetailsCache(){
			this.networkDetailsCache = new ArrayList<String[]>();
			this.fileExists = true;
		}

		public List<String[]> getNetworkDetailsCache() {
			return networkDetailsCache;
		}
		public void setNetworkDetailsCache(List<String[]> networkDetailsCache) {
			this.networkDetailsCache = networkDetailsCache;
		}
		public boolean isFileExists() {
			return fileExists;
		}
		public void setFileExists(boolean fileExists) {
			this.fileExists = fileExists;
		}

	}


	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public int getLatestVersionOfNetwork(String networkID)
			throws QuadrigaStorageException{
		List<Integer> latestVersion = dbConnect.getLatestVersionOfNetwork(networkID);
		if(latestVersion==null || latestVersion.size()==0||latestVersion.get(0)==null){
			return -1;
		}

		int version =latestVersion.get(0);
		return version;
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	@Override
	@Transactional
	public List<INetwork> getNetworksOfOwner(IUser user)
			throws QuadrigaStorageException {
		List<INetwork> networkList = null;

		try {

			networkList = dbConnect.getNetworkOfOwner(user);
		}catch(QuadrigaStorageException e){
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
	public int getNetworkStatusCode(String status){
		if(status==null)
			return INetworkStatus.UNKNOWN_CODE;
		if(status.equals(INetworkStatus.APPROVED))
			return INetworkStatus.APPROVED_CODE;
		if(status.equals(INetworkStatus.REJECTED))
			return INetworkStatus.REJECTED_CODE;
		if(status.equals(INetworkStatus.ASSIGNED))
			return INetworkStatus.ASSIGNED_CODE;
		if(status.equals(INetworkStatus.PENDING))
			return INetworkStatus.PENDING_CODE;

		return INetworkStatus.UNKNOWN_CODE;

	}

	@Override
	public List<INetwork> editNetworkStatusCode(List<INetwork> networkList){

		if(networkList==null){
			return networkList;
		}

		Iterator<INetwork> networkListIterator = networkList.iterator();
		while(networkListIterator.hasNext()){
			INetwork network = networkListIterator.next();
			network.setStatus(getNetworkStatusCode(network.getStatus())+"");
		}

		return networkList;
	}

	@Override
	@Transactional
	public String getSourceReferenceURL(String networkId,int versionNo) throws QuadrigaStorageException, JAXBException, QStoreStorageException{
		String sourceReferenceUrl = null;
		List<INetworkNodeInfo> networkNodeInfoList = getNetworkTopNodesByVersion(networkId, versionNo);
		if(networkNodeInfoList != null){
			INetworkNodeInfo someNetworkNodeInfo = networkNodeInfoList.get(0);
			String statementID = someNetworkNodeInfo.getId();
			ElementEventsType elementEventsType = getElementEventTypeFromCreationEventTypeID(statementID);
			return getSourceReferenceFromElementEventsType(elementEventsType);
		}
		return sourceReferenceUrl;
	}


	@Override
	public String getSourceReferenceFromElementEventsType(ElementEventsType elementEventsType){
		String sourceReferenceUrl = null;

		List<CreationEvent> creationEventList =elementEventsType.getRelationEventOrAppellationEvent();
		Iterator <CreationEvent> creationEventIterator= creationEventList.iterator();

		while(creationEventIterator.hasNext()){
			CreationEvent creationEvent = creationEventIterator.next();
			// Check if event is Appellation event
			if(creationEvent instanceof AppellationEventType)
			{
				AppellationEventType appellationEventType = (AppellationEventType) creationEvent;
				return appellationEventType.getAppellationSourceReference();
			}
			// Check if event is Relation event
			if(creationEvent instanceof RelationEventType){
				RelationEventType relationEventType = (RelationEventType) creationEvent;

				return relationEventType.getRelationEventSourceReference();
			}
		}

		return sourceReferenceUrl;
	}

}
