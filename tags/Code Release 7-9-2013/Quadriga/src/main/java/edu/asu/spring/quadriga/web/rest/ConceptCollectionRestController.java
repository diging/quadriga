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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAcessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * Controller for conception collection rest apis exposed to other clients
 * @author SatyaSwaroop Boddu
 * @author LohithDwaraka
 *
 */

@Controller
public class ConceptCollectionRestController {


	private static final Logger logger = LoggerFactory
			.getLogger(ConceptCollectionRestController.class);

	@Autowired
	private IUserManager usermanager;

	@Autowired
	private IConceptCollectionManager conceptControllerManager;

	private IConceptCollection collection;
	@Autowired
	private IConceptCollectionFactory collectionFactory;

	private IConcept concept;
	@Autowired
	private IConceptFactory conFact;

	@Autowired
	private IRestVelocityFactory restVelocityFactory;

	@Autowired
	@Qualifier("updateConceptPowerURL")
	private String conceptPowerURL;
	
	/**
	 * Rest interface for the getting list of concept collections of a user
	 * http://<<URL>:<PORT>>/quadriga/rest/conceptcollections
	 * http://localhost:8080/quadriga/rest/conceptcollections
	 * 
	 * @author SatyaSwaroop Boddu
	 * @param userId
	 * @param model
	 * @return
	 * @throws RestException 
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/conceptcollections", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listConceptCollections(ModelMap model, Principal principal, HttpServletRequest req) throws RestException
			 {
		List<IConceptCollection> collectionsList = null;
		VelocityEngine engine = null;
		Template template = null;
		  
		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();
			String userId = principal.getName();
			collectionsList = conceptControllerManager
					.getCollectionsOwnedbyUser(userId);
			template = engine
					.getTemplate("velocitytemplates/conceptcollections.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("list", collectionsList);
			
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
		} catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
			throw new RestException(405);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("Exception:", e);
			throw new RestException(403);
		}

		
	}

	/**
	 * Rest interface for the getting concept details i.e, list of items in the
	 * collection
	 * http://<<URL>:<PORT>>/quadriga/rest/conceptdetails/{collectionID}
	 * http://localhost:8080/quadriga/rest/conceptdetails/167
	 * 
	 * @author SatyaSwaroop Boddu
	 * @param collectionID
	 * @param model
	 * @return
	 * @throws RestException 
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/conceptdetails/{collectionID}", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String getConceptList(
			@PathVariable("collectionID") int collectionID, ModelMap model, HttpServletRequest req, Principal principal) throws RestException
			{
		VelocityEngine engine=null;
		Template template = null;
		StringWriter sw = new StringWriter();
		collection = collectionFactory.createConceptCollectionObject();
		collection.setId(collectionID);
		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();
			conceptControllerManager.getCollectionDetails(collection, principal.getName());
			template = engine
					.getTemplate("velocitytemplates/conceptdetails.vm");
			VelocityContext context = new VelocityContext(restVelocityFactory.getVelocityContext());
			context.put("list", collection.getItems());
			context.put("conceptPowerURL",conceptPowerURL);
			template.merge(context, sw);
			return sw.toString();
		} catch (ResourceNotFoundException e) {
			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (ParseErrorException e) {
			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (MethodInvocationException e) {
			logger.error("Exception:", e);
			throw new RestException(404);
		} catch (QuadrigaStorageException e) {
			logger.error("Exception:", e);
			throw new RestException(405);
		} catch (QuadrigaAcessException e) {
			logger.error("Exception:", e);
			throw new RestException(403);
		} 
		catch (Exception e) {
			logger.error("Exception:", e);
			throw new RestException(405);
		}
		
	}

}
