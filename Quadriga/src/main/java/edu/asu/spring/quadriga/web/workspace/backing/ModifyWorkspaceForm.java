package edu.asu.spring.quadriga.web.workspace.backing;

import java.util.List;

/**
 * This is the bean class for holding the list of 
 * Workspace objects to be displayed in the UI pages.
 * @author Kiran Batna
 *
 */
public class ModifyWorkspaceForm 
{
	private List<ModifyWorkspace> workspaceList;

	public List<ModifyWorkspace> getWorkspaceList() {
		return workspaceList;
	}

	public void setWorkspaceList(List<ModifyWorkspace> workspaceList) {
		this.workspaceList = workspaceList;
	}

}
