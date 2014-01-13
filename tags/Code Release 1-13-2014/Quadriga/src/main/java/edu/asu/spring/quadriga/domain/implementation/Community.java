package edu.asu.spring.quadriga.domain.implementation;


import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollectionEntityId;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;
/**
 * The class representation of the community got from Dspace repostiory.
 * This class will be used by Quadriga and its representation is independent of the Dspace Rest service output.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public class Community implements ICommunity{

	private String id;
	private String name;
	private String shortDescription;
	private String introductoryText;
	private String countItems;
	private String handle;	
	private String entityReference;
	private String entityId;
	private List<String> collectionIds;
	private List<ICollection> collections;
	
	@Override
	public String getId() {
		return id;
	}
	
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
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
	public String getEntityId() {
		return entityId;
	}
	
	@Override
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	
	@Override
	public List<ICollection> getCollections() {
		return collections;
	}
	
	@Override
	public void setCollections(List<ICollection> collections) {
		this.collections = collections;
	}
	
	@Override
	public void clearCollections()
	{
		this.collections.clear();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCollection(ICollection collection) {
		if(this.collections == null)
			this.collections = new ArrayList<ICollection>();
		this.collections.add(collection);	
	}
	
	@Override
	public List<String> getCollectionIds() {
		return collectionIds;
	}
	
	@Override
	public void setCollectionIds(List<String> collectionIds) {
		this.collectionIds = collectionIds;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean copy(IDspaceCommunity dspaceCommunity)
	{
		if(dspaceCommunity != null)
		{
			if(dspaceCommunity.getId() != null)
			{
				this.id =dspaceCommunity.getId();
			}
			
			if(dspaceCommunity.getName() != null)
			{
				this.name =dspaceCommunity.getName();
			}
			
			if(dspaceCommunity.getDescription() != null)
			{
				this.shortDescription = dspaceCommunity.getDescription();
			}
			
			if(dspaceCommunity.getIntroductoryText() != null)
			{
				this.introductoryText = dspaceCommunity.getIntroductoryText();
			}
			
			if(dspaceCommunity.getHandle() != null)
			{
				this.handle = dspaceCommunity.getHandle();
			}
			
			//Add all the collection ids from the Dspace community object
			if(dspaceCommunity.getCollectionsIDList().getCollectionid().size()>0)
			{
				this.collectionIds = new ArrayList<String>();
				this.collections = new ArrayList<ICollection>();
				
				for(IDspaceCollectionEntityId collectionid : dspaceCommunity.getCollectionsIDList().getCollectionid())
				{
					this.collectionIds.add(collectionid.getId());
				}
			}
			
			if(dspaceCommunity.getEntityId() != null)
			{
				this.entityId = dspaceCommunity.getEntityId();
			}
			
			if(dspaceCommunity.getEntityReference() != null)
			{
				this.entityReference = dspaceCommunity.getEntityReference();
			}
			
			if(dspaceCommunity.getCountItems() != null )
			{
				this.countItems = dspaceCommunity.getCountItems();
			}
			
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICollection getCollectionById(String sCollectionId)
	{
		for(ICollection collection: this.collections)
		{
			if(collection.getId().equals(sCollectionId))
			{
				return collection;
			}
		}		
		return null;
	}
	
}
