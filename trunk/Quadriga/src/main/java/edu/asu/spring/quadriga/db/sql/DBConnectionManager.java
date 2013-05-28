package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import javax.swing.text.html.HTMLDocument.Iterator;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factories.impl.UserFactory;
import edu.asu.spring.quadriga.domain.implementation.Project;

/**
 * @Description      This call implements the database connection to retrieve
 *                   the details of given user.
 *                    
 * @implements       IDBConnectionManager interface.
 *  
 * @Called By        UserManager.java
 *                     
 * @author           Kiran
 * @author 			 Ram Kumar Kumaresan
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
	
	@Autowired
	private IProject project;
	
	@Autowired
	private IProjectFactory projectfactory;
	
	private ResultSet resultset;
	

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
		List<IQuadrigaRole> userRole = null;
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
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}

		return user;
	}

	@Override
	public List<IUser> getAllActiveUsers()
	{
		List<IUser> listUsers = null;
		String sDBCommand;
		String sOutErrorValue;
		
		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ACTIVE_USER_DETAILS + "(?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.registerOutParameter(1,Types.VARCHAR);

			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(1);

			if(sOutErrorValue == null)
			{
				listUsers = new ArrayList<IUser>();				
				IUser user = null;
				List<IQuadrigaRole> userRole = null;
				
				ResultSet rs = sqlStatement.getResultSet();
				while(rs.next())
				{					
					user = this.userFactory.createUserObject();
					user.setName(rs.getString(1));
					user.setUserName(rs.getString(2));
					user.setEmail(rs.getString(3));
					userRole = UserRoles(rs.getString(4));
					user.setQuadrigaRoles(userRole);	
					
					listUsers.add(user);
				}				
			}			
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return listUsers;
	}
	
	@Override
	public List<IUser> getAllInActiveUsers()
	{
		List<IUser> listUsers = null;
		String sDBCommand;
		String sOutErrorValue;
		
		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.INACTIVE_USER_DETAILS + "(?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.registerOutParameter(1,Types.VARCHAR);

			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(1);

			if(sOutErrorValue == null)
			{
				listUsers = new ArrayList<IUser>();				
				IUser user = null;
				List<IQuadrigaRole> userRole = null;
				
				ResultSet rs = sqlStatement.getResultSet();
				while(rs.next())
				{	
					user = this.userFactory.createUserObject();
					user.setName(rs.getString(1));
					user.setUserName(rs.getString(2));
					user.setEmail(rs.getString(3));
					userRole = UserRoles(rs.getString(4));
					user.setQuadrigaRoles(userRole);	
					
					listUsers.add(user);
				}				
			}			
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return listUsers;
	}
	
	@Override
	public int deactivateUser(String sUserId,String sDeactiveRoleDBId)
	{
		String sDBCommand;
		String sOutErrorValue;
		
		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.DEACTIVATE_USER+ "(?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.setString(1, sUserId);
			sqlStatement.setString(2, sDeactiveRoleDBId);
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(3);

			if(sOutErrorValue == null)
			{
				//User deactivated successfully
				return 1;
			}			
			else
			{
				//Error occurred in the database
				return 0;
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}
	
	@Override
	public int updateUserRoles(String sUserId,String sRoles)
	{
		String sDBCommand;
		String sOutErrorValue;
		
		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_USER_ROLES+ "(?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.setString(1, sUserId);
			sqlStatement.setString(2, sRoles);
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(3);

			if(sOutErrorValue == null)
			{
				//User roles updated successfully
				return 1;
			}			
			else
			{
				//Error occurred in the database
				return 0;
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
	}
	
	@Override
	public List<IUser> getUserRequests()
	{
		List<IUser> listUsers = null;
		String sDBCommand;
		String sOutErrorValue;
		
		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.GET_USER_REQUESTS+ "(?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.registerOutParameter(1,Types.VARCHAR);

			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(1);

			if(sOutErrorValue == null)
			{
				listUsers = new ArrayList<IUser>();				
				IUser user = null;
				
				ResultSet rs = sqlStatement.getResultSet();
				while(rs.next())
				{					
					user = this.userFactory.createUserObject();
					user.setName(rs.getString(1));
					user.setUserName(rs.getString(2));
					user.setEmail(rs.getString(3));					
					listUsers.add(user);
				}				
			}			
			else
			{
				throw new SQLException("Error occurred in the Database");
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		
		return listUsers;
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
	public List<IQuadrigaRole> UserRoles(String roles)
	{
		String[] role;
		List<IQuadrigaRole> rolesList = new ArrayList<IQuadrigaRole>();
		IQuadrigaRole userRole = null;

		role = roles.split("\\s*,\\s*");
		for(int i = 0; i<role.length;i++)
		{
			userRole = quadrigaRoleFactory.createQuadrigaRoleObject();
			userRole.setDBid(role[i]);
			rolesList.add(userRole);
		}
		return rolesList;
	}


	@Override
	public List<IProject> getProjectOfUser(String sUserId) {
		
		String dbCommand;
		
		getConnection();
		List<IProject> projectList = new ArrayList<IProject>();
//		project = new Project();
		
		
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.PROJECT_DETAILS + "(?)";
		try {
			
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			sqlStatement.registerOutParameter(1, Types.VARCHAR);
			
			sqlStatement.execute();
			
			resultset = sqlStatement.getResultSet();

	        while(resultset.next())
	        {
	            
	           	project = projectfactory.createProjectObject();
	        	project.setName(resultset.getString(1));
	        	project.setDescription(resultset.getString(2));
	        	project.setId(resultset.getString(2));
	        	project.setInternalid(resultset.getString(2));
	        	
	        	projectList.add(project);
	        	
	         }
	          	
		} 
		
		catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		return projectList;
	}
	
}
