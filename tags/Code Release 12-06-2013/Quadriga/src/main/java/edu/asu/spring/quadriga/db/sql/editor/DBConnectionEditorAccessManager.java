package edu.asu.spring.quadriga.db.sql.editor;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.editor.IDBConnectionEditorAccessManager;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionEditorAccessManager extends ADBConnectionManager implements
		IDBConnectionEditorAccessManager 
{
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionEditorAccessManager.class);
	
	@Override
	public boolean chkIsEditor(String userName) throws QuadrigaStorageException
	{
		String dbCommand;
		CallableStatement sqlStatement;
		boolean chkAccess;

		//command to call the SP
		dbCommand = "? = "+" "+ DBConstants.SP_CALL+ " " + DBConstants.CHECK_IS_EDITOR + "(?)";

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
	
	@Override
	public boolean chkIsNetworkEditor(String networkId,String userName) throws QuadrigaStorageException
	{
		String dbCommand;
		CallableStatement sqlStatement;
		boolean chkAccess;

		//command to call the SP
		dbCommand = "? = "+" "+ DBConstants.SP_CALL+ " " + DBConstants.CHECK_IS_EDITOR + "(?,?)";

		//get the connection
		getConnection();

		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding output variables to the SP
			sqlStatement.registerOutParameter(1,Types.BOOLEAN);

			//adding the input parameter
			sqlStatement.setString(2,networkId);
			sqlStatement.setString(3, userName);

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

}
