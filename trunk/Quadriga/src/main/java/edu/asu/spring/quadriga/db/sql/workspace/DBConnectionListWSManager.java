package edu.asu.spring.quadriga.db.sql.workspace;

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
import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * Class implements {@link IDBConnectionListWSManager} for all the DB connection 
 * necessary for listing workspace associated with project.
 * @implements IDBConnectionListWSManager
 * @author Kiran Kumar Batna
 */
public class DBConnectionListWSManager implements IDBConnectionListWSManager 
{
	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private IWorkspaceFactory workspaceFactory;
	
	@Autowired
    private IUserManager userManger;

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionListWSManager.class);
	
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
	 * This will list all the active workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@Override
	public List<IWorkSpace> listActiveWorkspace(String projectid) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		CallableStatement sqlStatement;
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_ACTIVE_WORKSPACE + "(?,?)";
		
		//establish the connection
	    getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, projectid);
			
			// add output parameter
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(2);
			
			if(errmsg == "")
			{
				ResultSet result =  sqlStatement.getResultSet();
				
				if(result.isBeforeFirst())
				{
					while(result.next())
					{
						workspace = workspaceFactory.createWorkspaceObject();
						workspace.setName(result.getString(1));
						workspace.setDescription(result.getString(2));
						workspace.setId(result.getString(3));
						
						// retrieve the user name details
						wsOwnerName = result.getString(4);
						wsOwner = userManger.getUserDetails(wsOwnerName);
						workspace.setOwner(wsOwner);
						
						//adding the workspace to the list
						workspaceList.add(workspace);
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
			logger.info("List active workspace method  :"+e.getMessage());
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return workspaceList;
	}
	
	/**
	 * This will list all the archive workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@Override
	public List<IWorkSpace> listArchivedWorkspace(String projectid) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		CallableStatement sqlStatement;
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_ARCHIVE_WORKSPACE + "(?,?)";
		
		//establish the connection
	    getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, projectid);
			
			// add output parameter
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(2);
			
			if(errmsg == "")
			{
				ResultSet result =  sqlStatement.getResultSet();
				
				if(result.isBeforeFirst())
				{
					while(result.next())
					{
						workspace = workspaceFactory.createWorkspaceObject();
						workspace.setName(result.getString(1));
						workspace.setDescription(result.getString(2));
						workspace.setId(result.getString(3));
						
						// retrieve the user name details
						wsOwnerName = result.getString(4);
						wsOwner = userManger.getUserDetails(wsOwnerName);
						workspace.setOwner(wsOwner);
						
						//adding the workspace to the list
						workspaceList.add(workspace);
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
			logger.info("List archive workspace method  :"+e.getMessage());
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return workspaceList;
	}

	/**
	 * This will list all the deactivated workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@Override
	public List<IWorkSpace> listDeactivatedWorkspace(String projectid) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		CallableStatement sqlStatement;
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_DEACTIVATED_WORKSPACE + "(?,?)";
		
		//establish the connection
	    getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, projectid);
			
			// add output parameter
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(2);
			
			if(errmsg == "")
			{
				ResultSet result =  sqlStatement.getResultSet();
				
				if(result.isBeforeFirst())
				{
					while(result.next())
					{
						workspace = workspaceFactory.createWorkspaceObject();
						workspace.setName(result.getString(1));
						workspace.setDescription(result.getString(2));
						workspace.setId(result.getString(3));
						
						// retrieve the user name details
						wsOwnerName = result.getString(4);
						wsOwner = userManger.getUserDetails(wsOwnerName);
						workspace.setOwner(wsOwner);
						
						//adding the workspace to the list
						workspaceList.add(workspace);
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
			logger.info("List deactivated workspace method :"+e.getMessage());
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return workspaceList;
	}
	
	/**
	 *This method display the workspace details for the workspace submitted.
	 * @param   workspaceId
	 * @return  IWorkSpace - workspace object
	 * @throws  QuadrigaStorageException
	 * @author  Kiran Kumar Batna
	 */
	@Override
	public IWorkSpace getWorkspaceDetails(String workspaceId) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		CallableStatement sqlStatement;
		
		//creating workspace object
		workspace = workspaceFactory.createWorkspaceObject();
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.WORKSPACE_DETAILS + "(?,?)";
		
		//establish the connection
		  getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, workspaceId);
			
			// add output parameter
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(2);
			
			if(errmsg == "")
			{
				ResultSet result =  sqlStatement.getResultSet();
				
				if(result.isBeforeFirst())
				{
					while(result.next())
					{
						workspace.setName(result.getString(1));
						workspace.setDescription(result.getString(2));
						wsOwnerName = result.getString(3);
						workspace.setId(result.getString(4));
						
						// retrieve the user name details
						wsOwner = userManger.getUserDetails(wsOwnerName);
						workspace.setOwner(wsOwner);
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
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return workspace;
	}
}
