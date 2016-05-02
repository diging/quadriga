package edu.asu.spring.quadriga.domain.factory.workbench;

import edu.asu.spring.quadriga.domain.workbench.IPublicPage;

/**
 * Factory interface to create public page object
 * 
 * @author Prasanth Priya Nesan
 * 
 */
public interface IPublicPageFactory {

	public abstract IPublicPage createPublicPageObject();

	public abstract IPublicPage clonePublicPageObject(IPublicPage publicpage);
}
