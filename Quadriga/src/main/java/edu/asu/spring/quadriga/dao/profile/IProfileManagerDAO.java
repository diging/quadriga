package edu.asu.spring.quadriga.dao.profile;

import java.util.List;

import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

public interface IProfileManagerDAO {
	
	public abstract void addUserProfileDBRequest(String name, String serviceId, SearchResultBackBean resultBackBean ) throws QuadrigaStorageException;
	
	public abstract List<IProfile> getUserProfiles(String loggedinUser) throws QuadrigaStorageException;
	
	public abstract void deleteUserProfileDBRequest(String profileid,String serviceid, String username) throws QuadrigaStorageException;
	
	public abstract String retrieveServiceIdRequest(String profileid) throws QuadrigaStorageException;
	
}
