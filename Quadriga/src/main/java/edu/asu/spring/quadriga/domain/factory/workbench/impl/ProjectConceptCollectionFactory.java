package edu.asu.spring.quadriga.domain.factory.workbench.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IProjectConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.ProjectConceptCollection;
import edu.asu.spring.quadriga.domain.workbench.IProjectConceptCollection;

@Service
public class ProjectConceptCollectionFactory implements
		IProjectConceptCollectionFactory {

	@Override
	public IProjectConceptCollection createProjectConceptCollectionObject() {
		return new ProjectConceptCollection();
	}

}
