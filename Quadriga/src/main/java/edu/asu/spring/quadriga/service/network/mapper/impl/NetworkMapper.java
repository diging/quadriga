package edu.asu.spring.quadriga.service.network.mapper.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.dao.IEditorDAO;
import edu.asu.spring.quadriga.dao.INetworkDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factory.impl.networks.NetworkFactory;
import edu.asu.spring.quadriga.domain.factory.networks.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.dto.NetworkStatementsDTO;
import edu.asu.spring.quadriga.dto.NetworksDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.mapper.INetworkMapper;
import edu.asu.spring.quadriga.service.network.mapper.IWorkspaceNetworkMapper;
import edu.asu.spring.quadriga.service.user.mapper.IUserDeepMapper;

@Service
public class NetworkMapper implements INetworkMapper{
    
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
    public INetwork getNetwork(NetworksDTO networksDTO) throws QuadrigaStorageException
    {
        INetwork network = null;
        if(networksDTO != null)
        {
            network = networkFactory.createNetworkObject();
            network.setNetworkId(networksDTO.getNetworkid());
            network.setNetworkName(networksDTO.getNetworkname());
        
            IWorkspaceNetwork networkworkspace = networkworkspacemapper.getNetworkWorkspaceByNetworkDTO(networksDTO, network);
            network.setNetworkWorkspace(networkworkspace);

            network.setStatus(networksDTO.getStatus());
            if(networksDTO.getNetworkowner() != null)
                network.setCreator(userDeepMapper.getUser(networksDTO.getNetworkowner()));
            network.setCreatedBy(networksDTO.getCreatedby());
            network.setCreatedDate(networksDTO.getCreateddate());
            network.setUpdatedBy(networksDTO.getUpdatedby());
            network.setUpdatedDate(networksDTO.getUpdateddate());
            
            List<Integer> version = dbconnect.getLatestVersionOfNetwork(networksDTO.getNetworkid());
            if(version!=null && !version.isEmpty()){
                network.setVersionNumber(version.get(0));
            }
            
            List<NetworkStatementsDTO> networkStatementsDTOList = networksDTO.getNetworkStamentesDTOList();
            List<INetworkNodeInfo> networkList = null;
            if(networkStatementsDTOList != null)
            {
                networkList = new ArrayList<INetworkNodeInfo>();
                INetworkNodeInfo networkNodeInfo = null;
                for(NetworkStatementsDTO networkStatementsDTO:networkStatementsDTOList)
                {
                    networkNodeInfo = networkNodeInfoFactory.createNetworkNodeInfoObject();
                    networkNodeInfo.setId(networkStatementsDTO.getStatementid());
                    networkNodeInfo.setStatementType(networkStatementsDTO.getStatementtype());
                    networkNodeInfo.setVersion(networkStatementsDTO.getVersion());
                    networkNodeInfo.setIsTop(networkStatementsDTO.getIstop());
                    networkList.add(networkNodeInfo);
                }
            }
            network.setNetworkNodes(networkList);
            
            //TODO : add version number and text url to network DTO
            //network.setAssignedUser(networksDTO.get)
            //network.setNetworkNodes(networksDTO.get)
            //network.setNetworksAccess(networksDTO.getn)
            //network.setTextUrl(textUrl)
            //network.setVersionNumber(networksDTO.get)
        }
        return network;
    }
    
    @Override
    public INetwork getNetworkShallowDetails(NetworksDTO networksDTO) throws QuadrigaStorageException{
        
        INetwork network = null;
        if(networksDTO != null)
        {
            network = networkFactory.createNetworkObject();
            network.setNetworkId(networksDTO.getNetworkid());
            network.setNetworkName(networksDTO.getNetworkname());

            network.setStatus(networksDTO.getStatus());
            if(networksDTO.getNetworkowner() != null)
                network.setCreator(userDeepMapper.getUser(networksDTO.getNetworkowner()));
            network.setCreatedBy(networksDTO.getCreatedby());
            network.setCreatedDate(networksDTO.getCreateddate());
            network.setUpdatedBy(networksDTO.getUpdatedby());
            network.setUpdatedDate(networksDTO.getUpdateddate());
        }
        return network;
    }
    
    @Override
    public List<INetwork> getListOfNetworksForUser(IUser user) throws QuadrigaStorageException
    {
        List<INetwork> networkList = null;
        List<NetworksDTO> networksDTO = dbconnect.getNetworkList(user);
        if(networksDTO!=null){
            networkList = getNetworkListFromDTOList(networksDTO);
        }
        return networkList;
    }
    
    @Override
    public List<INetwork> getNetworkListForProject(String projectid) throws QuadrigaStorageException{
        List<INetwork> networkList = null;
        List<NetworksDTO> networksDTO = dbconnect.getNetworkDTOList(projectid);
        if(networksDTO!=null){
            networkList = getNetworkListFromDTOList(networksDTO);
        }
        return networkList;
    }
   
    
    @Override
    public List<INetwork> getNetworksOfUserWithStatus(IUser user, String networkStatus) throws QuadrigaStorageException{
        List<INetwork> networkList = null;
        List<NetworksDTO> networksDTO = editormanager.getNetworksOfUserWithStatus(user,networkStatus);
        if(networksDTO!=null){
            networkList = getNetworkListFromDTOList(networksDTO);
        }
        return networkList;
    }
    
    @Override
    public List<INetworkNodeInfo> getNetworkNodes(String networkId,int versionId)
            throws QuadrigaStorageException{
        
        List<INetworkNodeInfo> networkNodeList = null;
        List<NetworkStatementsDTO> networkStatementsDTOList = dbconnect.getNetworkNodes(networkId, versionId);
        if(networkStatementsDTOList != null)
        {
            networkNodeList = new ArrayList<INetworkNodeInfo>();
            INetworkNodeInfo networkNodeInfo = null;
            for(NetworkStatementsDTO networkStatementsDTO:networkStatementsDTOList)
            {
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
    public List<INetwork> getEditorNetworkList(IUser user)
            throws QuadrigaStorageException{
        List<INetwork> networkList = null;
        List<NetworksDTO> networksDTO = editormanager.getEditorNetworkList(user);
        if(networksDTO!=null){
            networkList = getNetworkListFromDTOList(networksDTO);
        }
        return networkList;
        
    }
    
    public List<INetwork> getNetworkListFromDTOList(List<NetworksDTO> networksDTOList) throws QuadrigaStorageException{
        
        List<INetwork> networkList = null;
        if(networksDTOList != null)
        {
            networkList = new ArrayList<INetwork>();
            INetwork network = null;
            for(NetworksDTO networkDTO: networksDTOList)
            {
                network = networkFactory.createNetworkObject();
                network.setNetworkId(networkDTO.getNetworkid());
                network.setNetworkName(networkDTO.getNetworkname());
                network.setStatus(networkDTO.getStatus());
                network.setCreatedDate(networkDTO.getCreateddate());
                IWorkspaceNetwork networkworkspace = networkworkspacemapper.getNetworkWorkspaceByNetworkDTO(networkDTO, network);
                network.setNetworkWorkspace(networkworkspace);
                if(networkDTO.getNetworkowner() != null)
                    network.setCreator(userDeepMapper.getUser(networkDTO.getNetworkowner()));
                networkList.add(network);
            }
        }
        return networkList;
    }
    
}
