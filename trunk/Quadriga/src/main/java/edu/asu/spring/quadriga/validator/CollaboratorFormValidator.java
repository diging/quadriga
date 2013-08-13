package edu.asu.spring.quadriga.validator;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.backingbean.ModifyCollaborator;
import edu.asu.spring.quadriga.backingbean.ModifyCollaboratorForm;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
@Service
public class CollaboratorFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		// TODO Auto-generated method stub
		return arg0.isAssignableFrom(ModifyCollaboratorForm.class);
	}

	@Override
	public void validate(Object obj, Errors err) {
		
		List<ModifyCollaborator> collaboratorList;
		
		// TODO Auto-generated method stub
        ModifyCollaboratorForm collaboratorForm = (ModifyCollaboratorForm)obj;
        collaboratorList = collaboratorForm.getCollaborators();
        
       collaboratorFormValidation(collaboratorList,err);
        
	}
	
	public void collaboratorFormValidation(List<ModifyCollaborator> collaboratorList,Errors err)
	{
		List<ICollaboratorRole> collaboratorRoles;
		
		for(int i = 0;i<collaboratorList.size();i++)
		{
			ValidationUtils.rejectIfEmptyOrWhitespace(err, "collaborators["+i+"].collaboratorRoles", "collaborator_roles.required");
			
			if(err.getFieldError("collaborators["+i+"].collaboratorRoles")==null)
			{
				collaboratorRoles = collaboratorList.get(i).getCollaboratorRoles();
				if(collaboratorRoles == null)
				{
					err.rejectValue("collaborators["+i+"].collaboratorRoles", "collaborator_roles_selection.required");
				}
			}
		}
	}

}
