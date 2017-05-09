package edu.asu.spring.quadriga.web.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import edu.asu.spring.quadriga.web.login.QuadrigaGrantedAuthority;
import edu.asu.spring.quadriga.web.login.RoleNames;

/**
 * 
 * This class authenticates the Admin user and assigns the appropriate authorities.
 * @author Chiraag Subramanian 
 *
 */
public class AdminAuthenticationProvider implements AuthenticationProvider {
    private Properties properties;
    
    @Override
    public Authentication authenticate(Authentication authentication) 
      throws AuthenticationException {
        
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
         
        loadProperties();

        if (name.equals(getAdminUsername()) && password.equals(getAdminPassword())) {
  
            // use the credentials
            // and authenticate against the third-party system
            QuadrigaGrantedAuthority grantedAuthority =  new QuadrigaGrantedAuthority();
            grantedAuthority.setAuthority(RoleNames.ROLE_QUADRIGA_ADMIN);
            
            List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
            grantedAuthorities.add(grantedAuthority);
            
            return new UsernamePasswordAuthenticationToken(
              name, password, grantedAuthorities);
            
        } else {
            return null;
        }
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
          UsernamePasswordAuthenticationToken.class);
    }
    
    private void loadProperties(){
        ClassPathResource resource = new ClassPathResource( "settings.properties" );
        properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = resource.getInputStream();
            properties.load( inputStream );
            inputStream.close();
        } catch ( IOException e ) {
           
        } 
    }
    
    private String getAdminUsername() {
        return properties.getProperty( "quadriga.admin.username" );
    }
    
    private String getAdminPassword(){
        return properties.getProperty( "quadriga.admin.password" );
    }
    
    
}
