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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.IUserProfileManager;

/**
 * Controller for {@link IUser} related rest APIs exposed to other clients
 * 
 * @author LohithDwaraka
 * 
 */

@Controller
public class UserRestController {

	@Autowired
	private IUserProfileManager profileManager;
	
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
	@RequestMapping(value = "rest/userdetails", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String getUserDetails( ModelMap model, Principal principal, HttpServletRequest req)
			throws Exception {
		
		IUser userDetails = userManager.getUser(principal.getName());
		VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);
		List<IProfile> authFiles = profileManager.getUserProfiles(userDetails.getUserName());
		Template template = null;
		try {
			engine.init();
			template = engine
					.getTemplate("velocitytemplates/userDetails.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("userdetails", userDetails);
			context.put("list", authFiles);
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
