package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.domain.implementation.Collection;
import edu.asu.spring.quadriga.domain.implementation.CollectionsIdList;

/**
 * The interface that provides access to the class representation of the community got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */

public interface ICommunity {

	public abstract String getName();
	
	public abstract void setName(String name);
	
	public abstract String getDescription();
	
	public abstract void setDescription(String description);

	public abstract String getId();

	public abstract void setId(String id);

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

	public abstract ICollectionsIdList getCollectionsIDList();

	public abstract void setCollectionsIDList(ICollectionsIdList collections);

//	public abstract List<ICollection> getCollections();
//
//	public abstract void setCollections(List<ICollection> collections);
//
//	public abstract void addCollections(ICollection collection);
}
