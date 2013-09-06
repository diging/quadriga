package edu.asu.spring.quadriga.db.sql.conceptcollection;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.db.conceptcollection.IDBConnectionCCCollaboratorManager;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionCCCollaboratorManager extends ADBConnectionManager implements
		IDBConnectionCCCollaboratorManager 
{
	
	private static final Logger logger = LoggerFactory.getLogger(DBConnectionCCCollaboratorManager.class);
	
	/**
	 * retrieves data from database to add collaborators
	 * 
	 * @param collaborator
	 * @param collectionid
	 * @param userName
	 * @throws QuadrigaStorageException
	 * @author rohit pendbhaje
	 * 
	 */
	@Override
	public void addCollaboratorRequest(ICollaborator collaborator,
			String collectionid, String userName)
			throws QuadrigaStorageException 
	{
		String dbCommand;
		String errmsg;
		StringBuilder role;
		CallableStatement sqlStatement;

		role = new StringBuilder();
		String collabName = collaborator.getUserObj().getUserName();
		dbCommand = DBConstants.SP_CALL + " "
				+ DBConstants.ADD_CC_COLLABORATOR_REQUEST + "(?,?,?,?,?)";

		getConnection();

		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, collectionid);
			sqlStatement.setString(2, collabName);

			for (ICollaboratorRole collaboratorRole : collaborator
					.getCollaboratorRoles()) {
				if (collaboratorRole.getRoleDBid() != null) 
				{
					role.append(",");
					role.append(collaboratorRole.getRoleDBid());
				}
			}
			sqlStatement.setString(3, role.toString().substring(1));
			sqlStatement.setString(4, userName);
			sqlStatement.registerOutParameter(5, Types.VARCHAR);
			sqlStatement.execute();

			errmsg = sqlStatement.getString(5);
			
			if(!errmsg.equals(""))
			{
				logger.info("In concept collection add collaorator method :"+errmsg);
				throw new QuadrigaStorageException();
			}
			
		} catch (SQLException e) {
			logger.error("In concept collection add collaorator method :",e);
			throw new QuadrigaStorageException();
		}
		finally {
			closeConnection();
		}
	}


}
