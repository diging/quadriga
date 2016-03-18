package edu.asu.spring.quadriga.web.transformation;

import java.security.Principal;
import java.util.ArrayList;
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


@Controller
public class TransformationAssignUserController {
	@Autowired
	INetworkManager networkManager;

	@Autowired
	IEditorManager editorManager;

	@Autowired
	IUserManager userManager;

	private static final Logger logger = LoggerFactory
			.getLogger(TransformationAssignUserController.class);


	/**
	 * List networks assigned to a User
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "auth/transformation", method = RequestMethod.GET)
	public String listNetworksAssignedToUser(ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUser(principal.getName());
		
		
		List<INetwork> approvedNetworkList=null;
		try{
			approvedNetworkList = editorManager.getApprovedNetworkOfUser(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}
		
		List<String> dummyTransformations;
		
		dummyTransformations = new ArrayList<String>();
		try{
			dummyTransformations.add("dummyData");
			dummyTransformations.add("dummyData2");
			dummyTransformations.add("dummyData3");
			dummyTransformations.add("dummyData4");
			dummyTransformations.add("dummyData5");
			dummyTransformations.add("dummyData6");
	
	
		}
		catch(Exception e){
			logger.error("Issue with dummy transformations");
		}

		model.addAttribute("ApprovedNetworkList", approvedNetworkList);
		model.addAttribute("userId", user.getUserName());
		model.addAttribute("dummyTransformations", dummyTransformations);
		return "auth/transformation";
	}

	
}
