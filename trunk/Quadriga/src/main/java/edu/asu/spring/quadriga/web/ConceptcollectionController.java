package edu.asu.spring.quadriga.web;

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
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.IUserManager;

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

	/**
	 * Returns the list of concept collections of user to the view
	 * 
	 * 
	 * */
	@RequestMapping(value = "auth/conceptcollections", method = RequestMethod.GET)
	public String conceptCollectionHandler(ModelMap model)
			throws QuadrigaStorageException {
		UserDetails principal = (UserDetails) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		String userId = principal.getUsername();
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
	 * Returns the list of concept collections of user to the view
	 * @throws QuadrigaAccessException 
	 * 
	 * 
	 * */
	@RequestMapping(value = "auth/conceptcollections/{collection_id}", method = RequestMethod.GET)
	public String conceptDetailsHandler(
			@PathVariable("collection_id") String collection_id, ModelMap model, Principal principal)
			throws QuadrigaStorageException, QuadrigaAccessException {

		collection = collectionFactory.createConceptCollectionObject();
		collection.setId(collection_id);
		conceptControllerManager.getCollectionDetails(collection,principal.getName());
		model.addAttribute("concept", collection);
		conceptControllerManager.getCollaborators(collection);
		
		List<ICollaborator>collaboratingUsers =  conceptControllerManager.showCollaboratingUsers(collection_id);
		//model.addAttribute("collaboratingUsers", collaboratingUsers);
		return "auth/conceptcollections/details";
	}

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

	@RequestMapping(value = "auth/conceptcollections/addCollectionsForm", method = RequestMethod.GET)
	public ModelAndView addCollectionsForm() {
		return new ModelAndView("auth/conceptcollections/addCollectionsForm",
				"command",
				new ConceptCollectionFactory().createConceptCollectionObject());
	}

	/**
	 * Returns the list of concept collections of user to the view
	 * 
	 * @throws QuadrigaStorageException
	 * 
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
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException 
	 * 
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

	@RequestMapping(value = "auth/conceptcollections/updateitems", method = RequestMethod.POST)
	public String conceptUpdateHandler(HttpServletRequest req, ModelMap model, Principal principal)
			throws QuadrigaStorageException {
		String[] values = req.getParameterValues("selected");
		if (values != null)
			conceptControllerManager.update(values, collection,principal.getName() );

		return "redirect:/auth/conceptcollections/" + collection.getId() + "";
	}

}
