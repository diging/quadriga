package edu.asu.spring.quadriga.domain.implementation;

import java.util.List;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorForm;

public class CollaboratorForm implements ICollaboratorForm 
{
	
	private List<ICollaborator> collaborator;

	@Override
	public List<ICollaborator> getCollaborator() {
		return collaborator;
	}

	@Override
	public void setCollaborator(List<ICollaborator> collaborator) {
		this.collaborator = collaborator;
	}

}
