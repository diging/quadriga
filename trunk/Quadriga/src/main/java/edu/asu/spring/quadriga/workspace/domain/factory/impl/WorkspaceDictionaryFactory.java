package edu.asu.spring.quadriga.workspace.domain.factory.impl;

import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;
import edu.asu.spring.quadriga.workspace.domain.factory.IWorkspaceDictionaryFactory;

public class WorkspaceDictionaryFactory implements IWorkspaceDictionaryFactory {

	@Override
	public IWorkspaceDictionary createWorkspaceDictionaryObject() {
		return new WorkspaceDictionary();
	}

}
