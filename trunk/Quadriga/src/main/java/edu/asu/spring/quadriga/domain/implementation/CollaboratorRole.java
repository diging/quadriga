package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
/**
 * @description : CollaboratorRole class describing the properties 
 *                of a CollaboratorRole object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class CollaboratorRole implements ICollaboratorRole 
{
	private String roleDBid;
	private String roleid;
	private String rolename;
	private String roledescription;
	private String displayName;

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String getRoleDBid() 
	{
		return roleDBid;
	}
	
	@Override
	public void setRoleDBid(String roleDBid) 
	{
		this.roleDBid = roleDBid;
	}
	
	@Override
	public String getRoleid() {
		return roleid;
	}
	@Override
	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	@Override
	public String getRolename() {
		return rolename;
	}
	@Override
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	@Override
	public String getRoledescription() {
		return roledescription;
	}
	@Override
	public void setRoledescription(String roledescription) {
		this.roledescription = roledescription;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((displayName == null) ? 0 : displayName.hashCode());
		result = prime * result
				+ ((roleDBid == null) ? 0 : roleDBid.hashCode());
		result = prime * result
				+ ((roledescription == null) ? 0 : roledescription.hashCode());
		result = prime * result + ((roleid == null) ? 0 : roleid.hashCode());
		result = prime * result
				+ ((rolename == null) ? 0 : rolename.hashCode());
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
		CollaboratorRole other = (CollaboratorRole) obj;
		if (displayName == null) {
			if (other.displayName != null)
				return false;
		} else if (!displayName.equals(other.displayName))
			return false;
		if (roleDBid == null) {
			if (other.roleDBid != null)
				return false;
		} else if (!roleDBid.equals(other.roleDBid))
			return false;
		if (roledescription == null) {
			if (other.roledescription != null)
				return false;
		} else if (!roledescription.equals(other.roledescription))
			return false;
		if (roleid == null) {
			if (other.roleid != null)
				return false;
		} else if (!roleid.equals(other.roleid))
			return false;
		if (rolename == null) {
			if (other.rolename != null)
				return false;
		} else if (!rolename.equals(other.rolename))
			return false;
		return true;
	}
}
