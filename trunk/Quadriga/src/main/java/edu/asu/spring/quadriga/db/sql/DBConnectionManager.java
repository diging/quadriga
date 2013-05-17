package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRoles;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.implementation.QuadrigaRole;
import edu.asu.spring.quadriga.domain.implementation.User;


public class DBConnectionManager implements IDBConnectionManager{

	private Connection connection;
	private DataSource dataSource;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/*
	 *    Get the user details from the quadriga DB
	 */
	public IUser getUserDetails(String userid)
	{
		IUser user = new User();
		String outputValue;
		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{call sp_getUserDetails(?,?)}");
			sqlStatement.setString(1,userid);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//execute the statement
			sqlStatement.execute();

			outputValue = sqlStatement.getString(2);

			if( (outputValue.isEmpty())||(outputValue == null) )
			{
				ResultSet result =  sqlStatement.getResultSet();

				while(result.next())
				{
					user.setName(result.getString(1));
					user.setEmail(result.getString(3));
				}
			}	 
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

		return user;
	}

	/*
	 *  get the role details of a particular user
	 */
	@Override
	public List<String> getUserRoles(String userid)
	{
		List<String> userRoles = new ArrayList<String>();
		String outputValue;
		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{call sp_getUserDetails(?,?)}");
			sqlStatement.setString(1,userid);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//execute the statement
			sqlStatement.execute();

			outputValue = sqlStatement.getString(2);

			if( (outputValue.isEmpty())||(outputValue == null) )
			{
				ResultSet result =  sqlStatement.getResultSet();

				while(result.next())
				{
					userRoles.add(result.getString(1));
				}
			}	 
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return userRoles;
	}

	//	/**
	//	 * Gets the userdetails from the Quad DB and creates a user object 
	//	 * based on that information to be passed onto the application layer
	//	 * 
	//	 * @return User object corresponding to the user id
	//	 * @throws SQLException
	//	 */	
	//	public User getUserDetails(String sUserId) throws SQLException
	//	{
	//		getConnection();
	//				
	//		User user = new User();
	//		CallableStatement cStatement = connection.prepareCall("{call sp_getQuadrigaUserDetails(?,?,?,?,?)}");
	//		cStatement.setString(1, sUserId);
	//		cStatement.registerOutParameter(2, Types.VARCHAR);
	//		cStatement.registerOutParameter(3, Types.VARCHAR);
	//		cStatement.registerOutParameter(4, Types.VARCHAR);
	//		cStatement.registerOutParameter(5, Types.INTEGER);
	//		
	//		//Make the call to the stored procedure
	//		cStatement.execute();
	//		int iError = cStatement.getInt(5);
	//		
	//		//User is valid and active
	//		if(iError==0)
	//		{
	//			user.setName(cStatement.getString(2));
	//			user.setEmail(cStatement.getString(3));
	//			//TODO roles: string to list logic			
	//		}
	//		//User is valid but inactive
	//		else if(iError==1)
	//		{
	//			//Set null for user name
	//			user.setName(null);
	//		}
	//		else
	//		{
	//			user = null;
	//		}
	//		closeConnection();
	//		return user;		
	//	}

	/**
	 * Used to connect to the Quad DB using the provided
	 * url, userid and password 
	 * 
	 * @return connection handle for the created connection
	 * @throws SQLException 
	 */
	private void getConnection() {
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
