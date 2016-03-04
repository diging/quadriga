package edu.asu.spring.quadriga.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.service.QuadrigaUserDetails;

/**
 * Converts the user name in the principal to lower case characters if the
 * authentication is successful. This will allow users to authenticate to
 * various services and access content in the UI with case insensitive user name
 * checks.
 * 
 * @author skollur1
 *
 */
@Service("authSuccessHandler")
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        Object obj = authentication.getPrincipal();
        if (obj instanceof QuadrigaUserDetails) {
            QuadrigaUserDetails quadrigaUserDetails = (QuadrigaUserDetails) obj;
            String userName = quadrigaUserDetails.getUsername();
            quadrigaUserDetails.setUsername(userName.toLowerCase());
        }
        response.sendRedirect(request.getContextPath() + "/auth/welcome");
    }
}