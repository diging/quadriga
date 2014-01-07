package edu.asu.spring.quadriga.web.profile.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import edu.asu.spring.quadriga.db.sql.profile.DBConnectionTempProfileManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;

@Service
public class ProfileManager {

	@Autowired
	DBConnectionTempProfileManager dbconnect;
	
	public List<ISearchResult> getUserProfile() throws QuadrigaStorageException
	{
		List<ISearchResult> profile = null;
		profile = dbconnect.showProfile();
		return profile;		
	}
}
