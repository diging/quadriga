package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

public class DBConnectionRetrieveProjCollabManager implements
		IDBConnectionRetrieveProjCollabManager 
{
	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
    private IUserManager userManager;
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionRetrieveProjectManager.class);
	
	/**
	 *  Assigns the data source
	 *  @param  dataSource
	 *  @author Kiran Kumar Batna
	 */
	@Override
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}

	/**
	 * Close the DB connection
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	private void closeConnection() throws QuadrigaStorageException {
		try {
			if (connection != null) {
				connection.close();
			}
		}
		catch(SQLException e)
		{
			logger.info("Close database Connection  :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}

	/**
	 * Establishes connection with the Quadriga DB
	 * @return      connection handle for the created connection
	 * @throws      QuadrigaStorageException
	 * @author      Kiran Kumar Batna
	 */
	private void getConnection() throws QuadrigaStorageException {
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			logger.info("Open database connection :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}
	
	
	/**
	 * This method returns the collaborators associated with given project.
	 * @param projectId
	 * @return List - list of Collaborators associated with the project 
	 * @throws QuadrigaStorageException
	 * @author Rohit Sukelshwar Pendbhaje
	 */
	@Override
	public List<ICollaborator> getProjectCollaborators(String projectId) throws QuadrigaStorageException
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
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.PROJECT_COLLABORATORS + "(?,?)";
		
		//establish database connection
		getConnection();
		
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1,projectId);
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
						userCollaboratorRole = getCollaboratorRolesList(collabroles);
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
				throw new QuadrigaStorageException(errmsg);
			}
		}
		catch(SQLException e)
		{
			logger.info("Retrieve project collaborators method :"+e.getMessage());
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}
		return  collaborators;
	} 

	/**
	 * This method converts the comma(,) separated collaborator roles into a list.
	 * @param role - collaborator roles in a string.
	 * @return List - list of collaborator roles.
	 * @author Rohit Sukelshwar Pendbhaje 
	 */
	@Override
	public List<ICollaboratorRole> getCollaboratorRolesList(String role)
	{
        String[] collabroles;
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole = null;
		
		collabroles = role.split(",");
		
		for(int i=0;i<collabroles.length;i++)
		{
			collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
			collaboratorRole.setRoleDBid(collabroles[i]);
			collaboratorRoleList.add(collaboratorRole);
		}
		return collaboratorRoleList;
	}
	
	/**
	 * This method returns the users who are not collaborators to the supplied project.
	 * @param projectid
	 * @return List<IUser> - user objects who are not collaborators to the supplied project.
	 * @throws QuadrigaStorageException
	 * @author Rohit Sukelshwar Pendbhaje
	 */
	@Override
	public List<IUser> getProjectNonCollaborators(String projectid) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		String collabUserName;
		CallableStatement sqlStatement ;
		IUser user;
		List<IUser> nonCollaboratorUser = new ArrayList<IUser>();
		
		//statement to execute the stored procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.SHOW_NONCOLLABORATOR_REQUEST + "(?,?)";
		
		//establish database connection
		getConnection();
		
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, projectid);
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
				throw new QuadrigaStorageException(errmsg);
			}
		}
		catch(SQLException e)
		{
			logger.info("Retrieve project noncollaborators method :"+e.getMessage());
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}
		return nonCollaboratorUser;
	}

	@Override
	public List<IUser> getProjectCollaboratorsRequest(String projectid)throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		String collabUserName;
		CallableStatement sqlStatement = null ;
		IUser user;
		List<IUser> CollaboratorUser = new ArrayList<IUser>();
		
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.SHOW_COLLABORATOR_REQUEST + "(?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
		 
		sqlStatement.setString(1, projectid);
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
					CollaboratorUser.add(user);

		        }
		    }
		}
		
		else{
			throw new QuadrigaStorageException(errmsg);
		}
		}
		catch (SQLException e) {
			logger.info("Retrieve project collaborators method :"+e.getMessage());
			throw new QuadrigaStorageException();
		}
		
		finally
		{
			closeConnection();
		}
		
		return CollaboratorUser;
	}

}
