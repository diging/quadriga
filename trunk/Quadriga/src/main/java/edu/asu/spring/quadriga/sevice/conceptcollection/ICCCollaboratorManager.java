package edu.asu.spring.quadriga.sevice.conceptcollection;

import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface ICCCollaboratorManager {

	public abstract void addCollaborators(ICollaborator collaborator, String collectionid,
			String userName) throws QuadrigaStorageException;

}
