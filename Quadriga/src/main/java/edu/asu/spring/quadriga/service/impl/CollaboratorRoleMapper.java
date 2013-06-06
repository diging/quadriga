package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.service.ICollaboratorRoleMapper;

@Service
public class CollaboratorRoleMapper implements ICollaboratorRoleMapper{

	@Autowired 
	private List<ICollaboratorRole> collaboratorRoles;

	
	@Override
	public void setCollaboratorRole(List<ICollaboratorRole> collaboratorroles) {
		this.collaboratorRoles = collaboratorroles;
		
	}
	
	@Override
	public List<ICollaboratorRole> getCollaboratorRole() {
		
		return this.collaboratorRoles;
	}


	@Override
	public ICollaboratorRole getCollaboratorRoles(String collaboratorRoleDBId) {
		
		
		for(ICollaboratorRole role: collaboratorRoles)
		{
			if(role.getRoleDBid().equals(collaboratorRoleDBId))
			{
				return role;
			}
		}
		
		return null;
		
	}

	
public String getCollaboratorRoleId(String collaboratorRoleId) {
		
		
		for(ICollaboratorRole role: collaboratorRoles)
		{
			if(role.getRoleid().equals(collaboratorRoleId))
			{
				return role.getRoleDBid();
			}
		}
		
		return null;
		
	}

	
	

}
