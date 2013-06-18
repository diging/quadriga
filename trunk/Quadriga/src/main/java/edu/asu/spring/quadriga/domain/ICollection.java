package edu.asu.spring.quadriga.domain;

import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;

public interface ICollection {

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

	public abstract boolean isLoaded();

	public abstract void setLoaded(boolean isLoaded);

	public abstract boolean copy(IDspaceCollection dspaceCollection);

}