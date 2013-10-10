package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjectManager;
import edu.asu.spring.quadriga.domain.IProject;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionModifyProjectManager extends ADBConnectionManager implements
		IDBConnectionModifyProjectManager 
{
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionModifyProjectManager.class);
	
	/**
	 *  {@inheritDoc}
	 */
	@Override
	public void addProjectRequest(IProject project) throws QuadrigaStorageException
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
        
        //establish the connection with the database
        try
        {
            //get the connection
            getConnection();
            
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
			
			if(!errmsg.equals(""))
			{
				logger.error("Add project request method :",errmsg);
	        	throw new QuadrigaStorageException();
			}
			
        }
        catch(SQLException e)
        {
        	logger.error("Add project request method :",e);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
	}
	
    /**
     * {@inheritDoc}
     */
	@Override
	public void updateProjectRequest(IProject project,String userName) throws QuadrigaStorageException
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
       
        try
        {
            //get the connection
            getConnection();
            
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
			
			if(!errmsg.equals(""))
			{
				logger.error("Update project request method :",errmsg);
	        	throw new QuadrigaStorageException(); 
			}
        }
        catch(SQLException e)
        {
        	logger.error("Update project request method :",e);
        	throw new QuadrigaStorageException(); 
        }
        finally
        {
        	closeConnection();
        }
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
	public void deleteProjectRequest(String projectIdList) throws QuadrigaStorageException
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
        	
        	if(!errmsg.equals(""))
        	{
        		logger.info("Delte project request method : "+errmsg);
        		throw new QuadrigaStorageException();
        	}
        }
        catch(SQLException ex)
        {
        	logger.info("Delte project request method : "+ex.getMessage());
        	throw new QuadrigaStorageException();
        }
	}
	
	/**
	 * This method transfers the ownership of project form one person to another
	 * @param projectId - project id for which the ownership is transfered.
	 * @param oldOwner - existing owner of the project
	 * @param newOwner - new owner of the owner
	 * @param collabRole - collaborator role of the existing owner
	 * @throws QuadrigaStorageException
	 * @author kiranbatna
	 */
	@Override
	public void transferProjectOwnerRequest(String projectId,String oldOwner,String newOwner,String collabRole) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		//command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.TRANSFER_PROJECT_OWNERSHIP + "(?,?,?,?,?)";
        
        //get the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//add input parameters
        	sqlStatement.setString(1,projectId);
        	sqlStatement.setString(2, oldOwner);
        	sqlStatement.setString(3, newOwner);
        	sqlStatement.setString(4, collabRole);
        	
        	//add output parameter
        	sqlStatement.registerOutParameter(5, Types.VARCHAR);
        	
           	sqlStatement.execute();
        	errmsg = sqlStatement.getString(5);
        	
        	if(!errmsg.equals(""))
        	{
        		logger.info("Transfer project request method :"+errmsg);
        		throw new QuadrigaStorageException(errmsg);
        	}
        }
        catch(SQLException e)
        {
        	logger.error("Transfer project request method :"+e);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
	}
	
	
	@Override
	public String assignProjectOwnerEditor(String projectId,String owner) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		//command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ASSIGN_PROJECT_EDITOR_OWNER + "(?,?,?)";
        
        //get the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//add input parameters
        	sqlStatement.setString(1,projectId);
        	sqlStatement.setString(2, owner);
        	
        	//add output parameter
        	sqlStatement.registerOutParameter(3, Types.VARCHAR);
        	
           	sqlStatement.execute();
        	errmsg = sqlStatement.getString(3);
        	
        	if(!errmsg.equals(""))
        	{
        		logger.info("Assign project editor to owner request method :"+errmsg);
        	}
        	return errmsg;
        }
        catch(SQLException e)
        {
        	logger.error("Assign project editor to owner request method :"+e);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
	}
	
	
	@Override
	public String deleteProjectOwnerEditor(String projectId,String owner) throws QuadrigaStorageException
	{
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		//command to call the SP
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_PROJECT_EDITOR_OWNER + "(?,?,?)";
        
        //get the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//add input parameters
        	sqlStatement.setString(1,projectId);
        	sqlStatement.setString(2, owner);
        	
        	//add output parameter
        	sqlStatement.registerOutParameter(3, Types.VARCHAR);
        	
           	sqlStatement.execute();
        	errmsg = sqlStatement.getString(3);
        	
        	if(!errmsg.equals(""))
        	{
        		logger.info("Deleted owner from project editor Role : " +errmsg);
        	}
        	return errmsg;
        }
        catch(SQLException e)
        {
        	logger.error("Deleted owner from project editor Role method :"+e);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
	}
}
