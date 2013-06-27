package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
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
	@Qualifier("projectCollaborator")
	private List<ICollaboratorRole> projectCollabRoles;
	
	@Autowired
	@Qualifier("ccCollaborator")
	private List<ICollaboratorRole> ccCollabRoles;
	
	@Autowired
	@Qualifier("dictCollaborator")
	private List<ICollaboratorRole> dictCollabRoles;
	

	@Override
	public void setProjectCollaboratorRole(List<ICollaboratorRole> collaboratorRoles) {
		this.projectCollabRoles = collaboratorRoles;
	}

	@Override
	public List<ICollaboratorRole> getProjectCollaboratorRoles() {
		return this.projectCollabRoles;
	}

	@Override
	public void setCollectionCollaboratorRole(List<ICollaboratorRole> collaboratorRoles) {
		this.ccCollabRoles = collaboratorRoles;
		
	}

	@Override
	public List<ICollaboratorRole> getCollectionCollaboratorRoles() {
		return this.ccCollabRoles;
	}
	
	@Override
	public void setDictCollaboratorRole(List<ICollaboratorRole> collaboratorRoles) {
		this.dictCollabRoles = collaboratorRoles;		
	}

	@Override
	public List<ICollaboratorRole> getDictCollaboratorRoles() {
		return this.dictCollabRoles;
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
	public void fillProjectCollaboratorRole(ICollaboratorRole collaboratorRole) {
		
		for(ICollaboratorRole role: projectCollabRoles)
		{
			if(role.getRoleDBid().equals(collaboratorRole.getRoleDBid()))
			{
				collaboratorRole.setRoleid(role.getRoleid());
				// add rest of role information to collaboratorRole object
			}
		}
		
	}

	@Override
	public void fillCollectionCollaboratorRole(ICollaboratorRole collaboratorRole) {
		
		for(ICollaboratorRole role: ccCollabRoles)
		{
			if(role.getRoleDBid().equals(collaboratorRole.getRoleDBid()))
			{
				collaboratorRole.setRoleid(role.getRoleid());
				// add rest of role information to collaboratorRole object
			}
		}
		
	}
	
	@Override
	public void fillDictCollaboratorRole(ICollaboratorRole collaboratorRole) {

		for(ICollaboratorRole role: dictCollabRoles)
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

	@Override
	public ICollaboratorRole getProjectCollaboratorRoleById(
			String collaboratorRoleId) {

		for(ICollaboratorRole role: projectCollabRoles)
		{
			if(role.getRoleid().equals(collaboratorRoleId))
			{
				return role;
			}
		}
		
		return null;	
	}

	@Override
	public ICollaboratorRole getCCCollaboratorRoleById(String collaboratorRoleId) {

		for(ICollaboratorRole role: ccCollabRoles)
		{
			if(role.getRoleid().equals(collaboratorRoleId))
			{
				return role;
			}
		}
		
		return null;	
	}

	@Override
	public ICollaboratorRole getDictCollaboratorRoleById(String collaboratorRoleId) {
		
		for(ICollaboratorRole role: dictCollabRoles)
		{
			if(role.getRoleid().equals(collaboratorRoleId))
			{
				return role;
			}
		}
		return null;
	}

	
	

}
