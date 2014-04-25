package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
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
		boolean haveAccess;
		String conceptCollectionOwner;
		String collaboratorName;
		String collaboratorRoleId;
		List<ICollaboratorRole> collaboratorRoles;
		IConceptCollection collection;
		ArrayList<String> roles;

		haveAccess = false;

		// fetch the details of the concept collection
		collection = collectionFactory.createConceptCollectionObject();
		collection.setConceptCollectionId(conceptCollectionId);
		conceptCollectionManager.getCollectionDetails(collection, userName);

		// check if the user is a concept collection owner
		conceptCollectionOwner = collection.getOwner().getUserName();
		if (userName.equals(conceptCollectionOwner)) {
			haveAccess = true;
		}

		// check the collaborator roles if he is not owner
		if (!haveAccess) {
			if (userRoles.length > 0) {
				roles = getAccessRoleList(userRoles);
				// fetch the collaborators of the concept collection
				List<IConceptCollectionCollaborator> ccCollaboratorList = conceptCollectionManager
						.showCollaboratingUsers(conceptCollectionId);

				if (ccCollaboratorList != null) {
					for (IConceptCollectionCollaborator ccCollaborator : ccCollaboratorList) {
						// check if he is the collaborator to the concept
						// collection
						collaboratorName = ccCollaborator.getCollaborator()
								.getUserObj().getUserName();
						if (userName != null) {
							if (userName.equals(collaboratorName)) {
								collaboratorRoles = ccCollaborator
										.getCollaborator()
										.getCollaboratorRoles();
								if (collaboratorRoles != null) {
									for (ICollaboratorRole collabRole : collaboratorRoles) {
										collaboratorRoleId = collabRole
												.getRoleid();
										if (roles != null) {
											if (roles.contains(collaboratorRoleId)) {
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

	/**
	 * This method converts the array of string into an arraylist.
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
