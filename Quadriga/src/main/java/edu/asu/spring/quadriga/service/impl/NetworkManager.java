package edu.asu.spring.quadriga.service.impl;

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
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
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
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

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

	boolean fileExist=true;
	
	@Autowired
	@Qualifier("DBConnectionNetworkManagerBean")
	private IDBConnectionNetworkManager dbConnect;

	private List<List<Object>> relationEventPredicateMapping;

	@Override
	public List<List<Object>> getRelationEventPredicateMapping(){
		return this.relationEventPredicateMapping;
	}

	@Override
	public void setRelationEventPredicateMapping( List<List<Object>> relationEventPredicateMapping){
		this.relationEventPredicateMapping=relationEventPredicateMapping;
	}


	public boolean getFileExist(){
		return this.fileExist;
	}
	
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


	/**
	 * Store the element IDs into DB by parsing through the XML and object goes through the event types 
	 * @param response
	 * @param user
	 * @param networkName
	 * @param workspaceid
	 * @author Lohith Dwaraka
	 */
	@Override
	public String receiveNetworkSubmitRequest(JAXBElement<ElementEventsType> response,IUser user,String networkName,String workspaceid,String updateStatus,String networkId){

		setFileExist(true);
		List <String[]> networkDetailsCache = new ArrayList<String[]>();
		IWorkSpace workspace = null;
		try {
			workspace = wsManager.getWorkspaceDetails(workspaceid, user.getUserName());
		} catch (QuadrigaStorageException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		} catch (QuadrigaAccessException e3) {
			// TODO Auto-generated catch block
			e3.printStackTrace();
		}
		
		
		
		List<IBitStream> bitStreamList = workspace.getBitstreams();
		logger.info("list of bitstream");
		Iterator <IBitStream> I2 = bitStreamList.iterator();
		while(I2.hasNext()){
			logger.info(I2.next().getId()+"");
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
						//dbConnect.addNetworkStatement(networkId, element.getValue().toString(), "AE", "1", user);
						String networkNodeInfo[] = { element.getValue().toString(),"AE", "1"};
						networkDetailsCache.add(networkNodeInfo);
					}
					if(element.getName().toString().contains("source_reference")){
						logger.debug("Dspace file : "+element.getValue().toString());
						boolean dspaceFileExists = hasBitStream(element.getValue().toString(), bitStreamList);
						if(dspaceFileExists == false){
							setFileExist(false);
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
						//dbConnect.addNetworkStatement(networkId, element.getValue().toString(), "RE", "1", user);
						String networkNodeInfo[] = { element.getValue().toString(),"RE", "1"};
						networkDetailsCache.add(networkNodeInfo);

					}
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
					getRelationEventElements(re,networkDetailsCache,bitStreamList);
				}catch(QuadrigaStorageException se){
					logger.error("DB Storage issue",se);
				}

			}
		}
		
		if(!getFileExist()){
			logger.info("Network not uploaded");
			logger.info("Some of the text files in the uploaded network were not present in the workspace");
			return "";
		}
		logger.debug("File Exist paramete value : "+getFileExist());
		
		if(updateStatus == "NEW"){
			try{
				networkId=dbConnect.addNetworkRequest(networkName, user,workspaceid);
			}catch(QuadrigaStorageException e1){
				logger.error("DB action error ",e1);
			}
		}
		
		for(String node[] : networkDetailsCache){
			try{
				dbConnect.addNetworkStatement(networkId,node[0],node[1], node[2], user);
			}catch(QuadrigaStorageException e1){
				logger.error("DB error while adding network statment",e1);
			}
		}
		return networkId;
	}


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

	/* 
	 * Formats the XML 
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
			logger.debug("URL : "+getQStoreGetURL()+id);
			response = restTemplate.exchange(getQStoreGetURL()+id,
					HttpMethod.GET,
					new HttpEntity<String[]>(headers),
					String.class);
		}catch(Exception e){
			e.getMessage();
		}
		return response;
	}


	/**
	 * Generate JSON string of the network to JIT jquery
	 */
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
			logger.debug("Respose bytes : "+responseText);
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
							String termId = tt.getTermID(tt)+"_"+shortUUID();
							//String node = tt.getTermInterpertation(tt);
							logger.debug(tt.getTermInterpertation(tt));
							this.jsonString .append("{\"adjacencies\": [],\"data\": {\"$color\": \"#85BB65\",\"$type\": \"square\",\"$dim\": 11},\"id\": \""+termId+"_"+shortUUID()+"\",\"name\": \""+node+"\"},");
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

	/**
	 * Generate short UUID (13 characters)
	 * 
	 * @return short UUID
	 */
	public String shortUUID() {
		UUID uuid = UUID.randomUUID();
		long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
		return Long.toString(l, Character.MAX_RADIX);
	}
	/**
	 * Helper funtions to create a JSon object
	 * @param relationEventObject
	 */
	public void printJsonObjectRE(RelationEventObject relationEventObject){
		PredicateObject predicateObject = relationEventObject.getPredicateObject();
		AppellationEventObject appellationEventObject = predicateObject.getAppellationEventObject();
		NodeObject nodeObject = new NodeObject();
		nodeObject.setRelationEventId(predicateObject.getRelationEventID());

		nodeObject.setPredicate(appellationEventObject.getNode());
		nodeObject.setPredicateId(appellationEventObject.getTermId());
		logger.debug("Predicate : "+appellationEventObject.getNode() );
		SubjectObject subjectObject = relationEventObject.getSubjectObject();
		ObjectTypeObject objectTypeObject = relationEventObject.getObjectTypeObject();
		if(subjectObject.getIsRelationEventObject()){
			nodeObject.setSubject(subjectObject.getSubjectRelationPredictionAppellation(subjectObject));
			nodeObject.setSubjectId(subjectObject.getSubjectRelationPredictionAppellationTermId(subjectObject));
			logger.debug("Subject Predicate node : "+subjectObject.getSubjectRelationPredictionAppellation(subjectObject));
			if(objectTypeObject.getIsRelationEventObject()){
				nodeObject.setObject(objectTypeObject.getObjectRelationPredictionAppellation(objectTypeObject));
				nodeObject.setObjectId(objectTypeObject.getObjectRelationPredictionAppellationTermId(objectTypeObject));
				updateJsonStringForRENode(nodeObject);
				logger.debug("Object Predicate node : "+objectTypeObject.getObjectRelationPredictionAppellation(objectTypeObject));
			}else{

				AppellationEventObject appellationEventObject1 = objectTypeObject.getAppellationEventObject();
				nodeObject.setObject(appellationEventObject1.getNode());
				nodeObject.setObjectId(appellationEventObject1.getTermId());
				updateJsonStringForRENode(nodeObject);
				logger.debug("Object Predicate : "+appellationEventObject1.getNode() );
			}
			printJsonObjectRE(subjectObject.getRelationEventObject());
		}else{

			AppellationEventObject appellationEventObject1 = subjectObject.getAppellationEventObject();
			nodeObject.setSubject(appellationEventObject1.getNode());
			nodeObject.setSubjectId(appellationEventObject1.getTermId());
			logger.debug("Subject Predicate : "+appellationEventObject1.getNode() );
		}
		if(objectTypeObject.getIsRelationEventObject()){
			printJsonObjectRE(objectTypeObject.getRelationEventObject());
		}else{
			AppellationEventObject appellationEventObject1 = objectTypeObject.getAppellationEventObject();
			nodeObject.setObject(appellationEventObject1.getNode());
			nodeObject.setObjectId(appellationEventObject1.getTermId());
			updateJsonStringForRENode(nodeObject);
			logger.debug("Object Predicate : "+appellationEventObject1.getNode() );
		}
	}

	/**
	 * Update JSON with every new triplet node
	 * @param nodeObject
	 */
	public void updateJsonStringForRENode(NodeObject nodeObject){
		String predicateNameId = nodeObject.getPredicate();

		String temp=checkRelationEventRepeatation(nodeObject.getRelationEventId(),nodeObject.getPredicate());
		String predicateName = predicateNameId.substring(0,predicateNameId.lastIndexOf('_'));
		if(!(temp.equals(""))){
			predicateNameId = temp;
		}

		this.jsonString.append("{\"adjacencies\":[");
		this.jsonString.append("{");
		this.jsonString.append("\"nodeTo\": \""+nodeObject.getSubject()+"\",");
		this.jsonString.append("\"nodeFrom\": \""+predicateName+"\",\"dim\" : \"15\", ");
		this.jsonString.append("\"data\": {\"$color\": \"#FFFFFF\",\"$labelid\": \"arrow1\",\"$labeltext\": \"Subject\"}");
		this.jsonString.append("},");

		this.jsonString.append("{");
		this.jsonString.append("\"nodeTo\": \""+nodeObject.getObject()+"\",");
		this.jsonString.append("\"nodeFrom\": \""+predicateName+"\",");
		this.jsonString.append("\"data\": {\"$color\": \"#FFFFFF\",\"$labelid\": \"arrow1\",\"dim\":\"8\",\"$labeltext\": \"Object\"}");
		this.jsonString.append("}],");

		this.jsonString.append("\"data\": {");
		this.jsonString.append("\"$color\": \"#CC0066\",");
		this.jsonString.append("\"$type\": \"circle\",");
		this.jsonString.append("\"$dim\": 13");
		this.jsonString.append("},");
		this.jsonString.append("\"id\": \""+predicateNameId+"\",");
		this.jsonString.append("\"type\": \"Predicate\",");
		this.jsonString.append("\"name\": \""+predicateName+"\"");
		this.jsonString.append("},");
	}


	public String checkRelationEventRepeatation(String relationEventId,String predicateName){
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
						return predicateNameLocal;
					}
				}
			}
		}
		return "";

	}

	public String stackRelationEventPredicateAppellationObject(String relationEventId,String predicateName,AppellationEventObject appellationEventObject){
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

		return "";

	}


	public AppellationEventObject checkRelationEventStack(String relationEventId){

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
				appellationEventObject.setNode(conceptCollectionManager.getCocneptLemmaFromConceptId(tt.getTermInterpertation(tt))+"_"+shortUUID());
				appellationEventObject.setTermId(tt.getTermID(tt)+"_"+shortUUID());
				PredicateObject predicateObject = new PredicateObject();
				predicateObject.setAppellationEventObject(appellationEventObject);

				predicateObject.setRelationEventID(getRelationEventId(re));
				stackRelationEventPredicateAppellationObject(getRelationEventId(re),predicateObject.getAppellationEventObject().getNode(),appellationEventObject);
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
			AppellationEventObject temp = checkRelationEventStack(getRelationEventId(re1));
			/*
			 * I am trying to fool subject as Appellation event
			 * when we find a existing relation event been referred here
			 * I will give appellation event with predicate of referred relation event
			 */
			if(!(temp == null)){
				subjectObject.setIsRelationEventObject(false);
				subjectObject.setAppellationEventObject(temp);
			}else{
				logger.debug("Subject : Its RE, now Recursive move");
				subjectObject.setIsRelationEventObject(true);
				RelationEventObject relationEventObject1   = new RelationEventObject();
				checkRelationEventStack(getRelationEventId(re1));
				relationEventObject1=getAllObjectFromRelationEvent(re1,relationEventObject1);
				subjectObject.setRelationEventObject(relationEventObject1);
			}
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
				appellationEventObject.setTermId(tt.getTermID(tt)+"_"+shortUUID());
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
			AppellationEventObject temp = checkRelationEventStack(getRelationEventId(re1));
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
				RelationEventObject relationEventObject1   = new RelationEventObject();
				logger.debug("Object : Its RE, now Recursive move");
				relationEventObject1=getAllObjectFromRelationEvent(re2,relationEventObject1);
				objectTypeObject.setRelationEventObject(relationEventObject1);
			}
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
				appellationEventObject.setTermId(tt.getTermID(tt)+"_"+shortUUID());
				objectTypeObject.setAppellationEventObject(appellationEventObject);
				logger.debug("objectType Term : "+tt.getTermInterpertation(tt));
			}
		}
		relationEventObject.setObjectTypeObject(objectTypeObject);
		return relationEventObject;
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

	/** 
	 * Get whole network XML from QStore
	 */
	@Override
	public String getWholeXMLQStore(String XML) throws ParserConfigurationException, SAXException, IOException {
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
	public INetwork getNetworkStatus(String networkId, IUser user) throws QuadrigaStorageException{
		INetwork network = null;
		try{
			network = dbConnect.getNetworkStatus(networkId, user);
		}catch(QuadrigaStorageException e){
			logger.error("Something went wrong in DB",e);
		}
		return network;
	}


	/* 
	 * Get Network list belonging to users
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

	/**
	 * Get All the nodes of the networks
	 */
	@Override
	public List<INetworkNodeInfo> getAllNetworkNodes(String networkId)
			throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkTopNodeList = dbConnect.getAllNetworkNodes(networkId);

		return networkTopNodeList;
	}

	/* 
	 * Get Project ID associated to workspace id
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

	/**
	 * Check if the network name already exist
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

	/**
	 * Get only the Top Nodes of the network
	 */
	@Override
	public List<INetworkNodeInfo> getNetworkTopNodes(String networkId)throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkTopNodeList = dbConnect.getNetworkTopNodes(networkId);

		return networkTopNodeList;
	}
	
	
	/**
	 * Get network detail object
	 * @param networkId
	 * @return
	 */
	@Override
	public INetwork getNetworkDetails(String networkId){
		INetwork network =null;
		try{
		network = dbConnect.getNetworkDetails(networkId);
		}catch(QuadrigaStorageException e){
			logger.error("DB error ",e);
		}
		return network;
	}


	/**
	 * Get only the Top Nodes of the network's old version
	 */
	@Override
	public List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId)throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkTopNodeList = dbConnect.getNetworkOldVersionTopNodes(networkId);

		return networkTopNodeList;
	}
	/**
	 * Archive network Statements
	 */
	@Override
	public String archiveNetworkStatement(String networkId,String id) throws QuadrigaStorageException{
		String msg = dbConnect.archiveNetworkStatement(networkId, id);
		return msg;
	}

	/**
	 * Archive network elements
	 */
	@Override
	public void archiveNetwork(String networkId) throws QuadrigaStorageException{

		try{
			dbConnect.archiveNetwork(networkId);
		}catch(QuadrigaStorageException e){
			throw new QuadrigaStorageException();
		}

	}


}
