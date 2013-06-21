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
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


/**
 * @author satyaswaroop
 * 
 */
public class DBConnectionCCManager extends ADBConnectionManager implements
		IDBConnectionCCManager {

	@Autowired
	private IUserFactory userFactory;
	
	

	private IConceptCollection conceptCollection;

	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;

	private IConcept concept;
	@Autowired
	private IConceptFactory conceptFactory;

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
						conceptCollection.setId(resultSet.getInt(1));
						conceptCollection.setName(resultSet.getString(2));
						conceptCollection
								.setDescription(resultSet.getString(3));
						collectionsList.add(conceptCollection);
					} while (resultSet.next());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException("Damn....Database guys are at work!!!!!!");
			
		}
		finally {
			closeConnection();
		}
		return collectionsList;

	}

	/**
	 * {@inheritDoc}
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public List<IConceptCollection> getCollaboratedConceptsofUser(String userId) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
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
						conceptCollection.setId(resultSet.getInt(1));
						conceptCollection.setName(resultSet.getString(2));
						conceptCollection
								.setDescription(resultSet.getString(3));

						collectionsList.add(conceptCollection);
					} while (resultSet.next());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException("Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}
		return collectionsList;
	}
	
	

	@Override
	/**
	 *  {@inheritDoc}
	 */
	public void getCollectionDetails(IConceptCollection collection) throws QuadrigaStorageException {

		String dbCommand;
		String errmsg = null;
		getConnection();
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.GET_COLLECTION_DETAILS + "(?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"
					+ dbCommand + "}");
			sqlStatement.setInt(1, collection.getId());
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			if (errmsg == null || errmsg.isEmpty()) {
				ResultSet resultSet = sqlStatement.getResultSet();
				if (resultSet.next()) {
					do {
						concept = conceptFactory.createConceptObject();
						concept.setLemma(resultSet.getString(4));
						concept.setId(resultSet.getString(1));
						concept.setPos(resultSet.getString(3));
						concept.setDescription(resultSet.getString(2));
						collection.addItem(concept);
					} while (resultSet.next());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException("Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}
	}

	@Override
	public void saveItem(String lemma, String id, String pos, String desc,
			int conceptId) throws QuadrigaStorageException {
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.ADD_COLLECTION_ITEM
				+ "(?,?,?,?,?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, id);
			sqlStatement.setString(2, lemma);
			sqlStatement.setString(3, pos);
			sqlStatement.setString(4, desc);
			sqlStatement.setInt(5, conceptId);
			sqlStatement.registerOutParameter(6, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(6);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException("Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}

	}

	/**
	 * {@inheritDoc}
	 * @throws QuadrigaStorageException 
	 */
	@Override
	public String validateId(String collectionname) throws QuadrigaStorageException {
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
			sql.printStackTrace();
			throw new QuadrigaStorageException("Damn....Database guys are at work!!!!!!");
		}
		finally{
			closeConnection();
		}
		return errmsg;
	}

	@Override
	public String addCollection(IConceptCollection con) throws QuadrigaStorageException {

		String name;
		String description;
		IUser owner = null;
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;

		// fetch the values from the project object
		name = con.getName();
		description = con.getDescription();

		owner = con.getOwner();

		// command to call the SP
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.ADD_CONCEPTCOLLECTION + "(?,?,?,?,?)";

		// get the connection
		getConnection();

		// establish the connection with the database
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
			e.printStackTrace();
			throw new QuadrigaStorageException("Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}

	}

	@Override
	public String deleteItems(String id, int collectionId) throws QuadrigaStorageException {

		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.DELETE_COLLECTION_ITEM + "(?,?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, id);
			sqlStatement.setInt(2, collectionId);
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(3);
			return errmsg;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException("Damn....Database guys are at work!!!!!!");
		} finally {
			closeConnection();
		}
	}

	@Override
	public String updateItem(IConcept concept, String collectionName) throws QuadrigaStorageException {
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.UPDATE_COLLECTION_ITEM + "(?,?,?,?,?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, concept.getId());
			sqlStatement.setString(2, concept.getLemma());
			sqlStatement.setString(3, concept.getDescription());
			sqlStatement.setString(4, concept.getPos());
			sqlStatement.setString(5, collectionName);
			sqlStatement.registerOutParameter(6, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(6);
			return errmsg;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new QuadrigaStorageException("Damn....Database guys are at work!!!!!!");
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
	public int setupTestEnvironment(String[] sQuery) throws QuadrigaStorageException {
		try {
			getConnection();
			Statement stmt = connection.createStatement();
			for (String s : sQuery)
				stmt.executeUpdate(s);
			return 1;
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw new QuadrigaStorageException("Damn....Database guys are at work!!!!!!");
		}
		finally {
			closeConnection();
		}
	}

}
