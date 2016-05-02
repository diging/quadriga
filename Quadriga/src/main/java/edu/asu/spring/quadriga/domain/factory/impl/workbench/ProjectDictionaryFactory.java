package edu.asu.spring.quadriga.domain.factory.impl.workbench;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IProjectDictionaryFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.ProjectDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;

@Service
public class ProjectDictionaryFactory implements IProjectDictionaryFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProjectDictionary createProjectDictionaryObject() {
		return new ProjectDictionary();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IProjectDictionary cloneProjectDictionaryObject(
			IProjectDictionary projectDictionary) 
	{
		IProjectDictionary clone = new ProjectDictionary();
		clone.setProject(projectDictionary.getProject());
		clone.setDictionary(projectDictionary.getDictionary());
		clone.setCreatedBy(projectDictionary.getCreatedBy());
		clone.setCreatedDate(projectDictionary.getCreatedDate());
		clone.setUpdatedBy(projectDictionary.getUpdatedBy());
		clone.setUpdatedDate(projectDictionary.getUpdatedDate());
		return clone;
	}

}
