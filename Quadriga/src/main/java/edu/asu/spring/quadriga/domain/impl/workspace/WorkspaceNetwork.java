package edu.asu.spring.quadriga.domain.impl.workspace;

import java.util.Date;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.workspace.IWorkSpace;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceNetwork;

public class WorkspaceNetwork implements IWorkspaceNetwork 
{
	private IWorkSpace workspace;
	private INetwork network;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	@Override
	public IWorkSpace getWorkspace() {
		return workspace;
	}

	@Override
	public void setWorkspace(IWorkSpace workspace) {
         this.workspace = workspace;		
	}

	@Override
	public INetwork getNetwork() {
		return network;
	}

	@Override
	public void setNetwork(INetwork network) {
         this.network = network;		
	}
	
	@Override
	public String getCreatedBy() {
		return createdBy;
	}

	@Override
	public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
	}

	@Override
	public Date getCreatedDate() {
		return createdDate;
	}

	@Override
	public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
	}

	@Override
	public String getUpdatedBy() {
		return updatedBy;
	}

	@Override
	public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
	}

	@Override
	public Date getUpdatedDate() {
		return updatedDate;
	}

	@Override
	public void setUpdatedDate(Date updatedDate) {
         this.updatedDate = updatedDate;
	}

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result + ((network == null) ? 0 : network.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime * result
				+ ((workspace == null) ? 0 : workspace.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkspaceNetwork other = (WorkspaceNetwork) obj;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (createdDate == null) {
			if (other.createdDate != null)
				return false;
		} else if (!createdDate.equals(other.createdDate))
			return false;
		if (network == null) {
			if (other.network != null)
				return false;
		} else if (!network.equals(other.network))
			return false;
		if (updatedBy == null) {
			if (other.updatedBy != null)
				return false;
		} else if (!updatedBy.equals(other.updatedBy))
			return false;
		if (updatedDate == null) {
			if (other.updatedDate != null)
				return false;
		} else if (!updatedDate.equals(other.updatedDate))
			return false;
		if (workspace == null) {
			if (other.workspace != null)
				return false;
		} else if (!workspace.equals(other.workspace))
			return false;
		return true;
	}*/
}
