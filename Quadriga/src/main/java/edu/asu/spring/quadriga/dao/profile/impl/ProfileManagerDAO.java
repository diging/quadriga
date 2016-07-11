package edu.asu.spring.quadriga.dao.profile.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.dao.profile.IProfileManagerDAO;
import edu.asu.spring.quadriga.domain.IProfile;
import edu.asu.spring.quadriga.domain.factories.IProfileFactory;
import edu.asu.spring.quadriga.dto.QuadrigaUserprofileDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserprofileDTOPK;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.web.profile.impl.SearchResultBackBean;

/**
 * this class manages addition, deletion, retrieval of the records for user
 * profile table
 * 
 * @author rohit pendbhaje
 *
 */
@Repository
public class ProfileManagerDAO extends BaseDAO<QuadrigaUserprofileDTO> implements
        IProfileManagerDAO {

    @Autowired
    private SessionFactory sessionFactory;
    
    @Autowired 
    private IProfileFactory profileFactory;

    /**
     * adds records in database table for the profile page
     * 
     * @param name
     *            name of the loggedin user serviceId id of the service from
     *            which records are added resultBackBean this instance contains
     *            all the searchresult information selected by user
     * @throws QuadrigaStorageException
     * @author rohit pendbhaje
     * 
     */
    @Override
    public void addUserProfileDBRequest(String name, String serviceId,
            SearchResultBackBean resultBackBean)
            throws QuadrigaStorageException {
        try {
            Date date = new Date();

            QuadrigaUserprofileDTO userProfile = new QuadrigaUserprofileDTO();
            QuadrigaUserprofileDTOPK userProfileKey = new QuadrigaUserprofileDTOPK(
                    name, serviceId, resultBackBean.getId());
            userProfile.setQuadrigaUserprofileDTOPK(userProfileKey);
            userProfile.setProfilename(resultBackBean.getWord());
            userProfile.setDescription(resultBackBean.getDescription());
            userProfile.setQuadrigaUserDTO(getUserDTO(name));
            userProfile.setCreatedby(name);
            userProfile.setCreateddate(date);
            userProfile.setUpdatedby(name);
            userProfile.setUpdateddate(date);
            sessionFactory.getCurrentSession().save(userProfile);
        } catch (HibernateException ex) {

            throw new QuadrigaStorageException("System error", ex);
        }

    }

    /**
     * retrieves records from database
     * 
     * @param loggedinUser
     * @return list of searchresultbackbeans
     * @throws QuadrigaStorageException
     * @author rohit pendbhaje
     * 
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<IProfile> getUserProfiles(String loggedinUser)
            throws QuadrigaStorageException {
        List<IProfile> userProfiles = new ArrayList<IProfile>();

        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(
                    "QuadrigaUserprofileDTO.findByUsername");
            query.setParameter("username", loggedinUser);
          
            List<QuadrigaUserprofileDTO> userProfileList = query.list();
            for (QuadrigaUserprofileDTO userProfile : userProfileList) {
                IProfile profile = profileFactory.createProfile();
                profile.setProfileId(userProfile
                        .getQuadrigaUserprofileDTOPK().getProfileid());
                
                profile.setServiceId(userProfile
                        .getQuadrigaUserprofileDTOPK().getServiceid());
                profile.setDescription(userProfile
                        .getDescription());
                profile.setProfilename(userProfile.getProfilename());
                userProfiles.add(profile);
            }
        } catch (Exception ex) {
            throw new QuadrigaStorageException();
        }

        return userProfiles;
    }

    /**
     * deletes profile record from table for particular profileid
     * 
     * @param username
     *            name of loggedin user serviceid id of the service
     *            corresponding to the record profileId id of the profile for
     *            which record is to be deleted
     * @throws QuadrigaStorageException
     * @author rohit pendbhaje
     * 
     */
    @Override
    public void deleteUserProfileDBRequest(String username, String serviceid,
            String profileId) throws QuadrigaStorageException {

        try {
            QuadrigaUserprofileDTOPK userProfileKey = new QuadrigaUserprofileDTOPK(
                    username, serviceid, profileId);
            QuadrigaUserprofileDTO userProfile = (QuadrigaUserprofileDTO) sessionFactory
                    .getCurrentSession().get(QuadrigaUserprofileDTO.class,
                            userProfileKey);

            sessionFactory.getCurrentSession().delete(userProfile);
        } catch (Exception ex) {
            throw new QuadrigaStorageException();
        }
    }

    /**
     * retrieves serviceid from table of particular profileid
     * 
     * @param profileId
     *            id of the profile for which record is to be deleted
     * @throws QuadrigaStorageException
     * @author rohit pendbhaje
     * 
     */

    @Override
    public String retrieveServiceIdRequest(String profileid)
            throws QuadrigaStorageException {

        String serviceid = null;

        try {
            Query query = sessionFactory.getCurrentSession().getNamedQuery(
                    "QuadrigaUserprofileDTO.findByProfileid");
            query.setParameter("profileid", profileid);
            List<QuadrigaUserprofileDTO> userprofileList = query.list();

            for (QuadrigaUserprofileDTO userprofile : userprofileList) {
                serviceid = userprofile.getQuadrigaUserprofileDTOPK()
                        .getServiceid();
            }
        }

        catch (Exception e) {
            throw new QuadrigaStorageException("sorry");
        }
        return serviceid;
    }

    @Override
    public QuadrigaUserprofileDTO getDTO(String id) {
        return getDTO(QuadrigaUserprofileDTO.class, id);
    }

}
