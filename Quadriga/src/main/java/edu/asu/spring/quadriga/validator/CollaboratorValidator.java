package edu.asu.spring.quadriga.validator;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
/**
 * This class checks if the collaborator user name and roles are empty
 * @author kiran batna
 *
 */
@Service
public class CollaboratorValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(Collaborator.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		String userName;
		List<ICollaboratorRole> role;
		
		Collaborator collaborator = (Collaborator)obj;
		userName = collaborator.getUserObj().getUserName();
		role = collaborator.getCollaboratorRoles();
		
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "userObj", "collaborator_user.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(err, "collaboratorRoles", "collaborator_roles.required");
		if(err.getFieldError("userObj")==null)
		{

		   validateUserName(userName,err);
		}
		
		if(err.getFieldError("collaboratorRoles")==null)
		{
		validateCollaboratorRoles(role,err);
		}
	}
	
	/**
	 * This method checks if username is empty
	 * @param UserName
	 * @param err
	 * @author kiran batna
	 */
	public void validateUserName(String UserName,Errors err)
	{

		if(UserName == null){
			err.rejectValue("userObj", "collaborator_user_selection.required");
		}
	}
	
	/**
	 * This methods validates if the roles are empty
	 * @param roles
	 * @param err
	 * @author kiran batna
	 */
	public void validateCollaboratorRoles(List<ICollaboratorRole> roles,Errors err)
	{

		if(roles == null){

			err.rejectValue("collaboratorRoles", "collaborator_roles_selection.required");}
	}

}