package edu.asu.spring.quadriga.service;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jettison.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.d3.domain.ID3Node;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

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

	public abstract String generateJsontoJQuery(String id, String statementType)
			throws JAXBException, QuadrigaStorageException;

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

	public abstract List<List<Object>> getRelationEventPredicateMapping();

	public abstract void setRelationEventPredicateMapping(
			List<List<Object>> relationEventPredicateMapping);

	public abstract List<INetwork> getNetworksInProject(String projectid)
			throws QuadrigaStorageException;

	public abstract String updateNetworkName(String networkId,String networkName) throws QuadrigaStorageException;

	public abstract void setIntialValueForD3JSon();

	public abstract String getD3JSon();

	String getStatementId();

	void setStatementId(String statementId);

	List<ID3Node> getD3NodeList();

	String getNetworkTree(String userName) throws JSONException;

	public String getNodeXmlStringFromQstore(String id)throws JAXBException;

	String shortUUID();
	
	
}