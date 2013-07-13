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
			sqlStatement.setString(4, userName);
			sqlStatement.registerOutParameter(5,Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(5);
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
