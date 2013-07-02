package edu.asu.spring.quadriga.dspace.service;

/**
 * The interface that provides access to the class representation of the collection id got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public interface IDspaceCollectionEntityId {

	public abstract String getId();

	public abstract void setId(String id);

}