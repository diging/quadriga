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
/**
 * 
 * @author Lohith Dwaraka
 *
 */
public interface INetworkManager {

	// Constants to request for type of JQuery JSon 
	public static String D3JQUERY = "D3JQUERY";
	public static String JITJQUERY= "JITJQUERY";
	
	// Constants to mention type of event in the QStore XML
	public static String RELATIONEVENT = "RE";
	public static String APPELLATIONEVENT = "AE";
	
	//Latest Version number for Network
	public static int VERSION_ZERO = 0;

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
	 * This method should help in getting the network's previous version network statements.
	 * We could use this to view different versions of the networks. 
	 * @param networkId							{@link INetwork} ID of type {@link String}
	 * @param versionNo							Version number of network. 
	 * @return									Returns the {@link List} of {@link INetworkNodeInfo}
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId, int versionNo)
			throws QuadrigaStorageException;

	/**
	 * This method should help in getting {@link List} of {@link INetwork} in the {@link IProject}.
	 * We could use this to list Networks belonging to a {@link IProject}. 
	 * @param projectid							{@link IProject} ID of type {@link String}
	 * @return									Returns {@link List} of {@link INetwork}
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract List<INetwork> getNetworksInProject(String projectid)
			throws QuadrigaStorageException;

	/**
	 * This method should help in renaming the {@link INetwork}. 
	 * We could use this while we have a network been rejected and we add a new {@link INetwork} based on editor annotation.
	 * We could prefer to change the name of the {@link INetwork} while reuploading the {@link INetwork} through clients. 
	 * @param networkId							{@link INetwork} ID of type {@link String}
	 * @param networkName						New {@link INetwork} name of type {@link String}
	 * @return									Returns success or error message in form of {@link String}.
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract String updateNetworkName(String networkId,String networkName) throws QuadrigaStorageException;


	/**
	 * This method should help in getting the JSTree JSon for the a {@link IUser} name.
	 * This tree JSon would contain the {@link List} of {@link IProject}. 
	 * Under each {@link IProject} there could a {@link List} of {@link IWorkSpace}. 
	 * Under each {@link IWorkSpace} there could a {@link List} of {@link INetwork}  
	 * @param userName							{@link IUser} name of the logged in {@link IUser}
	 * @return									Returns the JSTree JSon in the form of {@link String}
	 * @throws JSONException					Throws the JSon Exception in case we have any exception while forming the JSon object
	 */
	public abstract String getNetworkJSTreeJson(String userName) throws JSONException;

	/**
	 * This method should help in getting {@link ElementEventsType} XML for a particular {@link RelationEventType} ID.
	 * Usually our source of XML would be from QStore for a particular {@link RelationEventType} ID.
	 * This function would return nothing if the {@link RelationEventType} ID is not present in QStore.
	 * @param relationEventTypeID				{@link RelationEventType} ID of type {@link String}
	 * @return									Returns the {@link ElementEventsType} Object in form of XML.
	 * @throws JAXBException					JAXB exception are thrown during unmarshalling the object into XML.
	 */
	public abstract  String getRelationEventXmlStringFromQstore(String relationEventTypeID)throws JAXBException;

	/**
	 * This method should help in returning the UUID in the form of {@link String}.
	 * Usually used to append to a String to make it more unique, as UUID gives a unique ID all the time.
	 * @return									Returns the UUID in form of {@link String}
	 */
	public abstract String shortUUID();
	
	/**
	 * This method should help in returning the JSon String for a {@link INetwork} for a particualr JQuery Type
	 * @param networkId							{@link INetwork} ID of type {@link String}
	 * @param jqueryType						JQuery type could be D3JQUERY or JITJQUERY
	 * @return									Returns {@link INetworkJSon} object which contains the JSon String and {@link List} of network Name of type {@link String} 
	 * @throws QuadrigaStorageException			Database storage exception thrown
	 */
	public abstract  INetworkJSon getJsonForNetworks(String networkId, String jqueryType)  throws QuadrigaStorageException;

	/**
	 * This method would help in getting the Predicate details from the {@link PredicateObject} in {@link NodeObject}.
	 * We would be parsing through the {@link RelationEventType} object, where we would encounter {@link PredicateObject}.
	 * @param predicateObject					Target {@link PredicateObject} 
	 * @param nodeObject						Target {@link NodeObject}
	 * @return									Returns the modified {@link NodeObject}
	 */
	public abstract NodeObject getPredicateNodeObjectContent(PredicateObject predicateObject,
			NodeObject nodeObject);

	/**
	 * This method would help in parsing through a statement of type {@link RelationEventType}.
	 * A Network would contain a {@link List} of Network Statements, while Iterating through each statement we could call this method. 
	 * @param relationEventId					{@link RelationEventType} ID in form of {@link String}	
	 * @param statementType						Statement Type could be RE or AE constant
	 * @param statementId						Usually {@link RelationEventType} ID which should be assigned to each node for highlighting.
	 * @param relationEventPredicateMapping		{@link List} of {@link List} of {@link Object} to hold {@link PredicateObject} in it to avoid redundancy in the network. 
	 * @param nodeObjectWithStatementList		{@link List} of {@link INodeObjectWithStatement} containing the details of {@link NodeObject}
	 * @return									Returns the updated {@link List} of {@link INodeObjectWithStatement}
	 * @throws JAXBException					Throws JAXB exception in case we have issues while parsing JAXB element object.
	 * @throws QStoreStorageException			Database storage exception thrown
	 */
	public abstract List<INodeObjectWithStatement> parseEachStatement(String relationEventTypeId,
			String statementType, String statementId,
			List<List<Object>> relationEventPredicateMapping,
			List<INodeObjectWithStatement> nodeObjectWithStatementList)
			throws JAXBException, QStoreStorageException;

	/**
	 * This method should help in getting the {@link ElementEventsType} object using a {@link RelationEventType} ID.
	 * Usually the source of the data for {@link RelationEventType} is QStore, We could get the XML from QStore and Marshall it into a {@link ElementEventsType} object.
	 * @param relationEventId					{@link RelationEventType} ID in form of {@link String}	
	 * @return									Returns the {@link ElementEventsType} object for a particular {@link RelationEventType} ID
	 * @throws JAXBException					Throws JAXB exception in case we have issues while unmarshalling.
	 * @throws QStoreStorageException			Database storage exception thrown
	 */
	public abstract ElementEventsType getElementEventTypeFromRelationEventTypeID(
			String relationEventId) throws JAXBException,
			QStoreStorageException;

	/**
	 * This method shoud help in Unmarshalling XML {@link String} into {@link ElementEventsType} object.
	 * @param xml								XML in the form of {@link String}	
	 * @return									Returns the {@link ElementEventsType} object for a particular QStore XML
	 * @throws JAXBException					Throws JAXB exception in case we have issues while unmarshalling.
	 */
	public abstract ElementEventsType unMarshalXmlToElementEventsType(String xml)
			throws JAXBException;

	/**
	 * This method should help in parsing a {@link RelationEventType} object to get the details of the {@link RelationEventObject}.
	 * It could fetch information of the {@link PredicateObject} , {@link SubjectObject}, {@link ObjectTypeObject} of the {@link RelationEventObject}
	 * @param relationEventType					{@link RelationEventType} object
	 * @param relationEventObject				{@link RelationEventObject} object
	 * @param relationEventPredicateMapping		{@link List} of {@link List} of {@link Object} to hold {@link PredicateObject} in it to avoid redundancy in the network.
	 * @return									Returns the {@link RelationEventObject} object with parsed details.
	 */
	public abstract RelationEventObject parseThroughRelationEvent(
			RelationEventType relationEventType,
			RelationEventObject relationEventObject,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * This method should help in parsing through the {@link PredicateType} of a particular {@link RelationEventType}.
	 * We could parse the {@link PredicateType} object for any required variables or objects in it.
	 * @param relationEventType						Target {@link RelationEventType} object
	 * @param predicateType							Target {@link PredicateType} object	
	 * @param relationEventPredicateMapping			{@link List} of {@link List} of {@link Object} to hold {@link PredicateObject} in it to avoid redundancy in the network.	
	 * @return										Returns the {@link PredicateObject} object with needed info. 
	 */
	public abstract PredicateObject parseThroughPredicate(RelationEventType relationEventType,
			PredicateType predicateType,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * This method should help in checking if {@link RelationEventType} is already parsed earlier.
	 * We could have foreign reference to another {@link RelationEventType} in the {@link SubjectObjectType} part of the {@link RelationEventType}.
	 * We should not parse through the foreign reference since it would be redundant.
	 * @param relationEventId						{@link RelationEventType} ID in the form of {@link String}
	 * @param relationEventPredicateMapping			{@link List} of {@link List} of {@link Object} to hold {@link PredicateObject} in it to avoid redundancy in the network.
	 * @return										Returns {@link AppellationEventObject} if we have found earlier, else it would return null
	 */
	public abstract AppellationEventObject isRelationEventPresentInStack(String relationEventId,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * This method should help in parsing through the {@link SubjectObjectType} of a particular {@link RelationEventType} for SubjectType of Relation.
	 * We could parse the {@link SubjectObjectType} object for any required variables or objects in it.
	 * @param relationEventType						Target {@link RelationEventType} object 
	 * @param subjectObjectType						Target {@link SubjectObjectType} object
	 * @param relationEventPredicateMapping			{@link List} of {@link List} of {@link Object} to hold {@link PredicateObject} in it to avoid redundancy in the network.
	 * @return										Returns {@link SubjectObject} with filled in required data
	 */
	public abstract SubjectObject parseThroughSubject(RelationEventType relationEventType,
			SubjectObjectType subjectObjectType,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * This method should help in parsing through the {@link SubjectObjectType} of a particular {@link RelationEventType} for ObjectType of Relation.
	 * @param relationEventType						Target {@link RelationEventType} object 
	 * @param subjectObjectType						Target {@link SubjectObjectType} object
	 * @param relationEventPredicateMapping			{@link List} of {@link List} of {@link Object} to hold {@link PredicateObject} in it to avoid redundancy in the network.
	 * @return										Returns {@link ObjectTypeObject} with filled in required data
	 */
	public abstract ObjectTypeObject parseThroughObject(RelationEventType relationEventType,
			SubjectObjectType subjectObjectType,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * This method should help in preparing the {@link NodeObject} content.
	 * Preparse {@link NodeObject} with the contents of {@link PredicateObject}, {@link SubjectObject}, {@link ObjectTypeObject}. 
	 * @param relationEventObject					{@link RelationEventObject} object
	 * @param nodeObjectWithStatementList			{@link List} of {@link INodeObjectWithStatement} object 
	 * @param statementId							Statement ID of the Relation in form of {@link String}
	 * @return										Returns updated {@link List} of {@link INodeObjectWithStatement} object
	 */
	public abstract List<INodeObjectWithStatement> prepareNodeObjectContent(
			RelationEventObject relationEventObject,
			List<INodeObjectWithStatement> nodeObjectWithStatementList,
			String statementId);

	/**
	 * This method should help in parsing through the {@link SubjectObjectType} of a particular {@link RelationEventType} for SubjectType of Relation.
	 * @param relationEventTypeId						{@link RelationEventType} ID in the form of {@link String}
	 * @param predicateName								{@link PredicateObject} name in the form of {@link String}
	 * @param relationEventPredicateMapping				{@link List} of {@link List} of {@link Object} to hold {@link PredicateObject} in it to avoid redundancy in the network.
	 * @return											Returns predicate name in the form of {@link String}
	 */
	public abstract String getPredicateNameFromStackOfAE(String relationEventTypeId,
			String predicateName,
			List<List<Object>> relationEventPredicateMapping);

	/**
	 * This method should help to get JSon of selected JQuery Type based on version number.
	 * Our System would store version num for each network and network statement. Based on the version number we need to form the JSon String. for view  
	 * @param networkId									{@link INetwork} ID of type {@link String}
	 * @param jqueryType								JQuery type could be D3JQUERY or JITJQUERY of type {@link String}
	 * @param versionNo									Version number of the {@link INetwork} in form of String
	 * @return											Returns {@link INetworkJSon} object which contains the JSon String and {@link List} of network Name of type {@link String}
	 * @throws QuadrigaStorageException					Database storage exception thrown
	 */
	public abstract INetworkJSon getJsonForOldNetworks(String networkId, String jqueryType,
			String versionNo) throws QuadrigaStorageException;
	
	
	
}