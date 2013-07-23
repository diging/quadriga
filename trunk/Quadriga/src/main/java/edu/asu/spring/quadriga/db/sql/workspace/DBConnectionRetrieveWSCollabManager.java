package edu.asu.spring.quadriga.db.sql.workspace;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionRetrieveWSCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

public class DBConnectionRetrieveWSCollabManager extends ADBConnectionManager implements
		IDBConnectionRetrieveWSCollabManager {
	@Autowired
    private IUserManager userManager;
	
	@Autowired
	private IUserFactory userFactory;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionRetrieveWSCollabManager.class);
	
	/**
	 * This method retrieves the collaborators associated with a workspace
	 * @param workspaceId
	 * @return List<ICollaborator>
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public List<ICollaborator> getWorkspaceCollaborators(String workspaceId) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		String username;
		String collabroles;
		CallableStatement sqlStatement;
		IUser user;
		ICollaborator collaborator;
		List<ICollaboratorRole> userCollaboratorRole;
		List<ICollaborator> collaborators = new ArrayList<ICollaborator>();
		
		//command to execute the stored procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_WORKSPACE_COLLABORATOR+ "(?,?)";
		
		//establish database connection
		getConnection();
		
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,workspaceId);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			
			if(errmsg =="")
			{
				ResultSet resultset = sqlStatement.getResultSet();
				
				if(resultset.isBeforeFirst())
				{
					while(resultset.next())
			        {
						username = resultset.getString(1);
						collabroles = resultset.getString(2);
						//retrieve the user details
						user = userManager.getUserDetails(username);
						
						//storing the collaborator roles in a comma(,) separated list
						userCollaboratorRole = this.getCollaboratorDBRoleIdList(collabroles);
						
						//add the user and his collaborator roles to a object
						collaborator = collaboratorFactory.createCollaborator();
						collaborator.setUserObj(user);
						collaborator.setCollaboratorRoles(userCollaboratorRole);
                        //add the collaborator object to a list
						collaborators.add(collaborator);
			        }
				}
			}
			else
			{
				logger.info("getWorkspaceCollaborators method : " + errmsg);
				throw new QuadrigaStorageException(errmsg);
			}
		}
		catch(SQLException e)
		{
			logger.error("getWorkspaceCollaborators method :",e);
		}
		finally
		{
			closeConnection();
		}
		return  collaborators;
	}
	
	/**
	 * This method converts the comma separated(,) collaborator role DB id's to a list
	 * @param collabRoles - String of comma separated collab roles.
	 * @return List<ICollaboratorRole>
	 * @author kiranbatna
	 */
	@Override
	public List<ICollaboratorRole> getCollaboratorDBRoleIdList(String collabRoles)
	{
		//storing the collaborator roles in a comma(,) separated list
		String[] roleList;
		List<ICollaboratorRole> collaboratorRole;
		ICollaboratorRole role;
		
		collaboratorRole = new ArrayList<ICollaboratorRole>();
		roleList = collabRoles.split(",");
		
		for(String dbRoleId : roleList)
		{
			role = collaboratorRoleFactory.createCollaboratorRoleObject();
			role.setRoleDBid(dbRoleId);
			collaboratorRole.add(role);
		}
		return collaboratorRole;
	}
	
	/**
	 * This method retrieves the user who are not collaborators
	 * for a particular project
	 * @param workspaceId
	 * @return List<IUser>
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public List<IUser> getWorkspaceNonCollaborators(String workspaceId) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		String collabUserName;
		CallableStatement sqlStatement ;
		IUser user;
		List<IUser> nonCollaboratorUser = new ArrayList<IUser>();
		
		//statement to execute the stored procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_WORKSPACE_NON_COLLABORATOR + "(?,?)";
		
		//establish database connection
		getConnection();
		
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, workspaceId);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			
			if(errmsg == "")
			{
				ResultSet resultset = sqlStatement.getResultSet();
				if(resultset.isBeforeFirst())
				{
					while(resultset.next())
			        {
						collabUserName = resultset.getString(1);
						//retrieve the user details
						user = userManager.getUserDetails(collabUserName);
						nonCollaboratorUser.add(user);
			        }
				}
			}
			else
			{
				logger.info("getWorkspaceCollaborators method : " + errmsg);
				throw new QuadrigaStorageException(errmsg);
			}
		}
		catch(SQLException e)
		{
			logger.error("getWorkspaceNonCollaborators method :",e);
		}
		finally
		{
			closeConnection();
		}
		return nonCollaboratorUser;
	}

}
