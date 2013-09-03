package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class EditingAssignUserManager {

	@Autowired
	INetworkManager networkManager;

	@Autowired
	IEditorManager editorManager;

	@Autowired
	IUserManager userManager;

	private static final Logger logger = LoggerFactory
			.getLogger(EditingAssignUserManager.class);

	@RequestMapping(value = "auth/editing/assignuser/{networkId}", method = RequestMethod.GET)
	public String assignUserToNetwork(@PathVariable("networkId") String networkId,ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		try{
			editorManager.assignNetworkToUser(networkId, user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		List<INetwork> networkList=null;
		try{
			networkList = editorManager.getEditorNetworkList(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}

		model.addAttribute("networkList", networkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/editing";
	}

	@RequestMapping(value = "auth/editing/assignnetworktouser", method = RequestMethod.GET)
	public String listNetworksAssignedToUser(ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		List<INetwork> assignedNetworkList=null;
		try{
			assignedNetworkList = editorManager.getAssignNetworkOfUser(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		
		List<INetwork> approvedNetworkList=null;
		try{
			approvedNetworkList = editorManager.getApprovedNetworkOfUser(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		
		List<INetwork> rejectedNetworkList=null;
		try{
			rejectedNetworkList = editorManager.getRejectedNetworkOfUser(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}

		model.addAttribute("AssignedNetworkList", assignedNetworkList);
		model.addAttribute("ApprovedNetworkList", approvedNetworkList);
		model.addAttribute("RejectedNetworkList", rejectedNetworkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/assignednetworks";
	}

	@RequestMapping(value = "auth/editing/approvenetwork/{networkid}", method = RequestMethod.GET)
	public String approveNetwork(@PathVariable ( "networkid") String networkId  ,ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());

		try{
			editorManager.updateNetworkStatus(networkId, "APPROVED");
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		List<INetwork> networkList=null;
		try{
			networkList = editorManager.getAssignNetworkOfUser(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}

		model.addAttribute("networkList", networkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/assignednetworks";
	}

	@RequestMapping(value = "auth/editing/rejectnetwork/{networkid}", method = RequestMethod.GET)
	public String rejectNetwork(@PathVariable ( "networkid") String networkId  ,ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());

		try{
			editorManager.updateNetworkStatus(networkId, "REJECTED");
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		List<INetwork> networkList=null;
		try{
			networkList = editorManager.getAssignNetworkOfUser(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}

		model.addAttribute("networkList", networkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/assignednetworks";
	}
}
