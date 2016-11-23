package edu.asu.spring.quadriga.aspects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.editor.IEditorAccessManager;

/**
 * Service level Implementation of {@link IAuthorization} for {@link INetwork}.
 * This class specifically works on authorization check of user for
 * {@link INetwork} access.
 * 
 * @author Kiran kumar
 *
 */
@Service("networkAuthorization")
public class NetworkAuthorization implements IAuthorization {

    @Autowired
    private IEditorAccessManager accessManager;

    @Override
    public boolean chkAuthorization(String userName, Object accessObject, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        boolean haveAccess;
        haveAccess = false;

        // check if the user has a editor role for the network specified
        String networkId = (String) accessObject;

        haveAccess = accessManager.checkIsNetworkEditor(networkId, userName);

        return haveAccess;
    }

    @Override
    public boolean chkAuthorizationByRole(String userName, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        boolean haveAccess;

        haveAccess = false;

        // check if the user has a editor role associated
        haveAccess = accessManager.checkIsEditor(userName);

        return haveAccess;
    }

    @Override
    public boolean chkAuthorization(String userName, List<String> accessObjectId, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        // TODO Auto-generated method stub
        return false;
    }

}