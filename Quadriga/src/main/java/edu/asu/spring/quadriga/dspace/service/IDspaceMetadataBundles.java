package edu.asu.spring.quadriga.dspace.service;

import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;

public interface IDspaceMetadataBundles {

	public abstract IDspaceMetadataBundleEntity getBundleEntity();

	public abstract void setBundleEntity(IDspaceMetadataBundleEntity bundleEntity);

}