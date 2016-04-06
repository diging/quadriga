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

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
/**
 * This class will list all the networks approved by the user and 
 * display approved networks and some dummy transformations for
 * the data
 * 
 *  @author: Jaydatta Nagarkar.
 * */
import edu.asu.spring.quadriga.web.login.RoleNames;

@Controller
public class TransformationAssignUserController {
    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private IEditorManager editorManager;

    @Autowired
    private IUserManager userManager;

    @Autowired
    private IProject projectManager;

    private static final Logger logger = LoggerFactory
            .getLogger(TransformationAssignUserController.class);

    /**
     * List networks assigned to a User
     * 
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 1, userRole = {
            RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR,
            RoleNames.ROLE_PROJ_COLLABORATOR_ADMIN }) })
    @RequestMapping(value = "auth/transformation", method = RequestMethod.GET)
    private String listTransformations(ModelMap model, Principal principal)
            throws QuadrigaStorageException {
        IUser user = userManager.getUser(principal.getName());

        List<String> dummyTransformations = new ArrayList<String>();
        Set<IProject> projects = new HashSet<>();
        Map<String, List<INetwork>> networkMap = new HashMap<>();
        List<INetwork> approvedNetworkList = null;
        int flash = 0;
        try {
            approvedNetworkList = editorManager.getApprovedNetworkOfUser(user);
        } catch (QuadrigaStorageException e) {
            flash = 1;
            logger.error("Error fetching list of approved networks", e);
        }

        for (INetwork network : approvedNetworkList) {
            IProject project = network.getNetworkWorkspace().getWorkspace()
                    .getProjectWorkspace().getProject();
            if (networkMap.get(project.getProjectName()) == null) {
                networkMap.put(project.getProjectName(),
                        new ArrayList<INetwork>());
                projects.add(project);
            }
            networkMap.get(project.getProjectName()).add(network);
        }
        dummyTransformations.add("dummyData");
        dummyTransformations.add("dummyData2");
        dummyTransformations.add("dummyData3");
        dummyTransformations.add("dummyData4");
        dummyTransformations.add("dummyData5");
        dummyTransformations.add("dummyData6");
        model.addAttribute("flash", flash);
        model.addAttribute("projects", projects);
        model.addAttribute("networkMap", networkMap);
        model.addAttribute("dummyTransformations", dummyTransformations);

        return "auth/transformation";
    }
}