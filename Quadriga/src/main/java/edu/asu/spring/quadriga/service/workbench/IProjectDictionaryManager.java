package edu.asu.spring.quadriga.service.workbench;

import java.util.List;

import edu.asu.spring.quadriga.domain.IUser;
import edu.asu.spring.quadriga.domain.dictionary.IDictionary;
import edu.asu.spring.quadriga.domain.workbench.IProject;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

public interface IProjectDictionaryManager {

    /**
     * This class helps in adding dictionary to the project
     * 
     * @param projectId
     *            {@link IProject} ID of type {@link String}
     * @param dictionaryId
     *            {@link IDictionary} ID of type {@link String}
     * @param userId
     *            {@link IUser} ID of type {@link String}
     * @throws QuadrigaStorageException
     *             Throws Storage exception when there is a issue with access to
     *             DB
     */
    public void addDictionaryToProject(String projectId, String dictionaryId, String userId)
            throws QuadrigaStorageException;

    /**
     * This class helps in getting {@link List} the {@link IProjectDictionary}
     * from {@link IProject} and {@link IUser} Id
     * 
     * @param projectId
     *            {@link IProject} ID of type {@link String}
     * @param userId
     *            {@link IUser} ID of type {@link String}
     * @return Returns the of {@link List} of {@link IDictionary}
     * @throws QuadrigaStorageException
     *             Throws Storage exception when there is a issue with access to
     *             DB
     */
    public List<IDictionary> getDictionaries(String projectId) throws QuadrigaStorageException;

    /**
     * This class helps in deleting the {@link IDictionary} of a
     * {@link IProject} using {@link IProject} ID, {@link IDictionary} ID and
     * {@link IUser} Id
     * 
     * @param projectId
     *            {@link IProject} ID of type {@link String}
     * @param userId
     *            {@link IUser} ID of type {@link String}
     * @param dictioanaryId
     *            {@link IDictionary} ID of type {@link String}
     * @throws QuadrigaStorageException
     *             Throws Storage exception when there is a issue with access to
     *             DB
     */
    public void deleteProjectDictionary(String projectId, String userId, String dictioanaryId)
            throws QuadrigaStorageException;
}
