package edu.asu.spring.quadriga.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.accesschecks.IWSSecurityChecker;
import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.RestAccessPolicies;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptFactory;
import edu.asu.spring.quadriga.domain.impl.conceptcollection.ConceptCollection;
import edu.asu.spring.quadriga.domain.impl.conceptlist.Concept;
import edu.asu.spring.quadriga.domain.impl.conceptlist.ConceptList;
import edu.asu.spring.quadriga.domain.impl.conceptlist.QuadrigaConceptReply;
import edu.asu.spring.quadriga.domain.impl.workspacexml.Workspace;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceConceptCollection;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workbench.IProjectConceptCollectionManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * Controller for conception collection rest apis exposed to other clients
 * 
 * @author SatyaSwaroop Boddu
 * @author LohithDwaraka
 * 
 */

@Controller
public class ConceptCollectionRestController {

    private static final Logger logger = LoggerFactory.getLogger(ConceptCollectionRestController.class);

    @Autowired
    private IUserManager usermanager;

    @Autowired
    private IRestMessage restMessage;

    @Autowired
    private IWorkspaceCCManager workspaceCCManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IConceptCollectionFactory conceptCollectionFactory;
    @Autowired
    private IConceptCollectionManager conceptControllerManager;

    private IConceptCollection collection;

    @Autowired
    private IConceptCollectionFactory collectionFactory;

    @Autowired
    private IWSSecurityChecker checkWSSecurity;

    @Autowired
    private IConceptFactory conFact;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @Autowired
    @Qualifier("conceptPowerURL")
    private String conceptPowerURL;

    @Autowired
    @Qualifier("updateConceptPowerURLPath")
    private String updateConceptPowerURLPath;

    @Autowired
    private IProjectConceptCollectionManager projectConceptCollectionManager;

