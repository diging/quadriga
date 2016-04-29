package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.impl.Collaborator;
/**
 * 
 * {@inheritDoc}
 * @author rohit pendbhaje
 *
 */

@Service	
public class CollaboratorFactory implements ICollaboratorFactory {

	/**
	 * {@inheritDoc}
	 * 
	 * 
	 */
	@Override
	public ICollaborator createCollaborator() {
		return new Collaborator();
	}

}
