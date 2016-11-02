package edu.asu.spring.quadriga.aspects;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IQuadrigaRole;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;

/**
 * Service level Implementation of {@link IAuthorization} for
 * {@link IDictionary} This class specifically works on authorization check of
 * user for {@link IDictionary} access.
 * 
 * @author Kiran Kumar
 * 
 */
@Service("dictionaryAuthorization")
public class DictionaryAuthorization implements IAuthorization {

    @Autowired
    private IDictionaryManager dictonaryManager;

    /**
     * This method checks the access permissions for logged in user for given
     * dictionary id
     * 
     * @param -
     *            userName - logged in user
     * @param -
     *            accessObjectId - dictionary id
     * @param -
     *            userRoles - roles for which access permissions should be
     *            checked.
     * @return if has access - true if no access - false
     */
    @Override
    public boolean chkAuthorization(String userName, Object accessObject, String[] userRoles)
            throws QuadrigaStorageException, QuadrigaAccessException {
        String collaboratorName;
        String collaboratorRoleId;
        List<IQuadrigaRole> collaboratorRoles;

        IDictionary dictionary;
        String dictionaryId = null;

        // fetch the details of the concept collection
        if (accessObject.getClass().equals(String.class)) {
            dictionaryId = (String) accessObject;
            dictionary = dictonaryManager.getDictionaryDetails(dictionaryId);
        } else {
            dictionary = (IDictionary) accessObject;
        }
        // fetch the details of the concept collection

        if (dictionary == null) {
            throw new QuadrigaAccessException();
        }
        // check if the user is a dictionary owner
        String dictionaryOwner = dictionary.getOwner().getUserName();
        if (userName.equals(dictionaryOwner)) {
            return true;
        }

        if (userRoles.length == 0)
            return false;

        // check the collaborator roles if he is not owner
        List<String> roles = Arrays.asList(userRoles);
        // fetch the collaborators of the concept collection
        List<IDictionaryCollaborator> dictCollaboratorList = dictonaryManager.showCollaboratingUsers(dictionaryId);

        if (dictCollaboratorList == null || dictCollaboratorList.isEmpty())
            return false;

        for (IDictionaryCollaborator dictCollaborator : dictCollaboratorList) {
            // check if he is the collaborator to the concept
            // collection
            collaboratorName = dictCollaborator.getCollaborator().getUserObj().getUserName();
            if (userName != null && userName.equals(collaboratorName)) {
                collaboratorRoles = dictCollaborator.getCollaborator().getCollaboratorRoles();
                if (collaboratorRoles != null) {
                    for (IQuadrigaRole collabRole : collaboratorRoles) {
                        collaboratorRoleId = collabRole.getId();
                        if (roles != null) {
                            if (roles.contains(collaboratorRoleId)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * This method checks if the logged in user has permissions for any of the
     * dictionary present in the system.
     * 
     * @param -
     *            userName - logged in User
     * @param -
     *            userRoles - roles for which the access permissions to be
     *            checked.
     * @return - if has access - true no access - false
     * @throws QuadrigaStorageException
     *             , QuadrigaAccessException
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
