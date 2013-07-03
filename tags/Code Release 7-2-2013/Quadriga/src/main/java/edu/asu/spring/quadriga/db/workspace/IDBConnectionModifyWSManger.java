package edu.asu.spring.quadriga.db.workspace;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionModifyWSManger {

	public abstract String deleteWorkspaceRequest(String workspaceIdList)
			throws QuadrigaStorageException;

	public abstract String addWorkSpaceRequest(IWorkSpace workSpace, String projectId)
			throws QuadrigaStorageException;

	public abstract void setDataSource(DataSource dataSource);

}
