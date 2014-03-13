package edu.asu.spring.quadriga.service.impl.network;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
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
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.ObjectTypeObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.PredicateObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.RelationEventObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.SubjectObject;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

@Service
public class D3NetworkManager {

	@Autowired
	private INetworkManager networkManager;

	@Autowired
	IConceptCollectionManager conceptCollectionManager;

	private static final Logger logger = LoggerFactory
			.getLogger(D3NetworkManager.class);




	public String generateJSonForD3Jquery(List<INetworkNodeInfo> networkTopNodesList){



		return null;
	}

	public String parseNetwork(List<INetworkNodeInfo> networkTopNodesList){
		Iterator <INetworkNodeInfo> topNodeIterator = networkTopNodesList.iterator();
		List<List<Object>> relationEventPredicateMapping = new ArrayList<List<Object>>();
		while(topNodeIterator.hasNext()){
			INetworkNodeInfo networkNodeInfo = topNodeIterator.next();
			logger.debug("Node id "+networkNodeInfo.getId());
			logger.debug("Node statement type "+networkNodeInfo.getStatementType());
			if(networkNodeInfo.getStatementType().equals("RE")){
				try{
					String statementId = networkNodeInfo.getId();
					parseEachStatement(networkNodeInfo.getId(), networkNodeInfo.getStatementType(),statementId,relationEventPredicateMapping);
				}catch(QStoreStorageException e){
					logger.error("QStore retrieve error",e);
				}catch(JAXBException e){
					logger.error("Issue while parsing the JAXB object",e);
				}
			}
		}

		return null;
	}

	public String parseEachStatement(String relationEventId,String statementType, String statementId,List<List<Object>> relationEventPredicateMapping) throws JAXBException, QStoreStorageException{
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
				//printJsonObjectRE(jsonObject.getRelationEventObject());

			}
		}
		return null;
	}


	public ElementEventsType getElementEventTypeFromRelationEvent(String relationEventId) throws JAXBException, QStoreStorageException{
		String xml = networkManager.getNodeXmlStringFromQstore(relationEventId);
		ElementEventsType elementEventType = null;
		if(xml ==null){
			throw new QStoreStorageException("Some issue retriving data from Qstore, Please check the logs related to Qstore");
		}else{

			// Initialize ElementEventsType object for relation event
			elementEventType = unMarshalXmlToElementEventsType(xml);
		}	
		return elementEventType;
	}

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
	
	public PredicateObject parseThroughPredicate(RelationEventType relationEventType, PredicateType predicateType,List<List<Object>> relationEventPredicateMapping){
		//	Predicate has only appellation event, so get appellation event inside the predicate
		AppellationEventType appellationEvent = predicateType.getAppellationEvent();
		PredicateObject predicateObject = null;
		List<TermType> termTypeList= appellationEvent.getTerms(appellationEvent);
		Iterator <TermType> termTypeIterator = termTypeList.iterator();
		while(termTypeIterator.hasNext()){
			TermType tt = termTypeIterator.next();
			AppellationEventObject appellationEventObject = new AppellationEventObject();
			appellationEventObject.setNode(conceptCollectionManager.getConceptLemmaFromConceptId(tt.getTermInterpertation(tt))+"_"+networkManager.shortUUID());
			appellationEventObject.setTermId(tt.getTermID(tt)+"_"+networkManager.shortUUID());
			predicateObject = new PredicateObject();
			predicateObject.setAppellationEventObject(appellationEventObject);

			predicateObject.setRelationEventID(relationEventType.getRelationEventId(relationEventType));
			stackRelationEventPredicateAppellationObject(relationEventType.getRelationEventId(relationEventType),predicateObject.getAppellationEventObject().getNode(),appellationEventObject, relationEventPredicateMapping);
			return predicateObject;
		}
		return predicateObject;
	}
	
	
	/**
	 * Get Appellation associated to relation event
	 * @param relationEventId
	 * @return
	 */
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
				appellationEventObject.setTermId(tt.getTermID(tt)+"_"+networkManager.shortUUID());
				subjectObject.setAppellationEventObject(appellationEventObject);
				logger.debug("subjectType Term : "+tt.getTermInterpertation(tt));
			}
		}
		return subjectObject;
		
	}
	
	
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
				appellationEventObject.setTermId(tt.getTermID(tt)+"_"+networkManager.shortUUID());
				objectTypeObject.setAppellationEventObject(appellationEventObject);
				logger.debug("subjectType Term : "+tt.getTermInterpertation(tt));
			}
		}
		return objectTypeObject;
		
	}
	

}
