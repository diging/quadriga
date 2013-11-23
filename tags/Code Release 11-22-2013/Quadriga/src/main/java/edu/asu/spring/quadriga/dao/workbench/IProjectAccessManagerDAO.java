package edu.asu.spring.quadriga.dao.workbench;

public interface IProjectAccessManagerDAO {

	public abstract boolean chkProjectOwnerEditorRole(String userName, String projectId);

	public abstract boolean chkDuplicateProjUnixName(String unixName, String projectId);

	public abstract boolean chkProjectCollaborator(String userName, String collaboratorRole,
			String projectId);

	public abstract boolean chkIsProjectAssociated(String userName);

	public abstract boolean chkProjectOwner(String userName, String projectId);

}
