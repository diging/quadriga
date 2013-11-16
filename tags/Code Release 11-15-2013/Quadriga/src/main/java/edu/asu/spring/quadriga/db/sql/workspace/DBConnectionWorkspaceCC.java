/**
 * 
 */
package edu.asu.spring.quadriga.db.sql.workspace;

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
import edu.asu.spring.quadriga.db.workspace.IDBConnectionWorkspaceCC;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/** 
 * DB connection class to add, list, delete concept collection in workspace
 * @author Lohith Dwaraka
 *
 */
public class DBConnectionWorkspaceCC extends ADBConnectionManager implements IDBConnectionWorkspaceCC {

	@Autowired
	private IConceptCollectionFactory conceptCollectionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionWorkspaceCC.class);
	
	/**
	 *  Method add a Concept collection to a workspace                   
	 * @returns         path of list workspace Concept collection page
	 * @throws			SQLException
	 * @author          Lohith Dwaraka
	 */
	@Override
	public String addWorkspaceCC(String workspaceId, String CCId, String userId)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_WORKSPACE_CONCEPT_COLLECTION  + "(?,?,?,?)";

		logger.info(" DB command "+dbCommand);
		//get the connection
		getConnection();
		//establish the connection with the database
				try
				{
					sqlStatement = connection.prepareCall("{"+dbCommand+"}");

					//adding the input variables to the SP
					sqlStatement.setString(1, workspaceId);
					sqlStatement.setString(2, CCId);        	
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
					logger.error("",e);
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
	 * Method to list the Concept collection in workspace
	 * @param workspaceId
	 * @param userId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public List<IConceptCollection> listWorkspaceCC(String workspaceId,
			String userId) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;
		List<IConceptCollection> conceptCollectionList = new ArrayList<IConceptCollection>();

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.LIST_WORKSPACE_CONCEPT_COLLECTION  + "(?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		
		try{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, workspaceId);
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

	/**
	 * Method to delete the concept collection from workspace
	 * @param workspaceId
	 * @param userId
	 * @param dictioanaryId
	 * @return
	 * @throws QuadrigaStorageException
	 */
	@Override
	public String deleteWorkspaceCC(String workspaceId, String userId,
			String CCId) throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		String dbCommand;
		String errmsg="";
		CallableStatement sqlStatement;		

		//command to call the SP
		dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_WORKSPACE_CONCEPT_COLLECTION  + "(?,?,?,?)";

		//get the connection
		getConnection();
		//establish the connection with the database
		try{
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");

			//adding the input variables to the SP
			sqlStatement.setString(1, userId);
			sqlStatement.setString(2, CCId);        	
			sqlStatement.setString(3, workspaceId);  
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
