package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;


/**
 * The community interface used by Quadriga to provide access to community information collected from Dspace.
 * Its representation is independent of the Dspace Rest service output.
 * 
 * @author Ram Kumar Kumaresan
 */
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
	
	/**
	 * Add a collection to the community
	 * @param collection The collection to be added to the collection. Data must be already filled in the collection object.
	 */
	public abstract void addCollection(ICollection collection);

	public abstract List<String> getCollectionIds();

	public abstract void setCollectionIds(List<String> collectionIds);

	/**
	 * Copy the community details from a DspaceCommunity class to this object. The dspaceCommunity object should not be null.
	 * @param dspaceCommunity The object containing the data about the community fetched from Dspace.
	 * @return TRUE if the data was copied successfully. FALSE if the dspaceCommunity is null or there was error in copying the data.
	 */
	public abstract boolean copy(IDspaceCommunity dspaceCommunity);

	/**
	 * Retrieve the collection object for the given collectionid.
	 * @param sCollectionId The collection id of the collection to be retrieved
	 * @return The collection object corresponding to the collectionid. Will return NULL if the community does not contain a collection with the id.
	 */
	public abstract ICollection getCollectionById(String sCollectionId);

	public abstract void clearCollections();

}