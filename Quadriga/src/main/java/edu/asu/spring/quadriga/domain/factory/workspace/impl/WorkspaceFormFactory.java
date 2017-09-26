package edu.asu.spring.quadriga.domain.factory.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFormFactory;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspace;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceForm;

/**
 * Factory class to create ModifyWorkspaceForm object
 * @author kiran batna
 *
 */
@Service
public class WorkspaceFormFactory implements IWorkspaceFormFactory 
{
	
	@Override
	public ModifyWorkspaceForm createModifyWorkspaceForm()
	{
		return new ModifyWorkspaceForm();
	}

	@Override
	public ModifyWorkspaceForm cloneModifyWorkspaceFormObject(
			ModifyWorkspaceForm workspaceForm) {
		ModifyWorkspaceForm clone = new ModifyWorkspaceForm();
		List<ModifyWorkspace> workspaceList = new ArrayList<ModifyWorkspace>();
		for(ModifyWorkspace workspace : workspaceForm.getWorkspaceList())
		{
			workspaceList.add(workspace);
		}
		clone.setWorkspaceList(workspaceList);
		return clone;
	}



}
