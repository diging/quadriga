package edu.asu.spring.quadriga.web.config.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.UserProfile;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.impl.UserManager;

/**
 * This class consists of unit tests for UserHelper.
 * 
 * @author Chiraag Subramanian
 *
 */
public class UserHelperTest {
    
    @Mock
    private UserManager userManager;
    
    @Mock
    private Connection connection;
    
    @Mock
    private UserProfile profile;
    
    @Mock
    private IUser user;
    
    @InjectMocks
    private UserHelper userHelper;
    
    private final Logger logger = LoggerFactory.getLogger(UserHelperTest.class);
    
    private ConnectionKey connectionKey;
    
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this); 
        connectionKey = new ConnectionKey("github", "23473237");
    }
    
    @Test
    public void test_createUserName_whenUsernameNotUsed(){
        
        Mockito.when(connection.getKey()).thenReturn(connectionKey);
        Mockito.when(connection.fetchUserProfile()).thenReturn(profile);
        Mockito.when(profile.getUsername()).thenReturn("chiraag-subramanian");
        Mockito.when(user.getUserName()).thenReturn(null);
        
        try {
            Mockito.when(userManager.getUser(Mockito.anyString())).thenReturn(user);
        } catch (QuadrigaStorageException e) {
            logger.info("Error while testing userHelper.createUserName", e);
        }
        
        try {
            assertEquals("chiraag-subramanian_github",userHelper.createUserName(connection));
        } catch (QuadrigaStorageException e) {
            logger.info("Error while testing userHelper.createUserName", e);
        }
        
        
    }
    
    @Test
    public void test_createUserName_whenUsernameUsed(){
        
        Mockito.when(connection.getKey()).thenReturn(connectionKey);
        Mockito.when(connection.fetchUserProfile()).thenReturn(profile);
        Mockito.when(profile.getUsername()).thenReturn("chiraag-subramanian");
        Mockito.when(user.getUserName()).thenReturn("chiraag-subramanian_github");
        
        try {
            Mockito.when(userManager.getUser(Mockito.anyString())).thenReturn(user);
        } catch (QuadrigaStorageException e) {
            logger.info("Error while executing userManager.getUser", e);
        }
        
        try {
            assertFalse("chiraag-subramanian_github".equals(userHelper.createUserName(connection)));
        } catch (QuadrigaStorageException e) {
            logger.info("Error while testing userHelper.createUserName", e);
        }
    }
    
    @Test
    public void test_createUser_success(){
        
        Mockito.when(connection.getKey()).thenReturn(connectionKey);
        Mockito.when(connection.fetchUserProfile()).thenReturn(profile);
        IUser user = userHelper.createUser("chiraag-subramanian_github", connection);
        assertEquals("chiraag-subramanian_github",user.getUserName());
        assertEquals("github",user.getProvider());
        assertEquals("23473237",user.getUserIdOfProvider());
    }
}
