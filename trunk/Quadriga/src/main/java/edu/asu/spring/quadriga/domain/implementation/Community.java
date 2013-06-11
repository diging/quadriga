package edu.asu.spring.quadriga.domain.implementation;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICollections;
import edu.asu.spring.quadriga.domain.ICommunity;

/**
 * The class representation of the community got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
@XmlRootElement(name="communities")
public class Community implements ICommunity{

	private String id;
	private String name;
	private String shortDescription;
	private String introductoryText;
	private String countItems;
	private String handle;	
	private String entityReference;
	private String entityId;
	private ICollections collections;
	
	@XmlElementRefs({@XmlElementRef(type=Collections.class)})
	@Override
	public ICollections getCollections() {
		return collections;
	}

	@Override
	public void setCollections(ICollections collections) {
		this.collections = collections;
	}
	
	@Override
	public String getEntityId() {
		return entityId;
	}

	@Override
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	@Override
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Override
	public String getIntroductoryText() {
		return introductoryText;
	}

	@Override
	public void setIntroductoryText(String introductoryText) {
		this.introductoryText = introductoryText;
	}

	@Override
	public String getCountItems() {
		return countItems;
	}

	@Override
	public void setCountItems(String countItems) {
		this.countItems = countItems;
	}

	@Override
	public String getHandle() {
		return handle;
	}

	@Override
	public void setHandle(String handle) {
		this.handle = handle;
	}

	@Override
	public String getEntityReference() {
		return entityReference;
	}

	@Override
	public void setEntityReference(String entityReference) {
		this.entityReference = entityReference;
	}

	@Override
	public String getDescription() {
		return this.shortDescription;
	}

	@Override
	public void setDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	public static class Adapter extends XmlAdapter<Community, ICommunity>
	{

		@Override
		public ICommunity unmarshal(Community v) throws Exception {
			return v;
		}

		@Override
		public Community marshal(ICommunity v) throws Exception {
			return (Community)v;
		}
		
	}
}
