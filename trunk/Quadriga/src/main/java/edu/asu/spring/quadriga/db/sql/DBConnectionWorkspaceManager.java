package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionWorkspaceManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionWorkspaceManager implements
		IDBConnectionWorkspaceManager 
{
	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	/**
	 *  @Description: Assigns the data source
	 *  @param : dataSource
	 *  @author Kiran Kumar Batna
	 */
	@Override
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}
	
	/**
	 * @Description : Close the DB connection
	 * @return : 0 on success
	 *           -1 on failure
	 * @throws : SQL Exception
	 * @author Kiran Kumar Batna       
	 */
	private int closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
			return 0;
		}
		catch(SQLException se)
		{
			return -1;
		}
	}
	
	/**
	 * @Description : Establishes connection with the Quadriga DB
	 * @return      : connection handle for the created connection
	 * @throws      : QuadrigaStorageException
	 * @author      : Kiran Kumar Batna
	 */
	private void getConnection() throws QuadrigaStorageException {
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			
			throw new QuadrigaStorageException(e.getMessage());
		}
	}
	
	/**
	 * @description   : This adds a workspace record into the database.
	 * @param         : workSpace
	 * @return        : errmsg - blank when success and error message on failure.
	 * @throws        : QuadrigaStorageException
	 */
	@Override
	public String addWorkSpaceRequest(IWorkSpace workSpace) throws QuadrigaStorageException
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
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_WORKSPACE_REQUEST + "(?,?,?,?)";
		
		//establish the connection
		try
		{
		  getConnection();
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, wsName);
			sqlStatement.setString(2, wsDescription);
			sqlStatement.setString(3, wsOwnerName);
			
			// add output parameter
			sqlStatement.registerOutParameter(4, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(4);
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		
		return errmsg;
	}
	
	

}
