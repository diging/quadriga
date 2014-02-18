package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;
import edu.asu.spring.quadriga.service.IUserProfileManager;

@Service
public class UserProfileManager implements IUserProfileManager {
	
	@Autowired
	IDBConnectionProfileManager connectionProfileManager;
	

	@Override
	@Transactional
	public void addUserProfile(String name, String serviceId, SearchResultBackBean resultBackBean) throws QuadrigaStorageException {

		connectionProfileManager.addUserProfileDBRequest(name, serviceId, resultBackBean);
		
	}

	@Override
	@Transactional
	public List<SearchResultBackBean> showUserProfile(String loggedinUser) throws QuadrigaStorageException {
		
		List<SearchResultBackBean> searchResultList = connectionProfileManager.showProfileDBRequest(loggedinUser);
		
		return searchResultList;
	}

	@Override
	@Transactional
	public void deleteUserProfile(String profileId,String serviceid, String username)
			throws QuadrigaStorageException {
		
		connectionProfileManager.deleteUserProfileDBRequest(profileId,serviceid, username);
		
	}

	@Override
	@Transactional
	public String retrieveServiceId(String profileid) throws QuadrigaStorageException  {

		String serviceid = connectionProfileManager.retrieveServiceIdRequest(profileid);
		
		return serviceid;
	} 
	
	
	
	
	

}
