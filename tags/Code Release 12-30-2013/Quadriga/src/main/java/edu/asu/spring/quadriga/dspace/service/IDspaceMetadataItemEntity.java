package edu.asu.spring.quadriga.dspace.service;

public interface IDspaceMetadataItemEntity {

	public abstract void setName(String name);

	public abstract String getName();

	public abstract void setHandle(String handle);

	public abstract String getHandle();

	public abstract void setId(String id);

	public abstract String getId();

	public abstract IDspaceMetadataCollection getCollections();

	public abstract void setCollections(IDspaceMetadataCollection collections);

	public abstract IDspaceMetadataCommunity getCommunities();

	public abstract void setCommunities(IDspaceMetadataCommunity communities);

}
