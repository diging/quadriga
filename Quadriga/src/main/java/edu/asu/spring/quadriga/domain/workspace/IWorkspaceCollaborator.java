package edu.asu.spring.quadriga.domain.workspace;

import java.util.Date;

import edu.asu.spring.quadriga.domain.ICollaborator;

public interface IWorkspaceCollaborator 
{
	public abstract IWorkspace getWorkspace();
	
	public abstract void setWorkspace(IWorkspace workspace);
	
	public abstract ICollaborator getCollaborator();
	
	public abstract void setCollaborator(ICollaborator collaborator);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);
}
