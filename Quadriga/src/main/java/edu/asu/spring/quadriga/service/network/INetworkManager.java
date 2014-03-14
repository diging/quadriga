package edu.asu.spring.quadriga.service.network;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IUser;
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
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.domain.INetworkJSon;
import edu.asu.spring.quadriga.service.network.domain.INodeObjectWithStatement;

public interface INetworkManager {

	public static String D3JQUERY = "D3JQUERY";
	public static String JITJQUERY= "JITJQUERY";
	
	public static String RELATIONEVENT = "RE";
	public static String APPELLATIONEVENT = "AE";
	/**
	 * Gets the QStrore Add URL
	 * 
	 * @return String URL
	 */
	public abstract String getQStoreAddURL();

	public abstract String receiveNetworkSubmitRequest(
			JAXBElement<ElementEventsType> response, IUser user,
			String networkName, String workspaceid,String updateStatus,String networkId);

	/**
	 * Formats a unformatted XML to formatted XML
	 * @param input
	 * @param indent
	 * @return
	 */
	public abstract String prettyFormat(String input, int indent);

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

	public abstract INetwork getNetworkStatus(String networkName, IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getNetworkList(IUser user)
			throws QuadrigaStorageException;

	public abstract String getProjectIdForWorkspaceId(String workspaceid)
			throws QuadrigaStorageException;

	public abstract boolean hasNetworkName(String networkName, IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetworkNodeInfo> getNetworkTopNodes(String networkId)
			throws QuadrigaStorageException;

	public abstract String getQStoreGetURL();

	public abstract ResponseEntity<String> getNodeXmlFromQstore(String id) throws JAXBException;

	public abstract List<INetworkNodeInfo> getAllNetworkNodes(String networkId)
			throws QuadrigaStorageException;

	public abstract void archiveNetwork(String networkId) throws QuadrigaStorageException;

	public abstract String getWholeXMLQStore(String XML) throws ParserConfigurationException,
			SAXException, IOException;

	public abstract String getQStoreGetPOSTURL();

	public abstract List<INetworkNodeInfo> getNetworkOldVersionTopNodes(String networkId)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getNetworksInProject(String projectid)
			throws QuadrigaStorageException;

	public abstract String updateNetworkName(String networkId,String networkName) throws QuadrigaStorageException;


	String getNetworkTree(String userName) throws JSONException;

	public String getNodeXmlStringFromQstore(String id)throws JAXBException;

	String shortUUID();
	
	public INetworkJSon getJsonForNetworks(String networkId, String jqueryType)  throws QuadrigaStorageException;

	NodeObject getPredicateNodeObjectContent(PredicateObject predicateObject,
			NodeObject nodeObject);

	List<INodeObjectWithStatement> parseEachStatement(String relationEventId,
			String statementType, String statementId,
			List<List<Object>> relationEventPredicateMapping,
			List<INodeObjectWithStatement> nodeObjectWithStatementList)
			throws JAXBException, QStoreStorageException;

	ElementEventsType getElementEventTypeFromRelationEvent(
			String relationEventId) throws JAXBException,
			QStoreStorageException;

	ElementEventsType unMarshalXmlToElementEventsType(String xml)
			throws JAXBException;

	RelationEventObject parseThroughRelationEvent(
			RelationEventType relationEventType,
			RelationEventObject relationEventObject,
			List<List<Object>> relationEventPredicateMapping);

	String stackRelationEventPredicateAppellationObject(String relationEventId,
			String predicateName,
			AppellationEventObject appellationEventObject,
			List<List<Object>> relationEventPredicateMapping);

	PredicateObject parseThroughPredicate(RelationEventType relationEventType,
			PredicateType predicateType,
			List<List<Object>> relationEventPredicateMapping);

	AppellationEventObject checkRelationEventInStack(String relationEventId,
			List<List<Object>> relationEventPredicateMapping);

	SubjectObject parseThroughSubject(RelationEventType relationEventType,
			SubjectObjectType subjectObjectType,
			List<List<Object>> relationEventPredicateMapping);

	ObjectTypeObject parseThroughObject(RelationEventType relationEventType,
			SubjectObjectType subjectObjectType,
			List<List<Object>> relationEventPredicateMapping);

	List<INodeObjectWithStatement> prepareNodeObjectContent(
			RelationEventObject relationEventObject,
			List<INodeObjectWithStatement> nodeObjectWithStatementList,
			String statementId);

	String checkRelationEventRepeatation(String relationEventId,
			String predicateName,
			List<List<Object>> relationEventPredicateMapping);
	
	
	
}