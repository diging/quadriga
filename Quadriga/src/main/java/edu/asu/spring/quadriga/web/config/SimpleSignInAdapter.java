package edu.asu.spring.quadriga.web.config;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

import edu.asu.spring.quadriga.dao.impl.UserDAO;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.UsernameExistsException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.QuadrigaUserDetails;
import edu.asu.spring.quadriga.web.login.QuadrigaGrantedAuthority;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * 
 * This class authorizes (assigns appropriate roles) users authenticated by Github.
 * @author Chiraag Subramanian 
 *
 */
public final class SimpleSignInAdapter implements SignInAdapter {
    
    private final Logger logger = LoggerFactory.getLogger(SignInAdapter.class);
    
    private IUserManager userManager;
    private IUserHelper userHelper;

    public SimpleSignInAdapter(IUserManager userManager, IUserHelper userHelper) {
        this.userManager = userManager;
        this.userHelper = userHelper;
    }

    /**
     * This method checks if Quadriga contains details of the user authenticated by Github.
     * If the user details are present (which indicates the user is an authorized user), appropriate roles are assigned,
     * Else if the user details are not present (which indicates the user is an unregistered / not approved user, restricted access is assigned)
     */
    
    @Override
    public String signIn(String userId, Connection<?> connection,
            NativeWebRequest request) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        IUser user = null;
            user = (IUser)userManager.findUserByProviderUserId(connection.getKey().getProviderUserId(), connection.getKey().getProviderId());
      
        // if user details is not present in the database, create a database record for the user.
        if (user == null) {
            
            user = userHelper.createUser(connection);
            try {
                userManager.addSocialUser(user.getUserName(), user.getName(), user.getEmail(), user.getProvider(), user.getUserIdOfProvider());
               
            } catch (QuadrigaStorageException e) {
                logger.error("Could not add user.", e);
            } catch (UsernameExistsException e) {
                logger.error("Could not add user.", e);
            }
        } 
        // if user details is present in the database, assign appropriate roles to the user.
        else {
           
            List<IQuadrigaRole> quadrigaRoles = user.getQuadrigaRoles();
            if(quadrigaRoles != null){
                 for(IQuadrigaRole quadrigaRole : quadrigaRoles){
                     authorities.add(new QuadrigaGrantedAuthority(quadrigaRole.getId()));
                 }
            }
        }
        
        QuadrigaUserDetails userDetails = new QuadrigaUserDetails(user.getUserName(), user.getName(), user.getPassword(), null, user.getEmail());
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(userDetails, null,authorities));
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(
                request.getNativeRequest(HttpServletRequest.class),
                request.getNativeResponse(HttpServletResponse.class));

        if (savedRequest != null) {
            return savedRequest.getRedirectUrl();
        }
        return null;
    }

}
