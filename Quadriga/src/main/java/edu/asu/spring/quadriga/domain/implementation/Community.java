package edu.asu.spring.quadriga.domain.implementation;


import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICollectionsIdList;
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
	private ICollectionsIdList collectionsIDList;
//	private List<ICollection> collectionsList;
	

	@XmlElementRefs({@XmlElementRef(type=CollectionsIdList.class)})
	@Override
	public ICollectionsIdList getCollectionsIDList() {
		return collectionsIDList;
	}

	@Override
	public void setCollectionsIDList(ICollectionsIdList collectionsIDList) {
		this.collectionsIDList = collectionsIDList;
	}
	
//	@XmlElementRefs({@XmlElementRef(type=Collection.class)})
//	@Override
//	public List<ICollection> getCollections() {
//		return collectionsList;
//	}
//
//	@Override
//	public void setCollections(List<ICollection> collections) {
//		this.collectionsList = collections;
//	}
//	
//	@Override
//	public void addCollections(ICollection collection) {
//		this.collectionsList.add(collection);
//	}
	
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
