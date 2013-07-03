package edu.asu.spring.quadriga.db.sql.workspace;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionArchiveWSManger;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Class implements {@link IDBConnectionArchiveWSManger} for all the DB connection 
 * necessary for archiving and deactivating workspace associated with project.
 * @implements IDBConnectionArchiveWSManger
 * @author Kiran Kumar Batna
 */
public class DBConnectionArchiveWSManger implements
		IDBConnectionArchiveWSManger {
	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionArchiveWSManger.class);

	/**
	 *  Assigns the data source
	 *  @param  dataSource
	 *  @author Kiran Kumar Batna
	 */
	@Override
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}

	/**
	 * Close the DB connection
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	private void closeConnection() throws QuadrigaStorageException {
		try {
			if (connection != null) {
				connection.close();
			}
		}
		catch(SQLException e)
		{
			logger.info("Close database Connection  :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}

	/**
	 * Establishes connection with the Quadriga DB
	 * @return      connection handle for the created connection
	 * @throws      QuadrigaStorageException
	 * @author      Kiran Kumar Batna
	 */
	private void getConnection() throws QuadrigaStorageException {
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			logger.info("Open database connection :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}

	/**
	 * This will archive the requested workspace or activates the already archived workspace
	 * based on the @param archive .
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   archive - 1 to archive the workspace.
	 *                  - 0 to activate the archived workspace.
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	public String archiveWorkspace(String workspaceIdList,int archive,String wsUser) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		CallableStatement sqlStatement;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ARCHIVE_WORKSPACE_REQUEST + "(?,?,?,?)";
		
		//establish the connection
    	getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, workspaceIdList);
			sqlStatement.setInt(2, archive);
			sqlStatement.setString(3,wsUser);
			
			// add output parameter
			sqlStatement.registerOutParameter(4, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(4);
		}
		catch(SQLException e)
		{
			logger.info("Archvie workspace method :"+e.getMessage());
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
	
	/**
	 * This will deactivate the requested workspace or activated already deactivated workspace
	 * based on @param deactivate.
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @param   deactivate - 1 deactivate the workspace
	 *                     - 0 activate the workspace
	 * @param   wsUser
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	public String deactivateWorkspace(String workspaceIdList,int deactivate,String wsUser) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		CallableStatement sqlStatement;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DEACTIVATE_WORKSPACE_REQUEST + "(?,?,?,?)";
		
		//establish the connection
		getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, workspaceIdList);
			sqlStatement.setInt(2, deactivate);
			sqlStatement.setString(3,wsUser);
			
			// add output parameter
			sqlStatement.registerOutParameter(4, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(4);
		}
		catch(SQLException e)
		{
			logger.info("Deactivate workspace method  :"+e.getMessage());
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
}
