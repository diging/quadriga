package edu.asu.spring.quadriga.domain.factory.workspace.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workspace.IWorkspaceDictionaryFactory;
import edu.asu.spring.quadriga.domain.impl.workspace.WorkspaceDictionary;
import edu.asu.spring.quadriga.domain.workspace.IWorkspaceDictionary;

@Service
public class WorkspaceDictionaryFactory implements IWorkspaceDictionaryFactory {

	@Override
	public IWorkspaceDictionary createWorkspaceDictionaryObject() {
		return new WorkspaceDictionary();
	}

}
