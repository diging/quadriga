package edu.asu.spring.quadriga.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.profile.IProfileManagerDAO;
import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserProfileManager;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

/**
 * this class acts as a connecting layer between controller and DAO classes of profile package
 * 
 * @author rohit pendbhaje
 *
 */

@Service
public class UserProfileManager implements IUserProfileManager {
	
	@Autowired
	IProfileManagerDAO connectionProfileManager;
	
/**
 * adds a searchresults retrieved from services to the user's searchresult account
 * @param  name				name of the loggedin user
 * 		   serviceId		id of the service from which records are added
 * 		   resultBackBean	this instance contains all the searchresult information selected by user
 * @throws QuadrigaStorageException\
 * @author rohit pendbhaje
 */
	@Override
	@Transactional
	public void addUserProfile(String name, String serviceId, SearchResultBackBean resultBackBean) throws QuadrigaStorageException {

		connectionProfileManager.addUserProfileDBRequest(name, serviceId, resultBackBean);
		
	}

/**
 * displays the current set of records present in the user account
 * @param  loggedinUser		current logged in user
 * @return list of searchresults in database
 * @throws QuadrigaStorageException
 * @author rohit pendbhaje
 *  
 */
	@Override
	@Transactional
	public List<IProfile> getUserProfiles(String loggedinUser) throws QuadrigaStorageException {	
		return connectionProfileManager.getUserProfiles(loggedinUser);
	}

/**
 * deletes a searchresults retrieved from services to the user's searchresult account
 * @param  username		name of the loggedin user
 * 		   serviceid	id of the service from which records are added
 * 		   profileid	id of the searchresult being delete
 * @throws QuadrigaStorageException
 * @author rohit pendbhaje 
 */
	@Override
	@Transactional
	public void deleteUserProfile(String username,String serviceid, String profileId)
			throws QuadrigaStorageException {
		
		connectionProfileManager.deleteUserProfileDBRequest(username, serviceid, profileId);
		
	}

/**
 * retrieves the serviceid for particular profileid
 * @param  profileid 	id of the profile of which serviceid is required
 * @return serviceid of the corrosponding profileid
 * @throws QuadrigaStorageException
 * @author rohit pendbhaje
 */
	@Override
	@Transactional
	public String retrieveServiceId(String profileid) throws QuadrigaStorageException  {

		String serviceid = connectionProfileManager.retrieveServiceIdRequest(profileid);
		
		return serviceid;
	} 

}
