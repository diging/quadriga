package edu.asu.spring.quadriga.domain.workbench;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;

public interface IProjectCollaborator 
{
	public abstract String getProjectId();
	
	public abstract void setProjectId(String projectId);
	
	public abstract List<ICollaborator> getCollaborators();

	public abstract void setCollaborators(List<ICollaborator> collaborators);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdateDate(Date updatedDate);
	
}
