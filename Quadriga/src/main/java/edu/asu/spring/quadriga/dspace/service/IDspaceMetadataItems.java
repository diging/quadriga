package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

public interface IDspaceMetadataItems {

	public abstract void setItementities(List<IDspaceMetadataItemEntity> itementities);

	public abstract List<IDspaceMetadataItemEntity> getItementities();

}
