package edu.asu.spring.quadriga.db.sql;

import java.nio.ByteBuffer;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkNodeInfo;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.INetworkNodeInfoFactory;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.impl.workspace.ListWSManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;


/**
 * Class implements {@link DBConnectionNetworkManager} for all the DB connection necessary for networks functionality.
 *                    
 *  
 * @Called By        NetworkManager.java
 *                     
 * @author           Lohith Dwaraka 
 *
 */
public class DBConnectionNetworkManager implements IDBConnectionNetworkManager {
	private Connection connection;

	@Autowired
	private DataSource dataSource;

	@Autowired
	INetworkManager networkManager;
	
	@Autowired
	ListWSManager wsManager;
	
	@Autowired
	INetworkNodeInfoFactory networkNodeInfoFactory;
	
	@Autowired
	IRetrieveProjectManager retrieveProjectDetails;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionNetworkManager.class);
	
	@Autowired
	NetworkFactory networkFactory;
	
	/**
	 * Assigns the data source
	 *  
	 *  @param : dataSource
	 */
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}

	/**
	 * Close the DB connection
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
			logger.error("DB Issue",se);
		}
		return 1;
	}

	/**
	 * Establishes connection with the Quadriga DB
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
			logger.error("DB Issue",e);
		}
	}

	/**
	 * Sets up the environment
	 * 
	 * @return      : int
	 * 
	 * @throws      : SQLException 
	 */
	public int setupTestEnvironment(String sQuery)
	{
		try
		{
			getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sQuery);
			return 1;
		}
		catch(SQLException ex)
		{
			logger.error("DB Issue",ex);
		}finally{
			closeConnection();
		}
		return 1;
	}
	/**
	 * Generate short UUID (13 characters)
	 * 
	 * @return short UUID
	 */
	public String shortUUID() {
		UUID uuid = UUID.randomUUID();
		long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();
		return Long.toString(l, Character.MAX_RADIX);
	}

	@Override
	public String addNetworkRequest(String networkName, IUser user, String workspaceid) throws QuadrigaStorageException{
		String networkId="NET_"+shortUUID();
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_NETWORK_DETAILS  + "(?,?,?,?,?,?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, networkId);
			sqlStatement.setString(2, workspaceid);
			sqlStatement.setString(3, networkName);
			sqlStatement.setString(4, owner.getUserName());        	
			sqlStatement.setString(5,"0");
			sqlStatement.setString(6,"PENDING");

			//adding output variables to the SP
			sqlStatement.registerOutParameter(7,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(7);
			if(errmsg.isEmpty()){
				return networkId;
			}else{
				
				throw new QuadrigaStorageException(errmsg + "Something went wrong on DB side");
			}

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		return networkId;		
	}

	@Override
	public String addNetworkStatement(String networkId,String id,String type,String isTop, IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_NETWORK_STATEMENT  + "(?,?,?,?,?,?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, networkId);
			sqlStatement.setString(2, id);
			sqlStatement.setString(3, type);        	
			sqlStatement.setString(4, isTop);
			sqlStatement.setString(5, "0");
			sqlStatement.setString(6,owner.getUserName());

			//adding output variables to the SP
			sqlStatement.registerOutParameter(7,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(7);
			return errmsg;

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		return errmsg;		
	}
	

	@Override
	public INetwork getNetworkStatus(String networkId, IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		INetwork network=networkFactory.createNetworkObject();;
		CallableStatement sqlStatement;
		
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_NETWORK_STATUS  + "(?,?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, owner.getUserName());
			sqlStatement.setString(2, networkId);        	

			//adding output variables to the SP
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					
					network.setId(resultSet.getString(1));
					network.setName(resultSet.getString(2));
					network.setStatus(resultSet.getString(3));
				} 
			}
			errmsg = sqlStatement.getString(3);
			if(errmsg.isEmpty()){
				return network;
			}else{
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		return network;		
	}

	
	@Override
	public List<INetwork> getNetworkList(IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		
		CallableStatement sqlStatement;
		List<INetwork> networkList = new ArrayList<INetwork>();
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_NETWORK_LIST  + "(?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, owner.getUserName());        	

			//adding output variables to the SP
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) {
					INetwork network=networkFactory.createNetworkObject();;
					network.setId(resultSet.getString(1));
					network.setWorkspaceid(resultSet.getString(2));
					network.setName(resultSet.getString(3));
					network.setStatus(resultSet.getString(4));
					
					network.setProjectid(networkManager.getProjectIdForWorkspaceId(network.getWorkspaceid()));
					IProject project =retrieveProjectDetails.getProjectDetails(network.getProjectid());
					network.setProjectName(project.getName());
					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
					network.setWorkspaceName(workspaceName);
					networkList.add(network);
				} 
			}
			errmsg = sqlStatement.getString(2);
			if(errmsg.isEmpty()){
				return networkList;
			}else{
				logger.error("Error Msg : "+errmsg);
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		return networkList;		
	}

	@Override
	public String getProjectIdForWorkspaceId(String workspaceid) throws QuadrigaStorageException{
		String dbCommand;
		String errmsg="";
		String projectid="";
		CallableStatement sqlStatement;
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_PROJECTID_WORKSPACE  + "(?,?)";
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
					projectid = resultSet.getString(1);
				} 
			}
			errmsg = sqlStatement.getString(2);
			if(errmsg.isEmpty()){
				return projectid;
			}else{
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		return projectid;		
	}
	
	@Override
	public boolean hasNetworkName(String networkName,IUser user) throws QuadrigaStorageException{
		String dbCommand;
		String errmsg="";
		String result="";
		CallableStatement sqlStatement;
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.HAS_NETWORK_NAME  + "(?,?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, user.getUserName());
			sqlStatement.setString(2, networkName); 

			//adding output variables to the SP
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) {
					result = resultSet.getString(1);
				} 
			}
			errmsg = sqlStatement.getString(3);
			if(errmsg.isEmpty()){
				if(result.isEmpty()){
					return false;
				}else if(result.equals("1")){
					return true;
				}
			}else{
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		return true;		
	}
	
	@Override
	public List<INetworkNodeInfo> getNetworkTopNodes(String networkId)throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkTopNodeList = new ArrayList<INetworkNodeInfo>();
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_NETWORK_TOP_NODES_LIST  + "(?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, networkId);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) {
					INetworkNodeInfo networkNodeInfo = networkNodeInfoFactory.createNetworkNodeInfoObject();
					networkNodeInfo.setId(resultSet.getString(1));
					networkNodeInfo.setStatementType(resultSet.getString(2));
					networkTopNodeList.add(networkNodeInfo);
				} 
			}
			errmsg = sqlStatement.getString(2);
			if(errmsg.isEmpty()){
				return networkTopNodeList;
			}else{
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		
		return networkTopNodeList;
	}
	
	@Override
	public List<INetworkNodeInfo> getAllNetworkNodes(String networkId)throws QuadrigaStorageException{
		List<INetworkNodeInfo> networkNodeList = new ArrayList<INetworkNodeInfo>();
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_ALL_NETWORK_NODES_LIST  + "(?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, networkId);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) {
					INetworkNodeInfo networkNodeInfo = networkNodeInfoFactory.createNetworkNodeInfoObject();
					networkNodeInfo.setId(resultSet.getString(1));
					networkNodeInfo.setStatementType(resultSet.getString(2));
					networkNodeList.add(networkNodeInfo);
				} 
			}
			errmsg = sqlStatement.getString(2);
			if(errmsg.isEmpty()){
				return networkNodeList;
			}else{
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		
		return networkNodeList;
	}
	
	@Override
	public String archiveNetworkStatement(String networkId,String id) throws QuadrigaStorageException{
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ARCHIVE_NETWORK_STATEMENT  + "(?,?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, networkId);
			sqlStatement.setString(2, id);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(3);
			return errmsg;

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		return errmsg;		
	}
	
	
	@Override
	public String archiveNetwork(String networkId) throws QuadrigaStorageException{
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ARCHIVE_NETWORK  + "(?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, networkId);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(2);
			return errmsg;

		}
		catch(SQLException e)
		{
			errmsg="DB Issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();

		}catch(Exception e){
			errmsg="DB Issue";
			logger.error(errmsg,e);
		}
		finally
		{
			closeConnection();
		}
		return errmsg;		
	}
}
