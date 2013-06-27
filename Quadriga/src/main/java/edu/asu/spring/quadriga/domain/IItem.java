package edu.asu.spring.quadriga.domain;

import java.util.Date;
import java.util.List;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.dspace.service.IDspaceItem;

/**
 * The interface that provides access to the class representation of the Item got from Dspace repostiory.
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

	public abstract boolean copy(IDspaceItem dspaceItem);

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

	public abstract void setRestConnectionDetails(RestTemplate restTemplate, String url,
			String userName, String password);
	
}
