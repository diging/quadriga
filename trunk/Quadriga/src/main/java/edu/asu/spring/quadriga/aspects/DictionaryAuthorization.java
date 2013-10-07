package edu.asu.spring.quadriga.aspects;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.domain.ICollaboratorRole;
import edu.asu.spring.quadriga.domain.IDictionary;
import edu.asu.spring.quadriga.domain.factories.IDictionaryFactory;
import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.dictionary.IDictionaryManager;
import edu.asu.spring.quadriga.service.dictionary.IRetrieveDictionaryManager;

@Service("dictionaryAuthorization")
public class DictionaryAuthorization implements IAuthorization 
{
	@Autowired
	IRetrieveDictionaryManager dictionaryRetrieveManager;
	
	@Autowired
	IDictionaryFactory dictionaryFactory;
	
	@Autowired
	IDictionaryManager dictonaryManager;

	@Override
	public boolean chkAuthorization(String userName, String accessObjectId,
			String[] userRoles) throws QuadrigaStorageException,
			QuadrigaAccessException 
	{
		boolean haveAccess;
		String dictionaryOwner;
		String collaboratorName;
		String collaboratorRoleId;
		List<ICollaboratorRole> collaboratorRoles;
		IDictionary dictionary;
		ArrayList<String> roles;
		
		haveAccess = false;
		
		//fetch the details of the concept collection
		dictionary = dictionaryRetrieveManager.getDictionaryDetails(accessObjectId);
		
		
		//check if the user is a dictionary  owner
		dictionaryOwner = dictionary.getOwner().getUserName();
		if(userName.equals(dictionaryOwner))
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
				List<ICollaborator> collaboratorList = dictonaryManager.showCollaboratingUsers(accessObjectId);
				
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
