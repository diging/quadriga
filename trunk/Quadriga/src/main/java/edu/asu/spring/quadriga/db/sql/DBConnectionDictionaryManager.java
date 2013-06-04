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

import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.IDBConnectionDictionaryManager;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.domain.factories.IQuadrigaRoleFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;

public class DBConnectionDictionaryManager implements IDBConnectionDictionaryManager {

	private Connection connection;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private IUserFactory userFactory;

	@Autowired
	private IQuadrigaRoleFactory quadrigaRoleFactory;

	private IDictionary dictionary;

	@Autowired
	private IDictionaryFactory dictionaryFactory;

	/**
	 *  @Description: Assigns the data source
	 *  
	 *  @param : dataSource
	 */
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}

	/**
	 * @Description : Close the DB connection
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
			return -1;
		}
	}

	/**
	 * @Description : Establishes connection with the Quadriga DB
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
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * @Description     This method fetches the list of dictionary for current logged in user                    
	 * 
	 * @returns         List of Dictionary
	 * 
	 * @throws			SQLException
	 *                     
	 * @author          Lohith Dwaraka
	 * 
	 */

	@Override
	public List<IDictionary> getDictionaryOfUser(String userId) {
		String dbCommand;

		getConnection();
		List<IDictionary> dictionaryList = new ArrayList<IDictionary>();
		dbCommand = DBConstants.SP_CALL + " " + DBConstants.GET_DICTIONARY_DETAILS + "(?,?)";
		try {

			CallableStatement sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, userId);
			sqlStatement.registerOutParameter(2, java.sql.Types.VARCHAR);

			sqlStatement.execute();

			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet.next()) { 
				do { 
					dictionary = dictionaryFactory.createDictionaryObject();
					dictionary.setName(resultSet.getString(1));
					dictionary.setDescription(resultSet.getString(2));
					dictionary.setId(resultSet.getString(3));
					System.out.println(" come in");
					dictionaryList.add(dictionary);
				} while (resultSet.next());
			}		
		} 
		catch (SQLException e) {
			e.printStackTrace();
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

}
