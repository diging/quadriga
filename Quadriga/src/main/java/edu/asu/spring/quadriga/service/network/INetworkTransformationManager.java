package edu.asu.spring.quadriga.service.network;

import java.util.List;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.network.impl.CreationEvent;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;

/**
 * Manager class that handles network transformation. It gets networks from
 * QStore and then transforms it into Nodes and Links.
 * 
 * @author Julia Damerow
 *
 */
public interface INetworkTransformationManager {

    public abstract ITransformedNetwork getTransformedNetwork(String networkId) throws QuadrigaStorageException;

    public abstract ITransformedNetwork getTransformedNetwork(String networkId, String versionID)
            throws QuadrigaStorageException;

    /**
     * Method to retrieve transformed network of all networks given a project id
     * 
     * @param projectId
     * @return
     * @throws QuadrigaStorageException
     */
    ITransformedNetwork getTransformedNetworkOfProject(String projectId, String status) throws QuadrigaStorageException;

    ITransformedNetwork getSearchTransformedNetwork(String projectId, String conceptId, String status)
            throws QuadrigaStorageException;

    ITransformedNetwork getSearchTransformedNetworkMultipleProjects(List<String> projectIds, String conceptId,
            String status) throws QuadrigaStorageException;

    ITransformedNetwork getTransformedNetworkusingNetworkList(List<INetwork> networkList)
            throws QuadrigaStorageException;
    
    ITransformedNetwork getTransformedApprovedNetworks(List<INetwork> networkList) throws QuadrigaStorageException;

    ITransformedNetwork getAllTransformedNetworks(String xml)
            throws QStoreStorageException, JAXBException, QuadrigaStorageException;

    ITransformedNetwork getTransformedNetwork(List<CreationEvent> eventList,
            List<String> conceptIds);
}