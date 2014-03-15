package edu.asu.spring.quadriga.service.network;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.impl.networks.AppellationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.impl.networks.PredicateType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.domain.impl.networks.SubjectObjectType;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.AppellationEventObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.NodeObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.ObjectTypeObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.PredicateObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.RelationEventObject;
import edu.asu.spring.quadriga.domain.impl.networks.jsonobject.SubjectObject;
import edu.asu.spring.quadriga.domain.impl.workspacexml.Workspace;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;

public interface INetworkManager {

	// Constants to request for type of JQuery JSon 
	public static String D3JQUERY = "D3JQUERY";
	public static String JITJQUERY= "JITJQUERY";
	
	// Constants to mention type of event in the QStore XML
	public static String RELATIONEVENT = "RE";
	public static String APPELLATIONEVENT = "AE";

	/**
	 * Getter for QStore network adding URL. 
	 * This value would be stored in the rest-service.xml in the spring configuration location.
	 * We use {@link Autowired} to get {@link String} with ID "qStoreURL" and "qStoreURL_Add" from rest-service.xml
	 * @return			URL with type {@link String} 
	 */
	public abstract String getQStoreAddURL();

	/**
	 * Getter for URL to get network XML from QStore. 
	 * This value would be stored in the rest-service.xml in the spring configuration location.
	 * We use {@link Autowired} to get {@link String} with ID "qStoreURL" and "qStoreURL_Get" from rest-service.xml
	 * @return			URL with type {@link String} 
	 */
	public abstract String getQStoreGetURL();
	
	/**
	 * Getter for URL to get whole network XML from QStore by sending a list of {@link RelationEventType} IDs and {@link AppellationEventType} IDs. 
	 * This value would be stored in the rest-service.xml in the spring configuration location.
	 * We use {@link Autowired} to get {@link String} with ID "qStoreURL" and "qStoreURL_Post" from rest-service.xml
	 * @return			URL with type {@link String} 
	 */
	public abstract String getQStoreGetPOSTURL();

	
	public abstract String receiveNetworkSubmitRequest(
			JAXBElement<ElementEventsType> response, IUser user,
			String networkName, String workspaceid,String updateStatus,String networkId);

	/**
	 * Stores XML from Vogon into Q-Store
	 * @author Lohith Dwaraka
	 * @param XML
	 * @return
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 */
	public abstract String storeXMLQStore(String XML)
			throws ParserConfigurationException, SAXException, IOException;

	public abstract List<String[]> getRelationEventElements(RelationEventType re,
			List<String[]> networkNodeCache,List<IBitStream> bitStreamList) throws QuadrigaStorageException;

	/**
	 * Get {@link INetwork} object for a given networkId and {@link IUser}.
	 * We could access workspace, project details, network status and other information through this method  
	 * param networkID					{@link INetwork} ID of type {@link String} parameter
	 * @param user						{@link IUser} object 
	 * @return							{@link INetwork} object is returned
	 * @throws QuadrigaStorageException 	Database storage exception thrown
	 */
	public abstract INetwork getNetwork(String networkId, IUser user)
			throws QuadrigaStorageException;

	/**
	 * Get a {@link List} of {@link INetwork} objects of a given {@link IUser}.
	 * We could use this method incase we need to show {@link List} of {@link INetwork} in GUI or any other manipulation.
	 * @param user						{@link IUser} object 
	 * @return							{@link List} of {@link INetwork} objects
	 * @throws QuadrigaStorageException		Database storage exception thrown
	 */
	public abstract List<INetwork> getNetworkList(IUser user)
			throws QuadrigaStorageException;

	/**
	 * Get {@link IProject} ID of type {@link String} for {@link IWorkspace} ID of type {@link String}.
	 * As we have {@link INetwork} attached to a {@link Workspace}. We need {@link IProject} ID to display to which project the network belongs to. 
	 * @param workspaceid				{@link IWorkSpace} ID of type {@link String}
	 * @return							{@link IProject} ID of type {@link String}
	 * @throws QuadrigaStorageException		Database storage exception thrown
	 */
	public abstract String getProjectIdForWorkspaceId(String workspaceid)
			throws QuadrigaStorageException;


	/**
	 * Get only the Top Nodes of the {@link INetworkNodeInfo}. In a XML received from QStore we could have a list of Appellation Events and Relation Events.
	 * Relation Events would recursively have Relation Events as a part of Subject and Object of the part of relation.
	 * We would store only the top Relation Event ID, as we have facilities from QStore to fetch the XML of Relation Events.
	 * We have isTop field in network statement table to mention whether a statement is top or not. 
	 * @param networkId				{@link INetwork} ID of type {@link String}
	 * @return						Returns {@link List} of {@link INetworkNodeInfo}
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract List<INetworkNodeInfo> getNetworkTopNodes(String networkId)
			throws QuadrigaStorageException;




	/**
	 * Get All the statements of the the network. It could be used to delete, modify, archive a network.
	 * @param networkId							{@link INetwork} ID of type {@link String}
	 * @return									Returns {@link List} of {@link INetworkNodeInfo} 
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */		
	public abstract List<INetworkNodeInfo> getAllNetworkNodes(String networkId)
			throws QuadrigaStorageException;

