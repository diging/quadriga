package edu.asu.spring.quadriga.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.transform.Node;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * Controller for concept related rest api's exposed to other clients Client
 * 
 * @author Suraj Nilapwar
 * 
 */
@Controller
public class SearchNetworkRestController {

    private static final Logger logger = LoggerFactory.getLogger(SearchNetworkRestController.class);

    @Autowired
    private IRestMessage errorMessageRest;

    @Autowired
    private IRestVelocityFactory restVelocityFactory;

    @Autowired
    private IRetrieveProjectManager projectManager;

    @Autowired
    private INetworkTransformationManager transformationManager;
    
    @Autowired
    private IConceptpowerConnector conceptpowerConnector;

    /**
     * Rest interface to search concept in list of projects http://<<URL>:
     * <PORT>>/quadriga/rest/networks/search
     * http://localhost:8080/quadriga/rest/networks/search?conceptId=http://www.
     * digitalhps.org/concepts/CON516ec8c2-20bc-4f35-ae1e-ad908db9d662&
     * projectIds=PROJfFnfnn,PROJFgGPpk
     * 
     * @param conceptId:
     *            ConceptId for searching node
     * @param projectIds:
     *            List of comma separated projectId
     * @param response
     * @param accept
     * @param principal
     * @return status
     * @throws RestException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 2, userRole = {
            RoleNames.ROLE_COLLABORATOR_OWNER, RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "rest/network/search", method = RequestMethod.GET, produces = "application/xml")
    public ResponseEntity<String> getSearchTransformedNetwork(@RequestParam("conceptId") String conceptId,
            @RequestParam("projectIds") List<String> projectIds, HttpServletResponse response, String accept,
            Principal principal, HttpServletRequest req) throws RestException {

        List<IProject> projectList = new ArrayList<>();

        for (String projectId : projectIds) {
            IProject project = null;
            try {
                project = projectManager.getProjectDetails(projectId);
            } catch (QuadrigaStorageException e) {
                logger.error("QuadrigaStorageException:", e);
            }
            if (project != null) {
                projectList.add(project);
            }
        }

        if (projectList.size() == 0) {
            String errorMsg = errorMessageRest.getErrorMsg("Projects don't exist.");
            return new ResponseEntity<String>(errorMsg, HttpStatus.NOT_FOUND);
        }

        // Fetch ConceptPower entries related to the conceptId
        ConceptpowerReply reply = conceptpowerConnector.getById(conceptId);
        List<String> alternativeIdsForConcept = null;
        if (reply != null && reply.getConceptEntry().size() > 0) {
            alternativeIdsForConcept = reply.getConceptEntry().get(0).getAlternativeIdList();
        }
        if(alternativeIdsForConcept == null){
            alternativeIdsForConcept = new ArrayList<String>();
        }  
        ITransformedNetwork transformedNetwork;
        try {
            transformedNetwork = transformationManager.getSearchTransformedNetworkMultipleProjects(projectIds,
                    conceptId, alternativeIdsForConcept, INetworkStatus.APPROVED);
        } catch (QuadrigaStorageException e) {
            throw new RestException(403, e);
        }

        if (transformedNetwork == null) {
            throw new RestException(404);
        }

        try {
            VelocityEngine engine = restVelocityFactory.getVelocityEngine();
            engine.init();
            Template template = engine.getTemplate("velocitytemplates/transformationdetails.vm");
            VelocityContext context = new VelocityContext();
            context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
            if (transformedNetwork.getNodes() != null && transformedNetwork.getNodes().size() != 0) {
                context.put("nodeList", new ArrayList<Node>(transformedNetwork.getNodes().values()));
                context.put("linkList", transformedNetwork.getLinks());
            }
            context.put("graphId", conceptId);
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_XML);
            return new ResponseEntity<String>(writer.toString(), httpHeaders, HttpStatus.OK);
        } catch (IOException e) {
            throw new RestException(500, e);
        } catch (ResourceNotFoundException e) {
            throw new RestException(404, e);
        } catch (ParseErrorException e) {
            throw new RestException(500, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }
    }
}
