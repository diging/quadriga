package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * @description   	 interface to implement User class.
 * 
 * @author        	 Kiran Kumar Batna
 * @author 			 Ram Kumar Kumaresan
 */
public interface IUser 
{

	public abstract String getName();
	
	public abstract void setName(String name);
	
	public abstract String getUserName();
	
	public abstract void setUserName(String userName);
	
	public abstract String getEmail();
	
	public abstract void setEmail(String email);
	
	public abstract String getPassword();
	
	public abstract void setPassword(String password);
	
	public abstract List<IQuadrigaRole> getQuadrigaRoles();
	
	public abstract void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles);
}
