package edu.asu.spring.quadriga.web.rest;

import java.io.StringWriter;
import java.security.Principal;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * Controller for project related the rest apis exposed to other clients
 * 
 * @author SatyaSwaroop Boddu
 * @author LohithDwaraka
 * 
 */

@Controller
public class ProjectRestController {

	private static final Logger logger = LoggerFactory
			.getLogger(ProjectRestController.class);

	@Autowired
	IUserManager usermanager;

	@Autowired
	IProject project;

	@Autowired
	IProjectManager projectManager;

	@Autowired
	IRestVelocityFactory restVelocityFactory;

	/**
	 * Rest interface to List projects for a @userId
	 * 
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/projects", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listProjects(ModelMap model, Principal principal)
			throws Exception {
		List<IProject> projectList = null;
		VelocityEngine engine = restVelocityFactory.getVelocityEngine();
		Template template = null;
		try {
			engine.init();
			String userId = principal.getName();
			projectList = projectManager.getProjectsOfUser(userId);
			template = engine.getTemplate("velocitytemplates/projectlist.vm");
			VelocityContext context = new VelocityContext();
			context.put("list", projectList);

			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
		}

		return "";
	}
}
