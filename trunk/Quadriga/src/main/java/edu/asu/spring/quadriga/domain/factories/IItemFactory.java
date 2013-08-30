package edu.asu.spring.quadriga.domain.factories;

import edu.asu.spring.quadriga.domain.IItem;

/**
 * Factory interface for Item factories.
 * 
 * @author Ram Kumar Kumaresan
 */
public interface IItemFactory {

	/**
	 * Create a item object with all fields empty.
	 * 
	 * @return An empty item object
	 */
	public abstract IItem createItemObject();

}