package edu.asu.spring.quadriga.web.manageusers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IQuadrigaRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.validator.UserRolesFormValidator;
import edu.asu.spring.quadriga.web.login.RoleNames;
import edu.asu.spring.quadriga.web.users.backingbean.ModifyQuadrigaUser;
import edu.asu.spring.quadriga.web.users.backingbean.ModifyQuadrigaUserForm;
import edu.asu.spring.quadriga.web.users.backingbean.ModifyQuadrigaUserFormManager;

@Controller
public class ModifyUserRolesController 
{
	@Autowired
	private UserRolesFormValidator validator;
	
	@Autowired
	private IQuadrigaRoleManager rolemanager;
	
	@Autowired 
	private IUserManager userManager;
	
	@Autowired
	private ModifyQuadrigaUserFormManager quadrigaUserMananger;

	@InitBinder
	protected void initBinder(WebDataBinder validateBinder) throws Exception {
        validateBinder.setValidator(validator);
	}
	
	/**
	 * This method retrieve all the active users and their quadriga roles to be 
	 * displayed for updating.
	 * @return ModelAndView - View to display users and quadriga roles. 
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 * @author kiran batna
	 */
	@RequestMapping(value = "auth/users/updateroles", method = RequestMethod.GET)
	public ModelAndView updateQuadrigaRolesRequest()
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		ModifyQuadrigaUserForm userForm;
		model = new ModelAndView("auth/users/updateroles");
		
		//Get all Active Users
		List<IUser> activeUserList = userManager.getAllActiveUsers();
		
		userForm = quadrigaUserMananger.modifyUserQuadrigaRolesManager(activeUserList);
		
		//Get all Quadriga roles
		List<IQuadrigaRole> quadrigaRoles = rolemanager.getQuadrigaRoles();
		
		//remove the invalid quadriga role object
		IQuadrigaRole role = rolemanager.getQuadrigaRole(RoleNames.DB_ROLE_QUADRIGA_NOACCOUNT);
		if(quadrigaRoles.contains(role))
			quadrigaRoles.remove(role);
				
		model.getModelMap().put("userrolesform", userForm);
		model.getModelMap().put("quadrigaroles",quadrigaRoles);
		model.getModelMap().put("success", 0);
		return model;
	}
	
	/**
	 * This method updates the quadriga roles associated with the user
	 * @param userForm
	 * @param result
	 * @param principal
	 * @return ModelAndView - On success View showing the success message.
	 *                        On error View showing the data for updating.
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException
	 * @author kiran batna
	 */
	@RequestMapping(value = "auth/users/updateroles", method = RequestMethod.POST)
	public ModelAndView updateCollaboratorRequest(@Validated @ModelAttribute("userrolesform") ModifyQuadrigaUserForm userForm,
			BindingResult result,Principal principal) 
					throws QuadrigaStorageException, QuadrigaAccessException
	{
		ModelAndView model;
		List<ModifyQuadrigaUser> users;
		List<IQuadrigaRole> quadrigaRoles;
		String loggedInUser;
		StringBuilder userRoles;
		String userName = "";
		try
		{
			loggedInUser = principal.getName();
			userRoles = new StringBuilder();
			quadrigaRoles = new ArrayList<IQuadrigaRole>();
			
			//create model view
			model = new ModelAndView("auth/users/updateroles");
			
			if(result.hasErrors())
			{
				//Get all Active Users
				List<IUser> activeUserList = userManager.getAllActiveUsers();
				
				userForm = quadrigaUserMananger.modifyUserQuadrigaRolesManager(activeUserList);
				
				//Get all Quadriga roles
				quadrigaRoles = rolemanager.getQuadrigaRoles();
				
				//remove the invalid quadriga role object
				IQuadrigaRole role = rolemanager.getQuadrigaRole(RoleNames.DB_ROLE_QUADRIGA_NOACCOUNT);
				if(quadrigaRoles.contains(role))
					quadrigaRoles.remove(role);
						
				model.getModelMap().put("userrolesform", userForm);
				model.getModelMap().put("quadrigaroles",quadrigaRoles);
				model.getModelMap().put("success", 0);
			}
			else
			{
				users = userForm.getUsers();
				
				for(ModifyQuadrigaUser user : users)
				{
					userRoles = new StringBuilder();
					userName = user.getUserName();
					quadrigaRoles =  user.getQuadrigaRoles();
					
					for(IQuadrigaRole role : quadrigaRoles)
					{
						userRoles.append(",");
						userRoles.append(role.getDBid());
					}
					
					userManager.updateUserQuadrigaRoles(userName, userRoles.toString().substring(1), loggedInUser);
				}
				
				model.getModelMap().put("success", 1);
			}
		}
		catch(HibernateException ex)
		{
			throw new QuadrigaStorageException();
		}

		return model;
	}

}
