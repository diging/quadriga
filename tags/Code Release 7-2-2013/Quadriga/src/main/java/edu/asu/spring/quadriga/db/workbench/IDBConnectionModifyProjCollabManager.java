package edu.asu.spring.quadriga.db.workbench;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyProjCollabManager {

	public abstract String addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName)
			throws QuadrigaStorageException;

	public abstract void setDataSource(DataSource dataSource);

}
