package edu.asu.spring.quadriga.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.INetworkFactory;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.implementation.networks.AppellationEventType;
import edu.asu.spring.quadriga.domain.implementation.networks.CreationEvent;
import edu.asu.spring.quadriga.domain.implementation.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.implementation.networks.PredicateType;
import edu.asu.spring.quadriga.domain.implementation.networks.RelationEventType;
import edu.asu.spring.quadriga.domain.implementation.networks.RelationType;
import edu.asu.spring.quadriga.domain.implementation.networks.SubjectObjectType;
import edu.asu.spring.quadriga.domain.implementation.networks.TermType;
import edu.asu.spring.quadriga.domain.implementation.networks.jsonobject.AppellationEventObject;
import edu.asu.spring.quadriga.domain.implementation.networks.jsonobject.JsonObject;
import edu.asu.spring.quadriga.domain.implementation.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.domain.implementation.networks.jsonobject.ObjectTypeObject;
import edu.asu.spring.quadriga.domain.implementation.networks.jsonobject.PredicateObject;
import edu.asu.spring.quadriga.domain.implementation.networks.jsonobject.RelationEventObject;
import edu.asu.spring.quadriga.domain.implementation.networks.jsonobject.SubjectObject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.INetworkManager;

/**
 * This class acts as a Network manager which handles the networks object
 * 
 * @author : Lohith Dwaraka
 */
@Service
public class NetworkManager implements INetworkManager {

	private static final Logger logger = LoggerFactory
			.getLogger(NetworkManager.class);

	@Autowired
	@Qualifier("qStoreURL")
	private String qStoreURL;

	@Autowired
	IConceptCollectionManager conceptCollectionManager;
	
	public StringBuffer jsonString= new StringBuffer("");

	@Autowired
	@Qualifier("qStoreURL_Add")
	private String qStoreURL_Add;

	@Autowired
	@Qualifier("restTemplate")
	RestTemplate restTemplate;

	@Autowired
	@Qualifier("qStoreURL_Get")
	private String qStoreURL_Get;

	@Autowired
	@Qualifier("jaxbMarshaller")
	Jaxb2Marshaller jaxbMarshaller;

	@Autowired
	private INetworkFactory networkFactory;

	@Autowired
	@Qualifier("DBConnectionNetworkManagerBean")
	private IDBConnectionNetworkManager dbConnect;

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.INetworkManager#getQStoreAddURL()
	 */
	@Override
	public String getQStoreAddURL() {
		return qStoreURL+qStoreURL_Add;
	}

	@Override
	public String getQStoreGetURL() {
		return qStoreURL+qStoreURL_Get;
	}


