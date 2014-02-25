package edu.asu.spring.quadriga.db.profile;

import java.util.List;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

public interface IDBConnectionProfileManager {
	
	public abstract void addUserProfileDBRequest(String name, String serviceId, SearchResultBackBean resultBackBean ) throws QuadrigaStorageException;
	
	public abstract List<SearchResultBackBean> showProfileDBRequest(String loggedinUser) throws QuadrigaStorageException;
	
	public abstract void deleteUserProfileDBRequest(String profileid,String serviceid, String username) throws QuadrigaStorageException;
	
	public abstract String retrieveServiceIdRequest(String profileid) throws QuadrigaStorageException;
	
	public abstract int setupTestEnvironment(String sQuery) throws QuadrigaStorageException;


}
