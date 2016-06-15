package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.manageusers.beans.ApproveAccount;

/**
 * The controller to manage the user management part of the Quadriga.
 * Can be called only by pages accessible by admins
 * 
 * @author Ram Kumar Kumaresan
 *
 */
@Controller
public class ListUsersController {

	@Autowired 
	private IUserManager usermanager;

	@Autowired
	private IQuadrigaRoleManager rolemanager;

	/**
     * Admins are provided with the list of open user requets, active users and inactive users 
     * 
     * @return  Return to the user management page of the quadriga
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
        List<IQuadrigaRole> quadrigaRoles = rolemanager.getQuadrigaRoles(IQuadrigaRoleManager.MAIN_ROLES);
        model.addAttribute("quadrigaroles",quadrigaRoles);
        model.addAttribute("quadrolessize",quadrigaRoles.size());
        
        model.addAttribute("userRoles", rolemanager.getSelectableQuarigaRoles(IQuadrigaRoleManager.MAIN_ROLES));
        model.addAttribute("approveAccount", new ApproveAccount());
        

        return "auth/users/manage";
    }
	
}