package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IWorkspaceFormFactory;
import edu.asu.spring.quadriga.web.workspace.backing.ModifyWorkspaceForm;

@Service
public class WorkspaceFormFactory implements IWorkspaceFormFactory 
{
	
	@Override
	public ModifyWorkspaceForm createModifyWorkspaceForm()
	{
		return new ModifyWorkspaceForm();
	}

}
