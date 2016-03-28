package edu.asu.spring.quadriga.service.publicwebsite;

import java.util.List;
import java.util.Map;

import edu.asu.spring.quadriga.domain.IConceptStats;
import edu.asu.spring.quadriga.domain.IUserStats;
import edu.asu.spring.quadriga.domain.network.INetwork;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * Interface to implement stats for project.
 *
 */

public interface IProjectStats {

    public abstract List<IConceptStats> getConceptCount(List<INetwork> Networks)
            throws QuadrigaStorageException;

    /**
     * Retrieve the count of workspace for a given user associated to given
     * project
     * 
     * @param projectid
     * @param username
     * @return - the count of workspace for a given user associated to given
     *         project.
     * @throws QuadrigaStorageException
     */
    public abstract Map<String, IUserStats> getCountofWorkspace(String projectid)
            throws QuadrigaStorageException;

    /**
     * Retrieve the count of network for a given user associated to given
     * workspace
     * 
     * @param projectid
     * @return - the count of network for a given user associated to given
     *         project.
     * @throws QuadrigaStorageException
     */

    public abstract Map<String, IUserStats> getCountofNetwork(String projectid,
            Map<String, IUserStats> listUserStats)
            throws QuadrigaStorageException;

}