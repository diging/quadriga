package edu.asu.spring.quadriga.web.network;

import java.security.Principal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.impl.NetworkManager;
import edu.asu.spring.quadriga.web.rest.NetworkRestController;

/**
 * This class will handle list dictionaries controller for the dictionary
 * 
 * @author : Lohith Dwaraka
 * 
 */
@Controller
public class NetworkListManager {
	
	@Autowired
	NetworkManager networkManager;
	
	@Autowired
	IUserManager userManager;
	
	private static final Logger logger = LoggerFactory
			.getLogger(NetworkListManager.class);

	@RequestMapping(value = "auth/networks", method = RequestMethod.GET)
	public String listDictionary(ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		List<INetwork> networkList=null;
		try{
		networkList=networkManager.getNetworkList(user);
		
		}catch(QuadrigaStorageException e){
			logger.error("Something wrong on DB Side",e);
		}
		
		model.addAttribute("networkList", networkList);
		model.addAttribute("userId", user.getUserName());
		
		return "auth/networks";
	}
}
