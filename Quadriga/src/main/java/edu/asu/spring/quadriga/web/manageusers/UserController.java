package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Request List");		
		return "auth/users/requests";
	}
	
	@RequestMapping(value = "auth/users/activelist", method = RequestMethod.GET)
	public String userActiveList(ModelMap model, Principal principal)
	{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Active List");
		
		String sUserId = principal.getName();
		model.addAttribute("username", sUserId);
		
		List<IUser> activeUserList = usermanager.getAllActiveUsers();
//		System.out.println(activeUserList.size());
		model.addAttribute("activeUserList", activeUserList);
		return "auth/users/active";
	}
	
	@RequestMapping(value = "auth/users/inactivelist", method = RequestMethod.GET)
	public String userInactiveList(ModelMap model, Principal principal)
	{
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>INActive List");
		return "auth/users/inactive";
	}
}