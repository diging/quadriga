/**
 * 
 */
package edu.asu.spring.quadriga.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.impl.workspacexml.QuadrigaWorkspaceDetailsReply;
import edu.asu.spring.quadriga.domain.impl.workspacexml.Workspace;
import edu.asu.spring.quadriga.domain.impl.workspacexml.WorkspacesList;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IRestMessage;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;
import edu.asu.spring.quadriga.service.workspace.IModifyWSManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;

/**
 * @author satyaswaroop boddu
 *
 */
@Controller
public class WorkspaceRestController {

	private static final Logger logger = LoggerFactory.getLogger(ConceptCollectionRestController.class);
	@Autowired
	private IRestVelocityFactory restVelocityFactory;
	@Autowired
	private IWorkspaceFactory workspaceFactory;

	@Autowired
	private IRestMessage errorMessageRest;
	
	@Autowired
	private IWorkspaceDictionaryManager workspaceDictionaryManager;
	
	@Autowired
	private IWorkspaceCCManager workspaceCCManager;
	
	@Autowired 
	IUserManager userManager;

	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	IModifyWSManager modifyWSManager;
	/**
	 * Rest interface for the getting list of workspaces of a project
	 * http://<<URL>:<PORT>>/quadriga/rest/projects/{project_id}/workspaces
	 * http://localhost:8080/quadriga/rest/projects/1/workspaces
	 * 
	 * @author SatyaSwaroop Boddu
	 * 
	 * @param project_id
	 * @param model
	 * @param principal
	 * @param req
	 * @return
	 * @throws RestException
	 */
	@RequestMapping(value = "rest/projects/{project_id}/workspaces", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listWorkspaces(@PathVariable("project_id") String project_id, ModelMap model, Principal principal, HttpServletRequest req) throws RestException
	{
		List<IWorkSpace> workspaceList = null;
		VelocityEngine engine = null;
		Template template = null;


		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();
			//will use in future list workspaces need to be modified
			String userId = principal.getName();
			workspaceList = wsManager.listActiveWorkspace(project_id,userId);

			template = engine.getTemplate("velocitytemplates/workspaces.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("list", workspaceList);

			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			logger.error("Exception:", e);
			throw new RestException(e);
		} catch (ParseErrorException e) {

			logger.error("Exception:", e);
			throw new RestException(403);
		} catch (MethodInvocationException e) {

			logger.error("Exception:", e);
			throw new RestException(403);
		} catch (QuadrigaStorageException e) {
			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RestException(403);
		}
	}
	/**
	 * Rest interface for the getting list of workspaces of a project
	 * http://<<URL>:<PORT>>/quadriga/rest/workspaces/{workspaces_id}
	 * http://localhost:8080/quadriga/rest/workspaces/95082053023825920
	 * 
	 * @author SatyaSwaroop Boddu
	 * @param workspaces_id
	 * @param model
	 * @param principal
	 * @param req
	 * @return
	 * @throws RestException
	 */
	@RequestMapping(value = "rest/workspaces/{workspaces_id}", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String workspaceDetails(@PathVariable("workspaces_id") String workspaces_id, ModelMap model, Principal principal, HttpServletRequest req) throws RestException
	{
		VelocityEngine engine = null;
		Template template = null;

		try {
			//will use in future list workspaces need to be modified
			//List<IDictionary> dictionaryList =workspaceDictionaryManager.listWorkspaceDictionary(workspaces_id, userId);
			//List<IConceptCollection> ccList = workspaceCCManager.listWorkspaceCC(workspaces_id, userId);
			//workspace = wsManager.getWorkspaceDetails(workspaces_id,userId);
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();


			template = engine.getTemplate("velocitytemplates/workspacesdetails.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			
			context.put("workspaceid", workspaces_id);
			//context.put("cclist", ccList);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (ParseErrorException e) {

			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (MethodInvocationException e) {

			logger.error("Exception:", e);
			throw new RestException(403);
		} catch (QuadrigaStorageException e) {
			logger.error("Exception:", e);
			throw new RestException(403);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RestException(404);
		}
	}
	
	
	/**
	 * Rest interface add a new workspace to the project
	 * http://<<URL>:<PORT>>/quadriga/rest/projects/{project_id}/createworkspace
	 * http://localhost:8080/quadriga/rest/projects/{project_id}/createworkspace
	 * 
	 * @author Lohith Dwaraka
	 * @param userId
	 * @param model
	 * @return
	 * @throws RestException 
	 * @throws QuadrigaStorageException 
	 * @throws QuadrigaAccessException 
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/projects/{project_id}/createworkspace", method = RequestMethod.POST)
	@ResponseBody
	public String addWorkspaceToProject(@PathVariable("project_id") String projectId,HttpServletRequest request,
			HttpServletResponse response, @RequestBody String xml,
			@RequestHeader("Accept") String accept, ModelMap model, Principal principal) throws RestException, QuadrigaStorageException, QuadrigaAccessException{
		IUser user = userManager.getUserDetails(principal.getName());
		
		
		logger.debug("XML : "+xml);
		JAXBElement<QuadrigaWorkspaceDetailsReply> response1=null;
		try{
			JAXBContext context = JAXBContext.newInstance(QuadrigaWorkspaceDetailsReply.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			response1 =  unmarshaller.unmarshal(new StreamSource(is), QuadrigaWorkspaceDetailsReply.class);
		}catch(Exception e ){
			logger.error("Error in unmarshalling",e);
			response.setStatus(404);
			String errorMsg = errorMessageRest.getErrorMsg("Failed to add due to DB Error",request);
			return errorMsg;
		}
		if(response1 == null){
			response.setStatus(404);
			return "Concepts XML is not valid";
		}
		QuadrigaWorkspaceDetailsReply qReply= response1.getValue();
		WorkspacesList w1 = qReply.getWorkspacesList();
		List<Workspace> workspaceList = w1.getWorkspaceList();
		if(workspaceList.size()<1){
			response.setStatus(404);
			String errorMsg = errorMessageRest.getErrorMsg("Workspace XML is not valid",request);
			return errorMsg;
		}
		IWorkSpace workspaceNew = workspaceFactory.createWorkspaceObject();
		for(Workspace workspace : workspaceList){
			logger.info("Description : "+workspace.getDescription().trim());
			logger.info("URI : "+workspace.getUri().trim());
			logger.info("Name : " +workspace.getName().trim());
			logger.info("ID : "+workspace.getId().trim());
			workspaceNew.setDescription(workspace.getDescription().trim());
			workspaceNew.setWorkspaceName(workspace.getName().trim());
			workspaceNew.setOwner(user);
			modifyWSManager.addWorkSpaceRequest(workspaceNew, projectId);
		}
		
		
		response.setStatus(200);
		response.setContentType(accept);
		return "success";
	}
}
