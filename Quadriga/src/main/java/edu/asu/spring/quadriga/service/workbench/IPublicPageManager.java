package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.workbench.IPublicPage;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPublicPageManager {

    void saveOrUpdatePublicPage(IPublicPage publicPage) throws QuadrigaStorageException;
    List<IPublicPage> retrievePublicPageContent(String projectid) throws QuadrigaStorageException;

	}
