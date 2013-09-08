package edu.asu.spring.quadriga.db.sql.conceptcollection;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionModifyCCManager;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionModifyCCManager extends ADBConnectionManager implements
		IDBConnectionModifyCCManager 
{
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionModifyCCManager.class);
	
	@Override
	public void transferCollectionOwnerRequest(String collectionId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		//command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.TRANSFER_COLLECTION_OWNERSHIP + "(?,?,?,?,?)";
        
        //get the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//add input parameters
        	sqlStatement.setString(1,collectionId);
        	sqlStatement.setString(2, oldOwner);
        	sqlStatement.setString(3, newOwner);
        	sqlStatement.setString(4, collabRole);
        	
        	//add output parameter
        	sqlStatement.registerOutParameter(5, Types.VARCHAR);
        	
           	sqlStatement.execute();
        	errmsg = sqlStatement.getString(5);
        	
        	if(!errmsg.equals(""))
        	{
        		logger.info("Transfer collection request method :"+errmsg);
        		throw new QuadrigaStorageException(errmsg);
        	}
        }
        catch(SQLException e)
        {
        	logger.error("Transfer collection request method :"+e);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
		
	}

}
