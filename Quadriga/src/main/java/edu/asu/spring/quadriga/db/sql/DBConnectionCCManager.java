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

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.IConcept;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IConceptFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;

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
	 */
	@Override
	public List<IConceptCollection> getConceptsOwnedbyUser(String userId) {
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
			ResultSet resultSet = sqlStatement.getResultSet();
			if (resultSet.next()) {
				do {
					conceptCollection = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollection.setName(resultSet.getString(1));
					conceptCollection.setDescription(resultSet.getString(2));
					collectionsList.add(conceptCollection);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return collectionsList;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IConceptCollection> getCollaboratedConceptsofUser(String userId) {
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
			ResultSet resultSet = sqlStatement.getResultSet();
			if (resultSet.next()) {
				do {
					conceptCollection = conceptCollectionFactory
							.createConceptCollectionObject();
					conceptCollection.setName(resultSet.getString(1));
					conceptCollection.setDescription(resultSet.getString(2));

					collectionsList.add(conceptCollection);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			closeConnection();
		}
		return collectionsList;
	}

	@Override
	public void getCollectionDetails(IConceptCollection collection) {

		String dbCommand;
		String errmsg = null;
		getConnection();
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.GET_COLLECTION_DETAILS + "(?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"
					+ dbCommand + "}");
			sqlStatement.setString(1, collection.getName());
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			System.out.println(errmsg);
			ResultSet resultSet = sqlStatement.getResultSet();
			if (resultSet.next()) {
				do {

					concept = conceptFactory.createConceptObject();
					concept.setLemma(resultSet.getString(5));
					concept.setName(resultSet.getString(2));
					concept.setPos(resultSet.getString(4));
					concept.setDescription(resultSet.getString(3));
					collection.addItem(concept);
				} while (resultSet.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		} finally {
			closeConnection();
		}
	}

	@Override
	public void saveItem(String lemma, String id, String pos, String desc,
			String conceptId) {
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
			sqlStatement.setString(5, conceptId);
			sqlStatement.registerOutParameter(6, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(6);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeConnection();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String validateId(String collectionname) {
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
			return errmsg;
		} catch (SQLException sql) {

		}
		return errmsg;
	}

	@Override
	public String addCollection(IConceptCollection con) {

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
			throw new RuntimeException(e.getMessage());
		} finally {
			closeConnection();
		}

	}

	@Override
	public String deleteItems(String id, String collectionName) {

		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.DELETE_COLLECTION_ITEM + "(?,?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, id);
			sqlStatement.setString(2, collectionName);
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(3);
			return errmsg;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			closeConnection();
		}
	}

	@Override
	public String updateItem(IConcept concept, String collectionName) {
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.UPDATE_COLLECTION_ITEM + "(?,?,?,?,?,?)";
		getConnection();
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, concept.getName());
			sqlStatement.setString(2, concept.getLemma());
			sqlStatement.setString(3, concept.getDescription());
			sqlStatement.setString(4, concept.getPos());
			sqlStatement.setString(5, collectionName);
			sqlStatement.registerOutParameter(6, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(6);
			return errmsg;
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
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
	 */
	@Override
	public int setupTestEnvironment(String sQuery) {
		try {
			getConnection();
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sQuery);
			return 1;
		} catch (SQLException ex) {
			throw new RuntimeException(ex.getMessage());
		}
	}

}
