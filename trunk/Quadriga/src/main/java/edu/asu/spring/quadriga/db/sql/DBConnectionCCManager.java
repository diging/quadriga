/**
 * 
 */
package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class executes all the stored procedures related to the concept collections
 * @author satyaswaroop boddu
 */
public class DBConnectionCCManager extends ADBConnectionManager implements
		IDBConnectionCCManager {

	@Autowired
	private IUserFactory userFactory;

	private static final Logger logger = LoggerFactory
			.getLogger(DBConnectionCCManager.class);


	private IConceptCollection conceptCollection;

	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;

	private IConcept concept;
	@Autowired
	private IConceptFactory conceptFactory;
	
	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	@Autowired
	private DBConnectionManager dbConnectionManager;
	
	@Autowired
	private IDBConnectionRetrieveProjCollabManager dbConnectionProjectManager; 
	
	@Autowired
	private ICollaboratorRoleFactory collaboratorRoleFactory;
	
	@Autowired
	private ICollaboratorFactory collaboratorFactory;
	

	/**
	 * {@inheritDoc}
	 * 
	 * @throws QuadrigaStorageException
	 */
	@Override
	public List<IConceptCollection> getConceptsOwnedbyUser(String userId)
			throws QuadrigaStorageException {
		String dbCommand;
		String errmsg = null;
		getConnection();
		List<IConceptCollection> collectionsList = new ArrayList<IConceptCollection>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_CCOWNED_DETAILS
				+ "(?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"
					+ dbCommand + "}");
			sqlStatement.setString(1, userId);
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			if (errmsg == null || errmsg.isEmpty()) {
				ResultSet resultSet = sqlStatement.getResultSet();
				if (resultSet.next()) {
					do {
						conceptCollection = conceptCollectionFactory
								.createConceptCollectionObject();
						conceptCollection.setId(resultSet.getString(1));
						conceptCollection.setName(resultSet.getString(2));
						conceptCollection
								.setDescription(resultSet.getString(3));
						collectionsList.add(conceptCollection);
					} while (resultSet.next());
				}
			}
		} catch (SQLException e) {
			logger.error("Exception", e);
			throw new QuadrigaStorageException(
					"Damn....Database guys are at work!!!!!!");

		} finally {
			closeConnection();
		}
		return collectionsList;
		
		

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws QuadrigaStorageException
	 */
	@Override
	public List<IConceptCollection> getCollaboratedConceptsofUser(String userId)
			throws QuadrigaStorageException {

		String dbCommand;
		String errmsg = null;
		getConnection();
		List<IConceptCollection> collectionsList = new ArrayList<IConceptCollection>();
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.GET_CCCOLLABORATIONS_DETAILS + "(?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"
					+ dbCommand + "}");
			sqlStatement.setString(1, userId);
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			if (errmsg == null || errmsg.isEmpty()) {
				ResultSet resultSet = sqlStatement.getResultSet();
				if (resultSet.next()) {
					do {
						conceptCollection = conceptCollectionFactory
								.createConceptCollectionObject();
						conceptCollection.setId(resultSet.getString(1));
						conceptCollection.setName(resultSet.getString(2));
						conceptCollection
								.setDescription(resultSet.getString(3));

						collectionsList.add(conceptCollection);
					} while (resultSet.next());
				}
			}
		} catch (SQLException e) {
			logger.error("Exception", e);
			throw new QuadrigaStorageException(
					"Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}
		return collectionsList;
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void getCollectionDetails(IConceptCollection collection, String username)
			throws QuadrigaStorageException, QuadrigaAccessException {

		if (collection == null) {
			logger.error("getCollectionDetails: input argument conceptcollection is NULL");
			return;
		}
		String dbCommand;
		String errmsg = null;
		CallableStatement sqlStatement;
		collection.setCollaborators(new ArrayList<ICollaborator>());
		
		getConnection();
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.GET_COLLECTION_DETAILS + "(?,?,?)";
		try {

			 sqlStatement = connection.prepareCall("{"
					+ dbCommand + "}");
			sqlStatement.setString(1, collection.getId());
			sqlStatement.setString(2, username);
			sqlStatement.registerOutParameter(3, java.sql.Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(3);
			
			if (errmsg == null || errmsg.isEmpty()) {
				ResultSet resultSet = sqlStatement.getResultSet();
				if (resultSet.next()) {
					collection.setDescription(resultSet.getString(7));
					collection.setName(resultSet.getString(6));
					do {
						if(resultSet.getString(1) != null){
						concept = conceptFactory.createConceptObject();
						concept.setLemma(resultSet.getString(4));
						concept.setId(resultSet.getString(1));
						concept.setPos(resultSet.getString(3));
						concept.setDescription(resultSet.getString(2));
						collection.addItem(concept);
						}
					} while (resultSet.next());
					
				}
			}
			else
			{
				logger.info("USER ACCESS ERROR:" +errmsg );
				throw new QuadrigaAccessException("Hmmm!!  You dont have access to this page");
			}
		} catch (SQLException e) {
			logger.error("Exception", e);
			throw new QuadrigaStorageException(
					"Damn....Database guys are at work!!!!!!");
		} 
	
		finally {
			closeConnection();
		} 

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws QuadrigaStorageException
	 * @throws QuadrigaAccessException 
	 */
	@Override
	public void saveItem(String lemma, String id, String pos, String desc,
			String conceptId,String username) throws QuadrigaStorageException, QuadrigaAccessException {
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_COLLECTION_ITEM
				+ "(?,?,?,?,?,?,?)";
		getConnection();
		logger.info("---"+username);
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, id);
			sqlStatement.setString(2, lemma);
			sqlStatement.setString(3, pos);
			sqlStatement.setString(4, desc);
			sqlStatement.setString(5, conceptId);
			sqlStatement.setString(6, username);
			sqlStatement.registerOutParameter(7, Types.VARCHAR);
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(7);
			
			if(!errmsg.equals(""))
			{
			logger.error(errmsg);
			
			throw new QuadrigaAccessException("Hmmm!!  Need to try much more hard to get into this");
			}
		} catch (SQLException e) {
			logger.error("Exception:", e);
			e.printStackTrace();
			throw new QuadrigaStorageException(
					"Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws QuadrigaStorageException
	 */
	@Override
	public String validateId(String collectionname)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		String dbCommand;
		String errmsg = null;
		getConnection();
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.VALIDATE_COLLECTIONID + "(?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"
					+ dbCommand + "}");
			sqlStatement.setString(1, collectionname);
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);

		} catch (SQLException sql) {
			logger.error("Exception", sql);
			throw new QuadrigaStorageException(
					"Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}
		return errmsg;
	}

	@Override
	/**
	 * {@inheritDoc}
	 * @throws QuadrigaStorageException
	 */
	public String addCollection(IConceptCollection con)
			throws QuadrigaStorageException {

		String name;
		String description;
		IUser owner = null;
		String dbCommand;
		String errmsg = null;
		CallableStatement sqlStatement;

		name = con.getName();
		description = con.getDescription();

		owner = con.getOwner();

		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.ADD_CONCEPTCOLLECTION + "(?,?,?,?,?)";

		getConnection();

		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, name);
			sqlStatement.setString(2, description);
			sqlStatement.setString(3, "0");
			sqlStatement.setString(4, owner.getUserName());
			sqlStatement.registerOutParameter(5, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(5);
			return errmsg;

		} catch (SQLException e) {
			logger.error("Exception", e);
			throw new QuadrigaStorageException(
					"Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws QuadrigaStorageException
	 */
	@Override
	public String deleteItems(String id, String collectionId, String username)
			throws QuadrigaStorageException {

		String dbCommand;
		String errmsg = null;
		CallableStatement sqlStatement;
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.DELETE_COLLECTION_ITEM + "(?,?,?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, id);
			sqlStatement.setString(2, collectionId);
			sqlStatement.setString(3, username);
			sqlStatement.registerOutParameter(4, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(4);
			return errmsg;
		} catch (SQLException e) {
			logger.error("Exception", e);
			e.printStackTrace();
			throw new QuadrigaStorageException(
					"Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws QuadrigaStorageException
	 */
	@Override
	public String updateItem(IConcept concept, String collectionId, String username)
			throws QuadrigaStorageException {
		String dbCommand;
		String errmsg = null;
		CallableStatement sqlStatement;
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.UPDATE_COLLECTION_ITEM + "(?,?,?,?,?,?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, concept.getId());
			sqlStatement.setString(2, concept.getLemma());
			sqlStatement.setString(3, concept.getDescription());
			sqlStatement.setString(4, concept.getPos());
			sqlStatement.setString(5, collectionId);
			sqlStatement.setString(6, username);
			sqlStatement.registerOutParameter(7, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(7);
			return errmsg;
		} catch (SQLException e) {
			logger.error("Exception", e);
			e.printStackTrace();
			throw new QuadrigaStorageException(
					"Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}
	}

	/**
	 * Method used to execute a given INSERT, UPDATE and DELETE statement in the
	 * database.
	 * 
	 * @return Success - 1
	 * @author satya swaroop boddu
	 * @throws QuadrigaStorageException
	 */
	@Override
	public int setupTestEnvironment(String[] sQuery)
			throws QuadrigaStorageException {
		try {
			getConnection();
			Statement stmt = connection.createStatement();
			for (String s : sQuery)
				stmt.executeUpdate(s);
			return 1;
		} catch (SQLException ex) {
			logger.error("Exception", ex);
			throw new QuadrigaStorageException(
					"Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}
	}

	@Override
	public String addCollaboratorRequest(ICollaborator collaborator, String collectionid, String userName)throws QuadrigaStorageException {
		String dbCommand;
		String errmsg = null;
		String role = "";
		CallableStatement sqlStatement;
		
		String collabName = collaborator.getUserObj().getUserName();
		dbCommand = DBConstants.SP_CALL + " "+ DBConstants.ADD_CC_COLLABORATOR_REQUEST + "(?,?,?,?,?)";
	
		getConnection();
	

		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");

			sqlStatement.setString(1,collectionid);

			sqlStatement.setString(2, collabName);

			for(ICollaboratorRole collaboratorRole:collaborator.getCollaboratorRoles())
			{
				if(collaboratorRole.getRoleDBid()!=null)
				{
					role += collaboratorRole.getRoleDBid()+",";
				}
				
			}

			role = role.substring(0, role.length()-1);
			sqlStatement.setString(3,role);
			sqlStatement.setString(4, userName);
			sqlStatement.registerOutParameter(5, Types.VARCHAR);
			sqlStatement.execute();

			errmsg = sqlStatement.getString(5);
			
		} catch (SQLException e) {
				throw new QuadrigaStorageException();
			}
		
		finally {
			closeConnection();
		}
		
		return errmsg;
	}

	@Override
	public List<ICollaborator> showCollaboratorRequest(String collectionid)throws QuadrigaStorageException {
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		List<ICollaborator> collaboratorList = new ArrayList<ICollaborator>();
		dbCommand = DBConstants.SP_CALL + " "+ DBConstants.SHOW_CC_COLLABORATOR_REQUEST + "(?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");

			sqlStatement.setString(1, collectionid);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();

			errmsg = sqlStatement.getString(2);

			if(errmsg == "")
			{
				ResultSet resultset = sqlStatement.getResultSet();
				while(resultset.next())
				{
					ICollaborator collaborator = collaboratorFactory.createCollaborator();
					IUser user = userFactory.createUserObject();
					user.setUserName(resultset.getString(1));
					collaborator.setUserObj(user);
					List<ICollaboratorRole> collaboratorRoles =  dbConnectionProjectManager.splitAndgetCollaboratorRolesList(resultset.getString(2));
					collaborator.setCollaboratorRoles(collaboratorRoles);
					collaboratorList.add(collaborator);
				}			
			
		   }
			
		}
			catch (SQLException e) {
				throw new QuadrigaStorageException();	
			}
		
		
		finally {
			closeConnection();
		}
		
		return collaboratorList;
	}

	@Override
	public List<IUser> showNonCollaboratorRequest(String collectionid)throws QuadrigaStorageException {
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		List<IQuadrigaRole> quadrigaRoles = new ArrayList<IQuadrigaRole>();
		List<IUser> collaboratorList = new ArrayList<IUser>();
		dbCommand = DBConstants.SP_CALL + " "+ DBConstants.SHOW_CC_NONCOLLABORATOR_REQUEST + "(?,?)";
		
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, collectionid);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);

			if(errmsg == "")
			{
				ResultSet resultset = sqlStatement.getResultSet();
				while(resultset.next())
				{
					IUser collaborator = userFactory.createUserObject();
					collaborator.setUserName(resultset.getString(1));
					quadrigaRoles=dbConnectionManager.listQuadrigaUserRoles(resultset.getString(2));
					collaborator.setQuadrigaRoles(quadrigaRoles);
					collaboratorList.add(collaborator);
				}
			}
		} catch (SQLException e) {
			throw new QuadrigaStorageException();	
		}
		
		finally {
			closeConnection();
		}
	
		return collaboratorList;
	}

	@Override
	public void getCollaborators(IConceptCollection collection) {
		
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		IConceptCollection conceptCollection = conceptCollectionFactory.createConceptCollectionObject();
		conceptCollection.setCollaborators(new ArrayList<ICollaborator>());

		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		
		dbCommand = DBConstants.SP_CALL + " "+ DBConstants.GET_COLLECTION_COLLABORATOR + "(?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1,collection.getId());
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);

			if(errmsg == "")
			{
				ResultSet resultSet = sqlStatement.getResultSet();
				while(resultSet.next())
				{
					ICollaborator collaborator = collaboratorFactory.createCollaborator();
					IUser user = userFactory.createUserObject();
					user.setName(resultSet.getString(2));
					collaborator.setUserObj(user);
					logger.info("role data :" +resultSet.getString(3) );
					String role = resultSet.getString(3);
					if(role == null)
					{
						role="";
					}
					collaboratorRoles = dbConnectionProjectManager.splitAndgetCollaboratorRolesList(role);
					collaborator.setCollaboratorRoles(collaboratorRoles);
					collection.getCollaborators().add(collaborator);							
				}
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		finally {
			closeConnection();
		}
		
	}

	@Override
	public String deleteCollaboratorRequest(String userName, String collectionid)throws QuadrigaStorageException {
		
		String dbCommand;
        String errmsg;
        CallableStatement sqlStatement;
        
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_CC_COLLABORATOR_REQUEST + "(?,?,?)";

        getConnection();
        
		try {
			
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, collectionid);
			sqlStatement.setString(2, userName);
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			sqlStatement.execute();
			
			errmsg = sqlStatement.getString(3);
			
			if(errmsg.equals("no errors"))
			{
				return errmsg;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new QuadrigaStorageException();
			}

		return null;
	}

}	
	

