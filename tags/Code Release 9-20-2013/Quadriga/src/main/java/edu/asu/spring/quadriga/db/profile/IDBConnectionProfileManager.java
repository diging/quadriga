package edu.asu.spring.quadriga.db.profile;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionProfileManager {
	
	public abstract String addUserProfileDBRequest(String name, String servicename, String uri) throws QuadrigaStorageException;
	
	public abstract List<IProfile> showProfileDBRequest(String loggedinUser) throws QuadrigaStorageException;


}
