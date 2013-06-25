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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * Controller for conception collection rest apis exposed to other clients
 * @author SatyaSwaroop Boddu
 * @author LohithDwaraka
 *
 */

@Controller
public class ConceptCollectionRestController {

	@Autowired
	IDictionaryManager dictonaryManager;

	private static final Logger logger = LoggerFactory
			.getLogger(ConceptCollectionRestController.class);

	@Autowired
	IUserManager usermanager;

	@Autowired
	IConceptCollectionManager conceptControllerManager;

	IConceptCollection collection;
	@Autowired
	IConceptCollectionFactory collectionFactory;

	IConcept concept;
	@Autowired
	IConceptFactory conFact;

	@Autowired
	IRestVelocityFactory restVelocityFactory;

	/**
	 * Rest interface for the getting list of concept collections of a user
	 * http://<<URL>:<PORT>>/quadriga/rest/conceptcollections
	 * http://localhost:8080/quadriga/rest/conceptcollections
	 * 
	 * @author SatyaSwaroop Boddu
	 * @param userId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/conceptcollections", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listConceptCollections(ModelMap model, Principal principal)
			throws Exception {
		List<IConceptCollection> collectionsList = null;
		VelocityEngine engine = restVelocityFactory.getVelocityEngine();
		Template template = null;

		try {
			engine.init();
			String userId = principal.getName();
			collectionsList = conceptControllerManager
					.getCollectionsOwnedbyUser(userId);
			template = engine
					.getTemplate("velocitytemplates/conceptcollections.vm");
			VelocityContext context = new VelocityContext();
			context.put("list", collectionsList);
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
	 * Rest interface for the getting concept details i.e, list of items in the
	 * collection
	 * http://<<URL>:<PORT>>/quadriga/rest/conceptdetails/{collectionID}
	 * http://localhost:8080/quadriga/rest/conceptdetails/167
	 * 
	 * @author SatyaSwaroop Boddu
	 * @param collectionID
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/conceptdetails/{collectionID}", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String getConceptList(
			@PathVariable("collectionID") int collectionID, ModelMap model)
			throws Exception {
		VelocityEngine engine = restVelocityFactory.getVelocityEngine();
		Template template = null;
		StringWriter sw = new StringWriter();
		collection = collectionFactory.createConceptCollectionObject();
		collection.setId(collectionID);
		try {
			engine.init();
			conceptControllerManager.getCollectionDetails(collection);
			template = engine
					.getTemplate("velocitytemplates/conceptdetails.vm");
			VelocityContext context = new VelocityContext();
			context.put("list", collection.getItems());
			template.merge(context, sw);
			return sw.toString();
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
