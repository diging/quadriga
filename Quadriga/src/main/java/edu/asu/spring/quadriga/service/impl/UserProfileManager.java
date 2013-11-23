package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;
import edu.asu.spring.quadriga.profile.impl.ServiceBackBean;
import edu.asu.spring.quadriga.service.IUserProfileManager;

@Service
public class UserProfileManager implements IUserProfileManager {
	
	@Autowired
	@Qualifier("DBConnectionProfileManagerBean")
	IDBConnectionProfileManager connectionProfileManager;

	@Override
	public String addUserProfile(String name,String serviceid,String profilebuilder) throws QuadrigaStorageException {

		String errmsg = connectionProfileManager.addUserProfileDBRequest(name, serviceid, profilebuilder);
		
		return errmsg;
	}

	@Override
	public List<SearchResultBackBean> showUserProfile(String loggedinUser) throws QuadrigaStorageException {
		
		List<SearchResultBackBean> searchResultList = connectionProfileManager.showProfileDBRequest(loggedinUser);
		
		return searchResultList;
	}

	@Override
	public String deleteUserProfile(String id)
			throws QuadrigaStorageException {
		
		String errmsg = connectionProfileManager.deleteUserProfileDBRequest(id);
		
		return errmsg;
	} 
	
	
	

}
