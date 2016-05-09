package edu.asu.spring.quadriga.rest;

import java.io.StringWriter;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.ProjectAuthorization;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.XMLInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.mapper.PassThroughProjectDTOMapper;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;
import edu.asu.spring.quadriga.service.passthroughproject.IXMLReader;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * 
 * Controller for pass through projects rest APIs exposed to other clients and
 * saving the networks in QSTORE.
 *
 */
@Controller
public class UploadNetworkRestController {

    @Autowired
    private IPassThroughProjectManager passThroughProjectManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IRestMessage errorMessageRest;

    @Autowired
    private IXMLReader xmlReader;

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @Autowired
    private IWorkspaceManager wsManager;
    
    @Autowired 
    private IRetrieveProjectManager projectManager;

    @Autowired
    private ProjectAuthorization authorization;

    @RequestMapping(value = "rest/network", method = RequestMethod.POST)
    public ResponseEntity<String> submitNetwork(HttpServletRequest request, @RequestHeader("Accept") String accept,
            HttpServletResponse response, @RequestBody String xml, Principal principal) throws RestException {

        xml = xml.trim();
        if (xml.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide XML in body of the post request.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        String userid = principal.getName();

        IUser user;
        try {
            user = userManager.getUser(userid);
        } catch (QuadrigaStorageException e) {
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        XMLInfo xmlInfo;
        try {
            xmlInfo = xmlReader.getXMLInfo(xml);
        } catch (DocumentParserException e) {
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        String projectId = null;
        String workspaceId = null;

        try {
            IProject project1 = projectManager.getProjectDetails(xmlInfo.getProjectId());
            System.out.println(project1);
            return new ResponseEntity<String>("done", HttpStatus.ACCEPTED);
        } catch (QuadrigaStorageException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        if (xmlReader.isPassThroughXML(xml)) {
            IPassThroughProject project = passThroughProjectManager.getPassThroughProject(xmlInfo);

            try {
                projectId = passThroughProjectManager.getInternalProjectId(project.getExternalProjectid(), project.getClient());
                if (projectId == null) {
                    projectId = passThroughProjectManager.addPassThroughProject(user, project);
                } else {
                    String roles[] = { RoleNames.ROLE_COLLABORATOR_ADMIN, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                            RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR };
                    boolean isAuthorized = authorization.chkAuthorization(userid, projectId, roles);
                    if (!isAuthorized) {
                        String errorMsg = errorMessageRest.getErrorMsg("User is not authorized to access the resource");
                        return new ResponseEntity<String>(errorMsg, HttpStatus.UNAUTHORIZED);
                    }
                }
            } catch (QuadrigaStorageException | QuadrigaAccessException | NoSuchRoleException e) {
                String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
                return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            try {
                workspaceId = passThroughProjectManager.createWorkspaceForExternalProject(xmlInfo, projectId, user);
            } catch (JAXBException | QuadrigaStorageException | QuadrigaAccessException e) {
                String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
                return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            try {
                workspaceId = xmlInfo.getExternalWorkspaceId();
                String networkName = xmlInfo.getNetworkName();
                if (workspaceId == null || workspaceId.isEmpty()) {
                    String errorMsg = errorMessageRest
                            .getErrorMsg("Please provide a workspace id as a part of post parameters");
                    return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
                }
                String projectid = wsManager.getProjectIdFromWorkspaceId(workspaceId);
                if (projectid == null || projectid.isEmpty()) {
                    String errorMsg = errorMessageRest
                            .getErrorMsg("No project could be found for the given workspace id " + workspaceId);
                    return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
                }

                if (networkName == null || networkName.isEmpty()) {
                    String errorMsg = errorMessageRest
                            .getErrorMsg("Please provide network name as a part of post parameters");
                    return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
                }

            } catch (QuadrigaStorageException e) {
                String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
                return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        String network = xmlReader.getNetwork(xml);

        String networkId = null;
        if (!StringUtils.isEmpty(network)) {
            try {
                String responseFromQStore = networkManager.storeNetworks(network);
                networkId = networkManager.storeNetworkDetails(responseFromQStore, user, xmlInfo.getNetworkName(),
                        workspaceId, INetworkManager.NEWNETWORK, "", INetworkManager.VERSION_ZERO);
            } catch (JAXBException | QStoreStorageException e) {
                String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
                return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        StringWriter writer = new StringWriter();
        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine(request);
            engine.init();

            Template template = engine.getTemplate("velocitytemplates/passthroughproject.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("projectId", projectId);
            context.put("workspaceId", workspaceId);
            context.put("projectName", xmlInfo.getName());
            context.put("workspaceName", xmlInfo.getWorkspaceName());
            context.put("networkId", networkId);
            template.merge(context, writer);
        } catch (Exception e) {
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_XML);

        return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);

    }

}
