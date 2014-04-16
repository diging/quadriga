package edu.asu.spring.quadriga.domain.factory.workspace;

import edu.asu.spring.quadriga.domain.workspace.IWorkspaceBitStream;

public interface IWorkspaceBitstreamFactory {

	/**
	 * This method should help in creating and returning a {@link IWorkspaceBitStream} 
	 * @return							Returns {@link IWorkspaceBitStream} object
	 */
	public abstract IWorkspaceBitStream createWorkspaceBitstreamObject();

}