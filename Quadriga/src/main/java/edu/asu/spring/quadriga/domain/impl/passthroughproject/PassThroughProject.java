package edu.asu.spring.quadriga.domain.impl.passthroughproject;

import edu.asu.spring.quadriga.domain.impl.workbench.Project;
import edu.asu.spring.quadriga.domain.passthroughproject.IPassThroughProject;

/**
 * @description PassThroughProject class describing the properties of a
 *              PassThroughProject object
 *
 */
public class PassThroughProject extends Project implements IPassThroughProject {
    private String externalUserId;

    private String externalUserName;

    private String client;

    private String externalProjectid;

    @Override
    public String getExternalUserId() {
        return externalUserId;
    }

    @Override
    public void setExternalUserId(String externalUserId) {
        this.externalUserId = externalUserId;
    }

    @Override
    public String getExternalUserName() {
        return externalUserName;
    }

    @Override
    public void setExternalUserName(String externalUserName) {
        this.externalUserName = externalUserName;
    }

    @Override
    public String getClient() {
        return client;
    }

    @Override
    public void setClient(String client) {
        this.client = client;
    }

    @Override
    public String getExternalProjectid() {
        return externalProjectid;
    }

    @Override
    public void setExternalProjectid(String externalProjectid) {
        this.externalProjectid = externalProjectid;
    }
}
