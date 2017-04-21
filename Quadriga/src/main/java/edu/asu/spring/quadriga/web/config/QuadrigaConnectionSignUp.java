package edu.asu.spring.quadriga.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;

import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;


public class QuadrigaConnectionSignUp implements ConnectionSignUp{
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private IUserManager userManager;
    private IUserHelper userHelper;

    public QuadrigaConnectionSignUp(IUserManager userManager, IUserHelper userHelper) {
        this.userManager = userManager;
        this.userHelper = userHelper;
    }
 
    public String execute(Connection<?> connection) {
        logger.info("Start: Execute");
        logger.info("Start Call: userHelper.createUser");
        User user = userHelper.createUser(connection);
        logger.info("End Call: userHelper.createUser");
        try {
            logger.info("Start Call: userManager.addSocialUser");
            userManager.addSocialUser(user.getUserName(), user.getName(), user.getEmail(), user.getProvider(), user.getUserIdOfProvider());
            logger.info("End Call: userManager.addSocialUser");
        } catch (QuadrigaStorageException e) {
            logger.error("Could not store user.", e);
            return null;
        }
        logger.info("End: Execute");
        return user.getUserName();
        
    }
    
}
