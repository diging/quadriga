package edu.asu.spring.quadriga.mapper.networks.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.IEditorDAO;
import edu.asu.spring.quadriga.dao.INetworkDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factory.networks.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.factory.networks.impl.NetworkFactory;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.networks.INetworkMapper;
import edu.asu.spring.quadriga.mapper.networks.IWorkspaceNetworkMapper;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

@Service
public class NetworkMapper implements INetworkMapper {

    @Autowired
    private INetworkDAO dbconnect;

    @Autowired
    private IEditorDAO editormanager;

    @Autowired
    private NetworkFactory networkFactory;

    @Autowired
    private IUserDeepMapper userDeepMapper;

    @Autowired
    private IWorkspaceNetworkMapper networkworkspacemapper;

    @Autowired
    private INetworkNodeInfoFactory networkNodeInfoFactory;

    @Override
    public INetwork getNetwork(NetworksDTO networksDTO) throws QuadrigaStorageException {
        if (networksDTO == null) {
            return null;
        }

        INetwork network = networkFactory.createNetworkObject();
        network.setNetworkId(networksDTO.getNetworkid());
        network.setNetworkName(networksDTO.getNetworkname());

        network.setWorkspace(networkworkspacemapper.getWorkspaces(networksDTO, network));

        network.setStatus(networksDTO.getStatus());
        if (networksDTO.getNetworkowner() != null) {
            network.setCreator(userDeepMapper.getUser(networksDTO.getNetworkowner()));
        }
        network.setCreatedBy(networksDTO.getCreatedby());
        network.setCreatedDate(networksDTO.getCreateddate());
        network.setUpdatedBy(networksDTO.getUpdatedby());
        network.setUpdatedDate(networksDTO.getUpdateddate());

        List<Integer> version = dbconnect.getLatestVersionOfNetwork(networksDTO.getNetworkid());
        if (version != null && !version.isEmpty()) {
            network.setVersionNumber(version.get(0));
        }

        List<NetworkStatementsDTO> networkStatementsDTOList = networksDTO.getNetworkStamentesDTOList();
        List<INetworkNodeInfo> networkList = null;
        if (networkStatementsDTOList != null) {
            networkList = new ArrayList<INetworkNodeInfo>();
            INetworkNodeInfo networkNodeInfo = null;
            for (NetworkStatementsDTO networkStatementsDTO : networkStatementsDTOList) {
                networkNodeInfo = networkNodeInfoFactory.createNetworkNodeInfoObject();
                networkNodeInfo.setId(networkStatementsDTO.getStatementid());
                networkNodeInfo.setStatementType(networkStatementsDTO.getStatementtype());
                networkNodeInfo.setVersion(networkStatementsDTO.getVersion());
                networkNodeInfo.setIsTop(networkStatementsDTO.getIstop());
                networkList.add(networkNodeInfo);
            }
        }
        network.setNetworkNodes(networkList);

        // TODO : add version number and text url to network DTO

        return network;
    }

    @Override
    public INetwork getNetworkShallowDetails(NetworksDTO networksDTO) throws QuadrigaStorageException {

        INetwork network = null;
        if (networksDTO != null) {
            network = networkFactory.createNetworkObject();
            network.setNetworkId(networksDTO.getNetworkid());
            network.setNetworkName(networksDTO.getNetworkname());

            network.setStatus(networksDTO.getStatus());
            if (networksDTO.getNetworkowner() != null) {
                network.setCreator(userDeepMapper.getUser(networksDTO.getNetworkowner()));
            }
            network.setCreatedBy(networksDTO.getCreatedby());
            network.setCreatedDate(networksDTO.getCreateddate());
            network.setUpdatedBy(networksDTO.getUpdatedby());
            network.setUpdatedDate(networksDTO.getUpdateddate());
        }
        return network;
    }

    @Override
    public List<INetwork> getListOfNetworksForUser(IUser user) throws QuadrigaStorageException {
        List<NetworksDTO> networksDTO = dbconnect.getNetworkList(user);
        if (networksDTO != null) {
            return getNetworkListFromDTOList(networksDTO);
        }
        return null;
    }

    @Override
    public List<INetwork> getListOfApprovedNetworks() throws QuadrigaStorageException {
        List<NetworksDTO> networksDTO = dbconnect.getApprovedNetworkList();
        if (networksDTO != null) {
            return getNetworkListFromDTOList(networksDTO);
        }
        return null;
    }

