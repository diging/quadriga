package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

/**
 * The interface that provides access to the class representation of the collection id list got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public interface IDspaceCollectionsIdList {

	public abstract List<IDspaceCollectionEntityId> getCollectionid();

	public abstract void setCollectionid(List<IDspaceCollectionEntityId> collectionid);



}
