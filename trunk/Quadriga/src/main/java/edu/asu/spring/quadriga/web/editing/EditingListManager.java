package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
public class EditingListManager {
	
	@Autowired
	INetworkManager networkManager;
	
	@Autowired
	IEditorManager editorManager;
	
	@Autowired
	IUserManager userManager;
	
	private static final Logger logger = LoggerFactory
			.getLogger(EditingListManager.class);

	/**
	 * List of networks available to editor
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "auth/editing", method = RequestMethod.GET)
	public String listNetworkAvailableToEditors(ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		
		List<INetwork> assignedNetworkList=null;
		try{
			assignedNetworkList = editorManager.getAssignNetworkOfUser(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		
		List<INetwork> networkList=null;
		try{
			networkList = editorManager.getEditorNetworkList(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		
		model.addAttribute("assignedNetworkList", assignedNetworkList);
		model.addAttribute("networkList", networkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/editing";
	}
	
	/**
	 * List of networks assigned to other editor
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "auth/networksOtherEditors", method = RequestMethod.GET)
	public String listNetworkAssignedToOtherEditors(ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		
		List<INetwork> assignedNetworkList=null;
		try{
			assignedNetworkList = editorManager.getAssignedNetworkListOfOtherEditors(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		
		model.addAttribute("assignedNetworkList", assignedNetworkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/editing";
	}
	
	/**
	 * List of networks finished by other editor
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "auth/finishednetworksOtherEditors", method = RequestMethod.GET)
	public String listFinishedNetworksByOtherEditors(ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		
		List<INetwork> finishedNetworkList=null;
		try{
			finishedNetworkList = editorManager.getfinishedNetworkListOfOtherEditors(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		
		model.addAttribute("finishedNetworkList", finishedNetworkList);
		model.addAttribute("userId", user.getUserName());
		return "auth/editing";
	}
}
