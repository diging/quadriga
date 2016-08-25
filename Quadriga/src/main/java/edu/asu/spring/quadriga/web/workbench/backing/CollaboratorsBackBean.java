package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IProjectCollaborator;

/**
 * This class holds the list of collaborators to display 
 * as a Model attribute in the UI page
 * @author Kiran Kumar Batna
 *
 */
public class CollaboratorsBackBean {
	
	private List<IProjectCollaborator> collaboratorList;
	
	
	public CollaboratorsBackBean() {
		collaboratorList = new ArrayList<IProjectCollaborator>();
	}

	public List<IProjectCollaborator> getCollaboratorList() {
		return collaboratorList;
	}

	public void setCollaboratorList(List<IProjectCollaborator> collaboratorList) {
		this.collaboratorList = collaboratorList;
	}
	
}
