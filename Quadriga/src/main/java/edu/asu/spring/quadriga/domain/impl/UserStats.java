package edu.asu.spring.quadriga.domain.impl;

import edu.asu.spring.quadriga.domain.IUserStats;

/**
 * This class represents user with limited properties. It provides count of
 * networks and workspace in a project for which a user is owner of.
 *
 * @author Ajay Modi
 *
 */

public class UserStats implements IUserStats {

    private Integer workspaceCount;
    private Integer networkCount;
    private String username;

    public UserStats(String username, int workspaceCount, int networkCount) {
        this.username = username;
        this.workspaceCount = workspaceCount;
        this.networkCount = networkCount;
    }

    public UserStats() {
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Integer getWorkspaceCount() {
        return workspaceCount;
    }

    @Override
    public void setWorkspaceCount(Integer workspaceCount) {
        this.workspaceCount = workspaceCount;
    }

    @Override
    public Integer getNetworkCount() {
        return networkCount;
    }

    @Override
    public void setNetworkCount(Integer networkCount) {
        this.networkCount = networkCount;
    }

    @Override
    public void incrementNetworkCount() {
        this.networkCount = this.getNetworkCount() + 1;
    }

}
