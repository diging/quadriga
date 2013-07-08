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


	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean compareUserObjects(IUser user)
	{
		if(user!=null && user instanceof IUser)
		{
			//Check the user name
			if(this.userName !=null && user.getUserName() !=null)
			{
				//Check if username matches for the user objects
				if(!(this.userName.equals(user.getUserName())))
				{
					return false;
				}
			}
			else if(!(this.userName == null && user.getUserName() == null))
			{
				//One of the value is null and the other is not null
				return false;
			}

			//Check the roles for user objects
			if(this.quadrigaRoles != null && user.getQuadrigaRoles() != null)
			{
				//Check if all the roles match for the user objects
				List<IQuadrigaRole> userRoles = user.getQuadrigaRoles();
				if(this.quadrigaRoles.size() == userRoles.size())
				{				
					for(int i=0;i<this.quadrigaRoles.size();i++)
					{
						if(!(userRoles.get(i).compareQuadrigaRole(this.quadrigaRoles.get(i))))
						{
							return false;
						}
					}
					return true;
				}
			}
			else if(this.quadrigaRoles == null && user.getQuadrigaRoles() == null)
			{
				//Both the roles are null
				return true;
			}
		}
		return false;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (quadrigaRoles == null) {
			if (other.quadrigaRoles != null)
				return false;
		} else if (!quadrigaRoles.equals(other.quadrigaRoles))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}	
}
