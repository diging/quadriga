package edu.asu.spring.quadriga.service.passthroughproject;

import javax.xml.bind.JAXBException;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.exceptions.DocumentParserException;
import edu.asu.spring.quadriga.exceptions.NoSuchRoleException;
import edu.asu.spring.quadriga.exceptions.QStoreStorageException;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IPassThroughProjectManager {

    String storePassThroughProjectInfo(String xml, IUser user) throws DocumentParserException, NoSuchRoleException, QuadrigaStorageException, JAXBException, QuadrigaAccessException, QStoreStorageException;

}
