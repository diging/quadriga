package edu.asu.spring.quadriga.domain;

/**
 * @description   : interface to implement CollaboratorRole.
 * 
 * @author        : Kiran
 *
 */
public interface ICollaboratorRole 
{

	public abstract void setRoledescription(String roledescription);

	public abstract String getRoledescription();

	public abstract void setRolename(String rolename);

	public abstract String getRolename();

	public abstract void setRoleid(String roleid);

	public abstract String getRoleid();

	public abstract void setRoleDBid(String roleDBid);

	public abstract String getRoleDBid();

}
