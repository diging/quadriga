package edu.asu.spring.quadriga.web;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
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
	String username;
	IConceptCollection concept;
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
	    
	    list = conceptControllerManager.getCollectionsOfUser(userId);
	    model.addAttribute("conceptlist", list);
	   
	    user =  usermanager.getUserDetails(userId);
	    username = user.getName();
	    model.addAttribute("username", username);	
	return "auth/conceptcollections";
	}
}
