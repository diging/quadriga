package edu.asu.spring.quadriga.dspace.service;


public interface IDspaceMetadataBitStream {

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getSize();

	public abstract void setSize(String size);

	public abstract String getCheckSum();

	public abstract void setCheckSum(String checkSum);

//	public abstract IDspaceMetadataBundles getBundles();
//
//	public abstract void setBundles(IDspaceMetadataBundles bundles);

}