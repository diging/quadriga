package edu.asu.spring.quadriga.db.sql.dictionary;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionDictionaryCollaboratorManager;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionDictionaryCollaboratorManager extends ADBConnectionManager implements
		IDBConnectionDictionaryCollaboratorManager 
{
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionDictionaryCollaboratorManager.class);
	
	@Override
	public void updateCollaboratorRoles(String dictionaryId,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
	{
		String dbCommand;
        String errmsg;
        CallableStatement sqlStatement;
        
        //command to execute the stored procedure
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.UPDATE_DICTIONARY_COLLAB_REQUEST + "(?,?,?,?,?)";
        
        //establish the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//add the input parameters
        	sqlStatement.setString(1, dictionaryId);
        	sqlStatement.setString(2, collabUser);
        	sqlStatement.setString(3, collaboratorRole);
        	sqlStatement.setString(4, username);
        	
        	//add output parameter
        	sqlStatement.registerOutParameter(5, Types.VARCHAR);
        	
        	sqlStatement.execute();
        	
        	errmsg = sqlStatement.getString(5);
        	
        	if(!errmsg.equals(""))
        	{
        		logger.info("In update collaborator method :" + errmsg);
        		throw new QuadrigaStorageException(errmsg);
        	}
        }
        catch(SQLException ex)
        {
        	logger.error("n update collaborator method :"+ex);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
	}

}
