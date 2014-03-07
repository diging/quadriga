package edu.asu.spring.quadriga.web.conceptcollection;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.aspects.annotations.AccessPolicies;
import edu.asu.spring.quadriga.aspects.annotations.CheckedElementType;
import edu.asu.spring.quadriga.aspects.annotations.ElementAccessPolicy;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.impl.ConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.implementation.CollectionsValidator;
import edu.asu.spring.quadriga.domain.implementation.ConceptCollection;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply.ConceptEntry;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * This class is the controller for the concept collection requests.
 * 
 * @author SatyaSwaroop Boddu
 * 
 */

@Controller
public class ConceptcollectionController {

	@Autowired
	IConceptCollectionManager conceptControllerManager;
	
	private List<IConceptCollection> list;

	private List<IConceptCollection> collab_list;

	private String username;

	@Autowired
	private CollectionsValidator validator;

	private IConceptCollection collection;

	@Autowired
	private IConceptCollectionFactory collectionFactory;

	private IConcept concept;

	@Autowired
	private IConceptFactory conceptFactory;

	private ConceptpowerReply c;

	
	@Autowired
	private IUserManager usermanager;

	private IUser user;

	/**
	 * Attach the custom validator to the Spring context
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {

		binder.setValidator(validator);
	}

	public ConceptpowerReply getC() {
		return c;
	}

	public void setC(ConceptpowerReply c) {
		this.c = c;
	}
	public IConceptCollectionManager getConceptControllerManager() {
		return conceptControllerManager;
	}


	public void setConceptControllerManager(
			IConceptCollectionManager conceptControllerManager) {
		this.conceptControllerManager = conceptControllerManager;
	}


	public CollectionsValidator getValidator() {
		return validator;
	}


	public void setValidator(CollectionsValidator validator) {
		this.validator = validator;
	}


	public IConceptCollectionFactory getCollectionFactory() {
		return collectionFactory;
	}


	public void setCollectionFactory(IConceptCollectionFactory collectionFactory) {
		this.collectionFactory = collectionFactory;
	}



	public IConceptFactory getConceptFactory() {
		return conceptFactory;
	}


	public void setConceptFactory(IConceptFactory conceptFactory) {
		this.conceptFactory = conceptFactory;
	}


	public IUserManager getUsermanager() {
		return usermanager;
	}


	public void setUsermanager(IUserManager usermanager) {
		this.usermanager = usermanager;
	}
	
	/**
	 * This is used to fetch the user related concept collections from database.
	 * @param model
	 * @return 					Returns the list of concept collections of user to the view
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "auth/conceptcollections", method = RequestMethod.GET)
	public String conceptCollectionHandler(ModelMap model,Principal principal)
			throws QuadrigaStorageException {
		String userId = principal.getName();
		list = conceptControllerManager.getCollectionsOwnedbyUser(userId);
		model.addAttribute("conceptlist", list);
		collab_list = conceptControllerManager.getUserCollaborations(userId);
		model.addAttribute("collaborationlist", collab_list);
		user = usermanager.getUserDetails(userId);
		username = user.getUserName();
		model.addAttribute("username", username);
		return "auth/conceptcollections";
	}

	
	/**
	 * This is used to fetch the details of a concept from database and display
	 * @param collection_id
	 * @param model
	 * @param principal
	 * @return
	 * 				Returns the list of concept collections of user to the view
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@RequestMapping(value = "auth/conceptcollections/{collection_id}", method = RequestMethod.GET)
	public String conceptDetailsHandler(
			@PathVariable("collection_id") String collection_id, ModelMap model, Principal principal)
			throws QuadrigaStorageException, QuadrigaAccessException {

		collection = collectionFactory.createConceptCollectionObject();
		collection.setId(collection_id);
		conceptControllerManager.getCollectionDetails(collection,principal.getName());
		model.addAttribute("concept", collection);
		conceptControllerManager.getCollaborators(collection);
		
		model.addAttribute("collectionid", collection_id);
		
		List<ICollaborator>collaboratingUsers =  conceptControllerManager.showCollaboratingUsers(collection_id);
		model.addAttribute("collaboratingUsers", collaboratingUsers);
		return "auth/conceptcollections/details";
	}

	public IConceptCollection getCollection() {
		return collection;
	}

	public void setCollection(IConceptCollection collection) {
		this.collection = collection;
	}

	/**
	 * This method is used to search the conceptpower for items and will also give options to add it. 
	 * @param collection_id
	 * @param req
	 * 				HttpRequest must have pos and search name as parameters to hit the conceptpower rest service
	 * @param model
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@AccessPolicies({ @ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION,paramIndex = 1, userRole = {RoleNames.ROLE_CC_COLLABORATOR_ADMIN,RoleNames.ROLE_CC_COLLABORATOR_READ_WRITE} )})
	@RequestMapping(value = "auth/conceptcollections/{collection_id}/searchitems", method = RequestMethod.GET)
	public String conceptSearchHandler(@PathVariable("collection_id") String collection_id, HttpServletRequest req, ModelMap model)
			throws QuadrigaStorageException {
		
		c = conceptControllerManager.search(req.getParameter("name"),
				req.getParameter("pos"));
		if (c != null)
			model.addAttribute("result", c.getConceptEntry());
			model.addAttribute("collectionid", collection_id);
		return "auth/searchitems";
	}

	
	/**
	 * This method is used to save items into a conceptcollection by searching and choosing from the conceptpower
	 * @param collection_id
	 * @param req
	 * 				HttpRequest must have all the selected concept items ids. 
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 */
	@AccessPolicies({@ElementAccessPolicy(type = CheckedElementType.CONCEPTCOLLECTION,paramIndex = 1,userRole = {RoleNames.ROLE_CC_COLLABORATOR_ADMIN,RoleNames.ROLE_CC_COLLABORATOR_READ_WRITE})})
	@RequestMapping(value = "auth/conceptcollections/{collection_id}/addItems", method = RequestMethod.POST)
	public String saveItemsHandler(@PathVariable("collection_id") String collection_id, HttpServletRequest req, ModelMap model, Principal principal)
			throws QuadrigaStorageException, QuadrigaAccessException {
		
		String[] values = req.getParameterValues("selected");
		if (values != null) {
			for (String id : values) {
				ConceptEntry temp = new ConceptEntry();
				temp.setId(id);
				temp = c.getConceptEntry().get(
						(c.getConceptEntry().indexOf(temp)));
				conceptControllerManager.addItems(temp.getLemma(), id,
						temp.getPos(), temp.getDescription(), collection_id,principal.getName());
			}
		}
		int index;
		if ((index = list.indexOf(collection)) >= 0)			
			collection = list.get(index);
		
		else if ((index = collab_list.indexOf(collection)) >= 0)
			collection = collab_list.get(index);
		conceptControllerManager.getCollectionDetails(collection, principal.getName());
		model.addAttribute("concept", collection);
		return "redirect:/auth/conceptcollections/" + collection_id + "";
	}
	
