package edu.asu.spring.quadriga.web.rest;

import java.io.StringWriter;
import java.security.Principal;
import java.util.Iterator;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.implementation.Dictionary;
import edu.asu.spring.quadriga.domain.implementation.DictionaryItems;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * Controller for dictionary related rest apis exposed to other clients
 * 
 * @author SatyaSwaroop Boddu
 * @author LohithDwaraka
 * 
 */

@Controller
public class DictionaryRestController {

	@Autowired
	private IDictionaryManager dictonaryManager;

	private static final Logger logger = LoggerFactory
			.getLogger(DictionaryRestController.class);

	@Autowired
	private IUserManager usermanager;

	@Autowired
	private IDictionaryManager dictionaryManager;

	@Autowired
	private IDictionaryFactory dictionaryFactory;

	@Autowired
	private DictionaryItemsFactory dictionaryItemsFactory;

	@Autowired
	private IRestVelocityFactory restVelocityFactory;

	@Autowired
	@Qualifier("updateFromWordPowerURL")
	private String wordPowerURL;

	/**
	 * Rest interface for the List Dictionary for the userId
	 * http://<<URL>:<PORT>>/quadriga/rest/dictionaries
	 * http://localhost:8080/quadriga/rest/dictionaries
	 * 
	 * @author Lohith Dwaraka
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/dictionaries", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listDictionaries(ModelMap model, Principal principal, HttpServletRequest req)
			throws Exception {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		List<IDictionary> dictionaryList = null;
		VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);

		Template template = null;

		try {
			engine.init();
			dictionaryList = dictionaryManager.getDictionariesList(user.getUsername());
			template = engine
					.getTemplate("velocitytemplates/dictionarylist.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("list", dictionaryList);
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

	/**
	 * Rest interface for the List Dictionary items for the dictionary Id
	 * http://<<URL>:<PORT>>/quadriga/rest/dictionaryDetails/{DictionaryID}
	 * http://localhost:8080/quadriga/rest/dictionaryDetails/68
	 * 
	 * @author Lohith Dwaraka
	 * @param dictionaryId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/dictionaryDetails/{dictionaryId}", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listDictionaryItems(
			@PathVariable("dictionaryId") String dictionaryId, ModelMap model, HttpServletRequest req)
					throws Exception {

		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		List<IDictionaryItems> dictionaryItemsList = null;
		VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);

		Template template = null;

		try {
			engine.init();
			logger.debug("Getting dictionary items list for dictionary id : "
					+ dictionaryId);
			dictionaryItemsList = dictionaryManager
					.getDictionariesItems(dictionaryId,user.getUsername());
			if( dictionaryItemsList == null){
				throw new RestException("User does not have permission to access dictionary id :"+dictionaryId);
			}
			template = engine
					.getTemplate("velocitytemplates/dictionaryitemslist.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());

			context.put("list", dictionaryItemsList);
			context.put("wordPowerURL",wordPowerURL);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
			throw new RestException("Internal issue");
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block

			logger.error("Exception:", e);
			throw new RestException("Internal issue");
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
			throw new RestException("Internal issue");
		}
	}
}