	/**
	 * Parses through the XML and object goes through the event types 
	 * @param response
	 * @param user
	 * @param networkName
	 * @param workspaceid
	 * @author Lohith Dwaraka
	 */
	@Override
	public String receiveNetworkSubmitRequest(JAXBElement<ElementEventsType> response,IUser user,String networkName,String workspaceid){
		String networkId="";
		try{
			networkId=dbConnect.addNetworkRequest(networkName, user,workspaceid);
		}catch(QuadrigaStorageException e){
			logger.error("DB action error ",e);
		}

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
					if(element.getName().toString().contains("id")){
						logger.info("Appellation Event ID : "+element.getValue().toString());
						try{
							dbConnect.addNetworkStatement(networkId, element.getValue().toString(), "AE", "1", user);
						}catch(QuadrigaStorageException se){
							logger.error("DB Storage issue",se);
						}
					}
				}

			}
			if(ce instanceof RelationEventType){
				List<JAXBElement<?>> e2 = ce.getIdOrCreatorOrCreationDate();
				Iterator <JAXBElement<?>> I1 = e2.iterator();
				while(I1.hasNext()){
					JAXBElement<?> element = (JAXBElement<?>) I1.next();
					if(element.getName().toString().contains("id")){
						logger.info("Relation Event ID : "+element.getValue().toString());
						try{
							dbConnect.addNetworkStatement(networkId, element.getValue().toString(), "RE", "1", user);
						}catch(QuadrigaStorageException se){
							logger.error("DB Storage issue",se);
						}
					}
				}
				RelationEventType re = (RelationEventType) (ce);
				try{
					getRelationEventElements(re,networkId,user);
				}catch(QuadrigaStorageException se){
					logger.error("DB Storage issue",se);
				}

			}
		}
		return networkId;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.INetworkManager#prettyFormat(java.lang.String, int)
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

	@Override
	public ResponseEntity<String> getNodeXmlFromQstore(String id)throws JAXBException{
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_XML);
		messageConverters.add(new StringHttpMessageConverter());
		org.springframework.oxm.Marshaller marshaler = jaxbMarshaller;
		org.springframework.oxm.Unmarshaller unmarshaler = jaxbMarshaller;
		messageConverters.add(new MarshallingHttpMessageConverter(marshaler,unmarshaler));

		restTemplate.setMessageConverters(messageConverters);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setAccept(mediaTypes);
		ResponseEntity<String> response = null;
		try{
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
	public String generateJsontoJQuery(String id,String statementType) throws JAXBException, QuadrigaStorageException{
		JsonObject jsonObject = new JsonObject();
		this.jsonString.delete(0, this.jsonString.length());
		
		ResponseEntity<String> response = getNodeXmlFromQstore(id);
		
		if(response ==null){
			throw new QuadrigaStorageException("Some issue retriving data from Qstore, Please check the logs related to Qstore");
		}else{
			String responseText = response.getBody().toString();
			JAXBContext context = JAXBContext.newInstance(ElementEventsType.class);
			Unmarshaller unmarshaller1 = context.createUnmarshaller();
			unmarshaller1.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			InputStream is = new ByteArrayInputStream(responseText.getBytes());
			JAXBElement<ElementEventsType> response1 =  unmarshaller1.unmarshal(new StreamSource(is), ElementEventsType.class);
			//		logger.info("Respose bytes : "+responseText);
			try{
				ElementEventsType e = response1.getValue();
				List<CreationEvent> c =e.getRelationEventOrAppellationEvent();
				Iterator <CreationEvent> I= c.iterator();
				while(I.hasNext()){
					CreationEvent ce = I.next();
					if(ce instanceof AppellationEventType)
					{
						// Trying to get a list of terms in the appellation event type object
						AppellationEventType aet = (AppellationEventType) ce;

						List<TermType> termTypeList= aet.getTerms(aet);
						Iterator <TermType> I2 = termTypeList.iterator();
						while(I2.hasNext()){
							TermType tt = I2.next();
							String node = conceptCollectionManager.getCocneptLemmaFromConceptId(tt.getTermInterpertation(tt));
							//String node = tt.getTermInterpertation(tt);
							logger.debug(tt.getTermInterpertation(tt));
							this.jsonString .append("{\"adjacencies\": [],\"data\": {\"$color\": \"#EE6363\",\"$type\": \"circle\",\"$dim\": 11},\"id\": \""+node+"\",\"name\": \""+node+"\"},");
						}
					}
					if(ce instanceof RelationEventType){
						// Trying to get a list of objects in the relations event type object
						// First get PredicateType
						// Then go recursively to subject and object
						RelationEventType re = (RelationEventType) ce;
						this.jsonString.delete(0, this.jsonString.length());
						jsonObject.setIsRelationEventObject(true);
						RelationEventObject relationEventObject = new RelationEventObject();
						jsonObject.setRelationEventObject(relationEventObject);
						jsonObject.setRelationEventObject(getAllObjectFromRelationEvent(re,jsonObject.getRelationEventObject()));
						printJsonObjectRE(jsonObject.getRelationEventObject());

					}
				}

			}catch(Exception e){
				logger.error("",e);
			}
		}
		return this.jsonString.toString();
	}

	public void printJsonObjectRE(RelationEventObject relationEventObject){
		PredicateObject predicateObject = relationEventObject.getPredicateObject();
		AppellationEventObject appellationEventObject = predicateObject.getAppellationEventObject();
		NodeObject nodeObject = new NodeObject();
		nodeObject.setPredicate(appellationEventObject.getNode());
		logger.debug("Predicate : "+appellationEventObject.getNode() );
		SubjectObject subjectObject = relationEventObject.getSubjectObject();
		ObjectTypeObject objectTypeObject = relationEventObject.getObjectTypeObject();
		if(subjectObject.getIsRelationEventObject()){
			nodeObject.setSubject(subjectObject.getSubjectRelationPredictionAppellation(subjectObject));
			logger.debug("Subject Predicate node : "+subjectObject.getSubjectRelationPredictionAppellation(subjectObject));
			if(objectTypeObject.getIsRelationEventObject()){
				nodeObject.setObject(objectTypeObject.getObjectRelationPredictionAppellation(objectTypeObject));
				updateJsonStringForRENode(nodeObject);
				logger.debug("Object Predicate node : "+objectTypeObject.getObjectRelationPredictionAppellation(objectTypeObject));
			}else{

				AppellationEventObject appellationEventObject1 = objectTypeObject.getAppellationEventObject();
				nodeObject.setObject(appellationEventObject1.getNode());
				updateJsonStringForRENode(nodeObject);
				logger.debug("Object Predicate : "+appellationEventObject1.getNode() );
			}
			printJsonObjectRE(subjectObject.getRelationEventObject());
		}else{

			AppellationEventObject appellationEventObject1 = subjectObject.getAppellationEventObject();
			nodeObject.setSubject(appellationEventObject1.getNode());
			logger.debug("Subject Predicate : "+appellationEventObject1.getNode() );
		}
		if(objectTypeObject.getIsRelationEventObject()){
			printJsonObjectRE(objectTypeObject.getRelationEventObject());
		}else{
			AppellationEventObject appellationEventObject1 = objectTypeObject.getAppellationEventObject();
			nodeObject.setObject(appellationEventObject1.getNode());
			updateJsonStringForRENode(nodeObject);
			logger.debug("Object Predicate : "+appellationEventObject1.getNode() );
		}
	}

	public void updateJsonStringForRENode(NodeObject nodeObject){
		this.jsonString.append("{\"adjacencies\":[");
		this.jsonString.append("{");
		this.jsonString.append("\"nodeTo\": \""+nodeObject.getSubject()+"\",");
		this.jsonString.append("\"nodeFrom\": \""+nodeObject.getPredicate()+"\",");
		this.jsonString.append("\"data\": {\"$color\": \"#FFFFFF\"}");
		this.jsonString.append("},");

		this.jsonString.append("{");
		this.jsonString.append("\"nodeTo\": \""+nodeObject.getObject()+"\",");
		this.jsonString.append("\"nodeFrom\": \""+nodeObject.getPredicate()+"\",");
		this.jsonString.append("\"data\": {\"$color\": \"#FFFFFF\"}");
		this.jsonString.append("}],");

		this.jsonString.append("\"data\": {");
		this.jsonString.append("\"$color\": \"#EE6363\",");
		this.jsonString.append("\"$type\": \"circle\",");
		this.jsonString.append("\"$dim\": 11");
		this.jsonString.append("},");
		this.jsonString.append("\"id\": \""+nodeObject.getPredicate()+"\",");
		this.jsonString.append("\"name\": \""+nodeObject.getPredicate()+"\"");
		this.jsonString.append("},");
	}



	/**
	 * Get all the terms recursively from the relation event
	 * 
	 * @param re : RelationEventType
	 */
	public RelationEventObject getAllObjectFromRelationEvent(RelationEventType re,RelationEventObject relationEventObject){

		RelationType relationType = re.getRelation(re);
		PredicateType predicateType = relationType.getPredicateType(relationType);
		//		Check for Appellation event inside subject and add if any
		AppellationEventType ae = predicateType.getAppellationEvent();
		{
			List<TermType> termTypeList= ae.getTerms(ae);
			Iterator <TermType> I2 = termTypeList.iterator();
			while(I2.hasNext()){
				TermType tt = I2.next();
				AppellationEventObject appellationEventObject = new AppellationEventObject();
				appellationEventObject.setNode(conceptCollectionManager.getCocneptLemmaFromConceptId(tt.getTermInterpertation(tt)));
				PredicateObject predicateObject = new PredicateObject();
				predicateObject.setAppellationEventObject(appellationEventObject);
				relationEventObject.setPredicateObject(predicateObject);
				logger.debug("Predicate Term : "+ tt.getTermInterpertation(tt));
			}
		}

		SubjectObjectType subjectType = relationType.getSubjectType(relationType);
		//		Check for relation event inside subject and add if any
		logger.debug("Came to Subject, Now checking RE or AE");
		RelationEventType re1 = subjectType.getRelationEvent();

		SubjectObject subjectObject = new SubjectObject();

		if(re1 == null){
			subjectObject.setIsRelationEventObject(false);
			logger.debug("Subject : RE1 is null");
		}else{
			logger.debug("Subject : Its RE, now Recursive move");
			subjectObject.setIsRelationEventObject(true);
			RelationEventObject relationEventObject1   = new RelationEventObject();

			relationEventObject1=getAllObjectFromRelationEvent(re1,relationEventObject1);
			subjectObject.setRelationEventObject(relationEventObject1);
		}
		//	Check for Appellation event inside subject and add if any
		AppellationEventType ae1 = subjectType.getAppellationEvent();
		if(ae1 == null){
			logger.debug("Subject : AE1 is null");
		}else{


			logger.debug("Subject : Its AE , now printing the terms");
			List<TermType> termTypeList= ae1.getTerms(ae1);
			Iterator <TermType> I2 = termTypeList.iterator();
			while(I2.hasNext()){
				TermType tt = I2.next();
				AppellationEventObject appellationEventObject = new AppellationEventObject();
				appellationEventObject.setNode(conceptCollectionManager.getCocneptLemmaFromConceptId(tt.getTermInterpertation(tt)));
				subjectObject.setAppellationEventObject(appellationEventObject);
				logger.debug("subjectType Term : "+tt.getTermInterpertation(tt));
			}
		}
		relationEventObject.setSubjectObject(subjectObject);
		SubjectObjectType objectType = relationType.getObjectType(relationType);
		ObjectTypeObject objectTypeObject = new ObjectTypeObject();
		//		Check for relation event inside subject and add if any
		logger.debug("Came to Object, Now checking RE or AE");
		RelationEventType re2 = objectType.getRelationEvent();
		if(re2 == null){
			objectTypeObject.setIsRelationEventObject(false);
			logger.debug("Object : RE2 is null");
		}else{
			objectTypeObject.setIsRelationEventObject(true);
			RelationEventObject relationEventObject1   = new RelationEventObject();
			logger.debug("Object : Its RE, now Recursive move");
			relationEventObject1=getAllObjectFromRelationEvent(re2,relationEventObject1);
			objectTypeObject.setRelationEventObject(relationEventObject1);
		}
		//	Check for Appellation event inside subject and add if any
		AppellationEventType ae2 = objectType.getAppellationEvent();
		if(ae2 == null){
			logger.debug("Object : AE2 is null");
		}else{
			logger.debug("Object : Its AE , now printing the terms");
			List<TermType> termTypeList= ae2.getTerms(ae2);
			Iterator <TermType> I2 = termTypeList.iterator();
			while(I2.hasNext()){
				TermType tt = I2.next();
				AppellationEventObject appellationEventObject = new AppellationEventObject();
				appellationEventObject.setNode(conceptCollectionManager.getCocneptLemmaFromConceptId(tt.getTermInterpertation(tt)));
				objectTypeObject.setAppellationEventObject(appellationEventObject);
				logger.debug("objectType Term : "+tt.getTermInterpertation(tt));
			}
		}
		relationEventObject.setObjectTypeObject(objectTypeObject);
		return relationEventObject;
	}



	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.INetworkManager#getRelationEventElements(edu.asu.spring.quadriga.domain.implementation.networks.RelationEventType, java.lang.String, edu.asu.spring.quadriga.domain.IUser)
	 */
	@Override
	public void getRelationEventElements(RelationEventType re,String networkId,IUser user) throws QuadrigaStorageException{
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
										try{
											dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "RE", "0", user);
										}catch(QuadrigaStorageException se){
											logger.error("DB Storage issue",se);
										}
									}
								}
								getRelationEventElements(re1,networkId,user);
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
										try{
											dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "AE", "0", user);
										}catch(QuadrigaStorageException se){
											logger.error("DB Storage issue",se);
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
											try{
												dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "RE", "0", user);
											}catch(QuadrigaStorageException se){
												logger.error("DB Storage issue",se);
											}
										}
									}
									getRelationEventElements(re1,networkId,user);
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
											try{
												dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "AE", "0", user);
											}catch(QuadrigaStorageException se){
												logger.error("DB Storage issue",se);
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
										try{
											dbConnect.addNetworkStatement(networkId, element1.getValue().toString(), "AE", "0", user);
										}catch(QuadrigaStorageException se){
											logger.error("DB Storage issue",se);
										}
									}
								}
							}
						}
				}
			}
		}
	}


	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.INetworkManager#storeXMLQStore(java.lang.String)
	 */
	@Override
	public String storeXMLQStore(String XML) throws ParserConfigurationException, SAXException, IOException {
		String res="";
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		RestTemplate restTemplate = new RestTemplate();
		List<MediaType> mediaTypes = new ArrayList<MediaType>();
		mediaTypes.add(MediaType.APPLICATION_XML);
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new StringHttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_XML);
		headers.setAccept(mediaTypes);
		HttpEntity request = new HttpEntity(XML,headers);

		try{
			res = restTemplate.postForObject(getQStoreAddURL(), request,String.class);
		}catch(Exception e){
			logger.error("QStore not accepting the xml, please check with the server logs.",e);
			//res = e.getMessage();
			return res;
		}
		return res;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.INetworkManager#getNetworkStatus(java.lang.String, edu.asu.spring.quadriga.domain.IUser)
	 */
	@Override
	public INetwork getNetworkStatus(String networkId, IUser user) throws QuadrigaStorageException{
		INetwork network = null;
		try{
			network = dbConnect.getNetworkStatus(networkId, user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return network;
	}


	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.INetworkManager#getNetworkList(edu.asu.spring.quadriga.domain.IUser)
	 */
	@Override
	public List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException{
		List<INetwork> networkList = new ArrayList<INetwork>();

		try{
			networkList=dbConnect.getNetworkList(user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return networkList;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.INetworkManager#getProjectIdForWorkspaceId(java.lang.String)
	 */
	@Override
	public String getProjectIdForWorkspaceId(String workspaceid) throws QuadrigaStorageException{
		String projectid="";
		try{
			projectid=dbConnect.getProjectIdForWorkspaceId(workspaceid);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return projectid;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.service.impl.INetworkManager#hasNetworkName(java.lang.String, edu.asu.spring.quadriga.domain.IUser)
	 */
	@Override
	public boolean hasNetworkName(String networkName,IUser user) throws QuadrigaStorageException{

		boolean result=true;
		try{
			result=dbConnect.hasNetworkName(networkName,user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return result;
	}

	@Override
	public List<INetworkNodeInfo> getNetworkTopNodes(String networkId)throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkTopNodeList = dbConnect.getNetworkTopNodes(networkId);

		return networkTopNodeList;
	}
}
