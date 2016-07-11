package edu.asu.spring.quadriga.dto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_projectresolver")
public class ProjectHandleResolverDTO {

    @Id
    private String id;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    private String projectName;
    private String description;
    private String projectUrl;
    private String username;
    
    private String resolvedHandlePattern;
    private String handlePattern;
    private String handleExample;
    private String resolvedHandleExample;
    
    public String getProjectName() {
        return projectName;
    }
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getProjectUrl() {
        return projectUrl;
    }
    public void setProjectUrl(String projectUrl) {
        this.projectUrl = projectUrl;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getResolvedHandlePattern() {
        return resolvedHandlePattern;
    }
    public void setResolvedHandlePattern(String resolvedHandlePattern) {
        this.resolvedHandlePattern = resolvedHandlePattern;
    }
    public String getHandlePattern() {
        return handlePattern;
    }
    public void setHandlePattern(String handlePattern) {
        this.handlePattern = handlePattern;
    }
    public String getHandleExample() {
        return handleExample;
    }
    public void setHandleExample(String handleExample) {
        this.handleExample = handleExample;
    }
    public String getResolvedHandleExample() {
        return resolvedHandleExample;
    }
    public void setResolvedHandleExample(String resolvedHandleExample) {
        this.resolvedHandleExample = resolvedHandleExample;
    }
    
    
}

