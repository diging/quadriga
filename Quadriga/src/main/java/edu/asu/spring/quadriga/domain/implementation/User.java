package edu.asu.spring.quadriga.domain.implementation;

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
public class User implements IUser 
{
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
			//Check if username matches for the user objects
			if(this.userName.equalsIgnoreCase(user.getUserName()))
			{
				//Check if all the roles match for the user objects
				List<IQuadrigaRole> userRoles = user.getQuadrigaRoles();
				if(this.quadrigaRoles.size() == userRoles.size())
				{				
					for(int i=0;i<this.quadrigaRoles.size();i++)
					{
						userRoles.get(i).compareQuadrigaRole(this.quadrigaRoles.get(i));				
					}
					return true;
				}
			}		
		}
		return false;
	}	
}