    @Override
    public List<INetwork> getNetworkListForProject(String projectid) throws QuadrigaStorageException {
        List<NetworksDTO> networksDTO = dbconnect.getNetworkDTOList(projectid);
        if (networksDTO != null) {
            return getNetworkListFromDTOList(networksDTO);
        }
        return null;
    }

    @Override
    public List<INetwork> getNetworksOfUserWithStatus(IUser user, String networkStatus)
            throws QuadrigaStorageException {
        List<NetworksDTO> networksDTO = editormanager.getNetworksOfUserWithStatus(user, networkStatus);
        if (networksDTO != null) {
            return getNetworkListFromDTOList(networksDTO);
        }
        return null;
    }

    @Override
    public List<INetworkNodeInfo> getNetworkNodes(String networkId, int versionId) throws QuadrigaStorageException {

        List<INetworkNodeInfo> networkNodeList = null;
        List<NetworkStatementsDTO> networkStatementsDTOList = dbconnect.getNetworkNodes(networkId, versionId);
        if (networkStatementsDTOList != null) {
            networkNodeList = new ArrayList<INetworkNodeInfo>();
            INetworkNodeInfo networkNodeInfo = null;
            for (NetworkStatementsDTO networkStatementsDTO : networkStatementsDTOList) {
                networkNodeInfo = networkNodeInfoFactory.createNetworkNodeInfoObject();
                networkNodeInfo.setId(networkStatementsDTO.getStatementid());
                networkNodeInfo.setStatementType(networkStatementsDTO.getStatementtype());
                networkNodeInfo.setVersion(networkStatementsDTO.getVersion());
                networkNodeInfo.setIsTop(networkStatementsDTO.getIstop());
                networkNodeList.add(networkNodeInfo);
            }
        }

        return networkNodeList;
    }

    @Override
    public List<INetworkNodeInfo> getNetworkNodes(List<String> statementIds) throws QuadrigaStorageException{
        List<INetworkNodeInfo> networkNodeList = null;
        List<NetworkStatementsDTO> networkStatementsDTOList = dbconnect.getNetworkNodes(statementIds);
        if (networkStatementsDTOList != null) {
            networkNodeList = new ArrayList<INetworkNodeInfo>();
            INetworkNodeInfo networkNodeInfo = null;
            for (NetworkStatementsDTO networkStatementsDTO : networkStatementsDTOList) {
                networkNodeInfo = networkNodeInfoFactory.createNetworkNodeInfoObject();
                networkNodeInfo.setId(networkStatementsDTO.getStatementid());
                networkNodeInfo.setStatementType(networkStatementsDTO.getStatementtype());
                networkNodeInfo.setVersion(networkStatementsDTO.getVersion());
                networkNodeInfo.setIsTop(networkStatementsDTO.getIstop());
                networkNodeList.add(networkNodeInfo);
            }
        }

        return networkNodeList;
    }
    
    @Override
    public List<INetwork> getEditorNetworkList(IUser user) throws QuadrigaStorageException {
        List<INetwork> networkList = null;
        List<NetworksDTO> networksDTO = editormanager.getEditorNetworkList(user);
        if (networksDTO != null) {
            networkList = getNetworkListFromDTOList(networksDTO);
        }
        return networkList;

    }

    public List<INetwork> getNetworkListFromDTOList(List<NetworksDTO> networksDTOList) throws QuadrigaStorageException {

        List<INetwork> networkList = null;
        if (networksDTOList != null) {
            networkList = new ArrayList<INetwork>();
            for (NetworksDTO networkDTO : networksDTOList) {
                INetwork network = networkFactory.createNetworkObject();
                network.setNetworkId(networkDTO.getNetworkid());
                network.setNetworkName(networkDTO.getNetworkname());
                network.setStatus(networkDTO.getStatus());
                network.setCreatedDate(networkDTO.getCreateddate());
                network.setWorkspace(networkworkspacemapper.getWorkspaces(networkDTO, network));
                if (networkDTO.getNetworkowner() != null) {
                    network.setCreator(userDeepMapper.getUser(networkDTO.getNetworkowner()));
                }
                networkList.add(network);
            }
        }
        return networkList;
    }

}
