package edu.asu.spring.quadriga.authentication.qstore;

import edu.asu.spring.quadriga.authentication.UserAuthDetails;

public interface IQStoreUserManager {
    public UserAuthDetails getQStoreUser();
}