	/**
	 * Archive {@link INetwork} statements in case a new INetwork has replaced it. 
	 * We could use this method when a rejected {@link INetwork} is been replaced by new {@link INetwork}
	 * @param networkId							{@link INetwork} ID of type {@link String}
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract void archiveNetwork(String networkId) throws QuadrigaStorageException;

	/**
	 * This method should help in getting complete Network XML from Qstore.
	 * QStore allows us to get network XML for specific {@link RelationEventType} and also List of {@link RelationEventType} embedded in XML.
	 * We have to get the list of top node and send all the {@link RelationEventType} ID and {@link AppellationEventType} IDs embedded in XML  
	 * @param XML									List of {@link RelationEventType} ID and {@link AppellationEventType} IDs embedded in XML 
	 * @return										{@link String} XML containing {@link ElementEventsType} embedding {@link RelationEventType} and {@link AppellationEventType}
	 * @throws ParserConfigurationException			Throws Parse config errors in case
	 * @throws SAXException							Throws SAX Exception if the XML is not formed properly
	 * @throws IOException							Throws IO exception in case the QStore is not available
	 */
	public abstract String getWholeNetworkXMLFromQStore(String XML) throws ParserConfigurationException,
			SAXException, IOException;


	/**
	 * 
	 * @param networkId
	 * @return
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId)
			throws QuadrigaStorageException;

	/**
	 * 
	 * @param projectid
	 * @return
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract List<INetwork> getNetworksInProject(String projectid)
			throws QuadrigaStorageException;

	/**
	 * 
	 * @param networkId
	 * @param networkName
	 * @return
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract String updateNetworkName(String networkId,String networkName) throws QuadrigaStorageException;


	/**
	 * 
	 * @param userName
	 * @return
	 * @throws JSONException
	 */
	public abstract String getNetworkTree(String userName) throws JSONException;

	/**
	 * Get Relation Event XML from QStore for a particular {@link RelationEventType} ID.
	 * @param relationID				{@link RelationEventType} ID of type {@link String}
	 * @return							
	 * @throws JAXBException
	 */
	public abstract  String getRelationEventXmlStringFromQstore(String id)throws JAXBException;

	/**
	 * 
	 * @return
	 */
	public abstract String shortUUID();
	
	/**
	 * 
	 * @param networkId
	 * @param jqueryType
	 * @return
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract  INetworkJSon getJsonForNetworks(String networkId, String jqueryType)  throws QuadrigaStorageException;

	/**
	 * 
	 * @param predicateObject
	 * @param nodeObject
	 * @return
	 */
	public abstract NodeObject getPredicateNodeObjectContent(PredicateObject predicateObject,
			NodeObject nodeObject);

	/**
	 * 
	 * @param relationEventId
	 * @param statementType
	 * @param statementId
	 * @param relationEventPredicateMapping
	 * @param nodeObjectWithStatementList
	 * @return
	 * @throws JAXBException
	 * @throws QStoreStorageException			Database storage exception thrown
	 */
	public abstract List<INodeObjectWithStatement> parseEachStatement(String relationEventId,
			String statementType, String statementId,
			List<List<Object>> relationEventPredicateMapping,
			List<INodeObjectWithStatement> nodeObjectWithStatementList)
			throws JAXBException, QStoreStorageException;

	/**
	 * 
	 * @param relationEventId
	 * @return
	 * @throws JAXBException
	 * @throws QStoreStorageException			Database storage exception thrown
	 */
	public abstract ElementEventsType getElementEventTypeFromRelationEvent(
			String relationEventId) throws JAXBException,
			QStoreStorageException;

	/**
	 * 
	 * @param xml
	 * @return
	 * @throws JAXBException
	 */
	public abstract ElementEventsType unMarshalXmlToElementEventsType(String xml)
			throws JAXBException;

	/**
	 * 
	 * @param relationEventType
	 * @param relationEventObject
	 * @param relationEventPredicateMapping
	 * @return
	 */
	public abstract RelationEventObject parseThroughRelationEvent(
			RelationEventType relationEventType,
			RelationEventObject relationEventObject,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * 
	 * @param relationEventId
	 * @param predicateName
	 * @param appellationEventObject
	 * @param relationEventPredicateMapping
	 * @return
	 */
	public abstract String stackRelationEventPredicateAppellationObject(String relationEventId,
			String predicateName,
			AppellationEventObject appellationEventObject,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * 
	 * @param relationEventType
	 * @param predicateType
	 * @param relationEventPredicateMapping
	 * @return
	 */
	public abstract PredicateObject parseThroughPredicate(RelationEventType relationEventType,
			PredicateType predicateType,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * 
	 * @param relationEventId
	 * @param relationEventPredicateMapping
	 * @return
	 */
	public abstract AppellationEventObject checkRelationEventInStack(String relationEventId,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * 
	 * @param relationEventType
	 * @param subjectObjectType
	 * @param relationEventPredicateMapping
	 * @return
	 */
	public abstract SubjectObject parseThroughSubject(RelationEventType relationEventType,
			SubjectObjectType subjectObjectType,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * 
	 * @param relationEventType
	 * @param subjectObjectType
	 * @param relationEventPredicateMapping
	 * @return
	 */
	public abstract ObjectTypeObject parseThroughObject(RelationEventType relationEventType,
			SubjectObjectType subjectObjectType,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * 
	 * @param relationEventObject
	 * @param nodeObjectWithStatementList
	 * @param statementId
	 * @return
	 */
	public abstract List<INodeObjectWithStatement> prepareNodeObjectContent(
			RelationEventObject relationEventObject,
			List<INodeObjectWithStatement> nodeObjectWithStatementList,
			String statementId);

	/**
	 * 
	 * @param relationEventId
	 * @param predicateName
	 * @param relationEventPredicateMapping
	 * @return
	 */
	public abstract String checkRelationEventRepeatation(String relationEventId,
			String predicateName,
			List<List<Object>> relationEventPredicateMapping);
	
	
	
}