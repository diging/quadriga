package edu.asu.spring.quadriga.web.rest;

import java.io.IOException;
import java.io.StringWriter;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItem;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceDictionaryManager;

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
	private IWorkspaceDictionaryManager workspaceDictionaryManager;
	
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
	@Qualifier("updateFromWordPowerURLPath")
	private String updateFromWordPowerURLPath;
	
	@Autowired
	@Qualifier("wordPowerURL")
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

	/**
	 * Rest interface for uploading XML for dictionaries
	 * http://<<URL>:<PORT>>/quadriga/rest/uploaddictionaries
	 * hhttp://localhost:8080/quadriga/rest/uploaddictionaries
	 * 
	 * @author Lohith Dwaraka
	 * @param request
	 * @param response
	 * @param xml
	 * @param accept
	 * @return
	 * @throws QuadrigaException
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	@ResponseBody
	@RequestMapping(value = "rest/uploaddictionaries", method = RequestMethod.POST)
	public String getDictXMLFromVogon(HttpServletRequest request,
			HttpServletResponse response, @RequestBody String xml,
			@RequestHeader("Accept") String accept) throws QuadrigaException, ParserConfigurationException, SAXException, IOException {

		if (xml.equals("")) {
			response.setStatus(500);
			return "Please provide XML in body of the post request.";

		} else {
			if (accept != null && accept.equals("application/xml")) {
			}
			response.setStatus(200);
			response.setContentType(accept);
			return "";
		}
	}
	
	
	/**
	 * Rest interface for the List Dictionary for the userId
	 * http://<<URL>:<PORT>>/quadriga/rest/workspace/<workspaceID>/dictionaries
	 * hhttp://localhost:8080/quadriga/rest/workspace/WS_23048829469196290/dictionaries
	 * 
	 * @author Lohith Dwaraka
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/workspace/{workspaceId}/dictionaries", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listWorkspaceDictionaries(@PathVariable("workspaceId") String workspaceId,ModelMap model, Principal principal, HttpServletRequest req)
			throws Exception {
		UserDetails user = (UserDetails) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		List<IDictionary> dictionaryList = null;
		VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);

		Template template = null;

		try {
			engine.init();
			dictionaryList = workspaceDictionaryManager.listWorkspaceDictionary(workspaceId, user.getUsername());
			template = engine
					.getTemplate("velocitytemplates/dictionarylist.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("list", dictionaryList);
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
		List<IDictionaryItem> dictionaryItemsList = null;
		VelocityEngine engine = restVelocityFactory.getVelocityEngine(req);

		Template template = null;

		try {
			engine.init();
			logger.debug("Getting dictionary items list for dictionary id : "
					+ dictionaryId);
			dictionaryItemsList = dictionaryManager
					.getDictionariesItems(dictionaryId,user.getUsername());
			if( dictionaryItemsList == null){
				throw new RestException(404);
			}
			template = engine
					.getTemplate("velocitytemplates/dictionaryitemslist.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			String updateFromWordPowerURL=wordPowerURL;
			context.put("list", dictionaryItemsList);
			context.put("wordPowerURL", updateFromWordPowerURL);
			context.put("path", updateFromWordPowerURLPath);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block

			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
			throw new RestException(404);
		}
	}
}
