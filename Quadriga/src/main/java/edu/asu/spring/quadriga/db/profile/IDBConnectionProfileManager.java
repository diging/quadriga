package edu.asu.spring.quadriga.db.profile;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDBConnectionProfileManager {
	
	public abstract String addUserProfileDBRequest(String name, String servicename, String uri) throws QuadrigaStorageException;
	
	public abstract IProfile showProfileDBRequest(String loggedinUser) throws QuadrigaStorageException;


}
