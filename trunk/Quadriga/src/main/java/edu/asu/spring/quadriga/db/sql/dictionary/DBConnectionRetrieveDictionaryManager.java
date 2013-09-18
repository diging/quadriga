package edu.asu.spring.quadriga.db.sql.dictionary;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.dictionary.IDBConnectionRetrieveDictionaryManager;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

public class DBConnectionRetrieveDictionaryManager extends ADBConnectionManager implements
		IDBConnectionRetrieveDictionaryManager 
{
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionRetrieveDictionaryManager.class);
	
	@Autowired
	private IDictionaryFactory dictionaryFactory;
	
	@Autowired
	private IUserManager userManager;
	
	@Override
	public IDictionary getDictionaryDetails(String dictionaryId) throws QuadrigaStorageException
	{

        String dbCommand;
        String errmsg;
        CallableStatement sqlStatement;
        IDictionary dictionary = null;
        IUser user= null;
        
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_DICTIONARY_DETAILS  + "(?,?)";
        
		//get the connection
		getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//assign input and output parameters
        	sqlStatement.setString(1, dictionaryId);
            sqlStatement.registerOutParameter(2,Types.VARCHAR);
            
            sqlStatement.execute();
            
            errmsg = sqlStatement.getString(2);
            
            if(errmsg.equals(""))
            {
            	dictionary = dictionaryFactory.createDictionaryObject();
            	ResultSet result =  sqlStatement.getResultSet();
            	
            	if(result.isBeforeFirst())
				{
					while(result.next())
					{
						dictionary.setName(result.getString(1));
						dictionary.setDescription(result.getString(2));
						user = userManager.getUserDetails(result.getString(3));
						dictionary.setOwner(user);
						dictionary.setId(result.getString(4));
					}
				}
            }
            else
            {
            	logger.info("Retrieve Dictionay details :"+errmsg);
            	throw new QuadrigaStorageException(errmsg);
            }
        }
        catch(SQLException ex)
        {
        	logger.error("Retrieve Dcitionary details : ",ex);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
        return dictionary;
	}
}
