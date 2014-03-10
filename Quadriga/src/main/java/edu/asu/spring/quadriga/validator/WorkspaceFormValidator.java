package edu.asu.spring.quadriga.validator;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspace;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceForm;

/**
 * This class validates if any workspace is selected
 * @author kiran batna
 *
 */
@Service
public class WorkspaceFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> arg0) {
		return arg0.isAssignableFrom(ModifyWorkspaceForm.class);
	}

	@Override
	public void validate(Object obj, Errors err) 
	{
		List<ModifyWorkspace> workspaceList;
		String internalId;
		boolean isAllNull = true;
		
		ModifyWorkspaceForm workspaceForm = (ModifyWorkspaceForm)obj;
		workspaceList = workspaceForm.getWorkspaceList();
		
		//validating if any row is selected
		for(ModifyWorkspace workspace : workspaceList)
		{
			internalId = workspace.getId();
			if(internalId != null)
			{
				isAllNull = false;
			}
		}
		//if no row is selected
		if(isAllNull)
		{
			err.reject("workspace_selection.required");
		}

	}

}
