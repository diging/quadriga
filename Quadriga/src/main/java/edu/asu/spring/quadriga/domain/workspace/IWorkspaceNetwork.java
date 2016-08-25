package edu.asu.spring.quadriga.domain.workspace;

import java.util.Date;

import edu.asu.spring.quadriga.domain.network.INetwork;

public interface IWorkspaceNetwork 
{
	public abstract IWorkSpace getWorkspace();
	
	public abstract void setWorkspace(IWorkSpace workspace);
	
	public abstract INetwork getNetwork();
	
	public abstract void setNetwork(INetwork network);
	
	public abstract String getCreatedBy();
	
	public abstract void setCreatedBy(String createdBy);
	
	public abstract Date getCreatedDate();
	
	public abstract void setCreatedDate(Date createdDate);
	
	public abstract String getUpdatedBy();
	
	public abstract void setUpdatedBy(String updatedBy);
	
	public abstract Date getUpdatedDate();
	
	public abstract void setUpdatedDate(Date updatedDate);

}
