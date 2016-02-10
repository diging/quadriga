package edu.asu.spring.quadriga.domain.impl.workspace;

import edu.asu.spring.quadriga.domain.workspace.ITextFile;

public class TextFile implements ITextFile {

    private String fileName;
    private String projectId;
    private String workspaceId;
    private String refId;
    
    @Override
    public String getFileName() {
        return fileName;
    }
    
    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public String getProjectId() {
        return projectId;
    }
    
    @Override
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    
    @Override
    public String getWorkspaceId() {
        return workspaceId;
    }
    
    @Override
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }
    
    @Override
    public String getRefId() {
        return refId;
    }
    
    
    public void setRefId(String refId) {
        this.refId = refId;
    }
    

}
