package edu.asu.spring.quadriga.service.workbench;

import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IModifyPublicPageContentManager {

    void addNewPublicPageContent(IPublicPage publicPage) throws QuadrigaStorageException;

	}
