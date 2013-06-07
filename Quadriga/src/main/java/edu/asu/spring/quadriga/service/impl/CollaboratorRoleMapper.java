package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.service.ICollaboratorRoleMapper;
/**
 *@description: this class maps incoming database collaborator roles to the roles defined in the
 *				collaborators-roles.xml
 *
 * @author 		rohit pendbhaje
 *
 */
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

/**
 * @description: maps roleDBid of the collaborator roles from database to xml 
 * 
 * @param collaboratorRoleId incoming collaborator DBid from the database
 * 
 * @author rohit pendbhaje
 * 
 * @return collaborator role object
 * 
 * 
 */
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

/**
 * @description  maps roleid of the collaborator roles from database to xml 
 * 
 * @param collaboratorRoleId incoming collaborator id from the database
 * 
 * @author rohit pendbhaje
 * 
 * @return collaborator role object
 * 
 */
public ICollaboratorRole getCollaboratorRoleId(String collaboratorRoleId) {
		
		
		for(ICollaboratorRole role: collaboratorRoles)
		{
			if(role.getRoleid().equals(collaboratorRoleId))
			{
				return role;
			}
		}
		
		return null;	
	}

}
