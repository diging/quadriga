package edu.asu.spring.quadriga.aspects;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

@Service("networkAuthorization")
public class NetworkAuthorization implements IAuthorization {

	@Override
	public boolean chkAuthorization(String userName, String accessObjectId,
			String[] userRoles) throws QuadrigaStorageException,
			QuadrigaAccessException 
	{
		boolean haveAccess;
		
		haveAccess = false;
		
		return haveAccess;
	}

	/**
	 * Check if the user is a project editor role.
	 * Check if the user is a workspace editor role.
	 */
	@Override
	public boolean chkAuthorizationByRole(String userName, String[] userRoles)
			throws QuadrigaStorageException, QuadrigaAccessException 
   {
		boolean haveAccess;
		
		haveAccess = false;
		
		return haveAccess;
	}

}
