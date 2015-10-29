package edu.asu.spring.quadriga.service.impl.passthroughproject;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.dao.workbench.passthroughproject.IPassThroughProjectDAO;
import edu.asu.spring.quadriga.dao.workspace.IWorkspaceDAO;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.EProjectAccessibility;
import edu.asu.spring.quadriga.dto.PassThroughProjectDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.BaseMapper;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.BaseManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

@Service
public class PassThroughProjectManager extends BaseManager implements
        IPassThroughProjectManager {

    @Autowired
    private IWorkspaceDAO workspaceDao;
    
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private IPassThroughProjectDAO projectDao;

    @Autowired
    IUserManager userManager;

    @Resource(name = "projectconstants")
    private Properties messages;

    @Override
    public String createWorkspaceForExternalProject(String response, IUser user)
            throws JAXBException {
        // TODO Auto-generated method stub -- Karthik
        String workspaceId = workspaceDao.generateUniqueID();
        String networkName = "VogenWeb_Details";
        return networkManager.storeNetworkDetails(response, user, networkName,
                workspaceId, INetworkManager.NEWNETWORK, "",
                INetworkManager.VERSION_ZERO);
        // return networkId passed as "" initially Response from qStore;
    }

    @Override
    @Transactional
    public String addPassThroughProject(Principal principal,
            String projectName, String description, String externalProjectid,
            String externalUserId, String externalUserName, String client) throws QuadrigaStorageException {

        String projectId = messages.getProperty("project_internalid.name")
                + generateUniqueID();

        IUser user = userManager.getUser(principal.getName());
        
        Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserDTO.findByUsername");
        query.setParameter("username",principal.getName());
        
        List<QuadrigaUserDTO> quadrigaUsers = query.list();

        PassThroughProjectDTO projectDTO = new PassThroughProjectDTO();
        // internal project details
        projectDTO.setProjectid(projectId);
        projectDTO.setProjectname(projectName);
        projectDTO.setDescription(description);
        // Since we are not passing unix name in REST request, we are assigning the unix name as projet name
        projectDTO.setUnixname(projectName);
        projectDTO.setProjectid(projectId);
        projectDTO.setProjectowner(quadrigaUsers.get(0));
        projectDTO.setCreatedby(user.getUserName());
        projectDTO.setCreateddate(new Date());
        projectDTO.setUpdatedby(user.getUserName());
        projectDTO.setUpdateddate(new Date());
        projectDTO.setAccessibility(EProjectAccessibility.PUBLIC.name());

        // external project details
        projectDTO.setExternalProjectid(externalProjectid);
        projectDTO.setExternalUserId(externalUserId);
        projectDTO.setExternalUserName(externalUserName);
        projectDTO.setClient(client);

        projectDao.saveNewDTO(projectDTO);

        return projectId;

    }

    @Override
    public void getPassThroughProjectDTO() {
        // TODO Auto-generated method stub

    }

    @Override
    public String callQStore(String xml, IUser user)
            throws ParserConfigurationException, SAXException, IOException,
            JAXBException {
        // TODO Auto-generated method stub -- Karthik
        return createWorkspaceForExternalProject(
                networkManager.storeXMLQStore(xml), user);
        // Returns networkId
    }

}
