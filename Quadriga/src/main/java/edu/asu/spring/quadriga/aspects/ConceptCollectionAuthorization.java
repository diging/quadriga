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
 * {@link IConceptCollection}. This class specifically works on authorization
 * check of user for {@link IConceptCollection} access.
 * 
 * @author Kiran kumar
 * 
 */
@Service("conceptCollectionAuthorization")
public class ConceptCollectionAuthorization implements IAuthorization {
    @Autowired
    IConceptCollectionManager conceptCollectionManager;

    @Autowired
    private IConceptCollectionFactory collectionFactory;

    /**
     * This method checks the access permissions for the given ConceptCollection
     * for the logged in user.
     * 
     * @param userName
     *            - logged in user name.
     * @param conceptCollectionId
     *            - Concept Collection id.
     * @param userRoles
     *            - the roles for which the user should be checked against the
     *            Concept Collection.
     * @throws QuadrigaAccessException
     *             ,QuadrigaStorageException
     * @author Kiran Batna
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
        if (userRoles.length == 0)
            return false;

        List<String> roles = Arrays.asList(userRoles);
        // fetch the collaborators of the concept collection
        List<IConceptCollectionCollaborator> ccCollaboratorList = conceptCollectionManager
                .showCollaboratingUsers(conceptCollectionId);

        // if concept collection doesn't have collaborating users
        // return false becuase the user can't be a collaborator then
        if (ccCollaboratorList == null || ccCollaboratorList.isEmpty())
            return false;

        for (IConceptCollectionCollaborator ccCollaborator : ccCollaboratorList) {
            // check if he is the collaborator to the concept
            // collection
            String collaboratorName = ccCollaborator.getCollaborator().getUserObj()
                    .getUserName();
            // if the collaborator is not the logged in user continue
            if (userName == null || !userName.equals(collaboratorName))
                continue;

            List<IQuadrigaRole> collaboratorRoles = ccCollaborator.getCollaborator()
                    .getCollaboratorRoles();
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

        return false;
    }

    /**
     * This method checks the access permissions for logged in user against the
     * concept collections present in the system.
     * 
     * @param userName
     *            - logged in user name.
     * @param userRoles
     *            - the roles for which the user should be checked against the
     *            Concept Collection.
     * @throws QuadrigaAccessException
     *             ,QuadrigaStorageException
     * @author Kiran Batna
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
