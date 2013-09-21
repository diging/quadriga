package edu.asu.spring.quadriga.db.sql.dictionary;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionModifyDictionaryManager;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionModifyDictionaryManager extends ADBConnectionManager implements
		IDBConnectionModifyDictionaryManager 
{
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionModifyDictionaryManager.class);
	
	@Override
	public void updateDictionaryRequest(IDictionary dictionary, String userName) throws QuadrigaStorageException
	{
		String name;
		String description;
        String dbCommand;
        String errmsg;
        String dictionaryId;
        CallableStatement sqlStatement;
        
        //command to call the SP
      	dbCommand = DBConstants.SP_CALL+ " " + DBConstants.UPDATE_DICTIONARY_DETAILS  + "(?,?,?,?,?,?)";
      		
        //fetch the values from the dictionary object
        name = dictionary.getName();
        description = dictionary.getDescription();
        dictionaryId = dictionary.getId();
        
		//get the connection
		getConnection();
		
		try
		{
		    sqlStatement = connection.prepareCall("{"+dbCommand+"}");
		    
		    //add input parameters
		    sqlStatement.setString(1, name);
		    sqlStatement.setString(2, description);
		    sqlStatement.setString(3,"0");
		    sqlStatement.setString(4, userName);
		    sqlStatement.setString(5, dictionaryId);
		    
		    sqlStatement.registerOutParameter(6, Types.VARCHAR);
		    
		    //execute the sql statement
		    sqlStatement.execute();
		    
		    errmsg = sqlStatement.getString(6);
		    
		    if(!errmsg.equals(""))
		    {
		    	logger.info("Updating dictionary details :"+errmsg);
		    	throw new QuadrigaStorageException();
		    }
		}
		catch(SQLException ex)
		{
			logger.error("Updating dictionary details :",ex);
			throw new QuadrigaStorageException();
		}
	}
	
	@Override
	public void transferDictionaryOwner(String dictionaryId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		//command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.TRANSFER_DICTIONARY_OWNER + "(?,?,?,?,?)";
        
        //get the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//add input parameters
        	sqlStatement.setString(1,dictionaryId);
        	sqlStatement.setString(2, oldOwner);
        	sqlStatement.setString(3, newOwner);
        	sqlStatement.setString(4, collabRole);
        	
        	//add output parameter
        	sqlStatement.registerOutParameter(5, Types.VARCHAR);
        	
           	sqlStatement.execute();
        	errmsg = sqlStatement.getString(5);
        	
        	if(!errmsg.equals(""))
        	{
        		logger.info("Transfer dictionary owner request method :"+errmsg);
        		throw new QuadrigaStorageException(errmsg);
        	}
        }
        catch(SQLException e)
        {
        	logger.error("Transfer dictionary owner request method :"+e);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
	}

}
