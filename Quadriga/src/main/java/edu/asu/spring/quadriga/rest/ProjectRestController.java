package edu.asu.spring.quadriga.rest;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;

/**
 * Controller for project related the rest apis exposed to other clients
 * 
 * @author SatyaSwaroop Boddu
 * @author LohithDwaraka
 * 
 */

@Controller
public class ProjectRestController {

	@Autowired
	private IRetrieveProjectManager projectManager;

	@Autowired
	private IRestVelocityFactory restVelocityFactory;

	/**
	 * Rest interface to List projects for a @userId
	 * 
	 * @param userId
	 * @param model
	 * @return
	 * @throws RestException
	 */
	@RequestMapping(value = "rest/projects", method = RequestMethod.GET, produces = "application/xml")
	public ResponseEntity<String> listProjects(ModelMap model, Principal principal, HttpServletRequest req)
			throws RestException {
		try {
		    VelocityEngine engine = restVelocityFactory.getVelocityEngine();
			engine.init();
			String userId = principal.getName();
			List<IProject> projectList = projectManager.getProjectList(userId);
			Template template = engine.getTemplate("velocitytemplates/projectlist.vm");
			VelocityContext context = new VelocityContext();
			context.put("url", ServletUriComponentsBuilder.fromContextPath(req).toUriString());
			context.put("list", projectList);
			
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return new ResponseEntity<String>(writer.toString(), HttpStatus.OK);
		} catch (ResourceNotFoundException e) {
			throw new RestException(404, e);
		} catch (ParseErrorException e) {
			throw new RestException(500, e);
		} catch (MethodInvocationException e) {
			throw new RestException(500, e);
		} catch (QuadrigaStorageException e) {
		    throw new RestException(500, e);
        } catch (Exception e) {
            throw new RestException(500, e);
        }
	}
}
