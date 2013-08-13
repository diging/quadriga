package edu.asu.spring.quadriga.validator;

import java.util.List;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.implementation.CollaboratorForm;

public class CollaboratorFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(CollaboratorForm.class);
	}

	@Override
	public void validate(Object obj, Errors err) {
		
		List<ICollaborator> collaborator;
		
		// TODO Auto-generated method stub
        CollaboratorForm collaboratorForm = (CollaboratorForm)obj;
        collaborator = collaboratorForm.getCollaborator();
        
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "collaborator.userObj", "collaborator_user.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(err, "collaborator.collaboratorRoles", "collaborator_roles.required");
        
        if(err.getFieldError("collaborator.collaboratorRoles")==null)
		{
		   validateCollaboratorRoles(collaborator,err);
		}
	}
	
	public void validateCollaboratorRoles(List<ICollaborator> collaborator,Errors err)
	{
		for(ICollaborator collab : collaborator)
		{
			List<ICollaboratorRole> roles;
			roles = collab.getCollaboratorRoles();
			if(roles == null)
			{
				err.rejectValue("collaborator.collaboratorRoles", "collaborator_roles_selection.required");
			}
		}
	}

}
