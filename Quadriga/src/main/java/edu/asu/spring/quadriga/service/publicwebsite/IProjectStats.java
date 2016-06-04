package edu.asu.spring.quadriga.service.publicwebsite;

import java.util.List;

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
     * Retrieve the user statistics of project
     * 
     * @param projectid
     * @return - the user statistics of project
     */

    public abstract List<IUserStats> getUserStats(String projectid)
            throws QuadrigaStorageException;

}