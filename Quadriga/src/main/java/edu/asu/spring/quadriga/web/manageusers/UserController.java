package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * The controller to manage the user management part of the Quadriga.
 * Can be called only by pages accessible by admins
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Controller
public class UserController {

	@Autowired 
	private IUserManager usermanager;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	public IUserManager getUsermanager() {
		return usermanager;
	}

	public void setUsermanager(IUserManager usermanager) {
		this.usermanager = usermanager;
	}

	/**
	 * Admins are provided with the list of open user requets, active users and inactive users 
	 * 
	 * @return 	Return to the user management page of the quadriga
	 */
	@RequestMapping(value = "auth/users/manage", method = RequestMethod.GET)
	public String manageUsers(ModelMap model, Principal principal) throws QuadrigaStorageException
	{
		//Get all User Requests
		List<IUser> userRequestsList = usermanager.getUserRequests();
		model.addAttribute("userRequestsList", userRequestsList);

		//Get all Active Users
		List<IUser> activeUserList = usermanager.getAllActiveUsers();
		model.addAttribute("activeUserList", activeUserList);

		//Get all Inactive Users
		List<IUser> inactiveUserList = usermanager.getAllInActiveUsers();
		model.addAttribute("inactiveUserList", inactiveUserList);

		//Get all Quadriga roles
		List<IQuadrigaRole> quadrigaRoles = rolemanager.getQuadrigaRoles();
		model.addAttribute("quadrigaroles",quadrigaRoles);
		model.addAttribute("quadrolessize",quadrigaRoles.size());

		return "auth/users/manage";
	}

	/**
	 * Admins are provided with the list of open user requests
	 * Not used now. To be used when tabs are implemented in user management 
	 * 
	 * @return	Return to the page that displays all the open user requests
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value = "auth/users/requestslist", method = RequestMethod.GET)
	public String userRequestList(ModelMap model, Principal principal) throws QuadrigaStorageException
	{
		List<IUser> userRequestsList = usermanager.getUserRequests();
		model.addAttribute("userRequestsList", userRequestsList);
		return "auth/users/requests";
	}

	/**
	 * Method to handle the approval/denial of user requests
	 * @param sAccessRights		Contains the access option selected by an admin for a particular user
	 * 
	 * @return	Return to the user management page of the quadriga	
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value = "auth/users/access/{accessRights}", method = RequestMethod.GET)
	public String userAccessHandler(@PathVariable("accessRights") String sAccessRights, ModelMap model, Principal principal) throws QuadrigaStorageException
	{
		String[] sAccessSelected = sAccessRights.split("-");

		if(sAccessSelected[1].equalsIgnoreCase("approve"))
		{
			//User Request has been approved by the admin
			StringBuilder sRoles = new StringBuilder();
			for(int i=2;i<sAccessSelected.length;i++)
			{
				if(i==2)
				{
					sRoles.append(sAccessSelected[i]);
				}
				else
				{
					sRoles.append(",");
					sRoles.append(sAccessSelected[i]);
				}
			}
			usermanager.approveUserRequest(sAccessSelected[0], sRoles.toString(), principal.getName());
		}
		else
		{
			//User Request denied by the admin
			usermanager.denyUserRequest(sAccessSelected[0], principal.getName());
		}

		return "redirect:/auth/users/manage";

	}

	/**
	 * Admins are provided with the list of active users
	 * Not used now. To be used when tabs are implemented in user management 
	 * 
	 * @return	Return to the page that displays all active users
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value = "auth/users/activelist", method = RequestMethod.GET)
	public String userActiveList(ModelMap model, Principal principal) throws QuadrigaStorageException
	{
		List<IUser> activeUserList = usermanager.getAllActiveUsers();
		model.addAttribute("activeUserList", activeUserList);
		return "auth/users/active";
	}

	/**
	 * Admins are provided with the list of inactive users
	 * Not used now. To be used when tabs are implemented in user management 
	 * 
	 * @return	Return to the page that displays all inactive users
	 */
	@RequestMapping(value = "auth/users/inactivelist", method = RequestMethod.GET)
	public String userInactiveList(ModelMap model, Principal principal) throws QuadrigaStorageException
	{
		List<IUser> inactiveUserList = usermanager.getAllInActiveUsers();
		model.addAttribute("inactiveUserList", inactiveUserList);

		return "auth/users/inactive";
	}


	/**
	 * Method to handle the deactivation of a Quadriga user by an admin
	 * 
	 * @param sUserName	The userid of the user whose account is to be deactivated
	 * 
	 * @return	Return to the user management page of the quadriga
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value="auth/users/deactivate/{userName}", method = RequestMethod.GET)
	public String deactivateUser(@PathVariable("userName") String sUserName, ModelMap model, Principal principal) throws QuadrigaStorageException {

		usermanager.deactivateUser(sUserName, principal.getName());
		return "redirect:/auth/users/manage";
	}

	/**
	 * Method to handle the activation of a Quadriga user by an admin
	 * 
	 * @param sUserName	The userid of the user whose account is to be activated
	 * 
	 * @return	Return to the user management page of the quadriga
	 * @throws QuadrigaStorageException 
	 */
	@RequestMapping(value="auth/users/activate/{userName}", method = RequestMethod.GET)
	public String activateUser(@PathVariable("userName") String sUserName, ModelMap model, Principal principal) throws QuadrigaStorageException {

		//Deactivate the user account
		usermanager.activateUser(sUserName, principal.getName());

		return "redirect:/auth/users/manage";
	}

	/**
	 * Method to delete Quadriga user by admin
	 * @param sUserName
	 * @param model
	 * @param principal
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@RequestMapping(value="auth/users/delete/{userName}", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable("userName") String sUserName,Principal principal) throws QuadrigaStorageException 
	{

		ModelAndView model;
		usermanager.deleteUser(sUserName,principal.getName());
		model = new ModelAndView("redirect:/auth/users/manage");
		
		return model;
	}

}