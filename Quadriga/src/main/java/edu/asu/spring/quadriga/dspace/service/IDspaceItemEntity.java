package edu.asu.spring.quadriga.dspace.service;

import java.util.List;

/**
 * The interface that provides access to the class representation of the items list got from Dspace repostiory.
 * 
 * @author Ram Kumar Kumaresan
 * 
 */
public interface IDspaceItemEntity {

	public abstract List<IDspaceItem> getItems();

	public abstract void setItems(List<IDspaceItem> items);

}