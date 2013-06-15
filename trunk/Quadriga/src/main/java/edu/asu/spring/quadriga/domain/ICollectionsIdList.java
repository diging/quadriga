package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.domain.implementation.Collection;

public interface ICollectionsIdList {

	public abstract List<ICollectionEntityId> getCollectionid();

	public abstract void setCollectionid(List<ICollectionEntityId> collectionid);



}
