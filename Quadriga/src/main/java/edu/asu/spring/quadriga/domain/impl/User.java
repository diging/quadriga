package edu.asu.spring.quadriga.domain.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.IUser;

/**
 * @description : User class describing the properties of a User object
 * 
 * @author : Kiran Kumar Batna
 * @author : Ram Kumar Kumaresan
 */
public class User implements IUser, Serializable {
        
    private String name;
    private String userName;
    private String password;
    private String email;
    private List<IQuadrigaRole> quadrigaRoles;
    private String createdBy;
    private Date createdDate;
    private String updatedBy;
    private Date updatedDate;
    private String provider;
    private String userIdOfProvider;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public List<IQuadrigaRole> getQuadrigaRoles() {
        return quadrigaRoles;
    }

    @Override
    public void setQuadrigaRoles(List<IQuadrigaRole> quadrigaRoles) {
        this.quadrigaRoles = quadrigaRoles;

    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdatedDate() {
        return updatedDate;
    }

    @Override
    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String getProvider() {  
        return provider;
    }

    @Override
    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    public String getUserIdOfProvider() {
        return userIdOfProvider;
    }

    @Override
    public void setUserIdOfProvider(String userIdOfProvider) {
        this.userIdOfProvider = userIdOfProvider;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    @Override
    public String getQuadrigaRolesAsString() {
        StringBuffer sb = new StringBuffer();

        for (IQuadrigaRole role : quadrigaRoles) {
            sb.append(role.getDBid());
            sb.append(",");
        }

        return sb.toString();
    }

}
