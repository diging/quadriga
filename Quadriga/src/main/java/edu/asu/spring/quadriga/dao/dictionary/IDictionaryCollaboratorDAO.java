package edu.asu.spring.quadriga.dao.dictionary;

import edu.asu.spring.quadriga.dao.IBaseDAO;
import edu.asu.spring.quadriga.dto.DictionaryCollaboratorDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryCollaboratorDAO extends IBaseDAO<DictionaryCollaboratorDTO> {

	/**
	 * This method update the roles associated with the collaborator for dictionary
	 * @param : dictionaryId - dictionary id
	 * @param : collabUser - collaborator user
	 * @param : collaboratorRole - roles associated with the collaborator
	 * @param : userName - logged in user
	 * @throws : QuadrigatorageException
	 */
	public abstract void updateCollaboratorRoles(String dictionaryId, String collabUser,
			String collaboratorRole, String username) throws QuadrigaStorageException;

}
