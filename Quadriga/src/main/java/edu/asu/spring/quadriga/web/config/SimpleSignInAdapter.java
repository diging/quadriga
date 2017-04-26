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

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.exceptions.UsernameExistsException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.service.QuadrigaUserDetails;
import edu.asu.spring.quadriga.web.login.QuadrigaGrantedAuthority;
import edu.asu.spring.quadriga.web.login.RoleNames;

public final class SimpleSignInAdapter implements SignInAdapter {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());            

    private IUserManager userManager;
    private IUserHelper userHelper;

    public SimpleSignInAdapter(IUserManager userManager, IUserHelper userHelper) {
        this.userManager = userManager;
        this.userHelper = userHelper;
    }

    public String signIn(String userId, Connection<?> connection,
            NativeWebRequest request) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        IUser user = null;
        try {
            user = (IUser)userManager.findUserByProviderUserId(connection.getKey().getProviderUserId(), connection.getKey().getProviderId());
        } catch (QuadrigaStorageException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        
        if (user == null) {
            authorities.add(new QuadrigaGrantedAuthority(
                    RoleNames.ROLE_QUADRIGA_RESTRICTED));
            user = userHelper.createUser(connection);

            try {
                userManager.addSocialUser(user.getUserName(), user.getName(), user.getEmail(), user.getProvider(), user.getUserIdOfProvider());
               
            } catch (QuadrigaStorageException e) {
                logger.error("Could not add user.", e);
                user = null;
            } catch (UsernameExistsException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
           
            List<IQuadrigaRole> quadrigaRoles = user.getQuadrigaRoles();
            if(quadrigaRoles != null){
                 for(IQuadrigaRole quadrigaRole : quadrigaRoles){
                     authorities.add(new QuadrigaGrantedAuthority(quadrigaRole.getId()));
                 }
            }
        }
        
        QuadrigaUserDetails userDetails = new QuadrigaUserDetails(user.getUserName(), user.getName(), user.getPassword(), null, user.getEmail());
        
        SecurityContextHolder.getContext()
                .setAuthentication(
                        new UsernamePasswordAuthenticationToken(userDetails, null,
                                authorities));
        
        
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(
                request.getNativeRequest(HttpServletRequest.class),
                request.getNativeResponse(HttpServletResponse.class));

        if (savedRequest != null) {
            return savedRequest.getRedirectUrl();
        }
        return null;
    }

}
