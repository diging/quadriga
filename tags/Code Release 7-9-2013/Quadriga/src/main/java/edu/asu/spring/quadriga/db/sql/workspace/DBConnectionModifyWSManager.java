package edu.asu.spring.quadriga.db.sql.workspace;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManger;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Class implements {@link IDBConnectionModifyWSManger} for all the DB connection 
 * necessary for add/update/delete a workspace associated with project.
 * @implements IDBConnectionModifyWSManger
 * @author Kiran Kumar Batna
 */
public class DBConnectionModifyWSManager implements IDBConnectionModifyWSManger 
{
	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionModifyWSManager.class);

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
			logger.info("Close database connection :"+e.getMessage());
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
			logger.info("Establish database connection  :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}

	/**
	 * Establishes the test environment
	 * @param sQuery
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	@Override
	public void setupTestEnvironment(String sQuery) throws QuadrigaStorageException
	{
		getConnection();
		try
		{
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sQuery);
		}
		catch(SQLException ex)
		{
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}
	}
	
	/**
	 * This adds a workspace record into the database.
	 * @param  workSpace
	 * @return errmsg - blank when success and error message on failure.
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	@Override
	public String addWorkSpaceRequest(IWorkSpace workSpace,String projectId) throws QuadrigaStorageException
	{
		String errmsg;
		String wsName;
		String wsDescription;
		String wsOwnerName;
		String dbCommand;
		IUser  wsOwner = null;
		CallableStatement sqlStatement;

		// fetch the values from the WorkSpace object
		wsName = workSpace.getName();
		wsDescription = workSpace.getDescription();
		wsOwner = workSpace.getOwner();
		wsOwnerName = wsOwner.getUserName();

		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_WORKSPACE_REQUEST + "(?,?,?,?,?)";

		//establish the connection
		getConnection();

		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			// add input parameters
			sqlStatement.setString(1, wsName);
			sqlStatement.setString(2, wsDescription);
			sqlStatement.setString(3, wsOwnerName);
			sqlStatement.setString(4, projectId);

			// add output parameter
			sqlStatement.registerOutParameter(5, Types.VARCHAR);

			// execute the sql statement
			sqlStatement.execute();

			errmsg = sqlStatement.getString(5);
		}
		catch(SQLException e)
		{
			logger.info("Add workspace request method :"+e.getMessage());
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
	
	/**
	 * This will delete the requested workspace.
	 * @param   workspaceIdList - Comma separated workspace Id's.
	 * @return  String - errmsg containing blank on success and error message on failure.
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	public String deleteWorkspaceRequest(String workspaceIdList) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		CallableStatement sqlStatement;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_WORKSPACE_REQUEST + "(?,?)";
		
		//establish the connection
		getConnection();
		
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, workspaceIdList);
			
			// add output parameter
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(2);
		}
		catch(SQLException e)
		{
			logger.info("Delete workspace request method :"+e.getMessage());
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
}
