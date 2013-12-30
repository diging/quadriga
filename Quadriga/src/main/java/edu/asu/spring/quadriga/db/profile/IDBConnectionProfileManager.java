package edu.asu.spring.quadriga.db.profile;

import java.util.List;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;

public interface IDBConnectionProfileManager {
	
	public abstract String addUserProfileDBRequest(String name, String serviceId, SearchResultBackBean resultBackBean ) throws QuadrigaStorageException;
	
	public abstract List<SearchResultBackBean> showProfileDBRequest(String loggedinUser) throws QuadrigaStorageException;
	
	public abstract String deleteUserProfileDBRequest(String id, String username) throws QuadrigaStorageException;

}
