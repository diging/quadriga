package edu.asu.spring.quadriga.aspects;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

/**
 * Service level Implementation of {@link IAuthorization} for
 * {@link IConceptCollection} for REST APIs. This class specifically works on
 * authorization check of user for {@link IConceptCollection} access.
 * 
 * @author Lohith Dwaraka
 *
 */
@Service("conceptCollectionRestAuthorization")
public class ConceptCollectionRestAuthorization implements IAuthorization {
    @Autowired
    private IConceptCollectionManager conceptCollectionManager;

    @Autowired
    private IConceptCollectionFactory collectionFactory;

    /**
     * This method checks the access permission for the logged in user for given
     * concept collection for rest methods.
     * 
     * @param userName
     *            - username submitted for rest authentication.
     * @param conceptCollectionId
     *            - Concept collection id.
     * @param userRoles
     *            - Roles for which the user should be checked for access.
     * @return - if has access - true no access - false
     * @author Lohith Dwaraka.
     */
    @Override
    public boolean chkAuthorization(String userName,
            String conceptCollectionId, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {

        // fetch the details of the concept collection
        IConceptCollection collection = conceptCollectionManager.getConceptCollection(conceptCollectionId);

        // check if the user is a concept collection owner
        String conceptCollectionOwner = collection.getOwner().getUserName();
        if (userName.equals(conceptCollectionOwner)) {
            return true;
        }

        // check the collaborator roles if he is not owner
        if (userRoles.length == 0) {
            return false;
        }
        
        List<String> roles = Arrays.asList(userRoles);

        // fetch the collaborators of the concept collection
        List<IConceptCollectionCollaborator> ccCollaboratorList = conceptCollectionManager
                .showCollaboratingUsers(conceptCollectionId);

        if (ccCollaboratorList == null || ccCollaboratorList.isEmpty())
            return false;

        for (IConceptCollectionCollaborator ccCollaborator : ccCollaboratorList) {
            // check if he is the collaborator to the concept
            // collection
            String collaboratorName = ccCollaborator.getCollaborator()
                    .getUserObj().getUserName();

            if (userName != null) {
                if (userName.equals(collaboratorName)) {
                    List<IQuadrigaRole> collaboratorRoles = ccCollaborator
                            .getCollaborator().getCollaboratorRoles();
                    if (collaboratorRoles != null) {
                        for (IQuadrigaRole collabRole : collaboratorRoles) {
                            String collaboratorRoleId = collabRole.getId();
                            if (roles != null) {
                                if (roles.contains(collaboratorRoleId)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

        }

        return false;
    }

    /**
     * This method checks the access for the user in the concept collection
     * present in the system.
     * 
     * @param userName
     *            - User credentials entered for rest authentication.
     * @userRoles - Roles for which the user should be possessing for access.
     * @throws - QuadrigaStorageException, QuadrigaAccessException
     * @author Lohith Dwaraka
     */
    @Override
    public boolean chkAuthorizationByRole(String userName, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        return false;

    }

    @Override
    public boolean chkAuthorization(String userName, List<String> accessObjectId, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        // TODO Auto-generated method stub
        return false;
    }

}
