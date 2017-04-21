package edu.asu.spring.quadriga.web.tokens.impl;

import edu.asu.spring.quadriga.web.tokens.IApiTokenContents;

public class ApiTokenContents implements IApiTokenContents {

    private String username;
    private boolean expired;
    

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean isExpired() {
        return expired;
    }

    @Override
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    
    
}