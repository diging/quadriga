package edu.asu.spring.quadriga.conceptpower.impl;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.conceptpower.IConceptpowerConnector;
import edu.asu.spring.quadriga.domain.impl.ConceptpowerReply;

/**
 * This class provides functionality to search Conceptpower.
 * @author Julia Damerow
 *
 */
@Service
public class ConceptpowerConnector implements IConceptpowerConnector {

    @Inject
    @Named("restTemplate")
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("conceptPowerURL")
    private String conceptURL;

    @Autowired
    @Qualifier("searchConceptPowerURLPath")
    private String searchURL;

    @Autowired
    @Qualifier("updateConceptPowerURLPath")
    private String idUrl;

    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.impl.IConceptpowerConnector#search(java.lang.String, java.lang.String)
     */
    @Override
    public ConceptpowerReply search(String item, String pos) {
        Map<String, String> vars = new HashMap<String, String>();
        vars.put("name", item);
        vars.put("pos", pos);

        return restTemplate.getForObject(conceptURL + searchURL
                + "{name}/{pos}", ConceptpowerReply.class, vars);
    }
    
    /* (non-Javadoc)
     * @see edu.asu.spring.quadriga.conceptpower.impl.IConceptpowerConnector#getById(java.lang.String)
     */
    @Override
    public ConceptpowerReply getById(String id) {
        Map<String, String> vars = new HashMap<String, String>();
        //vars.put("name", id);
        String url = conceptURL + idUrl + id.trim();
        String test = restTemplate.getForObject(url, String.class, vars);

        return restTemplate.getForObject(url, ConceptpowerReply.class, vars);
    }
}
