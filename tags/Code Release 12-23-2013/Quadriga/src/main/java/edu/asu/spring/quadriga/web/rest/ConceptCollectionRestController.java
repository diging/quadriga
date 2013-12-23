package edu.asu.spring.quadriga.web.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.Principal;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.SAXException;

import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.aspects.annotations.RestAccessPolicies;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.impl.conceptlist.Concept;
import edu.asu.spring.quadriga.domain.impl.conceptlist.ConceptList;
import edu.asu.spring.quadriga.domain.impl.conceptlist.QuadrigaConceptReply;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.RestException;
import edu.asu.spring.quadriga.service.IErrorMessageRest;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.workspace.ICheckWSSecurity;
import edu.asu.spring.quadriga.service.workspace.IWorkspaceCCManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * Controller for conception collection rest apis exposed to other clients
 * 
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
	private IErrorMessageRest errorMessageRest;
	
	@Autowired
	private IWorkspaceCCManager workspaceCCManager;

	@Autowired
	private IUserManager userManager;

	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;
	@Autowired
	private IConceptCollectionManager conceptControllerManager;

	private IConceptCollection collection;
	@Autowired
	private IConceptCollectionFactory collectionFactory;

	@Autowired
	private ICheckWSSecurity checkWSSecurity;

	@Autowired
	private IConceptFactory conFact;

	@Autowired
	private IRestVelocityFactory restVelocityFactory;

	@Autowired
	@Qualifier("conceptPowerURL")
	private String conceptPowerURL;

	@Autowired
	@Qualifier("updateConceptPowerURLPath")
	private String updateConceptPowerURLPath;

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
	public String listConceptCollections(ModelMap model, Principal principal,
			HttpServletRequest req) throws RestException {
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
			VelocityContext context = new VelocityContext(
					restVelocityFactory.getVelocityContext());
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
			logger.error("Exception:", e);
			throw new RestException(405);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RestException(403);
		}

	}

	/**
	 * Rest interface for uploading XML for concept collection
	 * http://<<URL>:<PORT>>/quadriga/rest/syncconcepts/{conceptCollectionID}
	 * hhttp://localhost:8080/quadriga/rest/syncconcepts/
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
	 * @throws JAXBException
	 * @throws QuadrigaAccessException
	 * @throws QuadrigaStorageException
	 * @throws RestException 
	 */
	@RestAccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION_REST,paramIndex = 1, userRole = {RoleNames.ROLE_CC_COLLABORATOR_ADMIN,RoleNames.ROLE_CC_COLLABORATOR_RAD_WRITE} )})
	@ResponseBody
	@RequestMapping(value = "rest/syncconcepts/{conceptCollectionID}", method = RequestMethod.POST)
	public String addConceptsToConceptColleciton(
			@PathVariable("conceptCollectionID") String conceptCollectionId,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String xml, @RequestHeader("Accept") String accept,
			Principal principal) throws QuadrigaException,
			ParserConfigurationException, SAXException, IOException,
			JAXBException, QuadrigaAccessException, QuadrigaStorageException, RestException {
		IUser user = userManager.getUserDetails(principal.getName());
		if (xml.equals("")) {
			response.setStatus(404);
			String errorMsg = errorMessageRest.getErrorMsg("Please provide XML in body of the post request.",request);
			return errorMsg;
		} else {

			logger.debug("XML : " + xml);
			JAXBElement<QuadrigaConceptReply> response1 = null;
			try {
				JAXBContext context = JAXBContext
						.newInstance(QuadrigaConceptReply.class);
				Unmarshaller unmarshaller = context.createUnmarshaller();
				unmarshaller
						.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
				InputStream is = new ByteArrayInputStream(xml.getBytes());
				response1 = unmarshaller.unmarshal(new StreamSource(is),
						QuadrigaConceptReply.class);
			} catch (Exception e) {
				logger.error("Error in unmarshalling", e);
				response.setStatus(404);
				String errorMsg = errorMessageRest.getErrorMsg("Error in unmarshalling",request);
				return errorMsg;
			}
			QuadrigaConceptReply qReply = response1.getValue();
			ConceptList c1 = qReply.getConceptList();
			List<Concept> conceptList = c1.getConcepts();

			Iterator<Concept> I = conceptList.iterator();

			while (I.hasNext()) {
				Concept c = I.next();
				logger.debug("arg Name :" + c.getName().trim());
				logger.debug("arg Pos :" + c.getPos().trim());
				logger.debug("arg URI :" + c.getUri().trim());
				logger.debug("arg descrtiption :" + c.getDescription().trim());
				try {
					conceptControllerManager.addItems(c.getName(), c.getUri(),
							c.getPos(), c.getDescription(),
							conceptCollectionId, user.getUserName());
				} catch (QuadrigaStorageException e) {
					logger.error("Errors in adding items", e);
					response.setStatus(500);
					response.setContentType(accept);
					String errorMsg = errorMessageRest.getErrorMsg("Failed to add due to DB Error",request);
					return errorMsg;
				}

			}
			response.setStatus(200);
			response.setContentType(accept);
			return "Success";
		}
	}

	/**
	 * Rest interface for the getting list of concept collections of a user
	 * http://<<URL>:<PORT>>/quadriga/rest/workspace/<workspaceid>/
	 * conceptcollections
	 * http://localhost:8080/quadriga/rest/workspace/WS_22992652874022949
	 * /conceptcollections
	 * 
	 * @author Lohith Dwaraka
	 * @param userId
	 * @param model
	 * @return
	 * @throws RestException
	 * @throws Exception
	 */
	@RequestMapping(value = "rest/workspace/{workspaceId}/conceptcollections", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listWorkspaceConceptCollections(
			@PathVariable("workspaceId") String workspaceId, ModelMap model,
			Principal principal, HttpServletRequest req) throws RestException {
		List<IConceptCollection> collectionsList = null;
		VelocityEngine engine = null;
		Template template = null;

		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();
			String userId = principal.getName();
			collectionsList = workspaceCCManager.listWorkspaceCC(workspaceId,
					userId);
			template = engine
					.getTemplate("velocitytemplates/conceptcollections.vm");
			VelocityContext context = new VelocityContext(
					restVelocityFactory.getVelocityContext());
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
			logger.error("Exception:", e);
			throw new RestException(405);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RestException(403);
		}

	}

	/**
	 * Rest interface add a new Concept collection with a list of concepts
	 * http://<<URL>:<PORT>>/quadriga/rest/workspace/<workspaceid>/createcc
	 * http:
	 * //localhost:8080/quadriga/rest/workspace/WS_22992652874022949/createcc
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
	@RestAccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.WORKSPACE_REST,paramIndex = 1, userRole = { RoleNames.ROLE_WORKSPACE_COLLABORATOR_ADMIN , RoleNames.ROLE_PROJ_COLLABORATOR_CONTRIBUTOR} )})
	@ResponseBody
	@RequestMapping(value = "rest/workspace/{workspaceId}/createcc", method = RequestMethod.POST)
	public String addConceptCollectionsToWorkspace(
			@PathVariable("workspaceId") String workspaceId,
			HttpServletRequest request, HttpServletResponse response,
			@RequestBody String xml, @RequestHeader("Accept") String accept,
			ModelMap model, Principal principal )
			throws RestException, QuadrigaStorageException,
			QuadrigaAccessException {
		IUser user = usermanager.getUserDetails(principal.getName());
		String ccName = request.getParameter("name");
		String desc = request.getParameter("desc");
		
		if (!checkWSSecurity.checkIsWorkspaceExists(workspaceId)) {
			logger.info("Workspace ID : " + workspaceId + " doesn't exist");
			response.setStatus(404);
			String errorMsg = errorMessageRest.getErrorMsg("Workspace ID : " + workspaceId + " doesn't exist",request);
			return errorMsg;
		}

		
		IConceptCollection collection = conceptCollectionFactory
				.createConceptCollectionObject();

		if (ccName == null || ccName.isEmpty()) {
			response.setStatus(404);
			logger.info("came here " + ccName);
			String errorMsg = errorMessageRest.getErrorMsg("Please provide concept collection name",request);
			return errorMsg;
		}
		if (desc == null ||  desc.isEmpty()) {
			response.setStatus(404);
			String errorMsg = errorMessageRest.getErrorMsg("Please provide concept collection description",request);
			return errorMsg;
		}
		logger.debug("XML : " + xml);
		JAXBElement<QuadrigaConceptReply> response1 = null;
		try {
			JAXBContext context = JAXBContext
					.newInstance(QuadrigaConceptReply.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller
					.setEventHandler(new javax.xml.bind.helpers.DefaultValidationEventHandler());
			InputStream is = new ByteArrayInputStream(xml.getBytes());
			response1 = unmarshaller.unmarshal(new StreamSource(is),
					QuadrigaConceptReply.class);
		} catch (Exception e) {
			logger.error("Error in unmarshalling", e);
			response.setStatus(404);
			String errorMsg = errorMessageRest.getErrorMsg("Error in unmarshalling",request);
			return errorMsg;
		}
		if (response1 == null) {
			response.setStatus(404);
			String errorMsg = errorMessageRest.getErrorMsg("Concepts XML is not valid",request);
			return errorMsg;
		}
		QuadrigaConceptReply qReply = response1.getValue();
		ConceptList c1 = qReply.getConceptList();
		List<Concept> conceptList = c1.getConcepts();
		if (conceptList.size() < 1) {
			response.setStatus(404);
			String errorMsg = errorMessageRest.getErrorMsg("Concepts XML is not valid",request);
			return errorMsg;
		}

		collection.setDescription(desc);
		collection.setOwner(user);
		collection.setName(ccName);

		conceptControllerManager.addConceptCollection(collection);
		String ccId = conceptControllerManager.getConceptCollectionId(ccName);

		Iterator<Concept> I = conceptList.iterator();

		while (I.hasNext()) {
			Concept c = I.next();
			logger.debug("arg Name :" + c.getName().trim());
			logger.debug("arg Pos :" + c.getPos().trim());
			logger.debug("arg URI :" + c.getUri().trim());
			logger.debug("arg descrtiption :" + c.getDescription().trim());
			try {
				conceptControllerManager.addItems(c.getName().trim(), c
						.getUri().trim(), c.getPos().trim(), c.getDescription()
						.trim(), ccId, user.getUserName());
			} catch (QuadrigaStorageException e) {
				logger.error("Errors in adding items", e);
				response.setStatus(500);
				response.setContentType(accept);
				String errorMsg = errorMessageRest.getErrorMsg("Failed to add due to DB Error",request);
				return errorMsg;				
			}

		}

		String msg = workspaceCCManager.addWorkspaceCC(workspaceId, ccId,
				user.getUserName());

		response.setStatus(200);
		response.setContentType(accept);
		return ccId;
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
			@PathVariable("collectionID") String collectionID, ModelMap model,
			HttpServletRequest req, Principal principal) throws RestException {
		VelocityEngine engine = null;
		Template template = null;
		StringWriter sw = new StringWriter();
		collection = collectionFactory.createConceptCollectionObject();
		collection.setId(collectionID);
		try {
			engine = restVelocityFactory.getVelocityEngine(req);
			engine.init();
			conceptControllerManager.getCollectionDetails(collection,
					principal.getName());
			template = engine
					.getTemplate("velocitytemplates/conceptdetails.vm");
			VelocityContext context = new VelocityContext(
					restVelocityFactory.getVelocityContext());
			context.put("list", collection.getItems());
			context.put("conceptPowerURL", conceptPowerURL);
			context.put("path", updateConceptPowerURLPath);
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
		} catch (QuadrigaAccessException e) {
			logger.error("Exception:", e);
			throw new RestException(403);
		} catch (Exception e) {
			logger.error("Exception:", e);
			throw new RestException(405);
		}

	}
	

}
