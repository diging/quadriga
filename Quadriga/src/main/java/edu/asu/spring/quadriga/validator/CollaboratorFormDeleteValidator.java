package edu.asu.spring.quadriga.validator;


import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaborator;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyCollaboratorForm;

/**
 * This class validates the collaborator form used for deletion.
 * @author kiran batna
 *
 */
@Service
public class CollaboratorFormDeleteValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		
		return clazz.isAssignableFrom(ModifyCollaboratorForm.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ModifyCollaboratorForm collaboratorForm = (ModifyCollaboratorForm)target;
		List<ModifyCollaborator> collaboratorList = collaboratorForm.getCollaborators();
		String userName;
		boolean isAllNull = true;
		for(int i=0;i<collaboratorList.size();i++)
		{
				userName = collaboratorList.get(i).getUserName();
				if(userName != null)
				{
					isAllNull = false;
				}
		}
		
		if(isAllNull == true)
		{
			for(int i=0;i<collaboratorList.size();i++)
			{
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "collaborators["+i+"].userName", "collaborator_delete_selection.required");
			}
		}

	}
}