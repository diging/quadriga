package edu.asu.spring.quadriga.domain.impl.networks;

import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.enums.ENetworkAccessibility;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.workspace.IWorkspace;

public class Network implements INetwork 
{
	private String networkId;
	private String networkName;
	private String textUrl;
	private Date creationTime;
	private IUser creator;
	private ENetworkAccessibility networksAccess;
	private String status;
	private int versionNumber;
	private List<INetworkNodeInfo> networkNodes;
	private String assignedUser;
	private IWorkspace workspace;
	private String createdBy;
	private Date createdDate;
	private String updatedBy;
	private Date updatedDate;

	@Override
	public String getNetworkId() {
		return networkId;
	}

	@Override
	public void setNetworkId(String networkId) {
         this.networkId = networkId;
	}

	@Override
	public String getNetworkName() {
		return networkName;
	}

	@Override
	public void setNetworkName(String networkName) {
       this.networkName = networkName;
	}

	@Override
	public void setTextUrl(String textUrl) {
        this.textUrl = textUrl;
	}

	@Override
	public String getTextUrl() {
		return textUrl;
	}

	@Override
	public void setCreationTime(Date creationTime) {
         this.creationTime = creationTime;
	}

	@Override
	public Date getCreationTime() {
		return creationTime;
	}

	@Override
	public void setCreator(IUser creator) {
         this.creator = creator;
	}

	@Override
	public IUser getCreator() {
		return creator;
	}

	@Override
	public void setNetworksAccess(ENetworkAccessibility networksAccess) {
         this.networksAccess = networksAccess;
	}

	@Override
	public ENetworkAccessibility getNetworksAccess() {
		return networksAccess;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public void setStatus(String status) {
        this.status = status;
	}

	@Override
	public int getVersionNumber() {
		return versionNumber;
	}

	@Override
	public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
	}

	@Override
	public List<INetworkNodeInfo> getNetworkNodes() {
		return networkNodes;
	}

	@Override
	public void setNetworkNodes(List<INetworkNodeInfo> networkNodes) {
        this.networkNodes = networkNodes;
	}

	@Override
	public String getAssignedUser() {
		return assignedUser;
	}

	@Override
	public void setAssignedUser(String assignedUser) {
        this.assignedUser = assignedUser;
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
	
    public void setWorkspace(IWorkspace workspace) {
        this.workspace = workspace;
    }

    public IWorkspace getWorkspace() {
        return workspace;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assignedUser == null) ? 0 : assignedUser.hashCode());
		result = prime * result
				+ ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result
				+ ((createdDate == null) ? 0 : createdDate.hashCode());
		result = prime * result
				+ ((creationTime == null) ? 0 : creationTime.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result
				+ ((networkId == null) ? 0 : networkId.hashCode());
		result = prime * result
				+ ((networkName == null) ? 0 : networkName.hashCode());
		result = prime * result
				+ ((networkNodes == null) ? 0 : networkNodes.hashCode());
		result = prime * result
				+ ((networksAccess == null) ? 0 : networksAccess.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((textUrl == null) ? 0 : textUrl.hashCode());
		result = prime * result
				+ ((updatedBy == null) ? 0 : updatedBy.hashCode());
		result = prime * result
				+ ((updatedDate == null) ? 0 : updatedDate.hashCode());
		result = prime * result + versionNumber;
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
		Network other = (Network) obj;
		if (assignedUser == null) {
			if (other.assignedUser != null)
				return false;
		} else if (!assignedUser.equals(other.assignedUser))
			return false;
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
		if (creationTime == null) {
			if (other.creationTime != null)
				return false;
		} else if (!creationTime.equals(other.creationTime))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (networkId == null) {
			if (other.networkId != null)
				return false;
		} else if (!networkId.equals(other.networkId))
			return false;
		if (networkName == null) {
			if (other.networkName != null)
				return false;
		} else if (!networkName.equals(other.networkName))
			return false;
		if (networkNodes == null) {
			if (other.networkNodes != null)
				return false;
		} else if (!networkNodes.equals(other.networkNodes))
			return false;
		if (networksAccess != other.networksAccess)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (textUrl == null) {
			if (other.textUrl != null)
				return false;
		} else if (!textUrl.equals(other.textUrl))
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
		if (versionNumber != other.versionNumber)
			return false;
		return true;
	}


}
