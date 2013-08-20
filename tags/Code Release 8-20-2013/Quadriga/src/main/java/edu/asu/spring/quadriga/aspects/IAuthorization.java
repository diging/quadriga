package edu.asu.spring.quadriga.aspects;

import edu.asu.spring.quadriga.exceptions.QuadrigaAccessException;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IAuthorization {

	public abstract boolean chkAuthorization(String userName, String accessObjectId,
			String[] userRoles) throws QuadrigaStorageException, QuadrigaAccessException;
}
