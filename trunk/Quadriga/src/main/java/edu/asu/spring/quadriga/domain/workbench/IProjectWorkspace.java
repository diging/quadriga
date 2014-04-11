package edu.asu.spring.quadriga.domain.workbench;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;

public interface IProjectWorkspace 
{
	public abstract String getProjectId();
	
	public abstract void setProjectId(String projectId);
	
	public abstract List<IWorkSpace> getWorkspaces();
	
	public abstract void setWorkspaces(List<IWorkSpace> workspaces);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
