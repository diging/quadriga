/**
 * 
 */
package edu.asu.spring.quadriga.web.rest;

import java.io.StringWriter;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;

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
	IUserManager userManager;

	@Autowired
	IListWSManager wsManager;
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
						// TODO Auto-generated catch block
						logger.error("Exception:", e);
						throw new RestException(404);
					} catch (Exception e) {
						// TODO Auto-generated catch block
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
	@RequestMapping(value = "rest/workspaces/workspaces_id", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String workspaceDetails(@PathVariable("workspaces_id") String workspaces_id, ModelMap model, Principal principal, HttpServletRequest req) throws RestException
	{
		IWorkSpace workspace;
			VelocityEngine engine = null;
			Template template = null;
 
			try {
				//will use in future list workspaces need to be modified
				String userId = principal.getName();
				
				workspace = wsManager.getWorkspaceDetails(workspaces_id,userId);
					engine = restVelocityFactory.getVelocityEngine(req);
					engine.init();

					
					template = engine.getTemplate("velocitytemplates/workspaces.vm");
					VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
					context.put("workspace", workspace);
	
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
						// TODO Auto-generated catch block
						logger.error("Exception:", e);
						throw new RestException(403);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("Exception:", e);
						throw new RestException(404);
					}
	}
}
