package edu.asu.spring.quadriga.domain;

import java.util.List;
import java.util.Properties;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.dspace.service.IDspaceItem;

/**
 * The interface that provides access to the class representation of the Item got from Dspace repostiory.
 * Its representation is independent of the Dspace Rest service output. It is also responsible for making
 * REST service call to dspace and loading the related bitstreams if and when needed.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IItem  extends Runnable{

	public abstract void setHandle(String handle);

	public abstract String getHandle();

	public abstract void setId(String id);

	public abstract String getId();

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setBitids(List<String> bitids);

	public abstract List<String> getBitids();

	public abstract void setBitstreams(List<IBitStream> bitstreams);

	/**
	 * This method will return all the bitstreams associated with this item. Inorder to connect to Dspace, 
	 * the {@link #setRestConnectionDetails(RestTemplate, Properties, String, String)} method
	 * should be first used to set all the required connection varibales. If the bitstreams were already loaded, 
	 * this method will just return the bitstreams. If not, this method will start a thread to load the bitstreams 
	 * and return the bitstream references.   
	 * 
	 * @return 	Contains the list of bitstreams. Bitstream names will be null if the bitstream data was not yet fetched from dspace.
	 */
	public abstract List<IBitStream> getBitstreams();

	public abstract void addBitstream(IBitStream bitstream);

	/**
	 * Initialize the required connection details to make a REST service call to Dspace. All the values are needed to be set if the related
	 * Bitstreams are to be loaded. This is the first method to be used when trying to load bitstreams.
	 * 
	 * @param restTemplate			The RestTemplate object containing the details about the parser.
	 * @param dspaceProperties		The property strings related to dspace REST service connection.
	 * @param userName				The username of the authorized user.
	 * @param password				The password of the authorized user.
	 */
	public abstract void setRestConnectionDetails(RestTemplate restTemplate, Properties dspaceProperties,
			String userName, String password);
	
	/**
	 * Copy the information retrieved from dspace into the this item object. The dspaceItem object should not be null.
	 * @param dspaceItem	The object containing the data about the item fetched from Dspace.
	 * @return				TRUE if the data was copied successfully. FALSE if the dspaceItem is null or there was error in copying the data.
	 */
	public abstract boolean copy(IDspaceItem dspaceItem);
	
}
