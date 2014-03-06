package edu.asu.spring.quadriga.web.editing;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
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
import edu.asu.spring.quadriga.domain.implementation.NetworkAnnotation;
import edu.asu.spring.quadriga.dto.NetworksAnnotationsDTO;
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

	/**
	 * This method saves the annotation entered to the node
	 * @param request
	 * @param response
	 * @param networkId
	 * @param annotationText
	 * @param id
	 * @param objectType
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
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
			logger.debug("Came to Save Annotation : object type :" + objectType + " id : " +id);
			editingNetworkAnnotationManager.addAnnotationToNetwork(networkId, id,
					annotationText, user.getUserName(),objectType);
			
		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		logger.debug("Annotation added to the node:" + id);

		return "{ success: true; }";
	}
	
	/**
	 * This method retrieves the annotations associated with the node
	 * @param request
	 * @param response
	 * @param networkId
	 * @param nodeId
	 * @param type
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JSONException 
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/auth/editing/getAnnotation/{networkId}", method = RequestMethod.GET)
	public @ResponseBody String getAnnotationtoToNode(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			@RequestParam("nodeid") String nodeId,
			@RequestParam("type") String type, 
			//@RequestParam("objecttype") String objectType, 
			Principal principal) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUserDetails(principal.getName());
		logger.info("network ID:" + networkId);
		String annotation = "";
		
		try {
			List<NetworksAnnotationsDTO> resultList = editingNetworkAnnotationManager.getAnnotation(type,nodeId,user.getUserName(),networkId);
			JSONArray ja = new JSONArray();
			JSONObject j1 = new JSONObject();
			if(resultList != null || resultList.size() > 0){
				
				for (int i = 0; i < resultList.size(); i++) {
					JSONObject j = new JSONObject();
					j.put("name", resultList.get(i).getAnnotationtext());
					ja.put(j);
				}
				j1.put("text", ja);
			annotation = j1.toString();
			}
			
		
		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		logger.info("json::" +annotation);
		return annotation;
	}
		
	
	@SuppressWarnings("null")
	@RequestMapping(value = "/auth/editing/getAllAnnotations/{networkId}", method = RequestMethod.GET)
	public @ResponseBody String getAllAnnotationsInNetwork(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			Principal principal) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUserDetails(principal.getName());
		logger.info("network ID:" + networkId);
		String annotation = "";
		
		try {
			List<NetworkAnnotation> resultList = editingNetworkAnnotationManager.getAllAnnotationOfNetwork(user.getUserName(), networkId);
			JSONArray ja = new JSONArray();
			JSONObject j1 = new JSONObject();
			if(resultList != null || resultList.size() > 0){
				
				for (int i = 0; i < resultList.size(); i++) {
					JSONObject j = new JSONObject();
					j.put("name", resultList.get(i).getNodeName());
					j.put("text", resultList.get(i).getAnnotationText());
					ja.put(j);
				}
				j1.put("text", ja);
			annotation = j1.toString();
			}
			
		
		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		logger.info("json::" +annotation);
		return annotation;
	}
	
	

}

