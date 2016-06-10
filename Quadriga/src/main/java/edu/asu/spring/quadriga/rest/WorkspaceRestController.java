/**
 * 
 */
package edu.asu.spring.quadriga.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.impl.workspacexml.QuadrigaWorkspaceDetailsReply;
import edu.asu.spring.quadriga.domain.impl.workspacexml.Workspace;
import edu.asu.spring.quadriga.domain.impl.workspacexml.WorkspacesList;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;

/**
 * @author satyaswaroop boddu
 *
 */
@Controller
public class WorkspaceRestController {

    private static final Logger logger = LoggerFactory.getLogger(WorkspaceRestController.class);
    @Autowired
    private IRestVelocityFactory restVelocityFactory;
    @Autowired
    private IWorkspaceFactory workspaceFactory;

    @Autowired
    private IRestMessage errorMessageRest;

    @Autowired
    IUserManager userManager;

    @Autowired
    IListWSManager wsManager;

    @Autowired
    IModifyWSManager modifyWSManager;

    /**
     * Rest interface for the getting list of workspaces of a project http://<
     * <URL>:<PORT>>/quadriga/rest/projects/{project_id}/workspaces
     * http://localhost:8080/quadriga/rest/projects/1/workspaces
     * 
     * @author SatyaSwaroop Boddu
     * 
     * @param project_id
     * @param model
     * @param principal
     * @param req
     * @return
     * @throws RestException
     */
    @RequestMapping(value = "rest/projects/{project_id}/workspaces", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> listWorkspaces(@PathVariable("project_id") String project_id, ModelMap model,
            Principal principal, HttpServletRequest req) throws RestException {

        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();
            // will use in future list workspaces need to be modified
            String userId = principal.getName();
            List<IWorkSpace> workspaceList = wsManager.listWorkspace(project_id, userId);

            Template template = engine.getTemplate("velocitytemplates/workspaces.vm");
            VelocityContext context = new VelocityContext();
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
            context.put("list", workspaceList);

            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return new ResponseEntity<String>(writer.toString(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(500, e);
        } catch (MethodInvocationException e) {
            throw new RestException(500, e);
        } catch (QuadrigaStorageException e) {
            throw new RestException(500, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }
    }

    /**
     * Rest interface for the getting list of workspaces of a project http://<
     * <URL>:<PORT>>/quadriga/rest/workspaces/{workspaces_id}
     * http://localhost:8080/quadriga/rest/workspaces/95082053023825920
     * 
     * @author SatyaSwaroop Boddu
     * @param workspaces_id
     * @param model
     * @param principal
     * @param req
     * @return
     * @throws RestException
     */
    @RequestMapping(value = "rest/workspaces/{workspaces_id}", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> workspaceDetails(@PathVariable("workspaces_id") String workspaces_id, ModelMap model,
            Principal principal, HttpServletRequest req) throws RestException {

        try {
            // will use in future list workspaces need to be modified
            // List<IDictionary> dictionaryList
            // =workspaceDictionaryManager.listWorkspaceDictionary(workspaces_id,
            // userId);
            // List<IConceptCollection> ccList =
            // workspaceCCManager.listWorkspaceCC(workspaces_id, userId);
            // workspace = wsManager.getWorkspaceDetails(workspaces_id,userId);
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();

            Template template = engine.getTemplate("velocitytemplates/workspacesdetails.vm");
            VelocityContext context = new VelocityContext();
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
           
            context.put("workspaceid", workspaces_id);
            // context.put("cclist", ccList);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return new ResponseEntity<String>(writer.toString(), HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(500, e);
        } catch (MethodInvocationException e) {
            throw new RestException(500, e);
        } catch (QuadrigaStorageException e) {
            throw new RestException(500, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }
    }

    /**
     * Rest interface add a new workspace to the project http://<<URL>:
     * <PORT>>/quadriga/rest/projects/{project_id}/createworkspace
     * http://localhost:8080/quadriga/rest/projects/{project_id}/createworkspace
     * 
     * @author Lohith Dwaraka
     * @param userId
     * @param model
     * @return
     * @throws RestException
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     * @throws Exception
     */
    @RequestMapping(value = "rest/projects/{project_id}/workspace/add", method = RequestMethod.POST)
    public ResponseEntity<String> addWorkspaceToProject(@PathVariable("project_id") String projectId,
            HttpServletRequest request, HttpServletResponse response, @RequestBody String xml,
            @RequestHeader("Accept") String accept, ModelMap model, Principal principal)
                    throws RestException, QuadrigaStorageException, QuadrigaAccessException {
        
        logger.debug("XML : " + xml);
        JAXBElement<QuadrigaWorkspaceDetailsReply> response1 = null;
        try {
            JAXBContext context = JAXBContext.newInstance(QuadrigaWorkspaceDetailsReply.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            response1 = unmarshaller.unmarshal(new StreamSource(is), QuadrigaWorkspaceDetailsReply.class);
        } catch (JAXBException e) {
            logger.error("Error in unmarshalling", e);
            String errorMsg = errorMessageRest.getErrorMsg("Error in unmarshalling", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        QuadrigaWorkspaceDetailsReply qReply = response1.getValue();
        WorkspacesList workList = qReply.getWorkspacesList();
        List<Workspace> workspaceList = workList.getWorkspaceList();
        if (workspaceList.size() < 1) {
            String errorMsg = errorMessageRest.getErrorMsg("Workspace XML is not valid", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        IWorkSpace workspaceNew = workspaceFactory.createWorkspaceObject();
        for (Workspace workspace : workspaceList) {
            workspaceNew.setDescription(workspace.getDescription().trim());
            workspaceNew.setWorkspaceName(workspace.getName().trim());
            modifyWSManager.addWorkspaceToProject(workspaceNew, projectId, principal.getName());
        }

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(accept));
        return new ResponseEntity<String>("success", httpHeaders, HttpStatus.OK);
    }
}
