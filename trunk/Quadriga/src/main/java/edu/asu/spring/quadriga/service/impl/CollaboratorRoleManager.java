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
	
	/*@Override
	public void setCollaboratorRole(List<ICollaboratorRole> collaboratorroles) {
		this.projectCollabRoles = collaboratorroles;
		this.ccCollabRoles = collaboratorroles;
		
	}
	
	@Override
	public List<ICollaboratorRole> getCollaboratorRoles() {
		
		return this.projectCollabRoles;
	} */
	
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
		// TODO Auto-generated method stub
		return this.ccCollabRoles;
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
	/*@Override
	public void fillCollaboratorRole(ICollaboratorRole collaboratorRole) {
		
		for(ICollaboratorRole role: projectCollabRoles)
		{
			if(role.getRoleDBid().equals(collaboratorRole.getRoleDBid()))
			{
				collaboratorRole.setRoleid(role.getRoleid());
				System.out.println("-------------------"+role.getRoleid());
				// add rest of role information to collaboratorRole object
			}
		}
		
			
	} */
	
	@Override
	public void fillProjectCollaboratorRole(ICollaboratorRole collaboratorRole) {
		
		for(ICollaboratorRole role: projectCollabRoles)
		{
			if(role.getRoleDBid().equals(collaboratorRole.getRoleDBid()))
			{
				collaboratorRole.setRoleid(role.getRoleid());
				System.out.println("-------------------"+role.getRoleid());
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
				System.out.println("-------------------"+role.getRoleid());
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
		
		
		for(ICollaboratorRole role: projectCollabRoles)
		{
			if(role.getRoleid().equals(collaboratorRoleId))
			{
				return role;
			}
		}
		
		return null;	
	}




}
