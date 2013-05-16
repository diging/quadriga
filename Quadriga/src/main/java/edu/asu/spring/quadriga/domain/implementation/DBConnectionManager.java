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
	 * Gets the userdetails from the Quad DB and creates a user object 
	 * based on that information to be passed onto the application layer
	 * 
	 * @return User object corresponding to the user id
	 * @throws SQLException
	 */	
	public User getUserDetails(String sUserId) throws SQLException
	{
		getConnection();
		
		User user = new User();
		CallableStatement cStatement = connection.prepareCall("{call sp_getQuadrigaUserDetails(?,?,?,?,?)}");
		cStatement.setString(1, sUserId);
		cStatement.registerOutParameter(2, Types.VARCHAR);
		cStatement.registerOutParameter(3, Types.VARCHAR);
		cStatement.registerOutParameter(4, Types.VARCHAR);
		cStatement.registerOutParameter(5, Types.INTEGER);
		
		//Make the call to the stored procedure
		cStatement.execute();
		
		int iError = cStatement.getInt(5);
		
		//User is valid and active
		if(iError==0)
		{
			user.setName(cStatement.getString(2));
			user.setEmail(cStatement.getString(3));
			//TODO roles: string to list logic			
		}
		//User is valid but inactive
		else if(iError==1)
		{
			//Set null for user name
			user.setName(null);
		}
		else
		{
			user = null;
		}
		closeConnection();
		return user;		
	}
	
	/**
	 * Used to connect to the Quad DB using the provided
	 * url, userid and password 
	 * 
	 * @return connection handle for the created connection
	 * @throws SQLException 
	 */
	private void getConnection() throws SQLException {
		connection = DriverManager.getConnection(url, user, password);
	}
	
	/**
	 * Used to close the opened database connection
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
	
}