    /**
     * Rest interface for the getting list of concept collections of a user
     * http://<<URL>:<PORT>>/quadriga/rest/conceptcollections
     * http://localhost:8080/quadriga/rest/conceptcollections
     * 
     * @param model
     *            ModelMap to handle the requests of client
     * @param principal
     *            {@link Principal} object to access authenticated user
     * @param req
     *            {@link HttpServletRequest} object to access request param
     * @return Produces XML of list of concept collection details
     * @throws RestException
     */
    @RequestMapping(value = "rest/conceptcollections", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> listConceptCollections(ModelMap model, Principal principal, HttpServletRequest req)
            throws RestException {
        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();
            String userId = principal.getName();
            List<IConceptCollection> collectionsList = conceptControllerManager.getCollectionsOwnedbyUser(userId);
            Template template = engine.getTemplate("velocitytemplates/conceptcollections.vm");
            VelocityContext context = new VelocityContext();
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
            context.put("list", collectionsList);

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
     * Rest interface to get conceptcollections related to workspace
     * http://<<URL
     * >:<PORT>>/quadriga/auth/rest/workspace/{workspaceid}/conceptcollections
     * .json http://localhost:8080/quadriga/auth/rest/workspace/e23a8585-20bc-
     * 458e-ab7d-c758962b11aa/conceptcollections.json
     * 
     * 
     * @author Ajay Modi & Bharath Srikantan
     * @param workspaceid
     * @param model
     * @param principal
     * @return
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/rest/workspace/{workspaceid}/conceptcollections.json", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> listWorkspaceConceptCollection(@PathVariable("workspaceid") String workspaceId,
            Model model, Principal principal) {
        String userId = principal.getName();

        List<IWorkspaceConceptCollection> conceptCollectionList = null;
        JSONArray ja = new JSONArray();
        try {
            conceptCollectionList = workspaceCCManager.listWorkspaceCC(workspaceId);

        } catch (QuadrigaStorageException e) {
            logger.error("QuadrigaStorageException:", e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (conceptCollectionList != null) {

            for (IWorkspaceConceptCollection conceptCollection : conceptCollectionList) {
                JSONObject j = new JSONObject();
                try {
                    j.put("id", conceptCollection.getConceptCollection().getConceptCollectionId());
                    j.put("name", conceptCollection.getConceptCollection().getConceptCollectionName());
                    ja.put(j);

                } catch (JSONException e) {
                    logger.error("JSONException:", e);
                    return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        return new ResponseEntity<String>(ja.toString(), HttpStatus.OK);
    }

    /**
     * Rest interface for uploading XML for concept collection
     * http://<<URL>:<PORT>>/quadriga/rest/syncconcepts/{conceptCollectionID}
     * http://localhost:8080/quadriga/rest/syncconcepts/
     * 
     * @author Lohith Dwaraka
     * @param request
     *            {@link HttpServletRequest} object to access request URL param
     * @param response
     *            {@link HttpServletResponse} object to access response and
     *            setting status or/and header details
     * @param xml
     *            POST data in terms of XML from the client
     * @param accept
     *            Accepts data of type Application/XML
     * @return Returns of Application/XML to the client. XML contains concept
     *         which client doesn't have. It helps in syncing the concept at
     *         client and Quadriga.
     * @throws QuadrigaException
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws JAXBException
     * @throws QuadrigaAccessException
     * @throws QuadrigaStorageException
     * @throws RestException
     */
    @RestAccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION_REST, paramIndex = 1, userRole = {
            RoleNames.ROLE_CC_COLLABORATOR_ADMIN, RoleNames.ROLE_CC_COLLABORATOR_READ_WRITE }) })
    @RequestMapping(value = "rest/syncconcepts/{conceptCollectionID}", method = RequestMethod.POST)
    public ResponseEntity<String> addConceptsToConceptCollection(
            @PathVariable("conceptCollectionID") String conceptCollectionId, HttpServletRequest request,
            HttpServletResponse response, @RequestBody String xml, @RequestHeader("Accept") String accept,
            Principal principal) throws QuadrigaException, ParserConfigurationException, SAXException, IOException,
            JAXBException, QuadrigaAccessException, QuadrigaStorageException, RestException {
        IUser user = userManager.getUser(principal.getName());
        if (xml.equals("")) {
            String errorMsg = restMessage.getErrorMsg("Please provide XML in body of the post request.", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        logger.debug("XML : " + xml);
        JAXBElement<QuadrigaConceptReply> response1 = null;
        try {
            JAXBContext context = JAXBContext.newInstance(QuadrigaConceptReply.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            response1 = unmarshaller.unmarshal(new StreamSource(is), QuadrigaConceptReply.class);
        } catch (JAXBException e) {
            logger.error("Error in unmarshalling", e);
            String errorMsg = restMessage.getErrorMsg("Error in unmarshalling", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        QuadrigaConceptReply qReply = response1.getValue();
        ConceptList conList = qReply.getConceptList();
        List<Concept> conceptList = conList.getConcepts();

        Iterator<Concept> iter = conceptList.iterator();

        while (iter.hasNext()) {
            Concept concept = iter.next();
            logger.debug(concept.toString());
            try {
                conceptControllerManager.addItems(concept.getName(), concept.getUri(), concept.getPos(),
                        concept.getDescription(), conceptCollectionId, user.getUserName());
            } catch (QuadrigaStorageException e) {
                logger.error("Errors in adding items", e);
                String errorMsg = restMessage.getErrorMsg("Failed to add due to DB Error", request);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.valueOf(accept));
                return new ResponseEntity<String>(errorMsg, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(accept));
        return new ResponseEntity<String>("Success", httpHeaders, HttpStatus.OK);
    }

    /**
     *
     * Rest interface for the getting list of concept collections of a user
     * http://<<URL>:<PORT>>/quadriga/rest/workspace/
     * <workspaceid>/conceptcollections
     * http://localhost:8080/quadriga/rest/workspace/WS_22992652874022949/
     * conceptcollections
     * 
     * 
     * @param workspaceId
     *            {@link Workspace} ID , access this parameter to get concept
     *            collection of the {@link Workspace}
     * @param model
     *            {@link ModelMap} object to access any user request
     * @param principal
     *            {@link Principal} object to access logged in client.
     * @param req
     *            {@link HttpServletRequest} object to access the request params
     * @return Returns the Application/XML of concept collections belongs to
     *         {@link Workspace}
     * @throws RestException
     */
    @RestAccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE_REST, paramIndex = 1, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR,
            RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "rest/workspace/{workspaceId}/conceptcollections", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> listWorkspaceConceptCollections(@PathVariable("workspaceId") String workspaceId,
            ModelMap model, Principal principal, HttpServletRequest req) throws RestException {

        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();
            String userId = principal.getName();
            List<IWorkspaceConceptCollection> collectionsList = workspaceCCManager.listWorkspaceCC(workspaceId);
            Template template = engine.getTemplate("velocitytemplates/workspaceconceptcollections.vm");
            VelocityContext context = new VelocityContext();
            context.put("list", collectionsList);
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
            
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
     * Rest interface add a new {@link ConceptCollection} to the
     * {@link Workspace} http://<<URL>:<PORT>>/quadriga/rest/workspace/
     * <workspaceid>/createcc
     * http://localhost:8080/quadriga/rest/workspace/WS_22992652874022949/
     * createcc
     * 
     * @param workspaceId
     *            {@link Workspace} ID , access this parameter to get concept
     *            collection of the {@link Workspace}
     * @param request
     *            {@link HttpServletRequest} object to access the request params
     * @param response
     *            {@link HttpServletResponse} object to access response and
     *            setting status or/and header details
     * @param xml
     *            POST data in terms of XML from the client
     * @param accept
     *            Accepts data of type Application/XML
     * @param model
     *            {@link ModelMap} object to access any user request
     * @param principal
     *            {@link Principal} object to access logged in client.
     * @return Response of XML of success in creating the concept collection in
     *         the workspace.
     * @throws RestException
     * @throws QuadrigaStorageException
     * @throws QuadrigaAccessException
     */
    @RestAccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE_REST, paramIndex = 1, userRole = {
            RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR }) })
    @RequestMapping(value = "rest/workspace/{workspaceId}/createcc", method = RequestMethod.POST)
    public ResponseEntity<String> addConceptCollectionsToWorkspace(@PathVariable("workspaceId") String workspaceId,
            HttpServletRequest request, HttpServletResponse response, @RequestBody String xml,
            @RequestHeader("Accept") String accept, ModelMap model, Principal principal) throws RestException,
            QuadrigaStorageException, QuadrigaAccessException {
        IUser user = usermanager.getUser(principal.getName());
        String ccName = request.getParameter("name");
        String desc = request.getParameter("desc");

        if (!checkWSSecurity.checkIsWorkspaceExists(workspaceId)) {
            String errorMsg = restMessage.getErrorMsg("Workspace ID : " + workspaceId + " doesn't exist", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }

        if (ccName == null || ccName.isEmpty()) {
            String errorMsg = restMessage.getErrorMsg("Please provide concept collection name", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        if (desc == null || desc.isEmpty()) {
            String errorMsg = restMessage.getErrorMsg("Please provide concept collection description", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        logger.debug("XML : " + xml);
        JAXBElement<QuadrigaConceptReply> response1 = null;
        try {
            JAXBContext context = JAXBContext.newInstance(QuadrigaConceptReply.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
            InputStream is = new ByteArrayInputStream(xml.getBytes());
            response1 = unmarshaller.unmarshal(new StreamSource(is), QuadrigaConceptReply.class);
        } catch (JAXBException e) {
            logger.error("Error in unmarshalling", e);
            String errorMsg = restMessage.getErrorMsg("Error in unmarshalling", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        QuadrigaConceptReply qReply = response1.getValue();
        ConceptList conList = qReply.getConceptList();
        List<Concept> conceptList = conList.getConcepts();
        if (conceptList.size() < 1) {
            String errorMsg = restMessage.getErrorMsg("Concepts XML is not valid", request);
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        IConceptCollection collection = conceptCollectionFactory.createConceptCollectionObject();
        collection.setDescription(desc);
        collection.setOwner(user);
        collection.setConceptCollectionName(ccName);

        conceptControllerManager.addConceptCollection(collection);
        String ccId = collection.getConceptCollectionId();

        Iterator<Concept> iter = conceptList.iterator();

        while (iter.hasNext()) {
            Concept concept = iter.next();
            logger.debug(concept.toString());
            try {
                conceptControllerManager.addItems(concept.getName().trim(), concept.getUri().trim(), concept.getPos()
                        .trim(), concept.getDescription().trim(), ccId, user.getUserName());
            } catch (QuadrigaStorageException e) {
                logger.error("Errors in adding items", e);
                String errorMsg = restMessage.getErrorMsg("Failed to add due to DB Error", request);
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.setContentType(MediaType.valueOf(accept));
                return new ResponseEntity<String>(errorMsg, httpHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }

        workspaceCCManager.addWorkspaceCC(workspaceId, ccId, user.getUserName());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(accept));
        return new ResponseEntity<String>(ccId, httpHeaders, HttpStatus.OK);
    }

    /**
     * Rest interface for the getting concept details i.e, list of items in the
     * collection http://<<URL>:
     * <PORT>>/quadriga/rest/conceptdetails/{collectionID}
     * http://localhost:8080/quadriga/rest/conceptdetails/167
     * 
     * @author SatyaSwaroop Boddu
     * @param collectionID
     *            {@link ConceptCollection} ID to access the object details from
     *            the DB
     * @param model
     *            {@link ModelMap} object to access any user request
     * @return Response would be the XML containing the concepts belonging to
     *         {@link ConceptCollection} ID.
     * @throws RestException
     * @throws Exception
     */
    @RequestMapping(value = "rest/conceptdetails/{collectionID}", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getConceptList(@PathVariable("collectionID") String collectionID, ModelMap model,
            HttpServletRequest req, Principal principal) throws RestException {
        StringWriter sw = new StringWriter();
        IConceptCollection collection;
        try {
            collection = conceptControllerManager.getConceptCollection(collectionID);
        } catch (QuadrigaStorageException e1) {
            throw new RestException(500, e1);
        }
        collection.setConceptCollectionId(collectionID);
        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();
            Template template = engine.getTemplate("velocitytemplates/conceptdetails.vm");
            VelocityContext context = new VelocityContext();
            context.put("list", collection.getConceptCollectionConcepts());
            context.put("conceptPowerURL", conceptPowerURL);
            context.put("path", updateConceptPowerURLPath);
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
            
            template.merge(context, sw);
            return new ResponseEntity<String>(sw.toString(), HttpStatus.OK);

        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(500, e);
        } catch (MethodInvocationException e) {
            throw new RestException(500, e);
        } catch (QuadrigaStorageException e) {
            throw new RestException(500, e);
        } catch (QuadrigaAccessException e) {
            throw new RestException(403, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }

    }

    /**
     * Rest interface to get conceptcollections related to project
     * http://<<URL>:
     * <PORT>>/quadriga/auth/rest/<projectid>/conceptcollections.json
     * http://localhost
     * :8080/quadriga/auth/rest/PROJ_bb7ad41b-3e85-4309-b2ff-47d644307
     * b9b/conceptcollections.json
     * 
     * 
     * @author Ajay Modi & Bharath Srikantan
     * @param projectid
     * @param model
     * @param principal
     * @return
     */

    @RequestMapping(value = "auth/rest/{projectid}/conceptcollections.json", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<String> listProjectConceptCollectionJson(@PathVariable("projectid") String projectid,
            Model model, Principal principal) {
        String userId = principal.getName();
        List<IProjectConceptCollection> projectConceptCollectionList = null;
        // TODO: listProjectConceptCollection() needs to be modified
        try {
            projectConceptCollectionList = projectConceptCollectionManager.listProjectConceptCollection(projectid);
        } catch (QuadrigaStorageException e) {
            logger.error("QuadrigaStorageException:", e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        JSONArray ja = new JSONArray();

        if (projectConceptCollectionList != null) {
            for (IProjectConceptCollection conceptCollection : projectConceptCollectionList) {
                JSONObject j = new JSONObject();
                try {

                    j.put("id", conceptCollection.getConceptCollection().getConceptCollectionId());
                    j.put("name", conceptCollection.getConceptCollection().getConceptCollectionName());
                    ja.put(j);

                } catch (JSONException e) {
                    logger.error("JSONException:", e);
                    return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                }

            }

        }

        return new ResponseEntity<String>(ja.toString(), HttpStatus.OK);

    }
}
