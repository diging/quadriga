package edu.asu.spring.quadriga.validator;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.implementation.Collaborator;
@Service
public class CollaboratorValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(Collaborator.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		String userName;
		List<ICollaboratorRole> role;
		
		// TODO Auto-generated method stub
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
	
	public void validateUserName(String UserName,Errors err)
	{
		if(UserName == null)
			err.rejectValue("userObj", "collaborator_user_selection.required");
	}
	
	public void validateCollaboratorRoles(List<ICollaboratorRole> roles,Errors err)
	{
		if(roles == null)
			err.rejectValue("collaboratorRoles", "collaborator_roles_selection.required");
	}

}
