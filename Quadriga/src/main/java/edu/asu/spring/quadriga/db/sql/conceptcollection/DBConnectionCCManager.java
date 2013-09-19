/**
 * 
 */
package edu.asu.spring.quadriga.db.sql.conceptcollection;

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

import edu.asu.spring.quadriga.db.conceptcollection.IDBConnectionCCManager;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionRetrieveProjCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorFactory;
import edu.asu.spring.quadriga.domain.factories.ICollaboratorRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.ICollaboratorRoleManager;
import edu.asu.spring.quadriga.service.IUserManager;

/**
 * This class executes all the stored procedures related to the concept
 * collections
 * 
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

	@Autowired
	private ICollaboratorRoleManager collaboratorRoleManager;
	
	@Autowired
    private IUserManager userManager;

	/**
	 * {@inheritDoc}
	 * 
	 * @throws QuadrigaStorageException
	 */
	@Override
	public List<IConceptCollection> getConceptsOwnedbyUser(String userId)
			throws QuadrigaStorageException {
		if (userId == null || userId.isEmpty()) {
			logger.error("getConceptsOwnedbyUser: input argument userId is NULL");
			return null;
		} else {
			String dbCommand;
			String errmsg = null;
			getConnection();
			List<IConceptCollection> collectionsList = new ArrayList<IConceptCollection>();
			dbCommand = DBConstants.SP_CALL + " "
					+ DBConstants.GET_CCOWNED_DETAILS + "(?,?)";
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
							conceptCollection.setDescription(resultSet
									.getString(3));
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

	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws QuadrigaStorageException
	 */
	@Override
	public List<IConceptCollection> getCollaboratedConceptsofUser(String userId)
			throws QuadrigaStorageException {
		if (userId == null || userId.isEmpty()) {
			logger.error("getConceptsOwnedbyUser: input argument userId is NULL");
			return null;
		} else {
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
	}

	/**
	 * {@inheritDoc}
	 * 
	 */
	@Override
	public void getCollectionDetails(IConceptCollection collection,
			String username) throws QuadrigaStorageException,
			QuadrigaAccessException {

		if (collection == null || username == null || username.isEmpty()) {
			logger.error("getCollectionDetails: input argument conceptcollection or username  is NULL");
			return;
		}
		String dbCommand;
		String errmsg = null;
		CallableStatement sqlStatement;
		IUser owner = null;
		collection.setCollaborators(new ArrayList<ICollaborator>());

		getConnection();
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.GET_COLLECTION_DETAILS + "(?,?,?)";
		try {

			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
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
					owner = userManager.getUserDetails(resultSet.getString(8));
					collection.setOwner(owner);
					do {
						if (resultSet.getString(1) != null) {
							concept = conceptFactory.createConceptObject();
							concept.setLemma(resultSet.getString(4));
							concept.setId(resultSet.getString(1));
							concept.setPos(resultSet.getString(3));
							concept.setDescription(resultSet.getString(2));
							collection.addItem(concept);
						}
					} while (resultSet.next());

				}
			} else {
				logger.info("USER ACCESS ERROR:" + errmsg);
				throw new QuadrigaAccessException(
						"Hmmm!!  You dont have access to this page");
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
			String conceptId, String username) throws QuadrigaStorageException,
			QuadrigaAccessException {
		if (lemma == null || conceptId == null || id == null || pos == null || desc==null || username == null || username.isEmpty() || id.isEmpty() || lemma.isEmpty() || pos.isEmpty() || desc.isEmpty() || conceptId.isEmpty()) {
			logger.error("saveItem: input argument are improper");
			return;
		}
		String dbCommand;
		String errmsg = "";
		CallableStatement sqlStatement;
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_COLLECTION_ITEM
				+ "(?,?,?,?,?,?,?)";
		getConnection();
		logger.info("---" + username);
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

			if (!errmsg.equals("")) {
				logger.error(errmsg);

				throw new QuadrigaAccessException(
						"Hmmm!!  Need to try much more hard to get into this");
			}
		} catch (SQLException e) {
			logger.error("Exception:", e);
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
		if(collectionname==null || collectionname.isEmpty())
		{
			logger.error("validateId: input argument are improper");
			return null;
		}
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
		if(con==null)
		{
			logger.error("addCollection: input argument IConceptCollection is null");
			return null;
		}
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
			logger.info("db command " +dbCommand);
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
			logger.info("closing");
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
		if(id == null || collectionId==null || username==null || id.isEmpty() || collectionId.isEmpty() || username.isEmpty())
		{
			logger.error("deleteItems: input argument IConceptCollection is null");
			return null;
		}
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
	public String updateItem(IConcept concept, String collectionId,
			String username) throws QuadrigaStorageException {
		if(concept == null || collectionId==null || username==null ||  collectionId.isEmpty() || username.isEmpty())
		{
			logger.error("updateItem: input argument IConceptCollection is null");
			return null;
		}
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


	/**
	 * retrieves data from database to retrieve collaborators
	 * 
	 * @param collectionid
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 * 
	 */
	@Override
	public List<ICollaborator> showCollaboratorRequest(String collectionid)
			throws QuadrigaStorageException {
		if(collectionid == null || collectionid.isEmpty())
		{
			logger.error("showCollaboratorRequest: input argument are improper");
			return null;
		}
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		List<ICollaborator> collaboratorList = new ArrayList<ICollaborator>();
		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.SHOW_CC_COLLABORATOR_REQUEST + "(?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");

			sqlStatement.setString(1, collectionid);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();

			errmsg = sqlStatement.getString(2);

			if (errmsg == "") {
				ResultSet resultset = sqlStatement.getResultSet();
				while (resultset.next()) {
					ICollaborator collaborator = collaboratorFactory
							.createCollaborator();
					IUser user = userFactory.createUserObject();
					//fetching the user details
					user = userManager.getUserDetails(resultset.getString(1));
					collaborator.setUserObj(user);
					//fetch the collaborator roles
					collaboratorRoles = splitAndgetCollaboratorRolesList(resultset
							.getString(2));

					collaborator.setCollaboratorRoles(collaboratorRoles);
					collaboratorList.add(collaborator);
				}

			}

		} catch (SQLException e) {
			throw new QuadrigaStorageException(e);
		}

		finally {
			closeConnection();
		}

		return collaboratorList;
	}

	/**
	 * retrieves data from database to show non collaborators
	 * 
	 * @param collectionid
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 * 
	 */
	@Override
	public List<IUser> showNonCollaboratorRequest(String collectionid)
			throws QuadrigaStorageException {
		if(collectionid == null || collectionid.isEmpty())
		{
			logger.error("showNonCollaboratorRequest: input argument are improper");
			return null;
		}
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		List<IUser> collaboratorList = new ArrayList<IUser>();
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.SHOW_CC_NONCOLLABORATOR_REQUEST + "(?,?)";

		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, collectionid);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);

			if (errmsg == "") {
				ResultSet resultset = sqlStatement.getResultSet();
				while (resultset.next()) {
					IUser collaborator = userFactory.createUserObject();
					collaborator = userManager.getUserDetails(resultset.getString(1));
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

	/**
	 * retrieves data from database to show collaborators
	 * 
	 * @param collection
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 * 
	 */
	@Override
	public void getCollaborators(IConceptCollection collection) throws QuadrigaStorageException {
		if(collection == null )
		{
			logger.error("getCollaborators: input argument IConceptCollection is null");
			return;
		}
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		IConceptCollection conceptCollection = conceptCollectionFactory
				.createConceptCollectionObject();
		conceptCollection.setCollaborators(new ArrayList<ICollaborator>());

		List<ICollaboratorRole> collaboratorRoles = new ArrayList<ICollaboratorRole>();

		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.GET_COLLECTION_COLLABORATOR + "(?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, collection.getId());
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);

			if (errmsg == "") {
				ResultSet resultSet = sqlStatement.getResultSet();
				while (resultSet.next()) {
					ICollaborator collaborator = collaboratorFactory
							.createCollaborator();
					IUser user = userFactory.createUserObject();
					user.setName(resultSet.getString(2));
					collaborator.setUserObj(user);
					logger.info("role data :" + resultSet.getString(3));
					String role = resultSet.getString(3);
					if (role == null) {
						role = "";
					}
					collaboratorRoles = dbConnectionProjectManager
							.splitAndgetCollaboratorRolesList(role);
					collaborator.setCollaboratorRoles(collaboratorRoles);
					collection.getCollaborators().add(collaborator);
				}
			}

		} catch (SQLException e) {
			logger.error("retrieve concept collection collaborators :",e);
		
		}

		finally {
			closeConnection();
		}

	}

	/**
	 * splits the comma seperated roles string coming from database and converts
	 * into list of roles
	 * 
	 * @param role
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 * 
	 */
	public List<ICollaboratorRole> splitAndgetCollaboratorRolesList(String role) {
		if(role == null || role.isEmpty())
		{
			logger.error("splitAndgetCollaboratorRolesList: input argument role is null");
			return null;
		}
		String[] collabroles;
		List<ICollaboratorRole> collaboratorRoleList = new ArrayList<ICollaboratorRole>();
		ICollaboratorRole collaboratorRole = null;

		collabroles = role.split(",");

		for (int i = 0; i < collabroles.length; i++) {
			collaboratorRole = collaboratorRoleManager.getCollectionCollabRoleByDBId(collabroles[i]);
			collaboratorRoleList.add(collaboratorRole);
		}

		return collaboratorRoleList;
	}

}
