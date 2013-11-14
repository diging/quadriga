package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.db.sql.DBConnectionEditorManager;
import edu.asu.spring.quadriga.db.sql.DBConnectionManager;
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

	@Autowired
	DBConnectionEditorManager dbConnectionEditManager;

	private static final Logger logger = LoggerFactory
			.getLogger(EditingNetworkAnnotationsManager.class);

	
	@RequestMapping(value = "/auth/editing/saveAnnotation/{networkId}", method = RequestMethod.POST)
	public @ResponseBody String saveAnnotationtoToNode(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			@RequestParam("annotText") String annotationText,
			@RequestParam("nodename") String nodeName, 
			//@RequestParam("objecttype") String objectType, 
			Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		logger.info("network ID:" + networkId);
		String objectType = "node";
		try {
			dbConnectionEditManager.addAnnotationToNetwork(networkId, nodeName,
					annotationText, user.getUserName(),objectType);
		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		logger.info("Annotation added to the node:" + nodeName);

		return "{ success: true; }";
	}
	
	@RequestMapping(value = "/auth/editing/saveAnnotation/{networkId}", method = RequestMethod.POST)
	public @ResponseBody String getAnnotationtoToNode(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			@RequestParam("nodeid") String nodeId,
			@RequestParam("type") String type, 
			//@RequestParam("objecttype") String objectType, 
			Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		logger.info("network ID:" + networkId);
		String annotation = "";
		
		try {
			annotation = dbConnectionEditManager.getAnnotation(type,nodeId);
		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		logger.info("Annotation added to the node:" + nodeId);
		return annotation;
	}
		
	
	
	
	

}
