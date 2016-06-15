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
    private IStatisticSettingsDAO statisticSettingsDAO;

    @Autowired
    private StatisticsSettingsDTOMapper statisticSettingsDTOMapper;

    @Autowired
    private Environment env;

    @Transactional
    public void addOrUpdateStatisticsSettings(String projectId, String[] names)
            throws QuadrigaStorageException {
        List<String> defaultValues = getDefaultStatisticsSettingsTypes();
        List<String> namesList = Arrays.asList(names);
        for (String name : defaultValues) {
            StatisticsSettingsDTO statisticsSettingsDTO = statisticSettingsDAO
                    .getStatisticsSettings(projectId, name);

            if (statisticsSettingsDTO == null) {
                statisticsSettingsDTO = new StatisticsSettingsDTO(
                        statisticSettingsDAO.generateUniqueID(), projectId,
                        name, true);
            }

            if (namesList.contains(name)) {
                statisticsSettingsDTO.setIschecked(true);
            } else {

                statisticsSettingsDTO.setIschecked(false);
            }

            statisticSettingsDAO
                    .saveOrUpdateStatisticsSettings(statisticsSettingsDTO);
        }
    }

    @Transactional
    public List<IStatisticsSettings> getStatisticsSettingsList(String projectId)
            throws QuadrigaStorageException {
        List<StatisticsSettingsDTO> statisticsDTOList = null;
        List<String> defaultValues = getDefaultStatisticsSettingsTypes();
        statisticsDTOList = statisticSettingsDAO
                .getStatisticsSettings(projectId);
        if (statisticsDTOList.size() == 0) {
            statisticsDTOList = new ArrayList<StatisticsSettingsDTO>();
            for (String name : defaultValues) {
                StatisticsSettingsDTO statisticsSettingsDTO = new StatisticsSettingsDTO(
                        statisticSettingsDAO.generateUniqueID(), projectId,
                        name, false);
                statisticsDTOList.add(statisticsSettingsDTO);
            }
        }

        List<IStatisticsSettings> statisticsSettingsList = new ArrayList<IStatisticsSettings>();
        for (StatisticsSettingsDTO statisticsSettingsDTO : statisticsDTOList) {
            IStatisticsSettings s = statisticSettingsDTOMapper
                    .getStatisticsSettings(statisticsSettingsDTO);
            statisticsSettingsList.add(s);
        }

        return statisticsSettingsList;
    }

    private List<String> getDefaultStatisticsSettingsTypes() {
        String propertyValue = env.getProperty("statistics.settings");
        List<String> settings = Arrays.asList(propertyValue.split(","));
        return settings;
    }

}
