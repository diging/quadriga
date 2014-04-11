package edu.asu.spring.quadriga.domain.workspace;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.INetwork;

public interface IWorkspaceNetwork 
{
	public abstract String getWorkspaceId();
	
	public abstract void setWorkspaceId(String workspaceId);
	
	public abstract List<INetwork> getNetworks();
	
	public abstract void setNetworks(List<INetwork> networks);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
