package edu.asu.spring.quadriga.db.profile;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.ISearchResult;

public interface IDBConnectionProfileManager {
	
	public abstract String addUserProfileDBRequest(String name, String serviceid, String id) throws QuadrigaStorageException;
	
	public abstract List<ISearchResult> showProfileDBRequest(String loggedinUser, String serviceid) throws QuadrigaStorageException;

}
