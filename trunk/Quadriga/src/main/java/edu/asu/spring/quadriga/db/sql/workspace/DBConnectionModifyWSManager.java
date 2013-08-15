package edu.asu.spring.quadriga.db.sql.workspace;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Class implements {@link IDBConnectionModifyWSManager} for all the DB connection 
 * necessary for add/update/delete a workspace associated with project.
 * @implements IDBConnectionModifyWSManger
 * @author Kiran Kumar Batna
 */
public class DBConnectionModifyWSManager extends ADBConnectionManager implements IDBConnectionModifyWSManager 
{
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionModifyWSManager.class);

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
	
	/**
	 * This method updates the workspace
	 * @param workspace
	 * @return errmsg - blank on success else contains error message
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public String updateWorkspaceRequest(IWorkSpace workspace ) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsName;
		String wsDescription;
		String wsOwnerName;
		String workspaceId;
		IUser wsOwner;
		CallableStatement sqlStatement;
		
		//fetch the workspace details
		// fetch the values from the WorkSpace object
		wsName = workspace.getName();
		wsDescription = workspace.getDescription();
		wsOwner = workspace.getOwner();
		wsOwnerName = wsOwner.getUserName();
		workspaceId = workspace.getId();
		
		//command to call the stored procedure
		dbCommand = DBConstants.SP_CALL+" " + DBConstants.UPDATE_WORKSPACE_REQUEST + "(?,?,?,?,?)";
		
		//establish the connection
		getConnection();
		
		try
		{
			//prepare SQL statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			//add input parameters
			sqlStatement.setString(1,wsName);
			sqlStatement.setString(2, wsDescription);
			sqlStatement.setString(3, wsOwnerName);
			sqlStatement.setString(4,workspaceId);
			
			//add output parameters
			sqlStatement.registerOutParameter(5,Types.VARCHAR);
			
			//execute the statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(5);
		}
		catch(SQLException ex)
		{
			logger.info("Update workspace request method :"+ex.getMessage());
			throw new QuadrigaStorageException(ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
	
	/**
	 * This method assigns new owner to the workspace submitted
	 * @param projectId
	 * @param oldOwner
	 * @param newOwner
	 * @param collabRole
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public void transferWSOwnerRequest(String workspaceId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException 
	{
		String dbCommand;
		CallableStatement sqlStatement;
		String errmsg;
		
		//command to call the stored procedure
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.TRANSFER_WORKSPACE_OWNERSHIP + "(?,?,?,?,?)";
		
		//establish the connection
		getConnection();
		
		try
		{
			//prepare SQL statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			//add input parameters
			sqlStatement.setString(1, workspaceId);
			sqlStatement.setString(2, oldOwner);
			sqlStatement.setString(3, newOwner);
			sqlStatement.setString(4, collabRole);
			
			//add out parameters
			sqlStatement.registerOutParameter(5, Types.VARCHAR);
			
			//execute the statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(5);
			
			if(!errmsg.equals(""))
			{
				logger.info("Transfer workspace ownership request method :"+errmsg);
				throw new QuadrigaStorageException(errmsg);
			}
		}
		catch(SQLException ex)
		{
			logger.error("Transfer workspace ownership request method :"+ex);
			throw new QuadrigaStorageException(ex.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}
	
	@Override
	public String assignWorkspaceOwnerEditor(String workspaceId,String owner) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		//command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ASSIGN_WORKSPACE_EDITOR_OWNER + "(?,?,?)";
        
        //get the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//add input parameters
        	sqlStatement.setString(1,workspaceId);
        	sqlStatement.setString(2, owner);
        	
        	//add output parameter
        	sqlStatement.registerOutParameter(3, Types.VARCHAR);
        	
           	sqlStatement.execute();
        	errmsg = sqlStatement.getString(3);
        	
        	if(!errmsg.equals(""))
        	{
        		logger.info("Assign Workspace editor to owner request method :"+errmsg);
        	}
        	return errmsg;
        }
        catch(SQLException e)
        {
        	logger.error("Assign Workspace editor to owner request method :"+e);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
	}
	
}
