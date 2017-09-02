package edu.asu.spring.quadriga.domain.factory.workbench.impl;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IPublicPageFactory;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.domain.workbench.impl.PublicPage;

/**
 * Factory class to create Public Page object
 * 
 * @author Prasanth Priya Nesan
 * 
 */
@Service
public class PublicPageFactory implements IPublicPageFactory {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPublicPage createPublicPageObject() {
		return new PublicPage();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IPublicPage clonePublicPageObject(IPublicPage publicpage) {
		IPublicPage clone = new PublicPage();
		clone.setTitle(publicpage.getTitle());
		clone.setDescription(publicpage.getDescription());
		clone.setOrder(publicpage.getOrder());
		return clone;
	}

}
