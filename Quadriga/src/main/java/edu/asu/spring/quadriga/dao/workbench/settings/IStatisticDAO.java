package edu.asu.spring.quadriga.dao.workbench.settings;

import java.util.List;

import edu.asu.spring.quadriga.dto.ProjectStatisticsDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserDTO;
import edu.asu.spring.quadriga.dto.QuadrigaUserRequestsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface provides the methods to apply settings for statistics public
 * page.
 * 
 * @author Ajay Modi
 * 
 */
public interface IStatisticDAO {

    /**
     * Updates settings entry for a particular statistics
     * 
     * @param projectId
     * @param name
     * @param isChecked
     * @throws QuadrigaStorageException
     */
    public abstract void upsertStatisticsSettings(String projectId,
            String name, Boolean isChecked) throws QuadrigaStorageException;

    /**
     * Get settings for statistics for entire project
     * 
     * @param projectId
     * @throws QuadrigaStorageException
     */

    public abstract List<ProjectStatisticsDTO> getStatisticsSettings(
            String projectId) throws QuadrigaStorageException;

}
