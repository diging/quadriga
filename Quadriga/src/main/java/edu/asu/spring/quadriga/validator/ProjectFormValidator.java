package edu.asu.spring.quadriga.validator;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.web.workbench.backing.ModifyProject;
import edu.asu.spring.quadriga.web.workbench.backing.ModifyProjectForm;

/**
 * This method validates if any project is selected.
 * @author kiran batna
 *
 */
@Service
public class ProjectFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(ModifyProjectForm.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		List<ModifyProject> deleteProjectList;
		String internalId;
		boolean isAllNull = true;
		
		ModifyProjectForm projectForm = (ModifyProjectForm)obj;
		deleteProjectList = projectForm.getProjectList();
		
		//validating if any row is selected
		for(ModifyProject project : deleteProjectList)
		{
			internalId = project.getInternalid();
			if(internalId != null)
			{
				isAllNull = false;
			}
		}
		
		//if no row is selected
		if(isAllNull)
		{
			err.reject("project_selection.required");
		}
	}	
}
