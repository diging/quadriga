package edu.asu.spring.quadriga.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This class is implicitly used, when users try to signup using their social account. 
 * 
 * @author Chiraag Subramanian
 */
public class QuadrigaConnectionSignUp implements ConnectionSignUp{
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private IUserHelper userHelper;

    public QuadrigaConnectionSignUp(IUserHelper userHelper) {
        this.userHelper = userHelper;
    }
 
    /**
     * This method returns a unique username for the user or null to indicate that implicit sign up is not possible 
     * at the moment due to system error.
     */
    public String execute(Connection<?> connection) {
        try {
            return userHelper.createUserName(connection);
        } catch (QuadrigaStorageException e) {
            logger.error("Error while creating username", e);
            return null;
        }    
    }
    
}
