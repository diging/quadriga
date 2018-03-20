package edu.asu.spring.quadriga.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.exceptions.QuadrigaNotificationException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.UsernameExistsException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.manageusers.beans.AccountRequest;


public class QuadrigaConnectionSignUp implements ConnectionSignUp{
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private IUserManager userManager;
    private IUserHelper userHelper;

    public QuadrigaConnectionSignUp(IUserManager userManager, IUserHelper userHelper) {
        this.userManager = userManager;
        this.userHelper = userHelper;
    }
 
    public String execute(Connection<?> connection) {
        
        User user = userHelper.createUser(connection);
        AccountRequest accountRequest = new AccountRequest();
        accountRequest.setUsername(user.getUserName());
        accountRequest.setName( user.getName());
        accountRequest.setEmail(user.getEmail());
        accountRequest.setSocialSignIn(true);
        accountRequest.setProvider(user.getProvider());
        accountRequest.setUserIdOfProvider(user.getUserIdOfProvider());
        try {
             userManager.addNewUser(accountRequest);
        } catch (QuadrigaStorageException | QuadrigaNotificationException | UsernameExistsException e) {
            logger.error("Could not store user.", e);
            return null;
        }
        return user.getUserName();
        
    }
    
}
