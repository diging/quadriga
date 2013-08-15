package edu.asu.spring.quadriga.db.sql.workspace;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.sql.workbench.DBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWSAccessManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionWSAccessManager extends ADBConnectionManager  implements
IDBConnectionWSAccessManager 
{
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionProjectAccessManager.class);
	
	/**
	 *  This method verifies if the given user is workspace owner
	 *  @param  userName
	 *  @param  workspaceId
	 *  @return Booelan TRUE- if he is workspace owner else FALSE
	 *  @exception QuadrigaStorageException
	 *  @author Kiran Kumar Batna 
	 */
	@Override
	public boolean chkWorkspaceOwner(String userName,String workspaceId) throws QuadrigaStorageException
	{
		String dbCommand;
		CallableStatement sqlStatement;
		boolean chkAccess;

		//command to call the SP
		dbCommand = "? = "+" "+ DBConstants.SP_CALL+ " " + DBConstants.CHECK_WORKSPACE_OWNER + "(?,?)";

		//get the connection
		getConnection();

		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding output variables to the SP
			sqlStatement.registerOutParameter(1,Types.BOOLEAN);

			//adding the input parameter
			sqlStatement.setString(2,userName);
			sqlStatement.setString(3,workspaceId);

			sqlStatement.execute();

			chkAccess = sqlStatement.getBoolean(1);

			return chkAccess;

		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}

	}

	/**
	 * Checks if Workspace owner has editor roles
	 * @param userName
	 * @param workspaceId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public boolean chkWorkspaceOwnerEditorRole(String userName,String workspaceId) throws QuadrigaStorageException
	{
		String dbCommand;
		String result="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.CHECK_WORKSPACE_OWNER_EDITOR_ROLE + "(?,?,?)";

		//get the connection
		getConnection();

		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userName);
			sqlStatement.setString(2, workspaceId);
			sqlStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			sqlStatement.execute();

			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) {
					result = resultSet.getString(1);
				}		
			}
			if(result.isEmpty()){
				return false;
			}else if(result.equals("1")){
				return true;
			}
		}catch(SQLException e)
		{
			logger.info(" ",e);
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}
		return false;

	}
}
