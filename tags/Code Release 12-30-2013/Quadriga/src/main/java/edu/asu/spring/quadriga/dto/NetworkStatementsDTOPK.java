package edu.asu.spring.quadriga.dto;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Embeddable;

@Embeddable
public class NetworkStatementsDTOPK implements Serializable 
{
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	private String networkid;
	
	@Basic(optional = false)
	private String statementid;

	public NetworkStatementsDTOPK()
	{
		
	}

	public NetworkStatementsDTOPK(String networkid, String statementid) {
		this.networkid = networkid;
		this.statementid = statementid;
	}

	
	public String getNetworkid() {
		return networkid;
	}
	

	public void setNetworkid(String networkid) {
		this.networkid = networkid;
	}

	public String getStatementid() {
		return statementid;
	}

	public void setStatementid(String statementid) {
		this.statementid = statementid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((networkid == null) ? 0 : networkid.hashCode());
		result = prime * result
				+ ((statementid == null) ? 0 : statementid.hashCode());
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
		NetworkStatementsDTOPK other = (NetworkStatementsDTOPK) obj;
		if (networkid == null) {
			if (other.networkid != null)
				return false;
		} else if (!networkid.equals(other.networkid))
			return false;
		if (statementid == null) {
			if (other.statementid != null)
				return false;
		} else if (!statementid.equals(other.statementid))
			return false;
		return true;
	}


}
