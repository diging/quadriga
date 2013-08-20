package edu.asu.spring.quadriga.web.rest;

import java.io.StringWriter;
import java.security.Principal;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * Controller for dictionary related rest apis exposed to other clients
 * 
 * @author SatyaSwaroop Boddu
 * @author LohithDwaraka
 * 
 */

@Controller
public class UserRestController {


	@Autowired
	private IRestVelocityFactory restVelocityFactory;
	
	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryRestController.class);

	@Autowired
	private IUserManager userManager;

	

	/**
	 * Rest interface to fetch the user details
	 * http://<<URL>:<PORT>>/quadriga/rest/userdetails/{username}
	 * http://localhost:8080/quadriga/rest/userdetails/test
	 * 
	 * @author Lohith Dwaraka
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/user", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String getUserDetails( ModelMap model, Principal principal, HttpServletRequest req)
			throws Exception {
		
		IUser userDetails = userManager.getUserDetails(principal.getName());
		VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
		
		Template template = null;
		try {
			engine.init();
			template = engine
					.getTemplate("velocitytemplates/userDetails.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("userdetails", userDetails);
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
			throw new RestException(404);
		}
	
	}
}
