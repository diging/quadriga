package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
	
	@Autowired
	@Qualifier("workspaceCollaborator")
	private List<ICollaboratorRole> wsCollabRoles;
	

	@Override
	public List<ICollaboratorRole> getWsCollabRoles() {
		return wsCollabRoles;
	}

	@Override
	public void setWsCollabRoles(List<ICollaboratorRole> wsCollabRoles) {
		this.wsCollabRoles = wsCollabRoles;
	}

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
				collaboratorRole.setRoleDBid(role.getRoleDBid());
				collaboratorRole.setRolename(role.getRolename());
				collaboratorRole.setRoledescription(role.getRoledescription());
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
				collaboratorRole.setRoleDBid(role.getRoleDBid());
				collaboratorRole.setRolename(role.getRolename());
				collaboratorRole.setRoledescription(role.getRoledescription());
				collaboratorRole.setDisplayName(role.getDisplayName());
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
				collaboratorRole.setRoleDBid(role.getRoleDBid());
				collaboratorRole.setRolename(role.getRolename());
				collaboratorRole.setRoledescription(role.getRoledescription());
				collaboratorRole.setDisplayName(role.getDisplayName());
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
	public ICollaboratorRole getWSCollaboratorRoleByDBId(String collaboratorRoleDBId)
	{
		for(ICollaboratorRole role:wsCollabRoles)
		{
			if(role.getRoleDBid().equals(collaboratorRoleDBId))
			{
				return role;
			}
		}
		return null;
	}
	
	@Override
	public ICollaboratorRole getWSCollaboratorRoleById(String collaboratorRoleId)
	{
		for(ICollaboratorRole role:wsCollabRoles)
		{
			if(role.getRoleid().equals(collaboratorRoleId))
			{
				return role;
			}
		}
		return null;
	}

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

	@Override
	public String getProjectCollaboratorRoleByDBId(String collaboratorRoleDBId) {
		
	for(ICollaboratorRole collaboratorRole:projectCollabRoles)
	{
		if(collaboratorRole.getRoleDBid().equals(collaboratorRoleDBId))
		{
			return collaboratorRole.getDisplayName();
		}
	}
		
		return null;
	}
	
	

	@Override
	public String getDictCollaboratorRoleByDBId(String collaboratorRoleDBId) {
		
		for(ICollaboratorRole collaboratorRole:dictCollabRoles)
		{
			if(collaboratorRole.getRoleDBid().equals(collaboratorRoleDBId))
			{
				return collaboratorRole.getDisplayName();
			}
		}	
		
		return "";
	}
	
	@Override
	public String getDictCollaboratorRoleIdByDBId(String collaboratorRoleDBId) {
		
		for(ICollaboratorRole collaboratorRole:dictCollabRoles)
		{
			if(collaboratorRole.getRoleDBid().equals(collaboratorRoleDBId))
			{
				return collaboratorRole.getRoleid();
			}
		}	
		
		return "";
	}

	@Override
	public String getCollectionCollabRoleByDBId(String collaboratorRoleDBId) {

		for(ICollaboratorRole role:ccCollabRoles)
		{
			if(role.getRoleDBid().equals(collaboratorRoleDBId))
			{
				return role.getDisplayName();
			}	
		}
		return null;
	} 

}
