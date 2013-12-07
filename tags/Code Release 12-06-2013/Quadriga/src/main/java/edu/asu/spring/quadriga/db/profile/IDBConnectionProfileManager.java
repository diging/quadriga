package edu.asu.spring.quadriga.db.profile;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;
import edu.asu.spring.quadriga.profile.impl.ServiceBackBean;

public interface IDBConnectionProfileManager {
	
	public abstract String addUserProfileDBRequest(String name, String serviceid, String id) throws QuadrigaStorageException;
	
	public abstract List<SearchResultBackBean> showProfileDBRequest(String loggedinUser) throws QuadrigaStorageException;
	
	public abstract String deleteUserProfileDBRequest(String id, String username) throws QuadrigaStorageException;

}
