package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionProjectConceptColleciton;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionProjectConceptColleciton extends ADBConnectionManager implements
		IDBConnectionProjectConceptColleciton {

	private static final Logger logger = LoggerFactory.getLogger(DBConnectionProjectConceptColleciton.class);

	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;

	@Override
	public void addProjectConceptCollection(String projectId,
			String conceptCollectionId, String userId)
			throws QuadrigaStorageException {
		String dbCommand;
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

					sqlStatement.getString(4);


				}
				catch(SQLException e)
				{
					e.printStackTrace();
					throw new QuadrigaStorageException();
					
				}catch(Exception e){
					e.printStackTrace();
				}
				finally
				{
					closeConnection();
				}
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
	public void deleteProjectConceptCollection(String projectId,
			String userId, String conceptCollectionId)
			throws QuadrigaStorageException {
		String dbCommand;
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
			sqlStatement.getString(4);
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
	}
	
}
