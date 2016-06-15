package edu.asu.spring.quadriga.domain.impl.workbench;

import org.springframework.stereotype.Service;

import edu.asu.spring.quadriga.domain.IStatisticsSettings;

/**
 * @description : Statistics Settings class describing its properties.
 * 
 * @author : Ajay Modi
 * 
 */
@Service
public class StatisticsSettings implements IStatisticsSettings {
    private String projectId;
    private String name;
    private Boolean isChecked;
    private String message;

    @Override
    public String getProjectId() {
        return projectId;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Boolean getIsChecked() {
        return isChecked;
    }

    @Override
    public void setIsChecked(Boolean isChecked) {
        this.isChecked = isChecked;
    }

}
