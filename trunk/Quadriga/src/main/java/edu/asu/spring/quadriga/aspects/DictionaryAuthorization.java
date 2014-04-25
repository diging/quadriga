package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollectionCollaborator;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.dictionary.IDictionaryCollaborator;
import edu.asu.spring.quadriga.domain.factory.dictionary.IDictionaryFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.dictionary.IRetrieveDictionaryManager;

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
	IRetrieveDictionaryManager dictionaryRetrieveManager;

	@Autowired
	IDictionaryFactory dictionaryFactory;

	@Autowired
	IDictionaryManager dictonaryManager;

	/**
	 * This method checks the access permissions for logged in user for given
	 * dictionary id
	 * 
	 * @param - userName - logged in user
	 * @param - accessObjectId - dictionary id
	 * @param - userRoles - roles for which access permissions should be
	 *        checked.
	 * @return if has access - true if no access - false
	 */
	@Override
	public boolean chkAuthorization(String userName, String accessObjectId,
			String[] userRoles) throws QuadrigaStorageException,
			QuadrigaAccessException {
		boolean haveAccess;
		String dictionaryOwner;
		String collaboratorName;
		String collaboratorRoleId;
		List<ICollaboratorRole> collaboratorRoles;
		IDictionary dictionary;
		ArrayList<String> roles;

		haveAccess = false;

		// fetch the details of the concept collection
		dictionary = dictionaryRetrieveManager
				.getDictionaryDetails(accessObjectId);

		// check if the user is a dictionary owner
		dictionaryOwner = dictionary.getOwner().getUserName();
		if (userName.equals(dictionaryOwner)) {
			haveAccess = true;
		}

		// check the collaborator roles if he is not owner
		if (!haveAccess) {
			if (userRoles.length > 0) {
				roles = getAccessRoleList(userRoles);
				// fetch the collaborators of the concept collection
				List<IDictionaryCollaborator> dictCollaboratorList = dictonaryManager
						.showCollaboratingUsers(accessObjectId);
				if (dictCollaboratorList != null) {
					for (IDictionaryCollaborator dictCollaborator : dictCollaboratorList) {
						// check if he is the collaborator to the concept
						// collection
						collaboratorName = dictCollaborator.getCollaborator()
								.getUserObj().getUserName();
						if (userName != null) {
							if (userName.equals(collaboratorName)) {
								collaboratorRoles = dictCollaborator
										.getCollaborator()
										.getCollaboratorRoles();
								if (collaboratorRoles != null) {
									for (ICollaboratorRole collabRole : collaboratorRoles) {
										collaboratorRoleId = collabRole
												.getRoleid();
										if (roles != null) {
											if (roles
													.contains(collaboratorRoleId)) {
												haveAccess = true;
												return haveAccess;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return haveAccess;
	}

	/**
	 * This method checks if the logged in user has permissions for any of the
	 * dictionary present in the system.
	 * 
	 * @param - userName - logged in User
	 * @param - userRoles - roles for which the access permissions to be
	 *        checked.
	 * @return - if has access - true no access - false
	 * @throws QuadrigaStorageException
	 *             , QuadrigaAccessException
	 */
	@Override
	public boolean chkAuthorizationByRole(String userName, String[] userRoles)
			throws QuadrigaStorageException, QuadrigaAccessException {
		return false;

	}

	/**
	 * This method returns an array of strings to list
	 * 
	 * @param userRoles
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getAccessRoleList(String[] userRoles) {
		ArrayList<String> rolesList = new ArrayList<String>();

		for (String role : userRoles) {
			rolesList.add(role);
		}

		return rolesList;
	}

}
