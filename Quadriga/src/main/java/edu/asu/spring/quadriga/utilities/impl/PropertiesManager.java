package edu.asu.spring.quadriga.utilities.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Observable;
import java.util.Properties;

import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.PathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import edu.asu.spring.quadriga.exceptions.PropertiesStorageException;
import edu.asu.spring.quadriga.utilities.IPropertiesManager;


@PropertySource("classpath:/social.properties")
@Service
public class PropertiesManager extends Observable implements IPropertiesManager {
    

    @Autowired
    private Environment env;
    
    private PropertiesPersister persister;
    private Properties properties;
    private PathResource customPropsResource;

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
        String value = properties.getProperty(key);
        if (value != null) {
            return value.trim();
        }
        
        value = env.getProperty(key);
        if (value != null) {
            value = value.trim();
        }
        return value; 
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
            persister.store(properties, customPropsResource.getOutputStream(), "Quadriga custom properties.");
        } catch (IOException e) {
            throw new PropertiesStorageException("Could not store properties.", e);
        }
    }
}