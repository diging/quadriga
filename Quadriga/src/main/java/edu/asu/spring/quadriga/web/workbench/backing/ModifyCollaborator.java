package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;

/**
 * This class acts as a domain object to hold collaborator  to display 
 * as a Model attribute in the UI page
 * @author Kiran Kumar Batna
 */
@Service
public class ModifyCollaborator  
{
	private String userName;
	private String name;
	private List<IQuadrigaRole> collaboratorRoles;
	private List<String> roleIds;
	
	public ModifyCollaborator()
	{
		
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<IQuadrigaRole> getCollaboratorRoles() {
		return collaboratorRoles;
	}
	
	public void setCollaboratorRoles(List<IQuadrigaRole> collaboratorRoles) {
		this.collaboratorRoles = collaboratorRoles;
	}

    public List<String> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds) {
        this.roleIds = roleIds;
    }
}
