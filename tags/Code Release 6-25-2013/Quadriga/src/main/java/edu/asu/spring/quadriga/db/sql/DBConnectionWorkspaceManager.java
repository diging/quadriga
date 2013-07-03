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
import edu.asu.spring.quadriga.db.IDBConnectionWorkspaceManager;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

public class DBConnectionWorkspaceManager implements
		IDBConnectionWorkspaceManager 
{
	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	@Autowired
    private IUserManager userManger;
	
	@Autowired
	private IWorkspaceFactory workspaceFactory;
	
	/**
	 *  @Description: Assigns the data source
	 *  @param : dataSource
	 *  @author Kiran Kumar Batna
	 */
	@Override
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}
	
	/**
	 * @Description : Close the DB connection
	 * @return : 0 on success
	 *           -1 on failure
	 * @throws : SQL Exception
	 * @author Kiran Kumar Batna       
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
	 * @return      : connection handle for the created connection
	 * @throws      : QuadrigaStorageException
	 * @author      : Kiran Kumar Batna
	 */
	private void getConnection() throws QuadrigaStorageException {
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			
			throw new QuadrigaStorageException(e.getMessage());
		}
	}
	
	/**
	 * @description   : This will list all the workspaces associated
	 *                  with the project.
	 * @param         : projectid
	 * @return        : List<IWorkSpace> - list of workspaces associated 
	 *                  with the project.
	 * @throws        : QuadrigaStorageException
	 * @author        : Kiran Kumar Batna
	 */
	@Override
	public List<IWorkSpace> listWorkspace(int projectid,int archive,int active) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		CallableStatement sqlStatement;
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_WORKSPACE + "(?,?,?,?)";
		
		//establish the connection
		try
		{
		  getConnection();
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setInt(1, projectid);
			sqlStatement.setInt(2, archive);
			sqlStatement.setInt(3, active);
			
			// add output parameter
			sqlStatement.registerOutParameter(4, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(4);
			
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
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return workspaceList;
	}
	
	/**
	 * @description   : This adds a workspace record into the database.
	 * @param         : workSpace
	 * @return        : errmsg - blank when success and error message on failure.
	 * @throws        : QuadrigaStorageException
	 */
	@Override
	public String addWorkSpaceRequest(IWorkSpace workSpace,int projectId) throws QuadrigaStorageException
	{
		String errmsg;
		String wsName;
		String wsDescription;
		String wsOwnerName;
		String dbCommand;
		IUser  wsOwner = null;
		CallableStatement sqlStatement;
		
		// fetch the values from the WorkSpace object
		wsName = workSpace.getName();
		wsDescription = workSpace.getDescription();
		wsOwner = workSpace.getOwner();
		wsOwnerName = wsOwner.getUserName();
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_WORKSPACE_REQUEST + "(?,?,?,?,?)";
		
		//establish the connection
		try
		{
		  getConnection();
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, wsName);
			sqlStatement.setString(2, wsDescription);
			sqlStatement.setString(3, wsOwnerName);
			sqlStatement.setInt(4, projectId);
			
			// add output parameter
			sqlStatement.registerOutParameter(5, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(5);
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		
		return errmsg;
	}
	
	/**
	 * @description   : This will archive the requested workspace.
	 * @param         : workspaceIdList - Comma separated workspace Id's.
	 * @param         : archive
	 * @param         : wsUser
	 * @return        : String - errmsg containing blank on success and error message on failure.
	 * @throws        : QuadrigaStorageException
	 * @author        : Kiran Kumar Batna
	 */
	@Override
	public String archiveWorkspaceRequest(String workspaceIdList,int archive,String wsUser) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		CallableStatement sqlStatement;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ARCHIVE_WORKSPACE_REQUEST + "(?,?,?,?)";
		
		//establish the connection
		try
		{
		  getConnection();
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, workspaceIdList);
			sqlStatement.setInt(2, archive);
			sqlStatement.setString(3,wsUser);
			
			// add output parameter
			sqlStatement.registerOutParameter(4, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(4);
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		
		return errmsg;
	}
	
	/**
	 * @description   : This will deactivate the requested workspace.
	 * @param         : workspaceIdList - Comma separated workspace Id's.
	 * @param         : deactivate
	 * @param         : wsUser
	 * @return        : String - errmsg containing blank on success and error message on failure.
	 * @throws        : QuadrigaStorageException
	 * @author        : Kiran Kumar Batna
	 */
	@Override
	public String deactivateWorkspace(String workspaceIdList,int deactivate,String wsUser) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		CallableStatement sqlStatement;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DEACTIVATE_WORKSPACE_REQUEST + "(?,?,?,?)";
		
		//establish the connection
		try
		{
		  getConnection();
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, workspaceIdList);
			sqlStatement.setInt(2, deactivate);
			sqlStatement.setString(3,wsUser);
			
			// add output parameter
			sqlStatement.registerOutParameter(4, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(4);
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
	
	/**
	 * @description   : This will delete the requested workspace.
	 * @param         : workspaceIdList - Comma separated workspace Id's.
	 * @return        : String - errmsg containing blank on success and error message on failure.
	 * @throws        : QuadrigaStorageException
	 * @author        : Kiran Kumar Batna
	 */
	@Override
	public String deleteWorkspaceRequest(String workspaceIdList) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		CallableStatement sqlStatement;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_WORKSPACE_REQUEST + "(?,?)";
		
		//establish the connection
		try
		{
		  getConnection();
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, workspaceIdList);
			
			// add output parameter
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(2);
		}
		catch(SQLException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
	
	/**
	 * @description  : This method display the workspace details for the 
	 *                 workspace submitted.
	 * @param        : workspaceId
	 * @return       : IWorkSpace - workspace object
	 * @throws       : QuadrigaStorageException
	 * @author       : Kiran Kumar Batna
	 */
	@Override
	public IWorkSpace getWorkspaceDetails(long workspaceId) throws QuadrigaStorageException
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
		try
		{
		  getConnection();
		}
		catch(QuadrigaStorageException e)
		{
			throw new QuadrigaStorageException(e.getMessage());
		}
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setLong(1,workspaceId);
			
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
