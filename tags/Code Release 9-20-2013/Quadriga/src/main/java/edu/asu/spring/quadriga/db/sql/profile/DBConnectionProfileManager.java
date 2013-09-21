package edu.asu.spring.quadriga.db.sql.profile;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.db.sql.ADBConnectionManager;
import edu.asu.spring.quadriga.db.sql.DBConstants;
import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.factories.IProfileFactory;
import edu.asu.spring.quadriga.domain.factories.IUserFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;

public class DBConnectionProfileManager extends ADBConnectionManager implements IDBConnectionProfileManager {

	final Logger logger = LoggerFactory.getLogger(DBConnectionProfileManager.class);

	@Autowired
	IProfileFactory profileFactory;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	IUserFactory userFactory;
	
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
			if(errmsg.equals("no errors"))
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
	public List<IProfile> showProfileDBRequest(String loggedinUser) throws QuadrigaStorageException {
		
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		List<IProfile> profileList = new ArrayList<IProfile>();
		
		dbCommand = DBConstants.SP_CALL + " "+ DBConstants.SHOW_USER_PROFILE+ "(?,?)";

		getConnection();
		
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, loggedinUser);
			sqlStatement.registerOutParameter(2, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(2);
			if(errmsg.equals("no errors"))
			{
				ResultSet resulset = sqlStatement.getResultSet();
				while(resulset.next())
				{
					IUser user = userFactory.createUserObject();
					user = userManager.getUserDetails(loggedinUser);
					IProfile profile = profileFactory.createProfileObject();
					profile.setUserObj(user);
					profile.setServiceName(resulset.getString(1));
					profile.setUri(resulset.getString(2));
					profileList.add(profile);	
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return profileList;
	}

}
