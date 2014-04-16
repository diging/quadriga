package edu.asu.spring.quadriga.domain.factory.workspace.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceBitstreamFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceBitStream;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceBitStream;

/**
 *  Factory class to create {@link IWorkspaceBitStream} object of domain class type {@link WorkspaceBitStream}
 * @author Lohith Dwaraka
 *
 */
@Service
public class WorkspaceBitstreamFactory implements IWorkspaceBitstreamFactory {

	/**
	 * {@inheritDoc}
	*/
	@Override
	public IWorkspaceBitStream createWorkspaceBitstreamObject() {
		return new WorkspaceBitStream();
	}
}
