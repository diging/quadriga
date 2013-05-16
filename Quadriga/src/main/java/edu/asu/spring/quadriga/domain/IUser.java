package edu.asu.spring.quadriga.domain;

import java.util.List;

public interface IUser 
{

	public abstract void setQuadrigaRoles(List<IQuadrigaRoles> quadrigaRoles);

	public abstract List<IQuadrigaRoles> getQuadrigaRoles();

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setUserName(String userName);

	public abstract String getUserName();

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setEmail(String email);

	public abstract String getEmail();
	
   
}
