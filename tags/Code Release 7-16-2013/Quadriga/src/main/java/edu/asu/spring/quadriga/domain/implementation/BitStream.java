package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.IBitStream;

/**
 * The class representation of the ByteStream got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 */
public class BitStream implements IBitStream{

	private String id;
	private String name;
	private String size;
	private String mimeType;
	private String communityName;
	private String collectionName;
	private String itemName;
	
	@Override
	public String getCommunityName() {
		return communityName;
	}

	@Override
	public void setCommunityName(String communityName) {
		this.communityName = communityName;
	}

	@Override
	public String getCollectionName() {
		return collectionName;
	}

	@Override
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	@Override
	public String getItemName() {
		return itemName;
	}

	@Override
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getMimeType() {
		return mimeType;
	}

	@Override
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;		
	}

	@Override
	public String getSize() {
		return this.size;
	}

	@Override
	public void setSize(String size) {
		this.size = size;
	}

}
