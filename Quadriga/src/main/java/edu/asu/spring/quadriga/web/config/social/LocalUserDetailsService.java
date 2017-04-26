package edu.asu.spring.quadriga.web.config.social;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.StandardSocketFactory;

import edu.asu.spring.quadriga.dao.impl.BaseDAO;
import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.IUserManager;
import edu.asu.spring.quadriga.web.login.QuadrigaGrantedAuthority;
import edu.asu.spring.quadriga.web.config.social.QuadrigaSocialUserDetails;

@Service
public class LocalUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserManager userManager;

    private static final Logger logger = LoggerFactory.getLogger(LocalUserDetailsService.class);
    @Override
    public UserDetails loadUserByUsername(String arg0)
            throws UsernameNotFoundException {
        IUser user = null;
        try {
            user = userManager.getUser(arg0);
        } catch (QuadrigaStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (user == null)
            throw new UsernameNotFoundException("Couldn't find username.");

        List<QuadrigaGrantedAuthority> roles = new ArrayList<QuadrigaGrantedAuthority>();
        List<IQuadrigaRole> quadrigaRoles = user.getQuadrigaRoles();
        if(quadrigaRoles != null){
             for(IQuadrigaRole quadrigaRole : quadrigaRoles){
                 roles.add(new QuadrigaGrantedAuthority(quadrigaRole.getId()));
             }
        }

        UserDetails details = new QuadrigaSocialUserDetails(user.getUserName(),
                user.getName(), user.getPassword(), roles, user.getEmail());
        return details;
    }
}