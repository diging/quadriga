package edu.asu.spring.quadriga.domain.factory.impl.workspace;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceCollaborator;

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
		
		List<IWorkspaceCollaborator> collaborator = new ArrayList<IWorkspaceCollaborator>();
		
		for(IWorkspaceCollaborator workspaceCollaborator : workspace.getWorkspaceCollaborators())
		{
			collaborator.add(workspaceCollaborator);
		}
		
		clone.setWorkspaceCollaborators(collaborator);
		
		return clone;
	}

}
