package edu.asu.spring.quadriga.domain;

import java.util.List;

import org.springframework.web.client.RestTemplate;

import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;

public interface ICollection extends Runnable{

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getShortDescription();

	public abstract void setShortDescription(String shortDescription);

	public abstract String getEntityReference();

	public abstract void setEntityReference(String entityReference);

	public abstract String getHandle();

	public abstract void setHandle(String handle);

	public abstract String getCountItems();

	public abstract void setCountItems(String countItems);

	public abstract boolean copy(IDspaceCollection dspaceCollection);

	public abstract void setPassword(String password);

	public abstract String getPassword();

	public abstract void setUserName(String userName);

	public abstract String getUserName();

	public abstract void setRestTemplate(RestTemplate restTemplate);

	public abstract RestTemplate getRestTemplate();

	public abstract List<IItem> getItems();

	public abstract void setItems(List<IItem> items);

}