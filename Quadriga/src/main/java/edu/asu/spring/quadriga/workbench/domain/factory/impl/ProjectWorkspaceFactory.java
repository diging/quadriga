package edu.asu.spring.quadriga.workbench.domain.factory.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.workbench.ProjectWorkspace;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;
import edu.asu.spring.quadriga.workbench.domain.factory.IProjectWorkspaceFactory;

@Service
public class ProjectWorkspaceFactory implements IProjectWorkspaceFactory {

	@Override
	public IProjectWorkspace createProjectWorkspaceObject() {
		return new ProjectWorkspace();
	}

}
