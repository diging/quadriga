package edu.asu.spring.quadriga.web;

import java.security.Principal;
import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
import edu.asu.spring.quadriga.service.IConceptCollectionManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This class is the controller for the concept collection requests. 
 * @author SatyaSwaroop
 *
 */



@Controller
public class ConceptcollectionController {
	@Autowired IConceptCollectionManager conceptControllerManager;
	List<IConceptCollection> list;
	List<IConceptCollection> collab_list;
	String username;
	@Autowired
	private CollectionsValidator validator;
	IConceptCollection concept;
	@Autowired
	IConceptCollectionFactory collectionFactory;
	
	IConcept con;
	@Autowired
	IConceptFactory conFact;
	List<IUser> collaboratorList;
	ConceptpowerReply c;
	@Autowired IUserManager usermanager;
	IUser user;
	
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
	public String conceptCollectionHandler(ModelMap model) throws SQLException{
		UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
	    String userId = principal.getUsername();
	    
	    list = conceptControllerManager.getCollectionsOwnedbyUser(userId);
	    model.addAttribute("conceptlist", list);
	    collab_list = conceptControllerManager.getUserCollaborations(userId);
	    model.addAttribute("collaborationlist", collab_list);
	    
	    user =  usermanager.getUserDetails(userId);
	    username = user.getUserName();
	    model.addAttribute("username", username);	
	    return "auth/conceptcollections";
	}
	
	/**
	 * Returns the list of concept collections of user to the view
	 * 
	 * 
	 * */
	@RequestMapping(value = "auth/conceptdetails", method = RequestMethod.GET)
	public String conceptDetailsHandler(HttpServletRequest req, ModelMap model) throws SQLException{
		/*UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String userId = principal.getUsername();*/
		
		if(req.getParameter("name")!=null)
		{
			concept = collectionFactory.createConceptCollectionObject();
			concept.setName(req.getParameter("name"));
		}
	    int index;
	    if(( index = list.indexOf(concept))>=0)
	    concept = list.get(index);
	    else if((index = collab_list.indexOf(concept))>=0)
	    concept = collab_list.get(index);	
	    conceptControllerManager.getCollectionDetails(concept);
	    model.addAttribute("concept", concept);
	    return "auth/conceptcollections/details";
	}
	@RequestMapping(value = "auth/searchitems", method = RequestMethod.GET)
	public String conceptSearchHandler(HttpServletRequest req, ModelMap model) throws SQLException{
		/*UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String userId = principal.getUsername();*/
		c = conceptControllerManager.search(req.getParameter("name"), req.getParameter("pos"));
		if(c!=null)
	    model.addAttribute("result", c.getConceptEntry());
		
	    return "auth/searchitems";
	}
	@RequestMapping(value = "auth/conceptcollections/addItems", method = RequestMethod.POST)
	public String saveItemsHandler(HttpServletRequest req, ModelMap model) throws SQLException{
		/*UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String userId = principal.getUsername();*/
		String[] values= req.getParameterValues("selected");
		for(String id : values)
		{
			ConceptEntry temp = new ConceptEntry();
			temp.setId(id);
			temp = c.getConceptEntry().get((c.getConceptEntry().indexOf(temp)));
			conceptControllerManager.addItems(temp.getLemma(),id,temp.getPos(), temp.getDescription(),concept.getName());
		}
		
	    int index;
	    if(( index = list.indexOf(concept))>=0)
	    concept = list.get(index);
	    else if((index = collab_list.indexOf(concept))>=0)
	    concept = collab_list.get(index);	
	    conceptControllerManager.getCollectionDetails(concept);
	    model.addAttribute("concept", concept);
		return "auth/conceptcollections/details";
	}
	
	@RequestMapping(value="auth/conceptcollections/addCollectionsForm", method = RequestMethod.GET)
	public ModelAndView addCollectionsForm() {
	return new ModelAndView("auth/conceptcollections/addCollectionsForm", "command",new ConceptCollectionFactory().createConceptCollectionObject());
	}
	
	/**
	 * Returns the list of concept collections of user to the view
	 * 
	 * 
	 * */
	@RequestMapping(value = "auth/conceptcollections/addCollectionsForm", method = RequestMethod.POST)
	public ModelAndView addConceptCollection(@Validated @ModelAttribute("collection") ConceptCollection collection, BindingResult result,  Model model, Principal principal ){
		 if (result.hasErrors()) {
		      model.addAttribute("Error",
		          "Error: " + collection.getName()+"already exists.");
		      return new ModelAndView("auth/conceptcollections/addCollectionsForm", "command",new ConceptCollectionFactory().createConceptCollectionObject());
		    }
		 IUser user = usermanager.getUserDetails(principal.getName());
			collection.setOwner(user);
		 conceptControllerManager.addConceptCollection(collection);
		 concept = collection;
		
		 model.addAttribute("concept", collection);
		return new ModelAndView("auth/conceptcollections/details");
		
	}
	
	
	/**
	 * Returns the list of concept collections of user to the view
	 * 
	 * 
	 * */
	@RequestMapping(value = "auth/conceptcollections/deleteitems", method = RequestMethod.POST)
	public String deleteItems(HttpServletRequest req, ModelMap model){
		
		
		String[] values= req.getParameterValues("selected");
		if(values!=null){
		for(String id : values)
		{
			conceptControllerManager.deleteItem(id,concept.getName());
			con = conFact.createConceptObject();con.setId(id);
			concept.getItems().remove(concept.getItems().indexOf(con));
			
		}
		}
		
		conceptControllerManager.getCollectionDetails(concept);
			
		return "redirect:/auth/conceptdetails";
		
	}
	@RequestMapping(value = "auth/conceptcollections/updateitems", method = RequestMethod.POST)
	public String conceptUpdateHandler(HttpServletRequest req, ModelMap model) throws SQLException{
		String[] values= req.getParameterValues("selected");
		if(values!=null)
		conceptControllerManager.update(values,concept);
		
		return "redirect:/auth/conceptdetails";
	}
	
}
