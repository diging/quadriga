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
    
    private final Logger logger = LoggerFactory.getLogger(UserHelperTest.class);
    
    @Mock
    private UserManager userManager = Mockito.mock(UserManager.class);
    
    @InjectMocks
    private UserHelper userHelper;
    
    @Mock
    private Connection connection = Mockito.mock(Connection.class);
    
    @Mock
    private UserProfile profile = Mockito.mock(UserProfile.class);
    
    @Mock
    private IUser user = Mockito.mock(IUser.class);
    
    private ConnectionKey connectionKey;
    
    @Before
    public void init(){
        MockitoAnnotations.initMocks(this); 
    }
    
    @Test
    public void testCreateUserName(){
        connectionKey = new ConnectionKey("github", "23473237");
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
    public void testCreateUser(){
        connectionKey = new ConnectionKey("github", "23473237");
        Mockito.when(connection.getKey()).thenReturn(connectionKey);
        Mockito.when(connection.fetchUserProfile()).thenReturn(profile);
        IUser user = userHelper.createUser("chiraag-subramanian_github", connection);
        assertEquals("chiraag-subramanian_github",user.getUserName());
        assertEquals("github",user.getProvider());
        assertEquals("23473237",user.getUserIdOfProvider());
    }
}
