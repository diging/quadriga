/**
 * 
 */
package edu.asu.spring.quadriga.db.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * @author satyaswaroop
 *
 */
public abstract  class ADBConnectionManager {
	
	protected Connection connection;
	@Autowired
	protected DataSource dataSource;

	/**
	 *  @Description: Assigns the data source
	 *  
	 *  @param : dataSource
	 */
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}
	/**
	 * @Description : Close the DB connection
	 * 
	 * @return : 0 on success
	 *           -1 on failure
	 *           
	 * @throws : SQL Exception          
	 */
	protected int closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
			return 0;
		}
		catch(SQLException se)
		{
			return -1;
		}
	}

	/**
	 * @Description : Establishes connection with the Quadriga DB
	 * 
	 * @return      : connection handle for the created connection
	 * 
	 * @throws      : SQLException 
	 */
	protected void getConnection() {
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Establishes the test environment
	 * @param sQuery
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	public void setupTestEnvironment(String sQuery) throws QuadrigaStorageException
	{
		getConnection();
		try
		{
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sQuery);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}
	}


}