package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;

public class CollaboratorsBackBean {
	
	private List<ICollaborator> collaboratorList;
	
	
	public CollaboratorsBackBean() {
		collaboratorList = new ArrayList<ICollaborator>();
	}

	public List<ICollaborator> getCollaboratorList() {
		return collaboratorList;
	}

	public void setCollaboratorList(List<ICollaborator> collaboratorList) {
		this.collaboratorList = collaboratorList;
	}
	
}
