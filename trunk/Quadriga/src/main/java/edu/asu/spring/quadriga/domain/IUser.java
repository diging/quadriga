package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * @description   : interface to implement User class.
 * 
 * @author        : Kiran Kumar Batna
 *
 */
public interface IUser 
{

	public abstract void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles);

	public abstract List<IQuadrigaRole> getQuadrigaRoles();

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setUserName(String userName);

	public abstract String getUserName();

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setEmail(String email);

	public abstract String getEmail();

	public abstract String getQuadrigaRolesDBId();
	
	public String getProjectOwner();
	
	public abstract void setProjectOwner(String owner);
	
	public abstract void setProjectCollaborator(String collaborator);
	

	
   
}
