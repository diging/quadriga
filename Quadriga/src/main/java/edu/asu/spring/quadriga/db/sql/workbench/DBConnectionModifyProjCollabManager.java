package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionModifyProjCollabManager implements
		IDBConnectionModifyProjCollabManager
{
	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionModifyProjCollabManager.class);

	/**
	 *  Assigns the data source
	 *  @param  dataSource
	 *  @author Kiran Kumar Batna
	 */
	@Override
	public void setDataSource(DataSource dataSource) 
	{
		this.dataSource = dataSource;
	}

	/**
	 * Close the DB connection
	 * @throws QuadrigaStorageException
	 * @author Kiran Kumar Batna
	 */
	private void closeConnection() throws QuadrigaStorageException {
		try {
			if (connection != null) {
				connection.close();
			}
		}
		catch(SQLException e)
		{
			logger.info("Close database Connection  :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}

	/**
	 * Establishes connection with the Quadriga DB
	 * @return      connection handle for the created connection
	 * @throws      QuadrigaStorageException
	 * @author      Kiran Kumar Batna
	 */
	private void getConnection() throws QuadrigaStorageException {
		try
		{
			connection = dataSource.getConnection();
		}
		catch(SQLException e)
		{
			logger.info("Open database connection :"+e.getMessage());
			throw new QuadrigaStorageException("Oops!!Database hanged");
		}
	}
	
	@Override
	public String addCollaboratorRequest(ICollaborator collaborator, String projectid) throws QuadrigaStorageException
	{
		String dbCommand;
        String errmsg;
        String role;
        String collaboratorUserName;
        CallableStatement sqlStatement;
        
        role = "";
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_COLLABORATOR_REQUEST + "(?,?,?,?)";
        
        //retrieve collaborator user and role
        collaboratorUserName = collaborator.getUserObj().getUserName();
        //retrieve the associated roles and form a comma(,) separated string.
    	for(ICollaboratorRole collaboratorRole:collaborator.getCollaboratorRoles())
		{
		     role = collaboratorRole.getRoleDBid() + ",";
		}
    	//remove the ending comma from the string
    	role = role.substring(1,role.length()-1);
    	
    	//establish the connection
    	getConnection();
    	
    	try
    	{
    		sqlStatement = connection.prepareCall("{"+dbCommand+"}");
    		sqlStatement.setString(1, projectid);
			sqlStatement.setString(2, collaboratorUserName);
			sqlStatement.setString(3, role);
			sqlStatement.registerOutParameter(4,Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(4);
    	}
        catch(SQLException e)
        {
        	logger.info("Add project collaborator request method :"+e.getMessage());
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
    	return errmsg;
	}
}
