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
import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionModifyProjectManager implements
		IDBConnectionModifyProjectManager 
{
	private Connection connection;

	@Autowired
	private DataSource dataSource;
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionModifyProjectManager.class);

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
	
	/**
	 *  This method inserts a record for new project
	 *  @param  project
	 *  @return String error message on error else a blank string
	 *  @exception QuadrigaStorageException
	 *  @author Kiran Kumar Batna 
	 */
	@Override
	public String addProjectRequest(IProject project) throws QuadrigaStorageException
	{
		String name;
		String description;
		String unixName;
		String projectAccess;
        String dbCommand;
        String errmsg;
        IUser owner = null;
        CallableStatement sqlStatement;
        
        //fetch the values from the project object
        name = project.getName();
        description = project.getDescription();
        unixName = project.getUnixName();
        owner = project.getOwner();
        projectAccess = project.getProjectAccess().name();
        
        //command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_PROJECT_REQUEST + "(?,?,?,?,?,?)";
        
        //get the connection
        getConnection();
        
        //establish the connection with the database
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//adding the input variables to the SP
        	sqlStatement.setString(1, name);
        	sqlStatement.setString(2, description);
        	sqlStatement.setString(3,unixName);
        	sqlStatement.setString(4,projectAccess);
        	sqlStatement.setString(5,owner.getUserName());
        	
        	//adding output variables to the SP
			sqlStatement.registerOutParameter(6,Types.VARCHAR);

			sqlStatement.execute();

			errmsg = sqlStatement.getString(6);
			
			return errmsg;
			
        }
        catch(SQLException e)
        {
        	logger.info("Add project request method :"+e.getMessage());
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
	}
	
    /**
     * This method updates the details of given project.
     * @param project
     * @param userName
     * @return String errmsg blank on success and error message on failure.
     * @throws QuadrigaStorageException
     */
	@Override
	public String updateProjectRequest(IProject project,String userName) throws QuadrigaStorageException
	{
		String name;
		String description;
		String unixname;
		String projectAccess;
        String dbCommand;
        String errmsg;
        String projectid;
        CallableStatement sqlStatement;
        
      //fetch the values from the project object
        name = project.getName();
        description = project.getDescription();
        unixname = project.getUnixName();
        projectAccess = project.getProjectAccess().name();
        projectid = project.getInternalid();
        
        //command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.MODIFY_PROJECT_REQUEST + "(?,?,?,?,?,?,?)";
       
        //get the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//adding the input variables to the SP
        	sqlStatement.setString(1, name);
        	sqlStatement.setString(2, description);
        	sqlStatement.setString(3,unixname);
        	sqlStatement.setString(4,projectAccess);
        	sqlStatement.setString(5,userName);
        	sqlStatement.setString(6,projectid);
        	
        	//adding output variables to the SP
			sqlStatement.registerOutParameter(7,Types.VARCHAR);
			
			sqlStatement.execute();

			errmsg = sqlStatement.getString(7);
        }
        catch(SQLException e)
        {
        	logger.info("Update project request method :"+e.getMessage());
        	throw new QuadrigaStorageException(); 
        }
        finally
        {
        	closeConnection();
        }
        return errmsg;
	}
	
	/**
	 * This method deletes the project and its associations from database.
	 * @param    projectIdList - Project internal id's in a comma 
	 *           separated string.
	 * @return   String errmsg - blank identifies success and null identifies
	 *           error in deleting the records.
	 * @author   Kiran Kumar Batna
	 */
	@Override
	public String deleteProjectRequest(String projectIdList) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		//command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_PROJECT_REQUEST + "(?,?)";
        
        //get the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	sqlStatement.setString(1,projectIdList);
        	sqlStatement.registerOutParameter(2, Types.VARCHAR);
        	
        	sqlStatement.execute();
        	errmsg = sqlStatement.getString(2);
        }
        catch(SQLException ex)
        {
        	logger.info("Delte project request method : "+ex.getMessage());
        	throw new QuadrigaStorageException();
        }
        return errmsg;
	}
}
