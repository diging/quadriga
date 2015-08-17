package edu.asu.spring.quadriga.dao.conceptcollection;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.domain.ICollaborator;
import edu.asu.spring.quadriga.dto.ConceptCollectionCollaboratorDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IConceptCollectionCollaboratorDAO extends IBaseDAO<ConceptCollectionCollaboratorDTO> {

	/**
	 * This method adds the collaborator for given concept collection
	 * @param : collaborator - ICollaborator object
	 * @param : collectionid - Concept Collection id
	 * @param : userName - logged in user
	 * @throws : QuadrigaStorageException
	 */
	public abstract void addCollaboratorRequest(ICollaborator collaborator, String collectionid,
			String userName) throws QuadrigaStorageException;

}
