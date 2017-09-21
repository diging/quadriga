package edu.asu.spring.quadriga.domain.network;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;

/**
 * @description   : interface to implement Network class.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface INetwork 
{
	
	public abstract String getNetworkId();

	public abstract void setNetworkId(String networkId);
	
	public abstract String getNetworkName();

	public abstract void setNetworkName(String networkName);

	public abstract void setTextUrl(String textUrl);

	public abstract String getTextUrl();

	public abstract void setCreationTime(Date creationTime);

	public abstract Date getCreationTime();

	public abstract void setCreator(IUser creator);

	public abstract IUser getCreator();

	public abstract void setNetworksAccess(ENetworkAccessibility networksAccess);

	public abstract ENetworkAccessibility getNetworksAccess();

	public abstract String getStatus();

	public abstract void setStatus(String status);
	
	public abstract int getVersionNumber();
	
	public abstract void setVersionNumber(int versionNumber);
	
	public abstract List<INetworkNodeInfo> getNetworkNodes();
	
	public abstract void setNetworkNodes(List<INetworkNodeInfo> networkNodes);

	public abstract String getAssignedUser();

	public abstract void setAssignedUser(String assignedUser);

    public abstract String getCreatedBy();
    
    public abstract void setCreatedBy(String createdBy);
    
    public abstract Date getCreatedDate();
    
    public abstract void setCreatedDate(Date createdDate);
    
    public abstract String getUpdatedBy();
    
    public abstract void setUpdatedBy(String updatedBy);
    
    public abstract Date getUpdatedDate();
    
    public abstract void setUpdatedDate(Date updatedDate);

    void setWorkspace(IWorkspace workspace);

    IWorkspace getWorkspace();
}
