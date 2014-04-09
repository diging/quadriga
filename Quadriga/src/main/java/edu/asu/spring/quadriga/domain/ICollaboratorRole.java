package edu.asu.spring.quadriga.domain;

/**
 * @description   : interface to implement CollaboratorRole.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface ICollaboratorRole 
{

	public abstract String getRoleDBid();
	
	public abstract void setRoleDBid(String roleDBid);
	
	public abstract void setRoleid(String roleid);

	public abstract String getRoleid();
	
	public abstract void setRolename(String rolename);

	public abstract String getRolename();
	
	public abstract void setDisplayName(String displayName);
	
	public abstract String getDisplayName();
	
	public abstract void setRoledescription(String roledescription);

	public abstract String getRoledescription();

}
