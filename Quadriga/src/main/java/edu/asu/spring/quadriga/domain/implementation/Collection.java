package edu.asu.spring.quadriga.domain.implementation;

import edu.asu.spring.quadriga.domain.ICollection;
import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;

/**
 * The class representation of the Collection got from Dspace repostiory.
 * This class will be used by Quadriga and its representation is independent of the Dspace Rest service output
 * 
 * @author Ram Kumar Kumaresan
 */


public class Collection implements ICollection{

	private String id;
	private String name;
	private String shortDescription;
	private String entityReference;
	private String handle;
	private String countItems;
	private boolean isLoaded;
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#getId()
	 */
	@Override
	public String getId() {
		return id;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}
	
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#getShortDescription()
	 */
	@Override
	public String getShortDescription() {
		return shortDescription;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#setShortDescription(java.lang.String)
	 */
	@Override
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#getEntityReference()
	 */
	@Override
	public String getEntityReference() {
		return entityReference;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#setEntityReference(java.lang.String)
	 */
	@Override
	public void setEntityReference(String entityReference) {
		this.entityReference = entityReference;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#getHandle()
	 */
	@Override
	public String getHandle() {
		return handle;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#setHandle(java.lang.String)
	 */
	@Override
	public void setHandle(String handle) {
		this.handle = handle;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#getCountItems()
	 */
	@Override
	public String getCountItems() {
		return countItems;
	}
	
	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#setCountItems(java.lang.String)
	 */
	@Override
	public void setCountItems(String countItems) {
		this.countItems = countItems;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#isLoaded()
	 */
	@Override
	public boolean isLoaded() {
		return isLoaded;
	}

	/* (non-Javadoc)
	 * @see edu.asu.spring.quadriga.domain.implementation.ICollection#setLoaded(boolean)
	 */
	@Override
	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}	
	
	@Override
	public boolean copy(IDspaceCollection dspaceCollection)
	{		
		if(dspaceCollection != null)
		{
			if(dspaceCollection.getId() != null)
			{
				this.id = dspaceCollection.getId();
			}
			
			if(dspaceCollection.getName() != null)
			{
				this.name = dspaceCollection.getName();
				this.isLoaded = true;
			}
			
			if(dspaceCollection.getShortDescription() != null)
			{
				this.shortDescription = dspaceCollection.getShortDescription();
			}
			
			if(dspaceCollection.getEntityReference() != null)
			{
				this.entityReference = dspaceCollection.getEntityReference();
			}
			
			if(dspaceCollection.getHandle() != null)
			{
				this.handle = dspaceCollection.getHandle();
			}
			
			if(dspaceCollection.getCountItems() != null)
			{
				this.countItems = dspaceCollection.getCountItems();
			}			
			
			return this.isLoaded;
		}
		return false;
	}
}
