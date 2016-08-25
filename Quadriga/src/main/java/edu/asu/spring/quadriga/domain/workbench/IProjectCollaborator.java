package edu.asu.spring.quadriga.domain.workbench;

import java.util.Date;

import edu.asu.spring.quadriga.domain.ICollaborator;

public interface IProjectCollaborator 
{
	public abstract IProject getProject();
	
	public abstract void setProject(IProject project);

	public abstract ICollaborator getCollaborator();
	
	public abstract void setCollaborator(ICollaborator collaborator);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdateDate(Date updatedDate);
	
}
