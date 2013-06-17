package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

import edu.asu.spring.quadriga.dspace.service.impl.DspaceCollection;

public interface IDspaceCollectionsIdList {

	public abstract List<IDspaceCollectionEntityId> getCollectionid();

	public abstract void setCollectionid(List<IDspaceCollectionEntityId> collectionid);



}
