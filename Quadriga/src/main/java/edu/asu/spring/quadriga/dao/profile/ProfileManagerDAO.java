package edu.asu.spring.quadriga.dao.profile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.DAOConnectionManager;
import edu.asu.spring.quadriga.db.profile.IDBConnectionProfileManager;
import edu.asu.spring.quadriga.dto.QuadrigaUserprofileDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserprofileDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.impl.SearchResultBackBean;

@Repository
public class ProfileManagerDAO extends DAOConnectionManager
implements IDBConnectionProfileManager
{
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addUserProfileDBRequest(String name, String serviceId,
			SearchResultBackBean resultBackBean)
			throws QuadrigaStorageException
	{
		try
		{
			Date date = new Date();
			
			QuadrigaUserprofileDTO userProfile = new QuadrigaUserprofileDTO();
			QuadrigaUserprofileDTOPK userProfileKey = new QuadrigaUserprofileDTOPK(name,serviceId,resultBackBean.getId());
			userProfile.setQuadrigaUserprofileDTOPK(userProfileKey);
			userProfile.setProfilename(resultBackBean.getWord());
			userProfile.setDescription(resultBackBean.getDescription());
			userProfile.setQuadrigaUserDTO(getUserDTO(name));
			userProfile.setCreatedby(name);
			userProfile.setCreateddate(date);
			userProfile.setUpdatedby(name);
			userProfile.setUpdateddate(date);
			sessionFactory.getCurrentSession().save(userProfile);
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SearchResultBackBean> showProfileDBRequest(String loggedinUser)
			throws QuadrigaStorageException 
	{
		List<SearchResultBackBean> userProfileSearchList = null;

		try
		{
		  SearchResultBackBean searchResultBackBean = null;
		  userProfileSearchList = new ArrayList<SearchResultBackBean>();
		  Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserprofileDTO.findByUsername");
		  query.setParameter("username", loggedinUser);
		  List<QuadrigaUserprofileDTO> userProfileList = query.list();
		  for(QuadrigaUserprofileDTO userProfile : userProfileList)
		  {
			    searchResultBackBean = new SearchResultBackBean();
				searchResultBackBean.setId(userProfile.getQuadrigaUserprofileDTOPK().getProfileid());
				searchResultBackBean.setDescription(userProfile.getDescription());
				searchResultBackBean.setWord(userProfile.getProfilename());
				userProfileSearchList.add(searchResultBackBean);
		  }
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
		
		return userProfileSearchList;
	}

	@Override
	public void deleteUserProfileDBRequest(String profileId,String serviceid,String username)
			throws QuadrigaStorageException 
	{
		
		try
		{
			QuadrigaUserprofileDTOPK userProfileKey = new QuadrigaUserprofileDTOPK(username,serviceid,profileId);
			QuadrigaUserprofileDTO userProfile = (QuadrigaUserprofileDTO) sessionFactory.getCurrentSession().get(QuadrigaUserprofileDTO.class,userProfileKey);
			
		    sessionFactory.getCurrentSession().delete(userProfile);
		}
		catch(Exception ex)
		{
			throw new QuadrigaStorageException();
		}
	}

	@Override
	public String retrieveServiceIdRequest(String profileid) throws QuadrigaStorageException {
		
		String serviceid = null;
		
		try
		{
		Query query = sessionFactory.getCurrentSession().getNamedQuery("QuadrigaUserprofileDTO.findByProfileid");
		query.setParameter("profileid", profileid);
		List<QuadrigaUserprofileDTO> userprofileList = query.list();
		
		for(QuadrigaUserprofileDTO userprofile: userprofileList )
		{
			serviceid = userprofile.getQuadrigaUserprofileDTOPK().getServiceid();
		}
	}
		
		catch(Exception e){
			throw new QuadrigaStorageException("sorry");
		}
		return serviceid;
	}

}
