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
import edu.asu.spring.quadriga.db.workspace.IDBConnectionListWSManager;
import edu.asu.spring.quadriga.domain.IBitStream;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.IBitStreamFactory;
import edu.asu.spring.quadriga.domain.factories.IWorkspaceFactory;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * Class implements {@link IDBConnectionListWSManager} for all the DB connection 
 * necessary for listing workspace associated with project.
 * @implements IDBConnectionListWSManager
 * @author Kiran Kumar Batna
 */
public class DBConnectionListWSManager extends ADBConnectionManager implements IDBConnectionListWSManager 
{
	@Autowired
	private IWorkspaceFactory workspaceFactory;
	
	@Autowired
	private NetworkFactory networkFactory;
	
	@Autowired
    private IUserManager userManger;

	@Autowired
	private IBitStreamFactory bitstreamFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionListWSManager.class);
	
	/**
	 * This will list all the active workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	@Override
	public List<IWorkSpace> listWorkspace(String projectid,String user) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		CallableStatement sqlStatement;
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_WORKSPACE + "(?,?,?)";
		
		//establish the connection
	    getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, projectid);
			sqlStatement.setString(2, user);
			
			// add output parameter
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(3);
			
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
			logger.info("List workspace method  :"+e.getMessage());
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return workspaceList;
	}	
	
	
	@Override
	public List<IWorkSpace> listWorkspaceOfCollaborator(String projectid,String user) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		CallableStatement sqlStatement;
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_WORKSPACE_OF_COLLABORATOR + "(?,?,?)";
		
		//establish the connection
	    getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, projectid);
			sqlStatement.setString(2, user);
			
			// add output parameter
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(3);
			
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
			logger.info("List workspace method  :"+e.getMessage());
			throw new QuadrigaStorageException(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return workspaceList;
	}	
	
	/**
	 * This will list all the active workspaces associated with the project.
	 * @param    projectid
	 * @return   List<IWorkSpace> - list of workspaces associated 
	 *           with the project.
	 * @throws   QuadrigaStorageException
	 * @author   Kiran Kumar Batna
	 */
	public List<IWorkSpace> listActiveWorkspace(String projectid,String user,String spName) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		CallableStatement sqlStatement;
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + spName + "(?,?,?)";
		
		//establish the connection
	    getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, projectid);
			sqlStatement.setString(2, user);
			
			// add output parameter
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(3);
			
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
	public List<IWorkSpace> listArchivedWorkspace(String projectid,String user) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		CallableStatement sqlStatement;
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_ARCHIVE_WORKSPACE + "(?,?,?)";
		
		//establish the connection
	    getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, projectid);
			sqlStatement.setString(2, user);
			
			// add output parameter
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(3);
			
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
	public List<IWorkSpace> listDeactivatedWorkspace(String projectid,String user) throws QuadrigaStorageException
	{
		String errmsg;
		String dbCommand;
		String wsOwnerName;
		CallableStatement sqlStatement;
		List<IWorkSpace> workspaceList = new ArrayList<IWorkSpace>();
		IWorkSpace workspace = null;
		IUser wsOwner = null;
		
		//command call to the Stored Procedure
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_DEACTIVATED_WORKSPACE + "(?,?,?)";
		
		//establish the connection
	    getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, projectid);
			sqlStatement.setString(2, user);
			
			// add output parameter
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(3);
			
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
	public IWorkSpace getWorkspaceDetails(String workspaceId,String user) throws QuadrigaStorageException
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
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.WORKSPACE_DETAILS + "(?,?,?)";
		
		//establish the connection
		  getConnection();
		try
		{
			// prepare the SQL Statement for execution
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			// add input parameters
			sqlStatement.setString(1, workspaceId);
			sqlStatement.setString(2, user);
			
			// add output parameter
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			
			// execute the sql statement
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(3);
			
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
	
	@Override
	public List<IBitStream> getBitStreams(String workspaceId, String username) throws QuadrigaAccessException, QuadrigaStorageException
	{
		String sDBCommand;
		String sOutErrorValue;

		getConnection();

		sDBCommand = DBConstants.SP_CALL + " " + DBConstants.LIST_WORKSPACE_BITSTREAM + "(?,?,?)";

		try
		{
			CallableStatement sqlStatement = connection.prepareCall("{"+sDBCommand+"}");			

			sqlStatement.setString(1, workspaceId);
			sqlStatement.setString(2, username);
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			//Execute the stored procedure
			sqlStatement.execute();

			sOutErrorValue = sqlStatement.getString(3);

			if(sOutErrorValue == null)
			{
				//Successfully retrieved the bitstreams associated with the workspace
				List<IBitStream> bitstreamList = new ArrayList<IBitStream>();
				ResultSet result = sqlStatement.getResultSet();
				
				IBitStream bitstream = null;
				while(result.next())
				{
					bitstream = bitstreamFactory.createBitStreamObject();
					bitstream.setCommunityid(result.getString(1));
					bitstream.setCollectionid(result.getString(2));
					bitstream.setItemid(result.getString(3));					
					bitstream.setId(result.getString(4));
					bitstreamList.add(bitstream);
				}
				
				return bitstreamList;
			}
			else
			{
				logger.info("The user "+username+" tried to hack into the bitstream list with the following values:");
				logger.info("Class Name: DBConnectionDspaceManager");
				logger.info("Method Name: getBitStreams");
				logger.info("Workspace id: "+workspaceId);
				throw new QuadrigaAccessException(sOutErrorValue);
			}
		}
		catch(SQLException e)
		{
			logger.info("The user "+username+" tried to hack into the bitstream list with the following values:");
			logger.info("Class Name: DBConnectionDspaceManager");
			logger.info("Method Name: getBitStreams");
			logger.info("Workspace id: "+workspaceId);
			throw new QuadrigaStorageException(e);
		}
		finally
		{
			closeConnection();
		}			
	}
	
	/**
	 * Gets a list of networks belonging to the workspace
	 * @param workspaceid
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public List<INetwork> getWorkspaceNetworkList(String workspaceid) throws QuadrigaStorageException{
		String dbCommand;
		String errmsg="";
		
		CallableStatement sqlStatement;
		List<INetwork> networkList = new ArrayList<INetwork>();
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_WORKSPACE_NETWORKS  + "(?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, workspaceid);        	

			//adding output variables to the SP
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) {
					INetwork network=networkFactory.createNetworkObject();;
					network.setId(resultSet.getString(1));
					network.setName(resultSet.getString(2));
					network.setCreator(userManger.getUserDetails(resultSet.getString(3)));
					network.setStatus(resultSet.getString(4));
					networkList.add(network);
				} 
			}
			errmsg = sqlStatement.getString(2);
			if(errmsg.isEmpty()){
				return networkList;
			}else{
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			e.printStackTrace();
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return networkList;		
	}
	
	
	/**
	 * Gets a list of rejected networks belonging to the workspace
	 * @param workspaceid
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public List<INetwork> getWorkspaceRejectedNetworkList(String workspaceid) throws QuadrigaStorageException{
		String dbCommand;
		String errmsg="";
		
		CallableStatement sqlStatement;
		List<INetwork> networkList = new ArrayList<INetwork>();
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_WORKSPACE_REJECTED_NETWORKS  + "(?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, workspaceid);        	

			//adding output variables to the SP
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) {
					INetwork network=networkFactory.createNetworkObject();;
					network.setId(resultSet.getString(1));
					network.setName(resultSet.getString(2));
					network.setCreator(userManger.getUserDetails(resultSet.getString(3)));
					network.setStatus(resultSet.getString(4));
					networkList.add(network);
				} 
			}
			errmsg = sqlStatement.getString(2);
			if(errmsg.isEmpty()){
				return networkList;
			}else{
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			e.printStackTrace();
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return networkList;		
	}
	/**
	 *  Method gets the Workspace name using workspace id                    
	 * 
	 * @returns         return dictonary name
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	@Override
	public String getWorkspaceName(String workspaceId) throws QuadrigaStorageException
	{
		String dbCommand;
		String workspaceName="";
		CallableStatement sqlStatement;
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_WORKSPACE_NAME  + "(?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, workspaceId);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					workspaceName =resultSet.getString(1);
				} 
			}
			//String errmsg = sqlStatement.getString(2);

		}
		catch(SQLException e)
		{
			e.printStackTrace();
			throw new QuadrigaStorageException();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}

		return workspaceName;

	}


	@Override
	public List<IWorkSpace> listActiveWorkspaceOfOwner(String projectid,
			String username) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<IWorkSpace> listActiveWorkspaceOfCollaborator(String projectid,
			String username) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<IWorkSpace> listActiveWorkspace(String projectid,
			String username) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}
}
