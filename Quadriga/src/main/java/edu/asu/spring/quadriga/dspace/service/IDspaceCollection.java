package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

/**
 * The interface that provides access to the class representation of the Collection got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IDspaceCollection {

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
	
}