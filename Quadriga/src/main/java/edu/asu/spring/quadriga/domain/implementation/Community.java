package edu.asu.spring.quadriga.domain.implementation;


import java.util.ArrayList;
import java.util.List;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.domain.ICommunity;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollectionEntityId;
import edu.asu.spring.quadriga.dspace.service.IDspaceCommunity;
/**
 * The class representation of the community got from Dspace repostiory.
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
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#getShortDescription()
	 */
	@Override
	public String getShortDescription() {
		return shortDescription;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#setShortDescription(java.lang.String)
	 */
	@Override
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#getIntroductoryText()
	 */
	@Override
	public String getIntroductoryText() {
		return introductoryText;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#setIntroductoryText(java.lang.String)
	 */
	@Override
	public void setIntroductoryText(String introductoryText) {
		this.introductoryText = introductoryText;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#getCountItems()
	 */
	@Override
	public String getCountItems() {
		return countItems;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#setCountItems(java.lang.String)
	 */
	@Override
	public void setCountItems(String countItems) {
		this.countItems = countItems;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#getHandle()
	 */
	@Override
	public String getHandle() {
		return handle;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#setHandle(java.lang.String)
	 */
	@Override
	public void setHandle(String handle) {
		this.handle = handle;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#getEntityReference()
	 */
	@Override
	public String getEntityReference() {
		return entityReference;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#setEntityReference(java.lang.String)
	 */
	@Override
	public void setEntityReference(String entityReference) {
		this.entityReference = entityReference;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#getEntityId()
	 */
	@Override
	public String getEntityId() {
		return entityId;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#setEntityId(java.lang.String)
	 */
	@Override
	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#getCollections()
	 */
	@Override
	public List<ICollection> getCollections() {
		return collections;
	}
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICommunity#setCollections(java.util.List)
	 */
	@Override
	public void setCollections(List<ICollection> collections) {
		this.collections = collections;
	}
	
	@Override
	public void addCollection(ICollection collection) {
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
