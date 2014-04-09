package edu.asu.spring.quadriga.domain.factories.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.implementation.WorkSpace;

/**
 * @description  : Factory class for creating {@link WorkSpace}.
 * 
 * @author       : Kiran Kumar Batna
 *
 */
@Service
public class WorkspaceFactory implements IWorkspaceFactory 
{
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IWorkSpace createWorkspaceObject()
	{
		return new WorkSpace();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IWorkSpace cloneWorkspaceObject(IWorkSpace workspace)
	{
		IWorkSpace clone = createWorkspaceObject();
		clone.setWorkspaceName(workspace.getWorkspaceName());
		clone.setDescription(workspace.getDescription());
		clone.setWorkspaceId(workspace.getWorkspaceId());
		
		IUser owner = workspace.getOwner();
		
		clone.setOwner(owner);
		
		List<ICollaborator> collaborator = new ArrayList<ICollaborator>();
		
		for(ICollaborator collaboratorUser : workspace.getCollaborators())
		{
			collaborator.add(collaboratorUser);
		}
		
		clone.setCollaborators(collaborator);
		
		return clone;
	}

}
