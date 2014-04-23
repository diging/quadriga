package edu.asu.spring.quadriga.domain.factory.impl.workspace;

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

	@Override
	public IWorkspaceDictionary cloneWorkspaceDictionary(
			IWorkspaceDictionary workspaceDictionary) {
        IWorkspaceDictionary clone = new WorkspaceDictionary();
        clone.setDictionary(workspaceDictionary.getDictionary());
        clone.setWorkspace(workspaceDictionary.getWorkspace());
        clone.setCreatedBy(workspaceDictionary.getCreatedBy());
        clone.setCreatedDate(workspaceDictionary.getCreatedDate());
        clone.setUpdatedBy(workspaceDictionary.getUpdatedBy());
        clone.setUpdatedDate(workspaceDictionary.getUpdatedDate());
		return clone;
	}

}
