package edu.asu.spring.quadriga.utilities;

import java.util.Map;
import java.util.Observer;

import edu.asu.spring.quadriga.exceptions.PropertiesStorageException;

public interface IPropertiesManager {
    public abstract void setProperty(String key, String value) throws PropertiesStorageException;

    public abstract String getProperty(String key);

    public abstract void updateProperties(Map<String, String> props)
            throws PropertiesStorageException;

    public void addObserver(Observer observer);

}
