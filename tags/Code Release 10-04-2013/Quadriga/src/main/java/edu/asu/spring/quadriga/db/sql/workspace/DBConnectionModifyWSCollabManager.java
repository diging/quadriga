package edu.asu.spring.quadriga.db.sql.workspace;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionModifyWSCollabManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionModifyWSCollabManager extends ADBConnectionManager implements
		IDBConnectionModifyWSCollabManager 
{
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionModifyWSCollabManager.class);
	
	
	/**
	 * This method adds a collaborator for a workspace
	 * @param collaborator - collaborator user name
	 * @param collabRoleList - collaborator roles
	 * @param workspaceid - associate workspace
	 * @param userName - logged in user name
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public void addWorkspaceCollaborator(String collaborator,String collabRoleList,String workspaceid,String userName) throws QuadrigaStorageException
	{
		String dbCommand;
        String errmsg;
        CallableStatement sqlStatement;
        
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_WORKSPACE_COLLABORATOR + "(?,?,?,?,?)";
        
    	//establish the connection
    	getConnection();
    	
    	try
    	{
    		// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, workspaceid);
			sqlStatement.setString(2, collaborator);
			sqlStatement.setString(3, collabRoleList);
			sqlStatement.setString(4, userName);
			
			// add output parameter
			sqlStatement.registerOutParameter(5, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(5);
			
			if(errmsg !="")
			{
				logger.info("In add workspace collaborator :" + errmsg);
				throw new QuadrigaStorageException(errmsg);
			}
    	}
    	catch(SQLException e)
    	{
    		logger.error("In add worksapce collaborator :", e);
    		throw new QuadrigaStorageException();
    	}
    	finally
    	{
    		closeConnection();
    	}
	}
	
	/**
	 * This method deletes a collaborator for a workspace
	 * @param collaborator - collaborator user name list
	 * @param workspaceid - associate workspace
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public void deleteWorkspaceCollaborator(String collaborator,String workspaceid) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.DELETE_WORKSPACE_COLLABORATOR + "(?,?,?)";
		
    	//establish the connection
    	getConnection();
    	
		try
		{
    		// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			//add input parameters
			sqlStatement.setString(1, collaborator);
			sqlStatement.setString(2, workspaceid);
			
			//add output parameter
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			
			//execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(3);
			
			if(!errmsg.equals(""))
			{
				logger.info("In delete workspace collaborator :"+ errmsg);
				throw new QuadrigaStorageException(errmsg);
			}
		}
		catch(SQLException e)
		{
			logger.error("In delete workspace collaborator :"+e);
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}
		
	}
	
	@Override
	public void updateWorkspaceCollaborator(String workspaceId,String collabUser,String collaboratorRole,String userName) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_WORKSPACE_COLLABORATOR + "(?,?,?,?,?)";
		
		//establish the connection
		getConnection();
		
		try
		{
			//prepare SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			//add input parameters
			sqlStatement.setString(1, workspaceId);
			sqlStatement.setString(2,collabUser);
			sqlStatement.setString(3, collaboratorRole);
			sqlStatement.setString(4, userName);
			
			//add output parameters
			sqlStatement.registerOutParameter(5, Types.VARCHAR);
			
			//execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(5);
			
			if(!errmsg.equals(""))
			{
				logger.info("update Workspace collaborator method :"+errmsg);
				throw new QuadrigaStorageException(errmsg);
			}
		}
		catch(SQLException e)
		{
			logger.error("update Workspace collaborator method :"+e);
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}
	}
}
