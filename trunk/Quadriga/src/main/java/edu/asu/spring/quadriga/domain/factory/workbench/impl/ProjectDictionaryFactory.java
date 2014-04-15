package edu.asu.spring.quadriga.domain.factory.workbench.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IProjectDictionaryFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.ProjectDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProjectDictionary;

@Service
public class ProjectDictionaryFactory implements IProjectDictionaryFactory {

	@Override
	public IProjectDictionary createProjectDictionaryObject() {
		return new ProjectDictionary();
	}

}
