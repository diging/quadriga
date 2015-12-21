package edu.asu.spring.quadriga.authentication.qstore.impl;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.authentication.UserAuthDetails;
import edu.asu.spring.quadriga.authentication.qstore.IQStoreUserManager;

@Service
public class QStoreUserManager implements IQStoreUserManager {

    private UserAuthDetails users;

    @PostConstruct
    public void loadUsers() throws IOException {
        users = new UserAuthDetails();

        Resource resource = new ClassPathResource("/user.properties");
        Properties props = PropertiesLoaderUtils.loadProperties(resource);

        for (String username : props.stringPropertyNames()) {
            users.setUsername(username);
            users.setPassword(props.getProperty(username));
        }
    }

    @Override
    public UserAuthDetails getQStoreUser() {
        return users;
    }
}
