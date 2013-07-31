package edu.asu.spring.quadriga.db.sql;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.IDictionaryItems;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IDictionaryItemsFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.domain.factories.impl.CollaboratorFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;

/**
 * Class implements {@link DBConnectionDictionaryManager} for all the DB connection necessary for dictionary functionality.
 *                    
 * @implements       IDBConnectionDictionaryManager interface.
 *  
 * @Called By        DictionaryManager.java
 *                     
 * @author           Lohith Dwaraka 
 *
 */

public class DBConnectionDictionaryManager implements IDBConnectionDictionaryManager {

	private Connection connection;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private IUserFactory userFactory;
	
	
	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionDictionaryManager.class);

	@Autowired
	private IDictionaryFactory dictionaryFactory;

	@Autowired
	private IDictionaryItemsFactory dictionaryItemsFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;

	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;

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
	 *  Method fetches the list of dictionary for current logged in user                    
	 * 
	 * @returns         List of Dictionary
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */

	@Override
	public List<IDictionary> getDictionaryOfUser(String userId) throws QuadrigaStorageException {
		String dbCommand;
		String errmsg="";
		String errmsg1="";
		getConnection();
		IDictionary dictionary;
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_DICTIONARY_DETAILS + "(?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userId);
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();

			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					dictionary = dictionaryFactory.createDictionaryObject();
					dictionary.setName(resultSet.getString(1));
					dictionary.setDescription(resultSet.getString(2));
					dictionary.setId(resultSet.getString(3));
					dictionaryList.add(dictionary);
				} 
			}
			errmsg = sqlStatement.getString(2);
		} 
		catch (SQLException e) {
			logger.error("",e);
			throw new QuadrigaStorageException();
		}
		catch (Exception e) {
			logger.error("",e);
		}
		finally
		{
			closeConnection();
		}
		return dictionaryList;
	}
	
	@Override
	public String getDictionaryCollabPerm(String userId,String dicitonaryId) throws QuadrigaStorageException {
		String dbCommand;
		String errmsg="";
		String role="";
		getConnection();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_DICTIONARY_COLLAB_PERM + "(?,?,?)";
		try {
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userId);
			sqlStatement.setString(2, dicitonaryId);
			sqlStatement.registerOutParameter(3, java.sql.Types.VARCHAR);

			sqlStatement.execute();

			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					role=(resultSet.getString(1));
				} 
			}
			errmsg = sqlStatement.getString(3);
		}catch (SQLException e) {
			logger.error("StackTrace : ",e);
			throw new QuadrigaStorageException();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		
		return role;
	}
	
	@Override
	public List<IDictionary> getDictionaryCollabOfUser(String userId) throws QuadrigaStorageException {
		String dbCommand;
		String errmsg="";
		getConnection();
		IDictionary dictionary;
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_DICTIONARY_COLLAB + "(?,?)";
		try {
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userId);
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();

			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					dictionary = dictionaryFactory.createDictionaryObject();
					dictionary.setName(resultSet.getString(1));
					dictionary.setDescription(resultSet.getString(2));
					dictionary.setId(resultSet.getString(3));
					dictionaryList.add(dictionary);
				} 
			}
			errmsg = sqlStatement.getString(2);
		}catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return dictionaryList;
	}
	
	
	@Override
	public boolean userDictionaryPerm(String userId, String dictionaryId) throws QuadrigaStorageException{
		String dbCommand;
		String id="";
		String errmsg="";
		getConnection();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_DICTIONARY_PERM + "(?,?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userId);
			sqlStatement.setString(2, dictionaryId);
			sqlStatement.registerOutParameter(3, java.sql.Types.VARCHAR);

			sqlStatement.execute();

			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					id= resultSet.getString(1);
				} 
			}
			if(id.isEmpty())
			{
				return false;
			}
			else
			{
				return true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return false;
		
	}
	/**
	 *  Method deletes selected dictionary                    
	 * 
	 * @returns         return path
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	@Override
	public String deleteDictionary(String userId, String dictionaryId) throws QuadrigaStorageException{
		String dbCommand;
		String errmsg="";
		getConnection();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.DELETE_DICTIONARY + "(?,?,?)";
		logger.debug("deleting from DB dictionary from user "+userId +" and dictionaryId" +dictionaryId);
		try {
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userId);
			sqlStatement.setString(2, dictionaryId);
			sqlStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			sqlStatement.execute();

			errmsg = sqlStatement.getString(3);

			return errmsg;
		}catch(SQLException e){
			e.printStackTrace();
			throw new QuadrigaStorageException();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
	
	/**
	 *  Method return a list of dictionary items for a dictionary                   
	 * 
	 * @returns         list of dictionaryItems
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	
	@Override
	public List<IDictionaryItems> getDictionaryItemsDetails(String dictionaryid, String ownerName)throws QuadrigaStorageException{
		String dbCommand;
		String errmsg="";
		getConnection();
		IDictionaryItems dictionaryItems;
		List<IDictionaryItems> dictionaryList=new ArrayList<IDictionaryItems>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_DICTIONARY_ITEMS_DETAILS + "(?,?,?)";
		try {
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, dictionaryid);
			sqlStatement.setString(2, ownerName);
			sqlStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			logger.debug("Dictionary ID "+ dictionaryid);
			sqlStatement.execute();

			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					dictionaryItems = dictionaryItemsFactory.createDictionaryItemsObject();
					dictionaryItems.setId(resultSet.getString(1));
					dictionaryItems.setItems(resultSet.getString(2));					
					dictionaryItems.setPos(resultSet.getString(3));
					dictionaryList.add(dictionaryItems);
				} 
			}
			errmsg = sqlStatement.getString(3);
			if(errmsg.isEmpty())
			{
				return dictionaryList;
			}
			else
			{
				return null;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return dictionaryList;
	}

	
	@Override
	public List<IDictionaryItems> getDictionaryItemsDetailsCollab(String dictionaryid)throws QuadrigaStorageException{
		String dbCommand;
		String errmsg="";
		getConnection();
		IDictionaryItems dictionaryItems;
		List<IDictionaryItems> dictionaryList=new ArrayList<IDictionaryItems>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_DICTIONARY_ITEMS_DETAILS_COLLAB + "(?,?)";
		try {
			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, dictionaryid);
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);
			logger.debug("Dictionary ID "+ dictionaryid);
			sqlStatement.execute();

			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					dictionaryItems = dictionaryItemsFactory.createDictionaryItemsObject();
					dictionaryItems.setId(resultSet.getString(1));
					dictionaryItems.setItems(resultSet.getString(2));					
					dictionaryItems.setPos(resultSet.getString(3));
					dictionaryList.add(dictionaryItems);
				} 
			}
			errmsg = sqlStatement.getString(2);
			if(errmsg.isEmpty())
			{
				return dictionaryList;
			}
			else
			{
				return null;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return dictionaryList;
	}
	
	
	/**
	 *  Method gets the dictionary name using dictionary id                    
	 * 
	 * @returns         return dictonary name
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	@Override
	public String getDictionaryName(String dictionaryId) throws QuadrigaStorageException
	{
		String dbCommand;
		String dictionaryName="";
		CallableStatement sqlStatement;
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_DICTIONARY_NAME  + "(?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, dictionaryId);
			logger.info("dictionary id:"+dictionaryId);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					dictionaryName =resultSet.getString(1);
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

		return dictionaryName;

	}

	
	@Override
	public String getDictionaryOwner(String dictionaryId) throws QuadrigaStorageException
	{
		String dbCommand;
		String dictionaryOwner="";
		CallableStatement sqlStatement;
		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.GET_DICTIONARY_OWNER  + "(?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, dictionaryId);
			logger.info("dictionary id:"+dictionaryId);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(2,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					dictionaryOwner =resultSet.getString(1);
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

		return dictionaryOwner;

	}
	
	/**
	 *  Method adds an dictionary                   
	 * 
	 * @returns         path of list Dicitonary page
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	@Override
	public String addDictionary(IDictionary dictionary)throws QuadrigaStorageException
	{
		if(dictionary==null){
			return "Dictionary object is null" ;
		}
		String name;
		String description;
		IUser owner = null;
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;
		//fetch the values from the project object
		name = dictionary.getName();
		description = dictionary.getDescription();        
		owner = dictionary.getOwner();


		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_DICTIONARY  + "(?,?,?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, name);
			sqlStatement.setString(2, description);        	
			sqlStatement.setString(3,"0");
			sqlStatement.setString(4,owner.getUserName());

			//adding output variables to the SP
			sqlStatement.registerOutParameter(5,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(5);

			return errmsg;

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
		return errmsg;
	}

	/**
	 *  Method adds items into a dictionary                   
	 * 
	 * @returns         path of list dictionary items  page
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	@Override
	public String addDictionaryItems(String dictinaryId,String item,String id,String pos,String owner) throws QuadrigaStorageException
	{

		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_DICTIONARY_ITEM  + "(?,?,?,?,?,?)";

		//get the connection
		getConnection();
		logger.debug("dbCommand : "+dbCommand);
		//establish the connection with the database
		logger.info(dictinaryId +" , "+item+" , "+id +" , "+pos+" , "+ owner);
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			
			//adding the input variables to the SP
			sqlStatement.setString(1, dictinaryId);
			sqlStatement.setString(2, item);
			sqlStatement.setString(3, id);
			sqlStatement.setString(4, pos);
			sqlStatement.setString(5,owner);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(6,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(6);

			return errmsg;

		}
		catch(SQLException e)
		{
			errmsg="DB related issue";
			e.printStackTrace();
			throw new QuadrigaStorageException();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}

	/**
	 *  Method deletes items from a dictionary                   
	 * 
	 * @returns         path of list dictionary items  page
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	@Override
	public String deleteDictionaryItems(String dictinaryId,String itemid,String ownerName)throws QuadrigaStorageException
	{

		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_DICTIONARY_ITEM  + "(?,?,?,?)";

		//get the connection
		getConnection();
		logger.debug("dbCommand : "+dbCommand);
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, dictinaryId);
			sqlStatement.setString(2, itemid);
			sqlStatement.setString(3, ownerName);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(4);

			return errmsg;

		}
		catch(SQLException e)
		{
			errmsg="DB related issue";
			e.printStackTrace();
			throw new QuadrigaStorageException();
		}catch(Exception e){
			errmsg="Exception outside DB";
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}

	
	@Override
	public String deleteDictionaryItemsCollab(String dictinaryId,String itemid)throws QuadrigaStorageException
	{

		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_DICTIONARY_ITEM_COLLAB  + "(?,?,?)";

		//get the connection
		getConnection();
		logger.debug("dbCommand : "+dbCommand);
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, dictinaryId);
			sqlStatement.setString(2, itemid);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(3);

			return errmsg;

		}
		catch(SQLException e)
		{
			errmsg="DB related issue";
			e.printStackTrace();
			throw new QuadrigaStorageException();
		}catch(Exception e){
			errmsg="Exception outside DB";
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}

	
	/**
	 *  Method update items in a dictionary                   
	 * 
	 * @returns         path of list dictionary items  page
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */
	
	@Override
	public String updateDictionaryItems(String dictinaryId,String termid,String term ,String pos)throws QuadrigaStorageException
	{

		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;



		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.UPDATE_DICTIONARY_ITEM  + "(?,?,?,?,?)";

		//get the connection
		getConnection();
		logger.debug("dbCommand : "+dbCommand);
		//establish the connection with the database
		try
		{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, dictinaryId);
			sqlStatement.setString(2, termid);
			sqlStatement.setString(3, term);
			sqlStatement.setString(4, pos);

			//adding output variables to the SP
			sqlStatement.registerOutParameter(5,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(5);
			return errmsg;

		}
		catch(SQLException e)
		{	
			errmsg="DB related issue";
			e.printStackTrace();
			throw new QuadrigaStorageException();
		}catch(Exception e){
			errmsg="Exception outside DB";
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return errmsg;
	}
	
	
	public List<ICollaboratorRole> splitAndgetCollaboratorRolesList(String role)
	{
        String[] collabroles;
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole = null;
		
		collabroles = role.split(",");
		
		for(int i=0;i<collabroles.length;i++)
		{
			collaboratorRole = collaboratorRoleFactory.createCollaboratorRoleObject();
			collaboratorRole.setRoleDBid(collabroles[i]);
			collaboratorRole.setDisplayName(collaboratorRoleManager.getDictCollaboratorRoleByDBId(collabroles[i]));
			collaboratorRoleList.add(collaboratorRole);
		}
		
		return collaboratorRoleList;
	}

	@Override
	public List<IUser> showNonCollaboratingUsersRequest(String dictionaryid)throws QuadrigaStorageException {

		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		List<IUser> nonCollabUsersList = new ArrayList<IUser>();
		
		dbCommand = DBConstants.SP_CALL+" "+DBConstants.SHOW_DICT_NONCOLLABORATORS+"(?,?)";
		getConnection();
			try {
				sqlStatement = connection.prepareCall("{"+dbCommand+"}");
				sqlStatement.setString(1, dictionaryid);
				sqlStatement.registerOutParameter(2, Types.VARCHAR);
				sqlStatement.execute();
				errmsg = sqlStatement.getString(2);

				if(errmsg=="")
				{
					ResultSet resultSet = sqlStatement.getResultSet();

					while(resultSet.next())
					{
						IUser user = userFactory.createUserObject();
						user.setUserName(resultSet.getString(1));
						nonCollabUsersList.add(user);
					}
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new QuadrigaStorageException();
			}
			
			catch(Exception e)
			{
				e.printStackTrace();
			}
		
		return nonCollabUsersList;
	}

	@Override
	public List<IUser> getDictionaryCollaborators(String dictionaryid) 
	{
		
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		List<IUser> collaboratoratingUsersList = new ArrayList<IUser>();
		
		dbCommand = DBConstants.SP_CALL+" "+DBConstants.SHOW_DICT_COLLABORATORS+"(?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, dictionaryid);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			if(errmsg=="")
			{
				ResultSet resultSet = sqlStatement.getResultSet();
				while(resultSet.next())
				{
					IUser collaboratingUser = userFactory.createUserObject();
					collaboratingUser.setUserName(resultSet.getString(1));
					collaboratoratingUsersList.add(collaboratingUser);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		
		
		return collaboratoratingUsersList;
	}

	@Override
	public String addCollaborators(ICollaborator collaborator, String dictionaryid, String userName, String sessionUser) {

		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		String role ="" ;
		
		dbCommand = DBConstants.SP_CALL+" "+DBConstants.ADD_DICT_COLLABORATORS+"(?,?,?,?,?)";
		
		
		for(ICollaboratorRole collaboratorRole:collaborator.getCollaboratorRoles())
		{
				if(collaboratorRole.getRoleDBid()!=null)
				{
					role += collaboratorRole.getRoleDBid() + ",";
				}
			
		}
		
		role = role.substring(0,role.length()-1);
		
		getConnection();
		
		try {
				sqlStatement = connection.prepareCall("{"+dbCommand+"}");
				sqlStatement.setString(1, dictionaryid);
				sqlStatement.setString(2, userName);
				sqlStatement.setString(3, role);
				sqlStatement.setString(4, sessionUser);
				sqlStatement.registerOutParameter(5, Types.VARCHAR);
				sqlStatement.execute();

				errmsg = sqlStatement.getString(5);
						
				if(errmsg.equals("no errors"))
				{
					return errmsg;
				}	
			}
			
			 catch (SQLException e) {
				
				e.printStackTrace();
			}
		  
		return null;
	}

	@Override
	public List<ICollaborator> showCollaboratingUsersRequest(String dictionaryid)
			throws QuadrigaStorageException {
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		List<ICollaborator> collaborators = new ArrayList<ICollaborator>();
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		//List<IUser> collabList = new ArrayList<IUser>();
		
		dbCommand = DBConstants.SP_CALL+" "+DBConstants.SHOW_DICT_COLLABORATORS+"(?,?)";
		getConnection();
		
		try {
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, dictionaryid);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			
			if(errmsg.equals(""))
			{
				ResultSet resultSet = sqlStatement.getResultSet();
				
				while(resultSet.next())
				{
					ICollaborator collaborator = collaboratorFactory.createCollaborator();
					IUser user = userFactory.createUserObject();
					user.setUserName(resultSet.getString(1));
					collaborator.setUserObj(user);
					
					collaboratorRoles = splitAndgetCollaboratorRolesList(resultSet.getString(2));
					collaborator.setCollaboratorRoles(collaboratorRoles);
					
					collaborators.add(collaborator);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return collaborators;
	}

	@Override
	public String deleteCollaborators(String dictionaryid, String userName) {

		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		dbCommand = DBConstants.SP_CALL+" "+DBConstants.DELETE_DICT_COLLABORATORS+"(?,?,?)";
		getConnection();
		
		try {
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, dictionaryid);
			sqlStatement.setString(2, userName);
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(3);
			
			if(errmsg.equals(""))
			{
				return errmsg;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	
	
	
}
