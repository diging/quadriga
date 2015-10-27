package edu.asu.spring.quadriga.service.impl.passthroughproject;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

@Service
public class PassThroughProjectManager implements IPassThroughProjectManager {

    @Autowired
    private IWorkspaceDAO workspaceDao;

    @Autowired
    private INetworkManager networkManager;

    @Override
    public String createWorkspaceForExternalProject(String response,IUser user) throws JAXBException {
        // TODO Auto-generated method stub -- Karthik
        String workspaceId = workspaceDao.generateUniqueID();
        String networkName = "VogenWeb_Details";
        return networkManager.storeNetworkDetails(response, user, networkName, workspaceId, INetworkManager.NEWNETWORK,
                "", INetworkManager.VERSION_ZERO);
        // return networkId passed as "" initially Response from qStore;
    }

    @Override
    public void addPassThroughProject() {
        // TODO Auto-generated method stub

    }

    @Override
    public void getPassThroughProjectDTO() {
        // TODO Auto-generated method stub

    }

    @Override
    public String callQStore(String xml,IUser user) throws ParserConfigurationException, SAXException, IOException, JAXBException {
        // TODO Auto-generated method stub -- Karthik
        return createWorkspaceForExternalProject(networkManager.storeXMLQStore(xml),user);
        // Returns networkId
    }

}
