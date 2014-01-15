package edu.asu.spring.quadriga.db.sql;

import java.nio.ByteBuffer;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.IDBConnectionEditorManager;
import edu.asu.spring.quadriga.db.IDBConnectionNetworkManager;
import edu.asu.spring.quadriga.domain.INetwork;
import edu.asu.spring.quadriga.domain.INetworkOldVersion;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.impl.NetworkFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.INetworkManager;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.workbench.IRetrieveProjectManager;
import edu.asu.spring.quadriga.service.workspace.IListWSManager;


/**
 * Class implements {@link DBConnectionNetworkManager} for all the DB connection necessary for networks functionality.
 *                    
 *  
 * @Called By        NetworkManager.java
 *                     
 * @author           Lohith Dwaraka 
 *
 */
public class DBConnectionEditorManager extends ADBConnectionManager implements IDBConnectionEditorManager {

	@Autowired
	INetworkManager networkManager;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	IListWSManager wsManager;
	
	@Autowired
	IRetrieveProjectManager retrieveProjectDetails;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionNetworkManager.class);
	
	@Autowired
	private IDBConnectionNetworkManager dbConnectNetwork;
	
	@Autowired
	NetworkFactory networkFactory;
	
	
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
					
					//Deprecated. Should be replaced by set project and workspace
