package edu.asu.spring.quadriga.web.workbench.backing;

import java.util.List;

/**
 * This class holds the list of Collaborators to act as a Model
 * attribute to display in the UI pages.
 *
 */
public class ModifyCollaboratorForm 
{
	private List<ModifyCollaborator> collaborators;

	public List<ModifyCollaborator> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(List<ModifyCollaborator> collaborators) {
		this.collaborators = collaborators;
	}
}
