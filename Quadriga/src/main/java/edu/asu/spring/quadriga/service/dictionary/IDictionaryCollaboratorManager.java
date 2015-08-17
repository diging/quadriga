package edu.asu.spring.quadriga.service.dictionary;

import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IDictionaryCollaboratorManager {

	public abstract void updateCollaborators(String dictionaryId, String collabUser,
			String collaboratorRole, String username) throws QuadrigaStorageException;
	
	/**
     * this method used to delete collaborators in the current dictionary
     * 
     * @param dictionaryid
     * @param userName
     * @return
     */
    public void deleteCollaborators(String dictionaryid, String userName) throws QuadrigaStorageException;

}
