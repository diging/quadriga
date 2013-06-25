package edu.asu.spring.quadriga.web;

import java.io.StringWriter;
import java.util.Iterator;
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

import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.domain.factories.impl.DictionaryItemsFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.IDictionaryManager;
import edu.asu.spring.quadriga.service.IProjectManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * Controller for all the rest apis exposed to other clients
 * @author Sathya
 * @author LohithDwaraka
 *
 */

@Controller
public class RestController {

	@Autowired
	IDictionaryManager dictonaryManager;

	private static final Logger logger = LoggerFactory
			.getLogger(RestController.class);

	@Autowired
	IUserManager usermanager;

	@Autowired
	IProject project;

	@Autowired
	IDictionaryManager dictionaryManager;

	@Autowired
	IProjectManager projectManager;

	@Autowired
	IDictionaryFactory dictionaryFactory;

	@Autowired
	DictionaryItemsFactory dictionaryItemsFactory;

	@Autowired IConceptCollectionManager conceptControllerManager;
	
	IConceptCollection collection;
	@Autowired
	IConceptCollectionFactory collectionFactory;
	
	IConcept concept;
	@Autowired
	IConceptFactory conFact;
	
	@Autowired
	IRestVelocityFactory restVelocityFactory;

	/**
	 * Rest interface to List projects for a @userId
	 * @param userId
	 * @param model
	 * @return
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value = "rest/projects/{userID}", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listProjects(@PathVariable("userID") String userId,
			ModelMap model) throws QuadrigaStorageException {
		List<IProject> projectList = null;
		VelocityEngine engine = restVelocityFactory.RestVelocityFactory();

		Template template = null;


		try {
			engine.init();
			projectList = projectManager.getProjectsOfUser(userId);
			template = engine.getTemplate("velocitytemplates/projectlist.vm");
			VelocityContext context = new VelocityContext();
			context.put("list", projectList);

			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Rest interface for the List Dictionary for the userId 
	 * http://<<URL>:<PORT>>/quadriga/rest/dictionaries/{USERNAME}
	 * http://localhost:8080/quadriga/rest/dictionaries/test
	 * @author Lohith Dwaraka
	 * @param userId
	 * @param model
	 * @return
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value = "rest/dictionaries/{userID}", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listDictionaries(@PathVariable("userID") String userId,
			ModelMap model) throws QuadrigaStorageException {
		List<IDictionary> dictionaryList = null;
		VelocityEngine engine = restVelocityFactory.RestVelocityFactory();

		Template template = null;

		try {
			engine.init();
			logger.info("Getting dictionary list for user : "+userId);
			dictionaryList = dictionaryManager.getDictionariesList(userId);
			if(!(dictionaryList== null)){
				Iterator <IDictionary> I = dictionaryList.iterator();
				while(I.hasNext()){
					IDictionary dictionary=I.next();
					logger.info("Dictionary Name : "+dictionary.getName());
					logger.info("Dictionary Description : "+dictionary.getDescription());
					logger.info("Dictionary Id : "+dictionary.getId());
				}
			}
			template = engine.getTemplate("velocitytemplates/dictionarylist.vm");
			VelocityContext context = new VelocityContext();
			context.put("list", dictionaryList);

			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new QuadrigaStorageException();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Rest interface for the List Dictionary items for the dictionary Id 
	 * http://<<URL>:<PORT>>/quadriga/rest/dictionaryDetails/{DictionaryID}
	 * http://localhost:8080/quadriga/rest/dictionaryDetails/68
	 * @author Lohith Dwaraka
	 * @param dictionaryId
	 * @param model
	 * @return
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value = "rest/dictionaryDetails/{dictionaryId}", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listDictionaryItems(@PathVariable("dictionaryId") String dictionaryId,
			ModelMap model) throws QuadrigaStorageException {
		List<IDictionaryItems> dictionaryItemsList = null;
		VelocityEngine engine = restVelocityFactory.RestVelocityFactory();

		Template template = null;

		try {
			engine.init();
			logger.info("Getting dictionary items list for dictionary id : "+dictionaryId);
			dictionaryItemsList = dictionaryManager.getDictionariesItems(dictionaryId);
			if(!(dictionaryItemsList== null)){
				Iterator <IDictionaryItems> I = dictionaryItemsList.iterator();
				while(I.hasNext()){
					IDictionaryItems dictionaryItems=I.next();
					logger.info("Dictionary Item name : "+dictionaryItems.getItems());
					logger.info("Dictionary Description : "+dictionaryItems.getDescription());
					logger.info("Dictionary Pos : "+dictionaryItems.getPos());
				}
			}
			template = engine.getTemplate("velocitytemplates/dictionaryitemslist.vm");
			VelocityContext context = new VelocityContext();
			context.put("list", dictionaryItemsList);

			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	@RequestMapping(value = "rest/conceptcollections/{userID}", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String listConceptCollections(@PathVariable("userID") String userId,
			ModelMap model) throws QuadrigaStorageException {
		List<IConceptCollection> collectionsList = null;
		VelocityEngine engine = restVelocityFactory.RestVelocityFactory();
		Template template = null;
		StringWriter sw = new StringWriter();
		try {
			engine.init();
			collectionsList = conceptControllerManager.getCollectionsOwnedbyUser(userId);
			template = engine.getTemplate("velocitytemplates/conceptcollections.vm");
			VelocityContext context = new VelocityContext();
			context.put("list", collectionsList);
			StringWriter writer = new StringWriter();
			template.merge(context, writer);
			return writer.toString();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping(value = "rest/conceptdetails/{collectionID}", method = RequestMethod.GET, produces = "application/xml")
	@ResponseBody
	public String getConceptList(@PathVariable("collectionID") int collectionID,
			ModelMap model) throws QuadrigaStorageException {
		VelocityEngine engine = restVelocityFactory.RestVelocityFactory();
		Template template = null;
		StringWriter sw = new StringWriter();
		collection = collectionFactory.createConceptCollectionObject();
		collection.setId(collectionID);
		try {
			engine.init();
			conceptControllerManager.getCollectionDetails(collection);
			template = engine.getTemplate("velocitytemplates/conceptdetails.vm");
			VelocityContext context = new VelocityContext();
			context.put("list", collection.getItems());
			template.merge(context, sw);
			return sw.toString();
		} catch (ResourceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MethodInvocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		catch (QuadrigaStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
