package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.service.IUserProfileManager;

@Service
public class UserProfileManager implements IUserProfileManager {
	
	@Autowired
	@Qualifier("DBConnectionProfileManagerBean")
	IDBConnectionProfileManager connectionProfileManager;

	/*@Override
	public String addUserProfile(String name,String serviceid,String profileid,
			String description) throws QuadrigaStorageException {

		String errmsg = connectionProfileManager.addUserProfileDBRequest(name, serviceid, profileid, description);
		
		return errmsg;
	}

	@Override
	public List<ISearchResult> showUserProfile(String loggedinUser, String serviceid) throws QuadrigaStorageException {
		
		List<ISearchResult> searchResultList = connectionProfileManager.showProfileDBRequest(loggedinUser, serviceid);
		
		return searchResultList;
	} */
	
	
	

}