	/**
	 * This method handles the addcollections.jsp form which is used to add a new conceptcollection to quadriga
	 * @return
	 */
	@RequestMapping(value = "auth/conceptcollections/addCollectionsForm", method = RequestMethod.GET)
	public ModelAndView addCollectionsForm() {
		return new ModelAndView("auth/conceptcollections/addCollectionsForm",
				"command",
				new ConceptCollectionFactory().createConceptCollectionObject());
	}

	/**
	 * This method is used to save the form data submitted for adding a conceptcollection
	 * 
	 * @param collection
	 * @param result
	 * @param model
	 * @param principal
	 * @return
	 * 			Returns the list of concept collections of user to the view
	 * @throws QuadrigaStorageException
	 *  
	 * */
	@RequestMapping(value = "auth/conceptcollections/addCollectionsForm", method = RequestMethod.POST)
	public ModelAndView addConceptCollection(
			@Validated @ModelAttribute("collection") ConceptCollection collection,
			BindingResult result, Model model, Principal principal)
			throws QuadrigaStorageException {
		if (result.hasErrors()) {
			model.addAttribute("Error", "Error: " + collection.getName()
					+ "already exists.");
			return new ModelAndView(
					"auth/conceptcollections/addCollectionsForm", "command",
					new ConceptCollectionFactory()
							.createConceptCollectionObject());
		}
		
		IUser user = usermanager.getUserDetails(principal.getName());
		collection.setOwner(user);
		
		conceptControllerManager.addConceptCollection(collection);
		
		return new ModelAndView("redirect:/auth/conceptcollections");

	}

	/**
	 * Returns the list of concept collections of user to the view
	 * 
	 * @param req
	 * 				HttpReqest must have selected items ids as parameters
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException 
	 *  
	 * */
	@RequestMapping(value = "auth/conceptcollections/deleteitems", method = RequestMethod.POST)
	public String deleteItems(HttpServletRequest req, ModelMap model, Principal principal)
			throws QuadrigaStorageException, QuadrigaAccessException {

		String[] values = req.getParameterValues("selected");
		if (values != null) {
			for (String id : values) {

				conceptControllerManager.deleteItem(id, collection.getId(),principal.getName());
				concept = conceptFactory.createConceptObject();
				concept.setId(id);
				collection.getItems().remove(collection.getItems().indexOf(concept));

			}
		}

		conceptControllerManager.getCollectionDetails(collection,principal.getName());

		return "redirect:/auth/conceptcollections/" + collection.getId() + "";

	}
	/**
	 * This method is used to update the concept/item from concept power
	 * @param req 
	 * 				HttpReqest must have selected items ids as parameters
	 * @param model
	 * @param principal
	 * @return 
	 * 			This functions returns to details page
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value = "auth/conceptcollections/updateitems", method = RequestMethod.POST)
	public String conceptUpdateHandler(HttpServletRequest req, ModelMap model, Principal principal)
			throws QuadrigaStorageException {
		String[] values = req.getParameterValues("selected");
		if (values != null)
			conceptControllerManager.update(values, collection,principal.getName() );

		return "redirect:/auth/conceptcollections/" + collection.getId() + "";
	}

}
