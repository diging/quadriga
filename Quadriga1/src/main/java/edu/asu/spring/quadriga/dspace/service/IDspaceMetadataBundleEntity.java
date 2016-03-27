package edu.asu.spring.quadriga.dspace.service;


public interface IDspaceMetadataBundleEntity {

	public abstract String getName();

	public abstract void setName(String name);

	public abstract IDspaceMetadataItems getItems();

	public abstract void setItems(IDspaceMetadataItems items);

}