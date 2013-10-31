package edu.asu.spring.quadriga.aspects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.service.editor.IEditorAccessManager;

@Service
public class EditorAuthorization implements IAuthorization 
{
	
	@Autowired
	private IEditorAccessManager accessManager;

	@Override
	public boolean chkAuthorization(String userName, String accessObjectId,
			String[] userRoles) throws QuadrigaStorageException,
			QuadrigaAccessException 
	{
		boolean haveAccess;
		haveAccess = false;
		
		//check if the user has a editor role for the network specified
		haveAccess = accessManager.checkIsNetworkEditor(accessObjectId, userName);
		
		return haveAccess;
	}

	@Override
	public boolean chkAuthorizationByRole(String userName, String[] userRoles)
			throws QuadrigaStorageException, QuadrigaAccessException 
	{
		boolean haveAccess;
		
		haveAccess =false;
		
		//check if the user has a editor role associated
		haveAccess = accessManager.checkIsEditor(userName);
		
		return haveAccess;
	}

}
