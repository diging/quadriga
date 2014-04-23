package edu.asu.spring.quadriga.domain.factories;

import java.util.Properties;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.domain.dspace.ICollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceKeys;

/**
 * Factory interface for Collection factories.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface ICollectionFactory {

	/**
	 * Create a collection object with all fields empty and isloaded set to false
	 * @return An empty collection object
	 */
	public abstract ICollection createCollectionObject();

	/**
	 * Create a collection object and initialize the required details to make a REST service call to Dspace
	 * 
	 * @param id				The id of the collection.
	 * @param restTemplate		The RestTemplate object containing the details about the parser.
	 * @param dspaceProperties	The property strings related to dspace REST service connection.
	 * @param dspaceKeys		The Dspace Access keys used by the user.
	 * @param userName			The username of the authorized user.
	 * @param password			The password of the authorized user.
	 */
	public abstract ICollection createCollectionObject(String collectionid, String communityid,
			RestTemplate restTemplate, Properties dspaceProperties,
			IDspaceKeys dspaceKeys, String userName, String password);

}