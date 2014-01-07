package edu.asu.spring.quadriga.service;

import java.util.List;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;

public interface IUserProfileManager {
	
	public abstract void addUserProfile(String name, String serviceId, SearchResultBackBean resultBackBean) throws QuadrigaStorageException;
	
	public abstract List<SearchResultBackBean> showUserProfile(String loggedinUser) throws QuadrigaStorageException;
	
	public abstract void deleteUserProfile(String profileId,String serviceid, String username) throws QuadrigaStorageException;

}
