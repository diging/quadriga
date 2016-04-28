package edu.asu.spring.quadriga.domain;

/**
 * Interface to implement stats of user.
 *
 */
public interface IUserStats {

    public abstract Integer getWorkspaceCount();

    public abstract void setWorkspaceCount(Integer workspaceCount);

    public abstract Integer getNetworkCount();

    public abstract void setNetworkCount(Integer networkCount);

    public abstract String getUsername();

    public abstract void setUsername(String username);

    public abstract void incrementNetworkCount();

}
