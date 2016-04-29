package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

public interface IDspaceMetadataCollection {

	public abstract List<IDspaceMetadataCollectionEntity> getCollectionEntitites();

	public abstract void setCollectionEntitites(
			List<IDspaceMetadataCollectionEntity> collectionEntitites);

}