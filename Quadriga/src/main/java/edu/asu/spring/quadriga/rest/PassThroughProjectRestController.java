package edu.asu.spring.quadriga.rest;

import java.io.IOException;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.impl.passthroughproject.PassThroughProjectInfo;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectDocumentReader;
import edu.asu.spring.quadriga.service.passthroughproject.IPassThroughProjectManager;

/**
 * 
 * Controller for pass through projects rest apis exposed to other clients
 *
 */
@Controller
public class PassThroughProjectRestController {

    @Autowired
    private IPassThroughProjectManager passThroughProjectManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IRestMessage errorMessageRest;

    @Autowired
    private IPassThroughProjectDocumentReader passThroughProjectDocumentReader;

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @RequestMapping(value = "rest/passthroughproject", method = RequestMethod.POST)
    public ResponseEntity<String> getPassThroughProject(HttpServletRequest request,
            @RequestHeader("Accept") String accept, HttpServletResponse response, @RequestBody String xml,
            Principal principal) throws RestException {

        try {
            xml = xml.trim();
            if (xml.isEmpty()) {
                String errorMsg = errorMessageRest.getErrorMsg("Please provide XML in body of the post request.");
                return new ResponseEntity<String>(errorMsg, HttpStatus.BAD_REQUEST);

            }
            String userid = principal.getName();

            IUser user = userManager.getUser(userid);
            PassThroughProjectInfo passThroughProjectInfo = passThroughProjectDocumentReader
                    .getPassThroughProjectInfo(xml);

            IPassThroughProject project = passThroughProjectManager.getPassThroughProject(passThroughProjectInfo,
                    userid);

            String projectId = passThroughProjectManager.savePassThroughProject(user, project);

            String workspaceId = passThroughProjectManager.createWorkspaceForExternalProject(passThroughProjectInfo,
                    projectId, user);

            String network = passThroughProjectDocumentReader.getNetwork(xml);

            String networkId = null;
            if (!StringUtils.isEmpty(network)) {
                String responseFromQStore = networkManager.storeNetworks(network);

                networkId = networkManager.storeNetworkDetails(responseFromQStore, user,
                        passThroughProjectInfo.getNetworkName(), workspaceId, INetworkManager.NEWNETWORK, "",
                        INetworkManager.VERSION_ZERO);
            }

            VelocityEngine engine = restVelocityFactory.getVelocityEngine(request);
            engine.init();

            Template template = engine.getTemplate("velocitytemplates/passthroughproject.vm");
            VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
            context.put("projectId", projectId);
            context.put("workspaceId", workspaceId);
            context.put("projectName", passThroughProjectInfo.getName());
            context.put("workspaceName", passThroughProjectInfo.getWorkspaceName());
            context.put("networkId", networkId);

            StringWriter writer = new StringWriter();
            template.merge(context, writer);

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);

            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);

        } catch (QStoreStorageException | DocumentParserException | QuadrigaStorageException | NoSuchRoleException
                | JAXBException | QuadrigaAccessException | IOException e) {
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            String errorMsg = errorMessageRest.getErrorMsg(e.getMessage());
            return new ResponseEntity<String>(errorMsg, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

}
