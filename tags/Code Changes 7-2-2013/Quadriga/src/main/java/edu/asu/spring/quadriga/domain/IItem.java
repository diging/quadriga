package edu.asu.spring.quadriga.domain;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.dspace.service.IDspaceItem;
import edu.asu.spring.quadriga.dspace.service.impl.DspaceItem;

/**
 * The interface that provides access to the class representation of the Item got from Dspace repostiory.
 * Its representation is independent of the Dspace Rest service output.
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

	public abstract List<IBitStream> getBitstreams();

	public abstract void addBitstream(IBitStream bitstream);

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setUserName(String userName);

	public abstract String getUserName();

	public abstract void setUrl(String url);

	public abstract String getUrl();

	public abstract void setRestTemplate(RestTemplate restTemplate);

	public abstract RestTemplate getRestTemplate();

	/**
	 * Initialize the required details to make a REST service call to Dspace
	 * @param restTemplate		The RestTemplate object containing the details about the parser.
	 * @param url				The REST service url/domain.
	 * @param userName			The username of the authorized user.
	 * @param password			The password of the authorized user.
	 */
	public abstract void setRestConnectionDetails(RestTemplate restTemplate, String url,
			String userName, String password);
	
	/**
	 * Copy the information retrieved from dspace into the this item object. The dspaceItem object should not be null
	 * @param dspaceItem	The object containing the data about the item fetched from Dspace.
	 * @return				TRUE if the data was copied successfully. FALSE if the dspaceItem is null or there was error in copying the data.
	 */
	public abstract boolean copy(IDspaceItem dspaceItem);
	
}
