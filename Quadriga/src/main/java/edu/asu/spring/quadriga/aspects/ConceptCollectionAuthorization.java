package edu.asu.spring.quadriga.aspects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IConceptCollection;
import edu.asu.spring.quadriga.domain.factories.IConceptCollectionFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.sevice.conceptcollection.IConceptCollectionManager;

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
			if(!userRoles[0].equals("null"))
			{
				//fetch the collaborators of the concept collection
				List<ICollaborator> collaboratorList = conceptCollectionManager.showCollaboratingUsers(conceptCollectionId);
				
				for(ICollaborator collaborator : collaboratorList)
				{
					//check if he is the collaborator to the concept collection
					collaboratorName = collaborator.getUserObj().getUserName();
					
					if(userName.equals(collaboratorName))
					{
						collaboratorRoles = collaborator.getCollaboratorRoles();
						
						for(String role : userRoles)
						{
							for(ICollaboratorRole collabRole : collaboratorRoles)
							{
								collaboratorRoleId = collabRole.getRoleid();
								if(role.equals(collaboratorRoleId))
								{
									haveAccess = true;
									break;
								}
							}
							
							if(haveAccess)
								break;
						}
					}
				}
			}
		}
		return haveAccess;
	}

}
