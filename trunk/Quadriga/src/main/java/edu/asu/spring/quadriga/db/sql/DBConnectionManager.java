package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRoles;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;

public class DBConnectionManager implements IDBConnectionManager
{

	private Connection connection;
	private DataSource dataSource;
	
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	@Override
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
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

	/*
	 *    Get the user details from the quadriga DB
	 */
	@Override
	public IUser getUserDetails(String userid)
	{
		List<IQuadrigaRoles> userRole = null;
		String outputValue;
		boolean hasResults = false;
		String dbCommand;
		IUser user = null;
		try
		{
			getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.USER_DETAILS + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,userid);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//execute the statement
			hasResults = sqlStatement.execute();

			outputValue = sqlStatement.getString(2);
			
			if((outputValue.isEmpty())&&(hasResults))
			{
				ResultSet result =  sqlStatement.getResultSet();
                user  = userFactory.createUserObject();
				while(result.next())
				{
					user.setName(result.getString(1));
					user.setUserName(result.getString(2));
					user.setEmail(result.getString(3));
					userRole = UserRoles(result.getString(4));
					user.setQuadrigaRoles(userRole);
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
	
	@Override
	public List<IQuadrigaRoles> UserRoles(String roles)
	{
        String[] role;
        List<IQuadrigaRoles> rolesList = new ArrayList<IQuadrigaRoles>();
        IQuadrigaRoles userRole = null;
        
        role = roles.split("\\s*,\\s*");
        for(int i = 0; i<role.length;i++)
        {
        	userRole = quadrigaRoleFactory.createQuadrigaRoleObject();
        	userRole.setDBid(role[i]);
        	rolesList.add(userRole);
        }
		return rolesList;
	}
}
