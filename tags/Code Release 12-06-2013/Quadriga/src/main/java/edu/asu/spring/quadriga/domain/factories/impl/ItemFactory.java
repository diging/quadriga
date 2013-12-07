package edu.asu.spring.quadriga.domain.factories.impl;

import edu.asu.spring.quadriga.domain.IItem;
import edu.asu.spring.quadriga.domain.factories.IItemFactory;
import edu.asu.spring.quadriga.domain.implementation.Item;

/**
 * Factory class for creating {@link @Item}
 * 
 * @author Ram Kumar Kumaresan
 *
 */
public class ItemFactory implements IItemFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IItem createItemObject()
	{
		return new Item();
	}
}
