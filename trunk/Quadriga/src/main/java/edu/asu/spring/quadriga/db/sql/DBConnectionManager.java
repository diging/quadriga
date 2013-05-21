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

/**
 * @Description     : This call implements the database connection to retrieve
 *                    the details of given user.
 *                    
 * @implements      : IDBConnectionManager interface.
 *  
 * @Called By       : UserManager.java
 *                     
 * @author          : Kiran
 *
 */
public class DBConnectionManager implements IDBConnectionManager
{

	private Connection connection;
	
	@Autowired
	private DataSource dataSource;

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	/**
	 *  @Description: Assigns the data source
	 *  
	 *  @param : dataSource
	 */
	@Override
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
	 * @Description : Establishes connection with the Quadriga DB
	 * 
	 * @return      : connection handle for the created connection
	 * 
	 * @throws      : SQLException 
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
	 *  @Description : Retrieves the user details for the given userid
	 *  
	 *  @param       : userid (the userid for which details are to be retrieved).
	 *  
	 *  @return      : null - if the user is not present in the quadriga DB
	 *                 IUser - User object containing the user details.
	 *                 
	 *  @throws      : SQL Exception
	 */
	@Override
	public IUser getUserDetails(String userid)
	{
		List<IQuadrigaRoles> userRole = null;
		String outputValue;
		String dbCommand;
		IUser user = null;
		try
		{
			getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.USER_DETAILS + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,userid);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();

			outputValue = sqlStatement.getString(2);

			if(outputValue.isEmpty())
			{
				ResultSet result =  sqlStatement.getResultSet();

				if(result.isBeforeFirst())
				{
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
		}
		catch(SQLException e)
		{
			throw new RuntimeException("Database Connection error");
		}
		finally
		{
			closeConnection();
		}

		return user;
	}

	/**
	 *   @Description   : Splits the comma separated roles into a list
	 *   
	 *   @param         : roles - String containing the comma separated roles
	 *                            (ex: role1,role3)
	 *                            
	 *   @return        : list of QuadrigaRoles.
	 */
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
