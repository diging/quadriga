package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectAccessManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionProjectAccessManager extends ADBConnectionManager implements
		IDBConnectionProjectAccessManager {
	
	
	/**
	 *  This method verifies if the given user is project owner
	 *  @param  userName
	 *  @param  projectId
	 *  @return Booelan TRUE- if he is project owner else FALSE
	 *  @exception QuadrigaStorageException
	 *  @author Kiran Kumar Batna 
	 */
	@Override
	public boolean chkProjectOwner(String userName,String projectId) throws QuadrigaStorageException
	{
		String dbCommand;
	    CallableStatement sqlStatement;
	    boolean chkAccess;
	    
        //command to call the SP
        dbCommand = "? = "+" "+ DBConstants.SP_CALL+ " " + DBConstants.CHECK_PROJECT_OWNER + "(?,?)";
        
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
			sqlStatement.setString(3,projectId);

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

}
