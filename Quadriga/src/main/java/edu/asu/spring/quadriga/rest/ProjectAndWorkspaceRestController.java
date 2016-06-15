package edu.asu.spring.quadriga.rest;

import java.io.StringWriter;
import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.asu.spring.quadriga.aspects.ProjectAuthorization;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.XMLInfo;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;
import edu.asu.spring.quadriga.service.passthroughproject.IXMLReader;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class ProjectAndWorkspaceRestController {
    
    @Autowired
    private IRestMessage errorMessageRest;

    @Autowired
    private IXMLReader xmlReader;

    @Autowired
    private IUserManager userManager;
    
    @Autowired
    private IPassThroughProjectManager passThroughProjectManager;
    
    @Autowired
    private ProjectAuthorization authorization;

    @Autowired
    private IWorkspaceManager wsManager;
    
    @Autowired
    private IRestVelocityFactory restVelocityFactory;


    @RequestMapping(value = "rest/projects", method = RequestMethod.POST, produces = "application/xml")
    public ResponseEntity<String> createIfNotExistsProjectAndWorkspace(HttpServletRequest request, @RequestHeader("Accept") String accept,
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

        /*
         * Parse XML
         */
        XMLInfo xmlInfo;
        try {
            xmlInfo = xmlReader.getXMLInfo(xml);
        } catch (DocumentParserException e) {
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        String internalOrExternalProjectId = xmlInfo.getProjectId();
        String workspaceId = xmlInfo.getExternalWorkspaceId();
        String networkName = xmlInfo.getNetworkName();
        
        // check if necessary information is provided
        if (workspaceId == null || workspaceId.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide a workspace id as a part of the XML.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        if (internalOrExternalProjectId == null || internalOrExternalProjectId.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide a project id as a part of the XML.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        if (networkName == null || networkName.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide a network name as a part of the XML.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        
        /*
         * Create or retrieve project.
         */
        IProject project;
        try {
            project = passThroughProjectManager.retrieveOrCreateProject(xmlInfo, user);
        } catch (NoSuchRoleException | QuadrigaStorageException e2) {
            String errorMsg = e2.getMessage();
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        /*
         * check accessibility of project
         */
        String roles[] = { RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN,
                RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR };
        boolean isAuthorized;
        try {
            isAuthorized = authorization.chkAuthorization(userid, project.getProjectId(), roles);
        } catch (QuadrigaStorageException | QuadrigaAccessException e1) {
            String errorMsg = errorMessageRest.getErrorMsg("User is not authorized to access the resource");
            return new ResponseEntity<String>(errorMsg, HttpStatus.UNAUTHORIZED);
        }
        if (!isAuthorized) {
            String errorMsg = errorMessageRest.getErrorMsg("User is not authorized to access the resource");
            return new ResponseEntity<String>(errorMsg, HttpStatus.UNAUTHORIZED);
        }
        
        /*
         * Create or retrieve workspace
         */
        String projectIdOfWorkspace = null;
        try {
            workspaceId = passThroughProjectManager.retrieveOrCreateWorkspace(xmlInfo, project.getProjectId(), user);
            projectIdOfWorkspace = wsManager.getProjectIdFromWorkspaceId(workspaceId);
            
        } catch (JAXBException | QuadrigaStorageException | QuadrigaAccessException e1) {
            String errorMsg = e1.getMessage();
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        if (!projectIdOfWorkspace.equals(project.getProjectId())) {
            String errorMsg = errorMessageRest.getErrorMsg("The workspace belongs to a differen project than the one you specified.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        
        /*
         * Create response.
         */
        StringWriter writer = new StringWriter();
        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();

            Template template = engine.getTemplate("velocitytemplates/passthroughproject.vm");
            VelocityContext context = new VelocityContext();
            context.put("url", ServletUriComponentsBuilder.fromContextPath(request).toUriString());
            
            context.put("projectId", project.getProjectId());
            context.put("workspaceId", workspaceId);
            context.put("projectName", xmlInfo.getName());
            context.put("workspaceName", xmlInfo.getWorkspaceName());
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
