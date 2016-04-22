package edu.asu.spring.quadriga.domain.factory.workbench;

import edu.asu.spring.quadriga.domain.workbench.IPublicPage;

public interface IPublicPageFactory {

    public abstract IPublicPage createPublicPageObject();

    public abstract IPublicPage clonePublicPageObject(IPublicPage publicpage);
}