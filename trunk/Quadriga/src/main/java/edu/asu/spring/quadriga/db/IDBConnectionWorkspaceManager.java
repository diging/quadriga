package edu.asu.spring.quadriga.db;

import java.util.List;

import javax.sql.DataSource;

import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * @description : Interface class for methods relating database connections to
 *                workspace component
 * @author Kiran Kumar Batna
 */
public interface IDBConnectionWorkspaceManager 
{

	public abstract void setDataSource(DataSource dataSource);

	public abstract String addWorkSpaceRequest(IWorkSpace workSpace,int projectId)
			throws QuadrigaStorageException;

	public abstract List<IWorkSpace> listWorkspace(int projectid)
			throws QuadrigaStorageException;

}
