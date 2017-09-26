package edu.asu.spring.quadriga.domain.factory.workspace.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;
import edu.asu.spring.quadriga.domain.workspace.impl.Workspace;

/**
 * @description  : Factory class for creating {@link Workspace}.
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
	public IWorkspace createWorkspaceObject()
	{
		return new Workspace();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public IWorkspace cloneWorkspaceObject(IWorkspace workspace)
	{
		IWorkspace clone = createWorkspaceObject();
		clone.setWorkspaceName(workspace.getWorkspaceName());
		clone.setDescription(workspace.getDescription());
		clone.setWorkspaceId(workspace.getWorkspaceId());
		
		IUser owner = workspace.getOwner();
		
		clone.setOwner(owner);
		
		List<IWorkspaceCollaborator> collaborator = new ArrayList<IWorkspaceCollaborator>();
		
		for(IWorkspaceCollaborator workspaceCollaborator : workspace.getWorkspaceCollaborators())
		{
			collaborator.add(workspaceCollaborator);
		}
		
		clone.setWorkspaceCollaborators(collaborator);
		
		return clone;
	}

}
