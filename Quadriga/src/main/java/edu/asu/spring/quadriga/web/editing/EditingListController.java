package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;
import java.util.List;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.ID3Creator;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.service.network.INetworkTransformationManager;
import edu.asu.spring.quadriga.service.network.domain.ITransformedNetwork;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class EditingListController {

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private INetworkTransformationManager transformationManager;

    @Autowired
    private ID3Creator d3Creator;

    @Autowired
    private IEditorManager editorManager;

    @Autowired
    private IUserManager userManager;

    private static final Logger logger = LoggerFactory.getLogger(EditingListController.class);

    /**
     * List of networks available to editor
     * 
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({
            @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 0, userRole = { RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR }),
            @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 0, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_EDITOR }),
            @ElementAccessPolicy(type = CheckedElementType.NETWORK, paramIndex = 0, userRole = {}) })
    @RequestMapping(value = "auth/editing", method = RequestMethod.GET)
    public String listNetworkAvailableToEditors(ModelMap model, Principal principal) throws QuadrigaStorageException,
            QuadrigaAccessException {
        IUser user = userManager.getUser(principal.getName());

        List<INetwork> assignedNetworkList = editorManager.getAssignNetworkOfUser(user);
        List<INetwork> networkList = editorManager.getEditorNetworkList(user);

        model.addAttribute("assignedNetworkList", assignedNetworkList);
        model.addAttribute("networkList", networkList);
        model.addAttribute("userId", user.getUserName());
        return "auth/editing";
    }

    /**
     * List of networks assigned to other editor
     * 
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({
            @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 0, userRole = { RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR }),
            @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 0, userRole = {
                    RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR }),
            @ElementAccessPolicy(type = CheckedElementType.NETWORK, paramIndex = 0, userRole = {}) })
    @RequestMapping(value = "auth/networksOtherEditors", method = RequestMethod.GET)
    public String listNetworkAssignedToOtherEditors(ModelMap model, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException {
        IUser user = userManager.getUser(principal.getName());
        List<INetwork> assignedNetworkList = editorManager.getAssignedNetworkListOfOtherEditors(user);
        
        model.addAttribute("assignedNetworkList", assignedNetworkList);
        model.addAttribute("userId", principal.getName());
        return "auth/editing";
    }

    /**
     * List of networks finished by other editor
     * 
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @AccessPolicies({
            @ElementAccessPolicy(type = CheckedElementType.PROJECT, paramIndex = 0, userRole = { RoleNames.ROLE_PROJ_COLLABORATOR_EDITOR }),
            @ElementAccessPolicy(type = CheckedElementType.WORKSPACE, paramIndex = 0, userRole = {
                    RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN, RoleNames.ROLE_WORKSPACE_COLLABORATOR_CONTRIBUTOR }),
            @ElementAccessPolicy(type = CheckedElementType.NETWORK, paramIndex = 0, userRole = {}) })
    @RequestMapping(value = "auth/finishednetworksOtherEditors", method = RequestMethod.GET)
    public String listFinishedNetworksByOtherEditors(ModelMap model, Principal principal)
            throws QuadrigaStorageException, QuadrigaAccessException {
        IUser user = userManager.getUser(principal.getName());
        List<INetwork> finishedNetworkList = editorManager.getfinishedNetworkListOfOtherEditors(user);
        
        model.addAttribute("finishedNetworkList", finishedNetworkList);
        model.addAttribute("userId", user.getUserName());
        return "auth/editing";
    }

    

    /**
     * Visualize old version of network based on the version number Get the
     * network displayed on to JSP by passing JSON string on editing page
     * 
     * @author Lohith Dwaraka
     * @param networkId
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     * @throws JAXBException
     */
    @RequestMapping(value = "/auth/editing/oldversionvisualize/{networkId}/{versionNo}", method = RequestMethod.GET)
    public String visualizeNetworksOldVersion(@PathVariable("networkId") String networkId,
            @PathVariable("versionNo") String versionNo, ModelMap model, Principal principal)
            throws QuadrigaStorageException, JAXBException {
        INetwork network = networkManager.getNetwork(networkId);
        if (network == null) {
            return "auth/accessissue";
        }
        ITransformedNetwork transformedNetwork = transformationManager.getTransformedNetwork(networkId, versionNo);
        String nwId = "\"" + networkId + "\"";
        model.addAttribute("networkid", nwId);
        String json = null;
        if (transformedNetwork != null) {
            json = d3Creator.getD3JSON(transformedNetwork.getNodes(), transformedNetwork.getLinks());
        }
        model.addAttribute("jsonstring", json);
        return "auth/editing/visualize";
    }

    /**
     * List all the versions of a particular network Displays a jsp that
     * contains a table with all the information about the different versions of
     * the network
     * 
     * @author Sayalee Mehendale
     * @param networkId
     *            id of the particular network
     * @param model
     *            Model object to map values to view
     * @param principal
     *            current session user
     * @return returns a string to access the page that displays the network
     *         history
     * @throws QuadrigaStorageException
     *             Database storage exception thrown
     */
    @RequestMapping(value = "auth/editing/versionhistory/{networkId}", method = RequestMethod.GET)
    public String viewHistory(@PathVariable("networkId") String networkId, ModelMap model, Principal principal)
            throws QuadrigaStorageException {
        INetwork network = networkManager.getNetwork(networkId);
        if (network == null) {
            return "auth/accessissue";
        }
        List<INetwork> networkVersions = networkManager.getAllNetworkVersions(networkId);

        if (networkVersions != null && !networkVersions.isEmpty()) {
            model.addAttribute("Versions", networkVersions);
            return "auth/editing/history";
        }

        return null;
    }

}
