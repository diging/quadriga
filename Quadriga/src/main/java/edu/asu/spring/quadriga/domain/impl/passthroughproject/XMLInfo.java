package edu.asu.spring.quadriga.domain.impl.passthroughproject;

/**
 * 
 * This class holds all the information that is extracted from the xml file
 * submitted to Pass through project rest api.
 *
 */
public class XMLInfo {
    private String networkName;
    private String projectId;
    private String externalUserName;

    private String externalUserId;

    private String name;

    private String description;
    private String sender;
    private String workspaceName;
    private String externalWorkspaceId;

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String externalProjectId) {
        this.projectId = externalProjectId;
    }

    public String getExternalUserName() {
        return externalUserName;
    }

    public void setExternalUserName(String externalUserName) {
        this.externalUserName = externalUserName;
    }

    public String getExternalUserId() {
        return externalUserId;
    }

    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public String getExternalWorkspaceId() {
        return externalWorkspaceId;
    }

    public void setExternalWorkspaceId(String externalWorkspaceId) {
        this.externalWorkspaceId = externalWorkspaceId;
    }

}
