package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.support.SQLStateSQLExceptionTranslator;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.implementation.Project;
import edu.asu.spring.quadriga.domain.implementation.User;

/**
 * @Description      This call implements the database connection to retrieve
 *                   the details of given user.
 *                    
 * @implements       IDBConnectionManager interface.
 *  
 * @Called By        UserManager.java
 *                     
 * @author           Kiran Kumar Batna
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
	private IProjectFactory projectfactory;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;

	private ResultSet resultset,resultset1;

	private IProject project;


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
			else
			{
				throw new SQLException(outputValue);
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

	/**
	 * Queries the database and builds a list of active user objects
	 * 
	 * @return List containing user objects of all active users
	 */
	@Override
	public List<IUser> getAllActiveUsers(String sInactiveRoleId)
	{
		List<IUser> listUsers = null;
		String sDBCommand;
		String sOutErrorValue;

		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ACTIVE_USER_DETAILS + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.setString(1, sInactiveRoleId);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//Execute the SQL Stored Procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(2);

			//No SQL exception has occurred
			if(sOutErrorValue == null)
			{
				listUsers = new ArrayList<IUser>();				
				IUser user = null;
				List<IQuadrigaRole> userRole = null;

				ResultSet rs = sqlStatement.getResultSet();

				//Iterate through each row returned by the database
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
			else
			{
				throw new SQLException(sOutErrorValue);
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
	 * Queries the database and builds a list of inactive user objects
	 * 
	 * @return List containing user objects of all inactive users
	 */
	@Override
	public List<IUser> getAllInActiveUsers(String sInactiveRoleId)
	{
		List<IUser> listUsers = null;
		String sDBCommand;
		String sOutErrorValue;

		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.INACTIVE_USER_DETAILS + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");	
			sqlStatement.setString(1, sInactiveRoleId);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			//Execute the SQL Stored Procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(2);

			//No SQL exception has occurred			
			if(sOutErrorValue == null)
			{
				listUsers = new ArrayList<IUser>();				
				IUser user = null;
				List<IQuadrigaRole> userRole = null;

				ResultSet rs = sqlStatement.getResultSet();

				//Iterate through each row returned by the database
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
			else
			{
				throw new SQLException(sOutErrorValue);
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
	 * Deactivate a user in Quadriga.
	 * 
	 * @param 	sUserId					The userid of the user whose account has to be deactivated
	 * @param 	sDeactiveRoleDBId		The roleid corresponding to the inactive role fetched from the application context file
	 * 
	 * @return	Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 * 
	 */
	@Override
	public int deactivateUser(String sUserId,String sDeactiveRoleDBId,String sAdminId)
	{
		String sDBCommand;
		String sOutErrorValue;

		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.DEACTIVATE_USER+ "(?,?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.setString(1, sUserId);
			sqlStatement.setString(2, sDeactiveRoleDBId);
			sqlStatement.setString(3, sAdminId); 
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			//Execute the Stored Procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(4);

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

	/**
	 * Overwrite the existing userroles with the new user roles.
	 * 
	 * @param sUserId The userid of the user whose roles are to be changed.
	 * @param sRoles The new roles of the user. Must be fetched from the applicaton context file.
	 * @param sAdminId The userid of the admin who is changing the user setting
	 * 
	 * @return Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 */
	@Override
	public int updateUserRoles(String sUserId,String sRoles,String sAdminId)
	{
		String sDBCommand;
		String sOutErrorValue;

		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.UPDATE_USER_ROLES+ "(?,?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.setString(1, sUserId);
			sqlStatement.setString(2, sRoles);
			sqlStatement.setString(3, sAdminId); 
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(4);

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

	/**
	 * Approve the user request to access Quadriga and also assign new roles set by the admin.
	 * 
	 * @param sUserId The userid of the user whose access has been approved.
	 * @param sRoles The roles set by the admin. Must correspond to the roles found in the application context file
	 * @param sAdminId The userid of the admin who is changing the user setting
	 * 
	 * @return Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 * 
	 */
	@Override
	public int approveUserRequest(String sUserId, String sRoles, String sAdminId)
	{
		String sDBCommand;
		String sOutErrorValue;

		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.APPROVE_USER_REQUEST+ "(?,?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.setString(1, sUserId);
			sqlStatement.setString(2, sRoles);
			sqlStatement.setString(3,sAdminId);
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(4);

			if(sOutErrorValue == null)
			{
				//User request approved successfully
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

	/**
	 * A user has been denied the access to Quadriga.
	 * 
	 * @param sUserId		The userid of the user whose request is rejected
	 * @param sAdminId 		The admin-userid who rejected the request
	 * 
	 * Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 */
	@Override
	public int denyUserRequest(String sUserId,String sAdminId)
	{
		String sDBCommand;
		String sOutErrorValue;

		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.DENY_USER_REQUEST+ "(?,?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.setString(1, sUserId);
			sqlStatement.setString(2, sAdminId);
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(3);

			if(sOutErrorValue == null)
			{
				//User request approved successfully
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


	/**
	 * Returns all open user requests to quadriga.
	 * 
	 * @return Returns the list of user objects whose request are to be approved/denied.
	 * 
	 */	
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
				throw new SQLException(sOutErrorValue);
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
	 * Add a new account request to the quadriga.
	 * 
	 * @param sUserId The user id of the user who needs access to quadriga 
	 * @return Integer value that specifies the status of the operation. 1 - Successfully place the request. 
	 */
	
	@Override
	public int addAccountRequest(String sUserId)
	{
		String sDBCommand;
		String sOutErrorValue;

		try
		{
			getConnection();
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_USER_REQUEST+ "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			
			sqlStatement.setString(1, sUserId);;
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(2);

			if(sOutErrorValue == null)
			{
				//User request submitted successfully
				return 1;
			}			
			else
			{
				//Error occurred in database.
				throw new SQLException(sOutErrorValue);
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


	
	/**
	 * @Description     This method fetches the list of projects for current logged in user                    
	 * 
	 * @returns         List of projects
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Rohit Sukelshwar Pendbhaje
	 * 
     */
	
	@Override
	public List<IProject> getProjectOfUser(String sUserId) {


		String dbCommand;
		
		getConnection();
		List<IProject> projectList = new ArrayList<IProject>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.PROJECT_LIST + "(?)";
		try {
			
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			sqlStatement.registerOutParameter(1, Types.VARCHAR);
			
			sqlStatement.execute();
			
			ResultSet resultset = sqlStatement.getResultSet();
			
			while(resultset.next())
	        {
	        	project = projectfactory.createProjectObject();
	        	project.setName(resultset.getString(1));
	        	project.setDescription(resultset.getString(2));
	        	project.setId(resultset.getString(3));
	           	
	        	projectList.add(project);
	        
	         }
		
		} 
		catch (SQLException e) {

			e.printStackTrace();
		}

		return projectList;
	}


	@Override
	public void setUserDetails(String name, String username, String email,
			String roles) {
		
		throw new NotImplementedException("setUserDetails is not implemented");

	}
	
	/**
	 * @Description     This method takes string from database and converts it into the owner 
	 * 					(type of User class) object 
	 * 					 
	 * @returns         User object
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Rohit Sukelshwar Pendbhaje
	 * 
     */

	public IUser projectOwner()
	{
		IUser owner = userFactory.createUserObject();
        return owner;

	}
	
	public IUser projectCollaborators(String collaborators)
	{
		String[] collaborator;
		
		List<IUser> collaboratorList = new ArrayList<IUser>();

		IUser projectcollaborator = null;
		
		collaborator = collaborators.split(",");
		
		for(int i=0; i<collaborator.length;i++)
		{
			
			projectcollaborator = userFactory.createUserObject();
			
			projectcollaborator.setProjectCollaborator(collaborator[i]);
			
			collaboratorList.add(projectcollaborator);
		}
		
		return projectcollaborator;
		
	} 
	
	public ICollaboratorRole CollaboratorRole(String roles)
	{
		String role = null;
		
		ICollaboratorRole collaboratorRole = null;
		
		collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
		
		collaboratorRole.setRoleDBid(role);
		return collaboratorRole;
	}
	
	/**
	 * @Description     This method fetches the details of projects from database for the logged in user                    
	 * 
	 * @returns         project object
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Rohit Sukelshwar Pendbhaje
	 * 
     */
	
	@Override
	public IProject getProjectDetails(String projectId) {
		
		String dbCommand,dbCommand1;
		CallableStatement sqlStatement ;
		
		getConnection();

		IProject project = new Project();
		
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.PROJECT_DETAILS + "(?)";

		try {
				
        sqlStatement = connection.prepareCall("{"+dbCommand+"}");

		sqlStatement.registerOutParameter(1, Types.VARCHAR);

		sqlStatement.execute();
		
		ResultSet resultset = sqlStatement.getResultSet();

		while(resultset.next())
        {
			
        	if( projectId.equals(resultset.getString(3)))
        	{
        		project = projectfactory.createProjectObject();
	        	project.setName(resultset.getString(1));
	        	project.setDescription(resultset.getString(2));
	        	project.setId("quadriga" + resultset.getString(3));
	        	IUser owner = projectOwner();
	        	owner.setName(resultset.getString(4));
	        	project.setOwner(owner);
	        	
        	}
        		         
        }
       
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		dbCommand1 = DBConstants.SP_CALL+ " " + DBConstants.PROJECT_COLLABORATORS + "(?)";
		 try {
			sqlStatement = connection.prepareCall("{"+dbCommand1+"}");
		
			sqlStatement.registerOutParameter(1, Types.VARCHAR);

			sqlStatement.execute();
			
			resultset1 = sqlStatement.getResultSet();
			
			while(resultset1.next())
			{
								
				if( projectId.equals(resultset1.getString(1)))
				{
					project.setId(resultset1.getString(1));
					IUser collaborator = projectCollaborators(resultset1.getString(2));
					collaborator.setName(resultset1.getString(2));
					project.setProjectCollaborator(collaborator);
					ICollaboratorRole collaboratorrole = CollaboratorRole(resultset1.getString(3));
					project.setProjectCollaboratorRole(collaboratorrole);
				}
			}
		
		 } 
		 
		 catch (SQLException e) {
			 e.printStackTrace();
		 }
			
		return project;
	}

}
