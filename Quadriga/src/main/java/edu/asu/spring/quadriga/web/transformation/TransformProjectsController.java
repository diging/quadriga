package edu.asu.spring.quadriga.web.transformation;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.dto.TransformFilesDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.transformation.ITransformationManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This class will list all the networks approved by the user and display
 * approved networks and some dummy transformations for the data
 * 
 * @author: Jaydatta Nagarkar.
 * 
 */
@Controller
public class TransformProjectsController {
    @Autowired
    private ITransformationManager transformManager;

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private IRetrieveProjectManager retrieveProjectManager;

    @Autowired
    private IUserManager userManager;

    private static final Logger logger = LoggerFactory.getLogger(TransformProjectsController.class);

    /**
     * List networks assigned to a User
     * 
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/transformation", method = RequestMethod.GET)
    public String listTransformations(ModelMap model, Principal principal) throws QuadrigaStorageException {
        IUser user = userManager.getUser(principal.getName());
        Set<IProject> projects = new HashSet<>();
        Map<String, List<INetwork>> networkMap = new HashMap<>();
        List<IProject> retrievedProjects = new ArrayList<IProject>();
        try {
            retrievedProjects = retrieveProjectManager.getProjectList(user.getUserName());
            if (retrievedProjects == null) {
                retrievedProjects = new ArrayList<IProject>();
            }
        } catch (QuadrigaStorageException e) {
            logger.error("Error fetching list of approved networks", e);
            model.addAttribute("show_error_alert", true);
            model.addAttribute("error_alert_msg", "There was an error retrieving the list of approved networks.");
            return "auth/transformation";
        }
        List<INetwork> networks = new ArrayList<INetwork>();
        for (IProject retrievedProj : retrievedProjects) {
            networks.addAll(networkManager.getNetworksInProject(retrievedProj.getProjectId(), INetworkStatus.APPROVED));
        }

        List<INetwork> allNetworkList = new ArrayList<INetwork>();
        for (INetwork individualNetworks : networks) {
            if (individualNetworks.getStatus().equalsIgnoreCase("APPROVED")) {
                allNetworkList.add(individualNetworks);
            }
        }

        for (INetwork network : allNetworkList) {
            IProject project = network.getWorkspace().getProject();
            if (networkMap.get(project.getProjectName()) == null) {
                networkMap.put(project.getProjectName(), new ArrayList<INetwork>());
                projects.add(project);
            }
            networkMap.get(project.getProjectName()).add(network);
        }
        List<TransformFilesDTO> transformationsList = transformManager.getTransformationsList();
        model.addAttribute("projects", projects);
        model.addAttribute("networkMap", networkMap);
        model.addAttribute("transformationsList", transformationsList);

        return "auth/transformation";
    }
}