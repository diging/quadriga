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

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.IWordPower;
import edu.asu.spring.quadriga.domain.IWorkSpace;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.IUserManager;
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
public class DBConnectionEditorManager implements IDBConnectionEditorManager {
	private Connection connection;

	@Autowired
	private DataSource dataSource;

	@Autowired
	INetworkManager networkManager;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	ListWSManager wsManager;
	
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
			se.printStackTrace();
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
			e.printStackTrace();
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
			ex.printStackTrace();
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
	public List<INetwork> getEditorNetworkList(IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		
		CallableStatement sqlStatement;
		List<INetwork> networkList = new ArrayList<INetwork>();
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_EDITOR_NETWORK_LIST  + "(?,?)";
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
					network.setCreator(userManager.getUserDetails(resultSet.getString(4)));
					network.setStatus(resultSet.getString(5));
					
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

	
}
