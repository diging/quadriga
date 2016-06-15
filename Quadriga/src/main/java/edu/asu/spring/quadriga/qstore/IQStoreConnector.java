package edu.asu.spring.quadriga.qstore;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.impl.networks.RelationEventType;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;

public interface IQStoreConnector {

    public abstract String getQStoreAddURL();

    public abstract String getQStoreGetURL();

    public abstract String getQStoreGetPOSTURL();

    public abstract String getCreationEvent(String id)
            throws QStoreStorageException;

    public abstract String store(String xml) throws QStoreStorageException;

    /**
     * 
     * {@inheritDoc} QStore allows us to get network XML for specific
     * {@link RelationEventType} and also List of {@link RelationEventType}
     * embedded in XML.
     * 
     */
    public abstract String getStatements(String xml);

    public abstract String searchNodesByConcept(String conceptId) throws Exception;

}