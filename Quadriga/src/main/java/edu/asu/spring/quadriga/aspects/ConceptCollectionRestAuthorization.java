package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.conceptcollection.IConceptCollection;
import edu.asu.spring.quadriga.domain.factory.conceptcollection.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

/**
 * Service level Implementation of {@link IAuthorization} for {@link IConceptCollection} for REST APIs.
 * This class specifically works on authorization check of user for {@link IConceptCollection} access. 
 * @author Lohith Dwaraka
 *
 */
@Service("conceptCollectionRestAuthorization")
public class ConceptCollectionRestAuthorization implements IAuthorization 
{
	@Autowired
	IConceptCollectionManager conceptCollectionManager;
	
	@Autowired
	private IConceptCollectionFactory collectionFactory;

	/**
	 * This method checks the access permission for the logged in user for given
	 * concept collection for rest methods.
	 * @param userName - username submitted for rest authentication.
	 * @param conceptCollectionId - Concept collection id.
	 * @param userRoles - Roles for which the user should be checked for access.
	 * @return - if has access - true
	 *           no access - false
	 * @author Lohith Dwaraka.
	 */
	@Override
	public boolean chkAuthorization(String userName, String conceptCollectionId,
			String[] userRoles) throws QuadrigaStorageException,
			QuadrigaAccessException 
	{
		boolean haveAccess;
		String conceptCollectionOwner;
		String collaboratorName;
		String collaboratorRoleId;
		List<ICollaboratorRole> collaboratorRoles;
		IConceptCollection collection;
		ArrayList<String> roles;
		
		haveAccess = false;
		
		//fetch the details of the concept collection
		collection = collectionFactory.createConceptCollectionObject();
		collection.setConceptCollectionId(conceptCollectionId);
		conceptCollectionManager.getCollectionDetails(collection, userName);
		
		//check if the user is a concept collection owner
		conceptCollectionOwner = collection.getOwner().getUserName();
		if(userName.equals(conceptCollectionOwner))
		{
			haveAccess = true;
		}
		
		//check the collaborator roles if he is not owner
		if(!haveAccess)
		{
			if(userRoles.length>0)
			{
				roles = getAccessRoleList(userRoles);
				//fetch the collaborators of the concept collection
				List<ICollaborator> collaboratorList = conceptCollectionManager.showCollaboratingUsers(conceptCollectionId);
				
				for(ICollaborator collaborator : collaboratorList)
				{
					//check if he is the collaborator to the concept collection
					collaboratorName = collaborator.getUserObj().getUserName();
					
					if(userName.equals(collaboratorName))
					{
						collaboratorRoles = collaborator.getCollaboratorRoles();
						
							for(ICollaboratorRole collabRole : collaboratorRoles)
							{
								collaboratorRoleId = collabRole.getRoleid();
								if(roles.contains(collaboratorRoleId))
								{
									haveAccess = true;
									return haveAccess;
								}
							}
					}
				}
			}
		}
		return haveAccess;
	}
	
	/**
	 * This method checks the access for the user in the concept collection
	 * present in the system.
	 * @param userName - User credentials entered for rest authentication.
	 * @userRoles - Roles for which the user should be possessing for access.
	 * @throws - QuadrigaStorageException, QuadrigaAccessException
	 * @author Lohith Dwaraka
	 */
	@Override
	public boolean chkAuthorizationByRole(String userName,String[] userRoles )
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		return false;
		
	}
	
	/**
	 * This method converts String array into a list
	 * @param userRoles
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getAccessRoleList(String[] userRoles)
	{
		ArrayList<String> rolesList = new ArrayList<String>();
		
		for(String role : userRoles)
		{
			rolesList.add(role);
		}
		
		return rolesList;
	}

}
