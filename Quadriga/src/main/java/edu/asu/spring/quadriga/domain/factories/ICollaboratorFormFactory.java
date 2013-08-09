package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.ICollaboratorForm;

public interface ICollaboratorFormFactory {

	public abstract ICollaboratorForm cloneCollaboratorForm(ICollaboratorForm collaborator);

	public abstract ICollaboratorForm createCollaboratorForm();

}
