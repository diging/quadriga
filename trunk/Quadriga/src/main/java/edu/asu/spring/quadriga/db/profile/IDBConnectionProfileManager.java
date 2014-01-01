package edu.asu.spring.quadriga.db.profile;

import java.util.List;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;

public interface IDBConnectionProfileManager {
	
	public abstract void addUserProfileDBRequest(String name, String serviceId, SearchResultBackBean resultBackBean ) throws QuadrigaStorageException;
	
	public abstract List<SearchResultBackBean> showProfileDBRequest(String loggedinUser) throws QuadrigaStorageException;
	
	public abstract void deleteUserProfileDBRequest(String profileId,String serviceid, String username) throws QuadrigaStorageException;

}
