package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditingNetworkAnnotationManager;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class EditingNetworkAnnotationsController {

	@Autowired
	IUserManager userManager;

	@Autowired
	IEditorManager editorManager;

	@Autowired
	INetworkManager networkManager;
	
	@Autowired
	IEditingNetworkAnnotationManager editingNetworkAnnotationManager;
	

	private static final Logger logger = LoggerFactory
			.getLogger(EditingNetworkAnnotationsController.class);

	
	@RequestMapping(value = "/auth/editing/saveAnnotation/{networkId}", method = RequestMethod.POST)
	public @ResponseBody String saveAnnotationtoToNode(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			@RequestParam("annotText") String annotationText,
			@RequestParam("nodename") String id, 
			@RequestParam("type") String objectType, 
			Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUserDetails(principal.getName());
		logger.info("network ID:" + networkId);
		try {
			String arr[] = null;
			logger.debug("Came to Save Annotation : object type :" + objectType + " id : " +id);
			arr = editingNetworkAnnotationManager.getAnnotation(objectType,id , user.getUserName(),networkId);
			if(arr[0] == null && arr[1] == null){
				logger.debug("There is no annotation for this object");
				editingNetworkAnnotationManager.addAnnotationToNetwork(networkId, id,
					annotationText, user.getUserName(),objectType);
			} else {
				logger.debug("Updating the annoation "+ arr.toString());
				editingNetworkAnnotationManager.updateAnnotationToNetwork(arr[1], annotationText);
			}
			
		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		logger.debug("Annotation added to the node:" + id);

		return "{ success: true; }";
	}
	
	@RequestMapping(value = "/auth/editing/getAnnotation/{networkId}", method = RequestMethod.GET)
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
			String resultArr[] = null;
			resultArr = editingNetworkAnnotationManager.getAnnotation(type,nodeId,user.getUserName(),networkId);
			annotation = resultArr[0];
		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		return annotation;
	}
		
	
	
	
	

}
