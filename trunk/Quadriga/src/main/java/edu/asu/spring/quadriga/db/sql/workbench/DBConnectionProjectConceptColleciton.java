package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workspace.IDBConnectionProjectConceptColleciton;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionProjectConceptColleciton implements
		IDBConnectionProjectConceptColleciton {

	private Connection connection;
	
	@Autowired
	private DataSource dataSource;

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionProjectConceptColleciton.class);

	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;

	@Override
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}

	/**
	 * Close the DB connection
	 * @throws QuadrigaStorageException
	 * @author Lohith Dwaraka
	 */
	private void closeConnection() throws QuadrigaStorageException {
		try {
			if (connection != null) {
				connection.close();
			}
		}
		catch(SQLException e)
		{
			logger.info("Close database connection :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}

	/**
	 * Establishes connection with the Quadriga DB
	 * @return      connection handle for the created connection
	 * @throws      QuadrigaStorageException
	 * @author      Lohith Dwaraka
	 */
	private void getConnection() throws QuadrigaStorageException {
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			logger.info("Establish database connection  :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}
	@Override
	public String addProjectConceptCollection(String projectId,
			String conceptCollectionId, String userId)
			throws QuadrigaStorageException {
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_PROJECT_CONCEPT_COLLECTION  + "(?,?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
				try
				{
					sqlStatement = connection.prepareCall("{"+dbCommand+"}");

					//adding the input variables to the SP
					sqlStatement.setString(1, projectId);
					sqlStatement.setString(2, conceptCollectionId);        	
					sqlStatement.setString(3,userId);

					//adding output variables to the SP
					sqlStatement.registerOutParameter(4,Types.VARCHAR);

					sqlStatement.execute();

					errmsg = sqlStatement.getString(4);

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
		return null;
	}

	@Override
	public List<IConceptCollection> listProjectConceptCollection(String projectId,
			String userId) throws QuadrigaStorageException {
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;
		List<IConceptCollection> conceptCollectionList = new ArrayList<IConceptCollection>();

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_PROJECT_CONCEPT_COLLECTION  + "(?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		
		try{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, projectId);
			sqlStatement.setString(2, userId);        	

			//adding output variables to the SP
			sqlStatement.registerOutParameter(3,Types.VARCHAR);

			sqlStatement.execute();
			ResultSet resultSet = sqlStatement.getResultSet();
			if(resultSet !=null){ 
				while (resultSet.next()) { 
					IConceptCollection conceptCollection = conceptCollectionFactory.createConceptCollectionObject();
					conceptCollection.setName(resultSet.getString(1));
					conceptCollection.setDescription(resultSet.getString(2));
					conceptCollection.setId(resultSet.getString(3));
					conceptCollectionList.add(conceptCollection);
				} 
			}
			errmsg = sqlStatement.getString(3);
			if(errmsg.isEmpty())
			{
				return conceptCollectionList;
			}
			else
			{
				logger.info("No concept collection in the project :"+errmsg);
				return null;
			}
			
		}catch(SQLException e){
			logger.info(e.getMessage());
			throw new QuadrigaStorageException();
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		
		
		return conceptCollectionList;
	}
	

	@Override
	public String deleteProjectConceptCollection(String projectId,
			String userId, String conceptCollectionId)
			throws QuadrigaStorageException {
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;		

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_PROJECT_CONCEPT_COLLECTION  + "(?,?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, userId);
			sqlStatement.setString(2, conceptCollectionId);        	
			sqlStatement.setString(3, projectId);  
			//adding output variables to the SP
			sqlStatement.registerOutParameter(4,Types.VARCHAR);

			sqlStatement.execute();
			errmsg = sqlStatement.getString(4);
			return errmsg;
		}catch(SQLException e){
			logger.info(e.getMessage());
			throw new QuadrigaStorageException();
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		finally
		{
			closeConnection();
		}
		return "";
	}
	
}
