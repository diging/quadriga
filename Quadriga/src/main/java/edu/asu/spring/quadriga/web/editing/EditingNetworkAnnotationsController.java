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
import edu.asu.spring.quadriga.dto.NetworkAnnotationsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IEditingNetworkAnnotationManager;
import edu.asu.spring.quadriga.service.IEditorManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.network.INetworkManager;

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
			@RequestParam("nodeid") String nodeId,
			@RequestParam("nodename") String nodeName,
			@RequestParam("objecttype") String objectType, 
			Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUser(principal.getName());
		try {
			editingNetworkAnnotationManager.addAnnotationToNetwork(networkId, nodeId, nodeName,
					annotationText, user.getUserName(),objectType);

		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
			return "{ success: false; }";
		}

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
			@RequestParam("objecttype") String objectType, 
			Principal principal) throws QuadrigaStorageException, JSONException {
		String annotation = "";

		try {
			
			List<NetworkAnnotationsDTO> resultList = editingNetworkAnnotationManager.getAnnotation(objectType,nodeId,networkId);
			JSONArray ja = new JSONArray();
			JSONObject j1 = new JSONObject();
			if(resultList != null || resultList.size() > 0){
				logger.info("resultList is not null or size 0");
				for (int i = 0; i < resultList.size(); i++) {
					JSONObject j = new JSONObject();
					logger.info("resultList.get(i).getAnnotationText() :"+resultList.get(i).getAnnotationText());
					j.put("name", resultList.get(i).getAnnotationText());
					ja.put(j);
				}
				j1.put("text", ja);
				annotation = j1.toString();
			}else{
				annotation = j1.toString();
			}


		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		return annotation;
	}
	/**
	 * This method retrieves the annotations associated with the edge.
	 * @param request
	 * @param response
	 * @param networkId
	 * @param edgeId
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws JSONException 
	 */
	@SuppressWarnings("null")
	@RequestMapping(value = "/auth/editing/getAnnotationOfEdge/{networkId}", method = RequestMethod.GET)
	public @ResponseBody String getAnnotationForToEdge(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			@RequestParam("sourceid") String sourceId, 
			@RequestParam("targetid") String targetId,
			Principal principal) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUser(principal.getName());
		String annotation = "";

		try {
			List<NetworkAnnotationsDTO> resultList = editingNetworkAnnotationManager.getAnnotationOfEdge(sourceId,targetId,user.getUserName(),networkId);
			JSONArray ja = new JSONArray();
			JSONObject j1 = new JSONObject();
			if(resultList != null || resultList.size() > 0){

				for (int i = 0; i < resultList.size(); i++) {
					JSONObject j = new JSONObject();
					j.put("name", resultList.get(i).getAnnotationText());
					ja.put(j);
				}
				j1.put("text", ja);
				annotation = j1.toString();
			}else{
				annotation = j1.toString();
			}


		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		return annotation;
	}

	@RequestMapping(value = "/auth/editing/getAllAnnotations/{networkId}", method = RequestMethod.GET)
	public @ResponseBody String getAllAnnotationsInNetwork(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			Principal principal) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUser(principal.getName());
		String annotation = "";

		annotation = editingNetworkAnnotationManager.getAllAnnotationOfNetworkAsJson(user.getUserName(), networkId);
		return annotation;
	}

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
	@RequestMapping(value = "/auth/editing/saveAnnotationToEdge/{networkId}", method = RequestMethod.POST)
	public @ResponseBody String saveAnnotationtoToEdge(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			@RequestParam("annottext") String annotationText,
			@RequestParam("sourceid") String sourceId,
			@RequestParam("targetid") String targetId, 
			@RequestParam("sourcename") String sourceName,
			@RequestParam("targetename") String targetName,
			@RequestParam("objecttype") String objectType, 
			@RequestParam("targettype") String targetType,
			Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUser(principal.getName());
		
		
		try {
			editingNetworkAnnotationManager.addAnnotationToEdge(networkId, sourceId, targetId,sourceName,
					targetName,annotationText, user.getUserName(),objectType,targetType);

		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}

		return "{ success: true; }";
	}
	
	/**
	 * This method gets the annotations entered to the edge
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
	@SuppressWarnings("null")
	@RequestMapping(value = "/auth/editing/getAnnotationOfRelation/{networkId}", method = RequestMethod.GET)
	public @ResponseBody String getAnnotationForToRelation(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			@RequestParam("predicateid") String predicateId, 
			@RequestParam("subjectid") String subjectId, 
			@RequestParam("objectid") String objectId, 
			Principal principal) throws QuadrigaStorageException, JSONException {
		IUser user = userManager.getUser(principal.getName());
		String annotation = "";

		try {
			List<NetworkAnnotationsDTO> resultList = editingNetworkAnnotationManager.getAnnotationToRelation(networkId,subjectId,objectId,predicateId,user.getUserName());
			JSONArray ja = new JSONArray();
			JSONObject j1 = new JSONObject();
			if(resultList != null || resultList.size() > 0){

				for (int i = 0; i < resultList.size(); i++) {
					JSONObject j = new JSONObject();
					j.put("name", resultList.get(i).getAnnotationText());
					ja.put(j);
				}
				j1.put("text", ja);
				annotation = j1.toString();
			}else{
				annotation = j1.toString();
			}


		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}
		return annotation;
	}
	
	@RequestMapping(value = "/auth/editing/saveAnnotationToRelation/{networkId}", method = RequestMethod.POST)
	public @ResponseBody String saveAnnotationtoToRelation(HttpServletRequest request,
			HttpServletResponse response,
			@PathVariable("networkId") String networkId,
			@RequestParam("annotText") String annotationText,
			@RequestParam("subjectid") String subjectId,
			@RequestParam("subjectname") String subjectName, 
			@RequestParam("predicateid") String predicateId,
			@RequestParam("predicatename") String predicateName,
			@RequestParam("objectid") String objectId,
			@RequestParam("objectname") String objectName,
			@RequestParam("objecttype") String objectType, 
			@RequestParam("targettype") String targetType,
			Principal principal) throws QuadrigaStorageException {
		IUser user = userManager.getUser(principal.getName());
		try {
			editingNetworkAnnotationManager.addAnnotationToRelation(annotationText,networkId, predicateId, predicateName,subjectId,
					subjectName,objectId,objectName, user.getUserName(),objectType);

		} catch (QuadrigaStorageException e) {
			logger.error("Some issue in the DB", e);
		}

		return "{ success: true; }";
	}

	
}

