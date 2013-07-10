package edu.asu.spring.quadriga.db.workbench;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionRetrieveProjCollabManager {

	public abstract List<IUser> getProjectNonCollaborators(String projectid)
			throws QuadrigaStorageException;

	public abstract List<ICollaboratorRole> getCollaboratorRolesList(String role);

	public abstract List<ICollaborator> getProjectCollaborators(String projectId)
			throws QuadrigaStorageException;

	public abstract void setDataSource(DataSource dataSource);

	public abstract List<IUser> getProjectCollaboratorsRequest(String projectid)
			throws QuadrigaStorageException;

}
