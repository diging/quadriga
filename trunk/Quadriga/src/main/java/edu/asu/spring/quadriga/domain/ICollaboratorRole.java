package edu.asu.spring.quadriga.domain;

/**
 * @description   : interface to implement CollaboratorRole.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface ICollaboratorRole 
{

	/**
	 * Method to retrieve the role id stored in the database.
	 * @return - role id stored in the database.
	 */
	public abstract String getRoleDBid();
	
	
    /**
     * Method to set the role id to store in the database.
     * @param roleDBid - role id to be stored in the database.
     */
	public abstract void setRoleDBid(String roleDBid);
	
	/**
	 * Method to set the role id which is used in UI pages.
	 * @param roleid - collaborator role id used in the UI pages.
	 */
	public abstract void setRoleid(String roleid);

	/**
	 * Method to retrieve the role id
	 * @return roleid - collaborator role id used in the UI pages.
	 */
	public abstract String getRoleid();
	
	/**
	 * Method to set the collaborator role name.
	 * @param rolename - collaborator role name.
	 */
	public abstract void setRolename(String rolename);

	/**
	 * Method to retrieve the collaborator role name.
	 * @return rolename - collaborator role name.
	 */
	public abstract String getRolename();
	
	/**
	 * Method to set the display name to the collaborator role.
	 * @param displayName - it is the display name given to a collaborator role.
	 */
	public abstract void setDisplayName(String displayName);
	
	/**
	 * Method to retrieve the display name associated with the collaborator role.
	 * @return displayName - common name associated with the collaborator to be displayed.
	 */
	public abstract String getDisplayName();
	
	/**
	 * Method to set the description associated with the collaborator role.
	 * @param roledescription - text describing the collaborator role.
	 */
	public abstract void setRoledescription(String roledescription);

	/**
	 * Method to retrieve the description associated with the collaborator role.
	 * @return role description - text describing the collaborator role. 
	 */
	public abstract String getRoledescription();

}
