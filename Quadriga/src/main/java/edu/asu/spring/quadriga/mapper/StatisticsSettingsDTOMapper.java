package edu.asu.spring.quadriga.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
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
@PropertySource(value = "classpath:/messages.properties")
public class StatisticsSettingsDTOMapper extends BaseMapper {

    @Autowired
    private Environment env;

    /**
     * generates {@linkplain IStatisticsSettings} when
     * {@linkplain StatisticsSettingsDTO} is provided
     * 
     * @param StatisticsSettingsDTO
     * @return
     * @throws QuadrigaStorageException
     */
    public IStatisticsSettings getStatisticsSettings(
            StatisticsSettingsDTO statisticsSettingsDTO)
            throws QuadrigaStorageException {
        IStatisticsSettings statisticsSettings = new StatisticsSettings();

        statisticsSettings.setProjectId(statisticsSettingsDTO.getProjectid());
        statisticsSettings.setName(statisticsSettingsDTO.getName());
        statisticsSettings.setIsChecked(statisticsSettingsDTO.getIschecked());
        statisticsSettings.setMessage(getMessage(statisticsSettingsDTO
                .getName()));
        ;
        return statisticsSettings;
    }

    private String getMessage(String key) {
        String propertyValue = env.getProperty(key);
        return propertyValue;
    }
}
