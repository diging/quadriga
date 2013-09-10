package edu.asu.spring.quadriga.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserProfileManager;

@Service
public class UserProfileManager implements IUserProfileManager {
	
	@Autowired
	@Qualifier("DBConnectionProfileManagerBean")
	IDBConnectionProfileManager connectionProfileManager;

	@Override
	public String addUserProfile(String name,String servicename,String uri) throws QuadrigaStorageException {

		String errmsg = connectionProfileManager.addUserProfileDBRequest(name, servicename, uri);
		
		return errmsg;
	}

	@Override
	public IProfile showUserProfile() {

		
		return null;
	}
	
	
	

}
