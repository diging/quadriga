package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;
import edu.asu.spring.quadriga.web.network.INetworkStatus;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class EditingAssignUserController {

    @Autowired
    private INetworkManager networkManager;

    @Autowired
    private IEditorManager editorManager;

    @Autowired
    private IUserManager userManager;

    private static final Logger logger = LoggerFactory.getLogger(EditingAssignUserController.class);

    /**
     * Assign a network to User
     * 
     * @param networkId
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/editing/assignuser/{networkId}", method = RequestMethod.GET)
    public String assignNetworkToUser(@PathVariable("networkId") String networkId, ModelMap model, Principal principal)
            throws QuadrigaStorageException {
        IUser user = userManager.getUser(principal.getName());
        editorManager.assignNetworkToUser(networkId, user);
        editorManager.updateNetworkStatus(networkId, INetworkStatus.ASSIGNED);

        return "redirect:/auth/editing";
    }

    /**
     * List networks assigned to a User
     * 
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/editing/completed", method = RequestMethod.GET)
    public String listNetworksAssignedToUser(ModelMap model, Principal principal) throws QuadrigaStorageException {
        IUser user = userManager.getUser(principal.getName());

        List<INetwork> approvedNetworkList = editorManager.getApprovedNetworkOfUser(user);
        List<INetwork> rejectedNetworkList = editorManager.getRejectedNetworkOfUser(user);

        model.addAttribute("ApprovedNetworkList", approvedNetworkList);
        model.addAttribute("RejectedNetworkList", rejectedNetworkList);
        model.addAttribute("userId", user.getUserName());
        return "auth/editing/approvedrejectednetworks";
    }

    /**
     * List networks assigned to other Users
     * 
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/editing/assigned/others", method = RequestMethod.GET)
    public String listNetworksAssignedToOtherUser(ModelMap model, Principal principal) throws QuadrigaStorageException {
        IUser user = userManager.getUser(principal.getName());

        List<INetwork> networkList = editorManager.getAssignedNetworkListOfOtherEditors(user);

        model.addAttribute("networkList", networkList);
        model.addAttribute("userId", user.getUserName());
        return "auth/editing/networksAssginedToOtherUsers";
    }

    /**
     * Approve a network
     * 
     * @param networkId
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/editing/approvenetwork/{networkid}", method = RequestMethod.GET)
    public String approveNetwork(@PathVariable("networkid") String networkId, ModelMap model, Principal principal)
            throws QuadrigaStorageException {
        editorManager.updateNetworkStatus(networkId, INetworkStatus.APPROVED);
        editorManager.updateAssignedNetworkStatus(networkId, INetworkStatus.APPROVED);

        return "redirect:/auth/editing";
    }

    /**
     * Reject a submitted network
     * 
     * @param networkId
     * @param model
     * @param principal
     * @return
     * @throws QuadrigaStorageException
     */
    @RequestMapping(value = "auth/editing/rejectnetwork/{networkid}", method = RequestMethod.GET)
    public String rejectNetwork(@PathVariable("networkid") String networkId, ModelMap model, Principal principal)
            throws QuadrigaStorageException {

        editorManager.updateNetworkStatus(networkId, INetworkStatus.REJECTED);
        editorManager.updateAssignedNetworkStatus(networkId, INetworkStatus.REJECTED);

        return "redirect:/auth/editing";
    }
}
