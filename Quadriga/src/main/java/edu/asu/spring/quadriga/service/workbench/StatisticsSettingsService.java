package edu.asu.spring.quadriga.service.workbench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.settings.IStatisticSettingsDAO;
import edu.asu.spring.quadriga.domain.IStatisticsSettings;
import edu.asu.spring.quadriga.dto.StatisticsSettingsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.mapper.StatisticsSettingsDTOMapper;

/**
 * this class holds the list of statistics settings for a project and used in
 * the controller as a modelattribute
 * 
 * @author Ajay Modi
 *
 */
@Service
@PropertySource(value = "classpath:/settings.properties")
public class StatisticsSettingsService {

    @Autowired
    private IStatisticSettingsDAO statisticSettingsDao;

    @Autowired
    private Environment env;

    @Transactional
    public void addOrUpdateStatisticsSettings(String projectId, String[] names)
            throws QuadrigaStorageException {
        String[] defaultValues = getDefaultStatisticsSettings();
        List<String> namesList = Arrays.asList(names);
        for (String name : defaultValues) {
            StatisticsSettingsDTO statisticsSettingsDTO = null;
            if (namesList.contains(name)) {
                statisticsSettingsDTO = new StatisticsSettingsDTO(projectId,
                        name, true);
            } else {
                statisticsSettingsDTO = new StatisticsSettingsDTO(projectId,
                        name, false);
            }

            statisticSettingsDao
                    .saveOrUpdateStatisticsSettings(statisticsSettingsDTO);
        }
    }

    @Transactional
    public List<IStatisticsSettings> getStatisticsSettingsList(String projectId)
            throws QuadrigaStorageException {
        List<StatisticsSettingsDTO> statisticsDTOList = null;
        String[] defaultValues = getDefaultStatisticsSettings();
        statisticsDTOList = statisticSettingsDao
                .getStatisticsSettings(projectId);
        if (statisticsDTOList.size() == 0) {
            statisticsDTOList = new ArrayList<StatisticsSettingsDTO>();
            for (String name : defaultValues) {
                StatisticsSettingsDTO statisticsSettingsDTO = new StatisticsSettingsDTO(
                        projectId, name, false);
                statisticsDTOList.add(statisticsSettingsDTO);
            }
        }

        List<IStatisticsSettings> statisticsSettingsList = new ArrayList<IStatisticsSettings>();
        for (StatisticsSettingsDTO statisticsSettingsDTO : statisticsDTOList) {
            IStatisticsSettings s = StatisticsSettingsDTOMapper
                    .getStatisticsSettings(statisticsSettingsDTO);
            statisticsSettingsList.add(s);
        }

        return statisticsSettingsList;
    }

    private String[] getDefaultStatisticsSettings() {

        String propertyValue = env.getProperty("statistics.settings");
        String[] settings = propertyValue.split(",");
        return settings;
    }

}
