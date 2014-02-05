package edu.asu.spring.quadriga.domain;

/**
 * The interface that provides access to the class representation of the ByteStream got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IBitStream extends Runnable{

	public abstract String getName();

	public abstract void setName(String name);
	
	public abstract String getSize();
	
	public abstract void setSize(String size);

	public abstract void setMimeType(String mimeType);

	public abstract String getMimeType();

	public abstract void setId(String id);

	public abstract String getId();

	public abstract void setItemName(String itemName);

	public abstract String getItemName();

	public abstract void setCollectionName(String collectionName);

	public abstract String getCollectionName();

	public abstract void setCommunityName(String communityName);

	public abstract String getCommunityName();

	public abstract void setItemid(String itemid);

	public abstract String getItemid();

	public abstract void setCollectionid(String collectionid);

	public abstract String getCollectionid();

	public abstract void setCommunityid(String communityid);

	public abstract String getCommunityid();

	public abstract boolean getLoadStatus();

	public abstract void setLoadStatus(boolean isloaded);

}
