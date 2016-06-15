package edu.asu.spring.quadriga.validator;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;
@Service
public class CollaboratorFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(ModifyCollaboratorForm.class);
	}

	@Override
	public void validate(Object obj, Errors err) {
		
		List<ModifyCollaborator> collaboratorList;
		
        ModifyCollaboratorForm collaboratorForm = (ModifyCollaboratorForm)obj;
        collaboratorList = collaboratorForm.getCollaborators();
        
       collaboratorFormValidation(collaboratorList,err);
        
	}
	
	/**
	 * This method checks if any of the collaborator role is marked for updating and also
	 * if any collaborator roles are associated with the user
	 * @param collaboratorList
	 * @param err
	 */
	public void collaboratorFormValidation(List<ModifyCollaborator> collaboratorList,Errors err)
	{
		List<IQuadrigaRole> collaboratorRoles;
		
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
