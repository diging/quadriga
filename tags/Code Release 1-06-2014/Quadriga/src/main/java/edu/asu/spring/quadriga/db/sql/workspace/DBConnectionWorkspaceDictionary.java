package edu.asu.spring.quadriga.db.sql.workspace;

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
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWorkspaceDictionary;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


/** 
 * DB connection class to add, list, delete dictionary in workspace
 * @author Lohith Dwaraka
 *
 */
public class DBConnectionWorkspaceDictionary extends ADBConnectionManager implements IDBConnectionWorkspaceDictionary {
	
	@Autowired
	private IDictionaryFactory dictionaryFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionWorkspaceDictionary.class);

	
	/**
	 *  Method add a Concept collection to a workspace                   
	 * @returns         path of list workspace Concept collection page
	 * @throws			SQLException
	 * @author          Lohith Dwaraka
	 */
	@Override
	public void addWorkspaceDictionary(String workspaceId,
			String dictionaryId, String userId) throws QuadrigaStorageException {
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_WORKSPACE_DICTIONARY  + "(?,?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, workspaceId);
			sqlStatement.setString(2, dictionaryId);        	
			sqlStatement.setString(3,userId);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(4);

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
	}

	/**
	 * Method to list the Concept collection in workspace
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public List<IDictionary> listWorkspaceDictionary(String workspaceId,
			String userId) throws QuadrigaStorageException {
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_WORKSPACE_DICTIONARY  + "(?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, workspaceId);
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
				logger.info("No dictionaries in the workspace :"+errmsg);
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

	/**
	 * Method to delete dictionaries from a workspace
	 * @param workspaceId
	 * @param userId
	 * @param dictioanaryId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public void deleteWorkspaceDictionary(String workspaceId, String userId,
			String dictioanaryId) throws QuadrigaStorageException {
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;		

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_WORKSPACE_DICTIONARY  + "(?,?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, userId);
			sqlStatement.setString(2, dictioanaryId);        	
			sqlStatement.setString(3, workspaceId);  
			//adding output variables to the SP
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			sqlStatement.execute();
			errmsg = sqlStatement.getString(4);
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
	}

}
