package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.service.IUserManager;

@Controller
public class UserController {

	@Autowired 
	IUserManager usermanager;
	
	@RequestMapping(value = "auth/users/manage", method = RequestMethod.GET)
	public String manageUsers(ModelMap model, Principal principal)
	{
		// Get the LDAP-authenticated userid
		String sUserId = principal.getName();

		model.addAttribute("username", sUserId);
		return "auth/users/manage";
	}
	
	@RequestMapping(value = "auth/users/requestslist", method = RequestMethod.GET)
	public String userRequestList(ModelMap model, Principal principal)
	{
		String sUserId = principal.getName();
		model.addAttribute("username", sUserId);
		
		List<IUser> userRequestsList = usermanager.getUserRequests();
		model.addAttribute("userRequestsList", userRequestsList);
		return "auth/users/requests";
	}
	
	@RequestMapping(value = "auth/users/access/{accessRights}", method = RequestMethod.GET)
	public String userAccessHandler(@PathVariable("accessRights") String sAccessRights, ModelMap model, Principal principal)
	{
		String sUserId = principal.getName();
		model.addAttribute("username", sUserId);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+sAccessRights);
		
		//Reload the request list
		List<IUser> userRequestsList = usermanager.getUserRequests();
		model.addAttribute("userRequestsList", userRequestsList);
		return "auth/users/requests";
	}
	
	@RequestMapping(value = "auth/users/activelist", method = RequestMethod.GET)
	public String userActiveList(ModelMap model, Principal principal)
	{
		String sUserId = principal.getName();
		model.addAttribute("username", sUserId);
		
		List<IUser> activeUserList = usermanager.getAllActiveUsers();
		model.addAttribute("activeUserList", activeUserList);
		return "auth/users/active";
	}
	
	@RequestMapping(value = "auth/users/inactivelist", method = RequestMethod.GET)
	public String userInactiveList(ModelMap model, Principal principal)
	{
		String sUserId = principal.getName();
		model.addAttribute("username", sUserId);
		
		List<IUser> inactiveUserList = usermanager.getAllInActiveUsers();
		model.addAttribute("inactiveUserList", inactiveUserList);
		
		return "auth/users/inactive";
	}
	
	
	@RequestMapping(value="auth/users/deactivate/{userName}", method = RequestMethod.GET)
	public String deactivateUser(@PathVariable("userName") String sUserName, ModelMap model, Principal principal) {
		
		int iResult = usermanager.deactivateUser(sUserName);
		
		String sUserId = principal.getName();
		model.addAttribute("username", sUserId);
		
		//Reload the active user list
		List<IUser> activeUserList = usermanager.getAllActiveUsers();
		model.addAttribute("activeUserList", activeUserList);
		return "auth/users/active";
	}
	
	@RequestMapping(value="auth/users/activate/{userName}", method = RequestMethod.GET)
	public String activateUser(@PathVariable("userName") String sUserName, ModelMap model, Principal principal) {
		
		//Deactivate the user account
		int iResult = usermanager.activateUser(sUserName);
		
		String sUserId = principal.getName();
		model.addAttribute("username", sUserId);
		
		//Reload the inactive user list
		List<IUser> inactiveUserList = usermanager.getAllInActiveUsers();
		model.addAttribute("inactiveUserList", inactiveUserList);
		
		return "auth/users/inactive";
	}
}