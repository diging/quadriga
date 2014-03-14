package edu.asu.spring.quadriga.service.network.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
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
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.INetworkFactory;
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
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
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
 * This class acts as a Network manager which handles the networks object
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

	boolean fileExist=true;

	@Autowired
	private IDBConnectionNetworkManager dbConnect;

	/**
	 * Getter for DSpace file existance in the network XML	
	 * @return				Returns boolean value of DSpace existance
	 */
	public boolean getFileExist(){
		return this.fileExist;
	}

	/**
	 * Setter for DSpace file existance in the network XML	
	 * @param fileExist				boolean value of DSpace existance
	 */
	public void setFileExist(boolean fileExist){
		this.fileExist = fileExist;
	}

	/* 
	 * Prepare a QStore Add URL to add new networks to QStore
	 */
	@Override
	public String getQStoreAddURL() {
		return qStoreURL+qStoreURL_Add;
	}

	/* 
	 * Prepare a QStore Get URL to fetch element of networks from Qstore
	 */
	@Override
	public String getQStoreGetURL() {
		return qStoreURL+qStoreURL_Get;
	}

	/* 
	 * Prepare a QStore Get URL to fetch all the element of networks from Qstore
	 */
	@Override
	public String getQStoreGetPOSTURL() {
		return qStoreURL+qStoreURL_Get_POST;
	}

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
	
	@Override
	public List<INodeObjectWithStatement> parseEachStatement(String relationEventId,String statementType, String statementId, List<List<Object>> relationEventPredicateMapping, List<INodeObjectWithStatement> nodeObjectWithStatementList) throws JAXBException, QStoreStorageException{
		ElementEventsType elementEventType =getElementEventTypeFromRelationEvent(relationEventId);
		List<CreationEvent> creationEventList =elementEventType.getRelationEventOrAppellationEvent();
		Iterator <CreationEvent> creationEventIterator= creationEventList.iterator();
		
		while(creationEventIterator.hasNext()){
			CreationEvent creationEvent = creationEventIterator.next();
			// Check if event is Appellation event
			if(creationEvent instanceof AppellationEventType)
			{
				// Do nothing, we no need to display appellation events on UI.
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


	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.ID3NetworkManager#getElementEventTypeFromRelationEvent(java.lang.String)
	 */
	@Override
	public ElementEventsType getElementEventTypeFromRelationEvent(String relationEventId) throws JAXBException, QStoreStorageException{
		String xml = getNodeXmlStringFromQstore(relationEventId);
		ElementEventsType elementEventType = null;
		if(xml ==null){
			throw new QStoreStorageException("Some issue retriving data from Qstore, Please check the logs related to Qstore");
		}else{

			// Initialize ElementEventsType object for relation event
			elementEventType = unMarshalXmlToElementEventsType(xml);
		}	
		return elementEventType;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.ID3NetworkManager#unMarshalXmlToElementEventsType(java.lang.String)
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

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.ID3NetworkManager#parseThroughRelationEvent(edu.asu.spring.quadriga.domain.impl.networks.RelationEventType, edu.asu.spring.quadriga.domain.impl.networks.jsonobject.RelationEventObject, java.util.List)
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

	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.ID3NetworkManager#stackRelationEventPredicateAppellationObject(java.lang.String, java.lang.String, edu.asu.spring.quadriga.domain.impl.networks.jsonobject.AppellationEventObject, java.util.List)
	 */
	@Override
	public String stackRelationEventPredicateAppellationObject(String relationEventId,String predicateName,AppellationEventObject appellationEventObject,List<List<Object>> relationEventPredicateMapping){
		Iterator<List<Object>> relationEventPredicateMappingIterator = relationEventPredicateMapping.iterator();

		while(relationEventPredicateMappingIterator.hasNext()){
			List<Object> objectList = relationEventPredicateMappingIterator.next();
			Iterator<Object> objectIterator = objectList.iterator();
			while(objectIterator.hasNext()){
				Object object = objectIterator.next();
				if(object instanceof String[]){
					String pairs[] = (String [])object;
					if(pairs[0].equals(relationEventId)){
						String predicateNameLocal = pairs[1];
						return predicateNameLocal;
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

		return null;

	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.ID3NetworkManager#parseThroughPredicate(edu.asu.spring.quadriga.domain.impl.networks.RelationEventType, edu.asu.spring.quadriga.domain.impl.networks.PredicateType, java.util.List)
	 */
	@Override
	public PredicateObject parseThroughPredicate(RelationEventType relationEventType, PredicateType predicateType,List<List<Object>> relationEventPredicateMapping){
		//	Predicate has only appellation event, so get appellation event inside the predicate
		AppellationEventType appellationEvent = predicateType.getAppellationEvent();
		PredicateObject predicateObject = null;
		List<TermType> termTypeList= appellationEvent.getTerms(appellationEvent);
		Iterator <TermType> termTypeIterator = termTypeList.iterator();
		while(termTypeIterator.hasNext()){
			TermType tt = termTypeIterator.next();
			AppellationEventObject appellationEventObject = new AppellationEventObject();
			appellationEventObject.setNode(conceptCollectionManager.getConceptLemmaFromConceptId(tt.getTermInterpertation(tt))+"_"+shortUUID());
			appellationEventObject.setTermId(tt.getTermID(tt)+"_"+shortUUID());
			predicateObject = new PredicateObject();
			predicateObject.setAppellationEventObject(appellationEventObject);

			predicateObject.setRelationEventID(relationEventType.getRelationEventId(relationEventType));
			stackRelationEventPredicateAppellationObject(relationEventType.getRelationEventId(relationEventType),predicateObject.getAppellationEventObject().getNode(),appellationEventObject, relationEventPredicateMapping);
			return predicateObject;
		}
		return predicateObject;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.ID3NetworkManager#checkRelationEventInStack(java.lang.String, java.util.List)
	 */
	@Override
	public AppellationEventObject checkRelationEventInStack(String relationEventId,List<List<Object>> relationEventPredicateMapping){

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
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.ID3NetworkManager#parseThroughSubject(edu.asu.spring.quadriga.domain.impl.networks.RelationEventType, edu.asu.spring.quadriga.domain.impl.networks.SubjectObjectType, java.util.List)
	 */
	@Override
	public SubjectObject parseThroughSubject(RelationEventType relationEventType, SubjectObjectType subjectObjectType,List<List<Object>> relationEventPredicateMapping){

		//	Check for relation event inside subject and add if any
		RelationEventType subjectRelationEventType = subjectObjectType.getRelationEvent();

		SubjectObject subjectObject = new SubjectObject();

		if(subjectRelationEventType == null){
			subjectObject.setIsRelationEventObject(false);
		}else{
			AppellationEventObject temp = checkRelationEventInStack(subjectRelationEventType.getRelationEventId(subjectRelationEventType),relationEventPredicateMapping);
			/*
			 * I am trying to fool subject as Appellation event
			 * when we find a existing relation event been referred here
			 * I will give appellation event with predicate of referred relation event
			 */
			if(!(temp == null)){
				subjectObject.setIsRelationEventObject(false);
				subjectObject.setAppellationEventObject(temp);
			}else{
				subjectObject.setIsRelationEventObject(true);
				RelationEventObject relationEventObject   = new RelationEventObject();
				checkRelationEventInStack(subjectRelationEventType.getRelationEventId(subjectRelationEventType),relationEventPredicateMapping);
				relationEventObject=parseThroughRelationEvent(subjectRelationEventType,relationEventObject,relationEventPredicateMapping);
				subjectObject.setRelationEventObject(relationEventObject);
			}
		}
		//	Check for Appellation event inside subject and add if any
		AppellationEventType appellationEventType = subjectObjectType.getAppellationEvent();
		if(appellationEventType == null){
			
		}else{
			List<TermType> termTypeList= appellationEventType.getTerms(appellationEventType);
			Iterator <TermType> termTypeIterator = termTypeList.iterator();
			while(termTypeIterator.hasNext()){
				TermType tt = termTypeIterator.next();
				AppellationEventObject appellationEventObject = new AppellationEventObject();
				appellationEventObject.setNode(conceptCollectionManager.getConceptLemmaFromConceptId(tt.getTermInterpertation(tt)));
				appellationEventObject.setTermId(tt.getTermID(tt)+"_"+shortUUID());
				subjectObject.setAppellationEventObject(appellationEventObject);
				logger.debug("subjectType Term : "+tt.getTermInterpertation(tt));
			}
		}
		return subjectObject;
		
	}
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.ID3NetworkManager#parseThroughObject(edu.asu.spring.quadriga.domain.impl.networks.RelationEventType, edu.asu.spring.quadriga.domain.impl.networks.SubjectObjectType, java.util.List)
	 */
	@Override
	public ObjectTypeObject parseThroughObject(RelationEventType relationEventType, SubjectObjectType subjectObjectType,List<List<Object>> relationEventPredicateMapping){

		//	Check for relation event inside subject and add if any
		RelationEventType objectRelationEventType = subjectObjectType.getRelationEvent();

		ObjectTypeObject objectTypeObject = new ObjectTypeObject();

		if(objectRelationEventType == null){
			objectTypeObject.setIsRelationEventObject(false);
		}else{
			AppellationEventObject temp = checkRelationEventInStack(objectRelationEventType.getRelationEventId(objectRelationEventType),relationEventPredicateMapping);
			/*
			 * I am trying to fool subject as Appellation event
			 * when we find a existing relation event been referred here
			 * I will give appellation event with predicate of referred relation event
			 */
			if(!(temp == null)){
				objectTypeObject.setIsRelationEventObject(false);
				objectTypeObject.setAppellationEventObject(temp);
			}else{
				objectTypeObject.setIsRelationEventObject(true);
				RelationEventObject relationEventObject   = new RelationEventObject();
				checkRelationEventInStack(objectRelationEventType.getRelationEventId(objectRelationEventType),relationEventPredicateMapping);
				relationEventObject=parseThroughRelationEvent(objectRelationEventType,relationEventObject,relationEventPredicateMapping);
				objectTypeObject.setRelationEventObject(relationEventObject);
			}
		}
		//	Check for Appellation event inside subject and add if any
		AppellationEventType appellationEventType = subjectObjectType.getAppellationEvent();
		if(appellationEventType == null){
			
		}else{
			List<TermType> termTypeList= appellationEventType.getTerms(appellationEventType);
			Iterator <TermType> termTypeIterator = termTypeList.iterator();
			while(termTypeIterator.hasNext()){
				TermType tt = termTypeIterator.next();
				AppellationEventObject appellationEventObject = new AppellationEventObject();
				appellationEventObject.setNode(conceptCollectionManager.getConceptLemmaFromConceptId(tt.getTermInterpertation(tt)));
				appellationEventObject.setTermId(tt.getTermID(tt)+"_"+shortUUID());
				objectTypeObject.setAppellationEventObject(appellationEventObject);
				logger.debug("subjectType Term : "+tt.getTermInterpertation(tt));
			}
		}
		return objectTypeObject;
		
	}
	
	
	/**
	 * Use temp structure to allow our json builder work easily.
	 * {@link NodeObject} is used to build this temp structure. {@link NodeObject} has a predicate, subject object structure in it.
	 * I am recursively filling the {@link NodeObject}.
	 * @param relationEventObject
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
			logger.debug("Subject Predicate node : "+subjectObject.getSubjectRelationPredictionAppellation(subjectObject));
			
			// Get Object into temp structure 
			if(objectTypeObject.getIsRelationEventObject()){
				nodeObject.setObject(objectTypeObject.getObjectRelationPredictionAppellation(objectTypeObject));
				nodeObject.setObjectId(objectTypeObject.getObjectRelationPredictionAppellationTermId(objectTypeObject));
				nodeObjectWithStatementList.add(nodeObjectWithStatementFactory.getNodeObjectWithStatementFactory(nodeObject,statementId));
				logger.debug("Object Predicate node : "+objectTypeObject.getObjectRelationPredictionAppellation(objectTypeObject));
			}else{

				AppellationEventObject appellationEventObject1 = objectTypeObject.getAppellationEventObject();
				nodeObject.setObject(appellationEventObject1.getNode());
				nodeObject.setObjectId(appellationEventObject1.getTermId());
				nodeObjectWithStatementList.add(nodeObjectWithStatementFactory.getNodeObjectWithStatementFactory(nodeObject,statementId));
				logger.debug("Object Predicate : "+appellationEventObject1.getNode() );
			}
			
			nodeObjectWithStatementList = prepareNodeObjectContent(subjectObject.getRelationEventObject(),nodeObjectWithStatementList,statementId);
			
		}else{

			AppellationEventObject appellationEventObject1 = subjectObject.getAppellationEventObject();
			nodeObject.setSubject(appellationEventObject1.getNode());
			nodeObject.setSubjectId(appellationEventObject1.getTermId());
			logger.debug("Subject Predicate : "+appellationEventObject1.getNode() );
		}
		
		// Get Object into temp structure 
		if(objectTypeObject.getIsRelationEventObject()){
			nodeObjectWithStatementList = prepareNodeObjectContent(objectTypeObject.getRelationEventObject(),nodeObjectWithStatementList,statementId);
		}else{
			AppellationEventObject appellationEventObject1 = objectTypeObject.getAppellationEventObject();
			nodeObject.setObject(appellationEventObject1.getNode());
			nodeObject.setObjectId(appellationEventObject1.getTermId());
			nodeObjectWithStatementList.add(nodeObjectWithStatementFactory.getNodeObjectWithStatementFactory(nodeObject,statementId));
			logger.debug("Object Predicate : "+appellationEventObject1.getNode() );
		}
		
		return nodeObjectWithStatementList;
	}
	
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.network.impl.ID3NetworkManager#getPredicateNodeObjectContent(edu.asu.spring.quadriga.domain.impl.networks.jsonobject.PredicateObject, edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject)
	 */
	@Override
	public NodeObject getPredicateNodeObjectContent (PredicateObject predicateObject, NodeObject nodeObject){
		AppellationEventObject appellationEventObject = predicateObject.getAppellationEventObject();
		// Store predicate detail in our temporary structure
		nodeObject.setRelationEventId(predicateObject.getRelationEventID());

		nodeObject.setPredicate(appellationEventObject.getNode());
		nodeObject.setPredicateId(appellationEventObject.getTermId());
		logger.debug("Predicate : "+appellationEventObject.getNode() );
		
		return nodeObject;
	}

	@Override
	public String checkRelationEventRepeatation(String relationEventId,String predicateName,List<List<Object>> relationEventPredicateMapping){
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
						logger.debug(" relationEventId  :" +relationEventId +" id : "+pairs[0]+"predicate Name"+predicateNameLocal );
						return predicateNameLocal;
					}
				}
			}
		}
		return "";

	}
	
	/**
	 * Store the top network RE and AE IDs into DB by parsing through the XML and object goes through the event types 
	 * @param response
	 * @param user
	 * @param networkName
	 * @param workspaceid
	 * @author Lohith Dwaraka
	 */
	@Override
	@Transactional
	public String receiveNetworkSubmitRequest(JAXBElement<ElementEventsType> response,IUser user,String networkName,String workspaceid,String updateStatus,String networkId){

		setFileExist(true);
		List <String[]> networkDetailsCache = new ArrayList<String[]>();
		IWorkSpace workspace = null;
		try {
			workspace = wsManager.getWorkspaceDetails(workspaceid, user.getUserName());
		} catch (QuadrigaStorageException e3) {
			e3.printStackTrace();
		} catch (QuadrigaAccessException e3) {
			e3.printStackTrace();
		}


		// Get DSpace of the workspace
		List<IBitStream> bitStreamList = workspace.getBitstreams();
		logger.info("list of bitstream");
		Iterator <IBitStream> I2 = bitStreamList.iterator();
		while(I2.hasNext()){
			logger.info(I2.next().getId()+"");
		}
		// Below code reads the top level Appelation events 

		ElementEventsType e = response.getValue();
		List<CreationEvent> c =e.getRelationEventOrAppellationEvent();
		Iterator <CreationEvent> I= c.iterator();
		while(I.hasNext()){
			CreationEvent ce = I.next();
			if(ce instanceof AppellationEventType)
			{
				List<JAXBElement<?>> e2 = ce.getIdOrCreatorOrCreationDate();
				Iterator <JAXBElement<?>> I1 = e2.iterator();
				while(I1.hasNext()){
					JAXBElement<?> element = (JAXBElement<?>) I1.next();
					// get id
					if(element.getName().toString().contains("id")){
						logger.info("Appellation Event ID : "+element.getValue().toString());
						//dbConnect.addNetworkStatement(networkId, element.getValue().toString(), "AE", "1", user);
						String networkNodeInfo[] = { element.getValue().toString(),"AE", "1"};
						networkDetailsCache.add(networkNodeInfo);
					}
					// get dspace quadriga URL
					if(element.getName().toString().contains("source_reference")){
						logger.debug("Dspace file : "+element.getValue().toString());
						boolean dspaceFileExists = hasBitStream(element.getValue().toString(), bitStreamList);
						if(dspaceFileExists == false){
							setFileExist(false);
						}
					}
				}

			}
			// Below code reads the top level Relation events 
			if(ce instanceof RelationEventType){
				List<JAXBElement<?>> e2 = ce.getIdOrCreatorOrCreationDate();
				Iterator <JAXBElement<?>> I1 = e2.iterator();
				while(I1.hasNext()){
					JAXBElement<?> element = (JAXBElement<?>) I1.next();
					// get id
					if(element.getName().toString().contains("id")){
						logger.info("Relation Event ID : "+element.getValue().toString());
						//dbConnect.addNetworkStatement(networkId, element.getValue().toString(), "RE", "1", user);
						String networkNodeInfo[] = { element.getValue().toString(),"RE", "1"};
						networkDetailsCache.add(networkNodeInfo);

					}
					// get dspace quadriga URL
					if(element.getName().toString().contains("source_reference")){
						logger.debug("Dspace file : "+element.getValue().toString());
						boolean dspaceFileExists = hasBitStream(element.getValue().toString(), bitStreamList);
						if(dspaceFileExists == false){
							setFileExist(false);
						}
					}
				}
				RelationEventType re = (RelationEventType) (ce);
				try{
					// Go Recursively and check for Relation event within a relation events
					// and add it to DB
					getRelationEventElements(re,networkDetailsCache,bitStreamList);
				}catch(QuadrigaStorageException se){
					logger.error("DB Storage issue",se);
				}

			}
		}
		// Check if it DSpace is present in the XML
		if(!getFileExist()){
			logger.info("Network not uploaded");
			logger.info("Some of the text files in the uploaded network were not present in the workspace");
			return "";
		}
		logger.debug("File Exist paramete value : "+getFileExist());

		// Add network into DB 
		if(updateStatus == "NEW"){
			try{
				networkId=dbConnect.addNetworkRequest(networkName, user,workspaceid);
			}catch(QuadrigaStorageException e1){
				logger.error("DB action error ",e1);
			}
		}
		// Add network statements for networks
		for(String node[] : networkDetailsCache){
			try{
				String rowid = generateUniqueID();
				dbConnect.addNetworkStatement(rowid,networkId,node[0],node[1], node[2], user);
			}catch(QuadrigaStorageException e1){
				logger.error("DB error while adding network statment",e1);
			}
		}
		return networkId;
	}


	/**
	 * Check if we have bit streams in the network XML
	 * @param uri
	 * @param bitStreamList
	 * @return
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
	 * Helper class to get relation event ID
	 * @param re
	 * @return
	 */
	public String getRelationEventId(RelationEventType re){
		List<JAXBElement<?>> e2 = re.getIdOrCreatorOrCreationDate();
		Iterator <JAXBElement<?>> I1 = e2.iterator();
		while(I1.hasNext()){
			JAXBElement<?> element = (JAXBElement<?>) I1.next();
			if(element.getName().toString().contains("id")){
				logger.debug("Relation Event ID : "+element.getValue().toString());
				return element.getValue().toString();
			}
		}
		return "";
	}

	/**
	 * Formats the input XML with nice intendation
	 */
	@Override
	public String prettyFormat(String input, int indent) {
		String result="";
		try{
			Source xmlInput = new StreamSource(new StringReader(input));
			StringWriter stringWriter = new StringWriter();
			StreamResult xmlOutput = new StreamResult(stringWriter);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", indent);
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(xmlInput, xmlOutput);
			result= xmlOutput.getWriter().toString();
		}catch(Exception e){
			logger.error("Error in formating the XML " ,e );
		}
		return result;
	}

	/**
	 * Gets the network element xml for a particular ID
	 */
	@Override
	public ResponseEntity<String> getNodeXmlFromQstore(String id)throws JAXBException{
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
		return response;
	}

	@Override
	public String getNodeXmlStringFromQstore(String id)throws JAXBException{
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
	 * Generate short UUID (13 characters)
	 * 
	 * @return short UUID
	 */
	@Override
	public String shortUUID() {
		UUID uuid = UUID.randomUUID();
		long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
		return Long.toString(l, Character.MAX_RADIX);
	}
	





	/** 
	 * Get all the objects recursively for JSON creation
	 * 
	 */
	@Override
	public List<String[]> getRelationEventElements(RelationEventType re,List<String[]> networkDetailsCache,List<IBitStream> bitStreamList) throws QuadrigaStorageException{
		List <?> ee = re.getRelationCreatorOrRelation();
		Iterator <?> Iee=ee.iterator();
		while(Iee.hasNext()){
			Object o = Iee.next();
			if(o instanceof RelationType){
				RelationType rt = (RelationType) o;
				List<JAXBElement<?>> e3 =rt.getIdOrCreatorOrCreationDate();
				Iterator <JAXBElement<?>> I2 = e3.iterator();
				while(I2.hasNext()){
					JAXBElement<?> element = (JAXBElement<?>) I2.next();

					if(element.getValue().toString().contains("SubjectObjectType")){
						//	Handles the subject part of the relation
						if(element.getName().toString().contains("subject")){
							SubjectObjectType  subject= (SubjectObjectType) element.getValue();

							//	Check for relation event inside subject and add if any
							RelationEventType re1 = subject.getRelationEvent();
							if(re1 == null){
								logger.debug("RE1 is null");
							}else{
								List<JAXBElement<?>> e2 = re1.getIdOrCreatorOrCreationDate();
								Iterator <JAXBElement<?>> I1 = e2.iterator();
								while(I1.hasNext()){
									JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
									if(element1.getName().toString().contains("id")){
										logger.debug("Relation Event ID subject: "+element1.getValue().toString());
										//dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "RE", "0", user);
										String networkNodeInfo[] = { element1.getValue().toString(),"RE", "0"};
										networkDetailsCache.add(networkNodeInfo);
									}
									if(element1.getName().toString().contains("source_reference")){
										logger.debug("Dspace file : "+element1.getValue().toString());
										boolean dspaceFileExists = hasBitStream(element1.getValue().toString(), bitStreamList);
										if(dspaceFileExists == false){
											setFileExist(false);
										}
									}
								}
								networkDetailsCache=getRelationEventElements(re1,networkDetailsCache,bitStreamList);
							}

							//	Check for Appellation event inside subject and add if any
							AppellationEventType ae1 = subject.getAppellationEvent();
							if(ae1 == null){
								logger.debug("AE1 is null");
							}else{
								logger.debug("AE1 found subject");
								List<JAXBElement<?>> e2 = ae1.getIdOrCreatorOrCreationDate();
								Iterator <JAXBElement<?>> I1 = e2.iterator();
								while(I1.hasNext()){
									JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
									if(element1.getName().toString().contains("id")){
										logger.debug("Appellation Event ID : "+element1.getValue().toString());
										//dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "AE", "0", user);
										String networkNodeInfo[] = { element1.getValue().toString(),"AE", "0"};
										networkDetailsCache.add(networkNodeInfo);
									}
									if(element1.getName().toString().contains("source_reference")){
										logger.debug("Dspace file : "+element1.getValue().toString());
										boolean dspaceFileExists = hasBitStream(element1.getValue().toString(), bitStreamList);
										if(dspaceFileExists == false){
											setFileExist(false);
										}
									}
								}
							}
						}else 
							//	Handles the object part of the relation
							if(element.getName().toString().contains("object")){

								SubjectObjectType  object= (SubjectObjectType) element.getValue();

								//	Check for Relation event inside object and add if any
								RelationEventType re1 = object.getRelationEvent();
								if(re1 == null){
									logger.debug("RE1 is null");
								}else{
									List<JAXBElement<?>> e2 = re1.getIdOrCreatorOrCreationDate();
									Iterator <JAXBElement<?>> I1 = e2.iterator();
									while(I1.hasNext()){
										JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
										if(element1.getName().toString().contains("id")){
											logger.debug("Relation Event ID object: "+element1.getValue().toString());
											//dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "RE", "0", user);
											String networkNodeInfo[] = { element1.getValue().toString(),"RE", "0"};
											networkDetailsCache.add(networkNodeInfo);
										}
										if(element1.getName().toString().contains("source_reference")){
											logger.debug("Dspace file : "+element1.getValue().toString());
											boolean dspaceFileExists = hasBitStream(element1.getValue().toString(), bitStreamList);
											if(dspaceFileExists == false){
												setFileExist(false);
											}
										}
									}
									networkDetailsCache=getRelationEventElements(re1,networkDetailsCache,bitStreamList);
								}
								//	Check for Appellation event inside object and add if any
								AppellationEventType ae1 = object.getAppellationEvent();
								if(ae1 == null){
									logger.debug("AE1 is null");
								}else{
									logger.debug("AE1 found object");
									List<JAXBElement<?>> e2 = ae1.getIdOrCreatorOrCreationDate();
									Iterator <JAXBElement<?>> I1 = e2.iterator();
									while(I1.hasNext()){
										JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
										if(element1.getName().toString().contains("id")){
											logger.debug("Appellation Event ID : "+element1.getValue().toString());
											//dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "AE", "0", user);
											String networkNodeInfo[] = { element1.getValue().toString(),"AE", "0"};
											networkDetailsCache.add(networkNodeInfo);
										}
										if(element1.getName().toString().contains("source_reference")){
											logger.debug("Dspace file : "+element1.getValue().toString());
											boolean dspaceFileExists = hasBitStream(element1.getValue().toString(), bitStreamList);
											if(dspaceFileExists == false){
												setFileExist(false);
											}
										}
									}
								}

							}
					}else 
						//	Handles the predicate part of the relation

						if(element.getValue().toString().contains("PredicateType")){

							PredicateType  predicate= (PredicateType) element.getValue();

							//	Check for Appellation event inside predicate and add if any
							AppellationEventType ae1 = predicate.getAppellationEvent();
							if(ae1 == null){
								logger.debug("AE1 is null");
							}else{
								logger.debug("AE1 found object");
								List<JAXBElement<?>> e2 = ae1.getIdOrCreatorOrCreationDate();
								Iterator <JAXBElement<?>> I1 = e2.iterator();
								while(I1.hasNext()){
									JAXBElement<?> element1 = (JAXBElement<?>) I1.next();
									if(element1.getName().toString().contains("id")){
										logger.debug("Appellation Event ID : "+element1.getValue().toString());
										//dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "AE", "0", user);
										String networkNodeInfo[] = { element1.getValue().toString(),"RE", "0"};
										networkDetailsCache.add(networkNodeInfo);
									}
									if(element1.getName().toString().contains("source_reference")){
										logger.debug("Dspace file : "+element1.getValue().toString());
										boolean dspaceFileExists = hasBitStream(element1.getValue().toString(), bitStreamList);
										if(dspaceFileExists == false){
											setFileExist(false);
										}
									}
								}
							}
						}
				}
			}
		}
		return networkDetailsCache;
	}


	/** 
	 * Store XML into QStore
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
	 * Get whole network XML from QStore
	 */
	@Override
	public String getWholeXMLQStore(String XML) throws ParserConfigurationException, SAXException, IOException {
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
		HttpEntity<String> request = new HttpEntity<String>(XML,headers);

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

	/**
	 * Get Network Status from DB
	 */
	@Override
	@Transactional
	public INetwork getNetworkStatus(String networkId, IUser user) throws QuadrigaStorageException{
		INetwork network = null;
		try{
			network = dbConnect.getNetwork(networkId, user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return network;
	}


	/* 
	 * Get Network list belonging to users
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
	 * Get All the nodes of the networks
	 */
	@Override
	@Transactional
	public List<INetworkNodeInfo> getAllNetworkNodes(String networkId)
			throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkNodeList = dbConnect.getNetworkNodes(networkId);

		if(networkNodeList != null)
		{
			Iterator<INetworkNodeInfo> iterator = networkNodeList.iterator();
			while(iterator.hasNext())
			{
				INetworkNodeInfo networkNodeInfo = iterator.next();
				if(networkNodeInfo.getIsArchived() != 0)
					iterator.remove();
			}
		}

		return networkNodeList;
	}

	/* 
	 * Get Project ID associated to workspace id
	 */
	@Override
	@Transactional
	public String getProjectIdForWorkspaceId(String workspaceid) throws QuadrigaStorageException{
		if(workspaceid == null || workspaceid.equals(""))
			return null;
		//Get the project object associated with the workspace
		IProject project = projectManager.getProject(workspaceid);
		if(project != null)
			return project.getInternalid();
		else 
			return null;
	}

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

	/**
	 * Check if the network name already exist
	 */
	@Override
	@Transactional
	public boolean hasNetworkName(String networkName,IUser user) throws QuadrigaStorageException{

		boolean result=true;
		try{
			result=dbConnect.hasNetworkName(networkName,user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return result;
	}

	/**
	 * Get only the Top Nodes of the network
	 */
	@Override
	@Transactional
	public List<INetworkNodeInfo> getNetworkTopNodes(String networkId)throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkNodeList = dbConnect.getNetworkNodes(networkId);
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
	 * Get only the Top Nodes of the network's old version
	 */
	@Override
	@Transactional
	public List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId)throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkNodeList = dbConnect.getNetworkNodes(networkId);
		if(networkNodeList != null)
		{
			Iterator<INetworkNodeInfo> iterator = networkNodeList.iterator();
			while(iterator.hasNext())
			{
				INetworkNodeInfo networkNodeInfo = iterator.next();
				if(networkNodeInfo.getIsTop() != 1 || networkNodeInfo.getIsArchived() != INetworkStatus.ARCHIVE_LEVEL_ONE)
					iterator.remove();
			}
		}

		return networkNodeList;
	}

	/**
	 * Archive network elements
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
	 * Update network name when network is reuploaded
	 * @throws QuadrigaStorageException 
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
	

	@Override
	@Transactional
	public String getNetworkTree(String userName) throws JSONException{
		List<IProject> projectList = null;
		JSONObject core = new JSONObject();
		try{
			projectList = projectManager.getProjectList(userName);
			JSONArray dataArray = new JSONArray();

			for(IProject project : projectList){
				// Each data
				JSONObject data = new JSONObject();
				data.put("id",project.getInternalid());
				data.put("parent","#");
				data.put("text",project.getName());
				dataArray.put(data);
				String wsParent = project.getInternalid();

				List<IWorkSpace> wsList = wsManager.listActiveWorkspace(project.getInternalid(), userName);
				for(IWorkSpace ws : wsList){
					//workspace json
					JSONObject data1 = new JSONObject();
					data1.put("id",ws.getId());
					data1.put("parent",wsParent);
					data1.put("text",ws.getName());
					dataArray.put(data1);
					String networkParent = ws.getId();

					List<INetwork> networkList1 = wsManager.getWorkspaceNetworkList(ws.getId());
					for(INetwork network : networkList1){
						JSONObject data2 = new JSONObject();
						data2.put("id",network.getId());
						data2.put("parent",networkParent);
						String s = "<input type=button	onClick=\"location.href='networks/visualize/"+network.getId()+"'\" value='"+network.getName()+"'>";
						//data2.put("text","<a href=\"networks/visualize/"+network.getId()+"\">"+network.getName()+"</a>  - (Right click and open in new tab or window)");
						data2.put("text", s);
						data2.put("href", "networks/visualize/"+network.getId());
						JSONObject data2href = new JSONObject();
						data2href.put("href", "networks/visualize/"+network.getId());
						//						data2.put("a_attr", data2href);
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




}
