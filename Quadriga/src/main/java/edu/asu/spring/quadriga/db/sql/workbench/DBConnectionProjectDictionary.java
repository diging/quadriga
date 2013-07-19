package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionProjectDictionary extends ADBConnectionManager implements IDBConnectionProjectDictionary {

	@Autowired
	private IDictionaryFactory dictionaryFactory;
	

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionProjectDictionary.class);
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.db.sql.workspace.IDBConnectionWSDictionary#addWSDictionary(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String addProjectDictionary(String projectId, String dictionaryId, String userId)throws QuadrigaStorageException
	{

		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;



		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_PROJECT_DICTIONARY  + "(?,?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, projectId);
			sqlStatement.setString(2, dictionaryId);        	
			sqlStatement.setString(3,userId);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(4);

			return errmsg;

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			e.printStackTrace();
			throw new QuadrigaStorageException();
			
		}catch(Exception e){
			errmsg="DB Issue";
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
	
	@Override
	public List<IDictionary> listProjectDictionary(String projectId,String userId)throws QuadrigaStorageException
	{
		
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_PROJECT_DICTIONARY  + "(?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, projectId);
			sqlStatement.setString(2, userId);        	

			//adding output variables to the SP
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					IDictionary dictionary = dictionaryFactory.createDictionaryObject();
					dictionary.setName(resultSet.getString(1));
					dictionary.setDescription(resultSet.getString(2));
					dictionary.setId(resultSet.getString(3));
					dictionaryList.add(dictionary);
				} 
			}
			errmsg = sqlStatement.getString(3);
			if(errmsg.isEmpty())
			{
				return dictionaryList;
			}
			else
			{
				logger.info("No dictionaries in the project :"+errmsg);
				return null;
			}
		}catch(SQLException e){
			logger.info(e.getMessage());
			throw new QuadrigaStorageException();
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return dictionaryList;
	}
	
	@Override
	public String deleteProjectDictionary(String projectId,String userId,String dictioanaryId)throws QuadrigaStorageException
	{
		
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;		

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_PROJECT_DICTIONARY  + "(?,?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, userId);
			sqlStatement.setString(2, dictioanaryId);        	
			sqlStatement.setString(3, projectId);  
			//adding output variables to the SP
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			sqlStatement.execute();
			errmsg = sqlStatement.getString(4);
			return errmsg;
		}catch(SQLException e){
			logger.info(e.getMessage());
			throw new QuadrigaStorageException();
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return "";
	}
}
