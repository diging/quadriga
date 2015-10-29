package edu.asu.spring.quadriga.service.impl.passthroughproject;

import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;
import edu.asu.spring.quadriga.service.workspace.mapper.IListExternalWSManager;

@Service
public class PassThroughProjectManager implements IPassThroughProjectManager {

    @Autowired
    private IWorkspaceDAO workspaceDao;

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private IListExternalWSManager externalWSManager;

    @Override
    public String createWorkspaceForExternalProject(String externalWorkspaceId, String response, IUser user)
            throws JAXBException, QuadrigaStorageException, QuadrigaAccessException {
        // TODO Auto-generated method stub -- Karthik

        boolean isExternalWorkspaceExists = externalWSManager.isExternalWorkspaceExists(externalWorkspaceId);
        String workspaceId = null;
        if (!isExternalWorkspaceExists) {
            // External workspace does not exists so insert the values into
            // externalWorkspace table
            // Create a new externalWorkspaceId and InternalWorkspaceId and then
            // call storeNetworkDetails
            workspaceId = workspaceDao.generateUniqueID();
            externalWSManager.createExternalWorkspace(externalWorkspaceId, workspaceId);
        } else {
            // Get the workspace Id related to the external workspace Id
            workspaceId = externalWSManager.getInternalWorkspaceId(externalWorkspaceId);
        }

        // After creating the values save the values by calling store network
        // details. If already available. Call network details for updation

        String networkName = "VogenWeb_Details";

        networkManager.storeNetworkDetails(response, user, networkName, workspaceId, INetworkManager.NEWNETWORK, "",
                INetworkManager.VERSION_ZERO);

        return null;
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
    public String callQStore(String xml, IUser user) throws ParserConfigurationException, SAXException, IOException,
            JAXBException, QuadrigaStorageException, QuadrigaAccessException {
        // TODO Auto-generated method stub -- Karthik
        return createWorkspaceForExternalProject("", networkManager.storeXMLQStore(xml), user);
        // Returns networkId
    }

}
