package edu.asu.spring.quadriga.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import edu.asu.spring.quadriga.web.login.QuadrigaGrantedAuthority;

public class QuadrigaUserDetails implements UserDetails {

    /**
     * 
     */
    private static final long serialVersionUID = -9212214358763172918L;
    private String username;
    private String name;
    private String password;
    private List<QuadrigaGrantedAuthority> authorities;
    private String email;

    public QuadrigaUserDetails(String username, String name, String password,
            List<QuadrigaGrantedAuthority> authorities, String email) {
        super();
        this.username = username;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}