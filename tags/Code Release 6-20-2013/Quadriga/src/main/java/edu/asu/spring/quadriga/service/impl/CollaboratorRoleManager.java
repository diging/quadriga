package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
/**
 *@description: this class maps incoming database collaborator roles to the roles defined in the
 *				collaborators-roles.xml
 *
 * @author 		rohit pendbhaje
 *
 */
@Service
public class CollaboratorRoleManager implements ICollaboratorRoleManager{

	@Autowired 
	private List<ICollaboratorRole> collaboratorRoles;

	
	@Override
	public void setCollaboratorRole(List<ICollaboratorRole> collaboratorroles) {
		this.collaboratorRoles = collaboratorroles;
		
	}
	
	@Override
	public List<ICollaboratorRole> getCollaboratorRoles() {
		
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
	public void fillCollaboratorRole(ICollaboratorRole collaboratorRole) {
		
		
		for(ICollaboratorRole role: collaboratorRoles)
		{
			if(role.getRoleDBid().equals(collaboratorRole.getRoleDBid()))
			{
				collaboratorRole.setRoleid(role.getRoleid());
				// add rest of role information to collaboratorRole object
			}
		}
		
			
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
public ICollaboratorRole getCollaboratorRoleById(String collaboratorRoleId) {
		
		
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
