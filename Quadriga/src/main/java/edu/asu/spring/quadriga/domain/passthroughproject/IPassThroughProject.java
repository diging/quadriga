package edu.asu.spring.quadriga.domain.passthroughproject;

import edu.asu.spring.quadriga.domain.workbench.IProject;

public interface IPassThroughProject extends IProject {

    String getExternalUserId();

    void setExternalUserId(String externalUserId);

    String getExternalUserName();

    void setExternalUserName(String externalUserName);

    String getClient();

    void setClient(String client);

    String getExternalProjectid();

    void setExternalProjectid(String externalProjectid);

}
