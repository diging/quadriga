package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionManager;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IProjectFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

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

public class DBConnectionManager extends ADBConnectionManager implements IDBConnectionManager
{

	private static final Logger logger = LoggerFactory
			.getLogger(DBConnectionManager.class);
	

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	@Autowired
	private IProjectFactory projectfactory;

	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;


	/**
	 *  @Description : Retrieves the user details for the given userid
	 *  
	 *  @param       : userid (the userid for which details are to be retrieved).
	 *  
	 *  @return      : null - if the user is not present in the quadriga DB
	 *                 IUser - User object containing the user details.
	 * @throws QuadrigaStorageException 
	 *                 
	 *  @throws      : SQL Exception
	 */
	@Override
	public IUser getUserDetails(String userid) throws QuadrigaStorageException
	{
		List<IQuadrigaRole> userRole = null;
		String outputValue;
		String dbCommand;
		IUser user = null;
		Connection connection = null;

		try
		{
			connection = dataSource.getConnection();
			dbCommand = DBConstants.SP_CALL + " " + DBConstants.USER_DETAILS + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,userid);
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();

			outputValue = sqlStatement.getString(2);

			if(outputValue == "")
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
						userRole = listQuadrigaUserRoles(result.getString(4));
						user.setQuadrigaRoles(userRole);
					}
				}
			}
			else
			{
				logger.error("Exception in getUserGetails():");
				throw new QuadrigaStorageException(outputValue);
			}
		}
		catch(SQLException e)
		{
			logger.error("Exception in getUserDetails():",e);
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			try {
				connection.close();
			} catch (SQLException e) {
				throw new QuadrigaStorageException(e);
			}
		}

		return user;
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<IUser> getUsers(String userRoleId) throws QuadrigaStorageException
	{
		List<IUser> listUsers = null;
		String sDBCommand;
		String sOutErrorValue;
		getConnection();
		try
		{
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.GET_USERS + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");	
			sqlStatement.setString(1, userRoleId);
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
					userRole = listQuadrigaUserRoles(rs.getString(4));
					user.setQuadrigaRoles(userRole);	

					listUsers.add(user);
				}				
			}
			else
			{
				logger.error("Exception in getAllInActiveUsers():");
				throw new QuadrigaStorageException(sOutErrorValue);
			}
		}
		catch(SQLException e)
		{
			logger.error("Exception in getAllInActiveUsers():",e);
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}
		return listUsers;
	}
	
	/**
	 * {@inheritDoc} 
	 */
	@Override
	public List<IUser> getUsersNotInRole(String userRoleId) throws QuadrigaStorageException
	{
		List<IUser> listUsers = null;
		String sDBCommand;
		String sOutErrorValue;
		getConnection();
		try
		{
			sDBCommand = DBConstants.SP_CALL + " " + DBConstants.GET_USERS_NOT_IN_ROLE + "(?,?)";
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");	
			sqlStatement.setString(1, userRoleId);
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
					userRole = listQuadrigaUserRoles(rs.getString(4));
					user.setQuadrigaRoles(userRole);	

					listUsers.add(user);
				}				
			}
			else
			{
				logger.error("Exception in getAllInActiveUsers():");
				throw new QuadrigaStorageException(sOutErrorValue);
			}
		}
		catch(SQLException e)
		{
			logger.error("Exception in getAllInActiveUsers():",e);
			throw new QuadrigaStorageException(e);
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
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public int deactivateUser(String sUserId,String sDeactiveRoleDBId,String sAdminId) throws QuadrigaStorageException
	{
		String sDBCommand;
		String sOutErrorValue;
		getConnection();
		try
		{
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
				return SUCCESS;
			}			
			else
			{
				//Error occurred in the database
				return FAILURE;
			}
		}
		catch(SQLException e)
		{
			logger.error("Exception in deactiveUser():",e);
			throw new QuadrigaStorageException(e);
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
	 * 
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public int updateUserRoles(String sUserId,String sRoles,String sAdminId) throws QuadrigaStorageException
	{
		String sDBCommand;
		String sOutErrorValue;
		getConnection();
		try
		{
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
				return SUCCESS;
			}			
			else
			{
				//Error occurred in the database
				return FAILURE;
			}
		}
		catch(SQLException e)
		{
			logger.info("Exception in updateUserRoles():",e);
			throw new QuadrigaStorageException(e);
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
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public int approveUserRequest(String sUserId, String sRoles, String sAdminId) throws QuadrigaStorageException
	{
		String sDBCommand;
		String sOutErrorValue;
		getConnection();
		try
		{
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
			logger.error("Exception in approveUserRequest():",e);
			throw new QuadrigaStorageException(e);
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
	 * @return Returns the status of the operation. 1 - Deactivated. 0 - Error occurred.
	 * 
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public int denyUserRequest(String sUserId,String sAdminId) throws QuadrigaStorageException
	{
		String sDBCommand;
		String sOutErrorValue;
		getConnection();
		try
		{
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
				return SUCCESS;
			}			
			else
			{
				//Error occurred in the database
				return FAILURE;
			}
		}
		catch(SQLException e)
		{
			logger.error("Exception in denyUserRequest():",e);
			throw new QuadrigaStorageException(e);
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
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */	
	@Override
	public List<IUser> getUserRequests() throws QuadrigaStorageException
	{
		List<IUser> listUsers = null;
		String sDBCommand;
		String sOutErrorValue;
		getConnection();
		try
		{
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
				throw new QuadrigaStorageException(sOutErrorValue);
			}
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e);
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
	 * 
	 * @author Ram Kumar Kumaresan
	 * @throws QuadrigaStorageException 
	 */

	@Override
	public int addAccountRequest(String sUserId) throws QuadrigaStorageException
	{
		String sDBCommand;
		String sOutErrorValue;
		getConnection();
		try
		{
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
				logger.error("Exception in addAccountRequest():");
				throw new QuadrigaStorageException(sOutErrorValue);
			}
		}
		catch(SQLException e)
		{
			logger.error("Exception in addAccountRequest():",e);
			throw new QuadrigaStorageException(e);
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
	public List<IQuadrigaRole> listQuadrigaUserRoles(String roles)
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
}
