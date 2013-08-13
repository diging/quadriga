package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;

public class CollaboratorsBackBean {
	
	private List<ICollaborator> collaboratorList;
	
	private List<ICollaboratorRole> collaboratorRoleList;
	
	public CollaboratorsBackBean() {
		collaboratorList = new ArrayList<ICollaborator>();
	}

	public List<ICollaborator> getCollaboratorList() {
		return collaboratorList;
	}

	public void setCollaboratorList(List<ICollaborator> collaboratorList) {
		this.collaboratorList = collaboratorList;
	}

	public List<ICollaboratorRole> getCollaboratorRoleList() {
		return collaboratorRoleList;
	}

	public void setCollaboratorRoleList(List<ICollaboratorRole> collaboratorRoleList) {
		this.collaboratorRoleList = collaboratorRoleList;
	}
	
}
