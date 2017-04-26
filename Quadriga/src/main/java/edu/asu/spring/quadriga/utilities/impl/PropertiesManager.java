package edu.asu.spring.quadriga.utilities.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import edu.asu.spring.quadriga.exceptions.PropertiesStorageException;
import edu.asu.spring.quadriga.utilities.IPropertiesManager;

@Configuration
//@PropertySource("classpath:/social.properties")
@Service
public class PropertiesManager extends Observable implements IPropertiesManager {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;
    
    private PropertiesPersister persister;
    private Properties properties;
    private PathResource customPropsResource;
    @Value("${github_client_id}")
    private String githubClientId;
    @Value("${github_secret}")
    private String githubSecret;
    
    @PostConstruct
    public void init() throws IOException, URISyntaxException {
        persister = new DefaultPropertiesPersister();
        properties = new Properties();
        
        URL resURL = getClass().getResource("/custom.properties");
        customPropsResource = new PathResource(resURL.toURI());
        
        persister.load(properties, customPropsResource.getInputStream());      
    }
    
    @Override
    public String getProperty(String key) {
      /*  String value = properties.getProperty(key);
        System.out.println("key: "+key+", value: "+value);
        if (value != null) {
            return value.trim();
        }
        
        value = env.getProperty(key);
        if (value != null) {
            value = value.trim();
        }
        return value; */
        if(("github_client_id").equals(key)){
            System.out.println("key: "+"github_client_id"+", value: "+githubClientId);
            return githubClientId;
        }
            
        else if (("github_secret").equals(key)){
            System.out.println("key: "+"github_secret"+", value: "+githubSecret);
            return githubSecret;
        }
            
        
        return null;
    }
    
    @Override
    public void setProperty(String key, String value) throws PropertiesStorageException {
        properties.setProperty(key, value);
        saveProperties();
        setChanged();
        notifyObservers(key);
    }
    
    @Override
    public void updateProperties(Map<String, String> props) throws PropertiesStorageException {
        for (String key : props.keySet()) {
            properties.setProperty(key, props.get(key));
        }
        saveProperties();
        setChanged();
        notifyObservers(props);
    }
    
    protected void saveProperties() throws PropertiesStorageException {
        try {
            persister.store(properties, customPropsResource.getOutputStream(), "Giles custom properties.");
        } catch (IOException e) {
            throw new PropertiesStorageException("Could not store properties.", e);
        }
    }
}