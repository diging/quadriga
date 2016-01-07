package edu.asu.spring.quadriga.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkAnnotation;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IEditingNetworkAnnotationManager;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * Controller for networks related rest api's exposed to other clients Client
 * could add, manipulate or get details of {@link INetwork}
 * 
 * @author Lohith Dwaraka
 * 
 */
@Controller
public class NetworkRestController {

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private IRestMessage errorMessageRest;

    @Autowired
    private IListWSManager wsManager;

    @Autowired
    private IEditorManager editorManager;

    @Autowired
    private IEditingNetworkAnnotationManager editingNetworkAnnoManager;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @Autowired
    private IUserManager userManager;

    /**
     * Rest interface for uploading XML for networks http://<<URL>:
     * <PORT>>/quadriga/rest/uploadnetworks
     * http://localhost:8080/quadriga/rest/uploadnetworks
     * 
     * @author Lohith Dwaraka
     * @param request
     * @param response
     * @param xml
     * @param accept
     * @return XML
     * @throws QuadrigaException
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws JAXBException
     * @throws TransformerException
     * @throws QuadrigaStorageException
     * @throws RestException
     */
    @RequestMapping(value = "rest/uploadnetworks", method = RequestMethod.POST)
    public ResponseEntity<String> getNetworkFromClients(HttpServletRequest request, HttpServletResponse response,
            @RequestBody String xml, @RequestHeader("Accept") String accept, Principal principal)
                    throws QuadrigaException, ParserConfigurationException, SAXException, IOException, JAXBException,
                    TransformerException, QuadrigaStorageException, RestException {

        IUser user = userManager.getUser(principal.getName());
        String networkName = request.getParameter("networkname");
        String networkId = "";
        String workspaceid = request.getParameter("workspaceid");

        if (workspaceid == null || workspaceid.isEmpty()) {
            String errorMsg = errorMessageRest
                    .getErrorMsg("Please provide a workspace id as a part of post parameters");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        String projectid = networkManager.getProjectIdForWorkspaceId(workspaceid);
        if (projectid == null || projectid.isEmpty()) {
            String errorMsg = errorMessageRest
                    .getErrorMsg("No project could be found for the given workspace id " + workspaceid);
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }

        if (networkName == null || networkName.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide network name as a part of post parameters");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        xml = xml.trim();
        if (xml.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide XML in body of the post request.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);

        }

        String res = networkManager.storeXMLQStore(xml);
        if (res.equals("")) {
            String errorMsg = "Please provide correct XML in body of the post request. Qstore system is not accepting your XML";
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        networkId = networkManager.storeNetworkDetails(res, user, networkName, workspaceid, INetworkManager.NEWNETWORK,
                networkId, INetworkManager.VERSION_ZERO);

        // TODO: Send email to all editors of a workspace
        // TODO: Get the workspace editors from the workspaceid

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(accept));
        httpHeaders.set("networkid", networkId);
        return new ResponseEntity<String>(res, httpHeaders, HttpStatus.OK);

    }

    /**
     * Rest interface for getting status of network http://<<URL>:
     * <PORT>>/quadriga/rest/networkstatus/{NetworkId}
     * http://localhost:8080/quadriga/rest/networkstatus/NET_1huxp4w7p71o0
     * 
     * @param networkName
     * @param response
     * @param accept
     * @param principal
     * @return status
     * @throws Exception
     */
    @RequestMapping(value = "rest/networkstatus/{NetworkId}", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getNetworkStatus(@PathVariable("NetworkId") String networkId,
            HttpServletResponse response, String accept, Principal principal, HttpServletRequest req) throws Exception {
        INetwork network = networkManager.getNetwork(networkId);
        if (network == null) {
            String errorMsg = errorMessageRest.getErrorMsg("Network ID : " + networkId + " doesn't exist");
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }
        IUser user = userManager.getUser(principal.getName());
        VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
        Template template = null;
        String status = networkManager.getNetworkStatusCode(network.getStatus()) + "";
        try {
            engine.init();

            template = engine.getTemplate("velocitytemplates/networkstatus.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            if (network.getStatus() == null)
                context.put("status", INetworkStatus.UNKNOWN_CODE + "");
            else
                context.put("status", status);
            context.put("networkid", networkId);
            List<INetworkAnnotation> networkAnnoList = editingNetworkAnnoManager
                    .getAllAnnotationOfNetwork(user.getUserName(), networkId);
            context.put("networkAnnoList", networkAnnoList);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(400, e);
        } catch (MethodInvocationException e) {
            throw new RestException(400, e);
        }

    }

    /**
     * REST API for getting annotations of network http://<<URL>:
     * <PORT>>/quadriga/rest/network/annotations/{NetworkId}
     * http://localhost:8080/quadriga/rest/network/annotations/dcb6b7bf-ba6b-
     * 40d7-a067-e1edf3460daa
     * 
     * @param networkId
     *            {@link INetwork} ID of type {@link String}
     * @param response
     *            {@link HttpServletResponse} object to set the status and
     *            response
     * @param accept
     *            Type of MIME accepted
     * @param principal
     *            {@link Principal} object to obtain the user details
     * @param req
     *            {@link HttpServletRequest} object to access any user params
     * @return XML with list of annotations belonging to a Network
     * @throws Exception
     *             Throws the exception for any issue in the Velocity engine
     */
    @RequestMapping(value = "rest/network/{NetworkId}/annotations", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getAnnotationOfNetwork(@PathVariable("NetworkId") String networkId,
            HttpServletResponse response, String accept, Principal principal, HttpServletRequest req) throws Exception {
        INetwork network = networkManager.getNetwork(networkId);
        if (network == null) {
            String errorMsg = errorMessageRest.getErrorMsg("Network ID : " + networkId + " doesn't exist");
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }
        IUser user = userManager.getUser(principal.getName());
        VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
        Template template = null;
        try {
            engine.init();

            template = engine.getTemplate("velocitytemplates/networkannotations.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("networkId", networkId);
            List<INetworkAnnotation> networkAnnoList = editingNetworkAnnoManager
                    .getAllAnnotationOfNetwork(user.getUserName(), networkId);
            context.put("networkAnnoList", networkAnnoList);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(400, e);
        } catch (MethodInvocationException e) {
            throw new RestException(400, e);
        }

    }

    /**
     * Rest interface for getting all the network list belonging to a workspace
     * http://<<URL>:<PORT>>/quadriga/rest/workspace/{workspaceid}/networks
     * http://localhost:8080/quadriga/rest/workspace/WS_23092339551502339/
     * networks
     * 
     * @param networkName
     * @param response
     * @param accept
     * @param principal
     * @return status
     * @throws Exception
     */
    @RequestMapping(value = "rest/workspace/{workspaceid}/networks", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getWorkspaceNetworkList(@PathVariable("workspaceid") String workspaceId,
            HttpServletResponse response, String accept, Principal principal, HttpServletRequest req) throws Exception {
        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, principal.getName());
        if (workspace == null) {
            String errorMsg = errorMessageRest.getErrorMsg("Workspace ID : " + workspaceId + " doesn't exist");
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }

        List<IWorkspaceNetwork> workspaceNetworkList = wsManager.getWorkspaceNetworkList(workspaceId);
        workspaceNetworkList = networkManager.editWorkspaceNetworkStatusCode(workspaceNetworkList);
        VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
        Template template = null;
        try {
            engine.init();
            template = engine.getTemplate("velocitytemplates/approvednetworks.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("workspaceNetworkList", workspaceNetworkList);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(400, e);
        } catch (MethodInvocationException e) {
            throw new RestException(400, e);
        }
    }

    /**
     * Rest interface for getting rejected network list belonging to a workspace
     * http://<<URL>:
     * <PORT>>/quadriga/rest/workspace/{workspaceid}/rejectednetworks
     * http://localhost:8080/quadriga/rest/workspace/WS_23092339551502339/
     * rejectednetworks
     * 
     * @param networkName
     * @param response
     * @param accept
     * @param principal
     * @return status
     * @throws Exception
     */
    @RequestMapping(value = "rest/workspace/{workspaceid}/rejectednetworks", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getWorkspaceRejectedNetworkList(@PathVariable("workspaceid") String workspaceId,
            HttpServletResponse response, String accept, Principal principal, HttpServletRequest req) throws Exception {

        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, principal.getName());
        if (workspace == null) {
            String errorMsg = errorMessageRest.getErrorMsg("Workspace ID : " + workspaceId + " doesn't exist");
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }

        List<IWorkspaceNetwork> workspaceNetworkList = wsManager.getWorkspaceRejectedNetworkList(workspaceId);
        workspaceNetworkList = networkManager.editWorkspaceNetworkStatusCode(workspaceNetworkList);
        VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
        Template template = null;
        try {
            engine.init();
            template = engine.getTemplate("velocitytemplates/rejectednetworks.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("workspaceNetworkList", workspaceNetworkList);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(400, e);
        } catch (MethodInvocationException e) {
            throw new RestException(400, e);
        }
    }

    /**
     * Rest interface for getting accepted network list belonging to a workspace
     * http://<<URL>:
     * <PORT>>/quadriga/rest/workspace/{workspaceid}/approvednetworks
     * http://localhost:8080/quadriga/rest/workspace/WS_23092339551502339/
     * approvednetworks
     * 
     * @param networkName
     * @param response
     * @param accept
     * @param principal
     * @return status
     * @throws Exception
     */
    @RequestMapping(value = "rest/workspace/{workspaceid}/approvednetworks", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getWorkspaceApprovedNetworkList(@PathVariable("workspaceid") String workspaceId,
            HttpServletResponse response, String accept, Principal principal, HttpServletRequest req) throws Exception {

        IWorkSpace workspace = wsManager.getWorkspaceDetails(workspaceId, principal.getName());
        if (workspace == null) {
            String errorMsg = errorMessageRest.getErrorMsg("Workspace ID : " + workspaceId + " doesn't exist");
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }

        List<IWorkspaceNetwork> workspaceNetworkList = wsManager.getWorkspaceApprovedNetworkList(workspaceId);
        workspaceNetworkList = networkManager.editWorkspaceNetworkStatusCode(workspaceNetworkList);
        VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
        Template template = null;
        try {
            engine.init();
            template = engine.getTemplate("velocitytemplates/approvednetworks.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("workspaceNetworkList", workspaceNetworkList);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(400, e);
        } catch (MethodInvocationException e) {
            throw new RestException(400, e);
        }
    }

    /**
     * Rest interface for getting network XML for a particular Network ID.
     * http://<<URL>:<PORT>>/quadriga/rest/network/xml/{networkid}
     * http://localhost:8080/quadriga/rest/network/xml/NET_1huxp4w7p71o0
     * 
     * @param networkName
     * @param response
     * @param accept
     * @param principal
     * @return status
     * @throws Exception
     */
    @RequestMapping(value = "rest/network/{networkid}", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getNetworkXmlFromQstore(@PathVariable("networkid") String networkId,
            HttpServletResponse response, String accept, Principal principal, HttpServletRequest req) throws Exception {

        INetwork network = networkManager.getNetwork(networkId);
        if (network == null) {
            String errorMsg = errorMessageRest.getErrorMsg("Network ID : " + networkId + " doesn't exist");
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }

        String networkXML = networkManager.getNetworkXML(networkId);
        String status = networkManager.getNetworkStatusCode(network.getStatus()) + "";
        if (networkXML.isEmpty() || networkXML == null) {
            throw new RestException(404);
        }
        VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
        Template template = null;
        try {
            engine.init();
            template = engine.getTemplate("velocitytemplates/networkinfo.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("status", status);
            context.put("networkxml", networkXML);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(400, e);
        } catch (MethodInvocationException e) {
            throw new RestException(400, e);
        }
    }

    @RequestMapping(value = "rest/mynetworks", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getMyNetwork(HttpServletResponse response, String accept, Principal principal,
            HttpServletRequest req) throws Exception {

        IUser user = userManager.getUser(principal.getName());

        List<INetwork> networkList = networkManager.getNetworksOfOwner(user);
        networkList = networkManager.editNetworkStatusCode(networkList);
        VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
        Template template = null;
        try {
            engine.init();
            template = engine.getTemplate("velocitytemplates/mynetworks.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("networkList", networkList);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(400, e);
        } catch (MethodInvocationException e) {
            throw new RestException(400, e);
        }
    }

    /**
     * Rest interface for getting network list belonging to a workspace http://<
     * <URL>:<PORT>>/quadriga/rest/networkdetails/{networkid}
     * http://localhost:8080/quadriga/rest/networkdetails/NET_1huxp4w7p71o0
     * 
     * @param networkName
     * @param response
     * @param accept
     * @param principal
     * @return status
     * @throws Exception
     */
    @RequestMapping(value = "rest/network/{networkid}/all", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getNetworkDetails(@PathVariable("networkid") String networkId,
            HttpServletResponse response, String accept, Principal principal, HttpServletRequest req) throws Exception {

        INetwork network = networkManager.getNetwork(networkId);
        if (network == null) {
            String errorMsg = errorMessageRest.getErrorMsg("Network ID : " + networkId + " doesn't exist");
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }

        IUser user = userManager.getUser(principal.getName());
        String networkXML = networkManager.getNetworkXML(networkId);
        String status = networkManager.getNetworkStatusCode(network.getStatus()) + "";
        if (networkXML.isEmpty() || networkXML == null) {
            throw new RestException(404);
        }
        VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
        Template template = null;
        try {
            engine.init();
            template = engine.getTemplate("velocitytemplates/networkdetails.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("status", status);
            context.put("networkxml", networkXML);
            context.put("networkAnnoList",
                    editingNetworkAnnoManager.getAllAnnotationOfNetwork(user.getUserName(), networkId));
            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(400, e);
        } catch (MethodInvocationException e) {
            throw new RestException(400, e);
        }

    }

    /**
     * Rest interface for re-upload a changed network ( which was rejected
     * earlier ) http://<<URL>:<PORT>>/quadriga/rest/reuploadnetwork/{networkid}
     * http://localhost:8080/quadriga/rest/reuploadnetwork/NET_1huxp4w7p71o0
     * 
     * @param networkId
     * @param response
     * @param accept
     * @param principal
     * @param req
     * @return
     * @throws JAXBException
     * @throws QuadrigaStorageException
     * @throws RestException
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    @RequestMapping(value = "rest/reuploadnetwork/{networkid}", method = RequestMethod.POST)
    public ResponseEntity<String> reuploadNetwork(@PathVariable("networkid") String networkId,
            HttpServletResponse response, HttpServletRequest request, @RequestBody String xml,
            @RequestHeader("Accept") String accept, Principal principal) throws JAXBException, QuadrigaStorageException,
                    RestException, ParserConfigurationException, SAXException, IOException {

        IUser user = userManager.getUser(principal.getName());

        String networkName = request.getParameter("networkname");

        if (networkId == null || networkId.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide network id.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }

        INetwork network = null;
        try {
            network = networkManager.getNetwork(networkId);
            if (network == null) {
                String errorMsg = errorMessageRest.getErrorMsg("Network ID : " + networkId + " doesn't exist");
                return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
            }
        } catch (QuadrigaStorageException e) {
            throw new RestException(500, e);
        }
        if (!(network.getStatus().equals(INetworkStatus.REJECTED))) {
            String errorMsg = errorMessageRest.getErrorMsg("The Network doesn't have status : REJECTED");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        xml = xml.trim();
        if (xml.isEmpty()) {
            String errorMsg = errorMessageRest.getErrorMsg("Please provide XML in body of the post request.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);

        }
        String res = networkManager.storeXMLQStore(xml);
        if (res.equals("")) {
            String errorMsg = errorMessageRest.getErrorMsg(
                    "Please provide correct XML in body of the post request. Qstore system is not accepting ur XML");
            return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);
        }
        String networkNameUpdateStatus = "";
        if (!(networkName == null || networkName.equals(network.getNetworkName()) || networkName.equals(""))) {
                networkNameUpdateStatus = networkManager.updateNetworkName(networkId, networkName);
            if (!(networkNameUpdateStatus.equals("success"))) {
                String errorMsg = errorMessageRest.getErrorMsg("DB Issue, please try after sometime");
                return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        // networkManager.archiveNetwork(networkId);
        editorManager.updateNetworkStatus(networkId, INetworkStatus.PENDING);
        int latestVersion = networkManager.getLatestVersionOfNetwork(networkId) + 1;
        networkId = networkManager.storeNetworkDetails(res, user, networkName,
                network.getNetworkWorkspace().getWorkspace().getWorkspaceId(), INetworkManager.UPDATENETWORK,
                networkId, latestVersion);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.valueOf(accept));
        httpHeaders.set("networkid", networkId);
        return new ResponseEntity<String>(res.toString(), httpHeaders, HttpStatus.OK);

    }

}
