package edu.asu.spring.quadriga.domain;

import java.util.List;

public interface ICollaboratorForm {

	public abstract void setCollaborator(List<ICollaborator> collaborator);

	public abstract List<ICollaborator> getCollaborator();

}