//					network.setProjectid(networkManager.getProjectIdForWorkspaceId(network.getWorkspaceid()));
//					IProject project =retrieveProjectDetails.getProjectDetails(network.getProjectid());
//					network.setProjectName(project.getName());
//					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
//					network.setWorkspaceName(workspaceName);
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
	
	
	@Override
	public List<INetwork> getAssignedNetworkListOfOtherEditors(IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		
		CallableStatement sqlStatement;
		List<INetwork> networkList = new ArrayList<INetwork>();
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_ASSIGNED_NETWORK_OTHER_EDITORS  + "(?,?)";
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
					network.setAssignedUser(resultSet.getString(6));
					
					//Deprecated. Should be replaced by set project and workspace
//					network.setProjectid(networkManager.getProjectIdForWorkspaceId(network.getWorkspaceid()));
//					IProject project =retrieveProjectDetails.getProjectDetails(network.getProjectid());
//					network.setProjectName(project.getName());
//					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
//					network.setWorkspaceName(workspaceName);
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
	
	
	@Override
	public List<INetwork> getfinishedNetworkListOfOtherEditors(IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		
		CallableStatement sqlStatement;
		List<INetwork> networkList = new ArrayList<INetwork>();
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_FINISHED_NETWORK_OTHER_EDITORS  + "(?,?)";
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
					
					//Deprecated. Should be replaced by set project and workspace
//					network.setProjectid(networkManager.getProjectIdForWorkspaceId(network.getWorkspaceid()));
//					IProject project =retrieveProjectDetails.getProjectDetails(network.getProjectid());
//					network.setProjectName(project.getName());
//					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
//					network.setWorkspaceName(workspaceName);
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
	
	@Override
	public String assignNetworkToUser(String networkId, IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ASSIGN_USER_NETWORK  + "(?,?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, networkId);
			sqlStatement.setString(2, owner.getUserName());        	

			//adding output variables to the SP
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(3);
			if(errmsg.isEmpty()){
				return "";
			}else{
				logger.info(" error msg : " + errmsg);
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
		return errmsg;		
	}

	
	

	@Override
	@Transactional
	public List<INetwork> getAssignNetworkOfUser(IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		
		CallableStatement sqlStatement;
		List<INetwork> networkList = new ArrayList<INetwork>();
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_USER_ASSIGN_NETWORK  + "(?,?)";
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
					
//					INetworkOldVersion networkOldVersion = dbConnectNetwork.getNetworkOldVersion(network.getId());
//					network.setNetworkOldVersion(networkOldVersion);
					
					//Deprecated. Should be replaced by set project and workspace
//					network.setProjectid(networkManager.getProjectIdForWorkspaceId(network.getWorkspaceid()));					
//					IProject project =retrieveProjectDetails.getProjectDetails(network.getProjectid());
//					network.setProjectName(project.getName());					
//					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
//					network.setWorkspaceName(workspaceName);
					
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
	
	@Override
	public List<INetwork> getApprovedNetworkOfUser(IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		
		CallableStatement sqlStatement;
		List<INetwork> networkList = new ArrayList<INetwork>();
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_USER_APPROVED_NETWORK  + "(?,?)";
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
					
					//Deprecated. Should be replaced by set project and workspace
//					network.setProjectid(networkManager.getProjectIdForWorkspaceId(network.getWorkspaceid()));
//					IProject project =retrieveProjectDetails.getProjectDetails(network.getProjectid());
//					network.setProjectName(project.getName());
//					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
//					network.setWorkspaceName(workspaceName);
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
	
	@Override
	public List<INetwork> getRejectedNetworkOfUser(IUser user) throws QuadrigaStorageException{
		IUser owner = user;
		String dbCommand;
		String errmsg="";
		
		CallableStatement sqlStatement;
		List<INetwork> networkList = new ArrayList<INetwork>();
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_USER_REJECTED_NETWORK  + "(?,?)";
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
					
					//Deprecated. Should be replaced by set project and workspace
//					network.setProjectid(networkManager.getProjectIdForWorkspaceId(network.getWorkspaceid()));
//					IProject project =retrieveProjectDetails.getProjectDetails(network.getProjectid());
//					network.setProjectName(project.getName());
//					String workspaceName=wsManager.getWorkspaceName(network.getWorkspaceid());
//					network.setWorkspaceName(workspaceName);
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
	 * Method update items in a dictionary
	 * 
	 * @returns path of list dictionary items page
	 * 
	 * @throws SQLException
	 * 
	 * @author Lohith Dwaraka
	 * 
	 */

	@Override
	public String updateNetworkStatus(String networkId, String status) throws QuadrigaStorageException {

		String dbCommand;
		String errmsg = "";
		CallableStatement sqlStatement;

		// command to call the SP
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.UPDATE_NETWORK_STATUS + "(?,?,?)";

		// get the connection
		getConnection();
		logger.debug("dbCommand : " + dbCommand);
		// establish the connection with the database
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");

			// adding the input variables to the SP
			sqlStatement.setString(1, networkId);
			sqlStatement.setString(2, status);

			// adding output variables to the SP
			sqlStatement.registerOutParameter(3, Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(3);
			return errmsg;

		} catch (SQLException e) {
			errmsg = "DB related issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			errmsg = "Exception outside DB";
			logger.error(errmsg,e);
		} finally {
			closeConnection();
		}
		return errmsg;
	}
	
	
	/**
	 * Method update assigned in a network
	 * 
	 * @returns path of list dictionary items page
	 * 
	 * @throws SQLException
	 * 
	 * @author Lohith Dwaraka
	 * 
	 */

	@Override
	public String updateAssignedNetworkStatus(String networkId, String status) throws QuadrigaStorageException {

		String dbCommand;
		String errmsg = "";
		CallableStatement sqlStatement;

		// command to call the SP
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.UPDATE_ASSIGNED_NETWORK_STATUS + "(?,?,?)";

		// get the connection
		getConnection();
		logger.debug("dbCommand : " + dbCommand);
		// establish the connection with the database
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");

			// adding the input variables to the SP
			sqlStatement.setString(1, networkId);
			sqlStatement.setString(2, status);

			// adding output variables to the SP
			sqlStatement.registerOutParameter(3, Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(3);
			return errmsg;

		} catch (SQLException e) {
			errmsg = "DB related issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			errmsg = "Exception outside DB";
			logger.error(errmsg,e);
		} finally {
			closeConnection();
		}
		return errmsg;
	}
	
	
	
	/**
	 * Method add Annotation to a network
	 * 
	 * @returns success/fail
	 * 
	 * @throws SQLException
	 * 
	 * @author Lohith Dwaraka
	 * 
	 */

	@Override
	public String addAnnotationToNetwork(String networkId, String id, String annotationText, String userId,String objectType) throws QuadrigaStorageException {

		String dbCommand;
		String errmsg = "";
		
		CallableStatement sqlStatement;

		// command to call the SP
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.ADD_ANNOTATIONS_TO_NETWORKS + "(?,?,?,?,?,?)";

		// get the connection
		getConnection();
		logger.debug("dbCommand : " + dbCommand);
		// establish the connection with the database
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");

			// adding the input variables to the SP
			sqlStatement.setString(1, networkId);
			sqlStatement.setString(2, id);
			sqlStatement.setString(3, annotationText);
			sqlStatement.setString(4, userId);
			sqlStatement.setString(5, objectType);

			// adding output variables to the SP
			sqlStatement.registerOutParameter(6, Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(6);
			if(errmsg.isEmpty()){
				return errmsg;
			}else{
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

			

		} catch (SQLException e) {
			errmsg = "DB related issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			errmsg = "Exception outside DB";
			logger.error(errmsg,e);
		} finally {
			closeConnection();
		}
		return errmsg;
	}
	
	// Changed this won't work as expected the signature of this method changed as per req
	public String[] getAnnotation(String type, String id,String userId,String networkId) throws QuadrigaStorageException{
		
		String dbCommand;
		String errmsg="";
		String annotationText = "";
		String annotationId = "";
		String[] arr = new String[2];
		CallableStatement sqlStatement;
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_ANNOTATIONS  + "(?,?,?,?)";
		//get the connection
		getConnection();
		//establish the connection with the database
		
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, userId);
			sqlStatement.setString(2, id); 
			sqlStatement.setString(3, type);  
			
			//adding output variables to the SP
			sqlStatement.registerOutParameter(4,Types.VARCHAR);
			
			sqlStatement.execute();
			
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) {
					
					annotationText = resultSet.getString(1);
					annotationId = resultSet.getString(2);
					arr[0] = annotationText;
					arr[1] = annotationId;
				} 
			}
			errmsg = sqlStatement.getString(4);
			if(errmsg.isEmpty()){
				return arr;
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
		return arr;		
		
		
		
	}

	/**
	 * Method to update Annotation to a network
	 * 
	 * @returns success/fail
	 * 
	 * @throws SQLException
	 * 
	 * @author Sowjanya Ambati
	 * 
	 */
	// Changed this won't work as expected the signature of this method changed as per req
	@Override
	public String updateAnnotationToNetwork(String annotationId,String annotationText ) throws QuadrigaStorageException {

		String dbCommand;
		String errmsg = "";
		
		CallableStatement sqlStatement;

		// command to call the SP
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.UPDATE_ANNOTATIONS_TO_NETWORKS + "(?,?,?)";

		// get the connection
		getConnection();
		logger.debug("dbCommand : " + dbCommand);
		// establish the connection with the database
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");

			// adding the input variables to the SP
			sqlStatement.setString(1, annotationText);
			sqlStatement.setString(2, annotationId);
		
			// adding output variables to the SP
			sqlStatement.registerOutParameter(3, Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(3);
			if(errmsg.isEmpty()){
				return errmsg;
			}else{
				throw new QuadrigaStorageException("Something went wrong on DB side");
			}

			

		} catch (SQLException e) {
			errmsg = "DB related issue";
			logger.error(errmsg,e);
			throw new QuadrigaStorageException();
		} catch (Exception e) {
			errmsg = "Exception outside DB";
			logger.error(errmsg,e);
		} finally {
			closeConnection();
		}
		return errmsg;
	}
	
}
