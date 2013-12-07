package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.conceptcollection.IConceptCollectionManager;

@Service("conceptCollectionAuthorization")
public class ConceptCollectionAuthorization implements IAuthorization 
{
	@Autowired
	IConceptCollectionManager conceptCollectionManager;
	
	@Autowired
	private IConceptCollectionFactory collectionFactory;

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
		collection.setId(conceptCollectionId);
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
	
	@Override
	public boolean chkAuthorizationByRole(String userName,String[] userRoles )
			throws QuadrigaStorageException, QuadrigaAccessException
	{
		return false;
		
	}
	
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
