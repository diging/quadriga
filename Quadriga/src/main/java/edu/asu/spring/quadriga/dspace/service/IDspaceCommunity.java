package edu.asu.spring.quadriga.dspace.service;


/**
 * The interface that provides access to the class representation of the community got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */

public interface IDspaceCommunity {

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

	public abstract IDspaceCollectionsIdList getCollectionsIDList();

	public abstract void setCollectionsIDList(IDspaceCollectionsIdList collections);

//	public abstract List<IDspaceCollection> getCollections();
//
//	public abstract void setCollections(List<IDspaceCollection> collections);
//
//	public abstract void addCollections(IDspaceCollection collection);
}
