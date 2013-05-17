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
			getConnection();
			CallableStatement sqlStatement = connection.prepareCall("{call sp_getUserDetails(?,?)}");
			sqlStatement.setString(1,userid);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//execute the statement
			sqlStatement.execute();

			outputValue = sqlStatement.getString(2);

			//if( !(outputValue.isEmpty())||(outputValue != null) )
			if(outputValue.isEmpty())
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
		finally
		{
			closeConnection();
		}

		return user;
	}

	/*
	 *  get the role details of a particular user
	 */
	@Override
	public List<String> getUserRoles(String userid)
	{
		getConnection();
		List<String> userRoles = new ArrayList<String>();
		String outputValue;
		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{call sp_getUserRoles(?,?)}");
			sqlStatement.setString(1,userid);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//execute the statement
			sqlStatement.execute();

			outputValue = sqlStatement.getString(2);

			if( outputValue.isEmpty())
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
		finally
		{
			closeConnection();
		}
		return userRoles;
	}
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
