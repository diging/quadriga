package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;

@Service
public class ModifyCollaborator  
{
	private String userName;
	private String name;
	private List<ICollaboratorRole> collaboratorRoles;
	
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
	
	public List<ICollaboratorRole> getCollaboratorRoles() {
		return collaboratorRoles;
	}
	
	public void setCollaboratorRoles(List<ICollaboratorRole> collaboratorRoles) {
		this.collaboratorRoles = collaboratorRoles;
	}
}
