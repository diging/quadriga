package edu.asu.spring.quadriga.db.workspace;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionListWSManager {

	public abstract List<IWorkSpace> listDeactivatedWorkspace(String projectid)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listArchivedWorkspace(String projectid)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listActiveWorkspace(String projectid)
			throws QuadrigaStorageException;

	public abstract void setDataSource(DataSource dataSource);

	public abstract IWorkSpace getWorkspaceDetails(String workspaceId)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listWorkspace(String projectid)
			throws QuadrigaStorageException;

	public abstract void setupTestEnvironment(String sQuery)
			throws QuadrigaStorageException;

}
