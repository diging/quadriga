package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;


public interface ICommunity {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getShortDescription();

	public abstract void setShortDescription(String shortDescription);

	public abstract String getIntroductoryText();

	public abstract void setIntroductoryText(String introductoryText);

	public abstract String getCountItems();

	public abstract void setCountItems(String countItems);

	public abstract String getHandle();

	public abstract void setHandle(String handle);

	public abstract String getEntityReference();

	public abstract void setEntityReference(String entityReference);

	public abstract String getEntityId();

	public abstract void setEntityId(String entityId);

	public abstract List<ICollection> getCollections();

	public abstract void setCollections(List<ICollection> collections);
	
	public abstract void addCollection(ICollection collection);

	public abstract List<String> getCollectionIds();

	public abstract void setCollectionIds(List<String> collectionIds);

	public abstract boolean copy(IDspaceCommunity dspaceCommunity);

	public abstract ICollection getCollectionById(String sCollectionId);

}