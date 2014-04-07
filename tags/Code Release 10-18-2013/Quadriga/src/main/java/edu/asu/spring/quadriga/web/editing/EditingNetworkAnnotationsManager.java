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

import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class EditingNetworkAnnotationsManager {

	@Autowired
	IUserManager userManager;
	
	@Autowired
	IEditorManager editorManager;
	
	@Autowired
	INetworkManager networkManager;
	
	private static final Logger logger = LoggerFactory
			.getLogger(EditingNetworkAnnotationsManager.class);
	
	@RequestMapping(value = "auth/editing/saveAnnotation/{networkId}", method = RequestMethod.GET)
	public String saveAnnotationtoToNode(@PathVariable("networkId") String networkId,ModelMap model, Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		logger.info("network ID:"+networkId);
		
		/*model.addAttribute("userId", user.getUserName());
		
		try{
			 editorManager.getEditorNetworkList(user);
		}catch(QuadrigaStorageException e){
			logger.error("Some issue in the DB",e);
		}*/
		
		
		return "auth/editing";
	}
}