package edu.asu.spring.quadriga.web.users.backingbean;

import java.util.List;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;

/**
 * This class acts as a domain object to hold users  to display 
 * as a Model attribute in the UI page
 * @author Kiran Kumar Batna
 */
public class ModifyQuadrigaUser 
{
	private String userName;
	private String name;
	private List<IQuadrigaRole> quadrigaRoles;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<IQuadrigaRole> getQuadrigaRoles() {
		return quadrigaRoles;
	}
	public void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles) {
		this.quadrigaRoles = quadrigaRoles;
	}
}
