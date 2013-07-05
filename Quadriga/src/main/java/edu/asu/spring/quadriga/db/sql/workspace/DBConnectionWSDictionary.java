package edu.asu.spring.quadriga.db.sql.workspace;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWSDictionary;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionWSDictionary implements IDBConnectionWSDictionary {

	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionWSDictionary.class);
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.db.sql.workspace.IDBConnectionWSDictionary#setDataSource(javax.sql.DataSource)
	 */
	//@Override
	@Override
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}

	/**
	 * Close the DB connection
	 * @throws QuadrigaStorageException
	 * @author Lohith Dwaraka
	 */
	private void closeConnection() throws QuadrigaStorageException {
		try {
			if (connection != null) {
				connection.close();
			}
		}
		catch(SQLException e)
		{
			logger.info("Close database connection :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}

	/**
	 * Establishes connection with the Quadriga DB
	 * @return      connection handle for the created connection
	 * @throws      QuadrigaStorageException
	 * @author      Lohith Dwaraka
	 */
	private void getConnection() throws QuadrigaStorageException {
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			logger.info("Establish database connection  :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.db.sql.workspace.IDBConnectionWSDictionary#addWSDictionary(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String addWSDictionary(String projectId, String dictionaryId, String userId)throws QuadrigaStorageException
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
}
