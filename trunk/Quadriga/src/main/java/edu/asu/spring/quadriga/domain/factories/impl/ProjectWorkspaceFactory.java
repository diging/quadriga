package edu.asu.spring.quadriga.domain.factories.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factories.IProjectWorkspaceFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.ProjectWorkspace;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;

@Service
public class ProjectWorkspaceFactory implements IProjectWorkspaceFactory {

	@Override
	public IProjectWorkspace createProjectWorkspaceObject() {
		return new ProjectWorkspace();
	}

}
