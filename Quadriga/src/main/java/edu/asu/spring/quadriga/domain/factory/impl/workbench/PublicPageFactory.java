package edu.asu.spring.quadriga.domain.factory.impl.workbench;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.factory.workbench.IPublicPageFactory;
import edu.asu.spring.quadriga.domain.impl.workbench.PublicPage;
import edu.asu.spring.quadriga.domain.workbench.IPublicPage;

/**
 * Factory class to create Public Page object
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
	public IPublicPage clonePublicPageObject(IPublicPage publicpage) 
	{
		IPublicPage clone = new PublicPage();
		clone.setTitle1(publicpage.getTitle1());
		clone.setTitle2(publicpage.getTitle2());
		clone.setTitle3(publicpage.getTitle3());
		clone.setDescription1(publicpage.getDescription1());
		clone.setDescription2(publicpage.getDescription2());
		clone.setDescription3(publicpage.getDescription3());
		return clone;
	}
	
}
