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
import edu.asu.spring.quadriga.web.LoginController;
import edu.asu.spring.quadriga.web.config.IUserHelper;


@Service
public class UserHelper implements IUserHelper{
    
    @Autowired
    private IUserManager userManager;
    private static final Logger logger = LoggerFactory.getLogger(UserHelper.class);
    @Override
    public User createUser(Connection<?> connection) {
        
        UserProfile profile = connection.fetchUserProfile();
        
        String username = profile.getUsername() + "_" + connection.getKey().getProviderId();
        
        User user = new User();
        
        // make sure someone else didn't change their username to this one
        IUser userWithUsername = null;
        try {
            logger.info("Start userManager.getUser(username) : "+username);
            userWithUsername = userManager.getUser(username);
            logger.info("End userManager.getUser(username) : "+username);
        } catch (QuadrigaStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (userWithUsername.getUserName() == null) {
            logger.info("userWithUsername "+username+" does not exist in the database");
            user.setUserName(username);
        } else {
            logger.info("userWithUsername "+username+" exists in the database");
            user.setUserName(userManager.getUniqueUsername(connection.getKey().getProviderId()));
        }

        user.setName(profile.getName());
        user.setEmail(profile.getEmail());
        user.setProvider(connection.getKey().getProviderId());
        user.setUserIdOfProvider(connection.getKey().getProviderUserId());
        logger.info("username: "+user.getUserName()+" , name: "+user.getName()+" , email: "+user.getEmail()+" , provider: "+user.getProvider()+" , userIdOfProvider: "+user.getUserIdOfProvider());
        return user;
    }

}
