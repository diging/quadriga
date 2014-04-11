package edu.asu.spring.quadriga.domain;

import java.util.List;

/**
 * The interface that provides access to the class representation of the ByteStream got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IBitStream extends Runnable{

	public String getItemHandle();
	public void setItemHandle(String handle);
	
	public abstract String getName();

	public abstract void setName(String name);
	
	public abstract String getSize();
	
	public abstract void setSize(String size);

	public abstract void setMimeType(String mimeType);

	public abstract String getMimeType();

	public abstract void setId(String id);

	public abstract String getId();

	public abstract boolean getLoadStatus();

	public abstract void setLoadStatus(boolean isloaded);

	public abstract void setCollectionIds(List<String> collectionIds);

	public abstract List<String> getCollectionIds();

	public abstract void setCommunityIds(List<String> communityIds);

	public abstract List<String> getCommunityIds();

	public abstract void setItemName(String itemName);

	public abstract String getItemName();

	public abstract void addCommunityId(String communityId);

	public abstract void addCollectionId(String collectionId);

}
