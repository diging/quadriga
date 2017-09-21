package edu.asu.spring.quadriga.domain.network.impl;

import edu.asu.spring.quadriga.domain.network.INetworkNodeInfo;

public class NetworkNodeInfo implements INetworkNodeInfo 
{
	
    private String id;
    private String statementType;
    private int version;
    private int isTop;
    
	
	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
         this.id = id;
	}

	@Override
	public String getStatementType() {
		return statementType;
	}

	@Override
	public void setStatementType(String statementType) {
        this.statementType = statementType;
	}

	@Override
	public void setVersion(int version) {
         this.version = version;
	}

	@Override
	public int getVersion() {
		return version;
	}

	@Override
	public void setIsTop(int isTop) {
        this.isTop = isTop;
	}

	@Override
	public int getIsTop() {
		return isTop;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + isTop;
		result = prime * result
				+ ((statementType == null) ? 0 : statementType.hashCode());
		result = prime * result + version;
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
		NetworkNodeInfo other = (NetworkNodeInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (isTop != other.isTop)
			return false;
		if (statementType == null) {
			if (other.statementType != null)
				return false;
		} else if (!statementType.equals(other.statementType))
			return false;
		if (version != other.version)
			return false;
		return true;
	}
}
