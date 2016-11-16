package edu.asu.spring.quadriga.aspects;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.impl.networks.Network;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.IllegalObjectException;
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

    private final Logger logger = LoggerFactory.getLogger(NetworkAuthorization.class);

    @Override
    public boolean chkAuthorization(String userName, Object accessObject, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {

        String networkId = null;
        // check if the user has a editor role for the network specified

        if (accessObject instanceof String) {
            networkId = (String) accessObject;
        } else {
            try {
                INetwork nwObj = (Network) accessObject;
                networkId = nwObj.getNetworkId();
            } catch (ClassCastException cce) {
                throw new IllegalObjectException(cce);

            }
        }

        return accessManager.checkIsNetworkEditor(networkId, userName);

    }

    @Override
    public boolean chkAuthorizationByRole(String userName, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {

        // check if the user has a editor role associated
        return accessManager.checkIsEditor(userName);

    }

    @Override
    public boolean chkAuthorization(String userName, List<String> accessObjectId, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        // TODO Auto-generated method stub
        return false;
    }

}