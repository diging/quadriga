package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.impl.Collaborator;

/**
 * Creates {@link Collaborator} objects.
 * @author Julia Damerow
 *
 */
public interface ICollaboratorFactory {

	/**
	 * Creates a new {@link Collaborator} object.
	 * @return
	 */
	public ICollaborator createCollaborator();
}
