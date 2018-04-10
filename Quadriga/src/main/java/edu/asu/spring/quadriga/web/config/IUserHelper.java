package edu.asu.spring.quadriga.web.config;

import org.springframework.social.connect.Connection;

import edu.asu.spring.quadriga.domain.impl.User;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;


public interface IUserHelper {

    public abstract String createUserName(Connection<?> connection) throws QuadrigaStorageException;
    public abstract User createUser(String userName, Connection<?> connection);

}