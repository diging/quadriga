package edu.asu.spring.quadriga.service;

import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBElement;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.networks.ElementEventsType;
import edu.asu.spring.quadriga.domain.implementation.networks.RelationEventType;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface INetworkManager {

	/**
	 * Gets the QStrore Add URL
	 * 
	 * @return String URL
	 */
	public abstract String getQStoreAddURL();

	public abstract void receiveNetworkSubmitRequest(
			JAXBElement<ElementEventsType> response, IUser user,
			String networkName, String workspaceid);

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

	public abstract void getRelationEventElements(RelationEventType re,
			String networkId, IUser user) throws QuadrigaStorageException;

	public abstract INetwork getNetworkStatus(String networkName, IUser user)
			throws QuadrigaStorageException;

	public abstract List<INetwork> getNetworkList(IUser user)
			throws QuadrigaStorageException;

	public abstract String getProjectIdForWorkspaceId(String workspaceid)
			throws QuadrigaStorageException;

	public abstract boolean hasNetworkName(String networkName, IUser user)
			throws QuadrigaStorageException;

}