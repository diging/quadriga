package edu.asu.spring.quadriga.web;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.implementation.ConceptpowerReply;
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
	
	IConceptCollection concept;
	@Autowired
	IConceptCollectionFactory collectionFactory;
	List<IUser> collaboratorList;
	
	@Autowired IUserManager usermanager;
	IUser user;
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
	    list.addAll(collab_list);
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
		concept = collectionFactory.createConceptCollectionObject();
	    concept.setName(req.getParameter("name"));
	    concept = list.get(list.indexOf(concept));
	    conceptControllerManager.getCollectionDetails(concept);
	    model.addAttribute("concept", concept);
	    return "auth/conceptcollections/details";
	}
	@RequestMapping(value = "auth/searchitems", method = RequestMethod.GET)
	public String conceptSearchHandler(HttpServletRequest req, ModelMap model) throws SQLException{
		/*UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String userId = principal.getUsername();*/
		ConceptpowerReply c = conceptControllerManager.search(req.getParameter("name"), req.getParameter("pos"));
		if(c!=null)
	    model.addAttribute("result", c.getConceptEntry());
	    return "auth/searchitems";
	}
	
}
