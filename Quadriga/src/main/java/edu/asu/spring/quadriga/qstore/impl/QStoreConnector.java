package edu.asu.spring.quadriga.qstore.impl;

import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.xml.MarshallingHttpMessageConverter;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.factories.IRestVelocityFactory;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaException;
import edu.asu.spring.quadriga.qstore.IQStoreConnector;
import edu.asu.spring.quadriga.velocity.impl.VelocityBuilder;

@Service
@PropertySource(value = "classpath:/settings.properties")
public class QStoreConnector implements IQStoreConnector {

    private static final Logger logger = LoggerFactory
            .getLogger(QStoreConnector.class);

    @Autowired
    @Qualifier("qStoreURL")
    private String qStoreURL;

    @Autowired
    @Qualifier("qStoreURL_Add")
    private String qStoreURL_Add;
    
    @Autowired
    @Qualifier("qStoreURL_Search")
    private String qStoreUrl_search;

    @Autowired
    @Qualifier("restTemplate")
    RestTemplate restTemplate;

    @Autowired
    @Qualifier("qStoreURL_Get_POST")
    private String qStoreURL_Get_POST;

    @Autowired
    @Qualifier("qStoreURL_Get")
    private String qStoreURL_Get;

    @Autowired
    @Qualifier("jaxbMarshaller")
    private Jaxb2Marshaller jaxbMarshaller;
    
    @Autowired
    private IRestVelocityFactory restVelocityFactory;
    
    // TODO: we need to replace all restVelocityFactory uses with this builder
    @Autowired
    private VelocityBuilder velocityBuilder;

    @Autowired
    private Environment env;

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.qstore.impl.IQStoreConnector#getQStoreAddURL()
     */
    @Override
    public String getQStoreAddURL() {
        return qStoreURL + qStoreURL_Add;
    }

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.qstore.impl.IQStoreConnector#getQStoreGetURL()
     */
    @Override
    public String getQStoreGetURL() {
        return qStoreURL + qStoreURL_Get;
    }

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.qstore.impl.IQStoreConnector#getQStoreGetPOSTURL()
     */
    @Override
    public String getQStoreGetPOSTURL() {
        return qStoreURL + qStoreURL_Get_POST;
    }
    
    protected String getQStoreSearchUrl() {
        return qStoreURL + qStoreUrl_search;
    }
    
    @PostConstruct
    public void init() {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        messageConverters.add(new StringHttpMessageConverter());
        messageConverters.add(new MarshallingHttpMessageConverter(
                jaxbMarshaller, jaxbMarshaller));

        restTemplate.setMessageConverters(messageConverters);
    }

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.qstore.impl.IQStoreConnector#getCreationEvent(java.lang.String)
     */
    @Override
    @Cacheable(value="creationEvent")
    public String getCreationEvent(String id) throws QStoreStorageException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        
        // set media types
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_XML);
        headers.setAccept(mediaTypes);
        
        String authHeader = getAuthHeader();
        headers.set("Authorization", authHeader);
        ResponseEntity<String> response = null;
        
        logger.debug("URL : " + getQStoreGetURL() + id);
        // Get the XML from QStore
        try {
            logger.debug("Requesting: " + getQStoreGetURL() + id);
            response = restTemplate
                .exchange(getQStoreGetURL() + id, HttpMethod.GET,
                        new HttpEntity<String[]>(headers), String.class);
        } catch (RestClientException ex) {
            throw new QStoreStorageException(ex);
        }

        return response.getBody().toString();
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.qstore.impl.IQStoreConnector#store(java.lang.String)
     */
    @Override
    public String store(String xml) throws QStoreStorageException {
        String res = "";
        // add message converters
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = buildRestHeader(messageConverters, restTemplate);
        HttpEntity<String> request = new HttpEntity<String>(xml, headers);

        try {
            // add xml in QStore
            String url = getQStoreAddURL();
            res = restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            throw new QStoreStorageException(e); 
        }
        return res;
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.service.qstore.impl.IQStoreConnector#getStatements(java.lang.String)
     */
    @Override
    @Cacheable(value="statements")
    public String getStatements(String xml) {
        String res = "";
        // Add message converters for JAxb
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = buildRestHeader(messageConverters, restTemplate);

        HttpEntity<String> request = new HttpEntity<String>(xml, headers);

        try {
            // Get complete network xml from QStore
            res = restTemplate.postForObject(getQStoreGetPOSTURL(), request, String.class);
        } catch (Exception e) {
            logger.error("QStore not accepting the xml, please check with the server logs.", e);
            // res = e.getMessage();
            return res;
        }
        return res;
    }
    
    @Override
    @Cacheable(value="appellationEvents")
    public String getAppellationEventsByConceptAndText(String conceptUri, String textUri) throws QuadrigaException {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = buildRestHeader(messageConverters, restTemplate);
        
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("conceptUri", conceptUri);
        props.put("textUri", textUri);
        
        String payload;
        try {
            payload = velocityBuilder.getRenderedTemplate("velocitytemplates/searchQStore/searchAppEvents.vm", props);
        } catch (ResourceNotFoundException e1) {
            throw new QuadrigaException(e1);
        } catch (ParseErrorException e1) {
            throw new QuadrigaException(e1);
        } catch (Exception e1) {
            // the velocity engine actually throws 'Exception'
            throw new QuadrigaException(e1);
        }
         
        HttpEntity<String> request = new HttpEntity<String>(payload, headers);

        String res = "";
        try {
            logger.debug("Search Qstore for " + conceptUri + " and " + textUri);
            res = restTemplate.postForObject(getQStoreSearchUrl(), request, String.class);
        } catch (Exception e) {
            throw new QStoreStorageException(e);
        }
        return res;
    }
    
    @Override
    @Cacheable(value="conceptSearch")
    public String searchNodesByConcept(String conceptId) throws Exception {
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = buildRestHeader(messageConverters, restTemplate);
        
        VelocityEngine engine = restVelocityFactory.getVelocityEngine();
        engine.init();
        Template template = engine.getTemplate("velocitytemplates/searchQStore/requestNodes.vm");
        VelocityContext context = new VelocityContext();
        context.put("conceptId", conceptId);
        
        StringWriter writer = new StringWriter();
        template.merge(context, writer);

        HttpEntity<String> request = new HttpEntity<String>(writer.toString(), headers);

        String res = "";
        try {
            // Get complete network xml from QStore
            res = restTemplate.postForObject(getQStoreSearchUrl(), request, String.class);
        } catch (Exception e) {
            logger.error("QStore not accepting the xml, please check with the server logs.", e);
            // res = e.getMessage();
            return res;
        }
        return res;
    }

    protected HttpHeaders buildRestHeader(List<HttpMessageConverter<?>> messageConverters, RestTemplate restTemplate) {
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        mediaTypes.add(MediaType.APPLICATION_XML);
        messageConverters.add(new FormHttpMessageConverter());
        messageConverters.add(new StringHttpMessageConverter());
        restTemplate.setMessageConverters(messageConverters);
        // Add http header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        headers.setAccept(mediaTypes);
        String authHeader = getAuthHeader();
        headers.set("Authorization", authHeader);
        return headers;
    }


    private String getAuthHeader() {
        String auth = env.getProperty("qstore.admin.username") + ":"
                + env.getProperty("qstore.admin.password");
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset
                .forName("US-ASCII")));
        return "Basic " + new String(encodedAuth);
    }
}
