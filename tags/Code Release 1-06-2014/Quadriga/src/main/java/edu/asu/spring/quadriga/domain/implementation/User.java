package edu.asu.spring.quadriga.domain.implementation;

import java.io.Serializable;
import java.util.List;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * @description : User class describing the properties 
 *                of a User object
 * 
 * @author      : Kiran Kumar Batna
 * @author 		: Ram Kumar Kumaresan
 */
public class User implements IUser, Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8936305356496585075L;
	
	
	private String name;
	private String userName;
	private String password;
	private String email;
	private List<IQuadrigaRole> quadrigaRoles;

	@Override
	public String getEmail() {
		return email;
	}
	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getUserName() {
		return userName;
	}
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public List<IQuadrigaRole> getQuadrigaRoles() {
		return quadrigaRoles;
	}
	@Override
	public void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles) {
		this.quadrigaRoles = quadrigaRoles;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQuadrigaRolesDBId()
	{
		StringBuilder sRoleDBIds = new StringBuilder();
		for(IQuadrigaRole role:quadrigaRoles)
		{
			if(sRoleDBIds.length()==0)
				sRoleDBIds.append(role.getDBid());
			else
				sRoleDBIds.append(","+role.getDBid());
		}
		return sRoleDBIds.toString();
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result
				+ ((quadrigaRoles == null) ? 0 : quadrigaRoles.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		
		if(obj == null)
			return false;
		
		if(this == obj)
			return true;
		
		if(this.getClass() != obj.getClass())
			return false;
		
		User user = (User) obj;
		//Check the username
		if(this.userName == null)
		{
			if(user.userName != null)
				return false;
		}
		else if(!this.userName.equals(user.userName))
		{
			//One of the value is null and the other is not null (or) the values do not match
			return false;
		}
		
		//Check the name
		if(this.name == null)
		{
			if(user.name != null)
				return false;
		}
		else if(!this.name.equals(user.name))
		{
			//One of the value is null and the other is not null (or) the values do not match
			return false;
		}
		
		//Check the password
		if(this.password == null)
		{
			if(user.password != null)
				return false;
		}
		else if(!this.password.equals(user.password))
		{
			//One of the value is null and the other is not null (or) the values do not match
			return false;
		}
		
		//Check the email
		if(this.email == null)
		{
			if(user.email != null)
				return false;
		}
		else if(!this.email.equals(user.email))
		{
			//One of the value is null and the other is not null (or) the values do not match
			return false;
		}
		
		//Check the quadrigaRoles
		if(this.quadrigaRoles == null)
		{
			if(user.quadrigaRoles != null)
				return false;
		}
		else if(!this.quadrigaRoles.equals(user.quadrigaRoles))
		{
			//One of the value is null and the other is not null (or) the values do not match
			return false;
		}
		return true;
	}	
}
