package edu.asu.spring.quadriga.db.sql.workbench;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.db.workbench.IDBConnectionModifyProjCollabManager;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionModifyProjCollabManager extends ADBConnectionManager implements
		IDBConnectionModifyProjCollabManager
{
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionModifyProjCollabManager.class);
	
	@Override
	public String addCollaboratorRequest(ICollaborator collaborator, String projectid,String userName) throws QuadrigaStorageException
	{
		String dbCommand;
        String errmsg;
        String role;
        String collaboratorUserName;
        CallableStatement sqlStatement;
        
        role = "";
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.ADD_COLLABORATOR_REQUEST + "(?,?,?,?,?)";
        
        //retrieve collaborator user and role
        collaboratorUserName = collaborator.getUserObj().getUserName();
        //retrieve the associated roles and form a comma(,) separated string.
    	for(ICollaboratorRole collaboratorRole:collaborator.getCollaboratorRoles())
		{
		     role += collaboratorRole.getRoleDBid() + ",";
		}
    	//remove the ending comma from the string
    	role = role.substring(0,role.length()-1);
    	    	
    	//establish the connection
    	getConnection();
    	
    	try
    	{
    		sqlStatement = connection.prepareCall("{"+dbCommand+"}");
    		sqlStatement.setString(1, projectid);
			sqlStatement.setString(2, collaboratorUserName);
			sqlStatement.setString(3, role);
			sqlStatement.setString(4, userName);
			sqlStatement.registerOutParameter(5,Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(5);
			logger.info("errmsg "+errmsg);
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

	@Override
	public void deleteColloratorRequest(String userName, String projectid) throws QuadrigaStorageException {
		
		String dbCommand;
        String errmsg;
        CallableStatement sqlStatement;
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.DELETE_PROJECT_COLLAB_REQUEST + "(?,?,?)";
        getConnection();
        
		try {
			sqlStatement = connection.prepareCall("{"+dbCommand+"}");
			sqlStatement.setString(1, projectid);
			sqlStatement.setString(2, userName);
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(3);
			if(errmsg.equals("no errors"))
			{
				logger.info("Delte project collaborator request method :"+errmsg);
				throw new QuadrigaStorageException();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Delte project collaborator request method :"+e);
			throw new QuadrigaStorageException();
		}
		finally
		{
			closeConnection();
		}
	}
	
	@Override
	public void updateCollaboratorRequest(String projectid,String collabUser,String collaboratorRole,String username) throws QuadrigaStorageException
	{
		String dbCommand;
        String errmsg;
        CallableStatement sqlStatement;
        
        //command to execute the stored procedure
        dbCommand = DBConstants.SP_CALL+ " " + DBConstants.UPDATE_PROJECT_COLLAB_REQUEST + "(?,?,?,?,?)";
        
        //establish the connection
        getConnection();
        
        try
        {
        	sqlStatement = connection.prepareCall("{"+dbCommand+"}");
        	
        	//add the input parameters
        	sqlStatement.setString(1, projectid);
        	sqlStatement.setString(2, collabUser);
        	sqlStatement.setString(3, collaboratorRole);
        	sqlStatement.setString(4, username);
        	
        	//add output parameter
        	sqlStatement.registerOutParameter(5, Types.VARCHAR);
        	
        	sqlStatement.execute();
        	
        	errmsg = sqlStatement.getString(5);
        	
        	if(!errmsg.equals(""))
        	{
        		logger.info("In update collaborator method :" + errmsg);
        		throw new QuadrigaStorageException(errmsg);
        	}
        }
        catch(SQLException ex)
        {
        	logger.error("n update collaborator method :"+ex);
        	throw new QuadrigaStorageException();
        }
        finally
        {
        	closeConnection();
        }
	}
}
