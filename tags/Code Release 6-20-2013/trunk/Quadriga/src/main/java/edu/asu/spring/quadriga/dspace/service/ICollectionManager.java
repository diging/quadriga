package edu.asu.spring.quadriga.dspace.service;

import org.springframework.web.client.RestTemplate;
import edu.asu.spring.quadriga.domain.ICollection;


public interface ICollectionManager {

	public abstract ICollection testRestGET(RestTemplate restTemplate, String url, String sUserName, String sPassword);

}