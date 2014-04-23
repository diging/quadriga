package edu.asu.spring.quadriga.domain.factory.workspace;

import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;


public interface IWorkspaceDictionaryFactory {
	/**
	 * Factory method for creating {@link WorkspaceDictionary} object.
	 * @return
	 */
	public abstract IWorkspaceDictionary  createWorkspaceDictionaryObject();
	
	public abstract IWorkspaceDictionary cloneWorkspaceDictionary(IWorkspaceDictionary workspaceDictionary);
	

}
