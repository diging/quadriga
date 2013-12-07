package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionProjectAccessManager extends ADBConnectionManager implements
IDBConnectionProjectAccessManager {

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionProjectAccessManager.class);

	/**
	 *  This method verifies if the given user is project owner
	 *  @param  userName
	 *  @param  projectId
	 *  @return Booelan TRUE- if he is project owner else FALSE
	 *  @exception QuadrigaStorageException
	 *  @author Kiran Kumar Batna 
	 */
	@Override
	public boolean chkProjectOwner(String userName) throws QuadrigaStorageException
	{
		String dbCommand;
		CallableStatement sqlStatement;
		boolean chkAccess;

		//command to call the SP
		dbCommand = "? = "+" "+ DBConstants.SP_CALL+ " " + DBConstants.CHECK_PROJECT_OWNER + "(?)";

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

			sqlStatement.execute();

			chkAccess = sqlStatement.getBoolean(1);

			return chkAccess;

		}
		catch(SQLException e)
		{
			logger.error("Check project access:",e);
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}

	}
	
	/**
	 *  This method verifies if the given user has any projects associated as 
	 *  collaborator
	 *  @param  userName
	 *  @param  collaboratorRole
	 *  @return Booelan TRUE- if he is project owner else FALSE
	 *  @exception QuadrigaStorageException
	 *  @author Kiran Kumar Batna 
	 */
	@Override
	public boolean chkProjectCollaborator(String userName,String collaboratorRole) throws QuadrigaStorageException
	{
		String dbCommand;
		CallableStatement sqlStatement;
		boolean chkAccess;

		//command to call the SP
		dbCommand = "? = "+" "+ DBConstants.SP_CALL+ " " + DBConstants.CHECK_PROJECT_COLLABORATOR + "(?,?)";

		//establish the connection with the database
		try
		{
			//get the connection
			getConnection();
			
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding output variables to the SP
			sqlStatement.registerOutParameter(1,Types.BOOLEAN);

			//adding the input parameter
			sqlStatement.setString(2,userName);
			sqlStatement.setString(3,collaboratorRole);

			sqlStatement.execute();

			chkAccess = sqlStatement.getBoolean(1);

			return chkAccess;

		}
		catch(SQLException e)
		{
			logger.error("Check project access:",e);
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}

	}

	/**
	 *  This method verifies if the Unix name is already present
	 *  @param  unixName
	 *  @return Booelan TRUE- if unixName is already present else FALSE
	 *  @exception QuadrigaStorageException
	 *  @author Kiran Kumar Batna 
	 */
	@Override
	public boolean chkDuplicateProjUnixName(String unixName,String projectId) throws QuadrigaStorageException
	{
		String dbCommand;
		CallableStatement sqlStatement;
		boolean isDuplicate;

		//command to call the SP
		dbCommand = "? = "+" "+ DBConstants.SP_CALL+ " " + DBConstants.CHECK_PROJECT_UNIX_NAME + "(?,?)";

		//get the connection
		getConnection();

		//establish the connection to the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding output variables to the SP
			sqlStatement.registerOutParameter(1,Types.BOOLEAN);

			//adding the input parameter
			sqlStatement.setString(2,unixName);
            sqlStatement.setString(3, projectId);
			sqlStatement.execute();

			isDuplicate = sqlStatement.getBoolean(1);

			return isDuplicate;
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
	 * Checks if project owner has editor roles
	 * @param userName
	 * @param projectId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public boolean chkProjectOwnerEditorRole(String userName,String projectId) throws QuadrigaStorageException
	{
		String dbCommand;
		String result="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.CHECK_PROJECT_OWNER_EDITOR_ROLE + "(?,?,?)";

		//get the connection
		getConnection();

		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userName);
			sqlStatement.setString(2, projectId);
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
