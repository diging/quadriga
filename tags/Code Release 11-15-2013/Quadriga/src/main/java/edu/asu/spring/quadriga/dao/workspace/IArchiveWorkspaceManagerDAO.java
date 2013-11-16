package edu.asu.spring.quadriga.dao.workspace;

public interface IArchiveWorkspaceManagerDAO {

	public abstract void deactivateWorkspace(String workspaceIdList, boolean deactivate,
			String wsUser);

	public abstract void archiveWorkspace(String workspaceIdList, boolean archive,
			String wsUser);

}
