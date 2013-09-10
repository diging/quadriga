package edu.asu.spring.quadriga.db.sql.profile;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public class DBConnectionProfileManager extends ADBConnectionManager implements IDBConnectionProfileManager {

	final Logger logger = LoggerFactory.getLogger(DBConnectionProfileManager.class);

	
	@Override
	public String addUserProfileDBRequest(String name, String servicename,
			String uri) throws QuadrigaStorageException {
				
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		dbCommand = DBConstants.SP_CALL + " "+ DBConstants.ADD_USER_PROFILE + "(?,?,?,?)";
		
		getConnection();
		
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, name);
			sqlStatement.setString(2, servicename);
			sqlStatement.setString(3, uri);
			sqlStatement.registerOutParameter(4, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(4);
			if(errmsg == " ")
			{
				throw new QuadrigaStorageException();
			}
		} catch (SQLException e) {
			
			throw new QuadrigaStorageException(e);
		}
		
		finally{
			
			closeConnection();
		}

		return errmsg;
	}
	
	@Override
	public IProfile showProfileDBRequest(String loggedinUser) {
		
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		dbCommand = DBConstants.SP_CALL + " "+ DBConstants.ADD_USER_PROFILE + "(?,?)";
		
		getConnection();
		
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, loggedinUser);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			
			if(errmsg == " ")
			{
				ResultSet resulset = sqlStatement.getResultSet();
				while(resulset.next())
				{
					
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}


}
