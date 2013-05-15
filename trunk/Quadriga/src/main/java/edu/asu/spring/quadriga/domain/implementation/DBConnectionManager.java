package edu.asu.spring.quadriga.domain.implementation;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import edu.asu.spring.quadriga.domain.IDBConnectionManager;

public class DBConnectionManager implements IDBConnectionManager{

	private Connection connection;
	private String url, user, password;
	
	public DBConnectionManager() {
	connection = null;
	url = "jdbc:mysql://localhost:3307/dblp";
	user = "root";
	password = "root";
	}
	
	/**
	 * The getConnection method is used to connect to the database using the provided
	 * url, userid and password 
	 * 
	 * @return Statement handle for the created connection
	 * @throws SQLException 
	 */
	private void getConnection() throws SQLException {
		connection = DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * The closeConnection method is used to close the opened 
	 * statement and connection
	 * 
	 * @return  0 - successfully closed the connection
	 *          1 - Exception occurred in closing the connection
	 */	
	private int closeConnection() {
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
	
	public User getUserDetails(String sUserId) throws SQLException
	{
		getConnection();
		
		CallableStatement cStatement = connection.prepareCall("{call sp_getQuadrigaUserDetails(?,?,?,?)}");
		cStatement.setString(1, sUserId);
		cStatement.registerOutParameter(2, Types.VARCHAR);
		cStatement.registerOutParameter(3, Types.VARCHAR);
		cStatement.registerOutParameter(4, Types.VARCHAR);
		
		
		closeConnection();
		return null;		
	}
}
