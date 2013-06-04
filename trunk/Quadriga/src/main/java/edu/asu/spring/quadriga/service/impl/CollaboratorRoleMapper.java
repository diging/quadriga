package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.service.ICollaboratorRoleMapper;

public class CollaboratorRoleMapper implements ICollaboratorRoleMapper{

	@Autowired 
	List<ICollaboratorRole> CollaboratorRoles;

	
	@Override
	public void setCollaboratorRole(List<ICollaboratorRole> collaboratorroles) {
		this.CollaboratorRoles = collaboratorroles;
		
	}

	@Override
	public ICollaboratorRole getCollaboratorRoles(String collaboratorRoleDBId) {
		
		
		for(ICollaboratorRole role: CollaboratorRoles)
		{
			if(role.getRoleDBid().equals(collaboratorRoleDBId))
			{
				return role;
			}
		}
		
		return null;
		
	}

	
public String getCollaboratorRoleId(String collaboratorRoleId) {
		
		
		for(ICollaboratorRole role: CollaboratorRoles)
		{
			if(role.getRoleid().equals(collaboratorRoleId))
			{
				return role.getRoleDBid();
			}
		}
		
		return null;
		
	}

	@Override
	public List<ICollaboratorRole> getCollaboratorRole() {
		
		return this.CollaboratorRoles;
	}

	

}
