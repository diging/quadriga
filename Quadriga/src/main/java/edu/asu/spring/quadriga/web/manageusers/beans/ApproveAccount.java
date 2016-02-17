package edu.asu.spring.quadriga.web.manageusers.beans;

public class ApproveAccount {

    private String username;
    private String[] roles;
    private String action;
    
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String[] getRoles() {
        return roles;
    }
    public void setRoles(String[] roles) {
        this.roles = roles;
    }
    public String getAction() {
        return action;
    }
    public void setAction(String action) {
        this.action = action;
    }
    
    
}
