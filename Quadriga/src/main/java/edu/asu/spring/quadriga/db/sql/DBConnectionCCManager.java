/**
 * 
 */
package edu.asu.spring.quadriga.db.sql;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionCCManager;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;

/**
 * @author satyaswaroop
 *
 */
public class DBConnectionCCManager extends ADBConnectionManager implements IDBConnectionCCManager {

	
	
	@Autowired
	private IUserFactory userFactory;
	
	private IConceptCollection conceptCollection;

	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IConceptCollection> getConceptsOwnedbyUser(String userId) {
		String dbCommand;
		String errmsg=null;
		getConnection();
		List<IConceptCollection> collectionsList = new ArrayList<IConceptCollection>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_CCOWNED_DETAILS + "(?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userId);
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();
			 errmsg = sqlStatement.getString(2);
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet.next()) { 
				do { 
					conceptCollection = conceptCollectionFactory.createConceptCollectionObject();
					conceptCollection.setName(resultSet.getString(1));
					conceptCollection.setDescription(resultSet.getString(2));
					conceptCollection.setId(resultSet.getString(3));
					collectionsList.add(conceptCollection);
				} while (resultSet.next());
			}		
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println(""+errmsg);
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return collectionsList;

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<IConceptCollection> getCollaboratedConceptsofUser(
			String userId) {
		// TODO Auto-generated method stub
		String dbCommand;
		String errmsg=null;
		getConnection();
		List<IConceptCollection> collectionsList = new ArrayList<IConceptCollection>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_CCCOLLABORATIONS_DETAILS + "(?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userId);
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();
			 errmsg = sqlStatement.getString(2);
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet.next()) { 
				do { 
					conceptCollection = conceptCollectionFactory.createConceptCollectionObject();
					conceptCollection.setName(resultSet.getString(1));
					conceptCollection.setDescription(resultSet.getString(2));
					conceptCollection.setId(resultSet.getString(3));
					collectionsList.add(conceptCollection);
				} while (resultSet.next());
			}		
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			System.out.println(""+errmsg);
			e.printStackTrace();
		}
		finally
		{
			closeConnection();
		}
		return collectionsList;
	}

}
