package edu.asu.spring.quadriga.dao.workbench.settings;

import java.util.List;

import edu.asu.spring.quadriga.dto.StatisticsSettingsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This interface provides the methods to apply settings for statistics public
 * page.
 * 
 * @author Ajay Modi
 * 
 */
public interface IStatisticSettingsDAO {

    /**
     * Updates settings entry for a particular statistics
     * 
     * @param statsSettingsDTO
     * @return
     * @throws QuadrigaStorageException
     */
    public abstract int saveOrUpdateStatisticsSettings(
            StatisticsSettingsDTO statsSettingsDTO)
            throws QuadrigaStorageException;

    /**
     * Get settings for statistics for entire project
     * 
     * @param projectId
     * @throws QuadrigaStorageException
     */

    public abstract List<StatisticsSettingsDTO> getStatisticsSettings(
            String projectId) throws QuadrigaStorageException;

}
