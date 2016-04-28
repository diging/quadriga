package edu.asu.spring.quadriga.mapper;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IStatisticsSettings;
import edu.asu.spring.quadriga.domain.impl.workbench.StatisticsSettings;
import edu.asu.spring.quadriga.dto.StatisticsSettingsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;

/**
 * This DTO mapper class provides the mapping from
 * {@linkplain IStatisticsSettings} instance to
 * {@linkplain StatisticsSettingsDTO} instance.
 * 
 * @author Ajay Modi
 *
 */
@Service
public class StatisticsSettingsDTOMapper extends BaseMapper {

    /**
     * generates {@linkplain IStatisticsSettings} when
     * {@linkplain StatisticsSettingsDTO} is provided
     * 
     * @param StatisticsSettingsDTO
     * @return
     * @throws QuadrigaStorageException
     */
    public static IStatisticsSettings getStatisticsSettings(
            StatisticsSettingsDTO statisticsSettingsDTO)
            throws QuadrigaStorageException {
        IStatisticsSettings statisticsSettings = new StatisticsSettings();

        statisticsSettings.setProjectId(statisticsSettingsDTO.getProjectId());
        statisticsSettings.setName(statisticsSettingsDTO.getName());
        statisticsSettings.setIsChecked(statisticsSettingsDTO.getIschecked());

        return statisticsSettings;
    }
}
