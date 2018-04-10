package edu.asu.spring.quadriga.web.config.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.config.IUserHelper;

@Service
public class UserHelper implements IUserHelper{
    
    @Autowired
    private IUserManager userManager;
    private final Logger logger = LoggerFactory.getLogger(UserHelper.class);
    
    /**
     * This method creates a unique username using the username and providerid derived from the user connection.
     * @param connection : user connection
     * @return unique username           
     */
    @Override
    public String createUserName(Connection<?> connection) throws QuadrigaStorageException{
        
        UserProfile profile = connection.fetchUserProfile();
        String username = profile.getUsername() + "_" + connection.getKey().getProviderId();
        
        // make sure someone else didn't change their username to this one
        IUser userWithUsername = userManager.getUser(username);;
  
        if (userWithUsername.getUserName() == null) {
            return username;
           
        } 
        return userManager.getUniqueUsername(connection.getKey().getProviderId());
    }
    /**
     * This method creates a user object using the username and the user connection
     * @param userName : username
     * @param connection : user connection
     * @return user object 
     */
    @Override
    public User createUser(String userName, Connection<?> connection) {
        UserProfile profile = connection.fetchUserProfile();
        User user = new User();
        user.setUserName(userName);
        user.setName(profile.getName());
        user.setEmail(profile.getEmail());
        user.setProvider(connection.getKey().getProviderId());
        user.setUserIdOfProvider(connection.getKey().getProviderUserId());
        return user;
    }

}
