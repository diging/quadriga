package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * @description : User class describing the properties 
 *                of a User object
 * 
 * @author      : Kiran Kumar Batna
 *
 */
public class User implements IUser 
{
	private String name;
	private String userName;
	private String password;
	private String email;
	private List<IQuadrigaRole> quadrigaRoles;
	private String collaborator;
	
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
	public String getProjectOwner() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setProjectOwner(String owner) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setProjectCollaborator(String collaborator) {
		this.collaborator = collaborator;
		
	}
	

}
