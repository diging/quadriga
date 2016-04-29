package edu.asu.spring.quadriga.domain;

/**
 * Interface for project statistics settings object.
 * 
 * @author Ajay Modi
 *
 */

public interface IStatisticsSettings {

    public abstract String getProjectId();

    public abstract void setProjectId(String projectId);

    public abstract String getName();

    public abstract void setName(String name);

    public abstract String getMessage();

    public abstract void setMessage(String message);

    public abstract Boolean getIsChecked();

    public abstract void setIsChecked(Boolean ischecked);

}
