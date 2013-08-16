package edu.asu.spring.quadriga.domain;

import java.util.List;

import edu.asu.spring.quadriga.dspace.service.IDspaceCollection;

/**
 * The collection interface used by Quadriga to provide access to collection information collected from Dspace.
 * Its representation is independent of the Dspace Rest service output.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface ICollection extends Runnable{

	public abstract String getId();

	public abstract void setId(String id);

	public abstract String getName();

	public abstract void setName(String name);

	public abstract String getShortDescription();

	public abstract void setShortDescription(String shortDescription);

	public abstract String getEntityReference();

	public abstract void setEntityReference(String entityReference);

	public abstract String getHandle();

	public abstract void setHandle(String handle);

	public abstract String getCountItems();

	public abstract void setCountItems(String countItems);

	/**
	 * Copy the collection details from a DspaceCollection class to this object. This will also load all the dependent items in the collection.  
	 * The dspaceCollection object should not be null.
	 * 
	 * @param dspaceCollection 		The object containing the data about the collection fetched from Dspace.
	 * @return 						TRUE if the data was copied successfully. FALSE if the dspaceCollection is null or there was error in copying the data.
	 */
	public abstract boolean copy(IDspaceCollection dspaceCollection);

	public abstract List<IItem> getItems();

	public abstract void setItems(List<IItem> items);

	public abstract boolean getLoadStatus();

	public abstract IItem getItem(String itemid);

}