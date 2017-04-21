package edu.asu.spring.quadriga.web.config;

import org.springframework.social.connect.Connection;

import edu.asu.spring.quadriga.domain.impl.User;


public interface IUserHelper {

    public abstract User createUser(Connection<?> connection);

}