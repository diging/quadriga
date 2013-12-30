package edu.asu.spring.quadriga.dao.profile;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;

@Repository
public class ProfileManagerDAO extends DAOConnectionManager
//implements IDBConnectionProfileManager
{

//	@Override
	public String addUserProfileDBRequest(String name, String serviceId,
			SearchResultBackBean resultBackBean)
			throws QuadrigaStorageException
	{
		
		return null;
	}

//	@Override
	public List<SearchResultBackBean> showProfileDBRequest(String loggedinUser)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
	public String deleteUserProfileDBRequest(String id, String username)
			throws QuadrigaStorageException {
		// TODO Auto-generated method stub
		return null;
	}

}
