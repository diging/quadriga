package edu.asu.spring.quadriga.service.workbench;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.asu.spring.quadriga.dao.workbench.settings.IStatisticDAO;
import edu.asu.spring.quadriga.dto.ProjectStatisticsDTO;
import edu.asu.spring.quadriga.exceptions.QuadrigaStorageException;
import edu.asu.spring.quadriga.profile.validator.NotEmptyList;

/**
 * this class holds the list of statistics settings for a project and used in
 * the controller as a modelattribute
 * 
 * @author Ajay Modi
 *
 */
@Service
public class StatisticsSettingsService {

    @Autowired
    private IStatisticDAO statisticDao;

    @Transactional
    public void updateRow(String projectId, String name, Boolean isChecked)
            throws QuadrigaStorageException {
        statisticDao.upsertStatisticsSettings(projectId, name, isChecked);
    }

    @Transactional
    public void updateSettings(String projectId, String[] names)
            throws QuadrigaStorageException {
        List<String> statisticsSettingsList = getStatisticsSettingsList(projectId);
        List<String> namesList = Arrays.asList(names);
        if (statisticsSettingsList != null && statisticsSettingsList.size() > 0) {
            for (String name : statisticsSettingsList) {
                if (namesList.contains(name)) {
                    updateRow(projectId, name, true);
                } else {
                    updateRow(projectId, name, false);
                }
            }
        }
    }

    @Transactional
    public List<ProjectStatisticsDTO> getStatisticsSettingsDTOList(
            String projectId) throws QuadrigaStorageException {
        List<ProjectStatisticsDTO> statisticsDTOList = statisticDao
                .getStatisticsSettings(projectId);
        return statisticsDTOList;
    }

    public List<String> getNames(List<ProjectStatisticsDTO> statisticsDTOList)
            throws QuadrigaStorageException {
        List<String> names = new ArrayList<String>();
        if (statisticsDTOList != null && statisticsDTOList.size() > 0) {
            for (ProjectStatisticsDTO psDTO : statisticsDTOList) {
                String name = psDTO.getProjectStatisticsDTOPK().getName();
                names.add(name);
            }
        }
        return names;
    }

    @Transactional
    public List<String> getStatisticsSettingsList(String projectId)
            throws QuadrigaStorageException {
        List<ProjectStatisticsDTO> statisticsDTOList = statisticDao
                .getStatisticsSettings(projectId);
        if (statisticsDTOList == null || statisticsDTOList.size() == 0) {
            return new ArrayList<String>(getdefaultMapSettings().keySet());
        }
        List<String> names = getNames(statisticsDTOList);
        return names;
    }

    public Map<String, Boolean> convertIntoMap(
            List<ProjectStatisticsDTO> statisticsDTOList)
            throws QuadrigaStorageException {
        Map<String, Boolean> statisticsSettings = new HashMap<String, Boolean>();
        if (statisticsDTOList != null && statisticsDTOList.size() > 0) {
            for (ProjectStatisticsDTO psDTO : statisticsDTOList) {
                String name = psDTO.getProjectStatisticsDTOPK().getName();
                Boolean isChecked = psDTO.getIschecked();
                statisticsSettings.put(name, isChecked);
            }
        }
        return statisticsSettings;
    }

    public Map<String, Boolean> getdefaultMapSettings() {
        Map<String, Boolean> statisticsSettings = new HashMap<String, Boolean>();
        statisticsSettings.put("concept statistics", false);
        statisticsSettings.put("user statistics", false);
        return statisticsSettings;
    }

    @Transactional
    public Map<String, Boolean> getStatisticsSettingsMap(String projectId)
            throws QuadrigaStorageException {
        List<ProjectStatisticsDTO> statisticsDTOList = statisticDao
                .getStatisticsSettings(projectId);
        if (statisticsDTOList == null || statisticsDTOList.size() == 0) {
            return getdefaultMapSettings();
        }
        Map<String, Boolean> statisticsSettings = convertIntoMap(statisticsDTOList);
        return statisticsSettings;
    }

}
