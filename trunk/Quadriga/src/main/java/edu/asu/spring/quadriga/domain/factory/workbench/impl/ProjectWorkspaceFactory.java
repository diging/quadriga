package edu.asu.spring.quadriga.domain.factory.workbench.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IProjectWorkspaceFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.ProjectWorkspace;
import edu.asu.spring.quadriga.domain.workbench.IProjectWorkspace;

@Service
public class ProjectWorkspaceFactory implements IProjectWorkspaceFactory {

	@Override
	public IProjectWorkspace createProjectWorkspaceObject() {
		return new ProjectWorkspace();
	}

}
