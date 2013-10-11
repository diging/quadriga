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
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.ISearchResultFactory;
import edu.asu.spring.quadriga.service.IUserManager;

public class DBConnectionProfileManager extends ADBConnectionManager implements IDBConnectionProfileManager {

	final Logger logger = LoggerFactory.getLogger(DBConnectionProfileManager.class);

	@Autowired
	IProfileFactory profileFactory;
	
	@Autowired
	IUserManager userManager;
	
	@Autowired
	IUserFactory userFactory;
	
	@Autowired
	ISearchResultFactory searchResultFactory;
	
	@Override
	public String addUserProfileDBRequest(String username, String serviceid,String profileid,
			String description) throws QuadrigaStorageException {
				
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		
		dbCommand = DBConstants.SP_CALL + " "+ DBConstants.ADD_USER_PROFILE + "(?,?,?,?,?)";
		
		getConnection();
		
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, username);
			sqlStatement.setString(2, serviceid);
			sqlStatement.setString(3, profileid);
			sqlStatement.setString(4, description);
			sqlStatement.registerOutParameter(5, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(5);
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
	public List<ISearchResult> showProfileDBRequest(String loggedinUser, String serviceid) throws QuadrigaStorageException {
		
		String dbCommand;
		String errmsg;
		CallableStatement sqlStatement;
		List<ISearchResult> searchresultList = new ArrayList<ISearchResult>();
		
		dbCommand = DBConstants.SP_CALL + " "+ DBConstants.SHOW_USER_PROFILE+ "(?,?,?)";

		getConnection();
		
		try {
			sqlStatement = connection.prepareCall("{" + dbCommand + "}");
			sqlStatement.setString(1, loggedinUser);
			sqlStatement.setString(2, serviceid);
			sqlStatement.registerOutParameter(3, Types.VARCHAR);
			sqlStatement.execute();
			errmsg = sqlStatement.getString(3);
			if(errmsg.equals("no errors"))
			{
				ResultSet resulset = sqlStatement.getResultSet();
				while(resulset.next())
				{
					IUser user = userFactory.createUserObject();
					user = userManager.getUserDetails(loggedinUser);
					ISearchResult searchResult = searchResultFactory.getSearchResultObject();
					searchResult.setId(resulset.getString(1));
					searchResult.setDescription(resulset.getString(2));
					searchresultList.add(searchResult);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return searchresultList;
	}

}
