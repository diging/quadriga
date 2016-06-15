package edu.asu.spring.quadriga.validator;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.web.users.backingbean.ModifyQuadrigaUser;
import edu.asu.spring.quadriga.web.users.backingbean.ModifyQuadrigaUserForm;

/**
 * This method validates if any quadriga role is selected 
 * for the user
 * @author kiran batna
 *
 */
@Service
public class UserRolesFormValidator implements Validator 
{

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(ModifyQuadrigaUserForm.class);
	}

	@Override
	public void validate(Object obj, Errors err) {
		List<ModifyQuadrigaUser> users;
		
		ModifyQuadrigaUserForm userForm = (ModifyQuadrigaUserForm)obj;
		users = userForm.getUsers();
		userFormValidation(users,err);
	}
	
	/**
	 * This method validates if the any quadriga roles is selected 
	 * for a user
	 * @param users
	 * @param err
	 * @author kiran batna
	 */
	public void userFormValidation(List<ModifyQuadrigaUser> users, Errors err)
	{
		List<IQuadrigaRole> roles;
		for(int i = 0;i<users.size();i++)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(err, "users["+i+"].quadrigaRoles", "quadriga_roles.required");
			
			if(err.getFieldError("users["+i+"].quadrigaRoles")==null)
			{
				roles = users.get(i).getQuadrigaRoles();
				if(roles == null)
				{
					err.rejectValue("users["+i+"].quadrigaRoles", "quadriga_roles_selection.required");
				}
			}
		}
	}

}
